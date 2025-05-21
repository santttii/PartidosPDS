public abstract class Deporte {
    private String nombre;
    private int cantidadJugadores;
    private String descripcion;

    public Deporte(String nombre, int cantidadJugadores, String descripcion) {
        setNombre(nombre);
        setCantidadJugadores(cantidadJugadores);
        setDescripcion(descripcion);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidadJugadores() {
        return cantidadJugadores;
    }

    public void setCantidadJugadores(int cantidadJugadores) {
        this.cantidadJugadores = cantidadJugadores;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
