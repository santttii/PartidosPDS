package controller;

import java.util.Date;
import java.util.List;

import model.partido.Partido;
import model.partido.Jugador;
import model.partido.Deporte;
import model.emparejamiento.IEstrategiaEmparejamiento;
import model.estado.IEstadoPartido;
import model.estado.Pendiente;
import data.PartidoDAO;

public class PartidoController {

    private final PartidoDAO gestorPartidos;

    public PartidoController() {
        this.gestorPartidos = new PartidoDAO();
    }

    public PartidoController(PartidoDAO gestorPartidos) {
        this.gestorPartidos = gestorPartidos;
    }

    public Partido crearPartido(int cupoMaximo, Deporte deporte, String ubicacion,
                                Date horario, double duracion, IEstrategiaEmparejamiento emparejamiento) {
        try {
            validarParametrosPartido(cupoMaximo, deporte, ubicacion, horario, duracion, emparejamiento);
            IEstadoPartido estadoInicial = new Pendiente();

            Partido nuevoPartido = new Partido(cupoMaximo, deporte, ubicacion,
                    horario, duracion, estadoInicial, emparejamiento);

            if (gestorPartidos.agregarPartido(nuevoPartido)) {
                System.out.println("Partido creado exitosamente en " + ubicacion + " para " + horario);
                return nuevoPartido;
            } else {
                System.err.println("Error al agregar el partido al gestor");
                return null;
            }

        } catch (Exception e) {
            System.err.println("Error al crear partido: " + e.getMessage());
            return null;
        }
    }

    public boolean agregarJugadorAPartido(Partido partido, Jugador jugador) {
        try {
            if (partido == null || jugador == null) {
                throw new IllegalArgumentException("El partido o jugador no puede ser nulo");
            }
            int jugadoresAntes = partido.getJugadores().size();
            partido.agregarJugador(jugador);
            return partido.getJugadores().size() > jugadoresAntes;
        } catch (Exception e) {
            System.err.println("Error al agregar jugador al partido: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarJugadorDePartido(Partido partido, Jugador jugador) {
        try {
            if (partido == null || jugador == null) {
                throw new IllegalArgumentException("El partido o jugador no puede ser nulo");
            }
            int jugadoresAntes = partido.getJugadores().size();
            partido.eliminarJugador(jugador);
            return partido.getJugadores().size() < jugadoresAntes;
        } catch (Exception e) {
            System.err.println("Error al eliminar jugador del partido: " + e.getMessage());
            return false;
        }
    }

    public boolean iniciarPartido(Partido partido) {
        try {
            if (partido == null) {
                throw new IllegalArgumentException("El partido no puede ser nulo");
            }
            partido.iniciarPartido();
            return true;
        } catch (Exception e) {
            System.err.println("Error al iniciar partido: " + e.getMessage());
            return false;
        }
    }

    public boolean finalizarPartido(Partido partido) {
        try {
            if (partido == null) {
                throw new IllegalArgumentException("El partido no puede ser nulo");
            }
            partido.finalizarPartido();
            for (Jugador jugador : partido.getJugadores()) {
                jugador.incrementarCantidadPartidosJugados();
            }
            return true;
        } catch (Exception e) {
            System.err.println("Error al finalizar partido: " + e.getMessage());
            return false;
        }
    }

    public boolean cancelarPartido(Partido partido) {
        try {
            if (partido == null) {
                throw new IllegalArgumentException("El partido no puede ser nulo");
            }
            partido.cancelarPartido();
            return true;
        } catch (Exception e) {
            System.err.println("Error al cancelar partido: " + e.getMessage());
            return false;
        }
    }

    public boolean cambiarEstrategiaEmparejamiento(Partido partido, IEstrategiaEmparejamiento nuevaEstrategia) {
        try {
            if (partido == null || nuevaEstrategia == null) {
                throw new IllegalArgumentException("El partido o estrategia no puede ser nulo");
            }
            partido.cambiarEstrategia(nuevaEstrategia);
            System.out.println("Estrategia de emparejamiento cambiada exitosamente");
            return true;
        } catch (Exception e) {
            System.err.println("Error al cambiar estrategia: " + e.getMessage());
            return false;
        }
    }

    public String obtenerInformacionPartido(Partido partido) {
        if (partido == null) return "Partido no válido";

        StringBuilder info = new StringBuilder();
        info.append("=== INFORMACIÓN DEL PARTIDO ===\n")
                .append("Deporte: ").append(partido.getDeporte()).append("\n")
                .append("Ubicación: ").append(partido.getUbicacion()).append("\n")
                .append("Horario: ").append(partido.getHorario()).append("\n")
                .append("Duración: ").append(partido.getDuracion()).append(" horas\n")
                .append("Estado: ").append(partido.getNombreEstado()).append("\n")
                .append("Jugadores: ").append(partido.getJugadores().size()).append("/")
                .append(partido.getCupoMaximo()).append("\n");

        if (!partido.getJugadores().isEmpty()) {
            info.append("Lista de jugadores:\n");
            for (Jugador jugador : partido.getJugadores()) {
                info.append("  - ").append(jugador.getUsername()).append(" (").append(jugador.getNivel()).append(")\n");
            }
        }

        return info.toString();
    }

    public PartidoDAO getGestorPartidos() {
        return gestorPartidos;
    }

    private void validarParametrosPartido(int cupoMaximo, Deporte deporte, String ubicacion,
                                          Date horario, double duracion, IEstrategiaEmparejamiento emparejamiento) {
        if (cupoMaximo <= 0) throw new IllegalArgumentException("El cupo máximo debe ser mayor a 0");
        if (deporte == null) throw new IllegalArgumentException("El deporte no puede ser nulo");
        if (ubicacion == null || ubicacion.trim().isEmpty()) throw new IllegalArgumentException("La ubicación no puede estar vacía");
        if (horario == null) throw new IllegalArgumentException("El horario no puede ser nulo");
        if (horario.before(new Date())) throw new IllegalArgumentException("El horario no puede ser en el pasado");
        if (duracion <= 0) throw new IllegalArgumentException("La duración debe ser mayor a 0");
        if (emparejamiento == null) throw new IllegalArgumentException("La estrategia de emparejamiento no puede ser nula");
    }
}
