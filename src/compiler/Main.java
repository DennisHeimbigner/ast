
import gnu.getopt.Getopt;
import unidata.protobuf.compiler.*;

import java.util.*;
import java.io.*;
//import java.lang.reflection.*;

public class Main
{
    static final String DFALTLANGUAGE = "java";
    static final String DFALTPACKAGE = "unidata.protobuf.compiler";

    static List<String> includePaths = new ArrayList<String>();
    static List<String> defines = new ArrayList<String>();
    static List<String> arglist = new ArrayList<String>();
    static String optionLanguage = null;
    static boolean optionVerbose = false;
    static boolean optionParseDebug = false;
    static boolean optionSemanticsDebug = false;
    static boolean optionSemanticStepsDebug = false;
    static boolean optionDuplicate = false;

    static boolean debug = false;

    static public void main(String[] argv) throws Exception
    {
	int c;
	Getopt g = new Getopt("Main",argv,"-:D:I:L:W:V",null);
	while ((c = g.getopt()) != -1) {
            switch (c) {
	    case 1: // intermixed non-option
		arglist.add(g.getOptarg());
		break;		
	    case 'D':
		String def = g.getOptarg();
		if(def.length() > 0) defines.add(def);
		break;	
	    case 'I':
		String path = g.getOptarg();
		if(path.length() > 0) includePaths.add(path);
		break;	
	    case 'L':
		String optionLanguage = g.getOptarg();
		break;
	    case 'W':
		String wvalue = g.getOptarg();
		for(char x: wvalue.toCharArray()) {
		    switch (x) {
		    case 'd': debug = true; break;
		    case 'p': optionParseDebug = true; break;
		    case 't': optionSemanticStepsDebug = true; break;
		    case 's': optionSemanticsDebug = true; break;
		    case 'D': optionDuplicate = true; break;
		    default: break;
		    }
		}
		break;
	    case 'V':
		optionVerbose = true;		
		break;
	    case ':':
	        System.err.println("Command line option requires argument "+g.getOptopt());
	        System.exit(1);
	    case '?':
	        System.err.println("Illegal cmd line option: "+g.getOptopt());
	        System.exit(1);
	    default:
	        System.err.println("Unexpected getopt tag: "+c);
	        System.exit(1);
	    }
        }

	if(optionLanguage == null) optionLanguage = DFALTLANGUAGE;
	// Canonicalize the language name: all lower case except first character
	optionLanguage = optionLanguage.substring(0,1).toUpperCase()
                         + optionLanguage.substring(1).toLowerCase();

	if(arglist.size() == 0) {
	    System.err.println("No input file specified");
	    System.exit(1);
	}

	String rawfilename = arglist.get(0);
	String escapedfilename = AuxFcns.escapedname(rawfilename);
	String inputfilename = AuxFcns.locatefile(rawfilename,includePaths);

	if(inputfilename == null) {
	    System.err.println("Cannot locate input file: "+rawfilename);
	    System.exit(1);
	}
	File inputfile = new File(inputfilename);
	if(!inputfile.canRead()) {
	    System.err.println("Cannot read input file: "+inputfile.toString());
	    System.exit(1);
	}
	FileReader rdr = new FileReader(inputfile);

	// Try to locate the language specific ASTFactory using reflection
	ASTFactory factory = null;
	String factoryclassname = DFALTPACKAGE + ".ASTFactory"+optionLanguage;
        ClassLoader classLoader = Main.class.getClassLoader();
        try {
            Class factoryclass = Class.forName(factoryclassname);
	    factory = (ASTFactory)factoryclass.newInstance();
	    if(debug)
		System.err.println("Using AST factory: ASTFactory"+optionLanguage);
        } catch (ClassNotFoundException e) {
	    factory = new ASTFactoryDefault();
	    if(debug)
		System.err.println("Using default AST factory");
        }

        ProtobufParser parser = new ProtobufParser(factory);
	parser.setIncludePaths(includePaths);
	if(optionParseDebug) parser.setDebugLevel(1);

	boolean pass = parser.parse(rawfilename,rdr);
	if(!pass) {
	    System.err.println("Parse failed");
	    System.exit(1);
	}

	// Semantic Processing
	if(optionSemanticStepsDebug) Debug.setTag("trace.semantics.steps");
	if(optionSemanticStepsDebug) Debug.setTag("trace.semantics.steps");
	if(optionSemanticsDebug) Debug.setTag("trace.semantics");
	if(optionDuplicate) {
	    Debug.setTag("trace.duplicate.package");
	}
	Semantics sem = new Semantics();
	pass = sem.process(parser.getAST());
	if(!pass) {
	    System.err.println("Semantic processing failed");
	    System.exit(1);
	}

	// Try to locate the language specific Generator using reflection
	String generatorclassname = DFALTPACKAGE + "."+optionLanguage;
        classLoader = Main.class.getClassLoader();
        try {
            Class generatorclass = Class.forName(generatorclassname);
	    Generator generator = (Generator)generatorclass.newInstance();
	    generator.generate(arglist.toArray(new String[arglist.size()]),parser.getAST());
        } catch (ClassNotFoundException e) {
	    System.err.println("Generator class not found: "+generatorclassname);
	    System.exit(1);
        }
    }
}
