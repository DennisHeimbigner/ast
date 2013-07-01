
#ifdef ASTDEBUG
static int
ast_catch(int code)
{
    return code;
}

static int
ast_breakpoint(int err)
{
    return 1;
}
#endif /*ASTDEBUG*/

static int
ast_panic(const char* msg)
{
    fprintf(stderr,msg);
    return 0;
}
