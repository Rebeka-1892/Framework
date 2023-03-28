package etu1892.framework;

public class Mapping {
    String className;
    String Method;

    public Mapping(String className, String Method) {
        this.className = className;
        this.Method = Method;
    }

    public Mapping() {
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethod() {
        return Method;
    }

    public void setMethod(String Method) {
        this.Method = Method;
    }
    
    
}