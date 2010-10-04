
import jargs.gnu.CmdLineParser;
import unidata.protobuf.compiler.*;

import java.util.*;
import java.io.*;
//import java.lang.reflection.*;

public class Main
{
    static final String DFALTLANGUAGE = "java";
    static final String DFALTPACKAGE = "unidata.protobuf.compiler";

    static public void main(String[] argv) throws Exception
    {
	CmdLineParser cmdline = new CmdLineParser();
        CmdLineParser.Option optDebug = cmdline.addBooleanOption('D', "Debug");
        CmdLineParser.Option optVerbose = cmdline.addBooleanOption('V', "Verbose");
        CmdLineParser.Option optLanguage = cmdline.addStringOption('l', "language");
	try {
	    cmdline.parse(argv);
        } catch ( CmdLineParser.OptionException e ) {
	    System.err.println("Illegal cmd line option: "+e);
	    System.exit(1);
        }
        Boolean debugValue = (Boolean)cmdline.getOptionValue(optDebug);
	if(debugValue == null) debugValue = Boolean.FALSE;
        String languageValue = (String)cmdline.getOptionValue(optLanguage);
	if(languageValue == null) languageValue = DFALTLANGUAGE;
	// Canonicalize the language name: all lower case except first character
	languageValue = languageValue.substring(0,1).toUpperCase() + languageValue.substring(1).toLowerCase();

        String[] arglist = cmdline.getRemainingArgs();

	if(arglist.length == 0) {
	    System.err.println("No input file specified");
	    System.exit(1);
	}

	File inputfile = new File(arglist[0]);
	if(!inputfile.canRead()) {
	    System.err.println("Cannot read input file: "+inputfile.toString());
	    System.exit(1);
	}
	FileReader rdr = new FileReader(inputfile);

        ProtobufParser parser = new ProtobufParser();
	if(debugValue) parser.setDebugLevel(1);

	boolean pass = parser.parse(arglist[0],rdr);
	if(!pass) {
	    System.err.println("Parse failed");
	    System.exit(1);
	}

	// Semantic Processing
	Semantics sem = new Semantics();
	pass = sem.process(parser.getAST());
	if(!pass) {
	    System.err.println("Semantic processing failed");
	    System.exit(1);
	}

	// Try to locate the language specific Generator using reflection
	String generatorclassname = DFALTPACKAGE + "."+languageValue;
        ClassLoader classLoader = Main.class.getClassLoader();
        try {
            Class generatorclass = Class.forName(generatorclassname);
	    Generator generator = (Generator)generatorclass.newInstance();
	    generator.generate(arglist,parser.getAST());
        } catch (ClassNotFoundException e) {
	    System.err.println("Generator class not found: "+generatorclassname);
	    System.exit(1);
        }
    }
}
