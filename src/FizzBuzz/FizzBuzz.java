package FizzBuzz;

import soot.*;
import soot.jimple.JimpleBody;
import soot.options.Options;

import java.io.File;

public class FizzBuzz {
    public static String classPath =
            System.getProperty("user.dir") + File.separator + "demo" + File.separator + "FizzBuzz";
    public static String className = "FizzBuzz";
    public static String methodName = "printFizzBuzz";

    public static void setupSoot() {
        G.reset();
        Options.v().set_prepend_classpath(true);
        Options.v().set_soot_classpath(classPath);
        SootClass sc = Scene.v().loadClassAndSupport(className);
        sc.setApplicationClass();
        Scene.v().loadNecessaryClasses();
    }

    public static void main(String[] args) {
        setupSoot();

        // retrieve method body
        SootClass sc =
                Scene.v().getSootClass(className);
        SootMethod sm =
                sc.getMethodByName(methodName);
        JimpleBody body = (JimpleBody) sm.retrieveActiveBody();

        // print method info
        System.out.println("method name: " + sm.getName());
        System.out.println("method signature: " + sm.getSignature());
        System.out.println("--------------");

        System.out.println("arguments: ");
        for(Local l : body.getParameterLocals()) {
            System.out.println(l.getName() +
                    ":" + l.getType());
        }
        System.out.println("--------------");

        System.out.println("units: ");
        int c = 0;
        for(Unit u : body.getUnits()) {
            System.out.println(c + " --- " + u +
                    " --- " + u.getClass());
            c++;
        }
        System.out.println("--------------");
    }
}
