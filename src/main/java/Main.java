
import model.estado.Finalizado;
import model.notificacion.AdapterJavaEmailNotification;
import model.notificacion.IAdapterEmailNotification;
import model.notificacion.Notification;
import model.partido.Futbol;
import util.FirebaseInitializer;
import model.notificacion.Notificador;
import model.notificacion.PushNotification;
import model.notificacion.AdapterFirebasePushNotification;


public class Main {
    public static void main(String[] args) {
        try {
            //inicializarr firebase
            FirebaseInitializer.initialize();
            Futbol futbol = new Futbol("Fútbol", 11, "Partido de fútbol");

            String tokenFCM = "dCD4rJJnh7qLlAEX7cLByA:APA91bFdD3BysWkfruY5EOjA8iPGNTAWaYgkrhQjRAE1dawwnRpqbtTD1t9sS7hr4dHT9h5uzUgk540Pmb0GrW1C1BJ4iIFP_-blPtUQa5_TUlWuD4-TUBU";

            Notification notificacion = new Notification(
                    new Finalizado(),
                    futbol,
                    "patriciogabrielvecino@gmail.com",
                    tokenFCM
            );

            IAdapterEmailNotification emailAdapter = new AdapterJavaEmailNotification();

            System.out.println("Intentando enviar correo de prueba...");
            emailAdapter.EnviarEmail(notificacion);

            Notificador notificador = new Notificador(
              new PushNotification(new AdapterFirebasePushNotification()
              )
            );
            System.out.println("Intentando noti push...");
            notificador.EnviarNotificacion(notificacion);

        } catch (Exception e) {
            System.out.println("Error durante la prueba: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
