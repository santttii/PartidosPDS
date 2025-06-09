package model.estado;

import model.partido.Partido;

public class Finalizado implements IEstadoPartido {
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
}
