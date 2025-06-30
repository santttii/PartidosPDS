package model.estado;

import model.partido.Partido;

public interface IEstadoPartido {
    void iniciar(Partido partido);
    void finalizar(Partido partido);
    void cancelar(Partido partido);
    void confirmar(Partido partido);
    void jugar(Partido partido);
}
