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

import org.msscf.msscf.v2_13.cflib.CFLib.*;
import org.msscf.msscf.v2_13.cfbam.CFBam.*;
import org.msscf.msscf.v2_13.cfbam.CFBamObj.ICFBamRelationObj;
import org.msscf.msscf.v2_13.cfbam.CFBamObj.ICFBamTableObj;
import org.msscf.msscf.v2_13.cfcore.MssCF.*;

public class MSSBamCFGenBindIsSubObjectLookup
	extends MssCFGenBindObj
{
	private static final long serialVersionUID = 1L;

	public MSSBamCFGenBindIsSubObjectLookup() {
		super();
	}

	public MSSBamCFGenBindIsSubObjectLookup(
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

		if( ! ( genDef instanceof ICFBamRelationObj ) ) { 
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"genDef",
				genDef,
				"ICFBamRelationObj" );
		}

		ICFBamRelationObj relationDef = (ICFBamRelationObj)genDef;

		ICFBamSchema.RelationTypeEnum relationType = relationDef.getRequiredRelationType();
		if( ! ( relationType == ICFBamSchema.RelationTypeEnum.Lookup ) ) {
			return( "no" );
		}

		Iterator<ICFBamRelationObj> iter;
		ICFBamRelationObj scanRelation;
		String typeTag;
		ICFBamRelationObj superRelation;
		ICFBamTableObj targetTable = relationDef.getRequiredLookupToTable();
		ICFBamTableObj scanTable = relationDef.getRequiredContainerFromTable();
		while( scanTable != null ) {
			iter = scanTable.getOptionalComponentsRelation().iterator();
			while( iter.hasNext() ) {
				scanRelation = iter.next();
				relationType = scanRelation.getRequiredRelationType();
				if( ( relationType == ICFBamSchema.RelationTypeEnum.Components )
				 || ( relationType == ICFBamSchema.RelationTypeEnum.Children )
				 || ( relationType == ICFBamSchema.RelationTypeEnum.Components ) )
				{
					if( targetTable == scanRelation.getRequiredLookupToTable() ) {
						return( "yes" );
					}
				}
			}

			superRelation = MSSBamCFTableObj.getSuperClassRelation( scanTable );
			if( superRelation != null ) {
				scanTable = superRelation.getRequiredLookupToTable();
			}
			else {
				scanTable = null;
			}
		}

		return( "no" );
	}
}
