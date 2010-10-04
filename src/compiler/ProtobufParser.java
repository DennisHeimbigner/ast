/* A Bison parser, made by GNU Bison 2.4.2.  */

/* Skeleton implementation for Bison LALR(1) parsers in Java
   
      Copyright (C) 2007-2010 Free Software Foundation, Inc.
   
   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.
   
   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.
   
   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.
   
   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

package unidata.protobuf.compiler;
/* First part of user declarations.  */

/* "%code imports" blocks.  */

/* Line 33 of lalr1.java  */
/* Line 19 of "protobuf.y"  */

import java.io.*;
import unidata.protobuf.compiler.AST.Position;



/* Line 33 of lalr1.java  */
/* Line 48 of "./tmp"  */

/**
 * A Bison parser, automatically generated from <tt>protobuf.y</tt>.
 *
 * @author LALR (1) parser skeleton written by Paolo Bonzini.
 */
public class ProtobufParser extends ProtobufActions
{
    /** Version number for the Bison executable that generated this parser.  */
  public static final String bisonVersion = "2.4.2";

  /** Name of the skeleton that generated this parser.  */
  public static final String bisonSkeleton = "lalr1.java";


  /** True if verbose error messages are enabled.  */
  public boolean errorVerbose = true;


  /**
   * A class defining a pair of positions.  Positions, defined by the
   * <code>Position</code> class, denote a point in the input.
   * Locations represent a part of the input through the beginning
   * and ending positions.  */
  public class Location {
    /** The first, inclusive, position in the range.  */
    public Position begin;

    /** The first position beyond the range.  */
    public Position end;

    /**
     * Create a <code>Location</code> denoting an empty range located at
     * a given point.
     * @param loc The position at which the range is anchored.  */
    public Location (Position loc) {
      this.begin = this.end = loc;
    }

    /**
     * Create a <code>Location</code> from the endpoints of the range.
     * @param begin The first position included in the range.
     * @param end   The first position beyond the range.  */
    public Location (Position begin, Position end) {
      this.begin = begin;
      this.end = end;
    }

    /**
     * Print a representation of the location.  For this to be correct,
     * <code>Position</code> should override the <code>equals</code>
     * method.  */
    public String toString () {
      if (begin.equals (end))
        return begin.toString ();
      else
        return begin.toString () + "-" + end.toString ();
    }
  }



  /** Token returned by the scanner to signal the end of its input.  */
  public static final int EOF = 0;

/* Tokens.  */
  /** Token number, to be returned by the scanner.  */
  public static final int IMPORT = 258;
  /** Token number, to be returned by the scanner.  */
  public static final int PACKAGE = 259;
  /** Token number, to be returned by the scanner.  */
  public static final int OPTION = 260;
  /** Token number, to be returned by the scanner.  */
  public static final int MESSAGE = 261;
  /** Token number, to be returned by the scanner.  */
  public static final int EXTEND = 262;
  /** Token number, to be returned by the scanner.  */
  public static final int ENUM = 263;
  /** Token number, to be returned by the scanner.  */
  public static final int SERVICE = 264;
  /** Token number, to be returned by the scanner.  */
  public static final int RPC = 265;
  /** Token number, to be returned by the scanner.  */
  public static final int RETURNS = 266;
  /** Token number, to be returned by the scanner.  */
  public static final int DEFAULT = 267;
  /** Token number, to be returned by the scanner.  */
  public static final int TO = 268;
  /** Token number, to be returned by the scanner.  */
  public static final int MAX = 269;
  /** Token number, to be returned by the scanner.  */
  public static final int REQUIRED = 270;
  /** Token number, to be returned by the scanner.  */
  public static final int OPTIONAL = 271;
  /** Token number, to be returned by the scanner.  */
  public static final int REPEATED = 272;
  /** Token number, to be returned by the scanner.  */
  public static final int DOUBLE = 273;
  /** Token number, to be returned by the scanner.  */
  public static final int FLOAT = 274;
  /** Token number, to be returned by the scanner.  */
  public static final int INT32 = 275;
  /** Token number, to be returned by the scanner.  */
  public static final int INT64 = 276;
  /** Token number, to be returned by the scanner.  */
  public static final int UINT32 = 277;
  /** Token number, to be returned by the scanner.  */
  public static final int UINT64 = 278;
  /** Token number, to be returned by the scanner.  */
  public static final int SINT32 = 279;
  /** Token number, to be returned by the scanner.  */
  public static final int SINT64 = 280;
  /** Token number, to be returned by the scanner.  */
  public static final int FIXED32 = 281;
  /** Token number, to be returned by the scanner.  */
  public static final int FIXED64 = 282;
  /** Token number, to be returned by the scanner.  */
  public static final int SFIXED32 = 283;
  /** Token number, to be returned by the scanner.  */
  public static final int SFIXED64 = 284;
  /** Token number, to be returned by the scanner.  */
  public static final int BOOL = 285;
  /** Token number, to be returned by the scanner.  */
  public static final int STRING = 286;
  /** Token number, to be returned by the scanner.  */
  public static final int BYTES = 287;
  /** Token number, to be returned by the scanner.  */
  public static final int GOOGLEOPTION = 288;
  /** Token number, to be returned by the scanner.  */
  public static final int ENDFILE = 289;
  /** Token number, to be returned by the scanner.  */
  public static final int IDENTIFIER = 290;
  /** Token number, to be returned by the scanner.  */
  public static final int INTCONST = 291;
  /** Token number, to be returned by the scanner.  */
  public static final int FLOATCONST = 292;
  /** Token number, to be returned by the scanner.  */
  public static final int STRINGCONST = 293;
  /** Token number, to be returned by the scanner.  */
  public static final int TRUE = 294;
  /** Token number, to be returned by the scanner.  */
  public static final int FALSE = 295;



  
  private Location yylloc (YYStack rhs, int n)
  {
    if (n > 0)
      return new Location (rhs.locationAt (1).begin, rhs.locationAt (n).end);
    else
      return new Location (rhs.locationAt (0).end);
  }

  /**
   * Communication interface between the scanner and the Bison-generated
   * parser <tt>ProtobufParser</tt>.
   */
  public interface Lexer {
    /**
     * Method to retrieve the beginning position of the last scanned token.
     * @return the position at which the last scanned token starts.  */
    Position getStartPos ();

    /**
     * Method to retrieve the ending position of the last scanned token.
     * @return the first position beyond the last scanned token.  */
    Position getEndPos ();

    /**
     * Method to retrieve the semantic value of the last scanned token.
     * @return the semantic value of the last scanned token.  */
    Object getLVal ();

    /**
     * Entry point for the scanner.  Returns the token identifier corresponding
     * to the next token and prepares to return the semantic value
     * and beginning/ending positions of the token.
     * @return the token identifier corresponding to the next token. */
    int yylex () throws IOException;

    /**
     * Entry point for error reporting.  Emits an error
     * referring to the given location in a user-defined way.
     *
     * @param loc The location of the element to which the
     *                error message is related
     * @param s The string for the error message.  */
     void yyerror (Location loc, String s);
  }

  /** The object doing lexical analysis for us.  */
  private Lexer yylexer;
  
  



