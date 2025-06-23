package model.partido;

import java.io.Serializable;

public class Futbol extends Deporte implements Serializable {
    private static final long serialVersionUID = 1L;
    private static Futbol instancia;
    
    private Futbol() {
        super("Fútbol", 11, "Partido de fútbol clásico");
    }
    
    public static Futbol getInstancia() {
        if (instancia == null) {
            instancia = new Futbol();
        }
        return instancia;
    }
    
    // Necesario para la serialización
    protected Object readResolve() {
        return getInstancia();
    }
}