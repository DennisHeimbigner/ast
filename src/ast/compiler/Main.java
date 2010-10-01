import unidata.protobuf.ast.compiler.*;

import java.util.*;
import java.io.*;

public class Main
{
    static public void main(String[] argv) throws Exception
    {
	Getopts getopts = new Getopts("Unidata.protobuf",args,"L:d");
        String[] arglist = getopts.argList();
	if(arglist.length == 0) {
	    System.err.println("No input file specified");
	    System.exit(1);
	}
	FileReader rdr = new FileReader(arglist[0]);

        ProtobufParser parser = new ProtobufParser();
	if(getopts.getSwitch('d').set()) parser.setDebugLevel(1);

	boolean pass = parser.parse(arglist[0],rdr);
	if(!pass) {
	    System.err.println("Parse failed");
	    System.exit(1);
	}

	// Execute Semantics
	Semantics sem = new Semantics();
	pass = sem.checksemantics(parser.getAST());
	if(!pass) {
	    System.err.println("Semantic processing failed");
	    System.exit(1);
	}

	// Try to locate the language specific Generator using
	// reflection
	String language = DEFAULTLANGUAGE;
	if(getopts.getSwitch('l').set())
	    language = getopts.getSwitch('l').set()) language = 
	String

    }
}
