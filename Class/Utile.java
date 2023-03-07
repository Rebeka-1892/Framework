/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Class;

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
