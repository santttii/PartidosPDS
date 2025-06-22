
package ui;

import controller.JugadorController;
import data.JugadorDAO;
import data.PartidoDAO;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.partido.Jugador;

public class LoginScreen {
    private final Stage stage;
    private final JugadorController jugadorController;

    public LoginScreen(Stage stage) {
        this.stage = stage;
        this.jugadorController = JugadorController.getInstancia();
    }

    public void show() {
        Label label = new Label("Iniciar Sesión");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Usuario");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Contraseña");

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        Button loginBtn = new Button("Entrar");

        loginBtn.setOnAction(e -> {
            Jugador jugador = jugadorController.autenticarJugador(
                    usernameField.getText(),
                    passwordField.getText()
            );

            if (jugador != null) {
                new Alert(Alert.AlertType.INFORMATION, "Bienvenido " + jugador.getUsername()).showAndWait();
                // Ir a la pantalla de búsqueda de partidos
                new SearchMatchesScreen(stage, jugador).show();
            } else {
                errorLabel.setText("Credenciales incorrectas.");
            }
        });

        Button registerBtn = new Button("Registrarse");
        registerBtn.setOnAction(e -> new RegisterScreen(stage).show());

        VBox layout = new VBox(10, label, usernameField, passwordField, loginBtn, registerBtn, errorLabel);
        layout.setAlignment(Pos.CENTER);
        layout.setMinWidth(300);
        stage.setScene(new Scene(layout, 400, 300));
        stage.setTitle("Login");
        stage.show();
    }
}