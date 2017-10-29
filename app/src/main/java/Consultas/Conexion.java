package Consultas;

import Clases.Comidas;
import android.support.v7.util.SortedList;
import retrofit.Callback;

import java.util.ArrayList;

import Clases.Persona;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Body;
import retrofit.http.Query;

/**
 * Created by Estudiante on 10/10/2015.
 */
public interface Conexion {

    @GET("/Personas/Persona/{id}")
    void getPersona(@Path("id") int id, Callback<Persona> usersCallback);

    @GET("/Comidas")
    void getComidasInformation(Callback<ArrayList<Comidas>> usersCallback);

    @GET("/Comidas/Desayuno")
    void getDesayuno(Callback<ArrayList<Comidas>> usersCallback);

    @GET("/Comidas/Almuerzo")
    void getAlmuerzo(Callback<ArrayList<Comidas>> usersCallback);

    @GET("/Comidas/Cena")
    void getCena(Callback<ArrayList<Comidas>> usersCallback);
}
