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
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Utile utile = new Utile();
        try {
            String url = request.getRequestURL().toString();
            url = utile.getUrl(url, baseUrl);
            out.print("Coucou " + url );
            // for(Map.Entry<String,Mapping> entry: MappingUrls.entrySet()){
                // out.print(entry.getKey()+"  "+entry.getValue().getClassName());
            // }

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

                ModelView resultat = (ModelView) methode.invoke(instance);

                String vu = resultat.getView();
                out.println(vu);

                RequestDispatcher dispatcher = request.getRequestDispatcher(vu);
                dispatcher.forward(request, response);
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
