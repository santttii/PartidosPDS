public class Notificador {
    private IEstrategiaNotificacion estrategia;

    public Notificador(IEstrategiaNotificacion estrategia) {
        setEstrategia(estrategia);
    }

    public IEstrategiaNotificacion getEstrategia() {
        return estrategia;
    }

    public void setEstrategia(IEstrategiaNotificacion estrategia) {
        this.estrategia = estrategia;
    }

    public void CambiarEstrategia(IEstrategiaNotificacion nuevaEstrategia) {
        setEstrategia(nuevaEstrategia);
    }
}
