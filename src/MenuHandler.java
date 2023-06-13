import javafx.event.ActionEvent;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;


public class MenuHandler {

	public void mainIdea(ActionEvent event) {
	    Dialog<ButtonType> dialog = new Dialog<>();
	    dialog.setTitle("");
	    dialog.setHeaderText("The main idea of the game is to challenge the user's vocabulary skills by presenting word definitions and asking them to guess the corresponding words.\n\nThe game provides clear instructions either in the main window or through a separate help dialogue.\n\nThe user can start the quiz by clicking the \"start quiz\" button, which displays word definitions and masked solutions.\n\nThroughout the game, the application shows three scores: the number of wrong guesses, the number of uncovered letters, and the number of correctly guessed words.\n\nTo make guesses, the user enters their answer in a text field, and the application provides feedback on whether the guess is correct or not. Once the word is correct there will be no '?' marks but the letters will be uncovered.\n\nIf the user needs assistance, they can click the \"new letter\" button to uncover a random letter of the target word.\n\nThe user can also request a new word and its definition by clicking the \"new word\" button, regardless of whether they have correctly guessed the current word. The scores remain unaffected.\n\nThe quiz ends when the user clicks the \"end quiz\" button, and the application provides a summary report with statistics such as the number of words tried, correctly guessed words, uncovered letters,\n wrong guesses, ratio of correctly guessed words to total guesses, and average number of letters uncovered per word.\n\nThe user can start a new quiz at any time by pressing the \"new quiz\" button, which requires confirmation. This action resets all three scores and displays the summary report of the previous quiz before starting a new one.\n\nAdditionally, the user can select the difficulty level of the game, which determines the maximum length of the words to be guessed.\n\nWord definitions are stored in a file and are randomly presented during the game to ensure that the same definition is not repeated in a single session.\n\nTo make the game much easier for the user,every time the new Word is shown,a random letter is shown as well.");

	    ButtonType okButton = new ButtonType("OK", ButtonData.OK_DONE);
	    dialog.getDialogPane().getButtonTypes().add(okButton);

	    dialog.showAndWait().ifPresent(buttonType -> {
	        if (buttonType == okButton) {
	            dialog.close();
	        }
	    });
	}



    
	public void buttonsAction(ActionEvent event) {
	    Dialog<ButtonType> dialog = new Dialog<>();
	    dialog.setTitle("");
	    dialog.setHeaderText("\"Start quiz\" - when this button is pressed the applications starts showing first word definition and a masked solution as described above.\n"
	    		+ "\"New letter\" - when this button is pressed the user can ask to uncover a random letter, if there is still a letter to uncover.\n"
	    		+ "\"New word\" - when this button is pressed the application shows a new word definition and a corresponding new masked word,to help the user to make a correct gues.\n"
	    		+ "\"End quiz\" - when this button is pressed the application asks for confirmation and if the user confirms it gives a summary report of the executed quiz.\n"
	    		+ "\"New quiz\" - when this button is pressed the application must ask user confirmation,and it will start a new quiz,this button can be pressed anytime.\n");

	    ButtonType okButton = new ButtonType("OK", ButtonData.OK_DONE);
	    dialog.getDialogPane().getButtonTypes().add(okButton);

	    dialog.showAndWait().ifPresent(buttonType -> {
	        if (buttonType == okButton) {
	            dialog.close();
	        }
	    });
	}
	public void scoreAction(ActionEvent event) {
	    Dialog<ButtonType> dialog = new Dialog<>();
	    dialog.setTitle("");
	    dialog.setHeaderText("The application always shows three scores :\n"
	    		+ "The number of wrong guesses,\n"
	    		+ "The number of uncovered letters,\n"
	    		+ "The number of correctly guessed words and\n"
	    		+ "The ratio between tried and guessed words.");
	    

	    ButtonType okButton = new ButtonType("OK", ButtonData.OK_DONE);
	    dialog.getDialogPane().getButtonTypes().add(okButton);

	    dialog.showAndWait().ifPresent(buttonType -> {
	        if (buttonType == okButton) {
	            dialog.close();
	        }
	    });
	}
	
	public void wordsdefAction(ActionEvent event) {
	    Dialog<ButtonType> dialog = new Dialog<>();
	    dialog.setTitle("");
	    dialog.setHeaderText("The word definitions are stored in a file,they are not used twice\n"
	    		+ "(unless the application already used all of them).They are used in a random order.");

	    ButtonType okButton = new ButtonType("OK", ButtonData.OK_DONE);
	    dialog.getDialogPane().getButtonTypes().add(okButton);

	    dialog.showAndWait().ifPresent(buttonType -> {
	        if (buttonType == okButton) {
	            dialog.close();
	        }
	    });
	}
}

	