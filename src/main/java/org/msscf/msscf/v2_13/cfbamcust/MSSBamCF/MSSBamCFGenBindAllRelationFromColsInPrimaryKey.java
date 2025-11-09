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

import org.msscf.msscf.v2_13.cflib.CFLib.*;
import org.msscf.msscf.v2_13.cfbam.CFBamObj.ICFBamIndexColObj;
import org.msscf.msscf.v2_13.cfbam.CFBamObj.ICFBamIndexObj;
import org.msscf.msscf.v2_13.cfbam.CFBamObj.ICFBamRelationColObj;
import org.msscf.msscf.v2_13.cfbam.CFBamObj.ICFBamRelationObj;
import org.msscf.msscf.v2_13.cfbam.CFBamObj.ICFBamTableObj;
import org.msscf.msscf.v2_13.cfbam.CFBamObj.ICFBamValueObj;
import org.msscf.msscf.v2_13.cfcore.MssCF.*;

public class MSSBamCFGenBindAllRelationFromColsInPrimaryKey
	extends MssCFGenBindObj
{
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	public MSSBamCFGenBindAllRelationFromColsInPrimaryKey() {
		super();
	}

	public MSSBamCFGenBindAllRelationFromColsInPrimaryKey(
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

		ICFBamRelationObj relnToCheck;
		if( genDef instanceof ICFBamRelationObj ) {
			relnToCheck = (ICFBamRelationObj)genDef;
			if (areAllRelationFromColsInPrimaryKey(relnToCheck)) {
				return( "yes" );
			}
			else {
				return( "no" );
			}
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"genContext.GenDef",
				genDef,
				"ICFBamRelationObj" );
		}
	}

	public static boolean areAllRelationFromColsInPrimaryKey( ICFBamRelationObj relnToCheck ) {
		if (relnToCheck == null) {
			return false;
		}

		ICFBamTableObj fromTable = relnToCheck.getRequiredContainerFromTable();
		ICFBamIndexObj fromPKey = fromTable.getOptionalLookupPrimaryIndex();
		for(ICFBamRelationColObj col: relnToCheck.getOptionalComponentsColumns()) {
			ICFBamValueObj fromCol = col.getRequiredLookupFromCol().getRequiredLookupColumn();
			boolean inPrimaryKey = false;
			for (ICFBamIndexColObj pcol: fromPKey.getOptionalComponentsColumns()) {
				if (pcol.getRequiredLookupColumn() == fromCol) {
					inPrimaryKey = true;
					break;
				}
			}
			if (!inPrimaryKey) {
				return false;
			}
		}
		return true;
	}
}
