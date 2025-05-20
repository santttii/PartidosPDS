import java.util.ArrayList;
import java.util.Date;

public class Partido {

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
    private NivelJugador nivelMinJugador;
    private NivelJugador nivelMaxJugador;
    private IEstadoPartido estado;
    private IEstrategiaEmparejamiento emparejamiento;
    private ArrayList<IObserver> jugadoresObserver;

    // Constructor
    public Partido(Deporte deporte, String ubicacion, Date horario, double duracion,
                   NivelJugador nivelMinJugador, NivelJugador nivelMaxJugador,
                   IEstadoPartido estado, IEstrategiaEmparejamiento emparejamiento) {
        this.deporte = deporte;
        this.ubicacion = ubicacion;
        this.horario = horario;
        this.duracion = duracion;
        this.nivelMinJugador = nivelMinJugador;
        this.nivelMaxJugador = nivelMaxJugador;
        this.estado = estado;
        this.emparejamiento = emparejamiento;
        this.jugadoresObserver = new ArrayList<>();
    }

    // Métodos
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

    public void notificar() {
        for (IObserver jugador : jugadoresObserver) {
            jugador.actualizar(); // Se asume que IObserver tiene un método actualizar()
        }
    }
}
