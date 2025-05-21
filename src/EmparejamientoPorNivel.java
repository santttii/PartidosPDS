public class EmparejamientoPorNivel implements IEstrategiaEmparejamiento {

    public enum NivelJugador {
        PRINCIPIANTE,
        INTERMEDIO,
        AVANZADO
    }

    private NivelJugador nivelJugadores;

    public EmparejamientoPorNivel(NivelJugador nivelJugadores) {
        setNivelJugadores(nivelJugadores);
    }

    public NivelJugador getNivelJugadores() {
        return nivelJugadores;
    }

    public void setNivelJugadores(NivelJugador nivelJugadores) {
        this.nivelJugadores = nivelJugadores;
    }

    @Override
    public boolean esApto(Jugador jugador, Partido partido) {
        return false;
    }
}
