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
        // Layout principal horizontal
        HBox mainLayout = new HBox(20);
        mainLayout.setPadding(new Insets(20));

        // Panel izquierdo (búsqueda)
        VBox leftPanel = new VBox(15);
        leftPanel.setPrefWidth(600);

        // Panel derecho (mis partidos)
        VBox rightPanel = new VBox(15);
        rightPanel.setPrefWidth(300);
        rightPanel.setStyle("-fx-border-color: lightgray; -fx-border-width: 0 0 0 1; -fx-padding: 0 0 0 15;");

        // Configurar panel izquierdo
        Label titleLabel = new Label("Buscar Partidos");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        VBox filtrosPanel = crearPanelFiltros();
        VBox resultadosPanel = crearPanelResultados();
        HBox botonesPanel = crearPanelBotones();

        leftPanel.getChildren().addAll(titleLabel, filtrosPanel, resultadosPanel, botonesPanel);
        VBox.setVgrow(resultadosPanel, Priority.ALWAYS);

        // Configurar panel derecho
        Label misPartidosTitle = new Label("Mis Partidos");
        misPartidosTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        misPartidosListView = new ListView<>();
        misPartidosListView.setPrefHeight(400);
        misPartidosListView.setCellFactory(lv -> new MisPartidosListCell(jugadorActual,partidoController));
        VBox.setVgrow(misPartidosListView, Priority.ALWAYS);

        Button refreshButton = new Button("Actualizar Mis Partidos");
        refreshButton.setOnAction(e -> {
            partidoController.actualizarEstadosPartidos();
            cargarMisPartidos();
        });


        rightPanel.getChildren().addAll(misPartidosTitle, misPartidosListView, refreshButton);

        // Agregar paneles al layout principal
        mainLayout.getChildren().addAll(leftPanel, rightPanel);
        HBox.setHgrow(leftPanel, Priority.ALWAYS);

        // Cargar datos iniciales
        cargarPartidosDisponibles();
        cargarMisPartidos();

        System.out.println(jugadorActual.getCantidadPartidosJugados());

        // Crear y mostrar la escena
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
            List<Partido> misPartidos = partidoController.buscarPartidosPorJugador(jugadorActual)
                    .stream()
                    .toList();

            ObservableList<Partido> observableMisPartidos = FXCollections.observableArrayList(misPartidos);
            misPartidosListView.setItems(observableMisPartidos);

            if (misPartidos.isEmpty()) {
                misPartidosListView.setPlaceholder(new Label("No estás participando en ningún partido"));
            }
        }
    }

    // ... resto de los métodos existentes ...

    // Asegúrate de actualizar ambas listas cuando te unes a un partido
    private void unirseAPartido(Partido partido) {
        try {
            if (partidoController.agregarJugadorAPartido(partido, jugadorActual)) {
                mostrarAlerta("¡Te has unido al partido exitosamente!", Alert.AlertType.INFORMATION);
                cargarPartidosDisponibles();
                cargarMisPartidos(); // Actualizar la lista de mis partidos
            } else {
                mostrarAlerta("No se pudo unir al partido. Verifica si cumples los requisitos.", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            mostrarAlerta("Error al unirse al partido: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private VBox crearPanelFiltros() {
        VBox panel = new VBox(10);
        panel.setStyle("-fx-border-color: lightgray; -fx-border-width: 1; -fx-padding: 15;");

        Label filtrosLabel = new Label("Filtros de Búsqueda");
        filtrosLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // ComboBox para Deporte
        Label deporteLabel = new Label("Deporte:");
        ComboBox<Deporte> deporteCombo = new ComboBox<>();
        deporteCombo.getItems().addAll(
                Futbol.getInstancia(),
                Basquet.getInstancia(),
                Voley.getInstancia()
        );
        deporteCombo.setPromptText("Seleccionar deporte (opcional)");
        deporteCombo.setMaxWidth(Double.MAX_VALUE);

        // TextField para Ubicación
        Label ubicacionLabel = new Label("Ubicación:");
        TextField ubicacionField = new TextField();
        ubicacionField.setPromptText("Buscar por ubicación (opcional)");

        // ComboBox para Estado
        Label estadoLabel = new Label("Estado:");
        ComboBox<String> estadoCombo = new ComboBox<>();
        estadoCombo.getItems().addAll(
                "NecesitamosJugadores",
                "Confirmado",
                "Finalizado",
                "Cancelado"
        );
        estadoCombo.setPromptText("Seleccionar estado (opcional)");
        estadoCombo.setMaxWidth(Double.MAX_VALUE);

        // Botones de búsqueda
        HBox botonesSearch = new HBox(10);
        botonesSearch.setAlignment(Pos.CENTER);

        Button buscarBtn = new Button("Buscar con Filtros");
        buscarBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        buscarBtn.setOnAction(e -> {
            Deporte deporteSeleccionado = deporteCombo.getValue();
            String ubicacion = ubicacionField.getText().trim();
            String estado = estadoCombo.getValue();

            buscarConFiltros(deporteSeleccionado, ubicacion, estado);
        });

        Button disponiblesBtn = new Button("Ver Disponibles");
        disponiblesBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
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
        crearPartidoBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");
        crearPartidoBtn.setOnAction(e -> {
            new CreateMatchScreen(stage, jugadorActual).show();
        });

        Button unirseBtn = new Button("Unirse al Partido");
        unirseBtn.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-size: 14px;");
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
            if (partidoSeleccionado == null) {
                partidoSeleccionado = misPartidosListView.getSelectionModel().getSelectedItem();
            }

            if (partidoSeleccionado != null) {
                mostrarDetallesPartido(partidoSeleccionado);
            } else {
                mostrarAlerta("Por favor, selecciona un partido primero.", Alert.AlertType.WARNING);
            }
        });


        Button volverBtn = new Button("Volver");
        volverBtn.setOnAction(e -> {
            // TODO: Volver a la pantalla anterior (Home)
            stage.close();
        });

        panel.getChildren().addAll(crearPartidoBtn, unirseBtn, verDetallesBtn, volverBtn);
        return panel;
    }

    private void buscarConFiltros(Deporte deporte, String ubicacion, String estado) {
        List<Partido> partidos;

        // Si todos los filtros están vacíos, mostrar disponibles
        if (deporte == null && (ubicacion == null || ubicacion.isEmpty()) && estado == null) {
            partidos = partidoController.buscarPartidosDisponibles();
            resultadosLabel.setText("Partidos Disponibles (sin filtros específicos)");
        }
        // Si hay múltiples criterios, usar búsqueda combinada
        else if ((deporte != null ? 1 : 0) + (ubicacion != null && !ubicacion.isEmpty() ? 1 : 0) + (estado != null ? 1 : 0) > 1) {
            partidos = partidoController.buscarPartidosPorCriterios(
                    deporte,
                    ubicacion,
                    estado,
                    null
            );
            resultadosLabel.setText("Resultados con filtros combinados");
        }
        // Búsquedas individuales
        else if (deporte != null) {
            partidos = partidoController.buscarPartidosPorDeporte(deporte);
            resultadosLabel.setText("Partidos de " + deporte);
        } else if (ubicacion != null && !ubicacion.isEmpty()) {
            partidos = partidoController.buscarPartidosPorUbicacion(ubicacion);
            resultadosLabel.setText("Partidos en: " + ubicacion);
        } else if (estado != null) {
            partidos = partidoController.buscarPartidosPorEstado(estado);
            resultadosLabel.setText("Partidos con estado: " + estado);
        } else {
            partidos = partidoController.buscarPartidosDisponibles();
            resultadosLabel.setText("Partidos Disponibles");
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

    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Clase interna para personalizar cómo se muestran los partidos en la lista
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
        private final PartidoController partidoController;

        public MisPartidosListCell(Jugador jugadorActual, PartidoController partidoController) {
            this.jugadorActual = jugadorActual;
            this.partidoController = partidoController;
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

                // Agregar aquí el código del botón de comentar
                if ("Finalizado".equals(partido.getNombreEstado()) && 
                    partido.getJugadores().stream().anyMatch(j -> j.getIdJugador() == jugadorActual.getIdJugador())) {
                    
                    Button comentarBtn = new Button("Comentar");
                    comentarBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
                    comentarBtn.setOnAction(e -> {
                        new ComentariosScreen(partido, jugadorActual, partidoController).show();
                    });
                    
                    content.getChildren().add(comentarBtn);
                }

                // El resto del código existente para el botón de cancelar si es el creador
                if (partido.getCreador() != null && 
                    jugadorActual.getIdJugador() == partido.getCreador().getIdJugador() &&
                    !"Cancelado".equals(partido.getNombreEstado()) && !"Finalizado".equals(partido.getNombreEstado())) {
                    
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