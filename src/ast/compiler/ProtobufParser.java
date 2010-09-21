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

package unidata.protobuf.ast.compiler;
/* First part of user declarations.  */

/* "%code imports" blocks.  */

/* Line 33 of lalr1.java  */
/* Line 19 of "protobuf.y"  */

import java.io.*;
import unidata.protobuf.ast.compiler.AST.Position;



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
/* Line 73 of "protobuf.y"  */
    {setLocation(yyloc);protobuffile(((yystack.valueAt (4-(1)))),((yystack.valueAt (4-(2)))),((yystack.valueAt (4-(3)))));};
  break;
    

  case 3:
  if (yyn == 3)
    
/* Line 354 of lalr1.java  */
/* Line 77 of "protobuf.y"  */
    {setLocation(yyloc);yyval=packagedecl(null);};
  break;
    

  case 4:
  if (yyn == 4)
    
/* Line 354 of lalr1.java  */
/* Line 79 of "protobuf.y"  */
    {setLocation(yyloc);yyval=packagedecl(((yystack.valueAt (3-(2)))));};
  break;
    

  case 5:
  if (yyn == 5)
    
/* Line 354 of lalr1.java  */
/* Line 84 of "protobuf.y"  */
    {setLocation(yyloc);yyval=importlist(null,null);};
  break;
    

  case 6:
  if (yyn == 6)
    
/* Line 354 of lalr1.java  */
/* Line 86 of "protobuf.y"  */
    {setLocation(yyloc);yyval=importlist(((yystack.valueAt (2-(1)))),((yystack.valueAt (2-(2)))));};
  break;
    

  case 7:
  if (yyn == 7)
    
/* Line 354 of lalr1.java  */
/* Line 91 of "protobuf.y"  */
    {setLocation(yyloc);importstmt(((yystack.valueAt (4-(2)))),((yystack.valueAt (4-(4)))));};
  break;
    

  case 8:
  if (yyn == 8)
    
/* Line 354 of lalr1.java  */
/* Line 96 of "protobuf.y"  */
    {setLocation(yyloc);yyval=decllist(null,null);};
  break;
    

  case 9:
  if (yyn == 9)
    
/* Line 354 of lalr1.java  */
/* Line 98 of "protobuf.y"  */
    {setLocation(yyloc);yyval=decllist(((yystack.valueAt (2-(1)))),((yystack.valueAt (2-(2)))));};
  break;
    

  case 10:
  if (yyn == 10)
    
/* Line 354 of lalr1.java  */
/* Line 102 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 11:
  if (yyn == 11)
    
/* Line 354 of lalr1.java  */
/* Line 103 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 12:
  if (yyn == 12)
    
/* Line 354 of lalr1.java  */
/* Line 104 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 13:
  if (yyn == 13)
    
/* Line 354 of lalr1.java  */
/* Line 105 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 14:
  if (yyn == 14)
    
/* Line 354 of lalr1.java  */
/* Line 106 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 15:
  if (yyn == 15)
    
/* Line 354 of lalr1.java  */
/* Line 107 of "protobuf.y"  */
    {yyval=null;};
  break;
    

  case 16:
  if (yyn == 16)
    
/* Line 354 of lalr1.java  */
/* Line 112 of "protobuf.y"  */
    {yyval=((yystack.valueAt (3-(2))));};
  break;
    

  case 17:
  if (yyn == 17)
    
/* Line 354 of lalr1.java  */
/* Line 114 of "protobuf.y"  */
    {setLocation(yyloc);yyval=useroption(((yystack.valueAt (5-(3)))));};
  break;
    

  case 18:
  if (yyn == 18)
    
/* Line 354 of lalr1.java  */
/* Line 119 of "protobuf.y"  */
    {setLocation(yyloc);yyval=option(((yystack.valueAt (3-(1)))),((yystack.valueAt (3-(3)))));};
  break;
    

  case 19:
  if (yyn == 19)
    
/* Line 354 of lalr1.java  */
/* Line 124 of "protobuf.y"  */
    {setLocation(yyloc);yyval=message(((yystack.valueAt (3-(2)))),((yystack.valueAt (3-(3)))));};
  break;
    

  case 20:
  if (yyn == 20)
    
