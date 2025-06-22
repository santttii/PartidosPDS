package model.partido;

import java.io.Serializable;

public class Futbol extends Deporte implements Serializable {
    public Futbol(String nombre, int cantidadJugadores, String descripcion) {
        super(nombre, cantidadJugadores, descripcion);
    }
}
