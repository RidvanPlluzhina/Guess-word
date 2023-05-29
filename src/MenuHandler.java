import javafx.event.ActionEvent;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

public class MenuHandler {

    public void mainIdea(ActionEvent event) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("");
        dialog.setHeaderText(
                "The main idea of this game is to guess the word given by the application, which is accompanied \n by a definition."
                        + "This procedure is repeated, with each word corresponding to a specific definition.\n"
                        + "The text fields are masked with a \"?\" sign. In each text box, the user is asked  "
                        + "to write a single\n letter. If a word has 5 letters, there will be 5 text boxes.\n");

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
        dialog.setHeaderText(
                "\"Start quiz\" - when this button is pressed the applications starts showing first word definition and a masked solution as described above.\n"
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
                + "The number of uncovered letters and\n"
                + "The number of correctly guessed words. ");

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