/* Line 354 of lalr1.java  */
/* Line 129 of "protobuf.y"  */
    {setLocation(yyloc);yyval=extend(((yystack.valueAt (5-(2)))),((yystack.valueAt (5-(4)))));};
  break;
    

  case 21:
  if (yyn == 21)
    
/* Line 354 of lalr1.java  */
/* Line 131 of "protobuf.y"  */
    {yyval=null; /* ignore */ };
  break;
    

  case 22:
  if (yyn == 22)
    
/* Line 354 of lalr1.java  */
/* Line 136 of "protobuf.y"  */
    {setLocation(yyloc);yyval=fieldlist(null,null);};
  break;
    

  case 23:
  if (yyn == 23)
    
/* Line 354 of lalr1.java  */
/* Line 138 of "protobuf.y"  */
    {setLocation(yyloc);yyval=fieldlist(((yystack.valueAt (2-(1)))),((yystack.valueAt (2-(2)))));};
  break;
    

  case 24:
  if (yyn == 24)
    
/* Line 354 of lalr1.java  */
/* Line 140 of "protobuf.y"  */
    {yyval=((yystack.valueAt (2-(1))));};
  break;
    

  case 25:
  if (yyn == 25)
    
/* Line 354 of lalr1.java  */
/* Line 145 of "protobuf.y"  */
    {setLocation(yyloc);yyval=enumtype(((yystack.valueAt (5-(2)))),((yystack.valueAt (5-(4)))));};
  break;
    

  case 26:
  if (yyn == 26)
    
/* Line 354 of lalr1.java  */
/* Line 150 of "protobuf.y"  */
    {setLocation(yyloc);yyval=enumlist(null,null);};
  break;
    

  case 27:
  if (yyn == 27)
    
/* Line 354 of lalr1.java  */
/* Line 152 of "protobuf.y"  */
    {setLocation(yyloc);yyval=enumlist(((yystack.valueAt (2-(1)))),((yystack.valueAt (2-(2)))));};
  break;
    

  case 28:
  if (yyn == 28)
    
/* Line 354 of lalr1.java  */
/* Line 154 of "protobuf.y"  */
    {setLocation(yyloc);yyval=enumlist(((yystack.valueAt (2-(1)))),((yystack.valueAt (2-(2)))));};
  break;
    

  case 29:
  if (yyn == 29)
    
/* Line 354 of lalr1.java  */
/* Line 155 of "protobuf.y"  */
    {yyval=((yystack.valueAt (2-(1))));};
  break;
    

  case 30:
  if (yyn == 30)
    
/* Line 354 of lalr1.java  */
/* Line 160 of "protobuf.y"  */
    {setLocation(yyloc);if((yyval=enumfield(((yystack.valueAt (3-(1)))),((yystack.valueAt (3-(3))))))==null) {return YYABORT;}};
  break;
    

  case 31:
  if (yyn == 31)
    
/* Line 354 of lalr1.java  */
/* Line 165 of "protobuf.y"  */
    {setLocation(yyloc);yyval=service(((yystack.valueAt (5-(2)))),((yystack.valueAt (5-(4)))));};
  break;
    

  case 32:
  if (yyn == 32)
    
/* Line 354 of lalr1.java  */
/* Line 170 of "protobuf.y"  */
    {setLocation(yyloc);yyval=servicecaselist(null,null);};
  break;
    

  case 33:
  if (yyn == 33)
    
/* Line 354 of lalr1.java  */
/* Line 172 of "protobuf.y"  */
    {setLocation(yyloc);yyval=servicecaselist(((yystack.valueAt (2-(1)))),((yystack.valueAt (2-(2)))));};
  break;
    

  case 34:
  if (yyn == 34)
    
/* Line 354 of lalr1.java  */
/* Line 176 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 35:
  if (yyn == 35)
    
/* Line 354 of lalr1.java  */
/* Line 177 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 36:
  if (yyn == 36)
    
/* Line 354 of lalr1.java  */
/* Line 178 of "protobuf.y"  */
    {yyval=null;};
  break;
    

  case 37:
  if (yyn == 37)
    
/* Line 354 of lalr1.java  */
/* Line 183 of "protobuf.y"  */
    {setLocation(yyloc);yyval=rpc(((yystack.valueAt (10-(2)))),((yystack.valueAt (10-(4)))),((yystack.valueAt (10-(8)))));};
  break;
    

  case 38:
  if (yyn == 38)
    
