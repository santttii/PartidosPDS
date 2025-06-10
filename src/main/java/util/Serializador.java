package util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class Serializador {
    private static final String DIRECTORIO_DATOS = "datos/";

    static {
        try {
            Files.createDirectories(Path.of(DIRECTORIO_DATOS));
        } catch (IOException e) {
            System.err.println("Failed to create directory: " + e.getMessage());
        }
    }

    public static void guardarDatos(Object objeto, String nombreArchivo) {
        String rutaArchivo = DIRECTORIO_DATOS + nombreArchivo + ".ser";
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(rutaArchivo))) {
            out.writeObject(objeto);
            System.out.println("Datos guardados exitosamente en " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("Error al guardar los datos: " + e.getMessage());
        }
    }

    public static <T> T cargarDatos(String nombreArchivo, Class<T> clase) {
        String rutaArchivo = DIRECTORIO_DATOS + nombreArchivo + ".ser";
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            System.out.println("No se encontró archivo de datos para " + nombreArchivo);
            return null;
        }

        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(archivo))) {
            Object objeto = in.readObject();
            if (clase.isInstance(objeto)) {
                System.out.println("Datos cargados exitosamente de " + nombreArchivo);
                return clase.cast(objeto);
            } else {
                throw new ClassCastException("El objeto leído no es del tipo esperado");
            }
        } catch (Exception e) {
            System.err.println("Error al cargar los datos: " + e.getMessage());
            return null;
        }
    }
}