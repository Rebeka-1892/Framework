package modele;

import annotation.*;
import java.util.*;
import utilitaire.ModelView;
import utilitaire.FileUpload;

@Scope(type="singleton")
public class Emp {
    String nom = "";
    int age = 0;
    FileUpload file = null;
    HashMap<String,Object> session;

    public Emp(int age, String nom) {
        this.age = age;
        this.nom = nom;
    }
    
    public Emp() {
    }

    public String getnom(){
        return this.nom;
    }

    public int getage(){
        return this.age;
    }

    public void setnom(String nom){
        this.nom = nom;
    }

    public void setage(int age){
        this.age = age;
    }

    public HashMap<String,Object> getSession() {
        if( this.session == null){
            this.session = new HashMap<String,Object>();
        }
        return session;
    }

    public void AddSession(String key, Object value){
        if( this.session == null){
            this.session = new HashMap<String,Object>();
        }
        System.out.println("Key " + key + " value " + value);
        this.session.put(key, value);
    } 
    
    @Session
    @Urls(url="emp_all.do")
    public ModelView findAll(){
        Emp employe = new Emp(1, "Olona 1");

        for(Map.Entry<String,Object> entry: getSession().entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

        ModelView mod = new ModelView("emp_all.jsp");
        mod.AddItem("list_emp", employe);
        return mod;
    }

    @Urls(url="emp_save.do")
    public ModelView Save(){
        Emp employe = new Emp(getage(), getnom());

        ModelView mod = new ModelView("emp_all.jsp");
        mod.AddItem("list_emp", employe);
        return mod;
    }

    @Urls(url="emp_by_id.do")
    public ModelView FindById(@Parametre(nom = "id") int id) throws Exception{
        Emp employe = new Emp(id, "no id");

        ModelView mod = new ModelView("display.jsp");
        mod.AddItem("list_emp", employe);
        return mod;
    }

    @Urls(url="emp_upload.do")
    public ModelView Uploading(@Parametre(nom = "file") FileUpload file) throws Exception{
        Emp employe = new Emp(2, file.getName());

        ModelView mod = new ModelView("display.jsp");
        mod.AddItem("list_emp", employe);
        return mod;
    }

    @Urls(url="delete.do")
    @Auth(profile="admin")
    public ModelView Delete() {
        Emp employe = new Emp(1, "Delete done");

        ModelView mod = new ModelView("display.jsp");
        mod.AddItem("list_emp", employe);
        return mod;
    }

    @Urls(url="test.do")
    @Auth()
    public ModelView TestEmp() {
        Emp employe = new Emp(1, "IsConnected");

        ModelView mod = new ModelView("display.jsp");
        mod.AddItem("list_emp", employe);
        return mod;
    }

    @Urls(url="login.do")
    public ModelView Login() {
        Emp employe = new Emp(1, "Vous etes connectes");

        ModelView mod = new ModelView("display.jsp");
        mod.AddItem("list_emp", employe);
        mod.AddSession("isConnected", true);
        mod.AddSession("tafiditra", "nety");
        return mod;
    }

    @Urls(url="loginadmin.do")
    public ModelView LoginAdmin() {
        Emp employe = new Emp(1, "Vous etes connectes en tant que Admin");

        ModelView mod = new ModelView("display.jsp");
        mod.AddItem("list_emp", employe);
        mod.AddSession("isConnected", true);
        mod.AddSession("admin", "Mr admin");
        return mod;
    }

    @Urls(url="do.do")
    public ModelView JsonParty(){
        Emp employe = new Emp(1, "Json");
        ModelView mod = new ModelView();
        mod.AddItem("list_emp", employe);
        mod.setIsJson(true);

        return mod;
    }

    @Json
    @Urls(url="json.do")
    public Emp[] json(){
        Emp[] employe = new Emp[3];
        employe[0] = new Emp(1, "XD");
        employe[1] = new Emp(2, ":(");
        employe[2] = new Emp(3, ";p");
        return employe;
    }
}