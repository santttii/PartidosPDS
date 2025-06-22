package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.partido.Partido;
import model.partido.Jugador;
import model.partido.Deporte;
import util.Serializador;

public class PartidoDAO implements Serializable {
    private static final String ARCHIVO_PARTIDOS = "partidos";
    private static PartidoDAO instancia; // ← Singleton
    private ArrayList<Partido> partidos;

    public PartidoDAO() {
        this.partidos = new ArrayList<>();
    }

    public static PartidoDAO getInstancia() {
        if (instancia == null) {
            instancia = new PartidoDAO();
        }
        return instancia;
    }

    public void guardar() {
        Serializador.guardarDatos(partidos, ARCHIVO_PARTIDOS);
    }

    @SuppressWarnings("unchecked")
    public void cargar() {
        ArrayList<Partido> cargados = Serializador.cargarDatos(ARCHIVO_PARTIDOS, ArrayList.class);
        if (cargados != null) {
            this.partidos = cargados;
        }
    }

    /**
     * Agrega un nuevo partido a la lista
     */
    public boolean agregarPartido(Partido partido) {
        if (partido == null) {
            return false;
        }
        return partidos.add(partido);
    }

    /**
     * Elimina un partido de la lista
     */
    public boolean eliminarPartido(Partido partido) {
        return partidos.remove(partido);
    }

    /**
     * Busca partidos por deporte
     */
    public List<Partido> buscarPorDeporte(Deporte deporte) {
        List<Partido> resultado = new ArrayList<>();

        if (deporte == null) {
            return resultado;
        }

        for (Partido partido : partidos) {
            if (partido.getDeporte().equals(deporte)) {
                resultado.add(partido);
            }
        }

        return resultado;
    }

    /**
     * Busca partidos por ubicación (búsqueda parcial)
     */
    public List<Partido> buscarPorUbicacion(String ubicacion) {
        List<Partido> resultado = new ArrayList<>();

        if (ubicacion == null || ubicacion.trim().isEmpty()) {
            return resultado;
        }

        String ubicacionLower = ubicacion.toLowerCase();
        for (Partido partido : partidos) {
            if (partido.getUbicacion().toLowerCase().contains(ubicacionLower)) {
                resultado.add(partido);
            }
        }

        return resultado;
    }

    /**
     * Busca partidos por estado
     */
    public List<Partido> buscarPorEstado(String nombreEstado) {
        List<Partido> resultado = new ArrayList<>();

        if (nombreEstado == null || nombreEstado.trim().isEmpty()) {
            return resultado;
        }

        for (Partido partido : partidos) {
            if (partido.getNombreEstado().equals(nombreEstado)) {
                resultado.add(partido);
            }
        }

        return resultado;
    }

    /**
     * Busca partidos disponibles (Pendiente o NecesitamosJugadores)
     */
    public List<Partido> buscarDisponibles() {
        List<Partido> resultado = new ArrayList<>();

        for (Partido partido : partidos) {
            String estado = partido.getNombreEstado();
            if ("Pendiente".equals(estado) || "NecesitamosJugadores".equals(estado)) {
                resultado.add(partido);
            }
        }

        return resultado;
    }

    /**
     * Busca partidos disponibles para un jugador específico
     */
    public List<Partido> buscarDisponiblesParaJugador(Jugador jugador) {
        List<Partido> resultado = new ArrayList<>();

        if (jugador == null) {
            return resultado;
        }

        List<Partido> disponibles = buscarDisponibles();
        for (Partido partido : disponibles) {
            if (partido.esApto(jugador) && !partido.getJugadores().contains(jugador)) {
                resultado.add(partido);
            }
        }

        return resultado;
    }

    /**
     * Busca partidos donde participa un jugador específico
     */
    public List<Partido> buscarPorJugador(Jugador jugador) {
        List<Partido> resultado = new ArrayList<>();

        if (jugador == null) {
            return resultado;
        }

        for (Partido partido : partidos) {
            if (partido.getJugadores().contains(jugador)) {
                resultado.add(partido);
            }
        }

        return resultado;
    }

