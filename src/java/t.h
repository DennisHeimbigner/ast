/* Generated by the protocol buffer compiler.  DO NOT EDIT! */

#ifndef _T_
#define _T_

#include <edf.h>


// ===================================================================


// ===================================================================

typedef struct Message1 Message1;

// ===================================================================


struct  Message1
{
    int field1;
    struct {
        bool_t defined;
        unsigned long long value[1];
    } field2;
    struct {
        size_t count;
        double* values;
    } field3;
};


// ===================================================================

EXTERNC int edf_Message1_encode(EDF* edf, Message1* message1);
EXTERNC int edf_Message1_decode(EDF* edf, Message1** ref_message1);
EXTERNC int edf_Message1_free(EDF* edf, Message1* message1);

// ===================================================================


// ===================================================================




#endif /* _T_ */