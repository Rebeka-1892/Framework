package etu1892.framework.servlet;

import etu1892.framework.Mapping;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilitaire.*;

public class FrontServlet extends HttpServlet {
    HashMap<String,Mapping> MappingUrls;
    HashMap<String,Object> SingletonMap;
    String baseUrl;

    public void init() throws ServletException{
        try {
            baseUrl = getInitParameter("baseUrl");
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
                
                // Utile.resetFieldsToDefault(instance.getClass().getDeclaredFields(), instance);

                Method methode = Utile.getMethod(instance, nomMethode);
<<<<<<< Updated upstream
=======
                ModelView resultat = null;
>>>>>>> Stashed changes
                if (methode != null){
                    Object[] listeObjets = Utile.getListeObjetsParametres(methode, request);
                    if(request.getParameterMap()!=null){
                        Utile.setValue(request, instance);
                    }
                    if(listeObjets.length > 0){
                        if(methode.invoke(instance, listeObjets) instanceof ModelView){
                            resultat = (ModelView) methode.invoke(instance, listeObjets);
                            System.out.println("vita invoke");
                        }
                    }
                    else{
                        if(methode.invoke(instance) instanceof ModelView){
<<<<<<< Updated upstream
                            ModelView resultat = (ModelView) methode.invoke(instance);
                            System.out.println("vita invoke");

                            HashMap<String,Object> rep = resultat.getData();
                            for(Map.Entry<String,Object> entry: rep.entrySet()){
                                request.setAttribute(entry.getKey(), entry.getValue());
                            }

                            String vu = resultat.getView();
                            RequestDispatcher dispatcher = request.getRequestDispatcher(vu);
                            dispatcher.forward(request, response);
=======
                            resultat = (ModelView) methode.invoke(instance);
                            System.out.println("vita invoke" + nomMethode);
>>>>>>> Stashed changes
                        }
                    }   
                    HashMap<String,Object> rep = resultat.getData();
                    for(Map.Entry<String,Object> entry: rep.entrySet()){
                        request.setAttribute(entry.getKey(), entry.getValue());
                    }

                    String vu = resultat.getView();
                    RequestDispatcher dispatcher = request.getRequestDispatcher(vu);
                    dispatcher.forward(request, response);            
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
