package modele;
import utilitaire.ModelView;

import annotation.Urls;

public class Emp {
    int id;
    String nom;
    
    public Emp(){}
    
    @Urls(url="emp_all")
    public ModelView findAll(){
        ModelView modele = new ModelView("emp_all.jsp");
        return modele;
    }
}

