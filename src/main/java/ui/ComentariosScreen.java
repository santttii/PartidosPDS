package ui;

import controller.PartidoController;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.partido.Comentario;
import model.partido.Jugador;
import model.partido.Partido;

import java.util.Date;

public class ComentariosScreen {
    private final Stage stage;
    private final Partido partido;
    private final Jugador jugadorActual;
    private final PartidoController partidoController;

    public ComentariosScreen(Partido partido, Jugador jugadorActual, PartidoController partidoController) {
        this.stage = new Stage();
        this.partido = partido;
        this.jugadorActual = jugadorActual;
        this.partidoController = partidoController;
    }

    public void show() {
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(15));

        // Lista de comentarios existentes
        ListView<Comentario> comentariosListView = new ListView<>();
        comentariosListView.setPrefHeight(300);
        comentariosListView.setCellFactory(lv -> new ComentarioListCell());
        if (partido.getComentarios() != null) {
            comentariosListView.setItems(FXCollections.observableArrayList(partido.getComentarios()));
        }

        // Área para nuevo comentario
        TextArea nuevoComentarioArea = new TextArea();
        nuevoComentarioArea.setPromptText("Escribe tu comentario aquí...");
        nuevoComentarioArea.setPrefRowCount(3);

        // Botón para enviar comentario
        Button enviarBtn = new Button("Enviar Comentario");
        enviarBtn.setOnAction(e -> {
            String mensaje = nuevoComentarioArea.getText().trim();
            if (!mensaje.isEmpty()) {
                Comentario nuevoComentario = new Comentario(jugadorActual, mensaje);
                partido.agregarComentario(nuevoComentario);
                partidoController.guardarPartido(partido);
                comentariosListView.getItems().add(nuevoComentario);
                nuevoComentarioArea.clear();
            }
        });

        mainLayout.getChildren().addAll(
                new Label("Comentarios del partido"),
                comentariosListView,
                new Label("Nuevo comentario:"),
                nuevoComentarioArea,
                enviarBtn
        );

        Scene scene = new Scene(mainLayout, 400, 500);
        stage.setTitle("Comentarios del Partido");
        stage.setScene(scene);
        stage.show();
    }

    private static class ComentarioListCell extends ListCell<Comentario> {
        @Override
        protected void updateItem(Comentario comentario, boolean empty) {
            super.updateItem(comentario, empty);
            if (empty || comentario == null) {
                setText(null);
                setGraphic(null);
            } else {
                VBox content = new VBox(5);
                content.setPadding(new Insets(5));

                Label userLabel = new Label(comentario.getJugador().getUsername());
                userLabel.setStyle("-fx-font-weight: bold;");

                Label messageLabel = new Label(comentario.getMensaje());
                messageLabel.setWrapText(true);

                Label dateLabel = new Label(comentario.getFecha().toString());
                dateLabel.setStyle("-fx-font-size: 10px; -fx-text-fill: gray;");

                content.getChildren().addAll(userLabel, messageLabel, dateLabel);
                setGraphic(content);
            }
        }
    }
}
