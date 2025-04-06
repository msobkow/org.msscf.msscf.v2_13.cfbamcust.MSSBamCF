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

import java.math.*;
import java.text.*;
import java.util.*;

import org.msscf.msscf.v2_13.cflib.CFLib.*;
import org.msscf.msscf.v2_13.cfbam.CFBam.*;
import org.msscf.msscf.v2_13.cfbam.CFBamObj.*;
import org.msscf.msscf.v2_13.cfcore.CFGenKbObj.ICFGenKbDefClassObj;
import org.msscf.msscf.v2_13.cfcore.MssCF.MssCFEngine;

public class MSSBamCFIndexColObj
{
	public MSSBamCFIndexColObj() {
	}

	protected static ICFBamTableColObj probeForFromColumn( ICFBamTableObj searchTable,
		ICFBamSchema.RelationTypeEnum relationType,
		boolean isNullable,
		ICFBamValueObj typeSpec )
	{
		ICFBamRelationObj searchSuperRelation = MSSBamCFTableObj.getSuperClassRelation( searchTable );
		if( searchSuperRelation != null ) {
			ICFBamTableColObj superValue = probeForFromColumn( searchSuperRelation.getRequiredLookupToTable(),
				relationType,
				isNullable,
				typeSpec );
			if( superValue != null ) {
				return( superValue );
			}
		}

		ICFBamRelationObj relation;
		ICFBamRelationColObj relationCol;
		ICFBamIndexColObj fromCol;
		ICFBamValueObj dataCol;
		ICFBamTableColObj fromTableCol;
		Iterator<ICFBamRelationObj> iterRelations = searchTable.getOptionalComponentsRelation().iterator();
		Iterator<ICFBamRelationColObj> iterRelationColumns;

		while( iterRelations.hasNext() ) {
			relation = iterRelations.next();
			if( relation.getRequiredRelationType() == relationType ) {
				iterRelationColumns = relation.getOptionalComponentsColumns().iterator();
				while( iterRelationColumns.hasNext() ) {
					relationCol = iterRelationColumns.next();
					fromCol = relationCol.getRequiredLookupFromCol();
					dataCol = fromCol.getRequiredLookupColumn();
					if( dataCol instanceof ICFBamTableColObj ) {
						fromTableCol = (ICFBamTableColObj)dataCol;
						if( fromTableCol.getRequiredIsNullable() == isNullable ) {
							if( fromTableCol.getRequiredParentDataType() == typeSpec ) {
								return( fromTableCol );
							}
						}
					}
				}
			}
		}

		return( null );
	}

	protected static ICFBamTableColObj probeForFromColumn( ICFBamTableObj searchTable,
		String relationTypeName,
		boolean isNullable,
		ICFBamValueObj typeSpec )
	{
		ICFBamSchema.RelationTypeEnum relationType = CFBamSchema.parseRelationTypeEnum( relationTypeName ); 
		if( relationType == null ) {
			throw new CFLibNullArgumentException( CFBamIndexColObj.class,
				"probeForFromColumn",
				1,
				"Resolution of RelationType " + relationTypeName );
		}
		return( probeForFromColumn( searchTable, relationType, isNullable, typeSpec ) );
	}

