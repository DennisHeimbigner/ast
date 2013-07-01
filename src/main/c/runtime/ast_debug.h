#ifndef AST_DEBUG_H
#define AST_DEBUG_H

#define ASTDEBUG

/* Provide an error catcher */
#ifdef ASTDEBUG
extern int ast_catch(int);
extern int ast_breakpoint(int err);
#define ACATCH(status) ((status)?ast_catch(status):(status))
#define ATHROW(status,go) {ast_catch(status);goto go;}
#define AERR(status,err,go) {status=err;ast_catch(status);goto go;}
#else
#define ACATCH(status) (status)
#define ATHROW(status,go) {goto go;}
#define AERR(status,err,go) {status=err; goto go;}
#endif

/* Always defined */
#define APANIC(msg) {assert(ast_panic(msg));}
#define ASSERT(expr) {if(!(expr)) {APANIC(#expr);} else {}}
extern int ast_panic(const char* msg);

#endif /*AST_DEBUG_H*/
