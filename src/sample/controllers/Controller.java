package sample.controllers;

import javafx.animation.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import sample.Main;
import sample.objects.AnimationsAndEffects;
import sample.objects.Data;
import sample.objects.LoginsAndPasswords;

import javax.xml.transform.Source;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.TimeUnit;


public class Controller {

    @FXML
    private VBox mainBox;

    @FXML
    private Button close;

    @FXML
    private TextField sn;

    @FXML
    private TextArea contact;

    @FXML
    private TextArea defect;

    @FXML
    private AnchorPane imagePane;

    @FXML
    private ImageView logoBte;

    @FXML
    private ImageView logoDelta;

    @FXML
    private ImageView logoPref;

    @FXML
    private ImageView logoClose;

    private Stage primaryStage;

    private WebDriver driver;

    private Robot robot;

    {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    private Data data = new Data();

    private boolean snFlag = false; // флаг, что в поле "Серийный номер", ничего не вводили

    private boolean defectFlag = false; // флаг, что в поле "Неисправность", ничего не вводили

    private boolean contactFlag = false; // флаг, что в поле "Контактное лицо", ничего не вводили

    private boolean firstStartFlag = true; // флаг, что настройки пустые

    private LoginsAndPasswords loginsAndPasswords = new LoginsAndPasswords();

    private AnimationsAndEffects animationsAndEffects = new AnimationsAndEffects();

    private ColorAdjust greyEffect = new ColorAdjust();

    private Tooltip tooltip = new Tooltip(); // Всплывающие подсказки


//    PauseTransition pause = new PauseTransition(Duration.seconds(2));

    private Set<String> handles;

    private ArrayList<String> handlesList;

    DropShadow dropShadow = new DropShadow();


    @FXML
    private void initialize(){

        //Делаем неактивными кнопки сервисных компаний при загрузке приложения
        imagePane.setDisable(true);
        greyEffect.setContrast(-1);
        imagePane.setEffect(greyEffect);


        loginsAndPasswords.getPref(); // загрузка логинов и паролей

        firstStart();

        loginsAndPasswords.getPref(); // загрузка логинов и паролей

        firstStartFlag = false;

        onOfButton(); // отключение кнопок, пока не будут заполнены поля

        //launchBrows(); // запуск браузера

        sn.setEffect(dropShadow);
        defect.setEffect(dropShadow);
        contact.setEffect(dropShadow);

        animationButton(logoPref);
        animationButton(logoClose);
        animationButton(logoBte);
        animationButton(logoDelta);

        if (primaryStage.isShowing()){
            launchBrows();
        }

        //launchBrows(); // запуск браузера
        //robot = new Robot();
        robotNewTab();

        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);

        freshBtePage();

        //deltaTest();

    }



