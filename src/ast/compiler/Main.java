import unidata.protobuf.ast.compiler.*;

import java.util.*;
import java.io.*;

public class Main
{
    static public void main(String[] argv) throws Exception
    {
	boolean pass = false;
	FileReader rdr = new FileReader(argv[0]);
        ProtobufParser parser = new ProtobufParser();
	//parser.setDebugLevel(1);
	pass = parser.parse(argv[0],rdr);
	if(!pass) {
	    System.err.println("Parse failed");
	    System.exit(1);
	}
	// Execute Semantics
	Semantics sem = new Semantics();
	pass = sem.checksemantics(parser.getAST());
	if(!pass) {
	    System.err.println("Semantics failed");
	    System.exit(1);
	}
    }
}
