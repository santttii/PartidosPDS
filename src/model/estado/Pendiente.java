package model.estado;

import model.partido.Partido;

public class Pendiente implements IEstadoPartido {
    @Override
    public void iniciar(Partido partido) {
        System.out.println("❌ No se puede iniciar el partido. Aún está pendiente.");
    }

    @Override
    public void finalizar(Partido partido) {
        System.out.println("❌ No se puede finalizar un partido que no ha comenzado.");
    }

    @Override
    public void cancelar(Partido partido) {
        System.out.println("⚠️ Partido cancelado desde estado Pendiente.");
        partido.cambiarEstado(new Cancelado());
    }
}
