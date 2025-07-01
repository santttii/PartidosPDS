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
import java.time.LocalDate;

public class CreateMatchScreen {
    private final Stage stage;
    private final PartidoController partidoController;
    private final Jugador jugadorActual;

    // Agregar el nuevo campo para la cantidad de partidos
    private TextField cantidadPartidosField;
    private Label cantidadPartidosLabel;

    public CreateMatchScreen(Stage stage, Jugador jugadorActual) {
        this.stage = stage;
        this.partidoController = new PartidoController();
        this.jugadorActual = jugadorActual;
    }

    public void show() {
        VBox mainLayout = new VBox(15);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.TOP_CENTER);

        Label titleLabel = new Label("Crear Nuevo Partido");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        GridPane form = createForm();

        mainLayout.getChildren().addAll(titleLabel, form);

        Scene scene = new Scene(mainLayout, 600, 500);
        stage.setScene(scene);
        stage.setTitle("Crear Partido");
        stage.show();

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

        Label deporteLabel = new Label("Deporte:");
        ComboBox<Deporte> deporteCombo = new ComboBox<>();
        deporteCombo.getItems().addAll(
                Futbol.getInstancia(),
                Basquet.getInstancia(),
                Voley.getInstancia()
        );
        deporteCombo.setPromptText("Seleccionar deporte");

        Label ubicacionLabel = new Label("Ubicación:");
        TextField ubicacionField = new TextField();

        Label fechaLabel = new Label("Fecha y hora:");
        DatePicker datePicker = new DatePicker();

        TextField horaField = new TextField();
        horaField.setPromptText("14:00");
        horaField.setPrefWidth(100);

