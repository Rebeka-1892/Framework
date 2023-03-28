package utilitaire;

import jakarta.servlet.http.HttpServletRequest;

/**
 *
 * @author rebeka
 */
public class Utile {
    public String getUrl(HttpServletRequest req) throws Exception{
        if(req.getPathInfo() == null){
            return "/";
        }
        return req.getPathInfo();
    }
}
