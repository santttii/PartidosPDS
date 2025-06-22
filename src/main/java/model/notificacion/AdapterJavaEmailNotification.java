package model.notificacion;


import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.io.Serializable;
import java.util.Properties;

public class AdapterJavaEmailNotification implements IAdapterEmailNotification, Serializable {
    private static final long serialVersionUID = 1L;

    // Correo y contraseña del remitente ( mover esto a un archivo config si prefieres)
    private final String remitente = "martinkpce@gmail.com";  // Reemplaza con tu correo real
    private final String password = "wegj gfxt iiyo easy";        // Reemplaza con tu contraseña real

    @Override
    public void EnviarEmail(Notification notification) {

        String destinatario = notification.getCorreoDestinatario();
        String asunto = "Notificación del partido de " + notification.getDeporte();
        String mensaje = notification.TextoNotificacion();

        // Configuramos propiedades SMTP (ejemplo con Gmail)
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Sesión con autenticación
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, password);
            }
        });

        try {
            // Construimos el correo
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remitente));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(asunto);
            message.setText(mensaje);

            // Enviamos
            Transport.send(message);
            System.out.println("Correo enviado a " + destinatario);

        } catch (MessagingException e) {
            System.out.println("Error al enviar correo: " + e.getMessage());
        }
    }
}