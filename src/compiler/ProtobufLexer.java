/*
 * Copyright (c) 1998 - 2010. University Corporation for Atmospheric Research/Unidata
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

package unidata.protobuf.compiler;

import java.io.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
	
import unidata.protobuf.compiler.AST.Position;
import static unidata.protobuf.compiler.ProtobufParser.*;

class ProtobufLexer implements Lexer
{

    /* Define 1 and > 1st legal characters */
    /* Note: '.' is included but legality will be checked by semantic checker */
    static final String wordchars1 =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_.";
    static final String wordcharsn =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_.";

    /* Number characters, but see below for handling leading '.' */
    static final String numchars1 = ".+-0123456789";
    static final String numcharsn = ".+-0123456789Ee";
    // Following are used for hex and octal integers only
    static final String hexcharsn = "0123456789abcdefABCDEF";
    static final String octcharsn = "01234567";

    static String[] keywords = new String[]{
	"import",
	"package",
	"option",
	"message",
	"extend",
	"extensions",
	"enum",
	"service",
	"rpc",
	"returns",
	"default",
	"to",
	"max",
	"required",
	"optional",
	"repeated",
	"group",
	"double",
	"float",
	"int32",
	"int64",
	"uint32",
	"uint64",
	"sint32",
	"sint64",
	"fixed32",
	"fixed64",
	"sfixed32",
	"sfixed64",
	"bool",
	"string",
	"bytes",
	"true",
	"false",
	};

    static int[] keytokens = new int[]{
	IMPORT,
	PACKAGE,
	OPTION,
	MESSAGE,
	EXTEND,
	EXTENSIONS,
	ENUM,
	SERVICE,
	RPC,
	RETURNS,
	DEFAULT,
	TO,
	MAX,
	REQUIRED,
	OPTIONAL,
	REPEATED,
	GROUP,
	DOUBLE,
	FLOAT,
	INT32,
	INT64,
	UINT32,
	UINT64,
	SINT32,
	SINT64,
	FIXED32,
	FIXED64,
	SFIXED32,
	SFIXED64,
	BOOL,
	STRING,
	BYTES,
	TRUE,
	FALSE,
    };

    // structure for file stack
    static class FileEntry
    {
	String filename;
	Reader stream;
	int lineno;
	int charno;
    }

    /**************************************************/
    /* Per-lexer state */

    ProtobufActions parsestate = null;
    String filename = null;
    Reader stream = null;
    StringBuilder input = null;
    StringBuilder yytext = null;
    Position startpos = new Position();
    Position endpos = new Position();
    int lineno = 1;
    int charno = 1;
    Object lval = null;
    StringBuilder lookahead = null;
    Stack<FileEntry> filestack = null;
    boolean eof2 = false;
    List<String> includepaths = null;
    boolean forceidentifier = false;

    /**
     * *********************************************
     */

    /* Constructor(s) */
    public ProtobufLexer(ProtobufActions state)
    {
        reset(state);
    }

    public void reset(ProtobufActions state)
    {
        this.parsestate = state;
        input = new StringBuilder(); /* InputStream so far */
        yytext = new StringBuilder();
        lookahead = new StringBuilder();
        lval = null;
        charno = 1;
	lineno = 1;
	this.stream = null;
        filestack = new Stack<FileEntry>();
    }


    /* Get/Set */

    public void setStream(String filename, Reader stream)
    {
	this.filename = filename;
	this.stream = stream;
    }

    int
    peek() throws IOException
    {
        int c = read();
        pushback(c);
        return c;
    }

    void
    pushback(int c)
    {
        lookahead.insert(0, (char) c);
        charno--;
	if(charno == 0 || charno == '\n') {lineno--; charno = 1;}
    }

    int
    read() throws IOException
    {
        int c;
        if(lookahead.length() == 0) {
            c = stream.read();
            if(c < 0) c = 0;
        } else {
            c = lookahead.charAt(0);
            lookahead.deleteCharAt(0);
        }
        charno++;
	if(c == '\n') {lineno++; charno = 1;}	
        return c;
    }

    /* This is part of the Lexer interface */


    public int
    yylex()
            throws IOException
    {
        int token;
        int c;
        token = 0;
        yytext.setLength(0);

	// Capture start pos
	startpos = new Position(lineno,charno);

        token = -1;

	while(token < 0) {
            c = read();
            if(c == 0) {
                // If stack is not empty, or this is the first eof
                // then return ENDFILE otherwise return true eof
                if(filestack.empty()) {
                    if(eof2) {
                        yytext.append("EOF");
                        token = EOF;
                    } else { // !eof2
                        yytext.append("ENDFILE");
                        token = ENDFILE;
                        eof2 = true;
                    }
                } else { // !filestack.empty()
                    if(!popFileStack())
                        lexerror("Could not pop file stack");
                    yytext.append("ENDFILE");
                    token = ENDFILE;
                }
            } else if(c <= ' ' || c == '\177') {
                /* whitespace: ignore */
            } else if(c == '/' && peek() == '/') { // Comment
                do {c=read(); } while(c != '\0' && c != '\n');
                continue; // start over
            } else if(c == '"' || c == '\'') {
                int quotemark = c;
                boolean more = true;
                while (more && (c = read()) > 0) {
                    if(c == quotemark)
                        more = false;
                    else if(c == '\\') {
                        c = read();
                        if(c < 0) more = false;
                        /* Handle the typical \r \n etc */
                        else switch (c) {
                        case 'n': c = '\n'; break;
                        case 'r': c = '\r'; break;
                        case 't': c = '\t'; break;
                        case 'x': {
                            c = hexescape();
                            if(c < 0) {
                                lexerror("Illegal hex escape character");
                                more = false;
                            }
                        } break;
                        case '0': { // warning, might be less than four digits
                            c = octalescape();
                            if(c < 0) {
                                lexerror("Illegal octal escape character");
                                more = false;
                            }
                        } break;
                        default: break;
                        }
                    }
                    if(more) yytext.append((char)c);
                }
                token = STRINGCONST;
            } else if(numchars1.indexOf(c) >= 0) {
		// Special check for single char "+-."
		int cp = peek();
                if("+-.".indexOf(c) >= 0 && numcharsn.indexOf(cp) < 0) {
		    // single char sign token
		    yytext.append((char)c);
		    token = c;
                    read();
		} else {		
                    // probably a number
                    token = parsenumber(c);
		    if(token == 0) token = NAME; // Name, not a number
		}
            } else if(wordchars1.indexOf(c) >= 0) {
                /* we have a NAME */
                yytext.append((char) c);
                while ((c = read()) > 0) {
                    if(wordcharsn.indexOf(c) < 0) {
                        pushback(c);
                        break;
                    }
                    yytext.append((char) c);
                }
                token = NAME; // Default
                // Check for googleoption
                String tokentext = yytext.toString();
                if(tokentext.startsWith("google.protobuf.")
                   && tokentext.endsWith("Option")) 
                    token = GOOGLEOPTION;
                else if(!forceidentifier) {
                    // check for keyword: treat as case sensitive
                    for(int i=0;i<keywords.length;i++) {
                        if (keywords[i].equals(tokentext)) {
                            token = keytokens[i];
                            break;
                        }
                    }
                }
                forceidentifier = false;
            } else {
                /* we have a single char token */
                token = c;
                yytext.append((char)c);
            }
        }
        if(token < 0) {
            token = 0;
            lval = null;
        } else {
            lval = (yytext.length() == 0 ? (String) null : yytext.toString());
        }
        if(parsestate.getDebugLevel() > 0) dumptoken(token, (String) lval);
        // Capture end pos
        endpos = new Position(lineno,charno);
        return token;       /* Return the type of the token.  */
    }

    int
    parsenumber(int c) throws IOException
    {
	int radix = 10;
	if(c == '-') { // we know this is followed by a digit
	    yytext.append((char)c))
	    c = read(c);
	}
        if(c == '0') {// Hex or octal integer
            yytext.append((char) c);
            int c1 = read();
            if(c1 == 'x' || c1 == 'X') {
	        int c2 = peek();
		if(hexcharsn.indexOf(c2) >= 0) radix = 16;
		else pushback(c1); // encountered 0x... that is 0 followed by a name
            } else if(octcharsn.indexOf(c) >= 0) radix = 8;
            else radix = 10;
        }  else
            yytext.append((char) c);
        boolean more = true;
        while(more) {
            c = read();
            if(radix == 16) more = (hexcharsn.indexOf(c) >= 0);
            else if(radix == 8) more = (octcharsn.indexOf(c) >= 0);
            else if(radix == 10) more = (numcharsn.indexOf(c) >= 0);
            else more = false;
            if(more) yytext.append((char)c);
        }
        pushback(c); 
	int numberkind = 0;
        if(radix == 10) { // decimal int constant or float constant
            // Should be either a decimal integer or decimal float 
            try {
		long number = Long.parseLong(yytext.toString());
                numberkind = INTCONST;
            } catch (NumberFormatException nfe) {};
            if(numberkind == 0) try {
                double number = Double.parseDouble(yytext.toString());
		numberkind = FLOATCONST;
            } catch (NumberFormatException nfe) {};
        } else {// radix == 16 || radix == 8
            try {
                long number = Long.parseLong(yytext.toString(),radix);
                numberkind = INTCONST;
            } catch (NumberFormatException nfe) {}
	}
	return numberkind;
    }

    void
    dumptoken(int token, String lval)
    {
        switch (token) {
        case STRING:
            System.err.printf("TOKEN = |\"%s\"|\n", lval);
            break;
        case NAME:
            System.err.printf("TOKEN = |%s|\n", lval);
            break;
        case INTCONST:
        case FLOATCONST:
            System.err.printf("TOKEN = |%s|\n", lval);
            break;
        default:
            // See if this is a keyword
            for(int i=0;i<keytokens.length;i++) {
                if(keytokens[i] == token) {
                    System.err.printf("TOKEN = |%s|\n",keywords[i]);
                    return;
                }
            }
            System.err.printf("TOKEN = |%c|\n", (char)token);
            break;
        }
    }


    int hexescape() throws IOException
    {
	int[] digits = new int[] {-1,-1};
	int c = read();
	int d;
	for(int i=0;i<2;i++) {
	    d = tohex(c);
	    if(d < 0) {pushback(c); break;}
	    digits[i] = d;
	}
	// shift digits right
	while(digits[1] < 0) {
	    digits[1] = digits[0];
	    digits[0] = 0;
	}
	// Compute constant
	c = (digits[0] << 4) | digits[1];
        return c;
    }

    int octalescape() throws IOException
    {
	int[] digits = new int[] {-1,-1,-1};
	int c = read();
	int d;
	for(int i=0;i<3;i++) {
	    d = tooct(c);
	    if(d < 0) {pushback(c); break;}
	    digits[i] = d;
	}
	// shift digits right
	while(digits[2] < 0) {
	    digits[2] = digits[1];
	    digits[1] = digits[0];
	    digits[0] = 0;
	}
	// Compute octal constant
	c = (digits[0] << 6) | (digits[1] << 3) | digits[2];
        return c;
    }

    static int
    tohex(int c)
    {
        if(c >= 'a' && c <= 'f') return (c - 'a') + 0xa;
        if(c >= 'A' && c <= 'F') return (c - 'A') + 0xa;
        if(c >= '0' && c <= '9') return (c - '0');
        return -1;
    }

    static int
    tooct(int c)
    {
        if(c >= '0' && c <= '7') return (c - '0');
        return -1;
    }

    /**************************************************/
    /* Capture and restore to/from file stack */

    public boolean pushFileStack(String importfile)
	throws IOException
    {
	importfile = Util.locatefile(importfile,includepaths); // use include paths
	File f = new File(importfile);
	if(!f.canRead()) return false;
	FileReader fr = new FileReader(f);
	FileEntry entry = new FileEntry();
	entry.filename = filename;
	entry.stream = stream;
	entry.lineno = endpos.lineno;
	entry.charno = endpos.charno;
	filestack.push(entry);
	startpos.lineno = 1;
        startpos.charno = 1;
	endpos.lineno = 1;
        endpos.charno = 1;
        stream = fr;
        if(Debug.enabled("trace.imports"))
	    System.err.printf("[%d] enter: %s ",filestack.size()-1,filename);
	return true;
    }


    public boolean popFileStack()
    {
	if(filestack.empty()) return false;
	try {stream.close();} catch (IOException ioe) {};
	FileEntry entry = filestack.pop();
	filename = entry.filename;
	stream = entry.stream;
	startpos.lineno = (endpos.lineno = entry.lineno);
	startpos.charno = (endpos.charno = entry.charno);
        if(Debug.enabled("trace.imports"))
	    System.err.printf("[%d] re-enter: %s ",filestack.size(),filename);
	return true;
    }

    /**************************************************/
    /* Lexer Interface */

    /**
     * Method to retrieve the semantic value of the last scanned token.
     *
     * @return the semantic value of the last scanned token.
     */
    public Object getLVal()
    {
        return this.lval;
    }

    /**
     * Entry point for the scanner.	 Returns the token identifier corresponding
     * to the next token and prepares to return the semantic value
     * of the token.
     * @return the token identifier corresponding to the next token. */
    // int yylex() throws ParseException
    // Defined above

    /**
     * Entry point for error reporting.  Emits an error
     * in a user-defined way.
     *
     * @param s The string for the error message.
     */
    public void yyerror(String s)
    {
	System.err.println(String.format("yyerror %s:%s.%s ; %s",
			   filename,lineno,charno,s));
        if(yytext.length() > 0)
            System.err.print("; near |"+ yytext + "|");
        System.err.println();
    }

    public void lexerror(String msg)
    {
        StringBuilder nextline = new StringBuilder();
        int c;
        try {
            while ((c = read()) != -1) {
                if(c == '\n') break;
                nextline.append((char) c);
            }
        } catch (IOException ioe) {
        }
        System.out.printf("Lex error: %s; at %s:%d.%d; %s |%s|\n",
			  msg, filename,lineno,charno, yytext, nextline);
    }

}
