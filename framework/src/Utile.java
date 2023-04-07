package utilitaire;

import annotation.Urls;
import etu1892.framework.Mapping;
import jakarta.servlet.http.HttpServletRequest;
import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.HashMap;

public class Utile {
    public String getUrl(String url, String baseUrl) throws Exception{
        int len = baseUrl.length();
        return url.substring(len);
    }
    
    public static HashMap<String,Mapping> getAnnotedUrls(Vector<Class<?>> vect){
        Class[] classes = vect.toArray( new Class[ vect.size() ] );
        Method[] methodes;
        String value, className, method;
        Mapping mapping;
        HashMap<String, Mapping> reponse = new HashMap<String, Mapping>();
        for (Class cls : classes){
            className = cls.getName();
            methodes = cls.getDeclaredMethods();
            for(Method meth : methodes){
                Urls urls = meth.getAnnotation(Urls.class);
                if(urls != null){
                    value = urls.url();
                    method = meth.getName();
                    mapping = new Mapping(className, method);
                    System.out.println(className + " " + method);
                    reponse.put(value, mapping);
                    System.out.println(value + " " + mapping.getClassName());
                }
            }
        }
        return reponse;
    }

    public static Vector<Class<?>> getClasses(String packageName) throws ClassNotFoundException {
       Vector<Class<?>> classes=new Vector<Class<?>>();
        File directory = null;
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            if (classLoader == null) {
                throw new ClassNotFoundException("Can't get class loader.");
            }
            String path = packageName.replace('.', '/');
            URL resource = classLoader.getResource(path);
            if (resource == null) {
                throw new ClassNotFoundException("No resource for " + path);
            }
            directory = new File(resource.getFile());
        } catch (NullPointerException x) {
            x.printStackTrace();
            throw new ClassNotFoundException(packageName + " (" + directory + ") does not appear to be a valid package");
        }
        if (directory.exists()) {
            String[] files = directory.list();
            for (int i = 0; i < files.length; i++) {
                File file = new File(directory.getAbsolutePath() + File.separator + files[i]);
                if (file.isDirectory()) {
                    String inpack=file.getName();
                    if(packageName.length()!=0){
                        inpack=packageName+"."+inpack;
                    }
                    classes.addAll(getClasses(inpack));
                } else {
                    
                    if (files[i].endsWith(".class")) {
                        String name=files[i].substring(0, files[i].length() - 6);
                        if(packageName.length()!=0){
                            name=packageName+"."+name;
                        }
                        classes.add(Class.forName(name));
                    }
                }
            }
        } else {
            throw new ClassNotFoundException(packageName + " does not appear to be a valid package");
        }
        return classes;
    }
}
