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

    public Jugador(String username, String password, String email, NivelJugador nivel, Deporte deporteFavorito) {
        setIdJugador(idJugador);
        setUsername(username);
        setEmail(email);
        setPassword(password);
        setNivel(nivel);
        setDeporteFavorito(deporteFavorito);
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

    // METODOS OBSERVER

    @Override
    public void serNotificado() {
        // Codear en base al observer y notificaciones
    }

    // METODOS PROPIOS


    public void CrearPartido(Partido partido) {
        // codear crear partido
    }

    public void BuscaPartido() {
        // codear buscar partido
    }
}
