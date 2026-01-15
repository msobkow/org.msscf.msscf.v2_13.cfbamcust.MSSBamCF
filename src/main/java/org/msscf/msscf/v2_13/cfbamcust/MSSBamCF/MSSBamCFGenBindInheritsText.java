/*
 *	MSS Code Factory CFBam 2.13 CustMSSBamCF
 *
 *	Copyright (C) 2016-2026 Mark Stephen Sobkow (mailto:mark.sobkow@gmail.com)
 *	
 *	This program is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *	
 *	This program is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *	
 *	You should have received a copy of the GNU General Public License
 *	along with this program.  If not, see &lt;https://www.gnu.org/licenses/&gt;.
 *	
 *	If you wish to modify and use this code without publishing your changes,
 *	or integrate it with proprietary code, please contact Mark Stephen Sobkow
 *	for a commercial license at mark.sobkow@gmail.com
 */

package org.msscf.msscf.v2_13.cfbamcust.MSSBamCF;

import java.util.Iterator;
import java.util.SortedMap;

import org.msscf.msscf.v2_13.cflib.CFLib.*;
import org.msscf.msscf.v2_13.cfbam.CFBam.CFBamChainPKey;
import org.msscf.msscf.v2_13.cfbam.CFBam.ICFBamSchema;
import org.msscf.msscf.v2_13.cfcore.MssCF.*;
import org.msscf.msscf.v2_13.cfbam.CFBamObj.*;

public class MSSBamCFGenBindInheritsText
	extends MssCFGenBindObj
{
	private static final long serialVersionUID = 1L;

	public MSSBamCFGenBindInheritsText() {
		super();
	}

	public MSSBamCFGenBindInheritsText(
		MSSBamCFEngine argSchema,
		String toolset,
		String scopeDefClassName,
		String genDefClassName,
		String itemName)
	{
		super( argSchema, toolset, scopeDefClassName, genDefClassName, itemName);
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

		int numDataAttrs = 0;

		if (genDef instanceof ICFBamTableObj) {
			ICFBamTableObj tableDef = (ICFBamTableObj)genDef;
			ICFBamTableObj superDef;
			ICFBamValueObj columnDef;
			ICFBamTableColObj tableCol;
			ICFBamRelationObj relation;
			Iterator<ICFBamValueObj> tableColumns;
			Iterator<ICFBamRelationObj> tableRelations;
			while( tableDef != null ) {
				tableColumns = tableDef.getOptionalComponentsColumns().iterator();
				while( tableColumns.hasNext() ) {
					columnDef = tableColumns.next();
					if( columnDef instanceof ICFBamTextColObj ) {
						return( "yes" );
					}
					else if( columnDef instanceof ICFBamTableColObj ) {
						tableCol = (ICFBamTableColObj)columnDef;
						if( tableCol.getRequiredParentDataType() instanceof ICFBamTextTypeObj ) {
							return( "yes" );
						}
					}
				}
				superDef = null;
				tableRelations = tableDef.getOptionalComponentsRelation().iterator();
				while( ( superDef == null ) && tableRelations.hasNext() ) {
					relation = tableRelations.next();
					if( relation.getRequiredRelationType() == ICFBamSchema.RelationTypeEnum.Superclass ) {
						superDef = relation.getRequiredLookupToTable();
					}
				}
				tableDef = superDef;
			}
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"genContext.GenDef",
				genDef,
				"ICFBamTableObj" );
		}

		return ("no");
	}
}
