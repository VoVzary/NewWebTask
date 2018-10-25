package sample.objects;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Data {

    private String snData;

    private String defectData;

    private String contactData;

    private String noTask;


    public String getSnData() {
        return snData;
    }

    public void setSnData(String snData) {
        this.snData = snData;
    }

    public String getContactData() {
        return contactData;
    }

    public void setContactData(String contactData) {
        this.contactData = contactData;
    }

    public String getDefectData() {
        return defectData;
    }

    public void setDefectData(String defectData) {
        this.defectData = defectData;
    }

    public String getNoTask() {
        return noTask;
    }

    public void setNoTask(String noTask) {
        this.noTask = noTask;
    }
}
