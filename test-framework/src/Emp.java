package modele;

import annotation.*;
import utilitaire.ModelView;

@Scope(type="singleton")
public class Emp {
<<<<<<< Updated upstream
<<<<<<< Updated upstream
    int age;
    String nom;
=======
    String nom = "";
    int age = 0;
    FileUpload file = null;
>>>>>>> Stashed changes
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

        ModelView mod = new ModelView();
        mod.AddItem("list_emp", employe);

        ModelView modele = new ModelView("emp_all.jsp", mod.getData());
        return modele;
    }

    @Urls(url="emp_save")
    public ModelView Save(){
        System.out.println("Age: " + getage());
        Emp employe = new Emp(getage(), getnom());

        ModelView mod = new ModelView();
        mod.AddItem("list_emp", employe);

        ModelView modele = new ModelView("emp_all.jsp", mod.getData());
        return modele;
    }

    @Urls(url="emp_by_id")
    public ModelView FindById(@Parametre(nom = "id") int id) throws Exception{
        Emp employe = new Emp(77, "Tonga");

        ModelView mod = new ModelView();
        mod.AddItem("list_emp", employe);

        ModelView modele = new ModelView("emp_all.jsp", mod.getData());
        return modele;
    }
}
