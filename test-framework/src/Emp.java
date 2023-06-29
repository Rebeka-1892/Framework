package modele;
import modele.Admin;

import annotation.*;
import utilitaire.ModelView;

@Scope(type="singleton")
public class Emp {
<<<<<<< Updated upstream
    int age;
    String nom;
=======
    String nom = "";
    int age = 0;
    FileUpload file = null;
>>>>>>> Stashed changes

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
    
    @Urls(url="emp_all")
    public ModelView findAll(){
        Emp employe = new Emp(7, "Olona Miasa");

        ModelView mod = new ModelView("emp_all.jsp");
        mod.AddItem("list_emp", employe);
        return mod;
    }

    @Urls(url="emp_save")
    public ModelView Save(){
        System.out.println("Age: " + getage());
        Emp employe = new Emp(getage(), getnom());

        ModelView mod = new ModelView("emp_all.jsp");
        mod.AddItem("list_emp", employe);
        return mod;
    }

    @Urls(url="emp_by_id")
    public ModelView FindById(@Parametre(nom = "id") int id) throws Exception{
        Emp employe = new Emp(77, "Tonga");

        ModelView mod = new ModelView("display.jsp");
        mod.AddItem("list_emp", employe);
<<<<<<< Updated upstream

        ModelView modele = new ModelView("emp_all.jsp", mod.getData());
        return modele;
=======
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
>>>>>>> Stashed changes
    }
}
