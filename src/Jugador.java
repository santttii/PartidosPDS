public class Jugador implements IObserver {

    public enum NivelJugador {
        PRINCIPIANTE,
        INTERMEDIO,
        AVANZADO
    }

    private String nombre;
    private String apellido;
    private String username;
    private String password;
    private String email;
    private NivelJugador nivel;
    private Deporte deporteFavorito;

    public Jugador(String nombre, String apellido, String username, String password, String email, NivelJugador nivel, Deporte deporteFavorito) {
        setNombre(nombre);
        setApellido(apellido);
        setUsername(username);
        setEmail(email);
        setPassword(password);
        setNivel(nivel);
        setDeporteFavorito(deporteFavorito);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

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

    public Deporte getDeporteFavorito() {
        return deporteFavorito;
    }

    public void setDeporteFavorito(Deporte deporteFavorito) {
        this.deporteFavorito = deporteFavorito;
    }

    @Override
    public void serNotificado() {
        // Codear en base al observer y notificaciones
    }

    // METODOS

    public void CrearPartido(Partido partido) {
        // codear crear partido
    }

    public void IniciarSesion(String email, String password) {
        // codear login
    }

    public void Registrarse(Jugador jugador) {
        // codear registro
    }

    public void BuscaPartido() {
        // codear buscar partido
    }
}
