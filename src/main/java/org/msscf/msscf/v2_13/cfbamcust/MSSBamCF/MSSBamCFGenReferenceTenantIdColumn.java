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
import org.msscf.msscf.v2_13.cfbam.CFBam.*;
import org.msscf.msscf.v2_13.cfbam.CFBamObj.*;

public class MSSBamCFGenReferenceTenantIdColumn
	extends MssCFGenReferenceObj
{
	private static final long serialVersionUID = 1L;

	public MSSBamCFGenReferenceTenantIdColumn() {
		super();
	}

	public MSSBamCFGenReferenceTenantIdColumn(
		MSSBamCFEngine argSchema,
		String toolset,
		String scopeDefClassName,
		String genDefClassName,
		String itemName)
	{
		super(argSchema, toolset, scopeDefClassName, genDefClassName, itemName, "Value");
	}

	public static ICFBamValueObj resolveTenantId( ICFLibAnyObj genDef ) {
		ICFBamValueObj	retValue = null;
		ICFBamTableObj	tableDef;
		final String S_ProcName = "resolveTenantId";

		if( genDef instanceof ICFBamTableObj ) {
			tableDef = (ICFBamTableObj)genDef;
		}
		else {
			throw new CFLibUnsupportedClassException( MSSBamCFGenReferenceTenantIdColumn.class,
					S_ProcName,
					"genContext.GenDef",
					genDef,
					"ICFBamTableObj" );
		}

		ICFBamRelationObj rel;
		ICFBamSchema.RelationTypeEnum reltype;
		ICFBamRelationObj candidateRelation;
		Iterator<ICFBamRelationObj> relations;

		while( ( retValue == null ) && ( tableDef != null ) ) {
			if( tableDef.getRequiredName().equals( "Tenant" ) ) {
				throw new CFLibRuntimeException( MSSBamCFGenReferenceTenantIdColumn.class,
					S_ProcName,
					"Cannot dereference a Tenant from a Tenant" );
			}

			relations = tableDef.getOptionalComponentsRelation().iterator();
			while( ( retValue == null ) && ( relations.hasNext() ) ) {

				rel = relations.next();
				reltype = rel.getRequiredRelationType();

				if( reltype == ICFBamSchema.RelationTypeEnum.Owner ) {
					candidateRelation = rel;
				}
				else if( reltype == ICFBamSchema.RelationTypeEnum.Container ) {
					candidateRelation = rel;
				}
				else {
					candidateRelation = null;
				}

				if( candidateRelation != null ) {
					ICFBamTableObj targetTable = candidateRelation.getRequiredLookupToTable();
					if( ! targetTable.getRequiredName().equals( "Tenant" ) ) {
						candidateRelation = null;
					}
				}

				if( candidateRelation != null ) {
					ICFBamRelationColObj relcol = candidateRelation.getOptionalComponentsColumns().iterator().next();
					ICFBamIndexColObj indexCol = relcol.getRequiredLookupFromCol();
					retValue = indexCol.getRequiredLookupColumn();
				}
			}

			if( retValue == null ) {
				rel = MSSBamCFTableObj.getSuperClassRelation( tableDef );
				if( rel != null ) {
					tableDef = rel.getRequiredLookupToTable();
				}
				else {
					tableDef = null;
				}
			}
		}

		return( retValue );
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

		ICFBamValueObj deref = resolveTenantId( genDef );
		if( deref != null ) {
			return( deref );
		}
		else {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				0,
				"resolveTenantId( genContext.genDef )" );
		}
	}
}
