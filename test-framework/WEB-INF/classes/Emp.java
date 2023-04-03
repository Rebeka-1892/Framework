package modele;

import annotation.Urls;
import utilitaire.ModelView;

public class Emp {
    int id;
    String nom;

    public Emp() {
    }
    
    @Urls(url="emp_all")
    public ModelView findAll(){
        ModelView modele = new ModelView("emp_all.jsp");
        return modele;
    }
}
