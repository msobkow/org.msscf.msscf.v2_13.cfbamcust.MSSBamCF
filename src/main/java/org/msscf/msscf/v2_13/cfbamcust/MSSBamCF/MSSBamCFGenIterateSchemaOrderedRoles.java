/*
 *	MSS Code Factory CFBam 2.13 CustMSSBamCF
 *
 *	Copyright (c) 2016-2026 Mark Stephen Sobkow
 *	
 *	Mark's Code Fractal CFBam 2.13 Business Application Model
 *	
 *	Copyright 2016-2026 Mark Stephen Sobkow
 *	
 *	This file is part of Mark's Code Fractal CFBam.
 *	
 *	Mark's Code Fractal CFBam is available under dual commercial license from
 *	Mark Stephen Sobkow, or under the terms of the GNU General Public License,
 *	Version 3 or later with classpath and static linking exceptions.
 *	
 *	As a special exception, Mark Sobkow gives you permission to link this library
 *	with independent modules to produce an executable, provided that none of them
 *	conflict with the intent of the GPLv3; that is, you are not allowed to invoke
 *	the methods of this library from non-GPLv3-compatibly licensed code. You may not
 *	implement an LPGLv3 "wedge" to try to bypass this restriction. That said, code which
 *	does not rely on this library is free to specify whatever license its authors decide
 *	to use. Mark Sobkow specifically rejects the infectious nature of the GPLv3, and
 *	considers the mere act of including GPLv3 modules in an executable to be perfectly
 *	reasonable given tools like modern Java's single-jar deployment options.
 *	
 *	Mark's Code Fractal CFBam is free software: you can redistribute it and/or
 *	modify it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *	
 *	Mark's Code Fractal CFBam is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *	
 *	You should have received a copy of the GNU General Public License
 *	along with Mark's Code Fractal CFBam.  If not, see <https://www.gnu.org/licenses/>.
 *	
 *	If you wish to modify and use this code without publishing your changes,
 *	or integrate it with proprietary code, please contact Mark Stephen Sobkow
 *	for a commercial license at mark.sobkow@gmail.com
 */

package org.msscf.msscf.v2_13.cfbamcust.MSSBamCF;

import java.util.*;

import org.msscf.msscf.v2_13.cflib.CFLib.*;
import org.msscf.msscf.v2_13.cfcore.MssCF.*;
import org.msscf.msscf.v2_13.cfbam.CFBam.*;
import org.msscf.msscf.v2_13.cfbam.CFBamObj.*;

public class MSSBamCFGenIterateSchemaOrderedRoles
	extends MssCFGenIteratorObj
{
	private static final long serialVersionUID = 1L;

	public MSSBamCFGenIterateSchemaOrderedRoles() {
		super();
	}

	public MSSBamCFGenIterateSchemaOrderedRoles(
		MSSBamCFEngine argSchema,
		String toolset,
		String scopeDefClassName,
		String genDefClassName,
		String itemName)
	{
		super( argSchema, toolset, scopeDefClassName, genDefClassName, itemName, "SchemaRole");
	}

	public ListIterator<ICFLibAnyObj> enumerateDetails( MssCFGenContext genContext)
	{
		ICFLibAnyObj genDef;
		final String S_ProcName = "enumerateDetails";

		if (genContext == null) {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				1,
				"genContext" );
		}

		genDef = genContext.getGenDef();
		if (genDef == null) {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				0,
				"genContext.GenDef" );
		}

		if (!(genDef instanceof ICFBamSchemaDefObj)) {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"genContext.GenDef",
				genDef,
				"ICFBamSchemaDefObj" );
		}

		ICFBamSchemaDefObj schemaDefObj = (ICFBamSchemaDefObj)genDef;
		ICFBamSchemaRoleFactory schemaRoleFactory = ((ICFBamSchema)schemaDefObj.getSchema().getBackingStore()).getFactorySchemaRole();

		List<ICFBamSchemaRoleObj> optionalChildrenSchemaRoles = schemaDefObj.getOptionalComponentsRoles();
		int len = optionalChildrenSchemaRoles.size();
		ICFBamSchemaRoleObj[] arr = new ICFBamSchemaRoleObj[len];
		Iterator<ICFBamSchemaRoleObj> iter = optionalChildrenSchemaRoles.iterator();
		int idx = 0;
		while( ( idx < len ) && ( iter.hasNext() ) ) {
			arr[idx++] = iter.next();
		}
		if( idx < len ) {
			throw new CFLibArgumentUnderflowException( getClass(),
				S_ProcName,
				0,
				"idx",
				idx,
				len );
		}
		if( iter.hasNext() ) {
			throw new CFLibArgumentOverflowException( getClass(),
				S_ProcName,
				0,
				"idx",
				idx,
				len );
		}

		Comparator<ICFBamSchemaRoleObj> cmp = new Comparator<ICFBamSchemaRoleObj>() {
			public int compare( ICFBamSchemaRoleObj lhs, ICFBamSchemaRoleObj rhs ) {
				if( lhs == null ) {
					if( rhs == null ) {
						return( 0 );
					}
					else {
						return( -1 );
					}
				}
				else if( rhs == null ) {
					return( 1 );

				}
				else {
					String lhsName = lhs.getRequiredName();
					String rhsName = rhs.getRequiredName();
					String lhsMembershipString = lhs.getRequiredMembershipString();
					String rhsMembershipString = rhs.getRequiredMembershipString();
					boolean rhsEnablesLhs = false;
					String lhsMembers[] = lhsMembershipString.split(" ");
					String rhsMembers[] = rhsMembershipString.split(" ");
					boolean lhsEnablesRhs = false;
					for (String v: lhsMembers) {
						if (rhsName.equals(v)) {
							lhsEnablesRhs = true;
							break;
						}
					}
					for (String v: rhsMembers) {
						if (lhsName.equals(v)) {
							rhsEnablesLhs = true;
							break;
						}
					}
					int ret;
					if (lhsEnablesRhs) {
						if (rhsEnablesLhs) {
							ret = lhsName.compareTo(rhsName);
						}
						else {
							ret = 1;
						}
					}
					else {
						if (rhsEnablesLhs) {
							ret = -1;
						}
						else {
							ret = lhsName.compareTo(rhsName);
						}
					}
					return( ret );
				}
			}
		};

		Arrays.sort( arr, cmp );

		List<ICFLibAnyObj> list = new LinkedList<ICFLibAnyObj>();
		for( idx = 0; idx < len; idx ++ ) {
			list.add( arr[idx] );
		}

		return (list.listIterator());
	}
}
