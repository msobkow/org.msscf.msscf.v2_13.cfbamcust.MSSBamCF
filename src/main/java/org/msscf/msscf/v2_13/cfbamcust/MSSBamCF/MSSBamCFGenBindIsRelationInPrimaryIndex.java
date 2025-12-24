/*
 *	MSS Code Factory CFBam 2.13 CustMSSBamCF
 *
 *	Copyright (C) 2016-2025 Mark Stephen Sobkow (mailto:mark.sobkow@gmail.com)
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

import org.msscf.msscf.v2_13.cfbam.CFBamObj.ICFBamIndexColObj;
import org.msscf.msscf.v2_13.cfbam.CFBamObj.ICFBamIndexObj;
import org.msscf.msscf.v2_13.cfbam.CFBamObj.ICFBamRelationColObj;
import org.msscf.msscf.v2_13.cflib.CFLib.*;
import org.msscf.msscf.v2_13.cfbam.CFBamObj.ICFBamRelationObj;
import org.msscf.msscf.v2_13.cfbam.CFBamObj.ICFBamTableObj;
import org.msscf.msscf.v2_13.cfbam.CFBamObj.ICFBamValueObj;
import org.msscf.msscf.v2_13.cfcore.MssCF.*;

public class MSSBamCFGenBindIsRelationInPrimaryIndex
	extends MssCFGenBindObj
{
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	public MSSBamCFGenBindIsRelationInPrimaryIndex() {
		super();
	}

	public MSSBamCFGenBindIsRelationInPrimaryIndex(
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
			if (isRelationInPrimaryIndex(relnToCheck)) {
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

	public static boolean isRelationInPrimaryIndex( ICFBamRelationObj relnToCheck ) {
		if (relnToCheck == null) {
			return false;
		}
		ICFBamTableObj tbl = relnToCheck.getRequiredContainerFromTable();
		do {
			ICFBamRelationObj superRelation = tbl.getSuperClassRelation();
			if (superRelation != null) {
				tbl = superRelation.getRequiredLookupToTable();
			}
			else {
				break;
			}
		} while (true);
		ICFBamIndexObj pidx = tbl.getPrimaryKeyIndex();
		for (ICFBamRelationColObj col: relnToCheck.getOptionalComponentsColumns()) {
			ICFBamValueObj fromcol = col.getRequiredLookupFromCol().getRequiredLookupColumn();
			boolean colNotInList = true;
			for (ICFBamIndexColObj idxcol: pidx.getOptionalComponentsColumns()) {
				ICFBamValueObj rcol = idxcol.getRequiredLookupColumn();
				if (fromcol == rcol) {
					colNotInList = false;
					break;
				}
			}
			if (colNotInList) {
				return( false );
			}
		}
		return( true );
	}

}