    private void robotNewTab(){
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_T);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_T);
    }
    // анимация и эффекты на кнопки
    private void animationButton(ImageView logo){

        // эффект отражения
        Reflection reflection = new Reflection();

        // добавляем эффект отражения на картинку
        logo.setEffect(reflection);



        //добавляем анимацию увеличения картинки
        logo.setOnMouseEntered(event -> {
            ScaleTransition grow = new ScaleTransition(Duration.millis(300), logo);
            grow.setToX(1.3);
            grow.setToY(1.3);
            grow.playFromStart();
        });

        //добавляем анимацию уменьшения картинки
        logo.setOnMouseExited(event -> {
            ScaleTransition shrink = new ScaleTransition(Duration.millis(300), logo);
            shrink.setToX(1);
            shrink.setToY(1);
            shrink.playFromStart();
        });

        //добавляем эффект затемнения и восстановления цвета при нажатии
        ColorAdjust effectPressed = new ColorAdjust();
        effectPressed.setBrightness(-0.5);
        logo.setOnMouseReleased(event -> {

            reflection.setInput(effectPressed);

            switch (logo.getId().toString()){
                case "logoBte":
                    bteAction();
                    break;
                case "logoDelta":
                    deltaAction();
                    break;
                case "logoPref":
                    prefAction();
                    break;
                case "logoClose":
                    closeAction();
                    break;
            }
            /*if (logo.getId().toString().equals(logoBte.getId().toString())){
                bteAction();
            } else if (logo.getId().toString().equals(logoDelta.getId().toString())){
                deltaAction();
            } else if (logo.getId().toString().equals(logoPref.getId().toString())){
                prefAction();
            } else if (logo.getId().toString().equals(logoClose.getId().toString())){
                closeAction();
            }*/
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.millis(200), new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            reflection.setInput(null);
                        }
                    }));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();

        });


    }

    private void onOfButton(){



        sn.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                snFlag = true;

                if (defectFlag && contactFlag && !sn.getText().equals("") && !defect.getText().equals("") && !contact.getText().equals("")){

                    imagePane.setDisable(false);
                    greyEffect.setContrast(0);
                    imagePane.setEffect(greyEffect);

                } else {

                    imagePane.setDisable(true);
                    greyEffect.setContrast(-1);
                    imagePane.setEffect(greyEffect);
                }
            }
        });

        defect.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                defectFlag = true;

                if (snFlag && contactFlag && !sn.getText().equals("") && !defect.getText().equals("") && !contact.getText().equals("")){
                    imagePane.setDisable(false);
                    greyEffect.setContrast(0);
                } else {
                    imagePane.setDisable(true);
                    greyEffect.setContrast(-1);
                }
            }
        });

        contact.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                contactFlag = true;

                if (snFlag && defectFlag && !sn.getText().equals("") && !defect.getText().equals("") && !contact.getText().equals("")){
                    imagePane.setDisable(false);
                    greyEffect.setContrast(0);
                } else {
                    imagePane.setDisable(true);
                    greyEffect.setContrast(-1);
                }
            }
        });
    }

    // Обращение к драйверу и запуск браузера
    private void launchBrows(){
        System.setProperty("webdriver.chrome.driver", "src/sample/driver/chromedriver.exe"); //Обращение к драйверу
        driver = new ChromeDriver();
        //driver.manage().window().maximize();
    }

    private void freshBtePage(){

        driver.get("https://service.bte-atm.ru/request");
        driver.findElement(By.id("email")).sendKeys(loginsAndPasswords.getBteLogin());
        driver.findElement(By.id("password")).sendKeys(loginsAndPasswords.getBtePass());
        driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div/div/form/button")).click();
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);

        freshDeltaPage();

    }

    private void freshDeltaPage (){

        handles = driver.getWindowHandles();
        handlesList = new ArrayList<String>(handles);
        System.out.println(handlesList.size());

        try {
            driver.switchTo().window(handlesList.get(1));
        } catch (IndexOutOfBoundsException e){
            driverClose();
            initialize();
            //rebootApp();
        }

        driver.get("http://nsd.delta-systems.ru:8090/sd/");

    }

    public void driverClose(){
        driver.quit();
    }

    @FXML
    private void bteAction(){

        driver.switchTo().window(handlesList.get(0));

        setDataToData();

        driver.findElement(By.xpath("/html/body/nav/div[2]/ul[1]/li[2]/a")).click();
        driver.findElement(By.id("request-sn")).sendKeys(data.getSnData());
        driver.findElement(By.xpath("//*[@id=\"form-close\"]/div[1]/div[1]/div/ul")).click();
        driver.findElement(By.id("request-description")).sendKeys(data.getDefectData());
        driver.findElement(By.id("request-contactInfo")).sendKeys(data.getContactData());

        System.out.println(driver.findElement(By.xpath("/html/body/div[2]/div/table/tbody/tr[1]/td[2]/ul/li[2]/text()"))); //номер самой верхней заявки, страница обновляется каждые 30 сек
        System.out.println(driver.findElement(By.xpath("/html/body/div[2]/div/table/tbody/tr[1]/td[3]/ul/li[8]/text()"))); //серийник самой верхней заявки



        clearForm();

    }

    @FXML
    private void deltaAction(){

        //driver.switchTo().window(handlesList.get(1)); ВКЛЮЧИТЬ ПОСЛК ТЕСТА
        setDataToData();

        try {
            driver.findElement(By.id("username")).sendKeys(loginsAndPasswords.getDeltaLogin()); // Логин
            driver.findElement(By.id("password")).sendKeys(loginsAndPasswords.getDeltaPass()); // Пароль
            driver.findElement(By.xpath("//*[@id=\"command\"]/input")).click(); // Нажатие кнопки входа

            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

            try {
                resultWindow();
            } catch (IOException e) {
                e.printStackTrace();
            }

            driver.findElement(By.id("gwt-debug-addSC.f2e9fe69-1336-008d-0000-0000004904bd")).click(); // Нажатие кнопки "Добавить запрос"
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            driver.findElement(By.xpath("//*[@id=\"gwt-debug-agreementServiceProperty-value\"]/div/input")).click(); // выбор соглашения/услуги
            driver.manage().timeouts().setScriptTimeout(20, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            driver.findElement(By.id("gwt-debug-agreementServiceProperty-value")).click(); // выбор соглашения/услуги

            progPause(1000);

            driver.manage().timeouts().setScriptTimeout(20, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            driver.findElement(By.id("agreement$37101:slmService$37603")).click(); // выбор "обслуживание банкоматов"
            driver.manage().timeouts().setScriptTimeout(20, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            driver.findElement(By.id("gwt-debug-caseProperty-value")).click(); // выбор типа запроса
            driver.manage().timeouts().setScriptTimeout(20, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            driver.findElement(By.id("serviceCall$remont")).click(); // выбор "Запрос на ремонт через NSD/web-портал"


            progPause(2000);

            driver.manage().timeouts().setScriptTimeout(20, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            driver.findElement(By.id("gwt-debug-ATMobject-value")).click(); // выбор поля "Банкомат"
            driver.manage().timeouts().setScriptTimeout(120, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
            driver.findElement(By.xpath("//*[@id=\"gwt-debug-ATMobject-value\"]/div/input")).sendKeys(data.getSnData()); // ввод серийника

            progPause(2000);

            driver.manage().timeouts().setScriptTimeout(20, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            driver.findElement(By.xpath("//*[@id=\"gwt-debug-valueCellTree\"]/div/div/div[1]/div[2]/div[2]/div/div[1]/div/div/div")).click(); // выбор найденного банкомата из списка
            driver.manage().timeouts().setScriptTimeout(20, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            driver.findElement(By.id("gwt-debug-description-value")).sendKeys(data.getDefectData() + "\n" + data.getContactData()); // ввод неисправности и контактов в поле "Описание"
        /*driver.manage().timeouts().setScriptTimeout(20, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);*/

            progPause(1000);

            //driver.findElement(By.id("gwt-debug-apply")).click(); // нажатие клавиши сохранить
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            System.out.println(driver.findElement(By.id("gwt-debug-TitledTabBar.f2e9fdfc-1336-0048-0000-0000004904bd.title")).getText().substring(39, 45));
            //data.setNoTask(driver.findElement(By.id("gwt-debug-TitledTabBar.f2e9fdfc-1336-0048-0000-0000004904bd.title")).getText().substring(39, 45)); // определяем номер заявки
            driver.findElement(By.id("gwt-debug-logout")).click();

            clearForm();

        } catch (NoSuchElementException e){
            tooltip.setText("Проверьте правильность введённого серийного номера");
            rebootApp();

        } catch (IndexOutOfBoundsException e){
            tooltip.setText("Опаньки! " +  "\n" + " Что-то пошло не так, попробуйте отправить данные ещё раз!");
            rebootApp();
        }


    }

    private  void rebootApp(){
        driverClose();
        launchBrows();
        progPause(2000);
        robotNewTab();
        freshBtePage();
        primaryStage.hide();
        primaryStage.show();
        tooltip.show(getPrimaryStage());
    }


    private void setDataToData(){
        data.setSnData(sn.getText());
        data.setDefectData(defect.getText());
        data.setContactData(contact.getText());
    }

    private void clearForm(){
        sn.clear();
        defect.clear();
        contact.clear();

        snFlag = false;
        defectFlag = false;
    }

    @FXML
    private void closeAction() {
        driverClose();
        primaryStage.close();
    }

    private void deltaTest(){

        driver.get("http://nsd.delta-systems.ru:8090/sd/");

    }

    // Пауза в выполнении программы в миллисекундах. Ужасно, но, пока что, выхода нет.
    private void progPause(long i){

        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    // Открытие настроек
    private void prefWindow() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/fxmls/myPreferences.fxml"));
        loader.load();
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        MyPreferencesController myPreferencesController = loader.getController();
        myPreferencesController.setMyPreferenceStage(stage);

        if (firstStartFlag == true){ //если запуск первый и в настройках ничего не ввели
            myPreferencesController.getMyPreferenceStage().setOnCloseRequest(event -> {
                System.exit(0);// При закрытии окна, закрыть программу
            });
            myPreferencesController.firstStartCancelAction(); // При нажатии кнопки "Отмена", закрыть программу
        } else {
            myPreferencesController.getMyPreferenceStage().setOnCloseRequest(event -> {
                myPreferencesController.getMyPreferenceStage().close();

            });
        }

        stage.initOwner(getPrimaryStage());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Настройки");
        stage.setScene(new Scene(root));
        stage.showAndWait();

    }

    // Открытие окна с результатом
    private void resultWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/fxmls/result.fxml"));
        loader.load();
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        ResultController resultController = loader.getController();
        resultController.setResultStage(stage);
        stage.initOwner(getPrimaryStage());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Номер заявки");
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    // Действие при нажатии кнопки настроек
    @FXML
    private void prefAction(){
        try {
            prefWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Проверка есть ли настройки
    private void firstStart(){

        if (loginsAndPasswords.getBtePass() == "" && loginsAndPasswords.getDeltaPass()== ""){
            firstStartFlag = true;
            try {
                prefWindow();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
