package model.estado;

import model.partido.Partido;

import java.io.Serializable;

public class Confirmado implements IEstadoPartido, Serializable {
    private static final long serialVersionUID = 1L;
    @Override
    public void iniciar(Partido partido) {
        System.out.println("Partido iniciado.");
        partido.cambiarEstado(new EnJuego());
    }

    @Override
    public void finalizar(Partido partido) {
        System.out.println("No se puede finalizar un partido que no ha comenzado.");
    }

    @Override
    public void cancelar(Partido partido) {
        System.out.println("Partido cancelado.");
        partido.cambiarEstado(new Cancelado());
    }
}