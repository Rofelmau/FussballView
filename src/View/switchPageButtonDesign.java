package View;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

public interface switchPageButtonDesign {

    default void setDefaultDesign(Button b){
        defaultStyle(b);
       // b.setPrefWidth(150);
        b.setOnMouseEntered(event -> onMouseOver(b));
        b.setOnMouseExited(event -> onMouseExit(b));
        b.setOnMousePressed(event -> onMousePressed(b));
        b.setOnMouseReleased(event -> onMouseReleased(b));
        defaultStyle(b);
    }

    default void defaultStyle(Button b){
        b.setStyle("-fx-background-color: transparent;" );
    }

    default void onMouseOver(Button b){
        double currentSize = b.getFont().getSize();
        Font newFont = new Font(currentSize+0.5);
        b.setFont(newFont);
        b.setStyle("-fx-background-color:transparent;");
        b.getScene().getRoot().setCursor(Cursor.HAND);
    }

    default void onMouseExit(Button b){
        double currentSize = b.getFont().getSize();
        Font newFont = new Font(currentSize-0.5);
        b.setFont(newFont);
        b.setStyle("-fx-background-color:transparent;");
        b.getScene().getRoot().setCursor(Cursor.DEFAULT);
    }
    default void onMouseReleased(Button b){
        double currentSize = b.getFont().getSize();
        Font newFont = new Font(currentSize+1);
        b.setFont(newFont);
        defaultStyle(b);
    }

    default void onMousePressed(Button b){
        double currentSize = b.getFont().getSize();
        Font newFont = new Font(currentSize-1);
        b.setFont(newFont);
        defaultStyle(b);
    }
}
