public class Notification {

    public enum EstadoPartido {
        NecesitamosJugadores,
        Confirmado,
        Finalizado,
        EnJuego,
        PartidoArmado
    }

    private String username;
    private EstadoPartido estado;
    private Deporte deporte;

    public Notification(String username, EstadoPartido estado, Deporte deporte) {
        setDeporte(deporte);
        setUsername(username);
        setEstado(estado);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Deporte getDeporte() {
        return deporte;
    }

    public void setDeporte(Deporte deporte) {
        this.deporte = deporte;
    }

    public EstadoPartido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPartido estado) {
        this.estado = estado;
    }
}