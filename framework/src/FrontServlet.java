package etu1892.framework.servlet;

import utilitaire.Utile;
import etu1892.framework.Mapping;
import etu1892.framework.Traitement;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FrontServlet extends HttpServlet {
    HashMap<String,Mapping> MappingUrls;

    public void init() throws ServletException{
        try {
            MappingUrls = Traitement.getAnnotedUrls(Traitement.getClasses("modele"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Utile utile = new Utile();
            try {
                String url = utile.getUrl(request);
                out.println(utile.getUrl(request));
                Mapping mapping = MappingUrls.get(url);
                out.println(mapping.getMethod() + " " + mapping.getClass());
            } catch (Exception e) {
            }            
        }
    }
}