/* Line 354 of lalr1.java  */
/* Line 188 of "protobuf.y"  */
    {yyval=((yystack.valueAt (3-(2))));};
  break;
    

  case 39:
  if (yyn == 39)
    
/* Line 354 of lalr1.java  */
/* Line 193 of "protobuf.y"  */
    {setLocation(yyloc);yyval=messageelementlist(null,null);};
  break;
    

  case 40:
  if (yyn == 40)
    
/* Line 354 of lalr1.java  */
/* Line 195 of "protobuf.y"  */
    {setLocation(yyloc);yyval=messageelementlist(((yystack.valueAt (2-(1)))),((yystack.valueAt (2-(2)))));};
  break;
    

  case 41:
  if (yyn == 41)
    
/* Line 354 of lalr1.java  */
/* Line 199 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 42:
  if (yyn == 42)
    
/* Line 354 of lalr1.java  */
/* Line 200 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 43:
  if (yyn == 43)
    
/* Line 354 of lalr1.java  */
/* Line 201 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 44:
  if (yyn == 44)
    
/* Line 354 of lalr1.java  */
/* Line 202 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 45:
  if (yyn == 45)
    
/* Line 354 of lalr1.java  */
/* Line 203 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 46:
  if (yyn == 46)
    
/* Line 354 of lalr1.java  */
/* Line 204 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 47:
  if (yyn == 47)
    
/* Line 354 of lalr1.java  */
/* Line 205 of "protobuf.y"  */
    {yyval=null;};
  break;
    

  case 48:
  if (yyn == 48)
    
/* Line 354 of lalr1.java  */
/* Line 211 of "protobuf.y"  */
    {setLocation(yyloc);yyval=field(((yystack.valueAt (6-(1)))),((yystack.valueAt (6-(2)))),((yystack.valueAt (6-(3)))),((yystack.valueAt (6-(5)))),null);};
  break;
    

  case 49:
  if (yyn == 49)
    
/* Line 354 of lalr1.java  */
/* Line 213 of "protobuf.y"  */
    {setLocation(yyloc);yyval=field(((yystack.valueAt (9-(1)))),((yystack.valueAt (9-(2)))),((yystack.valueAt (9-(3)))),((yystack.valueAt (9-(5)))),((yystack.valueAt (9-(7)))));};
  break;
    

  case 50:
  if (yyn == 50)
    
/* Line 354 of lalr1.java  */
/* Line 218 of "protobuf.y"  */
    {setLocation(yyloc);yyval=fieldoptionlist(null,((yystack.valueAt (1-(1)))));};
  break;
    

  case 51:
  if (yyn == 51)
    
/* Line 354 of lalr1.java  */
/* Line 220 of "protobuf.y"  */
    {setLocation(yyloc);yyval=fieldoptionlist(((yystack.valueAt (3-(1)))),((yystack.valueAt (3-(3)))));};
  break;
    

  case 52:
  if (yyn == 52)
    
/* Line 354 of lalr1.java  */
/* Line 225 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 53:
  if (yyn == 53)
    
/* Line 354 of lalr1.java  */
/* Line 227 of "protobuf.y"  */
    {setLocation(yyloc);yyval=option(AST.DEFAULTNAME,((yystack.valueAt (3-(3)))));};
  break;
    

  case 54:
  if (yyn == 54)
    
/* Line 354 of lalr1.java  */
/* Line 232 of "protobuf.y"  */
    {yyval=((yystack.valueAt (2-(1))));};
  break;
    

  case 55:
  if (yyn == 55)
    
/* Line 354 of lalr1.java  */
/* Line 237 of "protobuf.y"  */
    {setLocation(yyloc);yyval=extensionlist(null,((yystack.valueAt (1-(1)))));};
  break;
    

  case 56:
  if (yyn == 56)
    
/* Line 354 of lalr1.java  */
/* Line 239 of "protobuf.y"  */
    {setLocation(yyloc);yyval=extensionlist(((yystack.valueAt (3-(1)))),((yystack.valueAt (3-(3)))));};
  break;
    

  case 57:
  if (yyn == 57)
    
/* Line 354 of lalr1.java  */
/* Line 244 of "protobuf.y"  */
    {setLocation(yyloc);if((yyval=extensionrange(((yystack.valueAt (1-(1)))),null)) == null) return YYABORT;};
  break;
    

  case 58:
  if (yyn == 58)
    
