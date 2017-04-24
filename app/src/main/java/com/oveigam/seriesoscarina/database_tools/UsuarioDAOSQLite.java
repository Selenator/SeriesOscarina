package com.oveigam.seriesoscarina.database_tools;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.oveigam.seriesoscarina.entities.Disco;
import com.oveigam.seriesoscarina.entities.Serie;

import java.util.ArrayList;


/**
 * Created by dam224 on 07/11/2016.
 */

public class UsuarioDAOSQLite implements UsuarioDAO {
    private SQLopener app;
    private Context context;

    public UsuarioDAOSQLite(Context context) {
        this.context = context;
        this.app = new SQLopener(this.context);
    }

    @Override
    public ArrayList<Serie> getSeries() {
        return getSeries("nombre");
    }

    @Override
    public ArrayList<Serie> getSeries(String orden) {
        ArrayList<Serie> resultado = new ArrayList<>();
        SQLiteDatabase sqlLiteDB = app.getWritableDatabase();
        String consulta = "SELECT * FROM series ORDER BY "+orden;
        Cursor cursor = sqlLiteDB.rawQuery(consulta, null);
        int i = 0;
        if (cursor.moveToFirst()) {
            while (i < cursor.getCount()) {
                resultado.add(new Serie(
                        cursor.getInt(0),
                        cursor.getString(1),
                        getNombreDisco(cursor.getInt(2)),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5).equals("true"),
                        cursor.getString(6).equals("true")));
                cursor.moveToNext();
                i++;
            }
        }


