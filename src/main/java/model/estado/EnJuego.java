package model.estado;

import data.JugadorDAO;
import model.partido.Jugador;
import model.partido.Partido;

import java.io.Serializable;

public class EnJuego implements IEstadoPartido, Serializable {
    private static final long serialVersionUID = 1L;
    @Override
    public void iniciar(Partido partido) {
        System.out.println("El partido ya está en curso.");
    }

    @Override
    public void finalizar(Partido partido) {
        if (!(partido.getEstado() instanceof Finalizado)) {
            JugadorDAO jugadorDAO = JugadorDAO.getInstancia();
            for (Jugador jugador : partido.getJugadores()) {
                jugador.setCantidadPartidosJugados(jugador.getCantidadPartidosJugados() + 1);
                jugadorDAO.actualizarJugador(jugador);
                System.out.println("Incrementado partidos jugados para: " + jugador.getUsername() +
                        " - Total: " + jugador.getCantidadPartidosJugados());
            }
            jugadorDAO.guardar();
        }
        System.out.println("Partido finalizado.");
        partido.cambiarEstado(new Finalizado());
    }

    @Override
    public void cancelar(Partido partido) {
        System.out.println("No se puede cancelar un partido en juego.");
    }

    @Override
    public void confirmar(Partido partido) {
        System.out.println("No se puede confirmar un partido que ya está en juego");
    }

    @Override
    public void jugar(Partido partido) {
        System.out.println("El partido ya está en juego");
    }


}