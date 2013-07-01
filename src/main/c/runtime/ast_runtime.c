/* Define a null value for bytes_t */
static bytes_t bytes_t_null = {0,NULL};

/**************************************************/
/* Misc data */
static int initialized = 0;

/* Known encoders */
static Ast_encoding* Ast_encoder_tables[Encoder_count];

/* All created runtime instances */
static Ast_runtime* runtimelist;

/* Define a standard default for resources */
static void* dfalt_alloc(Ast_runtime* rt, const size_t len)
    {return (len==0?NULL:calloc(1,len));}
static void* dfalt_realloc(Ast_runtime* rt, void* oldmemory, const size_t len)
    {return (len==0?NULL:realloc(oldmemory,len));}
static void  dfalt_free(Ast_runtime* rt, void* mem)
    {if(mem != NULL) free(mem);}

static struct Ast_resource_ops dfaltresourceops = {dfalt_alloc, dfalt_realloc, dfalt_free};
static Ast_resources dfaltresources = {NULL, &dfaltresourceops};

/**************************************************/
/* System Initialization */

static ast_err
ast_initialize(void)
{
    if(!initialized) {
	runtimelist = NULL;
	Ast_encoder_tables[Encoder_protobuf] = protobuf_encoding;
	Ast_encoder_tables[Encoder_xdr] = xdr_encoding;
        initialized = 1;
    }
    return AST_NOERR;
}

static ast_err
ast_finalize(void)
{
    if(initialized) {
	Ast_runtime* p = runtimelist;
	while(p != NULL) {
	    Ast_runtime* q = p;
	    p = p->next;	   	    
	    ast_reclaim(q);
	}
	runtimelist = NULL;
	initialized = 0;
    } 
    return AST_NOERR;
}

/**************************************************/
/* Constructor */

ast_err
Ast_runtime_new(Ast_resources* resources, Ast_runtime** rtp)
{
    if(resources == NULL) resources = &dfaltresources;
    /* We have a cyle here because alloc expects AST_runtime* as first argument */
    Ast_runtime tmp;
    memset(&tmp,0,sizeof(Ast_runtime));
    tmp.resources = resources;
    Ast_runtime* rt = (Ast_runtime*)ast_alloc(&tmp,sizeof(Ast_runtime));
    if(rt == NULL) return AST_ENOMEM;
    *rt = tmp; /* alloc may have set the data part */
    rt->next = runtimelist;
    runtimelist = rt;
    if(rtp) *rtp = rt;
    return AST_NOERR;
}

/* Destructor */
static ast_err
ast_reclaim(Ast_runtime* rt)
{
    Ast_runtime* p = runtimelist;
    while(p != NULL) {
	if(p->next == rt) {
	    p->next = rt->next;
	    break;
	}
    }
    ast_free(rt,rt);    
    return AST_NOERR;
}

/**************************************************/
/* get/set */

ast_err
ast_setio(Ast_runtime* rt, Ast_io* io)
{
    if(rt != NULL)
	rt->io = io;
    return AST_NOERR;
}

ast_err
ast_setencoding(Ast_runtime* rt, Ast_encoder encoder)
{
    if(rt != NULL) {
	if(((int)encoder) < 0 || ((int)encoder) >= ((int)Encoder_count))
	    return AST_EENCODER;
	rt->encoder = Ast_encoder_tables[encoder];
    }
    return AST_NOERR;
}

/**************************************************/
/* wrappers for the resources table */

void*
ast_alloc(Ast_runtime* rt, const size_t len)
{
    return rt->resources->ops->alloc(rt, len);
}

void*
ast_realloc(Ast_runtime* rt, void* old, const size_t len)
{
    return rt->resources->ops->realloc(rt, old, len);
}

void
ast_free(Ast_runtime* rt, void* mem)
{
    return rt->resources->ops->free(rt, mem);
}

/**************************************************/
/* wrappers for io */

ast_err
ast_read(Ast_runtime* rt, byte_t* buf, const size_t len, size_t* lenp)
{
    return rt->io->ops->read(rt, buf, len, lenp);
}

ast_err
ast_write(Ast_runtime* rt, const byte_t* buf, const size_t len)
{
    return rt->io->ops->write(rt, buf, len);
}

ast_err
ast_mark(Ast_runtime* rt,const size_t avail)
{
    return rt->io->ops->mark(rt,avail);
}

ast_err
ast_unmark(Ast_runtime* rt)
{
    return rt->io->ops->unmark(rt);
}

/**************************************************/
/* wrappers for the dispatch table */

ast_err
ast_skip_field(Ast_runtime* rt, const int wiretype, const int fieldno)
{
    return rt->encoder->skip_field(rt, wiretype, fieldno);
}

size_t
ast_get_size(Ast_runtime* rt, const Ast_sort sort, const void* valp)
{
    return rt->encoder->get_size(rt, sort, valp);
}

size_t
ast_get_size_packed(Ast_runtime* rt, const Ast_sort sort, const void* valp)
{
    return rt->encoder->get_size_packed(rt, sort, valp);
}

size_t
ast_get_message_size(Ast_runtime* rt, const size_t size)
{
    return rt->encoder->get_message_size(rt, size);
}

size_t
ast_get_tag_size(Ast_runtime* rt, const Ast_sort sort, const int fieldno)
{
    return rt->encoder->get_tag_size(rt, sort, fieldno);
}


ast_err
ast_write_tag(Ast_runtime* rt, const Ast_sort sort, const int fieldno)
{
    return rt->encoder->write_tag(rt, sort, fieldno);
}

bool_t
ast_read_tag(Ast_runtime* rt, int* wiretype, int* fieldno)
{
    return rt->encoder->read_tag(rt, wiretype, fieldno);
}


ast_err
ast_write_size(Ast_runtime* rt, const size_t size)
{
    return rt->encoder->write_size(rt, size);
}

size_t
ast_read_size(Ast_runtime* rt)
{
    return rt->encoder->read_size(rt);
}

int
ast_write_primitive(Ast_runtime* rt, const Ast_sort sort, const void* valp)
{
    return rt->encoder->write_primitive(rt, sort, valp);
}

int
ast_write_primitive_packed(Ast_runtime* rt, const Ast_sort sort, const void* valp)
{
    return rt->encoder->write_primitive_packed(rt, sort, valp);
}

int
ast_read_primitive(Ast_runtime* rt, const Ast_sort sort, void* valp)
{
    return rt->encoder->read_primitive(rt, sort, valp);
}

int
ast_read_primitive_packed(Ast_runtime* rt, const Ast_sort sort, void* valp)
{
    return rt->encoder->read_primitive_packed(rt, sort, valp);
}

int
ast_repeat_append(Ast_runtime* rt, const Ast_sort sort, const void* newval, void* list)
{
    return rt->encoder->repeat_append(rt, sort, newval, list);
}

int
ast_reclaim_string(Ast_runtime* rt, char* value)
{
    return rt->encoder->reclaim_string(rt, value);
}

int
ast_reclaim_bytes(Ast_runtime* rt, bytes_t* value)
{
    return rt->encoder->reclaim_bytes(rt, value);
}

