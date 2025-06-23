package model.partido;

import java.io.Serializable;

public class Voley extends Deporte implements Serializable {
    private static final long serialVersionUID = 1L;
    private static Voley instancia;
    
    private Voley() {
        super("Vóley", 6, "Partido de vóley de cancha");
    }
    
    public static Voley getInstancia() {
        if (instancia == null) {
            instancia = new Voley();
        }
        return instancia;
    }
    
    // Necesario para la serialización
    protected Object readResolve() {
        return getInstancia();
    }
}