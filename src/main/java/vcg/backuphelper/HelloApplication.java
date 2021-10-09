package vcg.backuphelper;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.Image;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        Group group = new Group();
        VBox dragTarget = new VBox();
        dragTarget.setStyle("-fx-background-color:#FFB7DE");
        group.getChildren().add(dragTarget);
        Label label = new Label("Drag a file to me.");
        TextArea infoText = new TextArea();
        infoText.setEditable(false);
        infoText.setPrefWidth(600);
        infoText.setPrefHeight(400);
        dragTarget.getChildren().addAll(label, infoText);
        drag(dragTarget, infoText);
        stage.setTitle("BackupHelper");
        stage.getIcons().add(new Image(getClass().getResource("icon.jpg").toString()));
        stage.setScene(new Scene(group));
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void drag(Node dragTarget, TextInputControl infoText) {
        dragTarget.setOnDragOver(event -> {
            if (event.getGestureSource() != dragTarget && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });
        dragTarget.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            boolean success = false;
            if (dragboard.hasFiles()) {
                List<File> files = dragboard.getFiles();
                StringBuilder path = new StringBuilder();
                files.forEach(file -> path.append(file.getPath()).append("\n"));
                infoText.setText(path.toString());
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }
}