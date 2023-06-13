import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import javafx.scene.layout.BorderPane;
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


    public class Guessword extends Application {

	/*This code declares variables for game dimensions,fonts,properties,lists, and graphical elements used in Guessword class.
	  These variables store game state, statistics, and interface elements.*/
    	
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
    private SimpleIntegerProperty uncoveredLettersperWord = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty correctlyGuessedLetters = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty guessedWords = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty newLetterCount = new SimpleIntegerProperty(0);
    private StringBuilder wrongLetters = new StringBuilder();
    private List<String> wordsTried = new ArrayList<>();
    private Text wrongGuessesText;
    private Text uncoveredLettersText;
    private Text uncoveredLettersperWordText;
    private Text guessedLetters;
   
    
    /*The method resets the appearance of the alphabet letters, sets a new word to guess, retrieves the definition associated with the word, 
      updates the number of letters to guess, creates graphical elements for each letter in the word, 
      sets the initial state of uncovered letters, and randomly reveals a letter in the word.*/
    
    private void startGame() {
        for (Text t : alphabet.values()) {
            t.setStrikethrough(false);
            t.setFill(Color.BLACK);
        }

        word.set(readtheWord.getNextWord().toUpperCase());
        wordsTried.add(word.get());

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
        uncoveredLettersperWord.set(uncoveredLettersperWord.get() + uncoveredLetters.get());

        int randomIndex = (int) (Math.random() * word.get().length());
        showLetterAtIndex(randomIndex);
    }
    
    
    /*This code displays a letter at a specific index, updates its visual properties, counts occurrences, 
      tracks game progress, and updates the UI with relevant information.*/
        
    private void showLetterAtIndex(int index) {
        if (index < 0 || index >= word.get().length()) {
            return;
        }
        
        char letter = word.get().charAt(index);
        Text t = alphabet.get(letter);
        t.setFill(Color.RED);
        t.setStrikethrough(true);

        int occurrences = countOccurrences(word.get(), letter);
        uncoveredLetters.set(uncoveredLetters.get() - occurrences);
        uncoveredLettersperWord.set(uncoveredLettersperWord.get() - occurrences);

        for (Node n : letters) {
            Letter letterObj = (Letter) n;
            if (letterObj.isEqualTo(letter) && !letterObj.isShown()) {
                lettersToGuess.set(lettersToGuess.get() - 1);
                letterObj.show();
            }
        }

        if (occurrences == 0) {
            wrongLetters.append(letter).append(", ");
            wrongGuesses.set(wrongGuesses.get() + 1);
        } else {
            correctlyGuessedLetters.set(correctlyGuessedLetters.get() + occurrences);
        }

        newLetterCount.set(newLetterCount.get() + 1);

        uncoveredLettersText.setText("Uncovered Letters: " + uncoveredLetters.get());
        uncoveredLettersperWordText.setText("Uncovered Letters per Word: " + uncoveredLettersperWord.get());
        wrongGuessesText.setText("Wrong Letters: " + wrongLetters.toString());

        
    }



    
    /*This code sets up the UI for the quiz, including the background, layout containers, 
      buttons for starting a new word and showing the next letter, and handling confirmation dialogs for ending or starting a new quiz.*/
    
        public Parent createContent() {
        BackgroundFill backgroundFill = new BackgroundFill(Color.ANTIQUEWHITE, null, null);
        Background background = new Background(backgroundFill);
         
        VBox vBox = new VBox(90);
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

        


        Button btnAgain = new Button("New Word");
        btnAgain.setOnAction(event -> startGame());

        Button btnNextLetter = new Button("New Letter");
        btnNextLetter.setOnAction(event -> showNextLetter());

        Button btnEnd = new Button("End Quiz");
        btnEnd.setOnAction(event -> {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("End Quiz");
            alert.setHeaderText("Are you sure that you want to end the quiz?");

            ButtonType yesButton = new ButtonType("Yes");
            ButtonType noButton = new ButtonType("No");

            alert.getButtonTypes().setAll(yesButton, noButton);

            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == yesButton) {
                    displayQuizResults();
                }
            });
        });
        
        Button btnNewQuiz = new Button("New Quiz");
        btnNewQuiz.setOnAction(event -> {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("New Quiz");
            alert.setHeaderText("Are you sure that you want to start a new quiz?");

            ButtonType yesButton = new ButtonType("Yes");
            ButtonType noButton = new ButtonType("No");

            alert.getButtonTypes().setAll(yesButton, noButton);

            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == yesButton) {
                	displayQuizResults1();
                }
            });
        });


	    

        /*This code configures the UI elements for displaying game information such as wrong guesses, 
          uncovered letters, guessed letters, guessed words.*/
        
        HBox buttonContainer = new HBox(10);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.getChildren().addAll(btnAgain,btnNextLetter,btnNewQuiz,btnEnd);
        

        
        this.wrongGuessesText = new Text();
        wrongGuessesText.setFont(FONT1);
        wrongGuessesText.textProperty().bind(Bindings.concat("Wrong Letters: ", wrongGuesses.asString()));
        
        this.uncoveredLettersText = new Text(); 
        uncoveredLettersText.setFont(FONT1);
        uncoveredLettersText.textProperty().bind(Bindings.concat("Uncovered Letters: ", uncoveredLetters.asString()));
       
        Text uncoveredLettersperText = new Text();
        uncoveredLettersperText.setFont(FONT1);
        uncoveredLettersperText.textProperty().bind(Bindings.concat("Uncovered Letters per Word: ", uncoveredLettersperWord.asString()));
        
        Text guessedLetters = new Text();
        guessedLetters.setFont(FONT1);
        guessedLetters.textProperty().bind(Bindings.concat("Guessed Letters: ", correctlyGuessedLetters.asString()));
        
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
                rowAlphabet,
                uncoveredLettersText,
                //uncoveredLettersperText,
                rowLetters,
                definitionText,
                buttonContainer,
                textContainer
        );

        return vBox;
    }
	public void startNewQuiz() {
    startGame();
    resetStats();
	}


	private void resetStats() {
	    wrongGuesses.set(0);
	    correctlyGuessedLetters.set(0);
	    newLetterCount.set(0);
	    //guessedWords.set(0);
	    wordsTried.clear();
	    uncoveredLetters.set(0);

	}


    /*This code calculates and displays the quiz results, including the number of words tried, correctly guessed words, 
	  total uncovered letters, number of wrong guesses, ratio of correct guesses, and average uncovered letters per word.*/
	
	public void displayQuizResults() {
	    int totalWordsTried = wordsTried.size();
	    int totalUncoveredLetters = uncoveredLettersperWord.get(); // Use the value from the property
	    int totalGuessedWords = guessedWords.get();
	    
	    double ratioOfGuesses = (double) totalGuessedWords / totalWordsTried * 100;
	    double averageUncoveredLetters = (double) totalUncoveredLetters / totalWordsTried;

	    Alert alert = new Alert(AlertType.INFORMATION);
	    alert.setTitle("Quiz Results");
	    alert.setHeaderText("");
	    alert.setContentText("Number of words tried: " + totalWordsTried
	            + "\nNumber of words correctly guessed: " + totalGuessedWords
	            + "\nTotal number of uncovered letters: " + totalUncoveredLetters 
	            + "\nNumber of wrong guesses: " + (totalWordsTried - totalGuessedWords)
	            + "\nRatio of correctly guessed words by nr of guesses: " + ratioOfGuesses + "%"
	            + "\nAverage uncovered letters per word tried: " + averageUncoveredLetters
	            + "\n\n\n\n\n");
	           //+ "\n\nGuessed Letters: " + correctlyGuessedLetters.get()
               // + "\nWrong Letters: " + wrongGuesses.get()
              //+ "\nNew Letters used: " + newLetterCount.get());

	    alert.showAndWait().ifPresent(buttonType -> {
	        if (buttonType == ButtonType.OK) {
	            Platform.exit();

	        }
	    });
	}



	public void displayQuizResults1() {
		int totalWordsTried = wordsTried.size();
	    int totalUncoveredLetters = uncoveredLettersperWord.get(); // Use the value from the property
	    int totalGuessedWords = guessedWords.get();
	    
	    double ratioOfGuesses = (double) totalGuessedWords / totalWordsTried * 100;
	    double averageUncoveredLetters = (double) totalUncoveredLetters / totalWordsTried;

	    Alert alert = new Alert(AlertType.INFORMATION);
	    alert.setTitle("Quiz Results");
	    alert.setHeaderText("");
	    alert.setContentText("Number of words tried: " + totalWordsTried
	            + "\nNumber of words correctly guessed: " + totalGuessedWords
	            + "\nTotal number of uncovered letters: " + totalUncoveredLetters 
	            + "\nNumber of wrong guesses: " + (totalWordsTried - totalGuessedWords)
	            + "\nRatio of correctly guessed words by nr of guesses: " + ratioOfGuesses + "%"
	            + "\nAverage uncovered letters per word tried: " + averageUncoveredLetters
	            + "\n\n\n\n\n");
	           //+ "\n\nGuessed Letters: " + correctlyGuessedLetters.get()
               // + "\nWrong Letters: " + wrongGuesses.get()
              //+ "\nNew Letters used: " + newLetterCount.get());

	    alert.showAndWait().ifPresent(buttonType -> {
	        if (buttonType == ButtonType.OK) {
	        	startGame();
	        	
	        }
	    });
	}


	/*This code displays the next letter in the game by iterating through the word. It updates the letter's visual properties, 
	  counts occurrences,reveals it in the UI, and adjusts game counters. If all letters are guessed, the game stops.*/
	
	private void showNextLetter() {
	    if (lettersToGuess.get() <= 0 || currentLetterIndex >= word.get().length()) {
	        return;
	    }

	    char nextLetter = '\0';

	    for (int i = currentLetterIndex; i < word.get().length(); i++) {
	        char letter = word.get().charAt(i);
	        Text t = alphabet.get(letter);

	        if (!t.isStrikethrough()) {
	            nextLetter = letter;
	            currentLetterIndex = i;
	            break;
	        }
	    }

	    if (nextLetter == '\0') {
	        return;
	    }

	    Text t = alphabet.get(nextLetter);
	    t.setFill(Color.RED);
	    t.setStrikethrough(true);

	    int occurrences = countOccurrences(word.get(), nextLetter);
	    uncoveredLetters.set(uncoveredLetters.get() - occurrences);
	    uncoveredLettersperWord.set(uncoveredLettersperWord.get() - occurrences);


	    for (Node n : letters) {
	        Letter letter = (Letter) n;
	        if (letter.isEqualTo(nextLetter) && !letter.isShown()) {
	            lettersToGuess.set(lettersToGuess.get() - 1);
	            letter.show();
	        }
	    }

	    if (occurrences == 0) {
	        wrongLetters.append(nextLetter).append(", ");
	    }

	    newLetterCount.set(newLetterCount.get() + 1);

	    if (lettersToGuess.get() <= 0) {
	        stopGame();
	    }

	    

	    uncoveredLettersText.setText("Uncovered Letters: " + uncoveredLetters.get());
	    uncoveredLettersperWordText.setText("Uncovered Letters per Word: " + uncoveredLetters.get());
	    wrongGuessesText.setText("Wrong Letters: " + wrongLetters.toString());
	}


	//This code counts the occurrences of a specific character (target) in a given word.
	
    private int countOccurrences(String word, char target) {
        int count = 0;
        for (char c : word.toCharArray()) {
            if (c == target) {
                count++;
            }
        }
        return count;
    }

    
    //This code stops the game by revealing all the letters in the UI. If there are still uncovered letters in the game.
    
    private void stopGame() {
        for (Node n : letters) {
            Letter letter = (Letter) n;
            letter.show();
        }

        if (uncoveredLetters.get() > 0) {
            wrongLetters.append("1, 2, 3, 4, 5, 6");
        }
        if (uncoveredLettersperWord.get() > 0) {
            wrongLetters.append("1, 2, 3, 4, 5, 6");
        }

        guessedWords.set(guessedWords.get() + 1);
        wrongGuessesText.setText("Wrong Letters: " + wrongLetters.toString());

        
        int count = 0;
        for (Node n : letters) {
            Letter letter = (Letter) n;
            if (letter.isShown()) {
                count++;
            }
        }
        System.out.println("Number of uncovered letters: " + count);
    }

       
    /*This code defines a custom Letter class representing a letter in the game. It includes visual elements like 
     a background rectangle,text, and question mark, along with properties for character value.*/
    
	private class Letter extends StackPane {
	    private Rectangle bg = new Rectangle(50, 60);
	    private Text text;
	    private Text questionMark;
	    private StringProperty character = new SimpleStringProperty("");
	    private boolean revealed;
	    private boolean shown;
	    
	    public boolean isShown() {
	        return shown;
	    }
	
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
	            RotateTransition rt = new RotateTransition(Duration.seconds(0.8), bg);
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

    

    
    
    /*The start method creates the game scene and handles key presses. It checks if a valid letter key is pressed, 
	updates the letter's visual state, adjusts counts for correct guesses,uncovered letters, and wrong guesses, 
	and stops the game if all letters are guessed */
	
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
	                        uncoveredLetters.set(uncoveredLetters.get()  - 1);
	                        uncoveredLettersperWord.set(uncoveredLettersperWord.get()  - 1); 

	                    }
	                }

	                if (guessedCorrectly) {
	                    correctlyGuessedLetters.set(correctlyGuessedLetters.get() + 1); 
	                    guessedLetters.setText("Guessed Letters: " + correctlyGuessedLetters.get());
	                } else {
	                    wrongGuesses.set(wrongGuesses.get() + 1); 
	                    wrongGuessesText.setText("Wrong Guesses: " + wrongGuesses.get());
	                }

	                if (lettersToGuess.get() <= 0) {
	                    stopGame();
	                }
	            }
	        });
	
	        
	    //Configuring the primary stage and scene, displaying the window, and starting the game.

        primaryStage.setResizable(false);
        primaryStage.setWidth(Vertical);
        primaryStage.setHeight(Horizontal);
        primaryStage.setTitle("Project 2023");
        primaryStage.setScene(scene);
        primaryStage.show();
        startGame();
       
    }
}

   