  /**
   * Instantiates the Bison-generated parser.
   * @param yylexer The scanner that will supply tokens to the parser.
   */
  public ProtobufParser (Lexer yylexer) {
    this.yylexer = yylexer;
    
  }

  private java.io.PrintStream yyDebugStream = System.err;

  /**
   * Return the <tt>PrintStream</tt> on which the debugging output is
   * printed.
   */
  public final java.io.PrintStream getDebugStream () { return yyDebugStream; }

  /**
   * Set the <tt>PrintStream</tt> on which the debug output is printed.
   * @param s The stream that is used for debugging output.
   */
  public final void setDebugStream(java.io.PrintStream s) { yyDebugStream = s; }

  private int yydebug = 0;

  /**
   * Answer the verbosity of the debugging output; 0 means that all kinds of
   * output from the parser are suppressed.
   */
  public final int getDebugLevel() { return yydebug; }

  /**
   * Set the verbosity of the debugging output; 0 means that all kinds of
   * output from the parser are suppressed.
   * @param level The verbosity level for debugging output.
   */
  public final void setDebugLevel(int level) { yydebug = level; }

  private final int yylex () throws IOException {
    return yylexer.yylex ();
  }
  protected final void yyerror (Location loc, String s) {
    yylexer.yyerror (loc, s);
  }

  
  protected final void yyerror (String s) {
    yylexer.yyerror ((Location)null, s);
  }
  protected final void yyerror (Position loc, String s) {
    yylexer.yyerror (new Location (loc), s);
  }

  protected final void yycdebug (String s) {
    if (yydebug > 0)
      yyDebugStream.println (s);
  }

  private final class YYStack {
    private int[] stateStack = new int[16];
    private Location[] locStack = new Location[16];
    private Object[] valueStack = new Object[16];

    public int size = 16;
    public int height = -1;

    public final void push (int state, Object value			    , Location loc) {
      height++;
      if (size == height)
        {
	  int[] newStateStack = new int[size * 2];
	  System.arraycopy (stateStack, 0, newStateStack, 0, height);
	  stateStack = newStateStack;
	  
	  Location[] newLocStack = new Location[size * 2];
	  System.arraycopy (locStack, 0, newLocStack, 0, height);
	  locStack = newLocStack;

	  Object[] newValueStack = new Object[size * 2];
	  System.arraycopy (valueStack, 0, newValueStack, 0, height);
	  valueStack = newValueStack;

	  size *= 2;
	}

      stateStack[height] = state;
      locStack[height] = loc;
      valueStack[height] = value;
    }

    public final void pop () {
      height--;
    }

    public final void pop (int num) {
      // Avoid memory leaks... garbage collection is a white lie!
      if (num > 0) {
	java.util.Arrays.fill (valueStack, height - num + 1, height, null);
        java.util.Arrays.fill (locStack, height - num + 1, height, null);
      }
      height -= num;
    }

    public final int stateAt (int i) {
      return stateStack[height - i];
    }

    public final Location locationAt (int i) {
      return locStack[height - i];
    }

    public final Object valueAt (int i) {
      return valueStack[height - i];
    }

    // Print the state stack on the debug stream.
    public void print (java.io.PrintStream out)
    {
      out.print ("Stack now");

      for (int i = 0; i < height; i++)
        {
	  out.print (' ');
	  out.print (stateStack[i]);
        }
      out.println ();
    }
  }

  /**
   * Returned by a Bison action in order to stop the parsing process and
   * return success (<tt>true</tt>).  */
  public static final int YYACCEPT = 0;

  /**
   * Returned by a Bison action in order to stop the parsing process and
   * return failure (<tt>false</tt>).  */
  public static final int YYABORT = 1;

  /**
   * Returned by a Bison action in order to start error recovery without
   * printing an error message.  */
  public static final int YYERROR = 2;

  /**
   * Returned by a Bison action in order to print an error message and start
   * error recovery.  Formally deprecated in Bison 2.4.2's NEWS entry, where
   * a plan to phase it out is discussed.  */
  public static final int YYFAIL = 3;

  private static final int YYNEWSTATE = 4;
  private static final int YYDEFAULT = 5;
  private static final int YYREDUCE = 6;
  private static final int YYERRLAB1 = 7;
  private static final int YYRETURN = 8;

  private int yyerrstatus_ = 0;

  /**
   * Return whether error recovery is being done.  In this state, the parser
   * reads token until it reaches a known state, and then restarts normal
   * operation.  */
  public final boolean recovering ()
  {
    return yyerrstatus_ == 0;
  }

