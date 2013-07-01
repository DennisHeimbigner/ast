
#define DFALTALLOC 1024

static int byteio_initialized = 0;

/* Should be exactly sizeof(uint64_t) characters long */
static const char BYTEIO_UIDSTRING[8] = "byteio  ";

static union byteio_uid_t {
char uidstring[8];
uint64_t uid;
} byteio_uid;


/**************************************************/
/* Byte IO */
/**************************************************/

/* Create a runtime readers and writers backed by a byte buffer */

/* Define the data kept in the stream field of Ast_runtime */
struct _ast_bytestream {
    uint64_t uid;
    bool_t extendible; // 0 => cannot extend
    size_t alloc;
    size_t pos;
    uint8_t* buffer;
    struct _ast_stack { /* Only used when reading */
        size_t maxpos;
        struct _ast_stack* stack;
    } stack;
};

static ast_err ast_byteio_read(Ast_runtime* rt,byte_t* data, const size_t len, size_t* red);
static ast_err ast_byteio_write(Ast_runtime* rt,const byte_t* data, const size_t);
static int ast_byteio_mark(Ast_runtime* rt, size_t count);
static int ast_byteio_unmark(Ast_runtime* rt);
static int ast_byteio_close(Ast_runtime* rt);

static struct Ast_io_ops byteops = {
ast_byteio_read,
ast_byteio_write,
ast_byteio_mark,
ast_byteio_unmark,
ast_byteio_close
};

/**************************************************/

static void
byteio_init(void)
{
    memcpy(byteio_uid.uidstring,BYTEIO_UIDSTRING,sizeof(BYTEIO_UIDSTRING));
    byteio_initialized = 1;    
}

/**************************************************/

static ast_err
ast_byteio_write(Ast_runtime* rt, const byte_t* data, const size_t len)
{
    ast_err status = AST_NOERR;
    Ast_io* io = rt->io;
    if(rt == NULL || io == NULL || io->mode != Ast_write)
	AERR(status,AST_EARG,done);
    if(io->id != byteio_uid.uid)
	AERR(status,AST_EARG,done);
    if(len == 0 || data == NULL) goto done;
    struct _ast_bytestream*  stream = (struct _ast_bytestream*)io->stream;
    while(stream->pos+len >= stream->alloc) {
	if(stream->extendible) {
	    char* newbuffer = NULL;
   	    size_t delta = DFALTALLOC;
	    if(stream->buffer == NULL) {
		stream->pos = 0;
	        stream->alloc += delta;		
		newbuffer = ast_alloc(rt,stream->alloc);
		if(newbuffer == NULL) AERR(status,AST_ENOMEM,done);
	    } else {
		stream->alloc += delta;
		newbuffer = ast_realloc(rt,stream->buffer,stream->alloc);
	    }
  	    if(newbuffer == NULL) AERR(status,AST_ENOMEM,done);
	} else AERR(status,AST_EIO,done); /* Cannot extend */
    } /*while*/
    memcpy(stream->buffer+stream->pos,(void*)data,len);
    stream->pos += len;

done:
    return ACATCH(status);
}

static ast_err
ast_byteio_read(Ast_runtime* rt, byte_t* data, size_t len, size_t* red)
{
    ast_err status = AST_NOERR;
    Ast_io* io = rt->io;
    struct _ast_bytestream*  stream = NULL;
    if(rt == NULL || io == NULL || io->mode != Ast_read)
	AERR(status,AST_EARG,done);
    if(io->id != byteio_uid.uid)
	AERR(status,AST_EARG,done);
    if(len == 0 || data == NULL)
	goto done;
    stream = (struct _ast_bytestream*)io->stream;    
    if(stream->pos+len > stream->stack.maxpos) {
	status = AST_EOF;
	len = 0;
	stream->pos = stream->stack.maxpos;
    } else if(stream->pos+len > stream->alloc) {
	status = AST_EOF;
	len = 0;
	stream->pos = stream->alloc;
    } else {
        memcpy((void*)data,stream->buffer+stream->pos,len);
        stream->pos += len;
    }
    if(red) *red = len;
done:
    return ACATCH(status);
}

/* limit reads to next n bytes */
static ast_err
ast_byteio_mark(Ast_runtime* rt, size_t count)
{
    ast_err status = AST_NOERR;
    Ast_io* io = rt->io;
    struct _ast_bytestream* stream = NULL;
    struct _ast_stack* node = NULL;
    if(rt == NULL || io == NULL || io->mode != Ast_read)
	AERR(status,AST_EARG,done);
    if(io->id != byteio_uid.uid)
	AERR(status,AST_EARG,done);
    stream = (struct _ast_bytestream*)io->stream;
    node = ast_alloc(rt,sizeof(struct _ast_stack));
    if(node == NULL)
	AERR(status,AST_ENOMEM,done);
    *node = stream->stack;
    stream->stack.stack = node;
    stream->stack.maxpos = stream->pos+count;
    /* stream maxpos must be <= alloc */
    if(stream->stack.maxpos > stream->alloc)
	PANIC("stream stack overflow");	
/*        stream->stack.maxpos = stream->alloc; */
done:
    return ACATCH(status);
}

