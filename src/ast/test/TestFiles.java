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

import junit.framework.TestCase;

import java.io.*;

public class TestFiles extends TestCase
{

    static int debug = 0;

    // Base line test data location relative to root jproto directory
    static String testdata1path = "test/testdata1";

    // Comparison test data location relative to root jproto directory
    static String expectedpath = "test/testdata1";

    // Storage of test case outputs relative to root jproto directory
    static String resultspath = "test/results";

    //////////////////////////////////////////////////
    // Define the test data basenames
    //////////////////////////////////////////////////
    static String[] testfiles = {
	"addressbook",
	"descriptor",
	"example",
	"more_extensions",
	"more_messages",
	"plugin",
	"simplerpc",
	"test-full",
	"test",
	"unittest",
	"unittest_custom_options",
	"unittest_embed_optimize_for",
	"unittest_empty",
	"unittest_enormous_descriptor",
	"unittest_import",
	"unittest_import_lite",
	"unittest_lite",
	"unittest_lite_imports_nonlite",
	"unittest_mset",
	"unittest_no_generic_services",
	"unittest_optimize_for",
/* ignore
	"cpp_test_bad_identifiers",
	"multiple_files_test",
*/
    };

    // define the xfails
    static String[] xfailtests= {
    };

    // For single file experiments
    static String[] xtestfiles = {
	"addressbook"
    };

    //////////////////////////////////////////////////
    String testdir = null;

    public TestFiles(String name, String testdir)
    {
        super(name);
        if (testdir == null) testdir = testdata1path;
        this.testdir = testdir;
    }

}
