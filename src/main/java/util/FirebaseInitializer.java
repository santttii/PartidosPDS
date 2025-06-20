package util;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;
import java.io.IOException;
public class FirebaseInitializer {

    public static void initialize() {
        try {
            if (FirebaseApp.getApps().isEmpty()) {
                FileInputStream serviceAccount = new FileInputStream("notificacionespds-firebase-adminsdk-fbsvc-4ee9897d5a.json");

                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                FirebaseApp.initializeApp(options);
                System.out.println("Firebase inicializado correctamente.");
            }
        } catch (IOException e) {
            System.err.println("Error al inicializar Firebase:");
            e.printStackTrace();
        }
    }
}