  private int yyaction (int yyn, YYStack yystack, int yylen) throws IOException
  {
    Object yyval;
    Location yyloc = yylloc (yystack, yylen);

    /* If YYLEN is nonzero, implement the default value of the action:
       `$$ = $1'.  Otherwise, use the top of the stack.

       Otherwise, the following line sets YYVAL to garbage.
       This behavior is undocumented and Bison
       users should not rely upon it.  */
    if (yylen > 0)
      yyval = yystack.valueAt (yylen - 1);
    else
      yyval = yystack.valueAt (0);

    yy_reduce_print (yyn, yystack);

    switch (yyn)
      {
	  case 2:
  if (yyn == 2)
    
/* Line 354 of lalr1.java  */
/* Line 72 of "protobuf.y"  */
    {setLocation(yyloc);protobufroot(((yystack.valueAt (1-(1)))));};
  break;
    

  case 3:
  if (yyn == 3)
    
/* Line 354 of lalr1.java  */
/* Line 76 of "protobuf.y"  */
    {setLocation(yyloc);yyval=protobuffile(((yystack.valueAt (4-(1)))),((yystack.valueAt (4-(2)))),((yystack.valueAt (4-(3)))));};
  break;
    

  case 4:
  if (yyn == 4)
    
/* Line 354 of lalr1.java  */
/* Line 80 of "protobuf.y"  */
    {setLocation(yyloc);yyval=packagedecl(null);};
  break;
    

  case 5:
  if (yyn == 5)
    
/* Line 354 of lalr1.java  */
/* Line 82 of "protobuf.y"  */
    {setLocation(yyloc);yyval=packagedecl(((yystack.valueAt (3-(2)))));};
  break;
    

  case 6:
  if (yyn == 6)
    
/* Line 354 of lalr1.java  */
/* Line 87 of "protobuf.y"  */
    {setLocation(yyloc);yyval=importlist(null,null);};
  break;
    

  case 7:
  if (yyn == 7)
    
/* Line 354 of lalr1.java  */
/* Line 89 of "protobuf.y"  */
    {setLocation(yyloc);yyval=importlist(((yystack.valueAt (2-(1)))),((yystack.valueAt (2-(2)))));};
  break;
    

  case 8:
  if (yyn == 8)
    
/* Line 354 of lalr1.java  */
/* Line 94 of "protobuf.y"  */
    {setLocation(yyloc);importstmt(((yystack.valueAt (4-(2)))),((yystack.valueAt (4-(4)))));};
  break;
    

  case 9:
  if (yyn == 9)
    
/* Line 354 of lalr1.java  */
/* Line 99 of "protobuf.y"  */
    {setLocation(yyloc);yyval=decllist(null,null);};
  break;
    

  case 10:
  if (yyn == 10)
    
/* Line 354 of lalr1.java  */
/* Line 101 of "protobuf.y"  */
    {setLocation(yyloc);yyval=decllist(((yystack.valueAt (2-(1)))),((yystack.valueAt (2-(2)))));};
  break;
    

  case 11:
  if (yyn == 11)
    
/* Line 354 of lalr1.java  */
/* Line 105 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 12:
  if (yyn == 12)
    
/* Line 354 of lalr1.java  */
/* Line 106 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 13:
  if (yyn == 13)
    
/* Line 354 of lalr1.java  */
/* Line 107 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 14:
  if (yyn == 14)
    
/* Line 354 of lalr1.java  */
/* Line 108 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 15:
  if (yyn == 15)
    
/* Line 354 of lalr1.java  */
/* Line 109 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 16:
  if (yyn == 16)
    
/* Line 354 of lalr1.java  */
/* Line 110 of "protobuf.y"  */
    {yyval=null;};
  break;
    

  case 17:
  if (yyn == 17)
    
/* Line 354 of lalr1.java  */
/* Line 115 of "protobuf.y"  */
    {yyval=((yystack.valueAt (3-(2))));};
  break;
    

  case 18:
  if (yyn == 18)
    
/* Line 354 of lalr1.java  */
/* Line 117 of "protobuf.y"  */
    {setLocation(yyloc);yyval=useroption(((yystack.valueAt (5-(3)))));};
  break;
    

  case 19:
  if (yyn == 19)
    
/* Line 354 of lalr1.java  */
/* Line 122 of "protobuf.y"  */
    {setLocation(yyloc);yyval=option(((yystack.valueAt (3-(1)))),((yystack.valueAt (3-(3)))));};
  break;
    

  case 20:
  if (yyn == 20)
    
/* Line 354 of lalr1.java  */
/* Line 127 of "protobuf.y"  */
    {setLocation(yyloc);yyval=message(((yystack.valueAt (3-(2)))),((yystack.valueAt (3-(3)))));};
  break;
    

  case 21:
  if (yyn == 21)
    
/* Line 354 of lalr1.java  */
/* Line 132 of "protobuf.y"  */
    {setLocation(yyloc);yyval=extend(((yystack.valueAt (5-(2)))),((yystack.valueAt (5-(4)))));};
  break;
    

  case 22:
  if (yyn == 22)
    
/* Line 354 of lalr1.java  */
/* Line 134 of "protobuf.y"  */
    {yyval=null; /* ignore */ };
  break;
    

  case 23:
  if (yyn == 23)
    
/* Line 354 of lalr1.java  */
/* Line 139 of "protobuf.y"  */
    {setLocation(yyloc);yyval=fieldlist(null,null);};
  break;
    

  case 24:
  if (yyn == 24)
    
/* Line 354 of lalr1.java  */
/* Line 141 of "protobuf.y"  */
    {setLocation(yyloc);yyval=fieldlist(((yystack.valueAt (2-(1)))),((yystack.valueAt (2-(2)))));};
  break;
    

  case 25:
  if (yyn == 25)
    
/* Line 354 of lalr1.java  */
/* Line 143 of "protobuf.y"  */
    {yyval=((yystack.valueAt (2-(1))));};
  break;
    

  case 26:
  if (yyn == 26)
    
/* Line 354 of lalr1.java  */
/* Line 148 of "protobuf.y"  */
    {setLocation(yyloc);yyval=enumtype(((yystack.valueAt (5-(2)))),((yystack.valueAt (5-(4)))));};
  break;
    

  case 27:
  if (yyn == 27)
    
/* Line 354 of lalr1.java  */
/* Line 153 of "protobuf.y"  */
    {setLocation(yyloc);yyval=enumlist(null,null);};
  break;
    

  case 28:
  if (yyn == 28)
    
/* Line 354 of lalr1.java  */
/* Line 155 of "protobuf.y"  */
    {setLocation(yyloc);yyval=enumlist(((yystack.valueAt (2-(1)))),((yystack.valueAt (2-(2)))));};
  break;
    

  case 29:
  if (yyn == 29)
    
/* Line 354 of lalr1.java  */
/* Line 157 of "protobuf.y"  */
    {setLocation(yyloc);yyval=enumlist(((yystack.valueAt (2-(1)))),((yystack.valueAt (2-(2)))));};
  break;
    

  case 30:
  if (yyn == 30)
    
/* Line 354 of lalr1.java  */
/* Line 158 of "protobuf.y"  */
    {yyval=((yystack.valueAt (2-(1))));};
  break;
    

  case 31:
  if (yyn == 31)
    
/* Line 354 of lalr1.java  */
/* Line 163 of "protobuf.y"  */
    {setLocation(yyloc);if((yyval=enumfield(((yystack.valueAt (3-(1)))),((yystack.valueAt (3-(3))))))==null) {return YYABORT;}};
  break;
    

  case 32:
  if (yyn == 32)
    
/* Line 354 of lalr1.java  */
/* Line 168 of "protobuf.y"  */
    {setLocation(yyloc);yyval=service(((yystack.valueAt (5-(2)))),((yystack.valueAt (5-(4)))));};
  break;
    

  case 33:
  if (yyn == 33)
    
/* Line 354 of lalr1.java  */
/* Line 173 of "protobuf.y"  */
    {setLocation(yyloc);yyval=servicecaselist(null,null);};
  break;
    

  case 34:
  if (yyn == 34)
    
/* Line 354 of lalr1.java  */
/* Line 175 of "protobuf.y"  */
    {setLocation(yyloc);yyval=servicecaselist(((yystack.valueAt (2-(1)))),((yystack.valueAt (2-(2)))));};
  break;
    

  case 35:
  if (yyn == 35)
    
/* Line 354 of lalr1.java  */
/* Line 179 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 36:
  if (yyn == 36)
    
/* Line 354 of lalr1.java  */
/* Line 180 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 37:
  if (yyn == 37)
    
/* Line 354 of lalr1.java  */
/* Line 181 of "protobuf.y"  */
    {yyval=null;};
  break;
    

  case 38:
  if (yyn == 38)
    
/* Line 354 of lalr1.java  */
/* Line 186 of "protobuf.y"  */
    {setLocation(yyloc);yyval=rpc(((yystack.valueAt (10-(2)))),((yystack.valueAt (10-(4)))),((yystack.valueAt (10-(8)))));};
  break;
    

  case 39:
  if (yyn == 39)
    
/* Line 354 of lalr1.java  */
/* Line 191 of "protobuf.y"  */
    {yyval=((yystack.valueAt (3-(2))));};
  break;
    

  case 40:
  if (yyn == 40)
    
/* Line 354 of lalr1.java  */
/* Line 196 of "protobuf.y"  */
    {setLocation(yyloc);yyval=messageelementlist(null,null);};
  break;
    

  case 41:
  if (yyn == 41)
    
/* Line 354 of lalr1.java  */
/* Line 198 of "protobuf.y"  */
    {setLocation(yyloc);yyval=messageelementlist(((yystack.valueAt (2-(1)))),((yystack.valueAt (2-(2)))));};
  break;
    

  case 42:
  if (yyn == 42)
    
/* Line 354 of lalr1.java  */
/* Line 202 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 43:
  if (yyn == 43)
    
/* Line 354 of lalr1.java  */
/* Line 203 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 44:
  if (yyn == 44)
    
/* Line 354 of lalr1.java  */
/* Line 204 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 45:
  if (yyn == 45)
    
/* Line 354 of lalr1.java  */
/* Line 205 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 46:
  if (yyn == 46)
    
/* Line 354 of lalr1.java  */
/* Line 206 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 47:
  if (yyn == 47)
    
/* Line 354 of lalr1.java  */
/* Line 207 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 48:
  if (yyn == 48)
    
/* Line 354 of lalr1.java  */
/* Line 208 of "protobuf.y"  */
    {yyval=null;};
  break;
    

  case 49:
  if (yyn == 49)
    
/* Line 354 of lalr1.java  */
/* Line 214 of "protobuf.y"  */
    {setLocation(yyloc);yyval=field(((yystack.valueAt (6-(1)))),((yystack.valueAt (6-(2)))),((yystack.valueAt (6-(3)))),((yystack.valueAt (6-(5)))),null);};
  break;
    

  case 50:
  if (yyn == 50)
    
/* Line 354 of lalr1.java  */
/* Line 216 of "protobuf.y"  */
    {setLocation(yyloc);yyval=field(((yystack.valueAt (9-(1)))),((yystack.valueAt (9-(2)))),((yystack.valueAt (9-(3)))),((yystack.valueAt (9-(5)))),((yystack.valueAt (9-(7)))));};
  break;
    

  case 51:
  if (yyn == 51)
    
/* Line 354 of lalr1.java  */
/* Line 221 of "protobuf.y"  */
    {setLocation(yyloc);yyval=fieldoptionlist(null,((yystack.valueAt (1-(1)))));};
  break;
    

  case 52:
  if (yyn == 52)
    
/* Line 354 of lalr1.java  */
/* Line 223 of "protobuf.y"  */
    {setLocation(yyloc);yyval=fieldoptionlist(((yystack.valueAt (3-(1)))),((yystack.valueAt (3-(3)))));};
  break;
    

  case 53:
  if (yyn == 53)
    
/* Line 354 of lalr1.java  */
/* Line 228 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 54:
  if (yyn == 54)
    
/* Line 354 of lalr1.java  */
/* Line 230 of "protobuf.y"  */
    {setLocation(yyloc);yyval=option(AST.DEFAULTNAME,((yystack.valueAt (3-(3)))));};
  break;
    

  case 55:
  if (yyn == 55)
    
/* Line 354 of lalr1.java  */
/* Line 235 of "protobuf.y"  */
    {yyval=((yystack.valueAt (2-(1))));};
  break;
    

  case 56:
  if (yyn == 56)
    
/* Line 354 of lalr1.java  */
/* Line 240 of "protobuf.y"  */
    {setLocation(yyloc);yyval=extensionlist(null,((yystack.valueAt (1-(1)))));};
  break;
    

  case 57:
  if (yyn == 57)
    
/* Line 354 of lalr1.java  */
/* Line 242 of "protobuf.y"  */
    {setLocation(yyloc);yyval=extensionlist(((yystack.valueAt (3-(1)))),((yystack.valueAt (3-(3)))));};
  break;
    

  case 58:
  if (yyn == 58)
    
/* Line 354 of lalr1.java  */
/* Line 247 of "protobuf.y"  */
    {setLocation(yyloc);if((yyval=extensionrange(((yystack.valueAt (1-(1)))),null)) == null) return YYABORT;};
  break;
    

  case 59:
  if (yyn == 59)
    
/* Line 354 of lalr1.java  */
/* Line 249 of "protobuf.y"  */
    {setLocation(yyloc);if((yyval=extensionrange(((yystack.valueAt (3-(1)))),((yystack.valueAt (3-(3)))))) == null) return YYABORT;};
  break;
    

  case 60:
  if (yyn == 60)
    
/* Line 354 of lalr1.java  */
/* Line 251 of "protobuf.y"  */
    {setLocation(yyloc);if((yyval=extensionrange(((yystack.valueAt (3-(1)))),null)) == null) return YYABORT;};
  break;
    

  case 61:
  if (yyn == 61)
    
/* Line 354 of lalr1.java  */
/* Line 255 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 62:
  if (yyn == 62)
    
/* Line 354 of lalr1.java  */
/* Line 256 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 63:
  if (yyn == 63)
    
/* Line 354 of lalr1.java  */
/* Line 257 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 64:
  if (yyn == 64)
    
/* Line 354 of lalr1.java  */
/* Line 261 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 65:
  if (yyn == 65)
    
/* Line 354 of lalr1.java  */
/* Line 262 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 66:
  if (yyn == 66)
    
/* Line 354 of lalr1.java  */
/* Line 263 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 67:
  if (yyn == 67)
    
/* Line 354 of lalr1.java  */
/* Line 264 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 68:
  if (yyn == 68)
    
/* Line 354 of lalr1.java  */
/* Line 265 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 69:
  if (yyn == 69)
    
/* Line 354 of lalr1.java  */
/* Line 266 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 70:
  if (yyn == 70)
    
/* Line 354 of lalr1.java  */
/* Line 267 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 71:
  if (yyn == 71)
    
/* Line 354 of lalr1.java  */
/* Line 268 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 72:
  if (yyn == 72)
    
/* Line 354 of lalr1.java  */
/* Line 269 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 73:
  if (yyn == 73)
    
/* Line 354 of lalr1.java  */
/* Line 270 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 74:
  if (yyn == 74)
    
/* Line 354 of lalr1.java  */
/* Line 271 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 75:
  if (yyn == 75)
    
/* Line 354 of lalr1.java  */
/* Line 272 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 76:
  if (yyn == 76)
    
/* Line 354 of lalr1.java  */
/* Line 273 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 77:
  if (yyn == 77)
    
/* Line 354 of lalr1.java  */
/* Line 274 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 78:
  if (yyn == 78)
    
/* Line 354 of lalr1.java  */
/* Line 275 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 79:
  if (yyn == 79)
    
/* Line 354 of lalr1.java  */
/* Line 276 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 80:
  if (yyn == 80)
    
/* Line 354 of lalr1.java  */
/* Line 282 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 81:
  if (yyn == 81)
    
/* Line 354 of lalr1.java  */
/* Line 286 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 82:
  if (yyn == 82)
    
/* Line 354 of lalr1.java  */
/* Line 290 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 83:
  if (yyn == 83)
    
/* Line 354 of lalr1.java  */
/* Line 291 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 84:
  if (yyn == 84)
    
/* Line 354 of lalr1.java  */
/* Line 292 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 85:
  if (yyn == 85)
    
/* Line 354 of lalr1.java  */
/* Line 293 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 86:
  if (yyn == 86)
    
/* Line 354 of lalr1.java  */
/* Line 294 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 87:
  if (yyn == 87)
    
/* Line 354 of lalr1.java  */
/* Line 295 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 88:
  if (yyn == 88)
    
/* Line 354 of lalr1.java  */
/* Line 301 of "protobuf.y"  */
    {if((yyval=identifier(((yystack.valueAt (1-(1))))))==null) {return YYABORT;}};
  break;
    



/* Line 354 of lalr1.java  */
/* Line 1220 of "./tmp"  */
	default: break;
      }

    yy_symbol_print ("-> $$ =", yyr1_[yyn], yyval, yyloc);

    yystack.pop (yylen);
    yylen = 0;

    /* Shift the result of the reduction.  */
    yyn = yyr1_[yyn];
    int yystate = yypgoto_[yyn - yyntokens_] + yystack.stateAt (0);
    if (0 <= yystate && yystate <= yylast_
	&& yycheck_[yystate] == yystack.stateAt (0))
      yystate = yytable_[yystate];
    else
      yystate = yydefgoto_[yyn - yyntokens_];

    yystack.push (yystate, yyval, yyloc);
    return YYNEWSTATE;
  }

