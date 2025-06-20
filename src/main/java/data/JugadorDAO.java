package data;

import java.util.ArrayList;
import java.util.List;

import model.partido.Jugador;
import model.partido.Deporte;

public class JugadorDAO {

    private ArrayList<Jugador> jugadores;
    private static int siguienteId = 1;

    public JugadorDAO() {
        this.jugadores = new ArrayList<>();
    }

    /**
     * Agrega un nuevo jugador a la lista
     */
    public boolean agregarJugador(Jugador jugador) {
        if (jugador == null) {
            return false;
        }

        // Asignar ID único si no tiene uno
        if (jugador.getIdJugador() == 0) {
            jugador.setIdJugador(siguienteId++);
        }

        return jugadores.add(jugador);
    }

    /**
     * Elimina un jugador de la lista
     */
    public boolean eliminarJugador(Jugador jugador) {
        return jugadores.remove(jugador);
    }

    /**
     * Busca un jugador por su ID
     */
    public Jugador buscarPorId(int id) {
        for (Jugador jugador : jugadores) {
            if (jugador.getIdJugador() == id) {
                return jugador;
            }
        }
        return null;
    }

    /**
     * Busca un jugador por su username
     */
    public Jugador buscarPorUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return null;
        }

        for (Jugador jugador : jugadores) {
            if (jugador.getUsername().equals(username)) {
                return jugador;
            }
        }
        return null;
    }

    /**
     * Busca un jugador por su email
     */
    public Jugador buscarPorEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return null;
        }

        for (Jugador jugador : jugadores) {
            if (jugador.getEmail().equals(email)) {
                return jugador;
            }
        }
        return null;
    }

    /**
     * Busca jugadores por nivel
     */
    public List<Jugador> buscarPorNivel(Jugador.NivelJugador nivel) {
        List<Jugador> resultado = new ArrayList<>();

        if (nivel == null) {
            return resultado;
        }

        for (Jugador jugador : jugadores) {
            if (jugador.getNivel().equals(nivel)) {
                resultado.add(jugador);
            }
        }

        return resultado;
    }

    /**
     * Busca jugadores por deporte favorito
     */
    public List<Jugador> buscarPorDeporte(Deporte deporte) {
        List<Jugador> resultado = new ArrayList<>();

        if (deporte == null) {
            return resultado;
        }

        for (Jugador jugador : jugadores) {
            if (jugador.getDeporteFavorito().equals(deporte)) {
                resultado.add(jugador);
            }
        }

        return resultado;
    }

    /**
     * Busca jugadores por ubicación (búsqueda parcial)
     */
    public List<Jugador> buscarPorUbicacion(String ubicacion) {
        List<Jugador> resultado = new ArrayList<>();

        if (ubicacion == null || ubicacion.trim().isEmpty()) {
            return resultado;
        }

        String ubicacionLower = ubicacion.toLowerCase();
        for (Jugador jugador : jugadores) {
            if (jugador.getUbicacion().toLowerCase().contains(ubicacionLower)) {
                resultado.add(jugador);
            }
        }

        return resultado;
    }

    /**
     * Busca jugadores por múltiples criterios
     */
    public List<Jugador> buscarPorCriterios(Jugador.NivelJugador nivel, Deporte deporte, String ubicacion) {
        List<Jugador> resultado = new ArrayList<>();

        for (Jugador jugador : jugadores) {
            boolean cumpleNivel = (nivel == null) || jugador.getNivel().equals(nivel);
            boolean cumpleDeporte = (deporte == null) || jugador.getDeporteFavorito().equals(deporte);
            boolean cumpleUbicacion = (ubicacion == null || ubicacion.trim().isEmpty()) ||
                    jugador.getUbicacion().toLowerCase().contains(ubicacion.toLowerCase());

            if (cumpleNivel && cumpleDeporte && cumpleUbicacion) {
                resultado.add(jugador);
            }
        }

        return resultado;
    }

    /**
     * Verifica si un username ya existe
     */
    public boolean existeUsername(String username) {
        return buscarPorUsername(username) != null;
    }

    /**
     * Verifica si un email ya existe
     */
    public boolean existeEmail(String email) {
        return buscarPorEmail(email) != null;
    }

    /**
     * Obtiene todos los jugadores
     */
    public List<Jugador> obtenerTodos() {
        return new ArrayList<>(jugadores);
    }

    /**
     * Obtiene la cantidad total de jugadores
     */
    public int obtenerCantidad() {
        return jugadores.size();
    }

    /**
     * Obtiene estadísticas generales
     */
    public String obtenerEstadisticas() {
        int principiantes = 0, intermedios = 0, avanzados = 0;

        for (Jugador jugador : jugadores) {
            switch (jugador.getNivel()) {
                case PRINCIPIANTE:
                    principiantes++;
                    break;
                case INTERMEDIO:
                    intermedios++;
                    break;
                case AVANZADO:
                    avanzados++;
                    break;
            }
        }

        StringBuilder stats = new StringBuilder();
        stats.append("=== ESTADÍSTICAS DE JUGADORES ===\n");
        stats.append("Total jugadores: ").append(jugadores.size()).append("\n");
        stats.append("Principiantes: ").append(principiantes).append("\n");
        stats.append("Intermedios: ").append(intermedios).append("\n");
        stats.append("Avanzados: ").append(avanzados).append("\n");

        return stats.toString();
    }

    /**
     * Limpia todos los jugadores (útil para testing)
     */
    public void limpiar() {
        jugadores.clear();
        siguienteId = 1;
    }
}