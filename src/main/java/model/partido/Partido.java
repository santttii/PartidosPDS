package model.partido;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import model.emparejamiento.IEstrategiaEmparejamiento;
import model.estado.Confirmado;
import model.estado.Finalizado;
import model.estado.IEstadoPartido;
import model.estado.NecesitamosJugadores;
import model.notificacion.IObservable;
import model.notificacion.IObserver;
import model.notificacion.Notification;
import model.partido.Comentario;

public class Partido implements IObservable, Serializable {

    public enum NivelJugador {
        PRINCIPIANTE,
        INTERMEDIO,
        AVANZADO
    }

    private int cupoMaximo;
    private Deporte deporte;
    private String ubicacion;
    private Date horario;
    private double duracion;
    private IEstadoPartido estado;
    private IEstrategiaEmparejamiento emparejamiento;
    private ArrayList<Jugador> jugadores;
    private ArrayList<IObserver> jugadoresObserver;
    private ArrayList<Comentario> comentarios;

    // ✅ NUEVO: Creador del partido
    private Jugador creador;

    public Partido(int cupoMaximo, Deporte deporte, String ubicacion, Date horario, double duracion,
                   IEstadoPartido estado, IEstrategiaEmparejamiento emparejamiento) {
        this.cupoMaximo = cupoMaximo;
        this.deporte = deporte;
        this.ubicacion = ubicacion;
        this.horario = horario;
        this.duracion = duracion;
        this.estado = estado;
        this.emparejamiento = emparejamiento;
        this.jugadores = new ArrayList<>();
        this.jugadoresObserver = new ArrayList<>();
        this.comentarios = new ArrayList<>();
    }

    // Getters y setters

    public int getCupoMaximo() {
        return cupoMaximo;
    }

    public Deporte getDeporte() {
        return deporte;
    }

    public void setDeporte(Deporte deporte) {
        this.deporte = deporte;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Date getHorario() {
        return horario;
    }

    public void setHorario(Date horario) {
        this.horario = horario;
    }

    public double getDuracion() {
        return duracion;
    }

    public void setDuracion(double duracion) {
        this.duracion = duracion;
    }

    public IEstadoPartido getEstado() {
        return estado;
    }

    public void setEstado(IEstadoPartido estado) {
        this.estado = estado;
    }

    public IEstrategiaEmparejamiento getEmparejamiento() {
        return emparejamiento;
    }

    public void setEmparejamiento(IEstrategiaEmparejamiento emparejamiento) {
        this.emparejamiento = emparejamiento;
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public ArrayList<IObserver> getJugadoresObserver() {
        return jugadoresObserver;
    }

    // ✅ Getter y Setter del Creador
    public Jugador getCreador() {
        return creador;
    }

    public void setCreador(Jugador creador) {
        this.creador = creador;
    }

    // Método para validar si un jugador es apto según la estrategia
    public boolean esApto(Jugador jugador) {
        return emparejamiento.esApto(jugador, this);
    }

    public ArrayList<Comentario> getComentarios() {
        return comentarios;
    }

    public void agregarJugador(Jugador jugador) {
        if (!(estado instanceof NecesitamosJugadores)) {
            System.out.println("No se pueden agregar jugadores en este estado (" + estado.getClass().getSimpleName() + ")");
            return;
        }

        if (!esApto(jugador)) {
            System.out.println("El jugador no cumple los requisitos para este partido.");
            return;
        }

        // Verificar si el jugador ya está en el partido usando el ID
        boolean jugadorYaExiste = jugadores.stream()
                .anyMatch(j -> j.getIdJugador() == jugador.getIdJugador());

        if (jugadorYaExiste) {
            System.out.println("El jugador ya está en el partido.");
            return;
        }

        jugadores.add(jugador);
        jugadoresObserver.add(jugador);

        System.out.println("Jugador agregado: " + jugador.getUsername());

        verificarYActualizarEstado();
    }


    public void agregarComentario(Comentario comentario) {
        if (this.estado instanceof Finalizado) {
            if (this.comentarios == null) {
                this.comentarios = new ArrayList<>();
            }
            this.comentarios.add(comentario);
        }
    }

    private void verificarYActualizarEstado() {
        int cantidad = jugadores.size();
        if (cantidad == cupoMaximo) {
            confirmarPartido();
        }
    }

    public void eliminarJugador(Jugador jugador) {
        if (jugadores.remove(jugador)) {
            jugadoresObserver.remove(jugador);
            System.out.println("Jugador eliminado: " + jugador.getUsername());
            verificarYActualizarEstado();
        } else {
            System.out.println("El jugador no estaba en el partido.");
        }
    }

    public String getNombreEstado() {
        return estado.getClass().getSimpleName();
    }

    // Métodos del patrón Observer

    @Override
    public void agregarJugador(IObserver jugador) {
        jugadoresObserver.add(jugador);
    }

    @Override
    public void eliminarJugador(IObserver jugador) {
        jugadoresObserver.remove(jugador);
    }

    @Override
    public void notificar() {
        for (IObserver jugador : jugadoresObserver) {
            Notification noti = new Notification(estado, deporte, jugador.getEmail(), jugador.getTokenFCM());
            jugador.serNotificado(this, noti);
        }
    }

    // Cambios de estado y estrategia

    public void cambiarEstado(IEstadoPartido nuevoEstado) {
        System.out.println("Transición de estado: " + this.getNombreEstado() + " → " + nuevoEstado.getClass().getSimpleName());
        this.estado = nuevoEstado;
        notificar();
    }

    public void cambiarEstrategia(IEstrategiaEmparejamiento nuevaEstrategia) {
        this.emparejamiento = nuevaEstrategia;
    }

    // Métodos delegados al estado (State Pattern)

    public void iniciarPartido() {
        estado.iniciar(this);
    }

    public void finalizarPartido() {
        estado.finalizar(this);
    }

    public void cancelarPartido() {
        estado.cancelar(this);
    }

    public void confirmarPartido() {estado.confirmar(this);}

    public void jugarPartido() {estado.jugar(this);}
}