  /* Return YYSTR after stripping away unnecessary quotes and
     backslashes, so that it's suitable for yyerror.  The heuristic is
     that double-quoting is unnecessary unless the string contains an
     apostrophe, a comma, or backslash (other than backslash-backslash).
     YYSTR is taken from yytname.  */
  private final String yytnamerr_ (String yystr)
  {
    if (yystr.charAt (0) == '"')
      {
        StringBuffer yyr = new StringBuffer ();
        strip_quotes: for (int i = 1; i < yystr.length (); i++)
          switch (yystr.charAt (i))
            {
            case '\'':
            case ',':
              break strip_quotes;

            case '\\':
	      if (yystr.charAt(++i) != '\\')
                break strip_quotes;
              /* Fall through.  */
            default:
              yyr.append (yystr.charAt (i));
              break;

            case '"':
              return yyr.toString ();
            }
      }
    else if (yystr.equals ("$end"))
      return "end of input";

    return yystr;
  }

  /*--------------------------------.
  | Print this symbol on YYOUTPUT.  |
  `--------------------------------*/

  private void yy_symbol_print (String s, int yytype,
			         Object yyvaluep				 , Object yylocationp)
  {
    if (yydebug > 0)
    yycdebug (s + (yytype < yyntokens_ ? " token " : " nterm ")
	      + yytname_[yytype] + " ("
	      + yylocationp + ": "
	      + (yyvaluep == null ? "(null)" : yyvaluep.toString ()) + ")");
  }

