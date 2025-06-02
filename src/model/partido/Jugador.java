package model.partido;

import model.notificacion.IObserver;
import model.notificacion.Notificador;
import model.notificacion.Notification;

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

    public Jugador(String username, String password, String email, NivelJugador nivel, Deporte deporteFavorito, Notificador notificador, String ubicacion, int cantidadPartidosJugados) {
        setIdJugador(idJugador);
        setUsername(username);
        setEmail(email);
        setPassword(password);
        setNivel(nivel);
        setDeporteFavorito(deporteFavorito);
        setNotificador(notificador);
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

    public void setNotificador(Notificador notificador) {
        this.notificador = notificador;
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

    }

    // METODOS PROPIOS


    public void CrearPartido(Partido partido) {
        // codear crear partido
    }

    public void BuscaPartido() {
        // codear buscar partido
    }
}
