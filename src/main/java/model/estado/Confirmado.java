package model.estado;

import model.partido.Partido;
import java.util.Date;
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

    @Override
    public void confirmar(Partido partido) {
        System.out.println("El partido ya está confirmado");
    }

    @Override
    public void jugar(Partido partido) {
        Date horaActual = new Date();
        if (horaActual.after(partido.getHorario())) {
            partido.cambiarEstado(new EnJuego());
        } else {
            System.out.println("El partido aún no puede comenzar, debe esperar a la hora programada");
        }
    }


}