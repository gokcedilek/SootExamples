package Circle;

import soot.*;
import soot.jimple.JimpleBody;
import soot.jimple.*;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;
import soot.options.Options;

import java.io.File;
import java.util.Collections;
import java.util.Iterator;

public class Circle {
    public static String classPath =
            System.getProperty("user.dir") + File.separator + "demo" + File.separator + "Circle";
    public static String className = "Circle";

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

        // get class info
        SootClass sc = getSootClassInfo();

        // get method info
        SootMethod areaMethod =
                getSootMethodInfo(sc);
        System.out.println("the area method is:" +
                " " + areaMethod.getSignature());

        // get body info
        getSootMethodBody(areaMethod);

        // get call graph
        CallGraph callGraph = Scene.v().getCallGraph();
        for(Iterator<Edge> it = callGraph.edgesOutOf(areaMethod); it.hasNext(); ){
            Edge edge = it.next();
            System.out.println(String.format("Method '%s' invokes method '%s' through stmt '%s", edge.src(), edge.tgt(), edge.srcUnit()));
        }

    }

    private static void getSootMethodBody(SootMethod sm) {
        JimpleBody body =
                (JimpleBody) sm.getActiveBody();
        int c = 0;
        for(Unit u: body.getUnits()) {
            Stmt stmt = (Stmt) u;
            System.out.println(c + " --- " + stmt);
            c++;
        }
        System.out.println("Traps: ");
        for(Trap trap : body.getTraps()){
            System.out.println(trap);
        }

    }

    private static SootMethod getSootMethodInfo(SootClass sc) {
        System.out.println(String.format("List of " +
                "%s's " +
                "methods: ", sc.getName()));
        for(SootMethod m : sc.getMethods()) {
            System.out.println(m.getName() +
                    " --- " + m.getSignature());
        }
        SootMethod areaMethod = null;
        try {
            areaMethod = sc.getMethodByName("area");
        } catch (Exception e) {
            System.out.println("exception: " + e);
        }
        areaMethod = sc.getMethod("int area" +
                "(boolean)");
//        try {
//            areaMethod = sc.getMethod("int area(boolean)");
//        } catch (Exception e) {
//            System.out.print("exception: " + e);
//        }
        return areaMethod;
    }

    private static SootClass getSootClassInfo() {
        SootClass sc =
                Scene.v().getSootClass(className);
        System.out.println(String.format("Class %s " +
                        "is an %s " +
                "class, it has %d methods.",
                sc.getName(),
                sc.isApplicationClass() ?
                        "application" : "library",
                sc.getMethodCount()));
        return sc;
    }
}
