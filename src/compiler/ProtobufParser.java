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
  public static final int DEFAULT = 268;
  /** Token number, to be returned by the scanner.  */
  public static final int TO = 269;
  /** Token number, to be returned by the scanner.  */
  public static final int MAX = 270;
  /** Token number, to be returned by the scanner.  */
  public static final int REQUIRED = 271;
  /** Token number, to be returned by the scanner.  */
  public static final int OPTIONAL = 272;
  /** Token number, to be returned by the scanner.  */
  public static final int REPEATED = 273;
  /** Token number, to be returned by the scanner.  */
  public static final int DOUBLE = 274;
  /** Token number, to be returned by the scanner.  */
  public static final int FLOAT = 275;
  /** Token number, to be returned by the scanner.  */
  public static final int INT32 = 276;
  /** Token number, to be returned by the scanner.  */
  public static final int INT64 = 277;
  /** Token number, to be returned by the scanner.  */
  public static final int UINT32 = 278;
  /** Token number, to be returned by the scanner.  */
  public static final int UINT64 = 279;
  /** Token number, to be returned by the scanner.  */
  public static final int SINT32 = 280;
  /** Token number, to be returned by the scanner.  */
  public static final int SINT64 = 281;
  /** Token number, to be returned by the scanner.  */
  public static final int FIXED32 = 282;
  /** Token number, to be returned by the scanner.  */
  public static final int FIXED64 = 283;
  /** Token number, to be returned by the scanner.  */
  public static final int SFIXED32 = 284;
  /** Token number, to be returned by the scanner.  */
  public static final int SFIXED64 = 285;
  /** Token number, to be returned by the scanner.  */
  public static final int BOOL = 286;
  /** Token number, to be returned by the scanner.  */
  public static final int STRING = 287;
  /** Token number, to be returned by the scanner.  */
  public static final int BYTES = 288;
  /** Token number, to be returned by the scanner.  */
  public static final int GOOGLEOPTION = 289;
  /** Token number, to be returned by the scanner.  */
  public static final int ENDFILE = 290;
  /** Token number, to be returned by the scanner.  */
  public static final int NAME = 291;
  /** Token number, to be returned by the scanner.  */
  public static final int INTCONST = 292;
  /** Token number, to be returned by the scanner.  */
  public static final int FLOATCONST = 293;
  /** Token number, to be returned by the scanner.  */
  public static final int STRINGCONST = 294;
  /** Token number, to be returned by the scanner.  */
  public static final int TRUE = 295;
  /** Token number, to be returned by the scanner.  */
  public static final int FALSE = 296;



  

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
/* Line 70 of "protobuf.y"  */
    {protobufroot(((yystack.valueAt (1-(1)))));};
  break;
    

  case 3:
  if (yyn == 3)
    
/* Line 354 of lalr1.java  */
/* Line 74 of "protobuf.y"  */
    {yyval=protobuffile(null,((yystack.valueAt (3-(1)))),((yystack.valueAt (3-(2)))));};
  break;
    

  case 4:
  if (yyn == 4)
    
/* Line 354 of lalr1.java  */
/* Line 76 of "protobuf.y"  */
    {yyval=protobuffile(((yystack.valueAt (4-(1)))),((yystack.valueAt (4-(2)))),((yystack.valueAt (4-(3)))));};
  break;
    

  case 5:
  if (yyn == 5)
    
/* Line 354 of lalr1.java  */
/* Line 80 of "protobuf.y"  */
    {yyval=packagedecl(((yystack.valueAt (3-(2)))));};
  break;
    

  case 6:
  if (yyn == 6)
    
/* Line 354 of lalr1.java  */
/* Line 85 of "protobuf.y"  */
    {yyval=importlist(null,null);};
  break;
    

  case 7:
  if (yyn == 7)
    
/* Line 354 of lalr1.java  */
/* Line 87 of "protobuf.y"  */
    {yyval=importlist(((yystack.valueAt (2-(1)))),((yystack.valueAt (2-(2)))));};
  break;
    

  case 8:
  if (yyn == 8)
    
/* Line 354 of lalr1.java  */
/* Line 92 of "protobuf.y"  */
    {yyval=importstmt(((yystack.valueAt (3-(1)))),((yystack.valueAt (3-(3)))));};
  break;
    

  case 9:
  if (yyn == 9)
    
/* Line 354 of lalr1.java  */
/* Line 97 of "protobuf.y"  */
    {yyval=importprefix(((yystack.valueAt (3-(2)))));};
  break;
    

  case 10:
  if (yyn == 10)
    
/* Line 354 of lalr1.java  */
/* Line 99 of "protobuf.y"  */
    {if(!filepush()) {return YYABORT;};};
  break;
    

  case 11:
  if (yyn == 11)
    
/* Line 354 of lalr1.java  */
/* Line 103 of "protobuf.y"  */
    {yyval=decllist(null,null);};
  break;
    

  case 12:
  if (yyn == 12)
    
