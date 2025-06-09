package main.java.model.estado;

import main.java.model.partido.Jugador;
import main.java.model.partido.Partido;

public class EnJuego implements IEstadoPartido {
    @Override
    public void iniciar(Partido partido) {
        System.out.println("El partido ya está en curso.");
    }

    @Override
    public void finalizar(Partido partido) {
        if (!(partido.getEstado() instanceof Finalizado)) { 
            for (Jugador jugador : partido.getJugadores()) {
                jugador.incrementarCantidadPartidosJugados();
            }
        }
        System.out.println("Partido finalizado.");
        partido.cambiarEstado(new Finalizado());
    }

    @Override
    public void cancelar(Partido partido) {
        System.out.println("No se puede cancelar un partido en juego.");
    }
}
