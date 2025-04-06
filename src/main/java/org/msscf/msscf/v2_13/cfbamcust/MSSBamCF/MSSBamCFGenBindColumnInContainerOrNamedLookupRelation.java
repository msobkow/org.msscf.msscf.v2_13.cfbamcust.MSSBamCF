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
import java.util.List;
import java.util.ListIterator;

import org.msscf.msscf.v2_13.cflib.CFLib.*;
import org.msscf.msscf.v2_13.cfcore.MssCF.*;
import org.msscf.msscf.v2_13.cfbam.CFBamObj.*;

public class MSSBamCFGenBindColumnInContainerOrNamedLookupRelation
	extends MssCFGenBindObj
{
	private static final long serialVersionUID = 1L;

	public MSSBamCFGenBindColumnInContainerOrNamedLookupRelation() {
		super();
	}

	public MSSBamCFGenBindColumnInContainerOrNamedLookupRelation(
		MSSBamCFEngine argSchema,
		String toolset,
		String scopeDefClassName,
		String genDefClassName,
		String itemName)
	{
		super( argSchema, toolset, scopeDefClassName, genDefClassName, itemName);
	}

	/**
	 *	The core evaluation of ColumnInOwnerLookupRelation is also to determine
	 *	if an index definition is in an owner relation.
	 *	<P>
	 *	WORKING: Yes, I know.  This should be a business method instead
	 *	of copying the code in ICFBamIndexObj and MSIsaGenBindColumnInOwnerLookupRelation.
	 *
	 *	@param	genDef to be considered
	 *	@return	True if the column participates in an owner relation.
	 */
	public static Boolean isColumnInContainerOrNamedLookupRelation(ICFLibAnyObj genDef) {

		ICFLibAnyObj		focusDef;
		ICFBamTableObj		tableDef;
		final String S_ProcName = "isColumnInContainerOrNamedLookupRelation";

		if( genDef instanceof ICFBamAtomObj ) {
			ICFBamAtomObj atomDef = (ICFBamAtomObj)genDef;
			ICFLibAnyObj atomScopeDef = atomDef.getObjScope();
			tableDef = (ICFBamTableObj)atomScopeDef;
			focusDef = genDef;
		}
		else if( genDef instanceof ICFBamTableColObj ) {
			ICFBamTableColObj tableColDef = (ICFBamTableColObj)genDef;
			ICFLibAnyObj tableColScopeDef = tableColDef.getObjScope();
			tableDef = (ICFBamTableObj)tableColScopeDef;
			focusDef = genDef;
		}
		else if( genDef instanceof ICFBamIndexColObj ) {
			ICFBamIndexColObj indexColDef = (ICFBamIndexColObj)genDef;
			focusDef = indexColDef.getRequiredLookupColumn();
			if( focusDef instanceof ICFBamAtomObj ) {
				tableDef = (ICFBamTableObj)((ICFBamAtomObj)focusDef).getObjScope();
			}
			else if( focusDef instanceof ICFBamTableColObj ) {
				tableDef = (ICFBamTableObj)((ICFBamTableColObj)focusDef).getObjScope();
			}
			else {
				throw new CFLibUnsupportedClassException( MSSBamCFGenBindColumnInContainerOrNamedLookupRelation.class,
					S_ProcName,
					"genContext.GenDef.LookupColumn",
					genDef,
					"ICFBamAtomObj, ICFBamTableColObj" );
			}
		}
		else if( genDef instanceof ICFBamRelationColObj ) {
			ICFBamRelationColObj relColDef = (ICFBamRelationColObj)genDef;
			ICFLibAnyObj columnDef = relColDef.getRequiredLookupFromCol();
			if( columnDef instanceof ICFBamAtomObj ) {
				focusDef = columnDef;
				tableDef = (ICFBamTableObj)columnDef.getObjScope();
			}
			else if( columnDef instanceof ICFBamTableColObj ) {
				focusDef = columnDef;
				tableDef = (ICFBamTableObj)columnDef.getObjScope();
			}
			else {
				throw new CFLibUnsupportedClassException( MSSBamCFGenBindColumnInContainerOrNamedLookupRelation.class,
					S_ProcName,
					"genContext.GenDef.FromCol",
					genDef,
					"ICFBamAtomObj, ICFBamTableColObj" );
			}
		}
		else {
			throw new CFLibUnsupportedClassException( MSSBamCFGenBindColumnInContainerOrNamedLookupRelation.class,
				S_ProcName,
				"genContext.GenDef",
				genDef,
				"ICFBamAtomObj, ICFBamTableColObj, ICFBamIndexColObj, ICFBamRelationColObj" );
		}

		List<ICFBamRelationObj> containerNamedLookupRelations = MSSBamCFTableObj.getOwnerContainerNamedLookupChainRelations( tableDef );
		if( ( containerNamedLookupRelations == null )
		 || ( ( containerNamedLookupRelations != null ) && ( containerNamedLookupRelations.size() == 0 ) ) )
		{
			return( false );
		}

		ListIterator<ICFBamRelationObj> ownerEnumerator = containerNamedLookupRelations.listIterator();

		ICFBamRelationObj ownerRelation;
		ICFBamRelationColObj ownerRelationCol;
		ICFBamIndexColObj indexCol;
		Iterator<ICFBamRelationColObj> ownerRelationCols;

		while( ownerEnumerator.hasNext() ) {

			ownerRelation = ownerEnumerator.next();
			ownerRelationCols = ownerRelation.getOptionalComponentsColumns().iterator();
			while( ownerRelationCols.hasNext() ) {
				ownerRelationCol = ownerRelationCols.next();
				indexCol = ownerRelationCol.getRequiredLookupFromCol();
				if( indexCol.getRequiredLookupColumn() == focusDef ) {
					return( true );
				}
			}
		}

		return( false );
	}

	public String expandBody( MssCFGenContext genContext ) {
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

		if (isColumnInContainerOrNamedLookupRelation(genDef)) {
			return ( "yes" );
		}
		else {
			return ( "no" );
		}
	}
}
