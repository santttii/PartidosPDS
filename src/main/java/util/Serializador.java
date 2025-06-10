package main.java.util;

import main.java.controller.SistemaGestor;
import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;

public class Serializador {
    private static final String DIRECTORIO_DATOS = "datos/";
    private static final String ARCHIVO_SISTEMA = DIRECTORIO_DATOS + "sistema.ser";

    static {
        try {
            Files.createDirectories(Path.of(DIRECTORIO_DATOS));
        } catch (IOException e) {
            System.err.println("Failed to create directory: " + e.getMessage());
        }
    }

    public static void guardarDatos() {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(ARCHIVO_SISTEMA))) {
            out.writeObject(SistemaGestor.getInstancia());
            System.out.println("Sistema guardado exitosamente");
        } catch (IOException e) {
            System.err.println("Error al guardar el sistema: " + e.getMessage());
        }
    }

    public static void cargarDatos() {
        File archivo = new File(ARCHIVO_SISTEMA);
        if (!archivo.exists()) {
            System.out.println("No se encontró archivo de datos previo.");
            return;
        }

        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(archivo))) {
            SistemaGestor sistemaLeido = (SistemaGestor) in.readObject();
            // Actualizar la instancia singleton con los datos cargados
            Field instance = SistemaGestor.class.getDeclaredField("instancia");
            instance.setAccessible(true);
            instance.set(null, sistemaLeido);
            System.out.println("Sistema cargado exitosamente");
        } catch (Exception e) {
            System.err.println("Error al cargar el sistema: " + e.getMessage());
        }
    }
}