  /**
   * Parse input from the scanner that was specified at object construction
   * time.  Return whether the end of the input was reached successfully.
   *
   * @return <tt>true</tt> if the parsing succeeds.  Note that this does not
   *          imply that there were no syntax errors.
   */
  public boolean parse () throws IOException, IOException
  {
    /// Lookahead and lookahead in internal form.
    int yychar = yyempty_;
    int yytoken = 0;

    /* State.  */
    int yyn = 0;
    int yylen = 0;
    int yystate = 0;

    YYStack yystack = new YYStack ();

    /* Error handling.  */
    int yynerrs_ = 0;
    /// The location where the error started.
    Location yyerrloc = null;

    /// Location of the lookahead.
    Location yylloc = new Location (new Position(),new Position());

    /// @$.
    Location yyloc;

    /// Semantic value of the lookahead.
    Object yylval = null;

    int yyresult;

    yycdebug ("Starting parse\n");
    yyerrstatus_ = 0;


    /* Initialize the stack.  */
    yystack.push (yystate, yylval, yylloc);

    int label = YYNEWSTATE;
    for (;;)
      switch (label)
      {
        /* New state.  Unlike in the C/C++ skeletons, the state is already
	   pushed when we come here.  */
      case YYNEWSTATE:
        yycdebug ("Entering state " + yystate + "\n");
        if (yydebug > 0)
          yystack.print (yyDebugStream);

        /* Accept?  */
        if (yystate == yyfinal_)
          return true;

        /* Take a decision.  First try without lookahead.  */
        yyn = yypact_[yystate];
        if (yyn == yypact_ninf_)
          {
            label = YYDEFAULT;
	    break;
          }

        /* Read a lookahead token.  */
        if (yychar == yyempty_)
          {
	    yycdebug ("Reading a token: ");
	    yychar = yylex ();
            
	    yylloc = new Location(yylexer.getStartPos (),
				            yylexer.getEndPos ());
            yylval = yylexer.getLVal ();
          }

        /* Convert token to internal form.  */
        if (yychar <= EOF)
          {
	    yychar = yytoken = EOF;
	    yycdebug ("Now at end of input.\n");
          }
        else
          {
	    yytoken = yytranslate_ (yychar);
	    yy_symbol_print ("Next token is", yytoken,
			     yylval, yylloc);
          }

        /* If the proper action on seeing token YYTOKEN is to reduce or to
           detect an error, take that action.  */
        yyn += yytoken;
        if (yyn < 0 || yylast_ < yyn || yycheck_[yyn] != yytoken)
          label = YYDEFAULT;

        /* <= 0 means reduce or error.  */
        else if ((yyn = yytable_[yyn]) <= 0)
          {
	    if (yyn == 0 || yyn == yytable_ninf_)
	      label = YYFAIL;
	    else
	      {
	        yyn = -yyn;
	        label = YYREDUCE;
	      }
          }

        else
          {
            /* Shift the lookahead token.  */
	    yy_symbol_print ("Shifting", yytoken,
			     yylval, yylloc);

            /* Discard the token being shifted.  */
            yychar = yyempty_;

            /* Count tokens shifted since error; after three, turn off error
               status.  */
            if (yyerrstatus_ > 0)
              --yyerrstatus_;

            yystate = yyn;
            yystack.push (yystate, yylval, yylloc);
            label = YYNEWSTATE;
          }
        break;

      /*-----------------------------------------------------------.
      | yydefault -- do the default action for the current state.  |
      `-----------------------------------------------------------*/
      case YYDEFAULT:
        yyn = yydefact_[yystate];
        if (yyn == 0)
          label = YYFAIL;
        else
          label = YYREDUCE;
        break;

      /*-----------------------------.
      | yyreduce -- Do a reduction.  |
      `-----------------------------*/
      case YYREDUCE:
        yylen = yyr2_[yyn];
        label = yyaction (yyn, yystack, yylen);
	yystate = yystack.stateAt (0);
        break;

      /*------------------------------------.
      | yyerrlab -- here on detecting error |
      `------------------------------------*/
      case YYFAIL:
        /* If not already recovering from an error, report this error.  */
        if (yyerrstatus_ == 0)
          {
	    ++yynerrs_;
	    yyerror (yylloc, yysyntax_error (yystate, yytoken));
          }

        yyerrloc = yylloc;
        if (yyerrstatus_ == 3)
          {
	    /* If just tried and failed to reuse lookahead token after an
	     error, discard it.  */

	    if (yychar <= EOF)
	      {
	      /* Return failure if at end of input.  */
	      if (yychar == EOF)
	        return false;
	      }
	    else
	      yychar = yyempty_;
          }

        /* Else will try to reuse lookahead token after shifting the error
           token.  */
        label = YYERRLAB1;
        break;

      /*---------------------------------------------------.
      | errorlab -- error raised explicitly by YYERROR.  |
      `---------------------------------------------------*/
      case YYERROR:

        yyerrloc = yystack.locationAt (yylen - 1);
        /* Do not reclaim the symbols of the rule which action triggered
           this YYERROR.  */
        yystack.pop (yylen);
        yylen = 0;
        yystate = yystack.stateAt (0);
        label = YYERRLAB1;
        break;

      /*-------------------------------------------------------------.
      | yyerrlab1 -- common code for both syntax error and YYERROR.  |
      `-------------------------------------------------------------*/
      case YYERRLAB1:
        yyerrstatus_ = 3;	/* Each real token shifted decrements this.  */

        for (;;)
          {
	    yyn = yypact_[yystate];
	    if (yyn != yypact_ninf_)
	      {
	        yyn += yyterror_;
	        if (0 <= yyn && yyn <= yylast_ && yycheck_[yyn] == yyterror_)
	          {
	            yyn = yytable_[yyn];
	            if (0 < yyn)
		      break;
	          }
	      }

	    /* Pop the current state because it cannot handle the error token.  */
	    if (yystack.height == 1)
	      return false;

	    yyerrloc = yystack.locationAt (0);
	    yystack.pop ();
	    yystate = yystack.stateAt (0);
	    if (yydebug > 0)
	      yystack.print (yyDebugStream);
          }

	
	/* Muck with the stack to setup for yylloc.  */
	yystack.push (0, null, yylloc);
	yystack.push (0, null, yyerrloc);
        yyloc = yylloc (yystack, 2);
	yystack.pop (2);

        /* Shift the error token.  */
        yy_symbol_print ("Shifting", yystos_[yyn],
			 yylval, yyloc);

        yystate = yyn;
	yystack.push (yyn, yylval, yyloc);
        label = YYNEWSTATE;
        break;

        /* Accept.  */
      case YYACCEPT:
        return true;

        /* Abort.  */
      case YYABORT:
        return false;
      }
  }

