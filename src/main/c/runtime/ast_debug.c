#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#include "config.h"
#include "ast_internal.h"

#ifdef ASTDEBUG
int
ast_catch(int code)
{
    return code;
}

int
ast_breakpoint(int err)
{
    return 1;
}
#endif /*ASTDEBUG*/

int
ast_panic(const char* msg)
{
    fprintf(stderr,msg);
    return 0;
}
