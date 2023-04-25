package modele;

import annotation.Urls;
import utilitaire.ModelView;

public class Emp {
    int id;
    String nom;

    public Emp(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }
    
    public Emp() {
    }

    public String getNom(){
        return this.nom;
    }
    
    @Urls(url="emp_all")
    public ModelView findAll(){
        Emp employe = new Emp(7, "Olona Miasa");

        ModelView mod = new ModelView();
        mod.AddItem("list_emp", employe);

        ModelView modele = new ModelView("emp_all.jsp", mod.getData());
        return modele;
    }
}
