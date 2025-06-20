
import javafx.application.Application;
import javafx.stage.Stage;
import ui.LoginScreen;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        new LoginScreen(primaryStage).show();
    }

    public static void main(String[] args) {
        // Tu código de Firebase y correos puede ir aquí ANTES de launch()
        System.out.println("Inicializando Firebase...");
        // ... código de Firebase ...

        // Iniciar JavaFX
        launch(args);
    }
}