        horaField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*:?\\d*")) {
                horaField.setText(oldValue);
            }
        });

        horaField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                String texto = horaField.getText().trim();
                if (!texto.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]")) {
                    horaField.setText("");
                    mostrarAlerta("Formato de hora inválido. Use HH:mm (ejemplo: 14:00)", Alert.AlertType.ERROR);
                }
            }
        });

        HBox dateTimeBox = new HBox(10, datePicker, horaField);

        Label duracionLabel = new Label("Duración (horas):");
        Spinner<Double> duracionSpinner = new Spinner<>(0.5, 4.0, 1.5, 0.5);

        Label estrategiaLabel = new Label("Estrategia de emparejamiento:");
        ComboBox<String> estrategiaCombo = new ComboBox<>();
        estrategiaCombo.getItems().addAll(
                "Cualquier nivel",
                "Nivel específico",
                "Rango de nivel",
                "Por cantidad de partidos",
                "Por ubicación"
        );

        // Inicializar los componentes para cantidad de partidos
        cantidadPartidosLabel = new Label("Cantidad mínima de partidos:");
        cantidadPartidosField = new TextField();
        cantidadPartidosField.setPromptText("Ingrese la cantidad");
        cantidadPartidosField.setPrefWidth(100);
        
        // Solo permitir números en el campo
        cantidadPartidosField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                cantidadPartidosField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        // Ocultar inicialmente
        cantidadPartidosLabel.setVisible(false);
        cantidadPartidosField.setVisible(false);

        VBox nivelesPanel = new VBox(10);

        ComboBox<Jugador.NivelJugador> nivelEspecificoCombo = new ComboBox<>();
        nivelEspecificoCombo.getItems().addAll(Jugador.NivelJugador.values());
        nivelEspecificoCombo.setPromptText("Seleccione nivel");

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

        estrategiaCombo.setOnAction(e -> {
            String seleccion = estrategiaCombo.getValue();
            nivelEspecificoCombo.setVisible(seleccion.equals("Nivel específico"));
            nivelMinLabel.setVisible(seleccion.equals("Rango de nivel"));
            nivelMinCombo.setVisible(seleccion.equals("Rango de nivel"));
            nivelMaxLabel.setVisible(seleccion.equals("Rango de nivel"));
            nivelMaxCombo.setVisible(seleccion.equals("Rango de nivel"));
            cantidadPartidosLabel.setVisible(seleccion.equals("Por cantidad de partidos"));
            cantidadPartidosField.setVisible(seleccion.equals("Por cantidad de partidos"));
            nivelesPanel.setVisible(seleccion.equals("Nivel específico") || seleccion.equals("Rango de nivel"));
        });

        Button crearBtn = new Button("Crear Partido");
        crearBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        Button cancelarBtn = new Button("Cancelar");
        HBox botonesBox = new HBox(10, crearBtn, cancelarBtn);

        form.add(deporteLabel, 0, 0);
        form.add(deporteCombo, 1, 0);
        form.add(ubicacionLabel, 0, 1);
        form.add(ubicacionField, 1, 1);
        form.add(fechaLabel, 0, 2);
        form.add(dateTimeBox, 1, 2);
        form.add(duracionLabel, 0, 3);
        form.add(duracionSpinner, 1, 3);
        form.add(estrategiaLabel, 0, 4);
        form.add(estrategiaCombo, 1, 4);
        form.add(nivelesPanel, 1, 5);
        // Agregar los nuevos componentes
        form.add(cantidadPartidosLabel, 0, 6);
        form.add(cantidadPartidosField, 1, 6);
        form.add(botonesBox, 1, 7);

        crearBtn.setOnAction(e -> {
            try {
                if (deporteCombo.getValue() == null || ubicacionField.getText().isEmpty() ||
                        datePicker.getValue() == null || horaField.getText().isEmpty() ||
                        estrategiaCombo.getValue() == null) {
                    mostrarError("Todos los campos son obligatorios");
                    return;
                }

                String estrategiaSeleccionada = estrategiaCombo.getValue();

                if (estrategiaSeleccionada.equals("Nivel específico")) {
                    if (nivelEspecificoCombo.getValue() == null) {
                        mostrarError("Debe seleccionar un nivel");
                        return;
                    }

                    if (!jugadorActual.getNivel().equals(nivelEspecificoCombo.getValue())) {
                        mostrarError("No puedes crear un partido para nivel " + nivelEspecificoCombo.getValue() +
                                " porque tu nivel es " + jugadorActual.getNivel());
                        return;
                    }
                }

                if (estrategiaSeleccionada.equals("Rango de nivel")) {
                    if (nivelMinCombo.getValue() == null || nivelMaxCombo.getValue() == null) {
                        mostrarError("Debe seleccionar niveles mínimo y máximo");
                        return;
                    }

                    Jugador.NivelJugador nivelUsuario = jugadorActual.getNivel();
                    Jugador.NivelJugador nivelMin = nivelMinCombo.getValue();
                    Jugador.NivelJugador nivelMax = nivelMaxCombo.getValue();

                    if (nivelMin.ordinal() > nivelMax.ordinal()) {
                        mostrarError("El nivel mínimo no puede ser mayor que el nivel máximo");
                        return;
                    }

                    if (nivelUsuario.ordinal() < nivelMin.ordinal() ||
                            nivelUsuario.ordinal() > nivelMax.ordinal()) {
                        mostrarError("Tu nivel (" + nivelUsuario +
                                ") no está dentro del rango especificado (" + nivelMin + " - " + nivelMax + ")");
                        return;
                    }
                }

                if (estrategiaSeleccionada.equals("Por cantidad de partidos")) {
                    if (cantidadPartidosField.getText().isEmpty()) {
                        mostrarError("Debe especificar la cantidad mínima de partidos");
                        return;
                    }
                }

                String ubicacionPartido = ubicacionField.getText().trim();
                String ubicacionUsuario = jugadorActual.getUbicacion();

                if (ubicacionUsuario == null || ubicacionUsuario.isEmpty()) {
                    mostrarError("Tu perfil no tiene ubicación configurada. Actualiza tu perfil antes de crear partidos");
                    return;
                }

                if (!ubicacionPartido.equalsIgnoreCase(ubicacionUsuario)) {
                    mostrarError("No puedes crear un partido en " + ubicacionPartido +
                            " porque tu ubicación registrada es " + ubicacionUsuario);
                    return;
                }

                String hora = horaField.getText();
                Date fechaHora = obtenerFechaHora(datePicker.getValue(), hora);
                if (fechaHora == null) return;

                IEstrategiaEmparejamiento estrategia;
                switch (estrategiaCombo.getValue()) {
                    case "Cualquier nivel":
                        estrategia = new EmparejamientoPorNivel();
                        break;
                    case "Nivel específico":
                        estrategia = new EmparejamientoPorNivel(nivelEspecificoCombo.getValue());
                        break;
                    case "Rango de nivel":
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
                        int cantidadMinima = Integer.parseInt(cantidadPartidosField.getText());
                        estrategia = new EmparejamientoPorHistorial(cantidadMinima);
                        break;
                    case "Por ubicación":
                        estrategia = new EmparejamientoPorCercania(ubicacionField.getText().trim());
                        break;
                    default:
                        estrategia = new EmparejamientoPorCercania("");
                }

                int cupoMaximo = deporteCombo.getValue().getCantidadJugadores();
                System.out.println("Cupo máximo establecido automáticamente: " + cupoMaximo +
                        " para " + deporteCombo.getValue().getNombre());

                Partido nuevoPartido = partidoController.crearPartido(
                        cupoMaximo,
                        deporteCombo.getValue(),
                        ubicacionField.getText(),
                        fechaHora,
                        duracionSpinner.getValue(),
                        estrategia,
                        jugadorActual
                );

                if (nuevoPartido != null) {
                    mostrarExito("Partido creado exitosamente");
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

    private Date obtenerFechaHora(LocalDate fecha, String hora) {
        try {
            String[] partes = hora.split(":");
            int horas = Integer.parseInt(partes[0]);
            int minutos = Integer.parseInt(partes[1]);

            LocalDateTime dateTime = fecha.atTime(horas, minutos);
            return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
        } catch (Exception e) {
            mostrarAlerta("Error al procesar la hora", Alert.AlertType.ERROR);
            return null;
        }
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

    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Alerta");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

private void mostrarDetallesPartido(Partido partido) {
    StringBuilder detalles = new StringBuilder();
    detalles.append("=== DETALLES DEL PARTIDO ===\n\n");
    detalles.append("Deporte: ").append(partido.getDeporte()).append("\n");
    detalles.append("Ubicación: ").append(partido.getUbicacion()).append("\n");
    detalles.append("Fecha y Hora: ").append(partido.getHorario()).append("\n");
    detalles.append("Duración: ").append(partido.getDuracion()).append(" horas\n");
    detalles.append("Estado: ").append(partido.getNombreEstado()).append("\n");
    detalles.append("Cupo: ").append(partido.getJugadores().size()).append("/")
            .append(partido.getCupoMaximo()).append("\n\n");
    
    // Agregar información de la estrategia de emparejamiento
    detalles.append("Estrategia de Emparejamiento: ").append(partido.getEmparejamiento().toString()).append("\n\n");

    // Lista de jugadores
    detalles.append("Jugadores:\n");
    for (Jugador j : partido.getJugadores()) {
        detalles.append("- ").append(j.getUsername())
                .append(" (Nivel: ").append(j.getNivel())
                .append(", Partidos jugados: ").append(j.getCantidadPartidosJugados())
                .append(")\n");
    }

    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Detalles del Partido");
    alert.setHeaderText(null);
    alert.setContentText(detalles.toString());
    alert.getDialogPane().setPrefWidth(500);
    
    // Hacer que el diálogo sea redimensionable
    alert.getDialogPane().setExpanded(true);
    alert.getDialogPane().setExpandableContent(null);
    
    alert.showAndWait();
}
}