package utilitaire;

import annotation.Urls;
import etu1892.framework.Mapping;
import jakarta.servlet.http.HttpServletRequest;
import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;

public class Utile {
    public String getUrl(HttpServletRequest req) throws Exception{
        if(req.getPathInfo() == null){
            return "/";
        }
        return req.getPathInfo();
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
                System.out.println(file);
                if (file.endsWith(".class")) {
                    classes.add(Class.forName(pckgname + '.' + file.substring(0, file.length() - 6)));
                }
                else{
                    getClasses(pckgname+"."+file);
                }
            }
        } else {
            throw new ClassNotFoundException(pckgname + " n'est pas un paquet valide");
        }
        Class[] classesA = new Class[classes.size()];
        classes.toArray(classesA);
        return classesA;
    }
    
    public static ArrayList<Class<?>> listClasses(String directory) throws ClassNotFoundException {
        ArrayList<Class<?>> classes = new ArrayList<>();
        File dir = new File(directory);

        if (!dir.isDirectory()) {
            throw new IllegalArgumentException("Le chemin doit correspondre à un dossier !");
        }

        File[] files = dir.listFiles();

        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(listClasses(file.getAbsolutePath()));
            } else if (file.getName().endsWith(".class")) {
                // classes.add(Class.forName(pckgname + '.' + file.substring(0, file.length() - 6)));
                    System.out.println(file.getName());
                    String className = file.getName().substring(0, file.getName().length() - 6);
                try {
                    // String className = file.getName().substring(0, file.getName().length() - 6);
                    Class<?> cls = new URLClassLoader(new URL[]{dir.toURI().toURL()}).loadClass(className);
                    classes.add(cls);
                } catch (MalformedURLException e) {
                    System.out.println(e);
                }
            }
        }

        return classes;
    }
}
