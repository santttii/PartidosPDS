public interface IEstadoPartido {
    void AgregarJugador(Jugador jugador);
    boolean Confirmar();
    boolean Iniciar();
    boolean Finalizar();
    boolean Cancelar();
}