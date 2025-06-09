package main.java.model.notificacion;
public interface IObservable {
    void agregarJugador(IObserver jugador);
    void eliminarJugador(IObserver jugador);
    void notificar();
}