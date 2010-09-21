/*
 * Copyright 1998-2009 University Corporation for Atmospheric Research/Unidata
 *
 * Portions of this software were developed by the Unidata Program at the
 * University Corporation for Atmospheric Research.
 *
 * Access and use of this software shall impose the following obligations
 * and understandings on the user. The user is granted the right, without
 * any fee or cost, to use, copy, modify, alter, enhance and distribute
 * this software, and any derivative works thereof, and its supporting
 * documentation for any purpose whatsoever, provided that this entire
 * notice appears in all copies of the software, derivative works and
 * supporting documentation.  Further, UCAR requests that the user credit
 * UCAR/Unidata in any publications that result from the use of this
 * software or in any product that includes this software. The names UCAR
 * and/or Unidata, however, may not be used in any advertising or publicity
 * to endorse or promote any products or commercial entity unless specific
 * written permission is obtained from UCAR/Unidata. The user also
 * understands that UCAR/Unidata is not obligated to provide the user with
 * any support, consulting, training or assistance of any kind with regard
 * to the use, operation and performance of this software nor to provide
 * the user with any updates, revisions, new versions or "bug fixes."
 *
 * THIS SOFTWARE IS PROVIDED BY UCAR/UNIDATA "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL UCAR/UNIDATA BE LIABLE FOR ANY SPECIAL,
 * INDIRECT OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING
 * FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT,
 * NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION
 * WITH THE ACCESS, USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package unidata.protobuf.ast.test;

import unidata.protobuf.ast.compiler.*;

import junit.framework.TestCase;

import java.io.*;
import java.io.FileWriter;

// Test that the Constraint parsing is correct

public class TestAST extends TestFiles
{

    static final String TITLE = "AST construction Tests";

    //////////////////////////////////////////////////
    boolean debug = false;

    boolean generate = true;

    boolean qualified = false;

    //////////////////////////////////////////////////                           
    // Constructors + etc.

    public TestAST(String name)
    {
        super(name,null);
    }

    protected void setUp()
    {
    }

    //////////////////////////////////////////////////

    public void testAST() throws Exception
    {
        if(generate) dogenerate();
        else dotests();
    }

    void dotests() throws Exception
    {
        File file;
        StringWriter content;
        PrintWriter pw;
        boolean pass = true;
	boolean isxfail;
        int ntests;
	String[] tests = null;

	if(xtestfiles.length > 0)
	    tests = xtestfiles;
	else
	    tests = testfiles;
        ntests = tests.length;

        for(int i = 0; i < ntests && pass; i++) {
	    String testname = tests[i];
            String path = testdir + "/" + testname + ".proto";

            loop: for(int j = 1; j < ntests && pass; j++) {
		isxfail = false;
		for(String x: xfailtests) {
		    if(testname.equals(x)) {isxfail = true; break;}
		}
                System.out.println("Testing (" + i + "): " + testname);

                try {
    	            File f = new File(path);
            	    if(!f.canRead()) {
		        System.err.printf("Test %s: cannot read\n",path);
			pass = false;
			break loop;
		    }
		    FileReader rdr = new FileReader(path);
		    ProtobufParser parser = new ProtobufParser();
		    pass = parser.parse(path,rdr);
		    if(!pass)
			System.err.println("Parse failed: "+testname);
		    else {
			// Capture the pretty print output
                        StringWriter writer = new StringWriter();
	                pw = new PrintWriter(writer);
			Debug.print(parser.getAST(),pw,false);
			try {pw.close();} catch (Exception e) {};
			String pretty = writer.toString();
			// Open the corresponding comparison data
			File comparefile = new File(expectedpath + "/" + testname + ".ast");
		        if(!comparefile.canRead()) {
		            System.err.printf("Test %s: cannot read\n",comparefile);
		   	    break loop;
			}
                        // Compare with expected result
 			FileReader expected = new FileReader(comparefile);
			StringReader result = new StringReader(pretty);
                        Diff diff = new Diff(testname);
                        pass = !diff.doDiff(result, expected);
                        try {result.close(); expected.close();
			} catch(IOException ioe) {}
                        junit.framework.Assert.assertTrue(testname, pass);
                    }
                } catch(Exception e) {
                    System.out.println("FAIL: TestAST: " + e.toString());
                    pass = false;
                }
                if(!pass) {
                    System.out.println("***Fail: " + testname);
                } else
                    System.out.println("***Pass: " + testname);
            }
        }
        junit.framework.Assert.assertTrue("TestFile", pass);
    }

    // Generate the expected results rather than testing
    void dogenerate() throws Exception
    {
        File file;
        FileWriter content;
        PrintWriter pw;
        int ntests = testfiles.length;

        for(int i = 0; i < ntests; i++) {
            String testname = testfiles[0];
            String protopath = testdir + "/" + testname + ".proto";
            String prettypath = testdir + "/" + testname + ".ast";

	    // parse and capture the prettyPrint output
            file = new File(protopath);
	    if(!file.canRead()) {
                System.err.println("Generation input not readable: "+protopath);
                continue;
            }
            FileReader rdr = new FileReader(file);
	    ProtobufParser parser = new ProtobufParser();
	    boolean pass = parser.parse(protopath,rdr);
	    assert (pass) : "Parse Failure: "+protopath;
	    if(!pass) continue;
	    // Capture the pretty print output
	    FileWriter capture = new FileWriter(prettypath);
            pw = new PrintWriter(capture);
	    Debug.print(parser.getAST(),pw,qualified);
   	    try {pw.close(); capture.close();} catch (Exception e) {};
        }
    }
}

