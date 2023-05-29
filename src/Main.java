import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private ExplanatoryText explanatoryText;

    public void start(Stage primaryStage) {

        explanatoryText = new ExplanatoryText(this);
        primaryStage.setScene(new Scene(explanatoryText, 1100, 750));
        primaryStage.setTitle("Explanatory Text");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void startGame() {
        Guessword guessword = new Guessword();
        Stage gameStage = new Stage();
        try {
            guessword.start(gameStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}