/* Copyright 2009, UCAR/Unidata and OPeNDAP, Inc.
   See the COPYRIGHT file for more information. */

/*
 * Bison 2.4.2 grammar file for Google Protocol Buffers .proto files
 */

/* There appears to be a flaw in the current locations implementation */
%locations
%error-verbose
%name-prefix "Protobuf"

%define public
%define package "unidata.protobuf.ast.compiler"
%define extends "ProtobufActions"
%define throws "IOException"
%define lex_throws "IOException"

%code imports {
import java.io.*;
import unidata.protobuf.ast.compiler.AST.Position;
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
}

%token IMPORT PACKAGE OPTION MESSAGE EXTEND
%token ENUM SERVICE RPC RETURNS
%token DEFAULT TO MAX REQUIRED OPTIONAL REPEATED
%token DOUBLE FLOAT INT32 INT64 UINT32 UINT64
%token SINT32 SINT64 FIXED32 FIXED64 SFIXED32 SFIXED64
%token BOOL STRING BYTES
%token GOOGLEOPTION
%token ENDFILE

%token IDENTIFIER INTCONST FLOATCONST STRINGCONST TRUE FALSE


%start protobuffile

%%

protobuffile:
	packagedecl importlist decllist ENDFILE
	    {setLocation(yyloc);protobuffile($1,$2,$3);}

packagedecl:
	  /*empty*/
	    {setLocation(yyloc);$$=packagedecl(null);}
	| PACKAGE identifier ';'
	    {setLocation(yyloc);$$=packagedecl($2);}
	;

importlist:
	  /*empty*/
	    {setLocation(yyloc);$$=importlist(null,null);}
	| importlist importstmt
	    {setLocation(yyloc);$$=importlist($1,$2);}
	;	

importstmt:
        IMPORT STRING ';' protobuffile
	    {setLocation(yyloc);importstmt($2,$4);}
        ;

decllist:
	  /*empty*/
	    {setLocation(yyloc);$$=decllist(null,null);}
	| decllist decl
	    {setLocation(yyloc);$$=decllist($1,$2);}
	;	

decl:
          message  {$$=$1;}
	| extend   {$$=$1;}
	| enumtype {$$=$1;}
	| optionstmt   {$$=$1;}
	| service  {$$=$1;}
	| ';'      {$$=null;}
        ;

optionstmt:
          OPTION option ';'
   	    {$$=$2;}
        | OPTION '(' option ')' ';'
	    {setLocation(yyloc);$$=useroption($3);}
        ;

option:
	identifier '=' constant
   	    {setLocation(yyloc);$$=option($1,$3);}
        ;

message:
        MESSAGE identifier messagebody
	    {setLocation(yyloc);$$=message($2,$3);}
        ;

extend:
          EXTEND path '{' fieldlist '}'
	    {setLocation(yyloc);$$=extend($2,$4);}
        | EXTEND GOOGLEOPTION  '{' fieldlist '}'
	    {$$=null; /* ignore */ }
        ;

fieldlist:
	  /*empty*/
	    {setLocation(yyloc);$$=fieldlist(null,null);}
	| fieldlist field
	    {setLocation(yyloc);$$=fieldlist($1,$2);}
	| fieldlist ';'
	    {$$=$1;}
	;

enumtype:
        ENUM identifier '{' enumlist '}'
	    {setLocation(yyloc);$$=enumtype($2,$4);}
        ;

enumlist:
	  /*empty*/
	    {setLocation(yyloc);$$=enumlist(null,null);}
	| enumlist optionstmt
	    {setLocation(yyloc);$$=enumlist($1,$2);}
	| enumlist enumfield
	    {setLocation(yyloc);$$=enumlist($1,$2);}
	| enumlist ';' {$$=$1;}
	;

enumfield:
        identifier '=' INTCONST
	    {setLocation(yyloc);if(($$=enumfield($1,$3))==null) {return YYABORT;}}
        ;

service:
        SERVICE identifier '{' servicecaselist '}'
	    {setLocation(yyloc);$$=service($2,$4);}
        ;

servicecaselist:
	  /*empty*/
	    {setLocation(yyloc);$$=servicecaselist(null,null);}
	| servicecaselist servicecase
	    {setLocation(yyloc);$$=servicecaselist($1,$2);}
	;

servicecase:
	  optionstmt {$$=$1;}
	| rpc    {$$=$1;}
	| ';'    {$$=null;}
	;

rpc:
        RPC identifier '(' usertype ')' RETURNS '(' usertype ')' ';'
	    {setLocation(yyloc);$$=rpc($2,$4,$8);}
        ;

messagebody:
        '{' messageelementlist '}'
	    {$$=$2;}
        ;

messageelementlist:
	  /*empty*/
	    {setLocation(yyloc);$$=messageelementlist(null,null);}
	| messageelementlist messageelement
	    {setLocation(yyloc);$$=messageelementlist($1,$2);}
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
	  cardinality type identifier '=' INTCONST  ';'
	    {setLocation(yyloc);$$=field($1,$2,$3,$5,null);}
	| cardinality type identifier '=' INTCONST '[' fieldoptionlist ']'  ';'
	    {setLocation(yyloc);$$=field($1,$2,$3,$5,$7);}
        ;

fieldoptionlist:
	  fieldoption
	    {setLocation(yyloc);$$=fieldoptionlist(null,$1);}
	| fieldoptionlist ',' fieldoption
	    {setLocation(yyloc);$$=fieldoptionlist($1,$3);}
	;

fieldoption:
          option
	    {$$=$1;}
	| DEFAULT '=' constant // treat like a special kind of option
	    {setLocation(yyloc);$$=option(AST.DEFAULTNAME,$3);}
        ;

extensions:
	extensionlist ';'
	    {$$=$1;}
	;

extensionlist:
          extensionrange
	    {setLocation(yyloc);$$=extensionlist(null,$1);}
        | extensions ',' extensionrange
	    {setLocation(yyloc);$$=extensionlist($1,$3);}
        ;

extensionrange:
          INTCONST
	    {setLocation(yyloc);if(($$=extensionrange($1,null)) == null) return YYABORT;}
        | INTCONST TO INTCONST
	    {setLocation(yyloc);if(($$=extensionrange($1,$3)) == null) return YYABORT;}
        | INTCONST TO MAX
	    {setLocation(yyloc);if(($$=extensionrange($1,null)) == null) return YYABORT;}
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
	;

// leading dot for identifiers means they're fully qualified
// Kenton: userType ::= "."? ident ( "." ident )*
path:
	identifier {$$=$1;}
	;

usertype: 
	identifier {$$=$1;}
	;

constant:
          identifier  {$$=$1;}
	| INTCONST    {$$=$1;}
	| FLOATCONST  {$$=$1;}
	| STRINGCONST {$$=$1;}
	| TRUE        {$$=$1;}
	| FALSE       {$$=$1;}
        ;

// Check for embedded "."s
identifier:
	IDENTIFIER
	    {if(($$=identifier($1))==null) {return YYABORT;}}
	;
