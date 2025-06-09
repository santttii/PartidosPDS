package main.java.model.estado;

import main.java.model.partido.Partido;

public interface IEstadoPartido {
    void iniciar(Partido partido);
    void finalizar(Partido partido);
    void cancelar(Partido partido);
}
