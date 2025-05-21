import java.util.ArrayList;
import java.util.Date;

public class Partido implements IObservable {

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

    // Método para validar si un jugador es apto según la estrategia
    public boolean esApto(Jugador jugador) {
        return emparejamiento.esApto(jugador, this);
    }

    // Agregar jugador (con validación por estrategia)
    public void agregarJugador(Jugador jugador) {
        if (!(estado instanceof Pendiente || estado instanceof NecesitamosJugadores)) {
            System.out.println("⚠️ No se pueden agregar jugadores en este estado (" + estado.getClass().getSimpleName() + ")");
            return;
        }

        if (!esApto(jugador)) {
            System.out.println("❌ El jugador no cumple los requisitos para este partido.");
            return;
        }

        if (jugadores.contains(jugador)) {
            System.out.println("⚠️ El jugador ya está en el partido.");
            return;
        }

        jugadores.add(jugador);
        jugadoresObserver.add(jugador);

        System.out.println("✅ Jugador agregado: " + jugador.getUsername());

        verificarYActualizarEstado();
    }

    private void verificarYActualizarEstado() {
        int cantidad = jugadores.size();

        if (cantidad == 0) {
            cambiarEstado(new Pendiente());
        } else if (cantidad < cupoMaximo) {
            cambiarEstado(new NecesitamosJugadores());
        } else if (cantidad == cupoMaximo) {
            cambiarEstado(new Confirmado());
        } else {
            System.out.println("⚠️ ¡Cupo excedido!");
        }
    }


    public void eliminarJugador(Jugador jugador) {
        if (jugadores.remove(jugador)) {
            jugadoresObserver.remove(jugador);
            System.out.println("🗑️ Jugador eliminado: " + jugador.getUsername());
            verificarYActualizarEstado();
        } else {
            System.out.println("❌ El jugador no estaba en el partido.");
        }
    }

    public String getNombreEstado() {
        return estado.getClass().getSimpleName();
    }



    // Métodos del patrón Observer

    @Override
    public void agregar(IObserver jugador) {
        jugadoresObserver.add(jugador);
    }

    @Override
    public void eliminar(IObserver jugador) {
        jugadoresObserver.remove(jugador);
    }

    @Override
    public void notificar() {
        for (IObserver jugador : jugadoresObserver) {
            jugador.serNotificado();
        }
    }

    // Cambios de estado y estrategia

    public void cambiarEstado(IEstadoPartido nuevoEstado) {
        System.out.println("🔄 Transición de estado: " + this.getNombreEstado() + " → " + nuevoEstado.getClass().getSimpleName());
        this.estado = nuevoEstado;
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

}
