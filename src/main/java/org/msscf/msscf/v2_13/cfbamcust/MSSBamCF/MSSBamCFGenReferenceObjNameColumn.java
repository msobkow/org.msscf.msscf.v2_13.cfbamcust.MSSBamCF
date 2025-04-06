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

import java.util.Iterator;

import org.msscf.msscf.v2_13.cflib.CFLib.*;
import org.msscf.msscf.v2_13.cfcore.MssCF.*;
import org.msscf.msscf.v2_13.cfbam.CFBamObj.*;

public class MSSBamCFGenReferenceObjNameColumn
	extends MssCFGenReferenceObj
{
	private static final long serialVersionUID = 1L;

	public MSSBamCFGenReferenceObjNameColumn() {
		super();
	}

	public MSSBamCFGenReferenceObjNameColumn(
		MSSBamCFEngine argSchema,
		String toolset,
		String scopeDefClassName,
		String genDefClassName,
		String itemName)
	{
		super(argSchema, toolset, scopeDefClassName, genDefClassName, itemName, "Value");
	}

	public ICFLibAnyObj dereference( MssCFGenContext genContext) {
		ICFLibAnyObj genDef;
		final String S_ProcName = "expandBody";

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

		ICFBamTableObj curTable = null;

		if (genDef instanceof ICFBamTableObj) {
			curTable = (ICFBamTableObj)genDef;
		}
		else if (genDef instanceof ICFBamAtomObj) {
			ICFLibAnyObj atomScope = ((ICFBamAtomObj)genDef).getRequiredContainerScope();
			if ((atomScope != null) && (atomScope instanceof ICFBamTableObj))
			{
				curTable = (ICFBamTableObj)atomScope;
			}
		}
		else if (genDef instanceof ICFBamTableColObj) {
			curTable = (ICFBamTableObj)((ICFBamTableColObj)genDef).getRequiredContainerTable();
		}
		else if (genDef instanceof ICFBamIndexObj) {
			curTable = (ICFBamTableObj)((ICFBamIndexObj)genDef).getRequiredContainerTable();
		}
		else if (genDef instanceof ICFBamIndexColObj) {
			ICFBamIndexObj colIndex = ((ICFBamIndexColObj)genDef).getRequiredContainerIndex();
			if (colIndex == null) {
				throw new CFLibNullArgumentException( getClass(),
					S_ProcName,
					0,
					"IndexColDef.getRequiredContainerIndex()" );
			}
			curTable = (ICFBamTableObj)colIndex.getRequiredContainerTable();
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
					S_ProcName,
					"genContext.GenDef",
					genDef,
					"ICFBamTableObj, ICFBamAtomObj, ICFBamTableColObj, ICFBamIndexObj, ICFBamIndexColObj" );
		}

		if (curTable == null) {
			throw new CFLibRuntimeException( getClass(),
				S_ProcName,
				"Could not locate current table for genContext.getGenDef()<"
					+ genDef.getClass().getSimpleName() + ">");
		}

		ICFBamIndexObj lookupIndex = null;

		ICFBamTableObj startTable = curTable;

		while ((lookupIndex == null) && (curTable != null)) {
			lookupIndex = curTable.getOptionalLookupLookupIndex();
			if (lookupIndex == null) {
				ICFBamRelationObj superClassRelation = MSSBamCFTableObj.getSuperClassRelation(curTable);
				if (superClassRelation != null) {
					ICFBamTableObj superClassTable = superClassRelation.getRequiredLookupToTable();
					if (superClassTable == null) {
						throw new CFLibRuntimeException( getClass(),
							S_ProcName,
							"Could not resolve Superclass for TableDef "
								+ curTable.getObjFullName() );
					}
					curTable = superClassTable;
				}
				else {
					curTable = null;
				}
			}
		}

		ICFBamValueObj nameCol = null;

		if( lookupIndex != null ) {
			Iterator<ICFBamIndexColObj> iter = lookupIndex.getOptionalComponentsColumns().iterator();
			ICFBamIndexColObj last = null;
			while( iter.hasNext() ) {
				last = iter.next();
			}
			if( last == null ) {
				throw new CFLibRuntimeException( getClass(),
						S_ProcName,
						"Current TableDef " + startTable.getObjFullName() + " LookupIndex has no columns" );
			}
			nameCol = last.getRequiredLookupColumn();
		}

		if( nameCol == null ) {
			// Try to find a column named "Name" in the table
			curTable = startTable;
			Iterator<ICFBamValueObj> iter;
			ICFBamValueObj cur;
			while( ( nameCol == null ) && ( curTable != null ) ) {
				iter = curTable.getOptionalComponentsColumns().iterator();
				while( ( nameCol == null ) && iter.hasNext() ) {
					cur = iter.next();
					if( "Name".equalsIgnoreCase( cur.getRequiredName() ) ) {
						nameCol = cur;
					}
				}
				if( nameCol == null ) {
					ICFBamRelationObj superClassRelation = MSSBamCFTableObj.getSuperClassRelation(curTable);
					if (superClassRelation != null) {
						ICFBamTableObj superClassTable = superClassRelation.getRequiredLookupToTable();
						if (superClassTable == null) {
							throw new CFLibRuntimeException( getClass(),
								S_ProcName,
								"Could not resolve Superclass for TableDef "
									+ curTable.getObjFullName() );
						}
						curTable = superClassTable;
					}
					else {
						curTable = null;
					}
				}
			}
		}

		if( nameCol == null ) {
			// Use the last column of the primary key
			ICFBamIndexObj pkey = startTable.getOptionalLookupPrimaryIndex();
			if( pkey != null ) {
				Iterator<ICFBamIndexColObj> iter = pkey.getOptionalComponentsColumns().iterator();
				ICFBamIndexColObj indexcol = null;
				while( iter.hasNext() ) {
					indexcol = iter.next();
				}
				if( indexcol == null ) {
					throw new CFLibRuntimeException( getClass(),
						S_ProcName,
						"Current TableDef " + startTable.getObjFullName() + " does not specify a LookupIndex, have a Name column, or specify a PrimaryIndex" );
				}
				nameCol = indexcol.getRequiredLookupColumn();
			}
		}

		if (nameCol == null) {
			throw new CFLibRuntimeException( getClass(),
				S_ProcName,
				"Current TableDef " + startTable.getObjFullName() + " does not specify a LookupIndex, have a Name column, or specify a PrimaryIndex" );
		}

		return ( nameCol );
	}
}
