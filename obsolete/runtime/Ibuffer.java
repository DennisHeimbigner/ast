/*********************************************************************
 *   Copyright 2010, UCAR/Unidata
 *   See netcdf/COPYRIGHT file for copying and redistribution conditions.
 *   $Id$
 *   $Header$
 *********************************************************************/

package unidata.ast.runtime;

// Wrapper class to provide expandable byte valuebuffer

class Ibuffer
{
    static final int INITSIZE = 4;

    int[] buffer = null;
    int pos;

    public Ibuffer() {buffer = new int[INITSIZE]; pos = 0;}

    public int[] getContent()
    {
	int[] newbuf = new int[pos];
        if(pos > 0)
	    System.arraycopy(buffer,0,newbuf,0,pos);
	return newbuf;
    }

    public void
    add(int i)
    {
	if((buffer.length - pos) <= 0) {
	    int[] newbuf = new int[pos*2];
	    System.arraycopy(buffer,0,newbuf,0,pos);
            buffer = newbuf;
        }
	buffer[pos++] = i;	
    }

}

