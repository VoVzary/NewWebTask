package sample.objects;

import java.util.prefs.Preferences;

public class LoginsAndPasswords {

    private String bteLogin;
    private String btePass;
    private String deltaLogin;
    private String deltaPass;

    private Preferences preferences = Preferences.userRoot().node("newwebtask");

    public void getPref(){
        setBteLogin(preferences.get("bte_login", ""));
        setBtePass(preferences.get("bte_pass", ""));
        setDeltaLogin(preferences.get("delta_login", ""));
        setDeltaPass(preferences.get("delta_pass", ""));
    }

    public void setPref(String logBte, String passBte, String logDelta, String passDelta){
        preferences.put("bte_login", logBte);
        preferences.put("bte_pass", passBte);
        preferences.put("delta_login", logDelta);
        preferences.put("delta_pass", passDelta);

    }

    public String getBteLogin() {
        return bteLogin;
    }

    public void setBteLogin(String bteLogin) {
        this.bteLogin = bteLogin;
    }

    public String getBtePass() {
        return btePass;
    }

    public void setBtePass(String btePass) {
        this.btePass = btePass;
    }

    public String getDeltaLogin() {
        return deltaLogin;
    }

    public void setDeltaLogin(String deltaLogin) {
        this.deltaLogin = deltaLogin;
    }

    public String getDeltaPass() {
        return deltaPass;
    }

    public void setDeltaPass(String deltaPass) {
        this.deltaPass = deltaPass;
    }



}
