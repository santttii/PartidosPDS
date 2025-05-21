import java.util.ArrayList;
import java.util.Date;

public class Partido implements IObservable {

    // Enum definido DENTRO de la clase Partido
    public enum NivelJugador {
        PRINCIPIANTE,
        INTERMEDIO,
        AVANZADO
    }

    // Atributos
    private Deporte deporte;
    private String ubicacion;
    private Date horario;
    private double duracion;
    private IEstadoPartido estado;
    private IEstrategiaEmparejamiento emparejamiento;
    private ArrayList<IObserver> jugadoresObserver;

    // Constructor
    public Partido(Deporte deporte, String ubicacion, Date horario, double duracion,
                   IEstadoPartido estado, IEstrategiaEmparejamiento emparejamiento) {
        setDeporte(deporte);
        setUbicacion(ubicacion);
        setHorario(horario);
        setDuracion(duracion);
        setEstado(estado);
        setEmparejamiento(emparejamiento);
        jugadoresObserver = new ArrayList<>();
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

    public ArrayList<IObserver> getJugadoresObserver() {
        return jugadoresObserver;
    }

    // Métodos como observer

    public void agregarJugador(IObserver jugador) {
        jugadoresObserver.add(jugador);
    }

    public void eliminarJugador(IObserver jugador) {
        jugadoresObserver.remove(jugador);
    }

    public void notificar() {
        for (IObserver jugador : jugadoresObserver) {
            jugador.serNotificado(); // Se asume que IObserver tiene un método actualizar()
        }
    }

    // Métodos propios

    public void cambiarEstado(IEstadoPartido nuevo) {
        this.estado = nuevo;
    }

    public void cambiarEstrategia(IEstrategiaEmparejamiento nuevo) {
        this.emparejamiento = nuevo;
    }

    public void agregar(IObserver jugador) {
        jugadoresObserver.add(jugador);
    }

    public void eliminar(IObserver jugador) {
        jugadoresObserver.remove(jugador);
    }
}