  // Generate an error message.
  private String yysyntax_error (int yystate, int tok)
  {
    if (errorVerbose)
      {
        int yyn = yypact_[yystate];
        if (yypact_ninf_ < yyn && yyn <= yylast_)
          {
	    StringBuffer res;

	    /* Start YYX at -YYN if negative to avoid negative indexes in
	       YYCHECK.  */
	    int yyxbegin = yyn < 0 ? -yyn : 0;

	    /* Stay within bounds of both yycheck and yytname.  */
	    int yychecklim = yylast_ - yyn + 1;
	    int yyxend = yychecklim < yyntokens_ ? yychecklim : yyntokens_;
	    int count = 0;
	    for (int x = yyxbegin; x < yyxend; ++x)
	      if (yycheck_[x + yyn] == x && x != yyterror_)
	        ++count;

	    // FIXME: This method of building the message is not compatible
	    // with internationalization.
	    res = new StringBuffer ("syntax error, unexpected ");
	    res.append (yytnamerr_ (yytname_[tok]));
	    if (count < 5)
	      {
	        count = 0;
	        for (int x = yyxbegin; x < yyxend; ++x)
	          if (yycheck_[x + yyn] == x && x != yyterror_)
		    {
		      res.append (count++ == 0 ? ", expecting " : " or ");
		      res.append (yytnamerr_ (yytname_[x]));
		    }
	      }
	    return res.toString ();
          }
      }

    return "syntax error";
  }


  /* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
     STATE-NUM.  */
  private static final byte yypact_ninf_ = -90;
  private static final byte yypact_[] =
  {
         6,    23,    67,   -90,   -90,   -90,    27,   -90,    69,   -90,
      40,   -90,    19,    32,     5,    23,    16,    23,    23,   -90,
     -90,   -90,   -90,   -90,   -90,   -90,   -90,     6,    23,    33,
      34,    31,    35,    36,   -90,    37,    38,   -90,    41,   -90,
      26,   -90,   -90,   -90,   -90,   -90,   -90,    60,   -90,   -90,
     -90,   -90,   -90,   -90,   -90,    -4,    -8,     4,     0,    13,
     -90,   -90,   -90,   -90,    64,   -90,   -90,   -90,   -90,   -90,
     -90,   -90,   -90,    30,    61,   -90,    68,   -90,   -90,   -90,
     -90,   -90,   -90,   -90,   -90,    62,    23,   -90,   -90,   -90,
     -90,   -90,     8,    71,   -90,   -90,   -90,   -90,   -90,   -90,
     -90,   -90,   -90,   -90,   -90,   -90,   -90,   -90,   -90,   -90,
      23,   -90,   -90,    72,    63,   -90,   -90,   -90,    66,   -90,
      23,    75,    70,   -11,    93,   -90,    -6,    73,    74,   -90,
      21,   -90,    23,    26,    76,    -6,    77,   -90,   -90,   -90,
      80,   -90
  };

  /* YYDEFACT[S] -- default rule to reduce with in state S when YYTABLE
     doesn't specify something else to do.  Zero means the default is an
     error.  */
  private static final byte yydefact_[] =
  {
         4,     0,     0,     2,     6,    88,     0,     1,     9,     5,
       0,     7,     0,     0,     0,     0,     0,     0,     0,     3,
      16,    10,    14,    11,    12,    13,    15,     4,     0,     0,
       0,     0,     0,     0,    80,     0,     0,     8,     0,    17,
       0,    40,    20,    23,    23,    27,    33,     0,    83,    84,
      85,    86,    87,    19,    82,     0,     0,     0,     0,     0,
      18,    61,    62,    63,    58,    48,    39,    47,    44,    45,
      43,    41,    42,    46,     0,    56,     0,    25,    22,    24,
      21,    30,    26,    28,    29,     0,     0,    37,    32,    35,
      34,    36,     0,     0,    55,    64,    65,    66,    67,    68,
      69,    70,    71,    72,    73,    74,    75,    76,    77,    78,
       0,    79,    81,     0,     0,    60,    59,    57,     0,    31,
       0,     0,     0,     0,     0,    49,     0,     0,     0,    53,
       0,    51,     0,     0,     0,     0,     0,    54,    50,    52,
       0,    38
  };

  /* YYPGOTO[NTERM-NUM].  */
  private static final byte yypgoto_[] =
  {
       -90,   -90,    85,   -90,   -90,   -90,   -90,   -90,    -3,    20,
      59,    78,    79,    81,   -90,   -90,   -90,   -90,   -90,   -90,
     -90,   -90,   -90,    82,   -90,   -19,   -90,   -90,    29,   -90,
     -90,   -90,   -89,    -9,    -1
  };

  /* YYDEFGOTO[NTERM-NUM].  */
  private static final short
  yydefgoto_[] =
  {
        -1,     2,     3,     4,     8,    11,    12,    21,    22,   129,
      23,    24,    56,    25,    58,    84,    26,    59,    90,    91,
      42,    55,    71,    79,   130,   131,    73,    74,    75,    76,
     110,    33,   111,    53,    30
  };

