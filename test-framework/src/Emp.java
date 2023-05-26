package modele;

import annotation.*;
import utilitaire.ModelView;
import utilitaire.FileUpload;

public class Emp {
    int age;
    String nom;
    FileUpload file;

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
    
    @Urls(url="emp_all.do")
    public ModelView findAll(){
        Emp employe = new Emp(1, "Olona 1");

        ModelView mod = new ModelView();
        mod.AddItem("list_emp", employe);

        ModelView modele = new ModelView("emp_all.jsp", mod.getData());
        return modele;
    }

    @Urls(url="emp_save.do")
    public ModelView Save(){
        System.out.println("Age: " + getage());
        Emp employe = new Emp(getage(), getnom());

        ModelView mod = new ModelView();
        mod.AddItem("list_emp", employe);

        ModelView modele = new ModelView("emp_all.jsp", mod.getData());
        return modele;
    }

    @Urls(url="emp_by_id.do")
    public ModelView FindById(@Parametre(nom = "id") int id) throws Exception{
        Emp employe = new Emp(id, "no id");

        ModelView mod = new ModelView();
        mod.AddItem("list_emp", employe);

        ModelView modele = new ModelView("display.jsp", mod.getData());
        return modele;
    }

    @Urls(url="emp_upload.do")
    public ModelView Uploading(@Parametre(nom = "file") FileUpload file) throws Exception{
        Emp employe = new Emp(2, file.getName());

        ModelView mod = new ModelView();
        mod.AddItem("list_emp", employe);

        ModelView modele = new ModelView("display.jsp", mod.getData());
        return modele;
    }
}