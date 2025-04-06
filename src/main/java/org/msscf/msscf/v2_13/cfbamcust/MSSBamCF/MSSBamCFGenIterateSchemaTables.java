/*
 *	MSS Code Factory CFBam 2.13 CustMSSBamCF
 *
 *	Copyright (c) 2020 Mark Stephen Sobkow
 *	
 *	This file is part of MSS Code Factory.
 *	
 *	MSS Code Factory is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *	
 *	MSS Code Factory is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *	
 *	You should have received a copy of the GNU General Public License
 *	along with MSS Code Factory.  If not, see https://www.gnu.org/licenses/.
 *	
 *	Donations to support MSS Code Factory can be made at
 *	https://www.paypal.com/paypalme2/MarkSobkow
 *	
 *	Contact Mark Stephen Sobkow at mark.sobkow@gmail.com for commercial licensing.
 */

package org.msscf.msscf.v2_13.cfbamcust.MSSBamCF;

import java.util.*;

import org.msscf.msscf.v2_13.cflib.CFLib.*;
import org.msscf.msscf.v2_13.cfcore.MssCF.*;
import org.msscf.msscf.v2_13.cfbam.CFBam.*;
import org.msscf.msscf.v2_13.cfbam.CFBamObj.*;

public class MSSBamCFGenIterateSchemaTables
	extends MssCFGenIteratorObj
{
	private static final long serialVersionUID = 1L;

	public MSSBamCFGenIterateSchemaTables() {
		super();
	}

	public MSSBamCFGenIterateSchemaTables(
		MSSBamCFEngine argSchema,
		String toolset,
		String scopeDefClassName,
		String genDefClassName,
		String itemName)
	{
		super( argSchema, toolset, scopeDefClassName, genDefClassName, itemName, "TableDef");
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
		ICFBamTableFactory tableFactory = ((ICFBamSchema)schemaDefObj.getSchema().getBackingStore()).getFactoryTable();

		List<ICFBamTableObj> optionalChildrenTables = schemaDefObj.getOptionalComponentsTables();
		int len = optionalChildrenTables.size();
		ICFBamTableObj[] arr = new ICFBamTableObj[len];
		Iterator<ICFBamTableObj> iter = optionalChildrenTables.iterator();
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

		Comparator<ICFBamTableObj> cmp = new Comparator<ICFBamTableObj>() {
			public int compare( ICFBamTableObj lhs, ICFBamTableObj rhs ) {
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
					int ret = lhsName.compareTo( rhsName );
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