/* Line 354 of lalr1.java  */
/* Line 105 of "protobuf.y"  */
    {yyval=decllist(((yystack.valueAt (2-(1)))),((yystack.valueAt (2-(2)))));};
  break;
    

  case 13:
  if (yyn == 13)
    
/* Line 354 of lalr1.java  */
/* Line 109 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 14:
  if (yyn == 14)
    
/* Line 354 of lalr1.java  */
/* Line 110 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 15:
  if (yyn == 15)
    
/* Line 354 of lalr1.java  */
/* Line 111 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 16:
  if (yyn == 16)
    
/* Line 354 of lalr1.java  */
/* Line 112 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 17:
  if (yyn == 17)
    
/* Line 354 of lalr1.java  */
/* Line 113 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 18:
  if (yyn == 18)
    
/* Line 354 of lalr1.java  */
/* Line 114 of "protobuf.y"  */
    {yyval=null;};
  break;
    

  case 19:
  if (yyn == 19)
    
/* Line 354 of lalr1.java  */
/* Line 119 of "protobuf.y"  */
    {yyval=((yystack.valueAt (3-(2))));};
  break;
    

  case 20:
  if (yyn == 20)
    
/* Line 354 of lalr1.java  */
/* Line 121 of "protobuf.y"  */
    {yyval=useroption(((yystack.valueAt (5-(3)))));};
  break;
    

  case 21:
  if (yyn == 21)
    
/* Line 354 of lalr1.java  */
/* Line 126 of "protobuf.y"  */
    {yyval=option(((yystack.valueAt (3-(1)))),((yystack.valueAt (3-(3)))));};
  break;
    

  case 22:
  if (yyn == 22)
    
/* Line 354 of lalr1.java  */
/* Line 131 of "protobuf.y"  */
    {yyval=message(((yystack.valueAt (3-(2)))),((yystack.valueAt (3-(3)))));};
  break;
    

  case 23:
  if (yyn == 23)
    
/* Line 354 of lalr1.java  */
/* Line 136 of "protobuf.y"  */
    {yyval=extend(((yystack.valueAt (5-(2)))),((yystack.valueAt (5-(4)))));};
  break;
    

  case 24:
  if (yyn == 24)
    
/* Line 354 of lalr1.java  */
/* Line 138 of "protobuf.y"  */
    {yyval=null; /* ignore */ };
  break;
    

  case 25:
  if (yyn == 25)
    
/* Line 354 of lalr1.java  */
/* Line 143 of "protobuf.y"  */
    {yyval=fieldlist(null,null);};
  break;
    

  case 26:
  if (yyn == 26)
    
/* Line 354 of lalr1.java  */
/* Line 145 of "protobuf.y"  */
    {yyval=fieldlist(((yystack.valueAt (2-(1)))),((yystack.valueAt (2-(2)))));};
  break;
    

  case 27:
  if (yyn == 27)
    
/* Line 354 of lalr1.java  */
/* Line 147 of "protobuf.y"  */
    {yyval=((yystack.valueAt (2-(1))));};
  break;
    

  case 28:
  if (yyn == 28)
    
/* Line 354 of lalr1.java  */
/* Line 152 of "protobuf.y"  */
    {yyval=enumtype(((yystack.valueAt (5-(2)))),((yystack.valueAt (5-(4)))));};
  break;
    

  case 29:
  if (yyn == 29)
    
/* Line 354 of lalr1.java  */
/* Line 157 of "protobuf.y"  */
    {yyval=enumlist(null,null);};
  break;
    

  case 30:
  if (yyn == 30)
    
/* Line 354 of lalr1.java  */
/* Line 159 of "protobuf.y"  */
    {yyval=enumlist(((yystack.valueAt (2-(1)))),((yystack.valueAt (2-(2)))));};
  break;
    

  case 31:
  if (yyn == 31)
    
/* Line 354 of lalr1.java  */
/* Line 161 of "protobuf.y"  */
    {yyval=enumlist(((yystack.valueAt (2-(1)))),((yystack.valueAt (2-(2)))));};
  break;
    

  case 32:
  if (yyn == 32)
    
/* Line 354 of lalr1.java  */
/* Line 162 of "protobuf.y"  */
    {yyval=((yystack.valueAt (2-(1))));};
  break;
    

  case 33:
  if (yyn == 33)
    
/* Line 354 of lalr1.java  */
/* Line 167 of "protobuf.y"  */
    {if((yyval=enumfield(((yystack.valueAt (3-(1)))),((yystack.valueAt (3-(3))))))==null) {return YYABORT;}};
  break;
    

  case 34:
  if (yyn == 34)
    
/* Line 354 of lalr1.java  */
/* Line 172 of "protobuf.y"  */
    {yyval=service(((yystack.valueAt (5-(2)))),((yystack.valueAt (5-(4)))));};
  break;
    

  case 35:
  if (yyn == 35)
    
