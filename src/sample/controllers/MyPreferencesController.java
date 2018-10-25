package sample.controllers;

import javafx.animation.ScaleTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.Reflection;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.Main;
import sample.objects.LoginsAndPasswords;

import java.io.File;
import java.util.Optional;
import java.util.Properties;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class MyPreferencesController {


    @FXML
    private VBox mainVbox;
    @FXML
    private ImageView bteLogo;
    @FXML
    private TextField bteLogin;
    @FXML
    private TextField btePass;
    @FXML
    private ImageView deltaLogo;
    @FXML
    private TextField deltaLogin;
    @FXML
    private TextField deltaPass;
    @FXML
    private TextField pathToDriver;
    @FXML
    private Button findDriver;
    @FXML
    private Button btnOk;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;

    private boolean changes = false;

    private Stage myPreferenceStage;

    private LoginsAndPasswords loginsAndPasswords = new LoginsAndPasswords();

    @FXML
    private void initialize() {

        loginsAndPasswords.getPref();
        bteLogin.setText(loginsAndPasswords.getBteLogin());
        btePass.setText(loginsAndPasswords.getBtePass());
        deltaLogin.setText(loginsAndPasswords.getDeltaLogin());
        deltaPass.setText(loginsAndPasswords.getDeltaPass());

        searchChanges();


        /*myPreferenceStage.setOnCloseRequest(event -> {
            myPreferenceStage.close();
        });*/

        // Анимация увеличения
        /*ScaleTransition scaleTransition = new ScaleTransition(Duration.valueOf(String.valueOf(300)), bteLogo);
        scaleTransition.setToX(1.3);
        scaleTransition.setToY(1.3);
        scaleTransition.play();*/

        //bteLogo.setEffect(new Reflection()); // Отражение


    }

    public Stage getMyPreferenceStage() {
        return myPreferenceStage;
    }

    public void setMyPreferenceStage(Stage primaryStage) {
        this.myPreferenceStage = primaryStage;
    }

    private void searchChanges(){
        bteLogin.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                changes = true;
            }
        });

        btePass.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                changes = true;
            }
        });

        deltaLogin.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                changes = true;
            }
        });

        deltaPass.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                changes = true;
            }
        });
    }

    private void showSaveDialog(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Сохранить изменения?");
        alert.setHeaderText("Сохранить изменения?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK){
            saveAction();
            myPreferenceStage.close();
            changes = false;
        } else {
            myPreferenceStage.close();
            changes = false;
        }
    }

    @FXML
    private void closeWithoutChanges(){

        /*preferences.put(PREF_BTE_LOGIN, "loginBTE");
        preferences.put(PREF_DELTA_LOGIN, "loginDelta");
        //preferences.node("sub_node").putBoolean("enabled", true);
        System.out.println(preferences.get(PREF_BTE_LOGIN, ""));
        System.out.println(preferences.get(PREF_DELTA_LOGIN, ""));*/
        myPreferenceStage.close();
    }

    //действие кнопки "Отмена", при первом старте
    public void firstStartCancelAction(){
        btnCancel.setOnAction(event -> {
            System.exit(0);
        });
    }

    @FXML
    private void saveAction() {
        loginsAndPasswords.setBteLogin(bteLogin.getText());
        loginsAndPasswords.setBtePass(btePass.getText());
        loginsAndPasswords.setDeltaLogin(deltaLogin.getText());
        loginsAndPasswords.setDeltaPass(deltaPass.getText());

        loginsAndPasswords.setPref(loginsAndPasswords.getBteLogin(), loginsAndPasswords.getBtePass(),
                loginsAndPasswords.getDeltaLogin(), loginsAndPasswords.getDeltaPass()); // запись настроек
    }

    @FXML
    private void okAction() {
        saveAction();
        myPreferenceStage.close(); // Добавить проверку на изменение настроек
    }

    @FXML
    private void closeAction() {
        if (changes == true){
            showSaveDialog();
        }else {
            myPreferenceStage.close();
        }
    }
}
