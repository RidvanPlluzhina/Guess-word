import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class ExplanatoryText extends VBox {

    public ExplanatoryText(Main main) {

    	setSpacing(100);
        setAlignment(Pos.CENTER);
        setBackground(new javafx.scene.layout.Background(new javafx.scene.layout.BackgroundFill(Color.ANTIQUEWHITE, null, null)));

        Text text1 = new Text("Welcome to the game \"Guess the word\"");
        text1.setUnderline(true);
        text1.setFont(Font.font("Arial", 40));
        text1.setTextAlignment(TextAlignment.CENTER);

        Text text = new Text(
        		  "This application is a game that is performed in the subject Introduction to Programming.\n"
                + "After running the application, the user will be able to see this explanatory text. The main\n"
                + "idea of this game is to guess the word given by the application, which is accompanied by a definition.\n"
                + "This procedure is repeated, with each word corresponding to a specific definition.\n"
                + "The text fields are masked with a \"?\" sign. In each text box, the user is asked to write\n"
                + "a single letter. If a word has 5 letters, there will be 5 text boxes.\n"
                + "The 'Next word' button will provide a new word, and the 'Next letter' button will reveal a random letter.\n"
                + "Each correctly guessed letter and wrong guesses will be marked. There is a counter indicating the number\n"
                + "of uncovered letters,which indicates how many letters are yet to be found in the complete word.\n"
                + "If the player wants to end the quiz, they can press the 'End Quiz' button, which will prompt them \n"
                + "for confirmation, and the result will be shown.");

        text.setFont(Font.font("Calibri", 25));
        text.setTextAlignment(TextAlignment.CENTER);

        getChildren().addAll(text1, text);

        Button startButton = new Button("Start Quiz");
        startButton.setOnAction(event -> {
            main.startGame();
            getScene().getWindow().hide();
        });
        getChildren().add(startButton);
    }
}





