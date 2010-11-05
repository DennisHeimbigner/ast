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
/* Line 17 of "protobuf.y"  */

import java.io.*;
import unidata.protobuf.compiler.AST.Position;



/* Line 33 of lalr1.java  */
/* Line 48 of "./ProtobufParser.java"  */

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
  public static final int EXTENSIONS = 263;
  /** Token number, to be returned by the scanner.  */
  public static final int ENUM = 264;
  /** Token number, to be returned by the scanner.  */
  public static final int SERVICE = 265;
  /** Token number, to be returned by the scanner.  */
  public static final int RPC = 266;
  /** Token number, to be returned by the scanner.  */
  public static final int RETURNS = 267;
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
  public static final int ENDFILE = 288;
  /** Token number, to be returned by the scanner.  */
  public static final int NAME = 289;
  /** Token number, to be returned by the scanner.  */
  public static final int INTCONST = 290;
  /** Token number, to be returned by the scanner.  */
  public static final int FLOATCONST = 291;
  /** Token number, to be returned by the scanner.  */
  public static final int STRINGCONST = 292;
  /** Token number, to be returned by the scanner.  */
  public static final int TRUE = 293;
  /** Token number, to be returned by the scanner.  */
  public static final int FALSE = 294;
  /** Token number, to be returned by the scanner.  */
  public static final int POSNAN = 295;
  /** Token number, to be returned by the scanner.  */
  public static final int POSINF = 296;
  /** Token number, to be returned by the scanner.  */
  public static final int NEGNAN = 297;
  /** Token number, to be returned by the scanner.  */
  public static final int NEGINF = 298;



  

  /**
   * Communication interface between the scanner and the Bison-generated
   * parser <tt>ProtobufParser</tt>.
   */
  public interface Lexer {
    

    /**
     * Method to retrieve the semantic value of the last scanned token.
     * @return the semantic value of the last scanned token.  */
    Object getLVal ();

    /**
     * Entry point for the scanner.  Returns the token identifier corresponding
     * to the next token and prepares to return the semantic value
     * of the token.
     * @return the token identifier corresponding to the next token. */
    int yylex () throws IOException;

    /**
     * Entry point for error reporting.  Emits an error
     * in a user-defined way.
     *
     * 
     * @param s The string for the error message.  */
     void yyerror (String s);
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
  protected final void yyerror (String s) {
    yylexer.yyerror (s);
  }

  

  protected final void yycdebug (String s) {
    if (yydebug > 0)
      yyDebugStream.println (s);
  }

  private final class YYStack {
    private int[] stateStack = new int[16];
    
    private Object[] valueStack = new Object[16];

    public int size = 16;
    public int height = -1;

    public final void push (int state, Object value			    ) {
      height++;
      if (size == height)
        {
	  int[] newStateStack = new int[size * 2];
	  System.arraycopy (stateStack, 0, newStateStack, 0, height);
	  stateStack = newStateStack;
	  

	  Object[] newValueStack = new Object[size * 2];
	  System.arraycopy (valueStack, 0, newValueStack, 0, height);
	  valueStack = newValueStack;

	  size *= 2;
	}

      stateStack[height] = state;
      
      valueStack[height] = value;
    }

    public final void pop () {
      height--;
    }

    public final void pop (int num) {
      // Avoid memory leaks... garbage collection is a white lie!
      if (num > 0) {
	java.util.Arrays.fill (valueStack, height - num + 1, height, null);
        
      }
      height -= num;
    }

    public final int stateAt (int i) {
      return stateStack[height - i];
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
    {protobufroot(((yystack.valueAt (1-(1)))));};
  break;
    

  case 3:
  if (yyn == 3)
    
/* Line 354 of lalr1.java  */
/* Line 77 of "protobuf.y"  */
    {yyval=protobuffile(((yystack.valueAt (2-(1)))));};
  break;
    

  case 4:
  if (yyn == 4)
    
/* Line 354 of lalr1.java  */
/* Line 82 of "protobuf.y"  */
    {yyval=packagedecl(((yystack.valueAt (3-(2)))));};
  break;
    

  case 5:
  if (yyn == 5)
    
/* Line 354 of lalr1.java  */
/* Line 87 of "protobuf.y"  */
    {yyval=importstmt(((yystack.valueAt (3-(1)))),((yystack.valueAt (3-(3)))));};
  break;
    

  case 6:
  if (yyn == 6)
    
/* Line 354 of lalr1.java  */
/* Line 92 of "protobuf.y"  */
    {yyval=importprefix(((yystack.valueAt (3-(2)))));};
  break;
    

  case 7:
  if (yyn == 7)
    
/* Line 354 of lalr1.java  */
/* Line 94 of "protobuf.y"  */
    {if(!filepush()) {return YYABORT;};};
  break;
    

  case 8:
  if (yyn == 8)
    
/* Line 354 of lalr1.java  */
/* Line 98 of "protobuf.y"  */
    {yyval=decllist(null,null);};
  break;
    

  case 9:
  if (yyn == 9)
    
/* Line 354 of lalr1.java  */
/* Line 100 of "protobuf.y"  */
    {if(((yystack.valueAt (2-(2)))) != null) yyval=decllist(((yystack.valueAt (2-(1)))),((yystack.valueAt (2-(2))))); else yyval=((yystack.valueAt (2-(1))));};
  break;
    

  case 10:
  if (yyn == 10)
    
/* Line 354 of lalr1.java  */
/* Line 104 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
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
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 17:
  if (yyn == 17)
    
/* Line 354 of lalr1.java  */
/* Line 111 of "protobuf.y"  */
    {yyval=null;};
  break;
    

  case 18:
  if (yyn == 18)
    
/* Line 354 of lalr1.java  */
/* Line 116 of "protobuf.y"  */
    {yyval=((yystack.valueAt (3-(2))));};
  break;
    

  case 19:
  if (yyn == 19)
    
/* Line 354 of lalr1.java  */
/* Line 121 of "protobuf.y"  */
    {yyval=option(((yystack.valueAt (3-(1)))),((yystack.valueAt (3-(3)))));};
  break;
    

  case 20:
  if (yyn == 20)
    
/* Line 354 of lalr1.java  */
/* Line 126 of "protobuf.y"  */
    {yyval=message(((yystack.valueAt (3-(2)))),((yystack.valueAt (3-(3)))));};
  break;
    

  case 21:
  if (yyn == 21)
    
/* Line 354 of lalr1.java  */
/* Line 131 of "protobuf.y"  */
    {yyval=extend(((yystack.valueAt (5-(2)))),((yystack.valueAt (5-(4)))));};
  break;
    

  case 22:
  if (yyn == 22)
    
/* Line 354 of lalr1.java  */
/* Line 137 of "protobuf.y"  */
    {yyval=fieldlist(null,null);};
  break;
    

  case 23:
  if (yyn == 23)
    
/* Line 354 of lalr1.java  */
/* Line 139 of "protobuf.y"  */
    {yyval=fieldlist(((yystack.valueAt (2-(1)))),((yystack.valueAt (2-(2)))));};
  break;
    

  case 24:
  if (yyn == 24)
    
/* Line 354 of lalr1.java  */
/* Line 141 of "protobuf.y"  */
    {yyval=((yystack.valueAt (2-(1))));};
  break;
    

  case 25:
  if (yyn == 25)
    
/* Line 354 of lalr1.java  */
/* Line 146 of "protobuf.y"  */
    {yyval=enumtype(((yystack.valueAt (5-(2)))),((yystack.valueAt (5-(4)))));};
  break;
    

  case 26:
  if (yyn == 26)
    
/* Line 354 of lalr1.java  */
/* Line 151 of "protobuf.y"  */
    {yyval=enumlist(null,null);};
  break;
    

  case 27:
  if (yyn == 27)
    
/* Line 354 of lalr1.java  */
/* Line 153 of "protobuf.y"  */
    {yyval=enumlist(((yystack.valueAt (2-(1)))),((yystack.valueAt (2-(2)))));};
  break;
    

  case 28:
  if (yyn == 28)
    
/* Line 354 of lalr1.java  */
/* Line 155 of "protobuf.y"  */
    {yyval=enumlist(((yystack.valueAt (2-(1)))),((yystack.valueAt (2-(2)))));};
  break;
    

  case 29:
  if (yyn == 29)
    
/* Line 354 of lalr1.java  */
/* Line 156 of "protobuf.y"  */
    {yyval=((yystack.valueAt (2-(1))));};
  break;
    

  case 30:
  if (yyn == 30)
    
/* Line 354 of lalr1.java  */
/* Line 161 of "protobuf.y"  */
    {if((yyval=enumvalue(((yystack.valueAt (3-(1)))),((yystack.valueAt (3-(3)))),null))==null) {return YYABORT;}};
  break;
    

  case 31:
  if (yyn == 31)
    
/* Line 354 of lalr1.java  */
/* Line 163 of "protobuf.y"  */
    {if((yyval=enumvalue(((yystack.valueAt (6-(1)))),((yystack.valueAt (6-(3)))),((yystack.valueAt (6-(5))))))==null) {return YYABORT;}};
  break;
    

  case 32:
  if (yyn == 32)
    
/* Line 354 of lalr1.java  */
/* Line 168 of "protobuf.y"  */
    {yyval=enumoptionlist(null,((yystack.valueAt (1-(1)))));};
  break;
    

  case 33:
  if (yyn == 33)
    
/* Line 354 of lalr1.java  */
/* Line 170 of "protobuf.y"  */
    {yyval=enumoptionlist(((yystack.valueAt (3-(1)))),((yystack.valueAt (3-(3)))));};
  break;
    

  case 34:
  if (yyn == 34)
    
/* Line 354 of lalr1.java  */
/* Line 176 of "protobuf.y"  */
    {yyval=service(((yystack.valueAt (5-(2)))),((yystack.valueAt (5-(4)))));};
  break;
    

  case 35:
  if (yyn == 35)
    
/* Line 354 of lalr1.java  */
/* Line 181 of "protobuf.y"  */
    {yyval=servicecaselist(null,null);};
  break;
    

  case 36:
  if (yyn == 36)
    
/* Line 354 of lalr1.java  */
/* Line 183 of "protobuf.y"  */
    {yyval=servicecaselist(((yystack.valueAt (2-(1)))),((yystack.valueAt (2-(2)))));};
  break;
    

  case 37:
  if (yyn == 37)
    
/* Line 354 of lalr1.java  */
/* Line 187 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 38:
  if (yyn == 38)
    
/* Line 354 of lalr1.java  */
/* Line 188 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 39:
  if (yyn == 39)
    
/* Line 354 of lalr1.java  */
/* Line 189 of "protobuf.y"  */
    {yyval=null;};
  break;
    

  case 40:
  if (yyn == 40)
    
/* Line 354 of lalr1.java  */
/* Line 194 of "protobuf.y"  */
    {yyval=rpc(((yystack.valueAt (10-(2)))),((yystack.valueAt (10-(4)))),((yystack.valueAt (10-(8)))),null);};
  break;
    

  case 41:
  if (yyn == 41)
    
/* Line 354 of lalr1.java  */
/* Line 197 of "protobuf.y"  */
    {yyval=rpc(((yystack.valueAt (12-(2)))),((yystack.valueAt (12-(4)))),((yystack.valueAt (12-(8)))),((yystack.valueAt (12-(11)))));};
  break;
    

  case 42:
  if (yyn == 42)
    
/* Line 354 of lalr1.java  */
/* Line 202 of "protobuf.y"  */
    {yyval=optionstmtlist(null,null);};
  break;
    

  case 43:
  if (yyn == 43)
    
/* Line 354 of lalr1.java  */
/* Line 204 of "protobuf.y"  */
    {yyval=optionstmtlist(((yystack.valueAt (2-(1)))),((yystack.valueAt (2-(2)))));};
  break;
    

  case 44:
  if (yyn == 44)
    
/* Line 354 of lalr1.java  */
/* Line 209 of "protobuf.y"  */
    {yyval=((yystack.valueAt (3-(2))));};
  break;
    

  case 45:
  if (yyn == 45)
    
/* Line 354 of lalr1.java  */
/* Line 214 of "protobuf.y"  */
    {yyval=messageelementlist(null,null);};
  break;
    

  case 46:
  if (yyn == 46)
    
/* Line 354 of lalr1.java  */
/* Line 216 of "protobuf.y"  */
    {yyval=messageelementlist(((yystack.valueAt (2-(1)))),((yystack.valueAt (2-(2)))));};
  break;
    

  case 47:
  if (yyn == 47)
    
/* Line 354 of lalr1.java  */
/* Line 220 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 48:
  if (yyn == 48)
    
/* Line 354 of lalr1.java  */
/* Line 221 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 49:
  if (yyn == 49)
    
/* Line 354 of lalr1.java  */
/* Line 222 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 50:
  if (yyn == 50)
    
/* Line 354 of lalr1.java  */
/* Line 223 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 51:
  if (yyn == 51)
    
/* Line 354 of lalr1.java  */
/* Line 224 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
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
/* Line 226 of "protobuf.y"  */
    {yyval=null;};
  break;
    

  case 54:
  if (yyn == 54)
    
/* Line 354 of lalr1.java  */
/* Line 232 of "protobuf.y"  */
    {yyval=field(((yystack.valueAt (6-(1)))),((yystack.valueAt (6-(2)))),((yystack.valueAt (6-(3)))),((yystack.valueAt (6-(5)))),null);};
  break;
    

  case 55:
  if (yyn == 55)
    
/* Line 354 of lalr1.java  */
/* Line 234 of "protobuf.y"  */
    {yyval=field(((yystack.valueAt (9-(1)))),((yystack.valueAt (9-(2)))),((yystack.valueAt (9-(3)))),((yystack.valueAt (9-(5)))),((yystack.valueAt (9-(7)))));};
  break;
    

  case 56:
  if (yyn == 56)
    
/* Line 354 of lalr1.java  */
/* Line 236 of "protobuf.y"  */
    {yyval=group(((yystack.valueAt (6-(1)))),((yystack.valueAt (6-(2)))),((yystack.valueAt (6-(3)))),((yystack.valueAt (6-(5)))),((yystack.valueAt (6-(6)))));};
  break;
    

  case 57:
  if (yyn == 57)
    
/* Line 354 of lalr1.java  */
/* Line 241 of "protobuf.y"  */
    {yyval=fieldoptionlist(null,((yystack.valueAt (1-(1)))));};
  break;
    

  case 58:
  if (yyn == 58)
    
/* Line 354 of lalr1.java  */
/* Line 243 of "protobuf.y"  */
    {yyval=fieldoptionlist(((yystack.valueAt (3-(1)))),((yystack.valueAt (3-(3)))));};
  break;
    

  case 59:
  if (yyn == 59)
    
/* Line 354 of lalr1.java  */
/* Line 248 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 60:
  if (yyn == 60)
    
/* Line 354 of lalr1.java  */
/* Line 253 of "protobuf.y"  */
    { yyval=extensions(((yystack.valueAt (3-(2)))));};
  break;
    

  case 61:
  if (yyn == 61)
    
/* Line 354 of lalr1.java  */
/* Line 258 of "protobuf.y"  */
    {yyval=extensionlist(null,((yystack.valueAt (1-(1)))));};
  break;
    

  case 62:
  if (yyn == 62)
    
/* Line 354 of lalr1.java  */
/* Line 260 of "protobuf.y"  */
    {yyval=extensionlist(((yystack.valueAt (3-(1)))),((yystack.valueAt (3-(3)))));};
  break;
    

  case 63:
  if (yyn == 63)
    
/* Line 354 of lalr1.java  */
/* Line 265 of "protobuf.y"  */
    {if((yyval=extensionrange(((yystack.valueAt (1-(1)))),null)) == null) return YYABORT;};
  break;
    

  case 64:
  if (yyn == 64)
    
/* Line 354 of lalr1.java  */
/* Line 267 of "protobuf.y"  */
    {if((yyval=extensionrange(((yystack.valueAt (3-(1)))),((yystack.valueAt (3-(3)))))) == null) return YYABORT;};
  break;
    

  case 65:
  if (yyn == 65)
    
/* Line 354 of lalr1.java  */
/* Line 269 of "protobuf.y"  */
    {if((yyval=extensionrange(((yystack.valueAt (3-(1)))),null)) == null) return YYABORT;};
  break;
    

  case 66:
  if (yyn == 66)
    
/* Line 354 of lalr1.java  */
/* Line 273 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 67:
  if (yyn == 67)
    
/* Line 354 of lalr1.java  */
/* Line 274 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 68:
  if (yyn == 68)
    
/* Line 354 of lalr1.java  */
/* Line 275 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 69:
  if (yyn == 69)
    
/* Line 354 of lalr1.java  */
/* Line 279 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 70:
  if (yyn == 70)
    
/* Line 354 of lalr1.java  */
/* Line 280 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 71:
  if (yyn == 71)
    
/* Line 354 of lalr1.java  */
/* Line 281 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 72:
  if (yyn == 72)
    
/* Line 354 of lalr1.java  */
/* Line 282 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 73:
  if (yyn == 73)
    
/* Line 354 of lalr1.java  */
/* Line 283 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 74:
  if (yyn == 74)
    
/* Line 354 of lalr1.java  */
/* Line 284 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 75:
  if (yyn == 75)
    
/* Line 354 of lalr1.java  */
/* Line 285 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 76:
  if (yyn == 76)
    
/* Line 354 of lalr1.java  */
/* Line 286 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 77:
  if (yyn == 77)
    
/* Line 354 of lalr1.java  */
/* Line 287 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 78:
  if (yyn == 78)
    
/* Line 354 of lalr1.java  */
/* Line 288 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 79:
  if (yyn == 79)
    
/* Line 354 of lalr1.java  */
/* Line 289 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 80:
  if (yyn == 80)
    
/* Line 354 of lalr1.java  */
/* Line 290 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 81:
  if (yyn == 81)
    
/* Line 354 of lalr1.java  */
/* Line 291 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 82:
  if (yyn == 82)
    
/* Line 354 of lalr1.java  */
/* Line 292 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 83:
  if (yyn == 83)
    
/* Line 354 of lalr1.java  */
/* Line 293 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 84:
  if (yyn == 84)
    
/* Line 354 of lalr1.java  */
/* Line 294 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 85:
  if (yyn == 85)
    
/* Line 354 of lalr1.java  */
/* Line 298 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 86:
  if (yyn == 86)
    
/* Line 354 of lalr1.java  */
/* Line 302 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 87:
  if (yyn == 87)
    
/* Line 354 of lalr1.java  */
/* Line 307 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 88:
  if (yyn == 88)
    
/* Line 354 of lalr1.java  */
/* Line 308 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 89:
  if (yyn == 89)
    
/* Line 354 of lalr1.java  */
/* Line 309 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 90:
  if (yyn == 90)
    
/* Line 354 of lalr1.java  */
/* Line 310 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 91:
  if (yyn == 91)
    
/* Line 354 of lalr1.java  */
/* Line 311 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 92:
  if (yyn == 92)
    
/* Line 354 of lalr1.java  */
/* Line 312 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 93:
  if (yyn == 93)
    
/* Line 354 of lalr1.java  */
/* Line 313 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 94:
  if (yyn == 94)
    
/* Line 354 of lalr1.java  */
/* Line 314 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 95:
  if (yyn == 95)
    
/* Line 354 of lalr1.java  */
/* Line 315 of "protobuf.y"  */
    {yyval = "-nan";};
  break;
    

  case 96:
  if (yyn == 96)
    
/* Line 354 of lalr1.java  */
/* Line 316 of "protobuf.y"  */
    {yyval = "-inf";};
  break;
    

  case 97:
  if (yyn == 97)
    
/* Line 354 of lalr1.java  */
/* Line 320 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 98:
  if (yyn == 98)
    
/* Line 354 of lalr1.java  */
/* Line 321 of "protobuf.y"  */
    {yyval=("+"+((yystack.valueAt (2-(2)))));};
  break;
    

  case 99:
  if (yyn == 99)
    
/* Line 354 of lalr1.java  */
/* Line 322 of "protobuf.y"  */
    {yyval=("-"+((yystack.valueAt (2-(2)))));};
  break;
    

  case 100:
  if (yyn == 100)
    
/* Line 354 of lalr1.java  */
/* Line 326 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 101:
  if (yyn == 101)
    
/* Line 354 of lalr1.java  */
/* Line 327 of "protobuf.y"  */
    {yyval=("+"+((yystack.valueAt (2-(1)))));};
  break;
    

  case 102:
  if (yyn == 102)
    
/* Line 354 of lalr1.java  */
/* Line 328 of "protobuf.y"  */
    {yyval=("-"+((yystack.valueAt (2-(1)))));};
  break;
    

  case 103:
  if (yyn == 103)
    
/* Line 354 of lalr1.java  */
/* Line 333 of "protobuf.y"  */
    {yyval=path(((yystack.valueAt (1-(1)))),false);};
  break;
    

  case 104:
  if (yyn == 104)
    
/* Line 354 of lalr1.java  */
/* Line 334 of "protobuf.y"  */
    {yyval=path(((yystack.valueAt (2-(2)))),true);};
  break;
    

  case 105:
  if (yyn == 105)
    
/* Line 354 of lalr1.java  */
/* Line 339 of "protobuf.y"  */
    {yyval=relpath(null,((yystack.valueAt (1-(1)))));};
  break;
    

  case 106:
  if (yyn == 106)
    
/* Line 354 of lalr1.java  */
/* Line 341 of "protobuf.y"  */
    {yyval=relpath(((yystack.valueAt (3-(1)))),((yystack.valueAt (3-(3)))));};
  break;
    

  case 107:
  if (yyn == 107)
    
/* Line 354 of lalr1.java  */
/* Line 347 of "protobuf.y"  */
    {startname();};
  break;
    

  case 108:
  if (yyn == 108)
    
/* Line 354 of lalr1.java  */
/* Line 349 of "protobuf.y"  */
    {endname(); yyval=((yystack.valueAt (2-(2))));};
  break;
    



/* Line 354 of lalr1.java  */
/* Line 1335 of "./ProtobufParser.java"  */
	default: break;
      }

    yy_symbol_print ("-> $$ =", yyr1_[yyn], yyval);

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

    yystack.push (yystate, yyval);
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
			         Object yyvaluep				 )
  {
    if (yydebug > 0)
    yycdebug (s + (yytype < yyntokens_ ? " token " : " nterm ")
	      + yytname_[yytype] + " ("
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
    

    /// Semantic value of the lookahead.
    Object yylval = null;

    int yyresult;

    yycdebug ("Starting parse\n");
    yyerrstatus_ = 0;


    /* Initialize the stack.  */
    yystack.push (yystate, yylval);

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
			     yylval);
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
			     yylval);

            /* Discard the token being shifted.  */
            yychar = yyempty_;

            /* Count tokens shifted since error; after three, turn off error
               status.  */
            if (yyerrstatus_ > 0)
              --yyerrstatus_;

            yystate = yyn;
            yystack.push (yystate, yylval);
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
	    yyerror (yysyntax_error (yystate, yytoken));
          }

        
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

	    
	    yystack.pop ();
	    yystate = yystack.stateAt (0);
	    if (yydebug > 0)
	      yystack.print (yyDebugStream);
          }

	

        /* Shift the error token.  */
        yy_symbol_print ("Shifting", yystos_[yyn],
			 yylval);

        yystate = yyn;
	yystack.push (yyn, yylval);
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
  private static final byte yypact_ninf_ = -116;
  private static final byte yypact_[] =
  {
      -116,     6,  -116,    21,  -116,    -8,   -18,  -116,  -116,   -18,
    -116,  -116,  -116,  -116,  -116,  -116,  -116,  -116,  -116,  -116,
    -116,  -116,  -116,     5,  -116,     8,  -116,    37,  -116,    26,
      20,    52,    49,    53,    59,    62,  -116,  -116,    37,  -116,
    -116,  -116,  -116,    47,  -116,  -116,  -116,  -116,  -116,  -116,
    -116,  -116,  -116,  -116,  -116,  -116,  -116,  -116,     0,     7,
    -116,  -116,  -116,  -116,     4,    46,     9,    11,  -116,  -116,
    -116,  -116,  -116,  -116,    15,  -116,  -116,  -116,  -116,  -116,
    -116,  -116,  -116,  -116,  -116,  -116,  -116,    48,  -116,  -116,
    -116,  -116,  -116,  -116,  -116,    66,  -116,  -116,  -116,  -116,
    -116,  -116,    99,    63,    71,  -116,  -116,  -116,  -116,  -116,
    -116,  -116,  -116,  -116,  -116,  -116,  -116,  -116,  -116,  -116,
    -116,  -116,  -116,  -116,    56,    65,     3,    82,  -116,    73,
      84,    85,    74,   -18,  -116,  -116,  -116,    86,  -116,    72,
      50,  -116,    -4,   111,  -116,  -116,  -116,  -116,  -116,    75,
    -116,    57,  -116,  -116,   -18,    81,  -116,    76,  -116,  -116,
     -12,  -116,  -116,    10,  -116,  -116
  };

  /* YYDEFACT[S] -- default rule to reduce with in state S when YYTABLE
     doesn't specify something else to do.  Zero means the default is an
     error.  */
  private static final byte yydefact_[] =
  {
         8,     0,     2,     0,     1,     0,   107,   107,   107,   107,
     107,   107,     3,    17,    15,    16,     7,     9,    13,    10,
      11,    12,    14,     0,   107,     0,    86,   103,   105,     0,
       0,     0,     0,     0,     0,     0,     8,     6,   104,     4,
     107,   108,    18,   107,    45,    20,    22,    26,    35,     5,
     106,    97,   100,    90,    91,    92,    93,    94,     0,     0,
      19,    88,    89,    87,     0,     0,   107,     0,    99,   102,
      95,    96,    98,   101,     0,    66,    67,    68,    53,    44,
      52,    49,    50,    48,    46,    47,    51,   107,    24,    21,
      23,    29,    25,    27,    28,     0,   107,    39,    34,    37,
      36,    38,    63,     0,     0,    61,    69,    70,    71,    72,
      73,    74,    75,    76,    77,    78,    79,    80,    81,    82,
      83,   107,    84,    85,     0,     0,     0,     0,    60,     0,
       0,     0,    30,   107,    65,    64,    62,     0,   107,     0,
       0,    32,     0,     0,    54,   107,    56,    31,   107,     0,
      59,     0,    57,    33,   107,     0,   107,     0,    55,    58,
       0,    40,    42,     0,    41,    43
  };

  /* YYPGOTO[NTERM-NUM].  */
  private static final byte yypgoto_[] =
  {
      -116,  -116,    91,  -116,  -116,  -116,  -116,  -116,  -116,   -59,
      -5,    68,    70,  -116,    78,  -116,  -116,  -116,  -116,  -116,
    -116,  -116,  -116,   -11,  -116,  -116,    79,  -116,   -26,    61,
    -116,    12,  -116,  -116,  -115,  -116,  -116,    13,  -116,    38,
     112,    -7,  -116
  };

  /* YYDEFGOTO[NTERM-NUM].  */
  private static final short
  yydefgoto_[] =
  {
        -1,     1,     2,    14,    15,    16,    36,     3,    17,    18,
     150,    19,    20,    65,    21,    66,    94,   142,    22,    67,
     100,   101,   163,    45,    64,    84,    85,   151,   152,    86,
     104,   105,    87,   121,   122,    25,    60,    61,    62,   123,
      27,    28,    29
  };

  /* YYTABLE[YYPACT[STATE-NUM]].  What to do in state STATE-NUM.  If
     positive, shift that token.  If negative, reduce the rule which
     number is the opposite.  If zero, do what YYDEFACT says.  */
  private static final short yytable_ninf_ = -1;
  private static final short
  yytable_[] =
  {
        31,    32,    30,    34,    35,    80,     4,    93,    99,     7,
       8,     9,    74,    10,     7,     7,     7,   134,   139,    75,
      76,    77,    96,    74,     5,     6,     7,     8,     9,    23,
      10,    11,   161,    50,   162,    68,    69,    24,   135,   157,
      70,    71,    72,    73,    26,   147,   148,    33,    78,    37,
     102,    79,    39,    91,    12,    97,    92,   164,    98,    95,
      41,    75,    76,    77,    42,    13,   106,   107,   108,   109,
     110,   111,   112,   113,   114,   115,   116,   117,   118,   119,
     120,    63,    51,    52,    53,    54,    55,    56,    57,   125,
      88,    51,    40,    89,   144,    44,    44,    43,   145,    46,
      58,    59,    24,    24,   165,    47,   155,   156,    48,   130,
     131,   124,   126,   127,   129,   128,   133,   102,   137,    68,
      72,   140,   138,   149,   143,   158,   154,    49,   160,   146,
     159,    31,    81,   141,    82,   103,    38,   132,    31,   136,
       0,    31,    83,   153,    90,     0,     0,     0,     0,    31
  };

  /* YYCHECK.  */
  private static final short
  yycheck_[] =
  {
         7,     8,     7,    10,    11,    64,     0,    66,    67,     5,
       6,     7,     8,     9,     5,     5,     5,    14,   133,    15,
      16,    17,    11,     8,     3,     4,     5,     6,     7,    37,
       9,    10,    44,    40,    46,    35,    36,    55,    35,   154,
      40,    41,    35,    36,     6,    49,    50,     9,    44,    44,
      35,    47,    44,    44,    33,    44,    47,    47,    47,    66,
      34,    15,    16,    17,    44,    44,    18,    19,    20,    21,
      22,    23,    24,    25,    26,    27,    28,    29,    30,    31,
      32,    43,    35,    36,    37,    38,    39,    40,    41,    96,
      44,    35,    55,    47,    44,    46,    46,    45,    48,    46,
      53,    54,    55,    55,   163,    46,    49,    50,    46,    53,
      54,    45,    13,    50,   121,    44,    51,    35,    45,    35,
      35,    35,    48,    12,    52,    44,    51,    36,    52,   140,
     156,   138,    64,   138,    64,    74,    24,   124,   145,   127,
      -1,   148,    64,   148,    65,    -1,    -1,    -1,    -1,   156
  };

  /* STOS_[STATE-NUM] -- The (internal number of the) accessing
     symbol of state STATE-NUM.  */
  private static final byte
  yystos_[] =
  {
         0,    57,    58,    63,     0,     3,     4,     5,     6,     7,
       9,    10,    33,    44,    59,    60,    61,    64,    65,    67,
      68,    70,    74,    37,    55,    91,    95,    96,    97,    98,
      66,    97,    97,    95,    97,    97,    62,    44,    96,    44,
      55,    34,    44,    45,    46,    79,    46,    46,    46,    58,
      97,    35,    36,    37,    38,    39,    40,    41,    53,    54,
      92,    93,    94,    95,    80,    69,    71,    75,    35,    36,
      40,    41,    35,    36,     8,    15,    16,    17,    44,    47,
      65,    67,    68,    70,    81,    82,    85,    88,    44,    47,
      82,    44,    47,    65,    72,    97,    11,    44,    47,    65,
      76,    77,    35,    85,    86,    87,    18,    19,    20,    21,
      22,    23,    24,    25,    26,    27,    28,    29,    30,    31,
      32,    89,    90,    95,    45,    97,    13,    50,    44,    97,
      53,    54,    93,    51,    14,    35,    87,    45,    48,    90,
      35,    66,    73,    52,    44,    48,    79,    49,    50,    12,
      66,    83,    84,    66,    51,    49,    50,    90,    44,    84,
      52,    44,    46,    78,    47,    65
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
     295,   296,   297,   298,    59,    61,   123,   125,    91,    93,
      44,    40,    41,    45,    43,    46
  };

  /* YYR1[YYN] -- Symbol number of symbol that rule YYN derives.  */
  private static final byte
  yyr1_[] =
  {
         0,    56,    57,    58,    59,    60,    61,    62,    63,    63,
      64,    64,    64,    64,    64,    64,    64,    64,    65,    66,
      67,    68,    69,    69,    69,    70,    71,    71,    71,    71,
      72,    72,    73,    73,    74,    75,    75,    76,    76,    76,
      77,    77,    78,    78,    79,    80,    80,    81,    81,    81,
      81,    81,    81,    81,    82,    82,    82,    83,    83,    84,
      85,    86,    86,    87,    87,    87,    88,    88,    88,    89,
      89,    89,    89,    89,    89,    89,    89,    89,    89,    89,
      89,    89,    89,    89,    89,    90,    91,    92,    92,    92,
      92,    92,    92,    92,    92,    92,    92,    93,    93,    93,
      94,    94,    94,    95,    95,    96,    96,    98,    97
  };

  /* YYR2[YYN] -- Number of symbols composing right hand side of rule YYN.  */
  private static final byte
  yyr2_[] =
  {
         0,     2,     1,     2,     3,     3,     3,     0,     0,     2,
       1,     1,     1,     1,     1,     1,     1,     1,     3,     3,
       3,     5,     0,     2,     2,     5,     0,     2,     2,     2,
       3,     6,     1,     3,     5,     0,     2,     1,     1,     1,
      10,    12,     0,     2,     3,     0,     2,     1,     1,     1,
       1,     1,     1,     1,     6,     9,     6,     1,     3,     1,
       3,     1,     3,     1,     3,     3,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     2,     2,     1,     2,     2,
       1,     2,     2,     1,     2,     1,     3,     0,     2
  };

  /* YYTNAME[SYMBOL-NUM] -- String name of the symbol SYMBOL-NUM.
     First, the terminals, then, starting at \a yyntokens_, nonterminals.  */
  private static final String yytname_[] =
  {
    "$end", "error", "$undefined", "IMPORT", "PACKAGE", "OPTION", "MESSAGE",
  "EXTEND", "EXTENSIONS", "ENUM", "SERVICE", "RPC", "RETURNS", "TO", "MAX",
  "REQUIRED", "OPTIONAL", "REPEATED", "DOUBLE", "FLOAT", "INT32", "INT64",
  "UINT32", "UINT64", "SINT32", "SINT64", "FIXED32", "FIXED64", "SFIXED32",
  "SFIXED64", "BOOL", "STRING", "BYTES", "ENDFILE", "NAME", "INTCONST",
  "FLOATCONST", "STRINGCONST", "TRUE", "FALSE", "POSNAN", "POSINF",
  "NEGNAN", "NEGINF", "';'", "'='", "'{'", "'}'", "'['", "']'", "','",
  "'('", "')'", "'-'", "'+'", "'.'", "$accept", "root", "protobuffile",
  "packagedecl", "importstmt", "importprefix", "pushfile", "decllist",
  "decl", "optionstmt", "option", "message", "extend", "fieldlist",
  "enumtype", "enumlist", "enumvalue", "enumoptionlist", "service",
  "servicecaselist", "servicecase", "rpc", "optionstmtlist", "messagebody",
  "messageelementlist", "messageelement", "field", "fieldoptionlist",
  "fieldoption", "extensions", "extensionlist", "extensionrange",
  "cardinality", "type", "usertype", "packagename", "constant", "intconst",
  "floatconst", "path", "relpath", "name", "$@1", null
  };

  /* YYRHS -- A `-1'-separated list of the rules' RHS.  */
  private static final byte yyrhs_[] =
  {
        57,     0,    -1,    58,    -1,    63,    33,    -1,     4,    91,
      44,    -1,    61,    62,    58,    -1,     3,    37,    44,    -1,
      -1,    -1,    63,    64,    -1,    67,    -1,    68,    -1,    70,
      -1,    65,    -1,    74,    -1,    59,    -1,    60,    -1,    44,
      -1,     5,    66,    44,    -1,    97,    45,    92,    -1,     6,
      97,    79,    -1,     7,    95,    46,    69,    47,    -1,    -1,
      69,    82,    -1,    69,    44,    -1,     9,    97,    46,    71,
      47,    -1,    -1,    71,    65,    -1,    71,    72,    -1,    71,
      44,    -1,    97,    45,    93,    -1,    97,    45,    93,    48,
      73,    49,    -1,    66,    -1,    73,    50,    66,    -1,    10,
      97,    46,    75,    47,    -1,    -1,    75,    76,    -1,    65,
      -1,    77,    -1,    44,    -1,    11,    97,    51,    90,    52,
      12,    51,    90,    52,    44,    -1,    11,    97,    51,    90,
      52,    12,    51,    90,    52,    46,    78,    47,    -1,    -1,
      78,    65,    -1,    46,    80,    47,    -1,    -1,    80,    81,
      -1,    82,    -1,    70,    -1,    67,    -1,    68,    -1,    85,
      -1,    65,    -1,    44,    -1,    88,    89,    97,    45,    35,
      44,    -1,    88,    89,    97,    45,    35,    48,    83,    49,
      44,    -1,    88,    89,    97,    45,    35,    79,    -1,    84,
      -1,    83,    50,    84,    -1,    66,    -1,     8,    86,    44,
      -1,    87,    -1,    85,    50,    87,    -1,    35,    -1,    35,
      13,    35,    -1,    35,    13,    14,    -1,    15,    -1,    16,
      -1,    17,    -1,    18,    -1,    19,    -1,    20,    -1,    21,
      -1,    22,    -1,    23,    -1,    24,    -1,    25,    -1,    26,
      -1,    27,    -1,    28,    -1,    29,    -1,    30,    -1,    31,
      -1,    32,    -1,    90,    -1,    95,    -1,    95,    -1,    95,
      -1,    93,    -1,    94,    -1,    37,    -1,    38,    -1,    39,
      -1,    40,    -1,    41,    -1,    53,    40,    -1,    53,    41,
      -1,    35,    -1,    54,    35,    -1,    53,    35,    -1,    36,
      -1,    54,    36,    -1,    53,    36,    -1,    96,    -1,    55,
      96,    -1,    97,    -1,    96,    55,    97,    -1,    -1,    98,
      34,    -1
  };

  /* YYPRHS[YYN] -- Index of the first RHS symbol of rule number YYN in
     YYRHS.  */
  private static final short yyprhs_[] =
  {
         0,     0,     3,     5,     8,    12,    16,    20,    21,    22,
      25,    27,    29,    31,    33,    35,    37,    39,    41,    45,
      49,    53,    59,    60,    63,    66,    72,    73,    76,    79,
      82,    86,    93,    95,    99,   105,   106,   109,   111,   113,
     115,   126,   139,   140,   143,   147,   148,   151,   153,   155,
     157,   159,   161,   163,   165,   172,   182,   189,   191,   195,
     197,   201,   203,   207,   209,   213,   217,   219,   221,   223,
     225,   227,   229,   231,   233,   235,   237,   239,   241,   243,
     245,   247,   249,   251,   253,   255,   257,   259,   261,   263,
     265,   267,   269,   271,   273,   275,   278,   281,   283,   286,
     289,   291,   294,   297,   299,   302,   304,   308,   309
  };

  /* YYRLINE[YYN] -- Source line where rule number YYN was defined.  */
  private static final short yyrline_[] =
  {
         0,    72,    72,    76,    81,    86,    91,    94,    98,    99,
     104,   105,   106,   107,   108,   109,   110,   111,   115,   120,
     125,   130,   137,   138,   140,   145,   151,   152,   154,   156,
     160,   162,   167,   169,   175,   181,   182,   187,   188,   189,
     193,   195,   202,   203,   208,   214,   215,   220,   221,   222,
     223,   224,   225,   226,   231,   233,   235,   240,   242,   247,
     252,   257,   259,   264,   266,   268,   273,   274,   275,   279,
     280,   281,   282,   283,   284,   285,   286,   287,   288,   289,
     290,   291,   292,   293,   294,   298,   302,   307,   308,   309,
     310,   311,   312,   313,   314,   315,   316,   320,   321,   322,
     326,   327,   328,   333,   334,   338,   340,   347,   347
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
		       ((yystack.valueAt (yynrhs-(yyi + 1)))));
  }

  /* YYTRANSLATE(YYLEX) -- Bison symbol number corresponding to YYLEX.  */
  private static final byte yytranslate_table_[] =
  {
         0,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
      51,    52,     2,    54,    50,    53,    55,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,    44,
       2,    45,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,    48,     2,    49,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,    46,     2,    47,     2,     2,     2,     2,
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
      35,    36,    37,    38,    39,    40,    41,    42,    43
  };

  private static final byte yytranslate_ (int t)
  {
    if (t >= 0 && t <= yyuser_token_number_max_)
      return yytranslate_table_[t];
    else
      return yyundef_token_;
  }

  private static final int yylast_ = 149;
  private static final int yynnts_ = 43;
  private static final int yyempty_ = -2;
  private static final int yyfinal_ = 4;
  private static final int yyterror_ = 1;
  private static final int yyerrcode_ = 256;
  private static final int yyntokens_ = 56;

  private static final int yyuser_token_number_max_ = 298;
  private static final int yyundef_token_ = 2;

/* User implementation code.  */
/* Unqualified %code blocks.  */

/* Line 876 of lalr1.java  */
/* Line 22 of "protobuf.y"  */

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
	reset(filename,stream);
        return parse();
    }

    public Object parseWarning(String s)
    {
	lexstate.yywarning(s);
	return null;
    }

    public Object parseError(String s)
    {
	yyerror(s);
	return null;
    }



/* Line 876 of lalr1.java  */
/* Line 2078 of "./ProtobufParser.java"  */

}


