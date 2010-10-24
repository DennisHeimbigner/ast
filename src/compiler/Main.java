
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
    static boolean optionDebug = false;
    static boolean optionParseDebug = false;
    static boolean optionTreeDebug = false;

    static public void main(String[] argv) throws Exception
    {
	int c;
	Getopt g = new Getopt("Main",argv,"-:D:I:L:X:V",null);
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
	    case 'X':
		String xvalue = g.getOptarg();
		for(char x: xvalue.toCharArray()) {
		    switch (x) {
		    case 'd': optionDebug = true; break;
		    case 'p': optionParseDebug = true; break;
		    case 't': optionTreeDebug = true; break;
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
	String escapedfilename = Util.escapedname(rawfilename);
	String inputfilename = Util.locatefile(rawfilename,includePaths);

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

        ProtobufParser parser = new ProtobufParser();
	parser.setIncludePaths(includePaths);
	if(optionParseDebug) parser.setDebugLevel(1);

	boolean pass = parser.parse(rawfilename,rdr);
	if(!pass) {
	    System.err.println("Parse failed");
	    System.exit(1);
	}

	// Semantic Processing
	if(optionTreeDebug) Semantics.debug = true;
	Semantics sem = new Semantics();
	pass = sem.process(parser.getAST());
	if(!pass) {
	    System.err.println("Semantic processing failed");
	    System.exit(1);
	}

	// Try to locate the language specific Generator using reflection
	String generatorclassname = DFALTPACKAGE + "."+optionLanguage;
        ClassLoader classLoader = Main.class.getClassLoader();
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
