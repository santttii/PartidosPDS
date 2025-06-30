package model.estado;

import model.partido.Partido;

import java.io.Serializable;

public class Finalizado implements IEstadoPartido, Serializable {
    private static final long serialVersionUID = 1L;
    @Override
    public void iniciar(Partido partido) {
        System.out.println("No se puede reiniciar un partido ya finalizado.");
    }

    @Override
    public void finalizar(Partido partido) {
        System.out.println("El partido ya fue finalizado.");
    }

    @Override
    public void cancelar(Partido partido) {
        System.out.println("No se puede cancelar un partido ya finalizado.");
    }

    @Override
    public void confirmar(Partido partido) {
        System.out.println("No se puede confirmar un partido finalizado");
    }

    @Override
    public void jugar(Partido partido) {
        System.out.println("No se puede jugar un partido que ya está finalizado");
    }


}
