/* Copyright 2009, UCAR/Unidata and OPeNDAP, Inc.
   See the COPYRIGHT file for more information. */

/*
 * Bison 2.4.2 grammar file for Google Protocol Buffers .proto files
 */

%error-verbose
%name-prefix "Protobuf"

%define public
%define package "unidata.protobuf.compiler"
%define extends "ProtobufActions"
%define throws "IOException"
%define lex_throws "IOException"

%code imports {
import java.io.*;
import unidata.protobuf.compiler.AST.Position;
}

%code {
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
}

%token IMPORT PACKAGE OPTION MESSAGE EXTEND EXTENSIONS
%token ENUM SERVICE RPC RETURNS GROUP
%token DEFAULT TO MAX REQUIRED OPTIONAL REPEATED
%token DOUBLE FLOAT INT32 INT64 UINT32 UINT64
%token SINT32 SINT64 FIXED32 FIXED64 SFIXED32 SFIXED64
%token BOOL STRING BYTES
%token GOOGLEOPTION
%token ENDFILE

%token NAME INTCONST FLOATCONST STRINGCONST TRUE FALSE POSNAN POSINF NEGNAN NEGINF


%start root

%%

root: protobuffile
	    {protobufroot($1);}

protobuffile:
	decllist ENDFILE
	    {$$=protobuffile($1);}
	;

packagedecl:
	PACKAGE packagename ';'
	    {$$=packagedecl($2);}
	;

importstmt:
	importprefix pushfile protobuffile
	    {$$=importstmt($1,$3);}
        ;
        
importprefix:
        IMPORT STRINGCONST ';'
	    {$$=importprefix($2);}

pushfile: /*empty*/ {if(!filepush()) {return YYABORT;};}

decllist:
	  /*empty*/
	    {$$=decllist(null,null);}
	| decllist decl
	    {$$=decllist($1,$2);}
	;	

decl:
          message       {$$=$1;}
        | extend        {$$=$1;}
        | enumtype      {$$=$1;}
        | optionstmt    {$$=$1;}
        | service       {$$=$1;}
        | packagedecl   {$$=$1;}
        | importstmt  {$$=$1;}
        | /*empty*/ ';' {$$=null;}
        ;

optionstmt:
        OPTION option ';'
   	    {$$=$2;}
        ;

option:
	  name '=' constant
   	    {$$=option($1,$3);}
	| '(' name ')' '=' constant
   	    {$$=useroption($2,$5);}
        ;

message:
        MESSAGE name messagebody
	    {$$=message($2,$3);}
        ;

extend:
          EXTEND path '{' fieldlist '}'
	    {$$=extend($2,$4);}
        | EXTEND GOOGLEOPTION  '{' fieldlist '}'
	    {$$=null; /* ignore */ }
        ;

fieldlist:
	  /*empty*/
	    {$$=fieldlist(null,null);}
	| fieldlist field
	    {$$=fieldlist($1,$2);}
	| fieldlist ';'
	    {$$=$1;}
	;

enumtype:
        ENUM name '{' enumlist '}'
	    {$$=enumtype($2,$4);}
        ;

enumlist:
	  /*empty*/
	    {$$=enumlist(null,null);}
	| enumlist optionstmt
	    {$$=enumlist($1,$2);}
	| enumlist enumfield
	    {$$=enumlist($1,$2);}
	| enumlist ';' {$$=$1;}
	;

enumfield:
        name '=' INTCONST
	    {if(($$=enumfield($1,$3))==null) {return YYABORT;}}
        ;

service:
        SERVICE name '{' servicecaselist '}'
	    {$$=service($2,$4);}
        ;

servicecaselist:
	  /*empty*/
	    {$$=servicecaselist(null,null);}
	| servicecaselist servicecase
	    {$$=servicecaselist($1,$2);}
	;

servicecase:
	  optionstmt {$$=$1;}
	| rpc    {$$=$1;}
	| ';'    {$$=null;}
	;

rpc:
        RPC name '(' usertype ')' RETURNS '(' usertype ')' ';'
	    {$$=rpc($2,$4,$8);}
        ;

messagebody:
        '{' messageelementlist '}'
	    {$$=$2;}
        ;

messageelementlist:
	  /*empty*/
	    {$$=messageelementlist(null,null);}
	| messageelementlist messageelement
	    {$$=messageelementlist($1,$2);}
	;

