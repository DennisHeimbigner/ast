/*
 * Copyright (c) 1998 - 2010. University Corporation for Atmospheric Research/Unidata
 * Portions of this software were developed by the Unidata Program at the
 * University Corporation for Atmospheric Research.
 *
 * Access and use of this software shall impose the following obligations
 * and understandings on the user. The user is granted the right, without
 * any fee or cost, to use, copy, modify, alter, enhance and distribute
 * this software, and any derivative works thereof, and its supporting
 * documentation for any purpose whatsoever, provided that this entire
 * notice appears in all copies of the software, derivative works and
 * supporting documentation.  Further, UCAR requests that the user credit
 * UCAR/Unidata in any publications that result from the use of this
 * software or in any product that includes this software. The names UCAR
 * and/or Unidata, however, may not be used in any advertising or publicity
 * to endorse or promote any products or commercial entity unless specific
 * written permission is obtained from UCAR/Unidata. The user also
 * understands that UCAR/Unidata is not obligated to provide the user with
 * any support, consulting, training or assistance of any kind with regard
 * to the use, operation and performance of this software nor to provide
 * the user with any updates, revisions, new versions or "bug fixes."
 *
 * THIS SOFTWARE IS PROVIDED BY UCAR/UNIDATA "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL UCAR/UNIDATA BE LIABLE FOR ANY SPECIAL,
 * INDIRECT OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING
 * FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT,
 * NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION
 * WITH THE ACCESS, USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package unidata.protobuf.compiler;

import java.util.*;

public class ASTFactoryDefault implements ASTFactory
{
    public AST.Root newRoot(String name)
	{return new AST.Root(name);}
    public AST.File newFile(String name)
	{return new AST.File(name);}
    public AST.Package newPackage(String name)
	{return new AST.Package(name);}
    public AST.Enum newEnum(String name)
	{return new AST.Enum(name);}
    public AST.EnumValue newEnumValue(String name, int value)
	{return new AST.EnumValue(name, value);}
    public AST.Extend newExtend(String name, String msgname)
	{return new AST.Extend(name,msgname);}
    public AST.Extensions newExtensions(String name)
	{return new AST.Extensions(name);}
    public AST.Field newField(String name, AST.Cardinality cardinality, String fieldtype, int id)
	{return new AST.Field(name, cardinality, fieldtype, id);}
    public AST.Group newGroup(String name, AST.Cardinality cardinality, int id)
	{return new AST.Group(name, cardinality, id);}
    public AST.Message newMessage(String name)
	{return new AST.Message(name);}
    public AST.Option newOption(String name, String value)
	{return new AST.Option(name, value);}
    public AST.Rpc newRpc(String name, String argtype, String returntype)
	{return new AST.Rpc(name, argtype, returntype);}
    public AST.Service newService(String name)
	{return new AST.Service(name);}
    public AST.PrimitiveType newPrimitiveType(AST.PrimitiveSort sort)
	{return new AST.PrimitiveType(sort);}
}
