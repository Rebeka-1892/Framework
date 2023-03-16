package modele;

import annotation.Urls;

public class Emp {
    int id;
    String nom;
    
    public Emp(){}
    
    @Urls(url="emp_all")
    public String findAll(){
        return nom;
    }
}