/* Pop out of the current mark */
static ast_err
ast_byteio_unmark(Ast_runtime* rt)
{
    ast_err status = AST_NOERR;
    Ast_io* io = rt->io;
    struct _ast_bytestream* stream = NULL;
    struct _ast_stack* node = NULL;
    if(rt == NULL || io == NULL || io->mode != Ast_read)
	AERR(status,AST_EARG,done);
    if(io->id != byteio_uid.uid)
	AERR(status,AST_EARG,done);
    stream = (struct _ast_bytestream*)io->stream;
    node = stream->stack.stack;
    stream->stack.maxpos = node->maxpos;
    if(stream->stack.maxpos > stream->alloc)
        PANIC("stream stack overflow");
    stream->stack.stack = node->stack;
    ast_free(rt,node);
done:
    return ACATCH(status);
}

static ast_err
ast_byteio_close(Ast_runtime* rt)
{
    ast_err status = AST_NOERR;
    Ast_io* io = rt->io;
    struct _ast_bytestream* stream = NULL;
    if(rt == NULL) return AST_EARG; 
    if(io == NULL) return AST_NOERR; /* don't care */
    if(io->id != byteio_uid.uid)
	AERR(status,AST_EARG,done);
    stream = (struct _ast_bytestream*)io->stream;
    if(stream->extendible && stream->buffer != NULL)
	ast_free(rt,stream->buffer);
    struct _ast_stack* curr =  stream->stack.stack;
    while(curr != NULL) {
        struct _ast_stack* next =  stream->stack.stack;
        ast_free(rt,curr);
	curr = next;	
    }
    ast_free(rt,io);
done:
    return ACATCH(status);
}

/* Extract the current position from a byteio runtime */
static ast_err
ast_byteio_count(Ast_runtime* rt, size_t* countp)
{
    ast_err status = AST_NOERR;
    Ast_io* io = rt->io;
    struct _ast_bytestream* stream = NULL;
    struct _ast_stack* node = NULL;
    if(rt == NULL || io == NULL || io->mode != Ast_read)
	AERR(status,AST_EARG,done);
    if(io->id != byteio_uid.uid)
	AERR(status,AST_EARG,done);
    stream = (struct _ast_bytestream*)io->stream;
    if(countp) *countp = stream->pos;
done:
    return ACATCH(status);
}

/* Extract the buffer from a byteio runtime */
static ast_err
ast_byteio_content(Ast_runtime* rt, bytes_t* result)
{
    ast_err status = AST_NOERR;
    Ast_io* io = rt->io;
    struct _ast_bytestream* stream = NULL;
    struct _ast_stack* node = NULL;
    bytes_t content;
    if(result == NULL)
	goto done;
    if(rt == NULL || io == NULL || io->mode != Ast_read)
	AERR(status,AST_EARG,done);
    if(io->id != byteio_uid.uid)
	AERR(status,AST_EARG,done);
    stream = (struct _ast_bytestream*)io->stream;
    content.nbytes = stream->pos;
    content.bytes = stream->buffer;
    /* prevent later writes */
    stream->alloc = 0;
    stream->pos = 0;
    stream->buffer = NULL;
    if(result) *result = content;
done:
    return ACATCH(status);
}

static int
ast_byteio_new(Ast_runtime* rt, Ast_iomode mode, void* buf, size_t len, Ast_io** iop)
{
    ast_err status = AST_NOERR;
    Ast_io* io = NULL;
    struct _ast_bytestream*  stream = NULL;

    if(!byteio_initialized) byteio_initialize();

    if(iop == NULL)
	AERR(status,AST_EARG,done);
    switch(mode) {
    case Ast_read:
	if(buf == NULL || len == 0)
	    AERR(status,AST_EARG,done);
	break;
    default:
	AERR(status,AST_EARG,done);
    } 

    io = ast_alloc(rt,sizeof(Ast_io));
    if(io == NULL) AERR(status,AST_ENOMEM,done);

    stream = ast_alloc(rt,sizeof(struct _ast_bytestream));
    if(stream == NULL) AERR(status,AST_ENOMEM,done);

    io->id = byteio_uid.uid;
    io->mode = mode;
    io->stream = (void*)stream;
    io->ops = &byteops;
    io->mode = mode;

    if(mode == Ast_read) {
        if(buf == NULL || len == 0)
	    AERR(status,AST_EIOMODE,done);
	stream->extendible = 0;
	stream->alloc = len;
	stream->pos = 0;
	stream->buffer = buf;
	stream->stack.maxpos = stream->alloc;
    } else if(mode == Ast_write) {
	if(buf == NULL) {
	    stream->extendible = 1;
	    /* Use default initial length */
	    len = DFALTALLOC;
	    buf = ast_alloc(rt,len);
	    if(buf == NULL) AERR(status,AST_ENOMEM,done);
	} else {
	    stream->extendible = 0;
	}
	stream->alloc = len;
	stream->pos = 0;
	stream->buffer = buf;
    }
    if(iop) *iop = io;

done:
    if(status != AST_NOERR) {
        if(io != NULL)
	    ast_free(rt,io);
        if(stream != NULL)
	    ast_free(rt,stream);
        if(stream->buffer != buf && mode == Ast_write)
	    ast_free(rt,stream->buffer);
    }
    return ACATCH(status);
}

