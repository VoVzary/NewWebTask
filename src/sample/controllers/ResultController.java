package sample.controllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.objects.AnimationsAndEffects;

public class ResultController {

    @FXML
    private TextField resultText;

    @FXML
    private ImageView logoCopy;

    private Stage resultStage;

    private AnimationsAndEffects animationsAndEffects = new AnimationsAndEffects();

    @FXML
    private void initialize(){

        animationsAndEffects.reflection(logoCopy);

        //НЕ УВЕЛИЧИВАЕТСЯ
        logoCopy.setOnMouseReleased(event -> {
            animationsAndEffects.growAndShrink(logoCopy, 1.3);
        });

        logoCopy.setOnMouseExited(event -> {
            animationsAndEffects.growAndShrink(logoCopy, 1);
        });

        logoCopy.setOnMouseReleased(event -> {
            animationsAndEffects.greyEffectBritness(-0.5);

            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.millis(200), new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            animationsAndEffects.getReflection().setInput(null);
                        }
                    }));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        });



    }


    public TextField getResultText() {
        return resultText;
    }

    public void setResultText(TextField resultText) {
        this.resultText = resultText;
    }

    public ImageView getLogoCopy() {
        return logoCopy;
    }

    public void setLogoCopy(ImageView logoCopy) {
        this.logoCopy = logoCopy;
    }

    public Stage getResultStage() {
        return resultStage;
    }

    public void setResultStage(Stage resultStage) {
        this.resultStage = resultStage;
    }
}
