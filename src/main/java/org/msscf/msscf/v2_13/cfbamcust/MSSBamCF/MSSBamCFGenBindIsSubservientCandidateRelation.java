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

import org.msscf.msscf.v2_13.cflib.CFLib.*;
import org.msscf.msscf.v2_13.cfbam.CFBamObj.ICFBamRelationObj;
import org.msscf.msscf.v2_13.cfbam.CFBamObj.ICFBamTableObj;
import org.msscf.msscf.v2_13.cfcore.MssCF.*;

public class MSSBamCFGenBindIsSubservientCandidateRelation
	extends MssCFGenBindObj
{
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	public MSSBamCFGenBindIsSubservientCandidateRelation() {
		super();
	}

	public MSSBamCFGenBindIsSubservientCandidateRelation(
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
			if (isSubservientCandidateRelation(relnToCheck)) {
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

	public static boolean isSubservientCandidateRelation( ICFBamRelationObj relnToCheck ) {
		if (relnToCheck == null) {
			return false;
		}

		ICFBamTableObj fromTable = relnToCheck.getRequiredContainerFromTable();
		ICFBamTableObj toTable = relnToCheck.getRequiredLookupToTable();
		if( fromTable.getOptionalLookupDefSchema() != null || toTable.getOptionalLookupDefSchema() != null) {
			return false;
		}
		if (MSSBamCFGenBindIsSuperiorCandidateRelation.inheritsMutable(toTable)) {
			return false;
		}
		if (MSSBamCFGenBindIsSuperiorCandidateRelation.inheritsMutable(fromTable)) {
			// An entity which pops in and out of existence can't enjoy database dependency enforcement on it's components and children
			return false;
		}
		switch(relnToCheck.getRequiredRelationType()) {
			case Lookup:
			case Container:
			case Owner:
			case Parent:
			case Unknown:
				return false;
			case Children:
			case Components:
				ICFBamRelationObj reversed = MSSBamCFAnyObj.derefReverseRelation(relnToCheck);
				if (reversed == null) {
					return false;
				}
				else {
					return true;
				}
			case Superclass:
				return false;
			default:
				return false;
		}
	}

}
