package etu1892.framework.servlet;

import jakarta.servlet.annotation.MultipartConfig;
import etu1892.framework.Mapping;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.lang.reflect.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilitaire.*;

@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // Taille limite avant écriture sur le disque (1 Mo)
    maxFileSize = 1024 * 1024 * 10, // Taille maximale des fichiers (10 Mo)
    maxRequestSize = 1024 * 1024 * 50 // Taille maximale de la requête (50 Mo)
)

public class FrontServlet extends HttpServlet {
    HashMap<String,Mapping> MappingUrls;
    HashMap<String,Object> SingletonMap;
    String baseUrl;
    String nomSession;

    public void init() throws ServletException{
        try {
            baseUrl = getInitParameter("baseUrl");
            nomSession = getInitParameter("sessionName");
            Vector<Class<?>> vect = Utile.getClasses("");
            MappingUrls = Utile.getAnnotedUrls(vect);
            SingletonMap  = Utile.getSingletonClasses(vect);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Utile utile = new Utile();
        HttpSession session = request.getSession();
        try {
            String url = request.getRequestURL().toString();
            url = utile.getUrl(url, baseUrl);
            out.print("Coucou " + url );

            if(MappingUrls.get(url) == null){
                throw new Exception("Tsy misy");
            }
            else{
                Mapping mapping = MappingUrls.get(url);
                    
                String nomClasse = mapping.getClassName();

                String nomMethode = mapping.getMethod();

                Object instance = null;

                if(SingletonMap.containsKey(nomClasse)){
                    instance = SingletonMap.get(nomClasse);
                    if (instance == null){
                        Class<?> classe = Class.forName(nomClasse);
                        Constructor<?> constr = classe.getConstructor();
                        instance = constr.newInstance();
                    }
                    else{
                        Utile.resetFieldsToDefault(instance.getClass().getDeclaredFields(), instance);
                    }
                }
                else{
                    Class<?> classe = Class.forName(nomClasse);
                    Constructor<?> constr = classe.getConstructor();
                    instance = constr.newInstance();
                }

                Method methode = Utile.getMethod(instance, nomMethode);
                ModelView resultat = null;
                if (methode != null){
                    // if(Utile.AuthentifiedMethod(session, methode, nomSession) == true){
                        Object[] listeObjets = Utile.getListeObjetsParametres(methode, request);
                        if(request.getParameterMap()!=null){
                            Utile.setValue(request, instance);
                        }
                        if(listeObjets.length > 0){
                            if(methode.invoke(instance, listeObjets) instanceof ModelView){
                                resultat = (ModelView) methode.invoke(instance, listeObjets);
                                // System.out.println("vita invoke");
                            }
                        }
                        else{
                            if(methode.invoke(instance) instanceof ModelView){
                                resultat = (ModelView) methode.invoke(instance);
                                // System.out.println("vita invoke" + nomMethode);
                            }
                        }   
                        HashMap<String,Object> rep = resultat.getData();
                        for(Map.Entry<String,Object> entry: rep.entrySet()){
                            request.setAttribute(entry.getKey(), entry.getValue());
                        }

                        Utile.setSession(resultat.getSession(), session);
                        Utile.setModeleSession(instance, methode, session);


                        if(Utile.AuthentifiedMethod(session, methode, nomSession) == false){
                            throw new Exception("Il y a une erreur, identifiez-vous");
                        }

                        String vu = resultat.getView();
                        RequestDispatcher dispatcher = request.getRequestDispatcher(vu);
                        dispatcher.forward(request, response);  
                    // }
                    // else{
                    //     throw new Exception("Il y a une erreur, identifiez-vous");
                    // }                              
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(FrontServlet.class.getName()).log(Level.SEVERE, null, ex);
            out.println(ex);
        }
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