/* Line 354 of lalr1.java  */
/* Line 246 of "protobuf.y"  */
    {setLocation(yyloc);if((yyval=extensionrange(((yystack.valueAt (3-(1)))),((yystack.valueAt (3-(3)))))) == null) return YYABORT;};
  break;
    

  case 59:
  if (yyn == 59)
    
/* Line 354 of lalr1.java  */
/* Line 248 of "protobuf.y"  */
    {setLocation(yyloc);if((yyval=extensionrange(((yystack.valueAt (3-(1)))),null)) == null) return YYABORT;};
  break;
    

  case 60:
  if (yyn == 60)
    
/* Line 354 of lalr1.java  */
/* Line 252 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 61:
  if (yyn == 61)
    
/* Line 354 of lalr1.java  */
/* Line 253 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 62:
  if (yyn == 62)
    
/* Line 354 of lalr1.java  */
/* Line 254 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 63:
  if (yyn == 63)
    
/* Line 354 of lalr1.java  */
/* Line 258 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 64:
  if (yyn == 64)
    
/* Line 354 of lalr1.java  */
/* Line 259 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 65:
  if (yyn == 65)
    
/* Line 354 of lalr1.java  */
/* Line 260 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 66:
  if (yyn == 66)
    
/* Line 354 of lalr1.java  */
/* Line 261 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 67:
  if (yyn == 67)
    
/* Line 354 of lalr1.java  */
/* Line 262 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 68:
  if (yyn == 68)
    
/* Line 354 of lalr1.java  */
/* Line 263 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 69:
  if (yyn == 69)
    
/* Line 354 of lalr1.java  */
/* Line 264 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 70:
  if (yyn == 70)
    
/* Line 354 of lalr1.java  */
/* Line 265 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 71:
  if (yyn == 71)
    
/* Line 354 of lalr1.java  */
/* Line 266 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 72:
  if (yyn == 72)
    
/* Line 354 of lalr1.java  */
/* Line 267 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 73:
  if (yyn == 73)
    
/* Line 354 of lalr1.java  */
/* Line 268 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 74:
  if (yyn == 74)
    
/* Line 354 of lalr1.java  */
/* Line 269 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 75:
  if (yyn == 75)
    
/* Line 354 of lalr1.java  */
/* Line 270 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 76:
  if (yyn == 76)
    
/* Line 354 of lalr1.java  */
/* Line 271 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 77:
  if (yyn == 77)
    
/* Line 354 of lalr1.java  */
/* Line 272 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 78:
  if (yyn == 78)
    
/* Line 354 of lalr1.java  */
/* Line 273 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 79:
  if (yyn == 79)
    
/* Line 354 of lalr1.java  */
/* Line 279 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 80:
  if (yyn == 80)
    
/* Line 354 of lalr1.java  */
/* Line 283 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 81:
  if (yyn == 81)
    
/* Line 354 of lalr1.java  */
/* Line 287 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 82:
  if (yyn == 82)
    
/* Line 354 of lalr1.java  */
/* Line 288 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 83:
  if (yyn == 83)
    
/* Line 354 of lalr1.java  */
/* Line 289 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 84:
  if (yyn == 84)
    
/* Line 354 of lalr1.java  */
/* Line 290 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 85:
  if (yyn == 85)
    
/* Line 354 of lalr1.java  */
/* Line 291 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 86:
  if (yyn == 86)
    
/* Line 354 of lalr1.java  */
/* Line 292 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 87:
  if (yyn == 87)
    
/* Line 354 of lalr1.java  */
/* Line 298 of "protobuf.y"  */
    {if((yyval=identifier(((yystack.valueAt (1-(1))))))==null) {return YYABORT;}};
  break;
    



