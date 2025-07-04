package controller;

import model.emparejamiento.EmparejamientoPorNivel;
import model.emparejamiento.IEstrategiaEmparejamiento;
import model.estado.*;
import model.partido.Deporte;
import model.partido.Jugador;
import model.partido.Partido;
import model.notificacion.Notification;
import data.PartidoDAO;
import data.JugadorDAO;

import java.util.Date;
import java.util.List;

public class PartidoController {

    private final PartidoDAO gestorPartidos;

    public PartidoController() {
        this.gestorPartidos = PartidoDAO.getInstancia();
        gestorPartidos.cargar();
    }

    public PartidoController(PartidoDAO gestorPartidos) {
        this.gestorPartidos = gestorPartidos;
        gestorPartidos.cargar();
    }

    public Partido crearPartido(int cupoMaximo, Deporte deporte, String ubicacion,
                                Date horario, double duracion, IEstrategiaEmparejamiento emparejamiento,
                                Jugador creador) {
        try {
            validarParametrosPartido(cupoMaximo, deporte, ubicacion, horario, duracion, emparejamiento);
            if (creador == null) {
                throw new IllegalArgumentException("El creador del partido no puede ser nulo");
            }

            IEstadoPartido estadoInicial = new NecesitamosJugadores();
            Partido nuevoPartido = new Partido(cupoMaximo, deporte, ubicacion,
                    horario, duracion, estadoInicial, emparejamiento);

            nuevoPartido.setCreador(creador); // ✅ ASIGNACIÓN DEL CREADOR

            // Agregar al creador como primer jugador
            nuevoPartido.agregarJugador(creador);

            if (gestorPartidos.agregarPartido(nuevoPartido)) {
                gestorPartidos.guardar();
                notificarJugadoresPorDeporte(nuevoPartido); // <-- Agrega esta línea
                System.out.println("Partido creado exitosamente en " + ubicacion + " para " + horario);
                return nuevoPartido;
            } else {
                System.err.println("Error al agregar el partido al gestor");
                return null;
            }

        } catch (Exception e) {
            System.err.println("Error al crear partido: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Partido crearPartido(Deporte deporte, String ubicacion,
                                Date horario, double duracion, IEstrategiaEmparejamiento emparejamiento,
                                Jugador creador) {
        int cupoMaximo = deporte.getCantidadJugadores();
        System.out.println("Creando partido con cupo automático: " + cupoMaximo +
                " jugadores para " + deporte.getNombre());

        return crearPartido(cupoMaximo, deporte, ubicacion, horario, duracion, emparejamiento, creador);
    }

    public boolean agregarJugadorAPartido(Partido partido, Jugador jugador) {
        try {
            if (partido == null || jugador == null) {
                throw new IllegalArgumentException("El partido o jugador no puede ser nulo");
            }

            boolean jugadorYaExiste = partido.getJugadores().stream()
                    .anyMatch(j -> j.getIdJugador() == jugador.getIdJugador());

            if (jugadorYaExiste) {
                System.out.println("El jugador ya está en el partido.");
                return false;
            }

            int jugadoresAntes = partido.getJugadores().size();
            partido.agregarJugador(jugador);
            boolean resultado = partido.getJugadores().size() > jugadoresAntes;

            if (resultado) {
                gestorPartidos.guardar();
            }

            return resultado;
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
            boolean resultado = partido.getJugadores().size() < jugadoresAntes;

            if (resultado) {
                gestorPartidos.guardar();
            }

            return resultado;
        } catch (Exception e) {
            System.err.println("Error al eliminar jugador del partido: " + e.getMessage());
            return false;
        }
    }

    public boolean confirmarPartido(Partido partido) {
        try {
            if (partido == null) {
                throw new IllegalArgumentException("El partido no puede ser nulo");
            }

            partido.confirmarPartido();
            gestorPartidos.guardar();
            return true;
        } catch (Exception e) {
            System.err.println("Error al confirmar partido: " + e.getMessage());
            return false;
        }
    }

    public boolean cancelarPartido(Partido partido) {
        try {
            if (partido == null) {
                throw new IllegalArgumentException("El partido no puede ser nulo");
            }

            partido.cancelarPartido();
            gestorPartidos.guardar();
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
            gestorPartidos.guardar();
            System.out.println("Estrategia de emparejamiento cambiada exitosamente");
            return true;
        } catch (Exception e) {
            System.err.println("Error al cambiar estrategia: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarPartido(Partido partido) {
        try {
            if (partido == null) {
                throw new IllegalArgumentException("El partido no puede ser nulo");
            }
            boolean resultado = gestorPartidos.eliminarPartido(partido);
            if (resultado) {
                gestorPartidos.guardar();
            }
            return resultado;
        } catch (Exception e) {
            System.err.println("Error al eliminar partido: " + e.getMessage());
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
                .append("Estado: ").append(partido.getNombreEstado()).append("\n");

        IEstrategiaEmparejamiento estrategia = partido.getEmparejamiento();

        info.append("Requisitos de nivel: ")
                .append(estrategia.getClass().getSimpleName())
                .append("\n");

        info.append("Jugadores: ").append(partido.getJugadores().size()).append("/")
                .append(partido.getCupoMaximo()).append("\n");

        if (!partido.getJugadores().isEmpty()) {
            info.append("Lista de jugadores:\n");
            for (Jugador jugador : partido.getJugadores()) {
                info.append("  - ").append(jugador.getUsername())
                        .append(" (").append(jugador.getNivel()).append(")\n");
            }
        }

        return info.toString();
    }

    public void actualizarEstadosPartidos() {
        Date horaActual = new Date();
        List<Partido> partidos = this.obtenerTodosLosPartidos();

        for (Partido partido : partidos) {
            Date horarioInicio = partido.getHorario();
            Date horarioFin = new Date(horarioInicio.getTime() +
                    (long)(partido.getDuracion() * 60 * 60 * 1000));

            if ("Confirmado".equals(partido.getNombreEstado())) {
                if (horaActual.after(horarioInicio) && horaActual.before(horarioFin)) {
                    partido.jugarPartido();
                    gestorPartidos.guardar();
                }
            }

            if ("EnJuego".equals(partido.getNombreEstado())) {
                if (horaActual.after(horarioFin)) {
                    partido.finalizarPartido();
                    gestorPartidos.guardar();
                }
            }
        }
    }

    public void guardarPartido(Partido partido) {
        gestorPartidos.guardar();
    }




    // ==== Búsquedas ====

    public List<Partido> buscarPartidosPorDeporte(Deporte deporte) {
        return gestorPartidos.buscarPorDeporte(deporte);
    }

    public List<Partido> buscarPartidosPorUbicacion(String ubicacion) {
        return gestorPartidos.buscarPorUbicacion(ubicacion);
    }

    public List<Partido> buscarPartidosPorEstado(String nombreEstado) {
        return gestorPartidos.buscarPorEstado(nombreEstado);
    }

    public List<Partido> buscarPartidosDisponibles() {
        return gestorPartidos.buscarDisponibles();
    }

    public List<Partido> buscarPartidosDisponiblesParaJugador(Jugador jugador) {
        return gestorPartidos.buscarDisponiblesParaJugador(jugador);
    }

    public List<Partido> buscarPartidosPorJugador(Jugador jugador) {
        return gestorPartidos.buscarPorJugador(jugador);
    }

    public List<Partido> buscarPartidosPorFecha(Date fecha) {
        return gestorPartidos.buscarPorFecha(fecha);
    }

    public List<Partido> buscarPartidosDespuesDeFecha(Date fecha) {
        return gestorPartidos.buscarDespuesDeFecha(fecha);
    }

    public List<Partido> buscarPartidosPorCriterios(Deporte deporte, String ubicacion, String estado, Date fechaDesde) {
        return gestorPartidos.buscarPorCriterios(deporte, ubicacion, estado, fechaDesde);
    }

    public List<Partido> obtenerPartidosProximos() {
        return gestorPartidos.obtenerPartidosProximos();
    }

    public List<Partido> obtenerTodosLosPartidos() {
        return gestorPartidos.obtenerTodos();
    }

    public int obtenerCantidadPartidos() {
        return gestorPartidos.obtenerCantidad();
    }

    public String obtenerEstadisticasPartidos() {
        return gestorPartidos.obtenerEstadisticas();
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

    private void notificarJugadoresPorDeporte(Partido partido) {
        if (partido == null) return;
        Deporte deporte = partido.getDeporte();
        // Cambia esto por el gestor de jugadores
        List<Jugador> jugadores = JugadorDAO.getInstancia().buscarPorDeporte(deporte);
        for (Jugador jugador : jugadores) {
            // Evitar notificar al creador si no quieres
            if (partido.getCreador() != null && jugador.getIdJugador() == partido.getCreador().getIdJugador()) continue;
            String mensaje = "¡Nuevo partido de " + deporte.getNombre() + " disponible!\n"
                    + "Ubicación: " + partido.getUbicacion() + "\n"
                    + "Fecha y hora: " + partido.getHorario();
            Notification noti = new Notification(
                mensaje,
                deporte,
                jugador.getEmail(),
                jugador.getTokenFCM()
            );
            jugador.serNotificado(partido, noti);
        }
    }
}