/* Line 354 of lalr1.java  */
/* Line 177 of "protobuf.y"  */
    {yyval=servicecaselist(null,null);};
  break;
    

  case 36:
  if (yyn == 36)
    
/* Line 354 of lalr1.java  */
/* Line 179 of "protobuf.y"  */
    {yyval=servicecaselist(((yystack.valueAt (2-(1)))),((yystack.valueAt (2-(2)))));};
  break;
    

  case 37:
  if (yyn == 37)
    
/* Line 354 of lalr1.java  */
/* Line 183 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 38:
  if (yyn == 38)
    
/* Line 354 of lalr1.java  */
/* Line 184 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 39:
  if (yyn == 39)
    
/* Line 354 of lalr1.java  */
/* Line 185 of "protobuf.y"  */
    {yyval=null;};
  break;
    

  case 40:
  if (yyn == 40)
    
/* Line 354 of lalr1.java  */
/* Line 190 of "protobuf.y"  */
    {yyval=rpc(((yystack.valueAt (10-(2)))),((yystack.valueAt (10-(4)))),((yystack.valueAt (10-(8)))));};
  break;
    

  case 41:
  if (yyn == 41)
    
/* Line 354 of lalr1.java  */
/* Line 195 of "protobuf.y"  */
    {yyval=((yystack.valueAt (3-(2))));};
  break;
    

  case 42:
  if (yyn == 42)
    
/* Line 354 of lalr1.java  */
/* Line 200 of "protobuf.y"  */
    {yyval=messageelementlist(null,null);};
  break;
    

  case 43:
  if (yyn == 43)
    
/* Line 354 of lalr1.java  */
/* Line 202 of "protobuf.y"  */
    {yyval=messageelementlist(((yystack.valueAt (2-(1)))),((yystack.valueAt (2-(2)))));};
  break;
    

  case 44:
  if (yyn == 44)
    
/* Line 354 of lalr1.java  */
/* Line 206 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 45:
  if (yyn == 45)
    
/* Line 354 of lalr1.java  */
/* Line 207 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 46:
  if (yyn == 46)
    
/* Line 354 of lalr1.java  */
/* Line 208 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 47:
  if (yyn == 47)
    
/* Line 354 of lalr1.java  */
/* Line 209 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 48:
  if (yyn == 48)
    
/* Line 354 of lalr1.java  */
/* Line 210 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 49:
  if (yyn == 49)
    
/* Line 354 of lalr1.java  */
/* Line 211 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 50:
  if (yyn == 50)
    
/* Line 354 of lalr1.java  */
/* Line 212 of "protobuf.y"  */
    {yyval=null;};
  break;
    

  case 51:
  if (yyn == 51)
    
/* Line 354 of lalr1.java  */
/* Line 218 of "protobuf.y"  */
    {yyval=field(((yystack.valueAt (6-(1)))),((yystack.valueAt (6-(2)))),((yystack.valueAt (6-(3)))),((yystack.valueAt (6-(5)))),null);};
  break;
    

  case 52:
  if (yyn == 52)
    
/* Line 354 of lalr1.java  */
/* Line 220 of "protobuf.y"  */
    {yyval=field(((yystack.valueAt (9-(1)))),((yystack.valueAt (9-(2)))),((yystack.valueAt (9-(3)))),((yystack.valueAt (9-(5)))),((yystack.valueAt (9-(7)))));};
  break;
    

  case 53:
  if (yyn == 53)
    
/* Line 354 of lalr1.java  */
/* Line 225 of "protobuf.y"  */
    {yyval=fieldoptionlist(null,((yystack.valueAt (1-(1)))));};
  break;
    

  case 54:
  if (yyn == 54)
    
/* Line 354 of lalr1.java  */
/* Line 227 of "protobuf.y"  */
    {yyval=fieldoptionlist(((yystack.valueAt (3-(1)))),((yystack.valueAt (3-(3)))));};
  break;
    

  case 55:
  if (yyn == 55)
    
/* Line 354 of lalr1.java  */
/* Line 232 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 56:
  if (yyn == 56)
    
/* Line 354 of lalr1.java  */
/* Line 234 of "protobuf.y"  */
    {yyval=option(AST.DEFAULTNAME,((yystack.valueAt (3-(3)))));};
  break;
    

  case 57:
  if (yyn == 57)
    
/* Line 354 of lalr1.java  */
/* Line 239 of "protobuf.y"  */
    { yyval=extensions(((yystack.valueAt (3-(2)))));};
  break;
    

  case 58:
  if (yyn == 58)
    
/* Line 354 of lalr1.java  */
/* Line 244 of "protobuf.y"  */
    {yyval=extensionlist(null,((yystack.valueAt (1-(1)))));};
  break;
    

  case 59:
  if (yyn == 59)
    
