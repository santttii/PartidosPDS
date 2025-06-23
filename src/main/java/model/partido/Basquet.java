package model.partido;

import java.io.Serializable;

public class Basquet extends Deporte implements Serializable {
    private static final long serialVersionUID = 1L;
    private static Basquet instancia;
    
    private Basquet() {
        super("Básquet", 5, "Partido de básquet profesional");
    }
    
    public static Basquet getInstancia() {
        if (instancia == null) {
            instancia = new Basquet();
        }
        return instancia;
    }
    
    // Necesario para la serialización
    protected Object readResolve() {
        return getInstancia();
    }
}