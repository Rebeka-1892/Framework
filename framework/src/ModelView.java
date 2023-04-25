package utilitaire;
import java.util.*;

public class ModelView {
    String view;
    HashMap<String,Object> data;

    public ModelView(String view, HashMap<String,Object> data) {
        this.data = data;
        this.view = view;
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

    public HashMap<String,Object> getData() {
        return data;
    }

    public void setData(HashMap<String,Object> data) {
        this.data = data;
    }    

    public void AddItem(String key, Object value){
        if( this.data == null){
            data = new HashMap<String,Object>();
        }
        this.data.put(key, value);
    }
}
