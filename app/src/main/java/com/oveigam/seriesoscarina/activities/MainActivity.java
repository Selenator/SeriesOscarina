package com.oveigam.seriesoscarina.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.oveigam.seriesoscarina.R;
import com.oveigam.seriesoscarina.adapters.SerieAdapter;
import com.oveigam.seriesoscarina.database_tools.SQLopener;
import com.oveigam.seriesoscarina.database_tools.UsuarioDAOSQLite;
import com.oveigam.seriesoscarina.entities.Disco;
import com.oveigam.seriesoscarina.entities.Serie;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    int filtroActivo;
    UsuarioDAOSQLite usuDAO;
    SerieAdapter adapter;
    private ArrayList<Disco> discos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        copiarBD();
        filtroActivo = -1;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu drawerMenu = navigationView.getMenu();
        View cabecera = navigationView.getHeaderView(0);

        navigationView.setNavigationItemSelectedListener(this);

        usuDAO = new UsuarioDAOSQLite(this);

        adapter = new SerieAdapter(this, usuDAO.getSeries(), usuDAO);
        ListView list = (ListView) findViewById(R.id.lista_series);
        list.setAdapter(adapter);

        Menu menuDiscos =  drawerMenu.getItem(0).getSubMenu();

        discos = usuDAO.getDiscos();
        int i = 0;
        for (Disco d : discos) {
            menuDiscos.add(R.id.grupo_discos, d.getId(), 0, d.getNombre());
            menuDiscos.getItem(i).setIcon(R.drawable.hdd);
            i++;
        }

        ((TextView) cabecera.findViewById(R.id.cabecera_titulos)).setText(SQLopener.NOME_BD);
        ((TextView) cabecera.findViewById(R.id.cabecera_numSeries)).setText(adapter.getCount()+"");
        float espacio = 0;
        for(Disco d:discos){
            espacio+=d.getCapacidad();
        }
        ((TextView) cabecera.findViewById(R.id.cabecera_espacio)).setText(espacio+" GB");

        getSupportActionBar().setTitle("Series");

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (filtroActivo!=-1) {
            adapter.mostrarTodo();
            ((ListView) findViewById(R.id.lista_series)).smoothScrollToPosition(0);
            filtroActivo = -1;
            getSupportActionBar().setTitle("Series");
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
        switch (id){
            case R.id.orderByNombre:
                adapter.ordenar("nombre",filtroActivo);
                break;
            case R.id.orderByDisco:
                adapter.ordenar("disco",filtroActivo);
                break;
            case R.id.orderByAudio:
                adapter.ordenar("audio",filtroActivo);
                break;
            case R.id.orderBySub:
                adapter.ordenar("subtitulos",filtroActivo);
                break;
        }

        item.setChecked(true);

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        getSupportActionBar().setTitle(usuDAO.getNombreDisco(id));
        adapter.filtrarPorDisco(id);
        ((ListView) findViewById(R.id.lista_series)).smoothScrollToPosition(0);
        filtroActivo = id;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void copiarBD() {
        String bddestino = "/data/data/" + getPackageName() + "/databases/"
                + SQLopener.NOME_BD;
        File file = new File(bddestino);
        Log.d("DEPURACIÓN", "Ruta archivo BD: " + bddestino);
        if (file.exists()) {
//            Toast.makeText(getApplicationContext(), "DATABASE ALREADY CREATED", Toast.LENGTH_LONG).show();
            return; // XA EXISTE A BASE DE DATOS
        }


        String pathbd = "/data/data/" + getPackageName()
                + "/databases/";
        File filepathdb = new File(pathbd);
        filepathdb.mkdirs();


        InputStream inputstream;
        try {
            inputstream = getAssets().open(SQLopener.NOME_BD);
            OutputStream outputstream = new FileOutputStream(bddestino);


            int tamread;
            byte[] buffer = new byte[2048];


            while ((tamread = inputstream.read(buffer)) > 0) {
                outputstream.write(buffer, 0, tamread);
            }


            inputstream.close();
            outputstream.flush();
            outputstream.close();
            Toast.makeText(getApplicationContext(), "DATABASE CREATED", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
