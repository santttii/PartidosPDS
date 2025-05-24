package controller;
import java.util.ArrayList;
import model.*;
import model.partido.Jugador;

public class SistemaGestor {
    // Instancia única del sistema
    private static SistemaGestor instancia;

    // Lista de jugadores del sistema
    private ArrayList<Jugador> jugadores;

    // Constructor privado para evitar instanciación externa
    private SistemaGestor() {
        jugadores = new ArrayList<>();
    }

    // Método estático para obtener la única instancia
    public static SistemaGestor getInstancia() {
        if (instancia == null) {
            instancia = new SistemaGestor();
        }
        return instancia;
    }

    // Métodos para manejar jugadores

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public void registrarJugador(Jugador jugador) {
        jugadores.add(jugador);
    }

    public void eliminarJugador(Jugador jugador) {
        jugadores.remove(jugador);
    }

    public Jugador getJugadorPorEmail(String email) {
        for (Jugador j : jugadores) {
            if (j.getEmail().equalsIgnoreCase(email)) {
                return j;
            }
        }
        return null;
    }

    public Jugador login(String email, String password) {
        for (Jugador j : jugadores) {
            if (j.getEmail().equals(email) && j.getPassword().equals(password)) {
                return j;
            }
        }
        return null;
    }
}