  /* YYTABLE[YYPACT[STATE-NUM]].  What to do in state STATE-NUM.  If
     positive, shift that token.  If negative, reduce the rule which
     number is the opposite.  If zero, do what YYDEFACT says.  */
  private static final short yytable_ninf_ = -1;
  private static final short
  yytable_[] =
  {
         6,    14,    15,    16,    17,    14,   128,    61,    62,    63,
       1,    61,    62,    63,    31,    34,    35,    36,    14,    61,
      62,    63,   115,    86,    14,    15,    16,    17,    18,     5,
     125,   122,    64,    77,    29,     5,   126,    65,    78,    54,
       5,    81,    66,   136,   116,    77,    82,    28,    38,    32,
      80,     5,    67,    19,    87,    83,    89,    85,     5,    88,
      20,     5,    48,    49,    50,    51,    52,     7,     9,   134,
     135,    13,    10,    27,    39,   112,    41,    92,    40,    93,
      43,    44,    45,    46,    47,   114,    95,    96,    97,    98,
      99,   100,   101,   102,   103,   104,   105,   106,   107,   108,
     109,    60,    94,     5,   127,   120,   113,    64,   119,   118,
     121,   123,    37,   124,    68,   132,   139,   138,   133,   112,
     140,   141,   117,    57,   137,     0,     0,     0,     0,     0,
       0,   112,    54,    69,     0,     0,    70,    72
  };

  /* YYCHECK.  */
  private static final short
  yycheck_[] =
  {
         1,     5,     6,     7,     8,     5,    12,    15,    16,    17,
       4,    15,    16,    17,    15,    16,    17,    18,     5,    15,
      16,    17,    14,    10,     5,     6,     7,     8,     9,    35,
      41,   120,    36,    41,    14,    35,    47,    41,    46,    40,
      35,    41,    46,   132,    36,    41,    46,    42,    28,    33,
      46,    35,    55,    34,    41,    58,    59,    58,    35,    46,
      41,    35,    36,    37,    38,    39,    40,     0,    41,    48,
      49,    31,     3,    41,    41,    76,    45,    13,    44,    49,
      45,    45,    45,    45,    43,    86,    18,    19,    20,    21,
      22,    23,    24,    25,    26,    27,    28,    29,    30,    31,
      32,    41,    41,    35,    11,    42,    44,    36,    36,   110,
      44,    36,    27,    43,    55,    42,   135,    41,    44,   120,
      43,    41,    93,    44,   133,    -1,    -1,    -1,    -1,    -1,
      -1,   132,   133,    55,    -1,    -1,    55,    55
  };

  /* STOS_[STATE-NUM] -- The (internal number of the) accessing
     symbol of state STATE-NUM.  */
  private static final byte
  yystos_[] =
  {
         0,     4,    51,    52,    53,    35,    84,     0,    54,    41,
       3,    55,    56,    31,     5,     6,     7,     8,     9,    34,
      41,    57,    58,    60,    61,    63,    66,    41,    42,    59,
      84,    84,    33,    81,    84,    84,    84,    52,    59,    41,
      44,    45,    70,    45,    45,    45,    45,    43,    36,    37,
      38,    39,    40,    83,    84,    71,    62,    62,    64,    67,
      41,    15,    16,    17,    36,    41,    46,    58,    60,    61,
      63,    72,    73,    76,    77,    78,    79,    41,    46,    73,
      46,    41,    46,    58,    65,    84,    10,    41,    46,    58,
      68,    69,    13,    49,    41,    18,    19,    20,    21,    22,
      23,    24,    25,    26,    27,    28,    29,    30,    31,    32,
      80,    82,    84,    44,    84,    14,    36,    78,    84,    36,
      42,    44,    82,    36,    43,    41,    47,    11,    12,    59,
      74,    75,    42,    44,    48,    49,    82,    83,    41,    75,
      43,    41
  };

  /* TOKEN_NUMBER_[YYLEX-NUM] -- Internal symbol number corresponding
     to YYLEX-NUM.  */
  private static final short
  yytoken_number_[] =
  {
         0,   256,   257,   258,   259,   260,   261,   262,   263,   264,
     265,   266,   267,   268,   269,   270,   271,   272,   273,   274,
     275,   276,   277,   278,   279,   280,   281,   282,   283,   284,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,    59,    40,    41,    61,   123,   125,    91,    93,    44
  };

  /* YYR1[YYN] -- Symbol number of symbol that rule YYN derives.  */
  private static final byte
  yyr1_[] =
  {
         0,    50,    51,    52,    53,    53,    54,    54,    55,    56,
      56,    57,    57,    57,    57,    57,    57,    58,    58,    59,
      60,    61,    61,    62,    62,    62,    63,    64,    64,    64,
      64,    65,    66,    67,    67,    68,    68,    68,    69,    70,
      71,    71,    72,    72,    72,    72,    72,    72,    72,    73,
      73,    74,    74,    75,    75,    76,    77,    77,    78,    78,
      78,    79,    79,    79,    80,    80,    80,    80,    80,    80,
      80,    80,    80,    80,    80,    80,    80,    80,    80,    80,
      81,    82,    83,    83,    83,    83,    83,    83,    84
  };

  /* YYR2[YYN] -- Number of symbols composing right hand side of rule YYN.  */
  private static final byte
  yyr2_[] =
  {
         0,     2,     1,     4,     0,     3,     0,     2,     4,     0,
       2,     1,     1,     1,     1,     1,     1,     3,     5,     3,
       3,     5,     5,     0,     2,     2,     5,     0,     2,     2,
       2,     3,     5,     0,     2,     1,     1,     1,    10,     3,
       0,     2,     1,     1,     1,     1,     1,     1,     1,     6,
       9,     1,     3,     1,     3,     2,     1,     3,     1,     3,
       3,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1
  };

  /* YYTNAME[SYMBOL-NUM] -- String name of the symbol SYMBOL-NUM.
     First, the terminals, then, starting at \a yyntokens_, nonterminals.  */
  private static final String yytname_[] =
  {
    "$end", "error", "$undefined", "IMPORT", "PACKAGE", "OPTION", "MESSAGE",
  "EXTEND", "ENUM", "SERVICE", "RPC", "RETURNS", "DEFAULT", "TO", "MAX",
  "REQUIRED", "OPTIONAL", "REPEATED", "DOUBLE", "FLOAT", "INT32", "INT64",
  "UINT32", "UINT64", "SINT32", "SINT64", "FIXED32", "FIXED64", "SFIXED32",
  "SFIXED64", "BOOL", "STRING", "BYTES", "GOOGLEOPTION", "ENDFILE",
  "IDENTIFIER", "INTCONST", "FLOATCONST", "STRINGCONST", "TRUE", "FALSE",
  "';'", "'('", "')'", "'='", "'{'", "'}'", "'['", "']'", "','", "$accept",
  "root", "protobuffile", "packagedecl", "importlist", "importstmt",
  "decllist", "decl", "optionstmt", "option", "message", "extend",
  "fieldlist", "enumtype", "enumlist", "enumfield", "service",
  "servicecaselist", "servicecase", "rpc", "messagebody",
  "messageelementlist", "messageelement", "field", "fieldoptionlist",
  "fieldoption", "extensions", "extensionlist", "extensionrange",
  "cardinality", "type", "path", "usertype", "constant", "identifier", null
  };

