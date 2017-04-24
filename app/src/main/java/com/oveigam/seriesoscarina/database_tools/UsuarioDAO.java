package com.oveigam.seriesoscarina.database_tools;


import com.oveigam.seriesoscarina.entities.Disco;
import com.oveigam.seriesoscarina.entities.Serie;

import java.util.ArrayList;

/**
 * Created by dam224 on 07/11/2016.
 */

public interface UsuarioDAO {
    ArrayList<Serie> getSeries();

    ArrayList<Serie> getSeries(String orden);

    ArrayList<Serie> getSeries(int id_disco);

    ArrayList<Serie> getSeries(int id_disco, String orden);

    ArrayList<Disco> getDiscos();

    Serie getSerie(int id_serie);

    String getNombreDisco(int id_disco);
}