/* Line 354 of lalr1.java  */
/* Line 246 of "protobuf.y"  */
    {yyval=extensionlist(((yystack.valueAt (3-(1)))),((yystack.valueAt (3-(3)))));};
  break;
    

  case 60:
  if (yyn == 60)
    
/* Line 354 of lalr1.java  */
/* Line 251 of "protobuf.y"  */
    {if((yyval=extensionrange(((yystack.valueAt (1-(1)))),null)) == null) return YYABORT;};
  break;
    

  case 61:
  if (yyn == 61)
    
/* Line 354 of lalr1.java  */
/* Line 253 of "protobuf.y"  */
    {if((yyval=extensionrange(((yystack.valueAt (3-(1)))),((yystack.valueAt (3-(3)))))) == null) return YYABORT;};
  break;
    

  case 62:
  if (yyn == 62)
    
/* Line 354 of lalr1.java  */
/* Line 255 of "protobuf.y"  */
    {if((yyval=extensionrange(((yystack.valueAt (3-(1)))),null)) == null) return YYABORT;};
  break;
    

  case 63:
  if (yyn == 63)
    
/* Line 354 of lalr1.java  */
/* Line 259 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 64:
  if (yyn == 64)
    
/* Line 354 of lalr1.java  */
/* Line 260 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 65:
  if (yyn == 65)
    
/* Line 354 of lalr1.java  */
/* Line 261 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 66:
  if (yyn == 66)
    
/* Line 354 of lalr1.java  */
/* Line 265 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 67:
  if (yyn == 67)
    
/* Line 354 of lalr1.java  */
/* Line 266 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 68:
  if (yyn == 68)
    
/* Line 354 of lalr1.java  */
/* Line 267 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 69:
  if (yyn == 69)
    
/* Line 354 of lalr1.java  */
/* Line 268 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 70:
  if (yyn == 70)
    
/* Line 354 of lalr1.java  */
/* Line 269 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 71:
  if (yyn == 71)
    
/* Line 354 of lalr1.java  */
/* Line 270 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 72:
  if (yyn == 72)
    
/* Line 354 of lalr1.java  */
/* Line 271 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 73:
  if (yyn == 73)
    
/* Line 354 of lalr1.java  */
/* Line 272 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 74:
  if (yyn == 74)
    
/* Line 354 of lalr1.java  */
/* Line 273 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 75:
  if (yyn == 75)
    
/* Line 354 of lalr1.java  */
/* Line 274 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 76:
  if (yyn == 76)
    
/* Line 354 of lalr1.java  */
/* Line 275 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 77:
  if (yyn == 77)
    
/* Line 354 of lalr1.java  */
/* Line 276 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 78:
  if (yyn == 78)
    
/* Line 354 of lalr1.java  */
/* Line 277 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 79:
  if (yyn == 79)
    
/* Line 354 of lalr1.java  */
/* Line 278 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 80:
  if (yyn == 80)
    
/* Line 354 of lalr1.java  */
/* Line 279 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 81:
  if (yyn == 81)
    
/* Line 354 of lalr1.java  */
/* Line 280 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 82:
  if (yyn == 82)
    
/* Line 354 of lalr1.java  */
/* Line 285 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 83:
  if (yyn == 83)
    
/* Line 354 of lalr1.java  */
/* Line 290 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 84:
  if (yyn == 84)
    
/* Line 354 of lalr1.java  */
/* Line 295 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 85:
  if (yyn == 85)
    
/* Line 354 of lalr1.java  */
/* Line 301 of "protobuf.y"  */
    {if(illegalname(((yystack.valueAt (1-(1)))))) {return YYABORT;}; yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 86:
  if (yyn == 86)
    
/* Line 354 of lalr1.java  */
/* Line 306 of "protobuf.y"  */
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
/* Line 316 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 93:
  if (yyn == 93)
    
/* Line 354 of lalr1.java  */
/* Line 317 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 94:
  if (yyn == 94)
    
/* Line 354 of lalr1.java  */
/* Line 318 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 95:
  if (yyn == 95)
    
/* Line 354 of lalr1.java  */
/* Line 319 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 96:
  if (yyn == 96)
    
/* Line 354 of lalr1.java  */
/* Line 320 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 97:
  if (yyn == 97)
    
/* Line 354 of lalr1.java  */
/* Line 321 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 98:
  if (yyn == 98)
    
/* Line 354 of lalr1.java  */
/* Line 322 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 99:
  if (yyn == 99)
    
/* Line 354 of lalr1.java  */
/* Line 323 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 100:
  if (yyn == 100)
    
/* Line 354 of lalr1.java  */
/* Line 324 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 101:
  if (yyn == 101)
    
/* Line 354 of lalr1.java  */
/* Line 325 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 102:
  if (yyn == 102)
    
/* Line 354 of lalr1.java  */
/* Line 326 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 103:
  if (yyn == 103)
    
/* Line 354 of lalr1.java  */
/* Line 327 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 104:
  if (yyn == 104)
    
/* Line 354 of lalr1.java  */
/* Line 328 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 105:
  if (yyn == 105)
    
/* Line 354 of lalr1.java  */
/* Line 329 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 106:
  if (yyn == 106)
    
/* Line 354 of lalr1.java  */
/* Line 330 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    

  case 107:
  if (yyn == 107)
    
/* Line 354 of lalr1.java  */
/* Line 331 of "protobuf.y"  */
    {yyval=((yystack.valueAt (1-(1))));};
  break;
    



/* Line 354 of lalr1.java  */
/* Line 1322 of "./tmp"  */
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
  private static final short yypact_ninf_ = -104;
  private static final short yypact_[] =
  {
        38,   245,    29,  -104,  -104,    40,  -104,  -104,  -104,  -104,
    -104,  -104,  -104,  -104,  -104,  -104,  -104,  -104,  -104,  -104,
    -104,  -104,     5,  -104,  -104,    40,     9,  -104,  -104,    59,
    -104,   110,     8,    38,    71,   245,   195,   245,   245,  -104,
    -104,  -104,  -104,  -104,  -104,  -104,  -104,  -104,  -104,  -104,
     245,    11,    14,  -104,    24,    44,    45,  -104,    47,    51,
      12,  -104,   122,  -104,  -104,  -104,  -104,  -104,  -104,    13,
    -104,  -104,  -104,  -104,  -104,  -104,  -104,    16,    10,   250,
       2,    25,  -104,    23,  -104,  -104,  -104,  -104,  -104,  -104,
    -104,  -104,  -104,  -104,  -104,  -104,   161,  -104,  -104,  -104,
    -104,    71,  -104,  -104,  -104,  -104,    26,   245,  -104,  -104,
    -104,  -104,  -104,    70,    49,    58,  -104,  -104,  -104,  -104,
    -104,  -104,  -104,  -104,  -104,  -104,  -104,  -104,  -104,  -104,
    -104,  -104,   245,  -104,  -104,    65,    61,     0,    68,  -104,
      64,  -104,   245,  -104,  -104,  -104,    69,    66,     3,    99,
    -104,   229,    75,    76,  -104,    -9,  -104,   245,   122,    80,
     229,    91,  -104,  -104,  -104,    82,  -104
  };

  /* YYDEFACT[S] -- default rule to reduce with in state S when YYTABLE
     doesn't specify something else to do.  Zero means the default is an
     error.  */
  private static final byte yydefact_[] =
  {
         6,     0,     0,     2,     6,    11,    93,    94,    95,    96,
      97,    98,    99,   100,   101,   102,   103,   104,   105,   106,
     107,    92,     0,    83,     1,    11,     0,     7,    10,     0,
       5,     0,     0,     6,     0,     0,     0,     0,     0,     3,
      18,    12,    16,    13,    14,    15,    17,     4,     9,     8,
       0,     0,     0,    85,     0,     0,     0,    84,     0,     0,
       0,    19,     0,    42,    22,    25,    25,    29,    35,     0,
      87,    88,    89,    90,    91,    21,    86,     0,     0,     0,
       0,     0,    20,     0,    63,    64,    65,    50,    41,    49,
      46,    47,    45,    43,    44,    48,     0,    27,    24,    26,
      23,    95,    32,    28,    30,    31,     0,     0,    39,    34,
      37,    36,    38,    60,     0,     0,    58,    66,    67,    68,
      69,    70,    71,    72,    73,    74,    75,    76,    77,    78,
      79,    80,     0,    81,    82,     0,     0,     0,     0,    57,
       0,    33,     0,    62,    61,    59,     0,     0,     0,     0,
      51,     0,     0,     0,    55,     0,    53,     0,     0,     0,
       0,     0,    56,    52,    54,     0,    40
  };

  /* YYPGOTO[NTERM-NUM].  */
  private static final byte yypgoto_[] =
  {
      -104,  -104,   108,  -104,   109,  -104,  -104,  -104,   117,  -104,
      15,   -48,    67,    72,    77,    73,  -104,  -104,  -104,  -104,
    -104,  -104,  -104,  -104,  -104,    74,  -104,   -14,   112,  -104,
      17,  -104,  -104,  -103,  -104,  -104,   -34,   -11,    -1
  };

  /* YYDEFGOTO[NTERM-NUM].  */
  private static final short
  yydefgoto_[] =
  {
        -1,     2,     3,     4,     5,    27,    28,    33,    29,    41,
      42,    51,    43,    44,    78,    45,    80,   105,    46,    81,
     111,   112,    64,    77,    93,    99,   155,   156,    95,   115,
     116,    96,   132,   133,    22,    56,    52,    75,    53
  };

  /* YYTABLE[YYPACT[STATE-NUM]].  What to do in state STATE-NUM.  If
     positive, shift that token.  If negative, reduce the rule which
     number is the opposite.  If zero, do what YYDEFACT says.  */
  private static final short yytable_ninf_ = -1;
  private static final short
  yytable_[] =
  {
        23,    54,    60,    58,    59,     6,     7,   101,     9,    10,
      11,    12,    13,    14,    15,   143,    16,    17,    18,    19,
      20,    34,    35,    36,    83,    37,    84,    85,    86,    24,
      34,    83,    84,    85,    86,    57,   107,   144,    21,   147,
     159,   160,     1,    26,   102,   150,   106,    30,    32,   103,
      48,   151,    97,    61,   161,    82,    69,    98,    87,    62,
     113,    76,   134,    88,    34,    35,    36,   108,    37,    38,
      63,   135,   109,   136,     6,     7,     8,     9,    10,    11,
      12,    13,    14,    15,   137,    16,    17,    18,    19,    20,
      65,    66,    89,    67,    39,   104,   110,    68,   140,   138,
     139,    40,   141,   154,   142,   113,   148,    21,   134,   146,
     149,   152,   154,    25,    50,    34,    35,    36,   157,    37,
      38,   158,   163,   134,   166,     6,     7,     8,     9,    10,
      11,    12,    13,    14,    15,   165,    16,    17,    18,    19,
      20,    49,    31,    79,    90,    47,   164,   162,     0,    91,
      92,    94,    40,     0,     0,   145,     0,    76,    21,    70,
      71,    72,    73,    74,     6,     7,     8,     9,    10,    11,
      12,    13,    14,    15,     0,    16,    17,    18,    19,    20,
     117,   118,   119,   120,   121,   122,   123,   124,   125,   126,
     127,   128,   129,   130,   131,   114,     0,    21,     6,     7,
       8,     9,    10,    11,    12,    13,    14,    15,     0,    16,
      17,    18,    19,    20,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,    55,
       0,    21,     6,     7,     8,     9,    10,    11,    12,    13,
      14,    15,   153,    16,    17,    18,    19,    20,     6,     7,
       8,     9,    10,    11,    12,    13,    14,    15,     0,    16,
      17,    18,    19,    20,     0,    21,    84,    85,    86,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,    21,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,    97,     0,     0,     0,     0,   100
  };

  /* YYCHECK.  */
  private static final short
  yycheck_[] =
  {
         1,    35,    50,    37,    38,     3,     4,     5,     6,     7,
       8,     9,    10,    11,    12,    15,    14,    15,    16,    17,
      18,     5,     6,     7,     8,     9,    16,    17,    18,     0,
       5,     8,    16,    17,    18,    36,    11,    37,    36,   142,
      49,    50,     4,     3,    42,    42,    80,    42,    39,    47,
      42,    48,    42,    42,   157,    42,    44,    47,    42,    45,
      37,    62,    96,    47,     5,     6,     7,    42,     9,    10,
      46,    45,    47,   107,     3,     4,     5,     6,     7,     8,
       9,    10,    11,    12,    14,    14,    15,    16,    17,    18,
      46,    46,    77,    46,    35,    80,    81,    46,   132,    50,
      42,    42,    37,   151,    43,    37,    37,    36,   142,    45,
      44,    12,   160,     4,    43,     5,     6,     7,    43,     9,
      10,    45,    42,   157,    42,     3,     4,     5,     6,     7,
       8,     9,    10,    11,    12,    44,    14,    15,    16,    17,
      18,    33,    25,    66,    77,    35,   160,   158,    -1,    77,
      77,    77,    42,    -1,    -1,   138,    -1,   158,    36,    37,
      38,    39,    40,    41,     3,     4,     5,     6,     7,     8,
       9,    10,    11,    12,    -1,    14,    15,    16,    17,    18,
      19,    20,    21,    22,    23,    24,    25,    26,    27,    28,
      29,    30,    31,    32,    33,    83,    -1,    36,     3,     4,
       5,     6,     7,     8,     9,    10,    11,    12,    -1,    14,
      15,    16,    17,    18,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    34,
      -1,    36,     3,     4,     5,     6,     7,     8,     9,    10,
      11,    12,    13,    14,    15,    16,    17,    18,     3,     4,
       5,     6,     7,     8,     9,    10,    11,    12,    -1,    14,
      15,    16,    17,    18,    -1,    36,    16,    17,    18,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    36,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    42,    -1,    -1,    -1,    -1,    47
  };

  /* STOS_[STATE-NUM] -- The (internal number of the) accessing
     symbol of state STATE-NUM.  */
  private static final byte
  yystos_[] =
  {
         0,     4,    52,    53,    54,    55,     3,     4,     5,     6,
       7,     8,     9,    10,    11,    12,    14,    15,    16,    17,
      18,    36,    85,    89,     0,    55,     3,    56,    57,    59,
      42,    59,    39,    58,     5,     6,     7,     9,    10,    35,
      42,    60,    61,    63,    64,    66,    69,    35,    42,    53,
      43,    62,    87,    89,    87,    34,    86,    89,    87,    87,
      62,    42,    45,    46,    73,    46,    46,    46,    46,    44,
      37,    38,    39,    40,    41,    88,    89,    74,    65,    65,
      67,    70,    42,     8,    16,    17,    18,    42,    47,    61,
      63,    64,    66,    75,    76,    79,    82,    42,    47,    76,
      47,     5,    42,    47,    61,    68,    87,    11,    42,    47,
      61,    71,    72,    37,    79,    80,    81,    19,    20,    21,
      22,    23,    24,    25,    26,    27,    28,    29,    30,    31,
      32,    33,    83,    84,    87,    45,    87,    14,    50,    42,
      87,    37,    43,    15,    37,    81,    45,    84,    37,    44,
      42,    48,    12,    13,    62,    77,    78,    43,    45,    49,
      50,    84,    88,    42,    78,    44,    42
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
     295,   296,    59,    40,    41,    61,   123,   125,    91,    93,
      44
  };

  /* YYR1[YYN] -- Symbol number of symbol that rule YYN derives.  */
  private static final byte
  yyr1_[] =
  {
         0,    51,    52,    53,    53,    54,    55,    55,    56,    57,
      58,    59,    59,    60,    60,    60,    60,    60,    60,    61,
      61,    62,    63,    64,    64,    65,    65,    65,    66,    67,
      67,    67,    67,    68,    69,    70,    70,    71,    71,    71,
      72,    73,    74,    74,    75,    75,    75,    75,    75,    75,
      75,    76,    76,    77,    77,    78,    78,    79,    80,    80,
      81,    81,    81,    82,    82,    82,    83,    83,    83,    83,
      83,    83,    83,    83,    83,    83,    83,    83,    83,    83,
      83,    83,    84,    85,    86,    87,    88,    88,    88,    88,
      88,    88,    89,    89,    89,    89,    89,    89,    89,    89,
      89,    89,    89,    89,    89,    89,    89,    89
  };

  /* YYR2[YYN] -- Number of symbols composing right hand side of rule YYN.  */
  private static final byte
  yyr2_[] =
  {
         0,     2,     1,     3,     4,     3,     0,     2,     3,     3,
       0,     0,     2,     1,     1,     1,     1,     1,     1,     3,
       5,     3,     3,     5,     5,     0,     2,     2,     5,     0,
       2,     2,     2,     3,     5,     0,     2,     1,     1,     1,
      10,     3,     0,     2,     1,     1,     1,     1,     1,     1,
       1,     6,     9,     1,     3,     1,     3,     3,     1,     3,
       1,     3,     3,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1
  };

  /* YYTNAME[SYMBOL-NUM] -- String name of the symbol SYMBOL-NUM.
     First, the terminals, then, starting at \a yyntokens_, nonterminals.  */
  private static final String yytname_[] =
  {
    "$end", "error", "$undefined", "IMPORT", "PACKAGE", "OPTION", "MESSAGE",
  "EXTEND", "EXTENSIONS", "ENUM", "SERVICE", "RPC", "RETURNS", "DEFAULT",
  "TO", "MAX", "REQUIRED", "OPTIONAL", "REPEATED", "DOUBLE", "FLOAT",
  "INT32", "INT64", "UINT32", "UINT64", "SINT32", "SINT64", "FIXED32",
  "FIXED64", "SFIXED32", "SFIXED64", "BOOL", "STRING", "BYTES",
  "GOOGLEOPTION", "ENDFILE", "NAME", "INTCONST", "FLOATCONST",
  "STRINGCONST", "TRUE", "FALSE", "';'", "'('", "')'", "'='", "'{'", "'}'",
  "'['", "']'", "','", "$accept", "root", "protobuffile", "packagedecl",
  "importlist", "importstmt", "importprefix", "pushfile", "decllist",
  "decl", "optionstmt", "option", "message", "extend", "fieldlist",
  "enumtype", "enumlist", "enumfield", "service", "servicecaselist",
  "servicecase", "rpc", "messagebody", "messageelementlist",
  "messageelement", "field", "fieldoptionlist", "fieldoption",
  "extensions", "extensionlist", "extensionrange", "cardinality", "type",
  "usertype", "packagename", "path", "name", "constant", "symbol", null
  };

  /* YYRHS -- A `-1'-separated list of the rules' RHS.  */
  private static final byte yyrhs_[] =
  {
        52,     0,    -1,    53,    -1,    55,    59,    35,    -1,    54,
      55,    59,    35,    -1,     4,    85,    42,    -1,    -1,    55,
      56,    -1,    57,    58,    53,    -1,     3,    39,    42,    -1,
      -1,    -1,    59,    60,    -1,    63,    -1,    64,    -1,    66,
      -1,    61,    -1,    69,    -1,    42,    -1,     5,    62,    42,
      -1,     5,    43,    62,    44,    42,    -1,    87,    45,    88,
      -1,     6,    87,    73,    -1,     7,    86,    46,    65,    47,
      -1,     7,    34,    46,    65,    47,    -1,    -1,    65,    76,
      -1,    65,    42,    -1,     9,    87,    46,    67,    47,    -1,
      -1,    67,    61,    -1,    67,    68,    -1,    67,    42,    -1,
      87,    45,    37,    -1,    10,    87,    46,    70,    47,    -1,
      -1,    70,    71,    -1,    61,    -1,    72,    -1,    42,    -1,
      11,    87,    43,    84,    44,    12,    43,    84,    44,    42,
      -1,    46,    74,    47,    -1,    -1,    74,    75,    -1,    76,
      -1,    66,    -1,    63,    -1,    64,    -1,    79,    -1,    61,
      -1,    42,    -1,    82,    83,    87,    45,    37,    42,    -1,
      82,    83,    87,    45,    37,    48,    77,    49,    42,    -1,
      78,    -1,    77,    50,    78,    -1,    62,    -1,    13,    45,
      88,    -1,     8,    80,    42,    -1,    81,    -1,    79,    50,
      81,    -1,    37,    -1,    37,    14,    37,    -1,    37,    14,
      15,    -1,    16,    -1,    17,    -1,    18,    -1,    19,    -1,
      20,    -1,    21,    -1,    22,    -1,    23,    -1,    24,    -1,
      25,    -1,    26,    -1,    27,    -1,    28,    -1,    29,    -1,
      30,    -1,    31,    -1,    32,    -1,    33,    -1,    84,    -1,
      87,    -1,    89,    -1,    89,    -1,    89,    -1,    89,    -1,
      37,    -1,    38,    -1,    39,    -1,    40,    -1,    41,    -1,
      36,    -1,     3,    -1,     4,    -1,     5,    -1,     6,    -1,
       7,    -1,     8,    -1,     9,    -1,    10,    -1,    11,    -1,
      12,    -1,    14,    -1,    15,    -1,    16,    -1,    17,    -1,
      18,    -1
  };

  /* YYPRHS[YYN] -- Index of the first RHS symbol of rule number YYN in
     YYRHS.  */
  private static final short yyprhs_[] =
  {
         0,     0,     3,     5,     9,    14,    18,    19,    22,    26,
      30,    31,    32,    35,    37,    39,    41,    43,    45,    47,
      51,    57,    61,    65,    71,    77,    78,    81,    84,    90,
      91,    94,    97,   100,   104,   110,   111,   114,   116,   118,
     120,   131,   135,   136,   139,   141,   143,   145,   147,   149,
     151,   153,   160,   170,   172,   176,   178,   182,   186,   188,
     192,   194,   198,   202,   204,   206,   208,   210,   212,   214,
     216,   218,   220,   222,   224,   226,   228,   230,   232,   234,
     236,   238,   240,   242,   244,   246,   248,   250,   252,   254,
     256,   258,   260,   262,   264,   266,   268,   270,   272,   274,
     276,   278,   280,   282,   284,   286,   288,   290
  };

  /* YYRLINE[YYN] -- Source line where rule number YYN was defined.  */
  private static final short yyrline_[] =
  {
         0,    69,    69,    73,    75,    79,    85,    86,    91,    96,
      99,   103,   104,   109,   110,   111,   112,   113,   114,   118,
     120,   125,   130,   135,   137,   143,   144,   146,   151,   157,
     158,   160,   162,   166,   171,   177,   178,   183,   184,   185,
     189,   194,   200,   201,   206,   207,   208,   209,   210,   211,
     212,   217,   219,   224,   226,   231,   233,   238,   243,   245,
     250,   252,   254,   259,   260,   261,   265,   266,   267,   268,
     269,   270,   271,   272,   273,   274,   275,   276,   277,   278,
     279,   280,   285,   290,   295,   300,   306,   307,   308,   309,
     310,   311,   316,   317,   318,   319,   320,   321,   322,   323,
     324,   325,   326,   327,   328,   329,   330,   331
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
      43,    44,     2,     2,    50,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,    42,
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
      35,    36,    37,    38,    39,    40,    41
  };

  private static final byte yytranslate_ (int t)
  {
    if (t >= 0 && t <= yyuser_token_number_max_)
      return yytranslate_table_[t];
    else
      return yyundef_token_;
  }

  private static final int yylast_ = 297;
  private static final int yynnts_ = 39;
  private static final int yyempty_ = -2;
  private static final int yyfinal_ = 24;
  private static final int yyterror_ = 1;
  private static final int yyerrcode_ = 256;
  private static final int yyntokens_ = 51;

  private static final int yyuser_token_number_max_ = 296;
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
        //((ProtobufLexer)yylexer).reset(state);
        //((ProtobufLexer)yylexer).setStream(stream);
        return parse();
    }

    public Object parseError(String s)
    {
	yyerror(s);
	return null;
    }



/* Line 876 of lalr1.java  */
/* Line 2086 of "./tmp"  */

}


