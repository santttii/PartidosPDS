package ui;

import controller.JugadorController;
import data.JugadorDAO;
import data.PartidoDAO;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.notificacion.*;
import model.partido.*;

public class RegisterScreen {
    private final Stage stage;
    private final JugadorController jugadorController;
    private final JugadorDAO jugadorDAO;

    public RegisterScreen(Stage stage) {
        this.stage = stage;
        this.jugadorDAO = JugadorDAO.getInstancia();  // instancia singleton de JugadorDAO
        this.jugadorController = new JugadorController(jugadorDAO, new PartidoDAO());
    }

    public void show() {
        TextField usernameField = new TextField();
        usernameField.setPromptText("Usuario");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        TextField tokenField = new TextField();
        tokenField.setPromptText("Token");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Contraseña");

        TextField ubicacionField = new TextField();
        ubicacionField.setPromptText("Ubicación");

        ComboBox<Jugador.NivelJugador> nivelBox = new ComboBox<>();
        nivelBox.getItems().addAll(Jugador.NivelJugador.values());
        nivelBox.setPromptText("Nivel");

        ComboBox<Deporte> deporteBox = new ComboBox<>();
        deporteBox.getItems().addAll(
                new Futbol("Fútbol", 11, "Partido de fútbol clásico"),
                new Basquet("Básquet", 5, "Partido de básquet profesional"),
                new Voley("Vóley", 6, "Partido de vóley de cancha")
        );
        deporteBox.setPromptText("Deporte");

        // ComboBox para tipo de notificación
        ComboBox<String> notificacionBox = new ComboBox<>();
        notificacionBox.getItems().addAll("Email", "Push");
        notificacionBox.setPromptText("Tipo de notificación");

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        Button registerBtn = new Button("Registrar");
        registerBtn.setOnAction(e -> {
            try {
                // Determinar estrategia de notificación
                IEstrategiaNotificacion estrategia;

                String tipo = notificacionBox.getValue();
                if ("Email".equals(tipo)) {
                    estrategia = new CorreoElectronico(new AdapterJavaEmailNotification());
                } else if ("Push".equals(tipo)) {
                    estrategia = new PushNotification(new AdapterFirebasePushNotification());
                } else {
                    errorLabel.setText("Debes seleccionar un tipo de notificación.");
                    return;
                }

                Jugador nuevo = jugadorController.registrarJugador(
                        usernameField.getText(),
                        emailField.getText(),
                        passwordField.getText(),
                        nivelBox.getValue(),
                        deporteBox.getValue(),
                        estrategia,
                        ubicacionField.getText(),
                        tokenField.getText()
                );
                System.out.println("Jugador registrado exitosamente: " + nuevo.getUsername());
                System.out.println("Jugador registrado exitosamente: " + nuevo.getPassword());

                if (nuevo != null) {
                    new Alert(Alert.AlertType.INFORMATION, "Registrado exitosamente").showAndWait();
                    new LoginScreen(stage).show();
                } else {
                    errorLabel.setText("Error al registrar.");
                }
            } catch (Exception ex) {
                errorLabel.setText("Error: " + ex.getMessage());
            }
        });

        VBox layout = new VBox(10,
                usernameField,
                emailField,
                tokenField,
                passwordField,
                ubicacionField,
                nivelBox,
                deporteBox,
                notificacionBox,
                registerBtn,
                errorLabel
        );
        layout.setAlignment(Pos.CENTER);
        stage.setScene(new Scene(layout, 400, 500));
        stage.setTitle("Registro");
        stage.show();
    }
}