    /**
     * Busca partidos por fecha
     */
    public List<Partido> buscarPorFecha(Date fecha) {
        List<Partido> resultado = new ArrayList<>();

        if (fecha == null) {
            return resultado;
        }

        for (Partido partido : partidos) {
            // Comparar solo la fecha (sin hora)
            if (esMismaFecha(partido.getHorario(), fecha)) {
                resultado.add(partido);
            }
        }

        return resultado;
    }

    /**
     * Busca partidos después de una fecha específica
     */
    public List<Partido> buscarDespuesDeFecha(Date fecha) {
        List<Partido> resultado = new ArrayList<>();

        if (fecha == null) {
            return resultado;
        }

        for (Partido partido : partidos) {
            if (partido.getHorario().after(fecha)) {
                resultado.add(partido);
            }
        }

        return resultado;
    }

    /**
     * Busca partidos por múltiples criterios
     */
    public List<Partido> buscarPorCriterios(Deporte deporte, String ubicacion, String estado, Date fechaDesde) {
        List<Partido> resultado = new ArrayList<>();

        for (Partido partido : partidos) {
            boolean cumpleDeporte = (deporte == null) || partido.getDeporte().equals(deporte);
            boolean cumpleUbicacion = (ubicacion == null || ubicacion.trim().isEmpty()) ||
                    partido.getUbicacion().toLowerCase().contains(ubicacion.toLowerCase());
            boolean cumpleEstado = (estado == null || estado.trim().isEmpty()) ||
                    partido.getNombreEstado().equals(estado);
            boolean cumpleFecha = (fechaDesde == null) || partido.getHorario().after(fechaDesde);

            if (cumpleDeporte && cumpleUbicacion && cumpleEstado && cumpleFecha) {
                resultado.add(partido);
            }
        }

        return resultado;
    }

    /**
     * Obtiene partidos próximos (siguientes 24 horas)
     */
    public List<Partido> obtenerPartidosProximos() {
        List<Partido> resultado = new ArrayList<>();
        Date ahora = new Date();
        Date mañana = new Date(ahora.getTime() + 24 * 60 * 60 * 1000); // +24 horas

        for (Partido partido : partidos) {
            Date horarioPartido = partido.getHorario();
            if (horarioPartido.after(ahora) && horarioPartido.before(mañana)) {
                resultado.add(partido);
            }
        }

        return resultado;
    }

    /**
     * Obtiene todos los partidos
     */
    public List<Partido> obtenerTodos() {
        return new ArrayList<>(partidos);
    }

    /**
     * Obtiene la cantidad total de partidos
     */
    public int obtenerCantidad() {
        return partidos.size();
    }

    /**
     * Obtiene estadísticas generales de partidos
     */
    public String obtenerEstadisticas() {
        int pendientes = 0, necesitanJugadores = 0, confirmados = 0, finalizados = 0, cancelados = 0;

        for (Partido partido : partidos) {
            String estado = partido.getNombreEstado();
            switch (estado) {
                case "Pendiente":
                    pendientes++;
                    break;
                case "NecesitamosJugadores":
                    necesitanJugadores++;
                    break;
                case "Confirmado":
                    confirmados++;
                    break;
                case "Finalizado":
                    finalizados++;
                    break;
                case "Cancelado":
                    cancelados++;
                    break;
            }
        }

        StringBuilder stats = new StringBuilder();
        stats.append("=== ESTADÍSTICAS DE PARTIDOS ===\n");
        stats.append("Total partidos: ").append(partidos.size()).append("\n");
        stats.append("Pendientes: ").append(pendientes).append("\n");
        stats.append("Necesitan jugadores: ").append(necesitanJugadores).append("\n");
        stats.append("Confirmados: ").append(confirmados).append("\n");
        stats.append("Finalizados: ").append(finalizados).append("\n");
        stats.append("Cancelados: ").append(cancelados).append("\n");

        return stats.toString();
    }

    /**
     * Limpia todos los partidos (útil para testing)
     */
    public void limpiar() {
        partidos.clear();
    }

    /**
     * Método auxiliar para comparar fechas sin considerar la hora
     */
    private boolean esMismaFecha(Date fecha1, Date fecha2) {
        // Implementación simple - en producción usar Calendar o LocalDate
        long diff = Math.abs(fecha1.getTime() - fecha2.getTime());
        return diff < 24 * 60 * 60 * 1000; // Menos de 24 horas de diferencia
    }
}