package Sphere;

import soot.*;
import soot.jimple.JimpleBody;
import soot.jimple.Stmt;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;
import soot.options.Options;

import java.io.File;
import java.util.Collections;
import java.util.Iterator;

public class Sphere {
    public static String classPath =
            System.getProperty("user.dir") + File.separator + "demo" + File.separator + "Sphere";
    public static String className = "Sphere";

    public static void setupSoot() {
        G.reset();
        Options.v().set_prepend_classpath(true);
        Options.v().set_allow_phantom_refs(true);
        Options.v().set_soot_classpath(classPath);
        Options.v().set_output_format(Options.output_format_jimple);
        Options.v().set_process_dir(Collections.singletonList(classPath));
        Options.v().set_whole_program(true);
        Scene.v().loadNecessaryClasses();
        PackManager.v().runPacks();
    }

    public static void main(String[] args) {
        setupSoot();

        SootClass sc =
                Scene.v().getSootClass(className);
        printMethods(sc);

        printCallGraph(sc);
    }

    public static void printMethods(SootClass sc) {
        for(SootMethod sm : sc.getMethods()) {
            System.out.println(String.format(
            "Method %s:", sm.getName()));
            printMethodBody(sm);
        }
    }

    private static void printMethodBody(SootMethod sm) {
        JimpleBody body =
                (JimpleBody) sm.getActiveBody();
        int c = 0;
        for(Unit u: body.getUnits()) {
            Stmt stmt = (Stmt) u;
            System.out.println(c + " --- " + stmt);
            c++;
        }
    }

    public static void printCallGraph(SootClass sc) {
        System.out.println("Call Graph:");
        CallGraph cg = Scene.v().getCallGraph();
        for(SootMethod sm : sc.getMethods()) {
            printMethodCall(sm, cg);
        }
    }

    private static void printMethodCall(SootMethod sm, CallGraph cg) {
        for(Iterator<Edge> it =
            cg.edgesOutOf(sm); it.hasNext();) {
            Edge e = it.next();
            System.out.println(String.format(
                    "Method '%s' invokes method" +
                            " '%s' through stmt" +
                            " '%s", e.src(), e.tgt(), e.srcUnit()));
        }
    }

}
