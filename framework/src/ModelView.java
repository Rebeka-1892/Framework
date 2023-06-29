package utilitaire;
import java.util.*;

public class ModelView {
    String view;
    HashMap<String,Object> data;
    HashMap<String,Object> session;
<<<<<<< Updated upstream
=======
    boolean isJson = false;
>>>>>>> Stashed changes

    public ModelView(String view, HashMap<String,Object> data) {
        this.data = data;
        this.view = view;
    }

    public ModelView(String view, HashMap<String,Object> data, HashMap<String,Object> session) {
        this.data = data;
        this.view = view;
        this.session = session;
    }

    public ModelView(String view) {
        this.view = view;
    }

    public ModelView(){ }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    } 

    public boolean getIsJson() {
        return isJson;
    }

    public void setIsJson(boolean isJson) {
        this.isJson = isJson;
    } 

    public HashMap<String,Object> getData() {
        return data;
    }

    public void setData(HashMap<String,Object> data) {
        this.data = data;
    }

    public HashMap<String,Object> getSession() {
        if( this.session == null){
            session = new HashMap<String,Object>();
        }

        return session;
    }

    public void setSession(HashMap<String,Object> session) {
        this.session = session;
    }   

    public void AddSession(String key, Object value){
        if( this.session == null){
            session = new HashMap<String,Object>();
        }
        this.session.put(key, value);
    } 

    public void AddItem(String key, Object value){
        if( this.data == null){
            data = new HashMap<String,Object>();
        }
        this.data.put(key, value);
    }
}
