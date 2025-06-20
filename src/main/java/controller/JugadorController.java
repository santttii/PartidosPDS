package controller;

import java.util.List;

import model.partido.Jugador;
import model.partido.Partido;
import model.partido.Deporte;
import model.notificacion.IEstrategiaNotificacion;
import data.JugadorDAO;
import data.PartidoDAO;

public class JugadorController {
    private static JugadorController instancia;
    private final JugadorDAO gestorJugadores;
    private final PartidoDAO gestorPartidos;

    // Constructor privado para singleton
    private JugadorController() {
        this.gestorJugadores = JugadorDAO.getInstancia();
        this.gestorPartidos = PartidoDAO.getInstancia();  // asumiendo PartidoDAO también es singleton y tiene getInstancia()
    }

    public static JugadorController getInstancia() {
        if (instancia == null) {
            instancia = new JugadorController();
        }
        return instancia;
    }

    public JugadorController(PartidoDAO gestorPartidos, JugadorDAO gestorJugadores) {
        this.gestorJugadores = gestorJugadores;
        this.gestorPartidos = gestorPartidos;
    }

    public JugadorController(JugadorDAO gestorJugadores, PartidoDAO gestorPartidos) {
        this.gestorJugadores = gestorJugadores;
        this.gestorPartidos = gestorPartidos;
    }

    /**
     * Registra un nuevo jugador en el sistema
     */
    public Jugador registrarJugador(String username, String email, String password,
                                    Jugador.NivelJugador nivel, Deporte deporteFavorito,
                                    IEstrategiaNotificacion estrategiaNotificacion, String ubicacion,
                                    String token) {
        try {
            validarDatosRegistro(username, password, email, nivel, deporteFavorito, estrategiaNotificacion, ubicacion);

            if (gestorJugadores.existeUsername(username)) {
                throw new IllegalArgumentException("El username ya está en uso");
            }

            if (gestorJugadores.existeEmail(email)) {
                throw new IllegalArgumentException("El email ya está registrado");
            }

            Jugador nuevoJugador = new Jugador(
                    username,
                    password,
                    email,
                    nivel,
                    deporteFavorito,
                    estrategiaNotificacion,
                    ubicacion,
                    0,
                    token
            );


            if (gestorJugadores.agregarJugador(nuevoJugador)) {
                System.out.println("Jugador registrado exitosamente: " + username + " dlslaskdkdskas");
                return nuevoJugador;
            } else {
                System.err.println("Error al agregar el jugador al gestor");
                return null;
            }

        } catch (Exception e) {
            System.err.println("Error al registrar jugador: " + e.getMessage());
            return null;
        }
    }

    /**
     * Autentica un jugador con username y password
     */
    public Jugador autenticarJugador(String username, String password) {
        if (username == null || password == null) {
            System.err.println("Username y password no pueden ser nulos");
            return null;
        }

        Jugador jugador = gestorJugadores.buscarPorUsername(username);
        if (jugador != null && jugador.getPassword().equals(password)) {
            System.out.println("Jugador autenticado: " + username);
            return jugador;
        }

        System.err.println("Credenciales incorrectas");
        return null;
    }

    /**
     * Permite a un jugador buscar partidos disponibles
     */
    public List<Partido> buscarPartidosDisponibles(Jugador jugador) {
        if (jugador == null) {
            System.err.println("El jugador no puede ser nulo");
            return List.of();
        }

        List<Partido> partidosDisponibles = gestorPartidos.buscarDisponiblesParaJugador(jugador);
        System.out.println("Se encontraron " + partidosDisponibles.size() +
                " partidos disponibles para " + jugador.getUsername());
        return partidosDisponibles;
    }

    /**
     * Validaciones para registro
     */
    private void validarDatosRegistro(String username, String password, String email,
                                      Jugador.NivelJugador nivel, Deporte deporteFavorito,
                                      IEstrategiaNotificacion estrategiaNotificacion, String ubicacion) {
        if (username == null || username.trim().isEmpty()) throw new IllegalArgumentException("Username no válido");
        if (password == null || password.length() < 4) throw new IllegalArgumentException("Password muy corta");
        if (email == null || !email.contains("@")) throw new IllegalArgumentException("Email inválido");
        if (nivel == null) throw new IllegalArgumentException("Nivel no especificado");
        if (deporteFavorito == null) throw new IllegalArgumentException("Deporte favorito requerido");
        if (estrategiaNotificacion == null) throw new IllegalArgumentException("Estrategia de notificación requerida");
        if (ubicacion == null || ubicacion.trim().isEmpty()) throw new IllegalArgumentException("Ubicación requerida");
    }
}
