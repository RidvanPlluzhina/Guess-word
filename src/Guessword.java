import java.util.HashMap;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;


public class Guessword extends Application {

    private static final int Vertical = 1500;
    private static final int Horizontal = 800;
    private static final Font FONT = new Font("Courier", 45);
    private static final Font FONT1 = new Font("Courier New", 36);
    private SimpleStringProperty word = new SimpleStringProperty();
    private SimpleIntegerProperty lettersToGuess = new SimpleIntegerProperty();
    private SimpleBooleanProperty playable = new SimpleBooleanProperty();
    private ObservableList<Node> letters;
    private HashMap<Character, Text> alphabet = new HashMap<Character, Text>();
    private ReadtheWord readtheWord = new ReadtheWord();
    private ReadtheDefinition readtheDefinition = new ReadtheDefinition();
 
    
    private int currentLetterIndex = 0;
    private SimpleIntegerProperty wrongGuesses = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty uncoveredLetters = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty correctlyGuessedWords = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty guessedWords = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty newLetterCount = new SimpleIntegerProperty(0);
    private StringBuilder wrongLetters = new StringBuilder();


    private Text wrongGuessesText;
    private Text uncoveredLettersText;
    private Text guessedLetters;
    

    private void startGame() {
        for (Text t : alphabet.values()) {
            t.setStrikethrough(false);
            t.setFill(Color.BLACK);
        }

        word.set(readtheWord.getNextWord().toUpperCase());

        int wordIndex = readtheWord.getCurrentIndex();
        int definitionIndex = wordIndex * 2;
        String definition = readtheDefinition.getDefinitionByIndex(definitionIndex);
        System.out.println("Definition: " + definition);

        lettersToGuess.set(word.length().get());
        letters.clear();
        for (char c : word.get().toCharArray()) {
            letters.add(new Letter(c));
        }
        currentLetterIndex = 0;

        uncoveredLetters.set(word.length().get()); 
    }


