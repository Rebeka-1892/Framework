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
    String baseUrl;

    public void init() throws ServletException{
        try {
            baseUrl = getInitParameter("baseUrl");
            MappingUrls = Utile.getAnnotedUrls(Utile.getClasses(""));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setValue(HttpServletRequest request, Object obj)throws ServletException, IOException, Exception {
        Map<String, String[]> parametre = request.getParameterMap();
        for ( Map.Entry<String, String[]> paramMap : parametre.entrySet() ) {
            String nomField = paramMap.getKey();
            String[] paramValues = paramMap.getValue();
            for(int i = 0; i<obj.getClass().getDeclaredFields().length; i++){
                if(obj.getClass().getDeclaredFields()[i].getName().compareToIgnoreCase(nomField) == 0){
                    Field field = obj.getClass().getDeclaredFields()[i];
                    field.setAccessible(true);
                    Class type = field.getType();
                    
                    if(type == int.class){
                        for (String paramValue : paramValues) {
                            System.out.println("int");
                            int value = Integer.parseInt(paramValue);
                            field.set(obj, value);
                        }
                    }
                    if(type == double.class){
                        for (String paramValue : paramValues) {
                            double value = Double.parseDouble(paramValue);
                            field.set(obj, value);
                        }
                    }
                    if(type == String.class){
                        for (String paramValue : paramValues) {
                            field.set(obj, paramValue);
                        }
                    }
                }
            }
            // for (String paramValue : paramValues) {
            //     for(int i=0; i<obj.getClass().getDeclaredMethods().length; i++){
            //         if(obj.getClass().getDeclaredMethods()[i].getName().compareToIgnoreCase("set"+ nomField) == 0){
            //             Method methode = obj.getClass().getDeclaredMethods()[i];
            //             methode.invoke(obj, paramValue);
            //         }
            //     }
            // }
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

                Class<?> classe = Class.forName(nomClasse);
                
                Method methode = classe.getDeclaredMethod(nomMethode);

                Constructor<?> constr = classe.getConstructor();
                Object instance = constr.newInstance();

                setValue(request, instance);

                if(methode.invoke(instance) instanceof ModelView){
                    ModelView resultat = (ModelView) methode.invoke(instance);
                    System.out.println("vita invoke");

                    HashMap<String,Object> rep = resultat.getData();
                    for(Map.Entry<String,Object> entry: rep.entrySet()){
                        request.setAttribute(entry.getKey(), entry.getValue());
                    }

                    String vu = resultat.getView();
                    RequestDispatcher dispatcher = request.getRequestDispatcher(vu);
                    dispatcher.forward(request, response);
                }
                // out.println(vu);
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