	public static ICFBamValueObj getWidestLookupColumn( ICFBamIndexColObj targetIndexCol, MSSBamCFGenContext genContext )
	{
		final String S_ProcName = "CFBamIndexColObj.getWidestLookupColumn() ";

		MssCFEngine genEngine = genContext.getGenEngine();

		/*
		 *	In order to implement the named lookups, what I need
		 *	to do is iterate through the columns of the lookup,
		 *	then for each one, pop to the top tabledef and search for
		 *	a matching inherited mandatory column type and use that
		 *	to pass the argument.
		 *
		 *	If no mandatory column is found, then an optional one
		 *	will be searched for.
		 *
		 *	I think I'll call the GEL verb SatisfyWidestLookupColumn
		 *	for a scope of IndexColumn, on the assumption that you're
		 *	iterating through the columns of the LookupIndex when
		 *	invoking this specialized verb.  The widest specification
		 *	is the one closest to the base class, not the first one
		 *	encountered in the inheritance tree.  Thus this will need
		 *	to be a recursive function that only considers the current
		 *	table's columns if the superclass could not find a match.
		 *
		 *	The first pass should only consider columns which are in the
		 *	Owner relationships of the table hierarchy.  Then a second
		 *	pass which considers columns of the Container relationships
		 *	should be performed.  The required passes should be made before
		 *	any optional passes are made.
		 *
		 *	Only if we can't resolve the column using an Owner or Container
		 *	relationship should we widen the search to general columns.
		 */

		// Determine the target column type

		ICFBamValueObj targetColumn = targetIndexCol.getRequiredLookupColumn();

		ICFBamValueObj typeSpec;
		if( targetColumn instanceof ICFBamTableColObj ) {
			typeSpec = ((ICFBamTableColObj)targetColumn).getRequiredParentDataType();
		}
		else {
			genEngine.getLog().message( S_ProcName + "Can only resolve TableCol columns, not "
				+ targetColumn.getClass().getSimpleName() + " "
				+ targetColumn.getRequiredContainerScope().getObjName() + "." + targetColumn.getRequiredName() );
			return( null );
		}

		// Emulate a "poptop Table" to get the table to be searched

		String goalTypeName = "Table";
		ICFGenKbDefClassObj goalType = genEngine.getDefClassTableObj().readDefClassByNameIdx(goalTypeName);
		if( goalType == null ) {
			genEngine.getLog().message( S_ProcName + "Could not find goal class \"" + goalTypeName + "\""
				+ "\" is invalid" );
			return( null );
		}

		ICFGenKbDefClassObj	objectDefType = genEngine.getDefClassTableObj().readDefClassByNameIdx( "Object" );
		if( objectDefType == null ) {
			genEngine.getLog().message( S_ProcName + "Could not find object type \"Object\". \"" );
			return( null );
		}

		MSSBamCFGenContext goalContext = genContext;
		MSSBamCFGenContext prevContext;
		MSSBamCFGenContext topContext = null;
		String searchTypeName;
		ICFGenKbDefClassObj searchType;

		while (goalContext != null) {
			searchTypeName = goalContext.getRequiredLookupGenDef().getRequiredName();
			searchType = genEngine.getDefClassTableObj().readDefClassByNameIdx(searchTypeName);
			if( searchType == null ) {
				genEngine.getLog().message( S_ProcName + "Could not find search class \"" + searchTypeName + "\"." );
				return( null );
			}

			while ( (searchType != null ) && ( searchType != goalType )) {
				if( searchType != objectDefType ) {
					searchType = searchType.getOptionalParentBaseDefClass();
				}
				else {
					searchType = null;
				}
			}

			if (searchType != null) {
				// The goal context definition derives from the target type
				topContext = goalContext;
			}

			prevContext = (MSSBamCFGenContext)goalContext.getPrevContext();
			if (prevContext != goalContext) {
				goalContext = prevContext;
			}
			else {
				goalContext = null;
			}
		}

		if (topContext == null) {
			genEngine.getLog().message( S_ProcName + "Could not find top context with a definition derived from \"" + goalTypeName + "\"");
			return (null);
		}

		ICFBamTableObj searchTable;
		ICFLibAnyObj refDef = topContext.getGenDef();
		if( refDef instanceof ICFBamTableObj ) {
			searchTable = (ICFBamTableObj)refDef;
		}
		else {
			genEngine.getLog().message( S_ProcName + "Top context says it has a definition derived from \"" + goalTypeName + "\", but returned an "
				+ refDef.getClass().getSimpleName() );
			return (null);
		}

		// Search for the shallowest required Owner attribute derived from the specified data type
		// If not found, search for the shallowest required Container attribute derived from the specified data type
		// If not found, search for the shallowest required Parent attribute derived from the specified data type
		// If not found, search for the shallowest required Lookup attribute derived from the specified data type
		// If not found, search for the shallowest optional Owner attribute derived from the specified data type
		// If not found, search for the shallowest optional Container attribute derived from the specified data type
		// If not found, search for the shallowest optional Parent attribute derived from the specified data type
		// If not found, search for the shallowest optional Lookup attribute derived from the specified data type
		// If not found, barf

		ICFBamTableColObj matchingTableCol = probeForFromColumn( searchTable, "Owner", false, typeSpec );
		if( matchingTableCol != null ) {
			return( matchingTableCol );
		}

		matchingTableCol = probeForFromColumn( searchTable, "Container", false, typeSpec );
		if( matchingTableCol != null ) {
			return( matchingTableCol );
		}

		matchingTableCol = probeForFromColumn( searchTable, "Parent", false, typeSpec );
		if( matchingTableCol != null ) {
			return( matchingTableCol );
		}

		matchingTableCol = probeForFromColumn( searchTable, "Lookup", false, typeSpec );
		if( matchingTableCol != null ) {
			return( matchingTableCol );
		}

		matchingTableCol = probeForFromColumn( searchTable, "Owner", true, typeSpec );
		if( matchingTableCol != null ) {
			return( matchingTableCol );
		}

		matchingTableCol = probeForFromColumn( searchTable, "Container", true, typeSpec );
		if( matchingTableCol != null ) {
			return( matchingTableCol );
		}

		matchingTableCol = probeForFromColumn( searchTable, "Parent", true, typeSpec );
		if( matchingTableCol != null ) {
			return( matchingTableCol );
		}

		matchingTableCol = probeForFromColumn( searchTable, "Lookup", true, typeSpec );
		if( matchingTableCol != null ) {
			return( matchingTableCol );
		}

		genEngine.getLog().message( S_ProcName + "Could not find a matching " + searchTable.getRequiredName() + " column for type spec \"" + typeSpec.getRequiredName() + "\"" );
		return( null );
	}
}
