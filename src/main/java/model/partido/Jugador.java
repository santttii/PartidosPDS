package main.java.model.partido;

import main.java.model.notificacion.IEstrategiaNotificacion;
import main.java.model.notificacion.IObserver;
import main.java.model.notificacion.Notificador;
import main.java.model.notificacion.Notification;

public class Jugador implements IObserver {

    public enum NivelJugador {
        PRINCIPIANTE,
        INTERMEDIO,
        AVANZADO
    }

    private int idJugador;
    private String username;
    private String password;
    private String email;
    private NivelJugador nivel;
    private Deporte deporteFavorito;
    private Notificador notificador;
    private String ubicacion;
    private int cantidadPartidosJugados;

    public Jugador(String username, String password, String email, NivelJugador nivel, Deporte deporteFavorito, IEstrategiaNotificacion estrategiaElegida, String ubicacion, int cantidadPartidosJugados) {
        setIdJugador(idJugador);
        setUsername(username);
        setEmail(email);
        setPassword(password);
        setNivel(nivel);
        setDeporteFavorito(deporteFavorito);
        setNotificador(estrategiaElegida);
        setUbicacion(ubicacion);
        setCantidadPartidosJugados(cantidadPartidosJugados);
    }

    public int getIdJugador() { return idJugador; }

    public void setIdJugador(int idJugador) { this.idJugador = idJugador; }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public NivelJugador getNivel() {
        return nivel;
    }

    public void setNivel(NivelJugador nivel) {
        this.nivel = nivel;
    }

    public Deporte getDeporteFavorito() { return deporteFavorito; }

    public void setDeporteFavorito(Deporte deporteFavorito) {
        this.deporteFavorito = deporteFavorito;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getCantidadPartidosJugados() {
        return cantidadPartidosJugados;
    }

    public Notificador getNotificador() {
        return notificador;
    }

    public void setNotificador(IEstrategiaNotificacion estrategiaElegida) {
        this.notificador = new Notificador(estrategiaElegida);
    }

    public void setCantidadPartidosJugados(int cantidadPartidosJugados) {
        this.cantidadPartidosJugados = cantidadPartidosJugados;
    }

    public void incrementarCantidadPartidosJugados() {
        this.cantidadPartidosJugados++;
    }

    // METODOS OBSERVER

    @Override
    public void serNotificado(Partido partido, Notification noti) {
        notificador.EnviarNotificacion(noti);
    }

    // METODOS PROPIOS


    public void CrearPartido(Partido partido) {
        // codear crear partido
    }

    public void BuscaPartido() {
        // codear buscar partido
    }
}