        return resultado;
    }

    @Override
    public ArrayList<Serie> getSeries(int id_disco) {
        return getSeries(id_disco, "nombre");
    }

    @Override
    public ArrayList<Serie> getSeries(int id_disco, String orden) {
        ArrayList<Serie> resultado = new ArrayList<>();
        SQLiteDatabase sqlLiteDB = app.getWritableDatabase();
        String consulta = "SELECT * FROM series WHERE disco="+id_disco+" ORDER BY nombre";
        Cursor cursor = sqlLiteDB.rawQuery(consulta, null);
        int i = 0;
        if (cursor.moveToFirst()) {
            while (i < cursor.getCount()) {
                resultado.add(new Serie(
                        cursor.getInt(0),
                        cursor.getString(1),
                        getNombreDisco(cursor.getInt(2)),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5).equals("true"),
                        cursor.getString(6).equals("true")));
                cursor.moveToNext();
                i++;
            }
        }

        return resultado;
    }

    @Override
    public ArrayList<Disco> getDiscos() {
        ArrayList<Disco> resultado = new ArrayList<>();
        SQLiteDatabase sqlLiteDB = app.getWritableDatabase();
        String consulta = "SELECT * FROM discos ORDER BY id";
        Cursor cursor = sqlLiteDB.rawQuery(consulta, null);
        int i = 0;
        if (cursor.moveToFirst()) {
            while (i < cursor.getCount()) {
                resultado.add(new Disco(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getFloat(2)
                ));
                cursor.moveToNext();
                i++;
            }
        }

        return resultado;
    }

    @Override
    public Serie getSerie(int id) {
        return null;
    }

    @Override
    public String getNombreDisco(int id_disco) {
        SQLiteDatabase sqlLiteDB = app.getWritableDatabase();
        String[] param = {String.valueOf(id_disco)};
        String consulta = "SELECT nombre FROM discos where id=?";
        Cursor cursor = sqlLiteDB.rawQuery(consulta, param);
        if (cursor.moveToFirst()) {
            return cursor.getString(0);
        }

        return "";
    }




    /*

    @Override
    public boolean existeLogin(String login) {
        SQLiteDatabase sqlLiteDB = app.getWritableDatabase();
        String[] param = {login};
        String consulta = "SELECT * FROM usuarios WHERE login=?";
        Cursor cursor = sqlLiteDB.rawQuery(consulta, param);

        return cursor.moveToFirst();
    }

    @Override
    public boolean existeCorreo(String email) {
        SQLiteDatabase sqlLiteDB = app.getWritableDatabase();
        String[] param = {email};
        String consulta = "SELECT * FROM usuarios WHERE email=?";
        Cursor cursor = sqlLiteDB.rawQuery(consulta, param);

        return cursor.moveToFirst();
    }

    @Override
    public Usuario getUsuario(String login, String password) {
        Usuario resultado = null;
        SQLiteDatabase sqlLiteDB = app.getWritableDatabase();
        String[] param = {login, password};
        String consultaUsers = "SELECT * FROM usuarios WHERE login=? AND password=?";
        Log.d("DEPURACIÓN", "Consulta: " + consultaUsers);
        Cursor cursorUsers = sqlLiteDB.rawQuery(consultaUsers, param);
        Log.d("DEPURACIÓN", "Nº filas: " + cursorUsers.getCount());
        if (cursorUsers.moveToFirst()) {
            resultado = new Usuario(cursorUsers.getInt(0),
                    cursorUsers.getString(1),
                    cursorUsers.getString(2),
                    cursorUsers.getString(3),
                    cursorUsers.getString(4),
                    cursorUsers.getString(5),
                    cursorUsers.getString(6).equals("true"),
                    cursorUsers.getInt(7),
                    getMultas(cursorUsers.getInt(0)));
        }
        return resultado;
    }

    @Override
    public ArrayList<Multa> getMultas(int id_user) {
        ArrayList<Multa> resultado = new ArrayList<>();
        SQLiteDatabase sqlLiteDB = app.getWritableDatabase();
        String[] param = {String.valueOf(id_user)};
        String consulta = "SELECT * FROM multas WHERE id_jugador = ? ORDER BY pagada";
        Cursor cursor = sqlLiteDB.rawQuery(consulta, param);
        int i = 0;
        if (cursor.moveToFirst()) {
            while (i < cursor.getCount()) {
                resultado.add(new Multa(cursor.getInt(0),
                        cursor.getFloat(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4).equals("true"),
                        cursor.getInt(5)));
                cursor.moveToNext();
                i++;
            }
        }

        return resultado;
    }

    @Override
    public ArrayList<Evento> getEventos(int id_equipo) {
        ArrayList<Evento> resultado = new ArrayList<>();
        SQLiteDatabase sqlLiteDB = app.getWritableDatabase();
        String[] param = {String.valueOf(id_equipo)};
        String consulta = "SELECT * FROM eventos WHERE equipo_id = ? ORDER BY fecha";
        Cursor cursor = sqlLiteDB.rawQuery(consulta, param);
        int i = 0;
        if (cursor.moveToFirst()) {
            while (i < cursor.getCount()) {
                resultado.add(new Evento(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getInt(6)));
                cursor.moveToNext();
                i++;
            }
        }

        return resultado;
    }

    @Override
    public ArrayList<String> getNombreEquipos() {
        ArrayList<String> resultado = new ArrayList<>();
        SQLiteDatabase sqlLiteDB = app.getWritableDatabase();
        String consulta = "SELECT nombre FROM equipos";
        Cursor cursor = sqlLiteDB.rawQuery(consulta, null);
        int i = 0;
        if (cursor.moveToFirst()) {
            while (i < cursor.getCount()) {
                resultado.add(cursor.getString(0));
                cursor.moveToNext();
                i++;
            }
        }

        return resultado;
    }

    @Override
    public ArrayList<Multa> buscarMultas(String[] param) {
        ArrayList<Multa> resultado = new ArrayList<>();
        SQLiteDatabase sqlLiteDB = app.getWritableDatabase();
        String consulta = "SELECT * FROM multas m " +
                "INNER JOIN usuarios u ON m.id_jugador=u.id " +
                "INNER JOIN equipos e ON u.equipo_id = e.id " +
                "WHERE u.nombre = ? " +
                "AND u.apellidos = ? " +
                "AND e.nombre = ? " +
                "AND motivo LIKE ? " +
                "AND fecha BETWEEN ? AND ? " +
                "AND pagada = ? ";
        Cursor cursor = sqlLiteDB.rawQuery(consulta, param);
        int i = 0;
        if (cursor.moveToFirst()) {
            while (i < cursor.getCount()) {
                resultado.add(new Multa(cursor.getInt(0),
                        cursor.getFloat(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4).equals("true"),
                        cursor.getInt(5)));
                cursor.moveToNext();
                i++;
            }
        }

        return resultado;
    }

    @Override
    public ArrayList<Multa> buscarMultas(String busqueda, int id_jug) {
        ArrayList<Multa> resultado = new ArrayList<>();
        SQLiteDatabase sqlLiteDB = app.getWritableDatabase();
        String[] param = {String.valueOf(id_jug), "%" + busqueda + "%", "%" + busqueda + "%", "%" + busqueda + "%"};
        String consulta = "SELECT * FROM multas " +
                "WHERE id_jugador = ? " +
                "AND cantidad LIKE ? " +
                "OR fecha LIKE ? " +
                "OR motivo LIKE ?";
        Cursor cursor = sqlLiteDB.rawQuery(consulta, param);
        int i = 0;
        if (cursor.moveToFirst()) {
            while (i < cursor.getCount()) {
                resultado.add(new Multa(cursor.getInt(0),
                        cursor.getFloat(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4).equals("true"),
                        cursor.getInt(5)));
                cursor.moveToNext();
                i++;
            }
        }

        return resultado;
    }

    @Override
    public ArrayList<Usuario> buscarJugadores(String busqueda, int id_eq) {
        ArrayList<Usuario> resultado = new ArrayList<>();
        SQLiteDatabase sqlLiteDB = app.getWritableDatabase();
        String[] param = {String.valueOf(id_eq), "%" + busqueda + "%", "%" + busqueda + "%", "%" + busqueda + "%"};
        String consulta = "SELECT * FROM usuarios " +
                "WHERE equipo_id = ? " +
                "AND nombre LIKE ? " +
                "OR email LIKE ? " +
                "OR apellidos LIKE ?" +
                "ORDER BY nombre";
        Cursor cursor = sqlLiteDB.rawQuery(consulta, param);
        int i = 0;
        if (cursor.moveToFirst()) {
            while (i < cursor.getCount()) {
                resultado.add(new Usuario(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6).equals("true"),
                        cursor.getInt(7),
                        getMultas(cursor.getInt(0))));
                cursor.moveToNext();
                i++;
            }
        }
        return resultado;
    }

    @Override
    public ArrayList<Evento> buscarEventos(String busqueda, int id_eq) {
        ArrayList<Evento> resultado = new ArrayList<>();
        SQLiteDatabase sqlLiteDB = app.getWritableDatabase();
        String[] param = {String.valueOf(id_eq), "%" + busqueda + "%", "%" + busqueda + "%", "%" + busqueda + "%", "%" + busqueda + "%", "%" + busqueda + "%"};
        String consulta = "SELECT * FROM eventos " +
                "WHERE equipo_id = ? " +
                "AND tipo LIKE ? " +
                "OR fecha LIKE ? " +
                "OR hora LIKE ? " +
                "OR localizacion LIKE ? " +
                "OR comentario LIKE ? " +
                "ORDER BY fecha";
        Cursor cursor = sqlLiteDB.rawQuery(consulta, param);
        int i = 0;
        if (cursor.moveToFirst()) {
            while (i < cursor.getCount()) {
                resultado.add(new Evento(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getInt(6)));
                cursor.moveToNext();
                i++;
            }
        }

        return resultado;
    }

    @Override
    public Equipo getEquipo(int id) {
        Equipo resultado = null;
        SQLiteDatabase sqlLiteDB = app.getWritableDatabase();
        String[] param = {String.valueOf(id)};
        String consultaEquipo = "SELECT * FROM equipos WHERE id=?";
        Cursor cursorEquipo = sqlLiteDB.rawQuery(consultaEquipo, param);
        String consultaUsers = "SELECT * FROM usuarios WHERE equipo_id=? " +
                "ORDER BY nombre";
        Cursor cursorUsers = sqlLiteDB.rawQuery(consultaUsers, param);
        ArrayList<Usuario> jugadores = new ArrayList<>();
        if (cursorEquipo.moveToFirst() && cursorUsers.moveToFirst()) {
            int i = 0;
            while (i < cursorUsers.getCount()) {
                jugadores.add(new Usuario(cursorUsers.getInt(0),
                        cursorUsers.getString(1),
                        cursorUsers.getString(2),
                        cursorUsers.getString(3),
                        cursorUsers.getString(4),
                        cursorUsers.getString(5),
                        cursorUsers.getString(6).equals("true"),
                        cursorUsers.getInt(7),
                        getMultas(cursorUsers.getInt(0))));
                cursorUsers.moveToNext();
                i++;
            }
            resultado = new Equipo(cursorEquipo.getInt(0),
                    cursorEquipo.getString(1),
                    cursorEquipo.getInt(2),
                    jugadores, getEventos(cursorEquipo.getInt(2)));
        }
        return resultado;
    }

    @Override
    public int getIdEquipo(String nombre){
        SQLiteDatabase sqlLiteDB = app.getWritableDatabase();
        String[] param = {nombre};
        String consultaEquipo = "SELECT id FROM equipos WHERE nombre=?";
        Cursor cursor = sqlLiteDB.rawQuery(consultaEquipo, param);
        if(cursor.moveToFirst()){
            return cursor.getInt(0);
        }else{
            return -1;
        }
    }


    //PARA REGISTRAR UN NUEVO USUARIO EN LA BASE DE DATOS
    @Override
    public boolean insertarUsuario(Usuario usr, boolean tieneEquipo) {
        boolean resultado = true;
        SQLiteDatabase sqlLiteDB = app.getWritableDatabase();
        ContentValues param = new ContentValues();
        param.put("login", usr.getLogin());
        param.put("password", usr.getPassword());
        param.put("email", usr.getEmail());
        param.put("nombre", usr.getNombre());
        param.put("apellidos", usr.getApellidos());
        param.put("esEntrenador", false);
        if (tieneEquipo)
            param.put("equipo_id", usr.getId_equipo());

        long fila = sqlLiteDB.insert("usuarios", null, param);

        if (fila == -1)
            return false;
        else {
            String usuarios = "";
            Cursor cursor = sqlLiteDB.rawQuery("select * from usuarios", null);
            if (cursor.moveToFirst()) {                // Se non ten datos xa non entra
                while (!cursor.isAfterLast()) {     // Quédase no bucle ata que remata de percorrer o cursor. Fixarse que leva un ! (not) diante
                    usuarios += " " + cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(2) + " " + cursor.getString(3) + " " + cursor.getString(4);
                    cursor.moveToNext();
                }
            }
            Log.d("DEPURACÍON", "11VMO Resultado inserción: " + usuarios);

            return resultado;
        }
    }

    @Override
    public boolean actualizarUsuario(Usuario usr, int id) {
        boolean resultado = true;
        SQLiteDatabase sqlLiteDB = app.getWritableDatabase();
        String consulta = "UPDATE usuarios " +
                "SET login = '" + usr.getLogin() + "', password = '" + usr.getPassword() + "', nombre = '" + usr.getNombre() + "', apellidos = '" + usr.getApellidos() + "', email = '" + usr.getEmail() + "' " +
                "WHERE id = '" + id + "'";
        Log.d("DEPURACIÓN", "Consulta ACTUALIZAR: " + consulta);
        try {
            sqlLiteDB.execSQL(consulta);
        //Comprobación de la lista de usuarios. El siguiente código tiene como finalidad
        //mostrar en el logcat el usuario que se acaba de insertar.

            String usuarios = "";
            Cursor cursor = sqlLiteDB.rawQuery("select * from usuarios", null);
            if (cursor.moveToFirst()) {                // Se non ten datos xa non entra
                while (!cursor.isAfterLast()) {     // Quédase no bucle ata que remata de percorrer o cursor. Fixarse que leva un ! (not) diante
                    usuarios += " " + cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(2) + " " + cursor.getString(3) + " " + cursor.getString(4);
                    cursor.moveToNext();
                }
            }
            Log.d("DEPURACÍON", "Resultado ACTUALIZAR: " + usuarios);
        } catch (SQLException e) {
            resultado = false;
        }

        return resultado;
    }
    */

}