/* Line 354 of lalr1.java  */
/* Line 1211 of "./tmp"  */
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
  private static final byte yypact_ninf_ = -79;
  private static final byte yypact_[] =
  {
        47,   -18,    54,   -79,   -79,    17,   -79,    61,   -79,    41,
     -79,    16,    32,    10,   -18,    -3,   -18,   -18,   -79,   -79,
     -79,   -79,   -79,   -79,   -79,   -79,    47,   -18,    34,    33,
      35,    36,    37,   -79,    38,    55,   -79,    58,   -79,    31,
     -79,   -79,   -79,   -79,   -79,   -79,    62,   -79,   -79,   -79,
     -79,   -79,   -79,   -79,     3,   -13,    19,    -4,     2,   -79,
     -79,   -79,   -79,    63,   -79,   -79,   -79,   -79,   -79,   -79,
     -79,   -79,    29,    64,   -79,    67,   -79,   -79,   -79,   -79,
     -79,   -79,   -79,   -79,    60,   -18,   -79,   -79,   -79,   -79,
     -79,    -9,    43,   -79,   -79,   -79,   -79,   -79,   -79,   -79,
     -79,   -79,   -79,   -79,   -79,   -79,   -79,   -79,   -79,   -18,
     -79,   -79,    70,    65,   -79,   -79,   -79,    66,   -79,   -18,
      73,    68,    14,   101,   -79,    -6,    71,    72,   -79,    -2,
     -79,   -18,    31,    74,    -6,    76,   -79,   -79,   -79,    79,
     -79
  };

  /* YYDEFACT[S] -- default rule to reduce with in state S when YYTABLE
     doesn't specify something else to do.  Zero means the default is an
     error.  */
  private static final byte yydefact_[] =
  {
         3,     0,     0,     5,    87,     0,     1,     8,     4,     0,
       6,     0,     0,     0,     0,     0,     0,     0,     2,    15,
       9,    13,    10,    11,    12,    14,     3,     0,     0,     0,
       0,     0,     0,    79,     0,     0,     7,     0,    16,     0,
      39,    19,    22,    22,    26,    32,     0,    82,    83,    84,
      85,    86,    18,    81,     0,     0,     0,     0,     0,    17,
      60,    61,    62,    57,    47,    38,    46,    43,    44,    42,
      40,    41,    45,     0,    55,     0,    24,    21,    23,    20,
      29,    25,    27,    28,     0,     0,    36,    31,    34,    33,
      35,     0,     0,    54,    63,    64,    65,    66,    67,    68,
      69,    70,    71,    72,    73,    74,    75,    76,    77,     0,
      78,    80,     0,     0,    59,    58,    56,     0,    30,     0,
       0,     0,     0,     0,    48,     0,     0,     0,    52,     0,
      50,     0,     0,     0,     0,     0,    53,    49,    51,     0,
      37
  };

  /* YYPGOTO[NTERM-NUM].  */
  private static final byte yypgoto_[] =
  {
       -79,    88,   -79,   -79,   -79,   -79,   -79,     5,    13,    69,
      75,    78,    80,   -79,   -79,   -79,   -79,   -79,   -79,   -79,
     -79,   -79,    81,   -79,   -17,   -79,   -79,    30,   -79,   -79,
     -79,   -78,    -8,    -1
  };

  /* YYDEFGOTO[NTERM-NUM].  */
  private static final short
  yydefgoto_[] =
  {
        -1,     2,     3,     7,    10,    11,    20,    21,   128,    22,
      23,    55,    24,    57,    83,    25,    58,    89,    90,    41,
      54,    70,    78,   129,   130,    72,    73,    74,    75,   109,
      32,   110,    52,    29
  };

  /* YYTABLE[YYPACT[STATE-NUM]].  What to do in state STATE-NUM.  If
     positive, shift that token.  If negative, reduce the rule which
     number is the opposite.  If zero, do what YYDEFACT says.  */
  private static final short yytable_ninf_ = -1;
  private static final short
  yytable_[] =
  {
         5,    13,    60,    61,    62,   114,   127,    13,    13,    14,
      15,    16,    85,    30,    33,    34,    35,     4,    60,    61,
      62,    13,    14,    15,    16,    17,    28,   115,    76,     4,
      31,     4,     4,    77,    60,    61,    62,    80,    53,    63,
      37,   121,    81,    86,    64,     4,   133,   134,    87,    65,
      18,     1,    27,   135,     6,   124,    84,    19,     8,    66,
      76,   125,    82,    88,     9,    79,     4,    47,    48,    49,
      50,    51,    12,    26,   111,    38,    91,    39,    92,    63,
      40,    42,    43,    44,   113,    94,    95,    96,    97,    98,
      99,   100,   101,   102,   103,   104,   105,   106,   107,   108,
      45,    46,     4,    59,   112,    93,   118,   119,   117,   122,
     120,   123,   126,   131,    36,   137,   132,   138,   111,   139,
     140,    56,   116,    67,   136,     0,     0,     0,     0,    68,
     111,    53,     0,     0,    69,    71
  };

  /* YYCHECK.  */
  private static final short
  yycheck_[] =
  {
         1,     5,    15,    16,    17,    14,    12,     5,     5,     6,
       7,     8,    10,    14,    15,    16,    17,    35,    15,    16,
      17,     5,     6,     7,     8,     9,    13,    36,    41,    35,
      33,    35,    35,    46,    15,    16,    17,    41,    39,    36,
      27,   119,    46,    41,    41,    35,    48,    49,    46,    46,
      34,     4,    42,   131,     0,    41,    57,    41,    41,    54,
      41,    47,    57,    58,     3,    46,    35,    36,    37,    38,
      39,    40,    31,    41,    75,    41,    13,    44,    49,    36,
      45,    45,    45,    45,    85,    18,    19,    20,    21,    22,
      23,    24,    25,    26,    27,    28,    29,    30,    31,    32,
      45,    43,    35,    41,    44,    41,    36,    42,   109,    36,
      44,    43,    11,    42,    26,    41,    44,   134,   119,    43,
      41,    43,    92,    54,   132,    -1,    -1,    -1,    -1,    54,
     131,   132,    -1,    -1,    54,    54
  };

  /* STOS_[STATE-NUM] -- The (internal number of the) accessing
     symbol of state STATE-NUM.  */
  private static final byte
  yystos_[] =
  {
         0,     4,    51,    52,    35,    83,     0,    53,    41,     3,
      54,    55,    31,     5,     6,     7,     8,     9,    34,    41,
      56,    57,    59,    60,    62,    65,    41,    42,    58,    83,
      83,    33,    80,    83,    83,    83,    51,    58,    41,    44,
      45,    69,    45,    45,    45,    45,    43,    36,    37,    38,
      39,    40,    82,    83,    70,    61,    61,    63,    66,    41,
      15,    16,    17,    36,    41,    46,    57,    59,    60,    62,
      71,    72,    75,    76,    77,    78,    41,    46,    72,    46,
      41,    46,    57,    64,    83,    10,    41,    46,    57,    67,
      68,    13,    49,    41,    18,    19,    20,    21,    22,    23,
      24,    25,    26,    27,    28,    29,    30,    31,    32,    79,
      81,    83,    44,    83,    14,    36,    77,    83,    36,    42,
      44,    81,    36,    43,    41,    47,    11,    12,    58,    73,
      74,    42,    44,    48,    49,    81,    82,    41,    74,    43,
      41
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
         0,    50,    51,    52,    52,    53,    53,    54,    55,    55,
      56,    56,    56,    56,    56,    56,    57,    57,    58,    59,
      60,    60,    61,    61,    61,    62,    63,    63,    63,    63,
      64,    65,    66,    66,    67,    67,    67,    68,    69,    70,
      70,    71,    71,    71,    71,    71,    71,    71,    72,    72,
      73,    73,    74,    74,    75,    76,    76,    77,    77,    77,
      78,    78,    78,    79,    79,    79,    79,    79,    79,    79,
      79,    79,    79,    79,    79,    79,    79,    79,    79,    80,
      81,    82,    82,    82,    82,    82,    82,    83
  };

  /* YYR2[YYN] -- Number of symbols composing right hand side of rule YYN.  */
  private static final byte
  yyr2_[] =
  {
         0,     2,     4,     0,     3,     0,     2,     4,     0,     2,
       1,     1,     1,     1,     1,     1,     3,     5,     3,     3,
       5,     5,     0,     2,     2,     5,     0,     2,     2,     2,
       3,     5,     0,     2,     1,     1,     1,    10,     3,     0,
       2,     1,     1,     1,     1,     1,     1,     1,     6,     9,
       1,     3,     1,     3,     2,     1,     3,     1,     3,     3,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1
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
  "protobuffile", "packagedecl", "importlist", "importstmt", "decllist",
  "decl", "optionstmt", "option", "message", "extend", "fieldlist",
  "enumtype", "enumlist", "enumfield", "service", "servicecaselist",
  "servicecase", "rpc", "messagebody", "messageelementlist",
  "messageelement", "field", "fieldoptionlist", "fieldoption",
  "extensions", "extensionlist", "extensionrange", "cardinality", "type",
  "path", "usertype", "constant", "identifier", null
  };

  /* YYRHS -- A `-1'-separated list of the rules' RHS.  */
  private static final byte yyrhs_[] =
  {
        51,     0,    -1,    52,    53,    55,    34,    -1,    -1,     4,
      83,    41,    -1,    -1,    53,    54,    -1,     3,    31,    41,
      51,    -1,    -1,    55,    56,    -1,    59,    -1,    60,    -1,
      62,    -1,    57,    -1,    65,    -1,    41,    -1,     5,    58,
      41,    -1,     5,    42,    58,    43,    41,    -1,    83,    44,
      82,    -1,     6,    83,    69,    -1,     7,    80,    45,    61,
      46,    -1,     7,    33,    45,    61,    46,    -1,    -1,    61,
      72,    -1,    61,    41,    -1,     8,    83,    45,    63,    46,
      -1,    -1,    63,    57,    -1,    63,    64,    -1,    63,    41,
      -1,    83,    44,    36,    -1,     9,    83,    45,    66,    46,
      -1,    -1,    66,    67,    -1,    57,    -1,    68,    -1,    41,
      -1,    10,    83,    42,    81,    43,    11,    42,    81,    43,
      41,    -1,    45,    70,    46,    -1,    -1,    70,    71,    -1,
      72,    -1,    62,    -1,    59,    -1,    60,    -1,    75,    -1,
      57,    -1,    41,    -1,    78,    79,    83,    44,    36,    41,
      -1,    78,    79,    83,    44,    36,    47,    73,    48,    41,
      -1,    74,    -1,    73,    49,    74,    -1,    58,    -1,    12,
      44,    82,    -1,    76,    41,    -1,    77,    -1,    75,    49,
      77,    -1,    36,    -1,    36,    13,    36,    -1,    36,    13,
      14,    -1,    15,    -1,    16,    -1,    17,    -1,    18,    -1,
      19,    -1,    20,    -1,    21,    -1,    22,    -1,    23,    -1,
      24,    -1,    25,    -1,    26,    -1,    27,    -1,    28,    -1,
      29,    -1,    30,    -1,    31,    -1,    32,    -1,    81,    -1,
      83,    -1,    83,    -1,    83,    -1,    36,    -1,    37,    -1,
      38,    -1,    39,    -1,    40,    -1,    35,    -1
  };

  /* YYPRHS[YYN] -- Index of the first RHS symbol of rule number YYN in
     YYRHS.  */
  private static final short yyprhs_[] =
  {
         0,     0,     3,     8,     9,    13,    14,    17,    22,    23,
      26,    28,    30,    32,    34,    36,    38,    42,    48,    52,
      56,    62,    68,    69,    72,    75,    81,    82,    85,    88,
      91,    95,   101,   102,   105,   107,   109,   111,   122,   126,
     127,   130,   132,   134,   136,   138,   140,   142,   144,   151,
     161,   163,   167,   169,   173,   176,   178,   182,   184,   188,
     192,   194,   196,   198,   200,   202,   204,   206,   208,   210,
     212,   214,   216,   218,   220,   222,   224,   226,   228,   230,
     232,   234,   236,   238,   240,   242,   244,   246
  };

  /* YYRLINE[YYN] -- Source line where rule number YYN was defined.  */
  private static final short yyrline_[] =
  {
         0,    72,    72,    77,    78,    84,    85,    90,    96,    97,
     102,   103,   104,   105,   106,   107,   111,   113,   118,   123,
     128,   130,   136,   137,   139,   144,   150,   151,   153,   155,
     159,   164,   170,   171,   176,   177,   178,   182,   187,   193,
     194,   199,   200,   201,   202,   203,   204,   205,   210,   212,
     217,   219,   224,   226,   231,   236,   238,   243,   245,   247,
     252,   253,   254,   258,   259,   260,   261,   262,   263,   264,
     265,   266,   267,   268,   269,   270,   271,   272,   273,   279,
     283,   287,   288,   289,   290,   291,   292,   297
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

  private static final int yylast_ = 135;
  private static final int yynnts_ = 34;
  private static final int yyempty_ = -2;
  private static final int yyfinal_ = 6;
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
/* Line 1938 of "./tmp"  */

}


