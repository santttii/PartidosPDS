import java.util.ArrayList;

public class SistemaGestor {
    private ArrayList<Jugador> jugadores;

    public SistemaGestor() {
        jugadores = new ArrayList<>();
    }

    public ArrayList<Jugador> getJugadores() { return jugadores; }

    public void AltaJugador(Jugador jugador) {
        jugadores.add(jugador);
    }

    public void BajaJugador(Jugador jugador) {
        jugadores.remove(jugador);
    }

    public  void ValidarCredenciales(Jugador jugador) {
        // cuando quiere logearse, valida o no que el mismo exista
    }
}