    public Parent createContent() {
        BackgroundFill backgroundFill = new BackgroundFill(Color.ANTIQUEWHITE, null, null);
        Background background = new Background(backgroundFill);
        
        MenuBar menuBar = new MenuBar();
        Menu gameLevelMenu = new Menu("Game Level");
        
        MenuItem menuItem1 = new MenuItem("Medium");
        menuItem1.setOnAction(event -> {
            MenuHandler menuHandler = new MenuHandler();
            menuHandler.mediumLevelAction(event);
        });
        
        MenuItem menuItem2 = new MenuItem("Hard");
        menuItem2.setOnAction(event -> {
        	MenuHandler menuHandler = new MenuHandler();
        	menuHandler.hardLevelAction(event);
        });
        gameLevelMenu.getItems().addAll(menuItem1,menuItem2);
        menuBar.getMenus().add(gameLevelMenu);
        
        VBox vBox = new VBox(70);
        
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setBackground(background);

        HBox rowLetters = new HBox();
        rowLetters.setAlignment(Pos.BASELINE_CENTER);
        letters = rowLetters.getChildren();

        playable.bind(lettersToGuess.greaterThan(0));
        playable.addListener((obs, old, newValue) -> {
            if (!newValue.booleanValue())
                stopGame();
        });

        Text definitionText = new Text();
        definitionText.setFont(FONT);
        word.addListener((obs, oldWord, newWord) -> {
            String definition = readtheDefinition.getNextDefinition();
            definitionText.setText("Definition: " + definition);
        });

        BorderPane alphabetContainer = new BorderPane();

        HBox rowAlphabet = new HBox(10);
        rowAlphabet.setAlignment(Pos.CENTER);
        for (char c = 'A'; c <= 'Z'; c++) {
            Text t = new Text(String.valueOf(c));
            t.setFont(FONT);
            alphabet.put(c, t);
            rowAlphabet.getChildren().add(t);
        }

        Rectangle alphabetBg = new Rectangle();
        alphabetBg.setFill(Color.TRANSPARENT);
        alphabetBg.setStroke(Color.BLACK);

        alphabetContainer.setCenter(rowAlphabet);
        alphabetContainer.setPadding(new Insets(10));

        // Create a border with top and bottom borders only
        BorderStroke borderStroke = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 0, 1, 0));
        Border border = new Border(borderStroke);

        alphabetContainer.setBorder(border);

        alphabetContainer.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        alphabetContainer.getChildren().add(alphabetBg);



        Button btnAgain = new Button("New Word");
        btnAgain.setOnAction(event -> startGame());
        Button btnNextLetter = new Button("New Letter");
        btnNextLetter.setOnAction(event -> showNextLetter());
        Button btnEnd = new Button ("End/New Quiz");
        btnEnd.setOnAction(event -> {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("End Quiz");
            alert.setHeaderText("Are you sure that you want to end the quiz?");

            ButtonType yesButton = new ButtonType("Yes");
            ButtonType noButton = new ButtonType("No");

            alert.getButtonTypes().setAll(yesButton, noButton);

            ButtonType newQuizButton = new ButtonType("New Quiz");

            alert.getButtonTypes().add(newQuizButton);

            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == yesButton) {
                    displayQuizResults();
                } else if (buttonType == newQuizButton) {
                    startNewQuiz();
                }
            });
        });

       


       
        
        HBox buttonContainer = new HBox(10);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.getChildren().addAll(btnAgain, btnNextLetter,btnEnd);
        

        Text wrongGuessesText = new Text();
        wrongGuessesText.setFont(FONT1);
        wrongGuessesText.textProperty().bind(Bindings.concat("Wrong Letters: ", wrongGuesses.asString()));
      
        Text uncoveredLettersText = new Text();
        uncoveredLettersText.setFont(FONT1);
        uncoveredLettersText.textProperty().bind(Bindings.concat("Uncovered Letters: ", uncoveredLetters.asString()));
        
        Text guessedLetters = new Text();
        guessedLetters.setFont(FONT1);
        guessedLetters.textProperty().bind(Bindings.concat("Guessed Letters: ", correctlyGuessedWords.asString()));
        
        Text guessedWordsText = new Text();
        guessedWordsText.setFont(FONT1);
        guessedWordsText.textProperty().bind(Bindings.concat("Guessed Words: ", guessedWords.asString()));
        
        Text newLetterCountText = new Text();
        newLetterCountText.setFont(FONT1);
        newLetterCountText.textProperty().bind(Bindings.concat("New Letter used: ", newLetterCount.asString()));

        
        HBox textContainer = new HBox(100);
        textContainer.setAlignment(Pos.CENTER);
        textContainer.getChildren().addAll(wrongGuessesText,guessedLetters, guessedWordsText);
        

        vBox.getChildren().addAll(
        		menuBar,
                rowAlphabet,
                uncoveredLettersText,
                //newLetterCountText,
                rowLetters,
                definitionText,
                buttonContainer,
                textContainer
        );

        return vBox;
    }
    

    
    private void startNewQuiz() {
      startGame();
      resetStats();
	}


    private void resetStats() {
    wrongGuesses.set(0);
    //uncoveredLetters.set(0);
    correctlyGuessedWords.set(0);
    newLetterCount.set(0);
    guessedWords.set(0);
}


	private void displayQuizResults() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Quiz Results");
        alert.setHeaderText("");
        alert.setContentText("Guessed Words: " + guessedWords.get()
                + "\nGuessed Letters: " + correctlyGuessedWords.get()
                + "\nWrong Letters: " + wrongGuesses.get()
                + "\nNew Letter used: " + newLetterCount.get()); 

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                Platform.exit();
            }
            
        });
    }



   
   

	
	private void showNextLetter() {
	    if (lettersToGuess.get() <= 0 || currentLetterIndex >= word.get().length()) {
	        return;
	    }

	    char nextLetter = word.get().charAt(currentLetterIndex);

	    Text t = alphabet.get(nextLetter);
	    if (!t.isStrikethrough()) {
	        t.setFill(Color.RED);
	        t.setStrikethrough(true);

	        int occurrences = countOccurrences(word.get(), nextLetter);
	        uncoveredLetters.set(uncoveredLetters.get() - occurrences);

	        for (Node n : letters) {
	            Letter letter = (Letter) n;
	            if (letter.isEqualTo(nextLetter)) {
	                lettersToGuess.set(lettersToGuess.get() - 1);
	                letter.show();
	            }
	        }

	        if (occurrences == 0) {
	            wrongLetters.append(currentLetterIndex + 1).append(", ");
	        }
	    }

	    currentLetterIndex++;
	    newLetterCount.set(newLetterCount.get() + 1);

	    if (lettersToGuess.get() <= 0) {
	        stopGame();
	    }

	    uncoveredLettersText.setText("Uncovered Letters: " + uncoveredLetters.get());
	    wrongGuessesText.setText("Wrong Letters: " + wrongLetters.toString());
	}








    private int countOccurrences(String word, char target) {
        int count = 0;
        for (char c : word.toCharArray()) {
            if (c == target) {
                count++;
            }
        }
        return count;
    }


    private void stopGame() {
        for (Node n : letters) {
            Letter letter = (Letter) n;
            letter.show();
        }

        if (uncoveredLetters.get() > 0) {
            wrongLetters.append("1, 2, 3, 4, 5, 6");
        }

        guessedWords.set(guessedWords.get() + 1);
        wrongGuessesText.setText("Wrong Letters: " + wrongLetters.toString());
    }


	
	

	private static class Letter extends StackPane {
	    private Rectangle bg = new Rectangle(50, 60);
	    private Text text;
	    private Text questionMark;
	    private StringProperty character = new SimpleStringProperty("");
	    private boolean revealed;

	    public Letter(char letter) {
	        bg.setFill(letter == ' ' ? Color.RED : Color.WHITE);
	        bg.setStroke(Color.RED);

	        text = new Text(String.valueOf(letter).toUpperCase());
	        text.setFont(FONT);
	        text.setVisible(false);

	        character.set(String.valueOf(letter).toUpperCase());

	        character.addListener((obs, oldVal, newVal) -> {
	            text.setText(newVal.toUpperCase());
	            text.setVisible(!newVal.isEmpty());
	        });

	        questionMark = createQuestionMark();
	        getChildren().addAll(bg, text, questionMark);
	        setAlignment(Pos.CENTER);

	        revealed = false;
	    }

	    private Text createQuestionMark() {
	        Text questionMark = new Text("?");
	        questionMark.setFont(Font.font("Arial", 30));
	        questionMark.setFill(Color.BLACK);
	        return questionMark;
	    }

	   

	    public void show() {
	        if (!revealed) {
	            RotateTransition rt = new RotateTransition(Duration.seconds(1.0), bg);
	            rt.setAxis(Rotate.Y_AXIS);
	            rt.setToAngle(180);
	            rt.setOnFinished(event -> {
	                text.setVisible(true);
	                getChildren().remove(questionMark);
	                revealed = true;
	            });
	            rt.play();
	        }
	    }

	    public boolean isEqualTo(char other) {
	        return text.getText().equals(String.valueOf(other).toUpperCase());
	    }
	}

    

    
    
    
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        scene.setOnKeyPressed((KeyEvent event) -> {
            if (event.getText().isEmpty())
                return;

            char pressed = event.getText().toUpperCase().charAt(0);
            if ((pressed < 'A' || pressed > 'Z') && pressed != '-')
                return;

            if (playable.get()) {
                Text t = alphabet.get(pressed);
                if (t.isStrikethrough())
                    return;

                t.setFill(Color.RED);
                t.setStrikethrough(true);

                boolean guessedCorrectly = false;
                for (Node n : letters) {
                    Letter letter = (Letter) n;
                    if (letter.isEqualTo(pressed)) {
                        lettersToGuess.set(lettersToGuess.get() - 1);
                        letter.show();
                        guessedCorrectly = true;
                        uncoveredLetters.set(uncoveredLetters.get() - 1); 
                    }
                }

                if (guessedCorrectly) {
                    correctlyGuessedWords.set(correctlyGuessedWords.get() + 1); 
                    guessedLetters.setText("Guessed Letters: " + correctlyGuessedWords.get());
                } else {
                    wrongGuesses.set(wrongGuesses.get() + 1); 
                    wrongGuessesText.setText("Wrong Guesses: " + wrongGuesses.get());
                }

                if (lettersToGuess.get() <= 0) {
                    stopGame();
                }
            }
        });

        primaryStage.setResizable(false);
        primaryStage.setWidth(Vertical);
        primaryStage.setHeight(Horizontal);
        primaryStage.setTitle("Project 2023");
        primaryStage.setScene(scene);
        primaryStage.show();
        startGame();
    }
}

   

