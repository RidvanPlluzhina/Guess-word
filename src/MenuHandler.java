import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;

public class MenuHandler {

    public void mediumLevelAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText("Do you want to continue with 'Medium' level of game?");
        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.YES) {
            	 Platform.exit();
                
            }
        });
    }


       

	public void hardLevelAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText("Do you want to continue with 'Hard' level of game?");
        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.YES) {
            	 Platform.exit();
            }
        });
    }
}

	