package main;

import model.notificacion.*;
import model.estado.Finalizado;
import model.partido.Futbol;


public class Main {
    public static void main(String[] args) {
        try {
            Futbol futbol = new Futbol("Fútbol", 11, "Partido de fútbol");
            Notification notificacion = new Notification(
                new Finalizado(),
                futbol,
                "patriciogabrielvecino@gmail.com"
            );

            IAdapterEmailNotification emailAdapter = new AdapterJavaEmailNotification();

            System.out.println("Intentando enviar correo de prueba...");
            emailAdapter.EnviarEmail(notificacion);
            
        } catch (Exception e) {
            System.out.println("Error durante la prueba: " + e.getMessage());
            e.printStackTrace();
        }
    }
}