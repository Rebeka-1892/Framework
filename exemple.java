//annotation
package  etu1793.framework.annotationDao;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ParamAnnotation {
    String description();
}

//model
@UrlAnnotation(urlPattern = "emp_find_by_id.do")
    public ModelView findById(@ParamAnnotation(description = "id") Integer id,@ParamAnnotation(description = "salut") String salut) {
        ModelView mv = new ModelView();
        mv.setView("emp_fiche.jsp");
        //mv.setData(new HashMap<String, Object>());
        ArrayList <Employe> l = new ArrayList<>();
        Employe e = new Employe("jean", 18);
        l.add(e);
        e = new Employe("jacque", 32);
        l.add(e);
        e = new Employe("patrik", 25);
        l.add(e);
        mv.addItem("employe", l.get(id));
        mv.addItem("id", id);
        mv.addItem("salut", salut);
        return mv;
    }


//traitement
public static Object[] getListeObjetsParametres(Method m, HttpServletRequest request) throws Exception {
    Parameter [] lp = m.getParameters();
    Object[] rep = new Object[lp.length];

    Map<String, String[]> parameterMap = request.getParameterMap();
    for (int i = 0; i < lp.length; i++) {
        System.out.println("isNamePresent "+lp[i].isNamePresent());
        System.out.println("nom param "+ lp[i].getName());
        Annotation[] annotes=lp[i].getAnnotations();
        for (Annotation annotation : annotes) {
            if(annotation.annotationType().getSimpleName().equals("ParamAnnotation")) {
                String valStr = request.getParameter(annotation.annotationType().getMethod("description").invoke(annotation).toString());
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
                }
                else {
                    rep[i] = null;
                }
            }
        }
    }

    return rep;
}