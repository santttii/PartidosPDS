package ui;

import controller.PartidoController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.emparejamiento.*;
import model.partido.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class CreateMatchScreen {
    private final Stage stage;
    private final PartidoController partidoController;
    private final Jugador jugadorActual;

    public CreateMatchScreen(Stage stage, Jugador jugadorActual) {
        this.stage = stage;
        this.partidoController = new PartidoController();
        this.jugadorActual = jugadorActual;
    }

    public void show() {

        VBox mainLayout = new VBox(15);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.TOP_CENTER);

        // Título
        Label titleLabel = new Label("Crear Nuevo Partido");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Formulario
        GridPane form = createForm();

        mainLayout.getChildren().addAll(titleLabel, form);

        Scene scene = new Scene(mainLayout, 600, 500);
        stage.setScene(scene);
        stage.setTitle("Crear Partido");
        stage.show();
        // Mejorar el debug para ver más información del jugador
        if (jugadorActual != null) {
            System.out.println("Jugador actual en CreateMatchScreen: " + 
                              jugadorActual.getUsername() + 
                              " (Nivel: " + jugadorActual.getNivel() + ")");
        } else {
            System.out.println("¡Advertencia! jugadorActual es null en CreateMatchScreen");
        }
    }

    private GridPane createForm() {
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(20));

        // Deporte
        Label deporteLabel = new Label("Deporte:");
        ComboBox<Deporte> deporteCombo = new ComboBox<>();
        deporteCombo.getItems().addAll(
                Futbol.getInstancia(),
                Basquet.getInstancia(),
                Voley.getInstancia()
        );
        deporteCombo.setPromptText("Seleccionar deporte");

        // Ubicación
        Label ubicacionLabel = new Label("Ubicación:");
        TextField ubicacionField = new TextField();

        // Cupo máximo
        Label cupoLabel = new Label("Cupo máximo:");
        Spinner<Integer> cupoSpinner = new Spinner<>(2, 22, 10);

        // Fecha y hora
        Label fechaLabel = new Label("Fecha y hora:");
        DatePicker datePicker = new DatePicker();
        ComboBox<String> horaCombo = new ComboBox<>();
        for (int i = 8; i <= 22; i++) {
            horaCombo.getItems().add(String.format("%02d:00", i));
        }
        HBox dateTimeBox = new HBox(10, datePicker, horaCombo);

        // Duración
        Label duracionLabel = new Label("Duración (horas):");
        Spinner<Double> duracionSpinner = new Spinner<>(0.5, 4.0, 1.5, 0.5);

        // En CreateMatchScreen.java, en la parte de configuración de estrategia

        Label estrategiaLabel = new Label("Estrategia de emparejamiento:");
        ComboBox<String> estrategiaCombo = new ComboBox<>();
        estrategiaCombo.getItems().addAll(
            "Cualquier nivel",
            "Nivel específico",
            "Rango de nivel",
            "Por cantidad de partidos",
            "Por ubicación"
        );

        // Panel para configuración de niveles
        VBox nivelesPanel = new VBox(10);

        // Para nivel específico
        ComboBox<Jugador.NivelJugador> nivelEspecificoCombo = new ComboBox<>();
        nivelEspecificoCombo.getItems().addAll(Jugador.NivelJugador.values());
        nivelEspecificoCombo.setPromptText("Seleccione nivel");

        // Para rango de niveles
        Label nivelMinLabel = new Label("Nivel mínimo:");
        ComboBox<Jugador.NivelJugador> nivelMinCombo = new ComboBox<>();
        nivelMinCombo.getItems().addAll(Jugador.NivelJugador.values());

        Label nivelMaxLabel = new Label("Nivel máximo:");
        ComboBox<Jugador.NivelJugador> nivelMaxCombo = new ComboBox<>();
        nivelMaxCombo.getItems().addAll(Jugador.NivelJugador.values());

        nivelesPanel.getChildren().addAll(
            nivelEspecificoCombo,
            nivelMinLabel, nivelMinCombo,
            nivelMaxLabel, nivelMaxCombo
        );
        nivelesPanel.setVisible(false);

        // Mostrar/ocultar controles según la estrategia seleccionada
        estrategiaCombo.setOnAction(e -> {
            String seleccion = estrategiaCombo.getValue();
            nivelEspecificoCombo.setVisible(seleccion.equals("Nivel específico"));
            nivelMinLabel.setVisible(seleccion.equals("Rango de nivel"));
            nivelMinCombo.setVisible(seleccion.equals("Rango de nivel"));
            nivelMaxLabel.setVisible(seleccion.equals("Rango de nivel"));
            nivelMaxCombo.setVisible(seleccion.equals("Rango de nivel"));
            nivelesPanel.setVisible(seleccion.equals("Nivel específico") || seleccion.equals("Rango de nivel"));
        });

        // Botones
        Button crearBtn = new Button("Crear Partido");
        crearBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        Button cancelarBtn = new Button("Cancelar");
        HBox botonesBox = new HBox(10, crearBtn, cancelarBtn);

        // Agregar elementos al form
        form.add(deporteLabel, 0, 0);
        form.add(deporteCombo, 1, 0);
        form.add(ubicacionLabel, 0, 1);
        form.add(ubicacionField, 1, 1);
        form.add(cupoLabel, 0, 2);
        form.add(cupoSpinner, 1, 2);
        form.add(fechaLabel, 0, 3);
        form.add(dateTimeBox, 1, 3);
        form.add(duracionLabel, 0, 4);
        form.add(duracionSpinner, 1, 4);
        form.add(estrategiaLabel, 0, 5);
        form.add(estrategiaCombo, 1, 5);
        form.add(nivelesPanel, 1, 6); // Coloca el panel de niveles
        form.add(botonesBox, 1, 7);

        // Eventos
        crearBtn.setOnAction(e -> {
            try {
                // Validar campos
                if (deporteCombo.getValue() == null || ubicacionField.getText().isEmpty() ||
                        datePicker.getValue() == null || horaCombo.getValue() == null ||
                        estrategiaCombo.getValue() == null) {
                    mostrarError("Todos los campos son obligatorios");
                    return;
                }

                // Crear fecha combinando DatePicker y ComboBox de hora
                LocalDateTime dateTime = LocalDateTime.of(
                        datePicker.getValue(),
                        LocalDateTime.parse(datePicker.getValue() + "T" + horaCombo.getValue()).toLocalTime()
                );
                Date fecha = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());

                // En la parte de creación del partido:
                IEstrategiaEmparejamiento estrategia;
                switch (estrategiaCombo.getValue()) {
                    case "Cualquier nivel":
                        estrategia = new EmparejamientoPorNivel();
                        break;
                    case "Nivel específico":
                        if (nivelEspecificoCombo.getValue() == null) {
                            mostrarError("Debe seleccionar un nivel");
                            return;
                        }
                        estrategia = new EmparejamientoPorNivel(nivelEspecificoCombo.getValue());
                        break;
                    case "Rango de nivel":
                        if (nivelMinCombo.getValue() == null || nivelMaxCombo.getValue() == null) {
                            mostrarError("Debe seleccionar niveles mínimo y máximo");
                            return;
                        }
                        try {
                            estrategia = new EmparejamientoPorNivel(
                                nivelMinCombo.getValue(),
                                nivelMaxCombo.getValue()
                            );
                        } catch (IllegalArgumentException ex) {
                            mostrarError(ex.getMessage());
                            return;
                        }
                        break;
                    case "Por cantidad de partidos":
                        estrategia = new EmparejamientoPorHistorial(0);
                        break;
                    default:
                        estrategia = new EmparejamientoPorCercania("");
                }

                // Crear partido
                Partido nuevoPartido = partidoController.crearPartido(
                        cupoSpinner.getValue(),
                        deporteCombo.getValue(),
                        ubicacionField.getText(),
                        fecha,
                        duracionSpinner.getValue(),
                        estrategia,
                        jugadorActual  // Agregamos el jugador actual como creador
                );

                if (nuevoPartido != null) {
                    mostrarExito("Partido creado exitosamente");
                    // Redirigir a SearchMatchesScreen
                    new SearchMatchesScreen(stage, jugadorActual).show();
                } else {
                    mostrarError("Error al crear el partido");
                }

            } catch (Exception ex) {
                mostrarError("Error: " + ex.getMessage());
            }
        });

        cancelarBtn.setOnAction(e -> stage.close());

        return form;
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarExito(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}