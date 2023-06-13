package utilitaire;

import annotation.*;
import etu1892.framework.Mapping;
import jakarta.servlet.http.HttpServletRequest;
import java.io.File;
import java.lang.reflect.Parameter;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.lang.reflect.Method;
import java.lang.annotation.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.*;
import java.lang.reflect.*;
import jakarta.servlet.ServletException;

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

    public static HashMap<String, Object> getSingletonClasses(Vector<Class<?>> vect) {
        HashMap<String, Object> singletonClasses = new HashMap<String, Object>();
        Class[] classes = vect.toArray( new Class[ vect.size() ] );
        for (Class<?> clazz : classes) {
            Annotation[] annotations = clazz.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation.annotationType() == Scope.class) {
                    Scope scopeAnnotation = (Scope) annotation;
                    String type = scopeAnnotation.type();
                    if ("singleton".equals(type)) {
                        try {
                            Object instance = clazz.newInstance();
                            singletonClasses.put(clazz.getName(), instance);
                            System.out.println("Singleton: " + clazz.getName());
                        } catch (InstantiationException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }
            }
        }

        return singletonClasses;
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

    public static Object[] getListeObjetsParametres(Method m, HttpServletRequest request) throws Exception {
        Parameter [] lp = m.getParameters();
        Object[] rep = new Object[lp.length];

        Map<String, String[]> parameterMap = request.getParameterMap();
        for (int i = 0; i < lp.length; i++) {
            System.out.println("isNamePresent "+lp[i].isNamePresent());
            System.out.println("nom param "+ lp[i].getName());
            Annotation[] annotes=lp[i].getAnnotations();
            for (Annotation annotation : annotes) {
                if(annotation.annotationType().getSimpleName().equals("Parametre")) {
<<<<<<< Updated upstream
<<<<<<< Updated upstream
                    String valStr = request.getParameter(annotation.annotationType().getMethod("nom").invoke(annotation).toString());
                    if(valStr != null) {
                        Class typeParametre = lp[i].getType();
                        if (typeParametre == int.class) {
                            int intValue = Integer.parseInt(valStr);
                            rep[i] = intValue;
                        }
                        else if (typeParametre == Integer.class) {
                            Integer intValue = Integer.parseInt(valStr);
                            rep[i] = intValue;
                        }
                        else if (typeParametre == double.class) {
                            double doubleValue = Double.parseDouble(valStr);
                            rep[i] = doubleValue;
                        }
                        else if (typeParametre == Double.class) {
                            Double doubleValue = Double.parseDouble(valStr);
                            rep[i] = doubleValue;
                        }
                        else if (typeParametre == boolean.class) {
                            boolean booleanValue = Boolean.parseBoolean(valStr);
                            rep[i] = booleanValue;
                        } else if (typeParametre == String.class) {
                            rep[i] = valStr;
                        }
                        else if (typeParametre == Date.class) {
                            String dateFormat = "yyyy-MM-dd";
                            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
                            Date date = formatter.parse(valStr);
                            rep[i] = date;
=======
=======
>>>>>>> Stashed changes
                    String enctype = request.getContentType();        
                    if (enctype != null && enctype.startsWith("multipart/form-data")) {
                        Part filePart = request.getPart(annotation.annotationType().getMethod("nom").invoke(annotation).toString());
                        System.out.println("Le formulaire a un attribut 'enctype' de type multipart/form-data.");
                        String fileName = Utile.getFileName(filePart);
                        byte[] bites = Utile.readBytesFromPart(filePart);
                        String filePath = "/home/ravalison/GitHub/byte.txt";
                        Utile.writeBytesAndFileNameToFile(bites, fileName, filePath);
                        FileUpload fileupload = new FileUpload(fileName, "", bites);
                        rep[i] = fileupload;
                    } else if (enctype == null) {
                        String valStr = request.getParameter(annotation.annotationType().getMethod("nom").invoke(annotation).toString());
                        System.out.println("Le formulaire n'a pas d'attribut 'enctype' de type multipart/form-data.");
                        if(valStr != null) {
                            Class typeParametre = lp[i].getType();
                            if (typeParametre == int.class) {
                                int intValue = Integer.parseInt(valStr);
                                rep[i] = intValue;
                            }
                            else if (typeParametre == Integer.class) {
                                Integer intValue = Integer.parseInt(valStr);
                                rep[i] = intValue;
                            }
                            else if (typeParametre == double.class) {
                                double doubleValue = Double.parseDouble(valStr);
                                rep[i] = doubleValue;
                            }
                            else if (typeParametre == Double.class) {
                                Double doubleValue = Double.parseDouble(valStr);
                                rep[i] = doubleValue;
                            }
                            else if (typeParametre == boolean.class) {
                                boolean booleanValue = Boolean.parseBoolean(valStr);
                                rep[i] = booleanValue;
                            } else if (typeParametre == String.class) {
                                rep[i] = valStr;
                            }
                            else if (typeParametre == Date.class) {
                                String dateFormat = "yyyy-MM-dd";
                                SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
                                Date date = formatter.parse(valStr);
                                rep[i] = date;
                            }
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
                        }
                    }
                    else {
                        rep[i] = null;
                    }
                }
            }
        }
        return rep;
    }

    public static Method getMethod(Object objet, String nomMethode){
        for(int i=0; i< objet.getClass().getDeclaredMethods().length; i++){
            if(objet.getClass().getDeclaredMethods()[i].getName().compareToIgnoreCase(nomMethode)==0){
                Method methode = objet.getClass().getDeclaredMethods()[i];
                return methode;
            }
        }
        return null;
    }

    public static void setValue(HttpServletRequest request, Object obj)throws ServletException, IOException, Exception {
        Map<String, String[]> parametre = request.getParameterMap();
        for ( Map.Entry<String, String[]> paramMap : parametre.entrySet() ) {
            String nomField = paramMap.getKey();
            String[] paramValues = paramMap.getValue();
            for(int i = 0; i<obj.getClass().getDeclaredFields().length; i++){
                if(obj.getClass().getDeclaredFields()[i].getName().compareToIgnoreCase(nomField) == 0){
                    Field field = obj.getClass().getDeclaredFields()[i];
                    field.setAccessible(true);
                    Class typeParametre = field.getType();
                    
                    if(typeParametre == int.class){
                        for (String paramValue : paramValues) {
                            System.out.println("int");
                            int value = Integer.parseInt(paramValue);
                            field.set(obj, value);
                        }
                    }
                    if(typeParametre == Integer.class){
                        for (String paramValue : paramValues) {
                            System.out.println("int");
                            Integer value = Integer.parseInt(paramValue);
                            field.set(obj, value);
                        }
                    }
                    if(typeParametre == double.class){
                        for (String paramValue : paramValues) {
                            double value = Double.parseDouble(paramValue);
                            field.set(obj, value);
                        }
                    }
                    if(typeParametre == Double.class){
                        for (String paramValue : paramValues) {
                            Double value = Double.parseDouble(paramValue);
                            field.set(obj, value);
                        }
                    }
                    if(typeParametre == boolean.class){
                        for (String paramValue : paramValues) {
                            boolean value = Boolean.parseBoolean(paramValue);
                            field.set(obj, value);
                        }
                    }
                    if(typeParametre == String.class){
                        for (String paramValue : paramValues) {
                            field.set(obj, paramValue);
                        }
                    }
                    if(typeParametre == Date.class){
                        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                        for (String paramValue : paramValues) {
                            java.util.Date value = format.parse(paramValue);
                            field.set(obj, value);
                        }
                    }
                    if(typeParametre == Timestamp.class){
                        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                        for (String paramValue : paramValues) {
                            java.util.Date date = format.parse(paramValue);
                            Timestamp value = new Timestamp(date.getTime());
                            field.set(obj, value);
                        }
                    }
                }
            }
        }
    }
<<<<<<< Updated upstream
}
=======

    public static void resetFieldsToDefault(Field[] fields, Object instance) throws IllegalAccessException {
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                Object defaultValue = field.get(instance);
                System.out.println(field.getName() + " : " + defaultValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void resetFieldsToDefault(Field[] fields, Object instance) throws IllegalAccessException {
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                Object defaultValue = field.get(instance);
                System.out.println(field.getName() + " : " + defaultValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        String[] parts = contentDisposition.split(";");
        
        for (String partInfo : parts) {
            if (partInfo.trim().startsWith("filename")) {
                return partInfo.substring(partInfo.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        
        return null;
    }

    public static byte[] readBytesFromPart(Part part) throws IOException, ServletException {
        return part.getInputStream().readAllBytes();
    }

    public static void writeBytesAndFileNameToFile(byte[] bytes, String fileName, String filePath) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            // Écrit le nom du fichier dans le fichier texte
            outputStream.write(fileName.getBytes());
            outputStream.write(System.lineSeparator().getBytes());

            // Écrit chaque octet du tableau byte[] dans le fichier texte
            for (byte b : bytes) {
                outputStream.write(b);
            }
        }
    }
}
>>>>>>> Stashed changes
