import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class ExplanatoryText extends BorderPane {

    public ExplanatoryText(Main main) {

        MenuBar menuBar = new MenuBar();
        Menu gameLevelMenu = new Menu("Help dialogue");

        MenuItem menuItem1 = new MenuItem("Game idea");
        menuItem1.setOnAction(event -> {
            MenuHandler menuHandler = new MenuHandler();
            menuHandler.mainIdea(event);
        });

        MenuItem menuItem2 = new MenuItem("Buttons");
        menuItem2.setOnAction(event -> {
            MenuHandler menuHandler = new MenuHandler();
            menuHandler.buttonsAction(event);
        });

        MenuItem menuItem3 = new MenuItem("Scores");
        menuItem3.setOnAction(event -> {
            MenuHandler menuHandler = new MenuHandler();
            menuHandler.scoreAction(event);
        });

        MenuItem menuItem4 = new MenuItem("Words and Definitions");
        menuItem4.setOnAction(event -> {
            MenuHandler menuHandler = new MenuHandler();
            menuHandler.wordsdefAction(event);
        });

        gameLevelMenu.getItems().addAll(menuItem1, menuItem2, menuItem3, menuItem4);
        menuBar.getMenus().add(gameLevelMenu);

        setTop(menuBar);

        VBox centerContent = new VBox();
        centerContent.setSpacing(100);
        centerContent.setAlignment(Pos.CENTER);
        setBackground(new javafx.scene.layout.Background(
                new javafx.scene.layout.BackgroundFill(Color.ANTIQUEWHITE, null, null)));

        Text text1 = new Text("Welcome to the game \"Guess the word\"");
        text1.setUnderline(true);
        text1.setFont(Font.font("Arial", 40));
        text1.setTextAlignment(TextAlignment.CENTER);

        Text text = new Text(
                "This application is a game that is performed in the subject Introduction to Programming.\n"
                        + "After running the application, the user will be able to see this explanatory text of the game.\n"
                        + "For further clarification, we strongly suggest to check the menubar called \"Help dialogue\",\n"
                        + "which is located at the top of the screen.To start with the quiz press the button \"Start Quiz\".");

        text.setFont(Font.font("Calibri", 25));
        text.setTextAlignment(TextAlignment.CENTER);

        centerContent.getChildren().addAll(text1, text);

        Button startButton = new Button("Start Quiz");
        startButton.setOnAction(event -> {
            main.startGame();
            getScene().getWindow().hide();
        });
        centerContent.getChildren().add(startButton);

        setCenter(centerContent);
    }
}