  /* YYRHS -- A `-1'-separated list of the rules' RHS.  */
  private static final byte yyrhs_[] =
  {
        51,     0,    -1,    52,    -1,    53,    54,    56,    34,    -1,
      -1,     4,    84,    41,    -1,    -1,    54,    55,    -1,     3,
      31,    41,    52,    -1,    -1,    56,    57,    -1,    60,    -1,
      61,    -1,    63,    -1,    58,    -1,    66,    -1,    41,    -1,
       5,    59,    41,    -1,     5,    42,    59,    43,    41,    -1,
      84,    44,    83,    -1,     6,    84,    70,    -1,     7,    81,
      45,    62,    46,    -1,     7,    33,    45,    62,    46,    -1,
      -1,    62,    73,    -1,    62,    41,    -1,     8,    84,    45,
      64,    46,    -1,    -1,    64,    58,    -1,    64,    65,    -1,
      64,    41,    -1,    84,    44,    36,    -1,     9,    84,    45,
      67,    46,    -1,    -1,    67,    68,    -1,    58,    -1,    69,
      -1,    41,    -1,    10,    84,    42,    82,    43,    11,    42,
      82,    43,    41,    -1,    45,    71,    46,    -1,    -1,    71,
      72,    -1,    73,    -1,    63,    -1,    60,    -1,    61,    -1,
      76,    -1,    58,    -1,    41,    -1,    79,    80,    84,    44,
      36,    41,    -1,    79,    80,    84,    44,    36,    47,    74,
      48,    41,    -1,    75,    -1,    74,    49,    75,    -1,    59,
      -1,    12,    44,    83,    -1,    77,    41,    -1,    78,    -1,
      76,    49,    78,    -1,    36,    -1,    36,    13,    36,    -1,
      36,    13,    14,    -1,    15,    -1,    16,    -1,    17,    -1,
      18,    -1,    19,    -1,    20,    -1,    21,    -1,    22,    -1,
      23,    -1,    24,    -1,    25,    -1,    26,    -1,    27,    -1,
      28,    -1,    29,    -1,    30,    -1,    31,    -1,    32,    -1,
      82,    -1,    84,    -1,    84,    -1,    84,    -1,    36,    -1,
      37,    -1,    38,    -1,    39,    -1,    40,    -1,    35,    -1
  };

  /* YYPRHS[YYN] -- Index of the first RHS symbol of rule number YYN in
     YYRHS.  */
  private static final short yyprhs_[] =
  {
         0,     0,     3,     5,    10,    11,    15,    16,    19,    24,
      25,    28,    30,    32,    34,    36,    38,    40,    44,    50,
      54,    58,    64,    70,    71,    74,    77,    83,    84,    87,
      90,    93,    97,   103,   104,   107,   109,   111,   113,   124,
     128,   129,   132,   134,   136,   138,   140,   142,   144,   146,
     153,   163,   165,   169,   171,   175,   178,   180,   184,   186,
     190,   194,   196,   198,   200,   202,   204,   206,   208,   210,
     212,   214,   216,   218,   220,   222,   224,   226,   228,   230,
     232,   234,   236,   238,   240,   242,   244,   246,   248
  };

  /* YYRLINE[YYN] -- Source line where rule number YYN was defined.  */
  private static final short yyrline_[] =
  {
         0,    71,    71,    75,    80,    81,    87,    88,    93,    99,
     100,   105,   106,   107,   108,   109,   110,   114,   116,   121,
     126,   131,   133,   139,   140,   142,   147,   153,   154,   156,
     158,   162,   167,   173,   174,   179,   180,   181,   185,   190,
     196,   197,   202,   203,   204,   205,   206,   207,   208,   213,
     215,   220,   222,   227,   229,   234,   239,   241,   246,   248,
     250,   255,   256,   257,   261,   262,   263,   264,   265,   266,
     267,   268,   269,   270,   271,   272,   273,   274,   275,   276,
     282,   286,   290,   291,   292,   293,   294,   295,   300
  };

  // Report on the debug stream that the rule yyrule is going to be reduced.
  private void yy_reduce_print (int yyrule, YYStack yystack)
  {
    if (yydebug == 0)
      return;

    int yylno = yyrline_[yyrule];
    int yynrhs = yyr2_[yyrule];
    /* Print the symbols being reduced, and their result.  */
    yycdebug ("Reducing stack by rule " + (yyrule - 1)
	      + " (line " + yylno + "), ");

    /* The symbols being reduced.  */
    for (int yyi = 0; yyi < yynrhs; yyi++)
      yy_symbol_print ("   $" + (yyi + 1) + " =",
		       yyrhs_[yyprhs_[yyrule] + yyi],
		       ((yystack.valueAt (yynrhs-(yyi + 1)))),
		       yystack.locationAt (yynrhs-(yyi + 1)));
  }

  /* YYTRANSLATE(YYLEX) -- Bison symbol number corresponding to YYLEX.  */
  private static final byte yytranslate_table_[] =
  {
         0,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
      42,    43,     2,     2,    49,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,    41,
       2,    44,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,    47,     2,    48,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,    45,     2,    46,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     1,     2,     3,     4,
       5,     6,     7,     8,     9,    10,    11,    12,    13,    14,
      15,    16,    17,    18,    19,    20,    21,    22,    23,    24,
      25,    26,    27,    28,    29,    30,    31,    32,    33,    34,
      35,    36,    37,    38,    39,    40
  };

  private static final byte yytranslate_ (int t)
  {
    if (t >= 0 && t <= yyuser_token_number_max_)
      return yytranslate_table_[t];
    else
      return yyundef_token_;
  }

  private static final int yylast_ = 137;
  private static final int yynnts_ = 35;
  private static final int yyempty_ = -2;
  private static final int yyfinal_ = 7;
  private static final int yyterror_ = 1;
  private static final int yyerrcode_ = 256;
  private static final int yyntokens_ = 50;

  private static final int yyuser_token_number_max_ = 295;
  private static final int yyundef_token_ = 2;

/* User implementation code.  */
/* Unqualified %code blocks.  */

/* Line 876 of lalr1.java  */
/* Line 24 of "protobuf.y"  */

//////////////////////////////////////////////////
// Constructors

    public ProtobufParser()
    {
	this((ASTFactory)null);
    }

    public ProtobufParser(ASTFactory factory)
    {
        super(factory);  
        this.yylexer = new ProtobufLexer(this);
        this.lexstate = (ProtobufLexer)this.yylexer;
    }
    
    public boolean parse(String filename, Reader stream) throws IOException
    {
	this.filename = filename;
        ((ProtobufLexer)yylexer).reset(state);
        ((ProtobufLexer)yylexer).setStream(stream);
        return parse();
    }

    public Object parseError(String s)
    {
	yyerror(getLocation(),s);
	return null;
    }



/* Line 876 of lalr1.java  */
/* Line 1947 of "./tmp"  */

}


