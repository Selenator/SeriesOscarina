package com.oveigam.seriesoscarina.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.oveigam.seriesoscarina.R;
import com.oveigam.seriesoscarina.database_tools.UsuarioDAOSQLite;
import com.oveigam.seriesoscarina.entities.Serie;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Oscarina on 13/04/2017.
 */
public class SerieAdapter extends ArrayAdapter<Serie> {

    private ArrayList<Serie> series;
    private UsuarioDAOSQLite usuDAO;

    public SerieAdapter(Context context, ArrayList<Serie> series, UsuarioDAOSQLite usuDAO) {
        super(context, 0, series);
        this.series = series;
        this.usuDAO = usuDAO;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Serie serie = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ficha_series, parent, false);
        }

        // Lookup view for data population
        TextView textTitulo = (TextView) convertView.findViewById(R.id.textTitulo);
        TextView textDisco = (TextView) convertView.findViewById(R.id.textDisco);

        textTitulo.setText(serie.getNombre());
        textDisco.setText(serie.getLocalizacion());

        ImageView audio = (ImageView) convertView.findViewById(R.id.imgAudio);
        ImageView subs = (ImageView) convertView.findViewById(R.id.imgSubs);

        switch (serie.getAudio()) {
            case "ESP":
                audio.setImageResource(R.drawable.esp);
                break;
            case "ING":
                audio.setImageResource(R.drawable.ing);
                break;
            case "JAP":
                audio.setImageResource(R.drawable.jap);
                break;
        }

        if (serie.getSubs() != null) {
            switch (serie.getSubs()) {
                case "ESP":
                    subs.setImageResource(R.drawable.sesp);
                    break;
                case "ING":
                    subs.setImageResource(R.drawable.sing);
                    break;
                default:
                    subs.setImageResource(R.drawable.smix);
                    break;

            }
        } else {
            subs.setImageDrawable(null);
        }

        if (!serie.isAldia()) {
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.superLightRed));
        } else {
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.white));
        }

        return convertView;

    }

    public void filtrarPorDisco(int id_disco) {
        clear();
        addAll(usuDAO.getSeries(id_disco));
        notifyDataSetChanged();
    }

    public void mostrarTodo() {
        clear();
        addAll(usuDAO.getSeries());
        notifyDataSetChanged();
    }

    public void ordenar(String orden, int id_disco) {
        clear();
        if (id_disco != -1)
            addAll(usuDAO.getSeries(id_disco, orden));
        else
            addAll(usuDAO.getSeries(orden));
        notifyDataSetChanged();
    }

}
