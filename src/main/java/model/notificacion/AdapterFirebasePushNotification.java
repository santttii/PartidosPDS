package model.notificacion;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import util.FirebaseInitializer;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;

import java.io.Serializable;

public class AdapterFirebasePushNotification implements IAdapterPushNotification, Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public void EnviarPushNotification(Notification notification) {
        try {
            Message message = Message.builder()
                    .setToken(notification.getTokenFCM())
                    .setNotification(com.google.firebase.messaging.Notification.builder()
                            .setTitle("Cambio en el partido de " + notification.getDeporte())
                            .setBody(notification.TextoNotificacion())
                            .build()
                    )
                    .build();

            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Notificación enviada: " + response);

        } catch (Exception e) {
            System.err.println("Error al enviar push notification: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
