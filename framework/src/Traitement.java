package etu1892.framework;

import annotation.Urls;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class Traitement {
    
    public static Class[] getClasses(String pckgname) throws ClassNotFoundException {
        ArrayList<Class> classes = new ArrayList<>();

        File directory = null;
        try {
            ClassLoader cld = Thread.currentThread().getContextClassLoader();
            if (cld == null) {
                throw new ClassNotFoundException("la classe loader ne peut etre obtenue.");
            }
            String path = pckgname.replace('.', '/');
            URL resource = cld.getResource(path);
            if (resource == null) {
                throw new ClassNotFoundException("pas de ressource dans le paquet " + path);
            }
            directory = new File(resource.getFile());
        } catch (NullPointerException x) {
            throw new ClassNotFoundException(pckgname + " (" + directory + ") n'est pas un paquet valide");
        }
        if (directory.exists()) {
            String[] files = directory.list();
            for (String file : files) {
                if (file.endsWith(".class")) {
                    classes.add(Class.forName(pckgname + '.' + file.substring(0, file.length() - 6)));
                }
            }
        } else {
            throw new ClassNotFoundException(pckgname + " n'est pas un paquet valide");
        }
        Class[] classesA = new Class[classes.size()];
        classes.toArray(classesA);
        return classesA;
    }
    
    public static HashMap<String,Mapping> getAnnotedUrls(Class[] classes){
        Method[] methodes;
        String value, className, method;
        Mapping mapping;
        HashMap<String, Mapping> reponse = new HashMap<String, Mapping>();
        for (Class cls : classes){
            className = cls.getSimpleName();
            methodes = cls.getDeclaredMethods();
            for(Method meth : methodes){
                Urls urls = meth.getAnnotation(Urls.class);
                if(urls != null){
                    value = urls.url();
                    method = meth.getName();
                    mapping = new Mapping(className, method);
                    reponse.put(value, mapping);
                }
            }
        }
        return reponse;
    }
}

