package etu1892.framework.servlet;

import etu1892.framework.Mapping;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilitaire.Utile;

public class FrontServlet extends HttpServlet {
    HashMap<String,Mapping> MappingUrls;

    public void init() throws ServletException{
        try {
            MappingUrls = Utile.getAnnotedUrls(Utile.getClasses("modele"));
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
            String url = utile.getUrl(request);
            out.print(url);
            
            Mapping mapping = MappingUrls.get(url);
                out.println(mapping.getMethod() + " " + mapping.getClass());
                
            String nomDeLaClasse = mapping.getClassName();

            String nomDeLaMethode = mapping.getMethod();

            Class<?> classe = Class.forName(nomDeLaClasse);

            Method methode = classe.getDeclaredMethod(nomDeLaMethode);

            Object resultat = methode.invoke(new Utile());

<<<<<<< Updated upstream
            String vu = (String) resultat;

            RequestDispatcher dispatcher = request.getRequestDispatcher(vu);
            dispatcher.forward(request, response);
=======
                Constructor<?> constr = classe.getConstructor();
                Object instance = constr.newInstance();
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
>>>>>>> Stashed changes

        } catch (Exception ex) {
            Logger.getLogger(FrontServlet.class.getName()).log(Level.SEVERE, null, ex);
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
