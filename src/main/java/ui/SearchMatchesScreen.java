package ui;

import controller.PartidoController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.partido.*;

import java.util.List;

public class SearchMatchesScreen {
    private final Stage stage;
    private final PartidoController partidoController;
    private final Jugador jugadorActual;
    private ListView<Partido> partidosListView;
    private ListView<Partido> misPartidosListView;
    private Label resultadosLabel;

    public SearchMatchesScreen(Stage stage, Jugador jugadorActual) {
        this.stage = stage;
        this.partidoController = new PartidoController();
        this.jugadorActual = jugadorActual;
    }

    public void show() {
        HBox mainLayout = new HBox(20);
        mainLayout.setPadding(new Insets(20));

        VBox leftPanel = new VBox(15);
        leftPanel.setPrefWidth(600);

        VBox rightPanel = new VBox(15);
        rightPanel.setPrefWidth(300);
        rightPanel.setStyle("-fx-border-color: lightgray; -fx-border-width: 0 0 0 1; -fx-padding: 0 0 0 15;");

        Label titleLabel = new Label("Buscar Partidos");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        VBox filtrosPanel = crearPanelFiltros();
        VBox resultadosPanel = crearPanelResultados();
        HBox botonesPanel = crearPanelBotones();

        leftPanel.getChildren().addAll(titleLabel, filtrosPanel, resultadosPanel, botonesPanel);
        VBox.setVgrow(resultadosPanel, Priority.ALWAYS);

        Label misPartidosTitle = new Label("Mis Partidos");
        misPartidosTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        misPartidosListView = new ListView<>();
        misPartidosListView.setFixedCellSize(-1); // permitir celdas con altura dinámica
        misPartidosListView.setPrefHeight(400);
        misPartidosListView.setCellFactory(lv -> new MisPartidosListCell(jugadorActual));
        VBox.setVgrow(misPartidosListView, Priority.ALWAYS);

        Button refreshButton = new Button("Actualizar Mis Partidos");
        refreshButton.setOnAction(e -> cargarMisPartidos());

        rightPanel.getChildren().addAll(misPartidosTitle, misPartidosListView, refreshButton);
        mainLayout.getChildren().addAll(leftPanel, rightPanel);
        HBox.setHgrow(leftPanel, Priority.ALWAYS);

        cargarPartidosDisponibles();
        cargarMisPartidos();

        ScrollPane scrollPane = new ScrollPane(mainLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        Scene scene = new Scene(scrollPane, 1000, 600);
        stage.setScene(scene);
        stage.setTitle("Buscar Partidos");
        stage.show();
    }

    private void cargarMisPartidos() {
        if (jugadorActual != null) {
            List<Partido> misPartidos = partidoController.buscarPartidosPorJugador(jugadorActual);
            ObservableList<Partido> observableMisPartidos = FXCollections.observableArrayList(misPartidos);
            misPartidosListView.setItems(observableMisPartidos);

            if (misPartidos.isEmpty()) {
                misPartidosListView.setPlaceholder(new Label("No estás participando en ningún partido"));
            }
        }
    }

    private VBox crearPanelFiltros() {
        VBox panel = new VBox(10);
        panel.setStyle("-fx-border-color: lightgray; -fx-border-width: 1; -fx-padding: 15;");

        Label filtrosLabel = new Label("Filtros de Búsqueda");
        filtrosLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label deporteLabel = new Label("Deporte:");
        ComboBox<Deporte> deporteCombo = new ComboBox<>();
        deporteCombo.getItems().addAll(Futbol.getInstancia(), Basquet.getInstancia(), Voley.getInstancia());
        deporteCombo.setPromptText("Seleccionar deporte (opcional)");
        deporteCombo.setMaxWidth(Double.MAX_VALUE);

        Label ubicacionLabel = new Label("Ubicación:");
        TextField ubicacionField = new TextField();
        ubicacionField.setPromptText("Buscar por ubicación (opcional)");

        Label estadoLabel = new Label("Estado:");
        ComboBox<String> estadoCombo = new ComboBox<>();
        estadoCombo.getItems().addAll("NecesitamosJugadores", "Confirmado", "Finalizado", "Cancelado");
        estadoCombo.setPromptText("Seleccionar estado (opcional)");
        estadoCombo.setMaxWidth(Double.MAX_VALUE);

        HBox botonesSearch = new HBox(10);
        botonesSearch.setAlignment(Pos.CENTER);

        Button buscarBtn = new Button("Buscar con Filtros");
        buscarBtn.setOnAction(e -> buscarConFiltros(deporteCombo.getValue(), ubicacionField.getText().trim(), estadoCombo.getValue()));

        Button disponiblesBtn = new Button("Ver Disponibles");
        disponiblesBtn.setOnAction(e -> cargarPartidosDisponibles());

        Button limpiarBtn = new Button("Limpiar Filtros");
        limpiarBtn.setOnAction(e -> {
            deporteCombo.setValue(null);
            ubicacionField.clear();
            estadoCombo.setValue(null);
        });

        Button todosBtn = new Button("Ver Todos");
        todosBtn.setOnAction(e -> cargarTodosLosPartidos());

        botonesSearch.getChildren().addAll(buscarBtn, disponiblesBtn, limpiarBtn, todosBtn);

        panel.getChildren().addAll(
                filtrosLabel,
                deporteLabel, deporteCombo,
                ubicacionLabel, ubicacionField,
                estadoLabel, estadoCombo,
                botonesSearch
        );

        return panel;
    }

    private VBox crearPanelResultados() {
        VBox panel = new VBox(10);
        resultadosLabel = new Label("Partidos Disponibles");
        resultadosLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        partidosListView = new ListView<>();
        partidosListView.setPrefHeight(250);
        partidosListView.setCellFactory(listView -> new PartidoListCell());

        panel.getChildren().addAll(resultadosLabel, partidosListView);
        return panel;
    }

    private HBox crearPanelBotones() {
        HBox panel = new HBox(15);
        panel.setAlignment(Pos.CENTER);

        Button crearPartidoBtn = new Button("Crear Partido");
        crearPartidoBtn.setOnAction(e -> new CreateMatchScreen(stage, jugadorActual).show());

        Button unirseBtn = new Button("Unirse al Partido");
        unirseBtn.setOnAction(e -> {
            Partido partidoSeleccionado = partidosListView.getSelectionModel().getSelectedItem();
            if (partidoSeleccionado != null) {
                unirseAPartido(partidoSeleccionado);
            } else {
                mostrarAlerta("Por favor, selecciona un partido primero.", Alert.AlertType.WARNING);
            }
        });

        Button verDetallesBtn = new Button("Ver Detalles");
        verDetallesBtn.setOnAction(e -> {
            Partido partidoSeleccionado = partidosListView.getSelectionModel().getSelectedItem();
            if (partidoSeleccionado != null) {
                mostrarDetallesPartido(partidoSeleccionado);
            } else {
                mostrarAlerta("Por favor, selecciona un partido primero.", Alert.AlertType.WARNING);
            }
        });

        Button volverBtn = new Button("Volver");
        volverBtn.setOnAction(e -> stage.close());

        panel.getChildren().addAll(crearPartidoBtn, unirseBtn, verDetallesBtn, volverBtn);
        return panel;
    }

    private void buscarConFiltros(Deporte deporte, String ubicacion, String estado) {
        List<Partido> partidos;

        if (deporte == null && (ubicacion == null || ubicacion.isEmpty()) && estado == null) {
            partidos = partidoController.buscarPartidosDisponibles();
            resultadosLabel.setText("Partidos Disponibles (sin filtros)");
        } else if ((deporte != null ? 1 : 0) + (!ubicacion.isEmpty() ? 1 : 0) + (estado != null ? 1 : 0) > 1) {
            partidos = partidoController.buscarPartidosPorCriterios(deporte, ubicacion, estado, null);
            resultadosLabel.setText("Resultados con filtros combinados");
        } else if (deporte != null) {
            partidos = partidoController.buscarPartidosPorDeporte(deporte);
            resultadosLabel.setText("Partidos de " + deporte);
        } else if (!ubicacion.isEmpty()) {
            partidos = partidoController.buscarPartidosPorUbicacion(ubicacion);
            resultadosLabel.setText("Partidos en: " + ubicacion);
        } else {
            partidos = partidoController.buscarPartidosPorEstado(estado);
            resultadosLabel.setText("Partidos con estado: " + estado);
        }

        actualizarListaPartidos(partidos);
    }

    private void cargarPartidosDisponibles() {
        List<Partido> partidos = partidoController.buscarPartidosDisponibles();
        resultadosLabel.setText("Partidos Disponibles (" + partidos.size() + ")");
        actualizarListaPartidos(partidos);
    }

    private void cargarTodosLosPartidos() {
        List<Partido> partidos = partidoController.obtenerTodosLosPartidos();
        resultadosLabel.setText("Todos los Partidos (" + partidos.size() + ")");
        actualizarListaPartidos(partidos);
    }

    private void actualizarListaPartidos(List<Partido> partidos) {
        ObservableList<Partido> observablePartidos = FXCollections.observableArrayList(partidos);
        partidosListView.setItems(observablePartidos);
        if (partidos.isEmpty()) {
            partidosListView.setPlaceholder(new Label("No se encontraron partidos con los criterios especificados"));
        }
    }

    private void mostrarDetallesPartido(Partido partido) {
        String detalles = partidoController.obtenerInformacionPartido(partido);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Detalles del Partido");
        alert.setHeaderText("Información Completa");
        alert.setContentText(detalles);
        alert.getDialogPane().setPrefWidth(500);
        alert.showAndWait();
    }

    private void unirseAPartido(Partido partido) {
        try {
            if (partidoController.agregarJugadorAPartido(partido, jugadorActual)) {
                mostrarAlerta("¡Te has unido al partido exitosamente!", Alert.AlertType.INFORMATION);
                cargarPartidosDisponibles();
                cargarMisPartidos();
            } else {
                mostrarAlerta("No se pudo unir al partido. Verifica si cumples los requisitos.", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            mostrarAlerta("Error al unirse al partido: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // ==== CELDAS PERSONALIZADAS ====

    private static class PartidoListCell extends ListCell<Partido> {
        @Override
        protected void updateItem(Partido partido, boolean empty) {
            super.updateItem(partido, empty);
            if (empty || partido == null) {
                setText(null);
                setGraphic(null);
            } else {
                VBox content = new VBox(5);
                content.setPadding(new Insets(8));

                Label mainInfo = new Label(partido.getDeporte() + " - " + partido.getUbicacion());
                mainInfo.setStyle("-fx-font-weight: bold;");

                Label fecha = new Label("Fecha: " + partido.getHorario());
                Label estado = new Label("Estado: " + partido.getNombreEstado());

                content.getChildren().addAll(mainInfo, fecha, estado);
                setGraphic(content);
            }
        }
    }

    private class MisPartidosListCell extends ListCell<Partido> {
        private final Jugador jugadorActual;

        public MisPartidosListCell(Jugador jugadorActual) {
            this.jugadorActual = jugadorActual;
        }

        @Override
        protected void updateItem(Partido partido, boolean empty) {
            super.updateItem(partido, empty);
            if (empty || partido == null) {
                setText(null);
                setGraphic(null);
            } else {
                VBox content = new VBox(5);
                content.setPadding(new Insets(8));

                Label info = new Label(partido.getDeporte() + " - " + partido.getUbicacion());
                info.setStyle("-fx-font-weight: bold;");

                Label fecha = new Label("Fecha: " + partido.getHorario());
                Label estado = new Label("Estado: " + partido.getNombreEstado());

                content.getChildren().addAll(info, fecha, estado);

                if (jugadorActual.equals(partido.getCreador())) {
                    Button cancelarBtn = new Button("Cancelar");
                    cancelarBtn.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
                    cancelarBtn.setOnAction(e -> {
                        boolean confirmar = confirmarCancelacion(partido);
                        if (confirmar) {
                            partidoController.cancelarPartido(partido);
                            cargarMisPartidos();
                            mostrarAlerta("Partido cancelado correctamente.", Alert.AlertType.INFORMATION);
                        }
                    });

                    VBox.setMargin(cancelarBtn, new Insets(5, 0, 0, 0));
                    content.getChildren().add(cancelarBtn);
                }

                setGraphic(content);
                setText(null);
            }
        }

        private boolean confirmarCancelacion(Partido partido) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar cancelación");
            alert.setHeaderText("¿Estás seguro que querés cancelar este partido?");
            alert.setContentText("Ubicación: " + partido.getUbicacion() + "\nFecha: " + partido.getHorario());
            return alert.showAndWait().filter(response -> response == ButtonType.OK).isPresent();
        }
    }
}
