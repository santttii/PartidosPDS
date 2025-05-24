package model.estado;

import model.partido.Partido;

public class Cancelado implements IEstadoPartido {
    @Override
    public void iniciar(Partido partido) {
        System.out.println("🚫 No se puede iniciar un partido cancelado.");
    }

    @Override
    public void finalizar(Partido partido) {
        System.out.println("❌ No se puede finalizar un partido cancelado.");
    }

    @Override
    public void cancelar(Partido partido) {
        System.out.println("⚠️ El partido ya está cancelado.");
    }
}