messageelement:
          field      {$$=$1;}
        | enumtype   {$$=$1;}
        | message    {$$=$1;}
        | extend     {$$=$1;}
        | extensions {$$=$1;}
        | optionstmt {$$=$1;}
        | ';'        {$$=null;}
        ;

// tag number must be 2^28-1 or lower
field:
	   group {$$=$1;}
	| cardinality type name '=' INTCONST  ';'
	    {$$=field($1,$2,$3,$5,null);}
	| cardinality type name '=' INTCONST '[' fieldoptionlist ']'  ';'
	    {$$=field($1,$2,$3,$5,$7);}
        ;

group:
	cardinality GROUP name '=' INTCONST  messagebody 
	    {$$=group($1,$3,$5,$6);}
	;

fieldoptionlist:
	  fieldoption
	    {$$=fieldoptionlist(null,$1);}
	| fieldoptionlist ',' fieldoption
	    {$$=fieldoptionlist($1,$3);}
	;

fieldoption:
          option
	    {$$=$1;}
	| DEFAULT '=' constant // treat like a special kind of option
	    {$$=option(AST.DEFAULTNAME,$3);}
        ;

extensions:
	EXTENSIONS extensionlist ';'
	    { $$=extensions($2);}
	;

extensionlist:
          extensionrange
	    {$$=extensionlist(null,$1);}
        | extensions ',' extensionrange
	    {$$=extensionlist($1,$3);}
        ;

extensionrange:
          INTCONST
	    {if(($$=extensionrange($1,null)) == null) return YYABORT;}
        | INTCONST TO INTCONST
	    {if(($$=extensionrange($1,$3)) == null) return YYABORT;}
        | INTCONST TO MAX
	    {if(($$=extensionrange($1,null)) == null) return YYABORT;}
        ;

cardinality:
	  REQUIRED {$$=$1;}
	| OPTIONAL {$$=$1;}
	| REPEATED {$$=$1;}
        ;

type:
	  DOUBLE   {$$=$1;}
	| FLOAT    {$$=$1;}
	| INT32    {$$=$1;}
	| INT64    {$$=$1;}
	| UINT32   {$$=$1;}
	| UINT64   {$$=$1;}
	| SINT32   {$$=$1;}
	| SINT64   {$$=$1;}
	| FIXED32  {$$=$1;}
	| FIXED64  {$$=$1;}
	| SFIXED32 {$$=$1;}
	| SFIXED64 {$$=$1;}
	| BOOL     {$$=$1;}
	| STRING   {$$=$1;}
	| BYTES    {$$=$1;}
	| usertype {$$=$1;}


usertype:
	symbolnotgroup {$$=$1;} /* user defined type */
	;

// Package names can have embedded '.''s
packagename:
	symbol {$$=$1;}
	;

// Path is a reference to some other object, so it can have embedded '.'s.
path:
	symbol {$$=$1;}
	;

// names do not allow embedded '.'s
name:
	symbol
	    {if(illegalname($1)) {return YYABORT;}; $$=$1;}
	;

// In constants, symbols will end up being treated as strings that just happen to be unquoted.
constant:
          symbol  {$$=$1;}
	| INTCONST  {$$=$1;}
	| FLOATCONST  {$$=$1;}
	| STRINGCONST  {$$=$1;}
	| TRUE {$$=$1;}
	| FALSE {$$=$1;}
	| POSNAN {$$=$1;}
	| POSINF {$$=$1;}
	| '-' POSNAN {$$ = "-nan";}
	| '-' POSINF {$$ = "-inf";}
        ;

// Some keywords are legal as symbols
symbol:
	  startsymbol NAME endsymbol {$$=$2;}
	;

symbolnotgroup:
	  startsymbol_nogroup NAME endsymbol {$$=$2;}
	;

startsymbol:           /*empty*/ {startsymbol(ProtobufLexer.IDstate.ANYID);} ;
startsymbol_nogroup:   /*empty*/ {startsymbol(ProtobufLexer.IDstate.NOGROUPID);} ;
/* Not used
startsymbol_nokeyword: {startsymbol(ProtobufLexer.IDstate.NOKEYWORDID);} ;
*/
endsymbol: /*empty*/ {endsymbol();}

// the following are excluded because they cause parser conflicts: "default:

