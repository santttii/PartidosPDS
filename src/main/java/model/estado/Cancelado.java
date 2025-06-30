package model.estado;

import model.partido.Partido;

import java.io.Serializable;

public class Cancelado implements IEstadoPartido, Serializable {
    private static final long serialVersionUID = 1L;
    @Override
    public void iniciar(Partido partido) {
        System.out.println("No se puede iniciar un partido cancelado.");
    }

    @Override
    public void finalizar(Partido partido) {
        System.out.println("No se puede finalizar un partido cancelado.");
    }

    @Override
    public void cancelar(Partido partido) {
        System.out.println("El partido ya está cancelado.");
    }

    @Override
    public void confirmar(Partido partido) {
        System.out.println("No se puede confirmar un partido cancelado");
    }

    @Override
    public void jugar(Partido partido) {
        System.out.println("No se puede jugar un partido cancelado");
    }


}