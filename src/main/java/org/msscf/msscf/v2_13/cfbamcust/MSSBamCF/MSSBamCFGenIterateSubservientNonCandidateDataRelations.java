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

import java.util.*;

import org.msscf.msscf.v2_13.cflib.CFLib.*;
import org.msscf.msscf.v2_13.cfcore.MssCF.*;
import org.msscf.msscf.v2_13.cfbam.CFBam.*;
import org.msscf.msscf.v2_13.cfbam.CFBam.ICFBamSchema.RelationTypeEnum;
import org.msscf.msscf.v2_13.cfbam.CFBamObj.*;

public class MSSBamCFGenIterateSubservientNonCandidateDataRelations
	extends MssCFGenIteratorObj
{
	private static final long serialVersionUID = 1L;

	public MSSBamCFGenIterateSubservientNonCandidateDataRelations() {
		super();
	}

	public MSSBamCFGenIterateSubservientNonCandidateDataRelations(
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

		if( genContext == null ) {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				1,
				"genContext" );
		}

		genDef = genContext.getGenDef();
		if( genDef == null ) {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				0,
				"genContext.GenDef" );
		}

		if( ! (genDef instanceof ICFBamTableObj) ) {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"genContext.GenDef",
				genDef,
				"ICFBamTableObj" );
		}

		ICFBamTableObj tableObj = (ICFBamTableObj)genDef;
		List<ICFLibAnyObj> list = getSubservientNonCandidateDataRelations(tableObj);
		return (list.listIterator());
	}

	public static List<ICFLibAnyObj> getSubservientNonCandidateDataRelations(ICFBamTableObj tableObj) {
		final String S_ProcName = "getSubservientNonCandidateDataRelations";
		if(tableObj == null) {
			throw new CFLibNullArgumentException(MSSBamCFGenIterateSubservientNonCandidateDataRelations.class, S_ProcName, 0, "tableObj");
		}
		List<ICFLibAnyObj> list = new LinkedList<ICFLibAnyObj>();
		List<ICFBamRelationObj> tableRelations = MSSBamCFTableObj.getChildrenRelations(tableObj);
		if (tableRelations == null) {
			return list;
		}
		for( ICFBamRelationObj relation : tableRelations) {
			if (relation.getRequiredRelationType() != RelationTypeEnum.Superclass && MSSBamCFGenBindIsSubservientNonCandidateDataRelation.isSubservientNonCandidateDataRelation(relation)) {
				list.add(relation);
			}
		}
		return list;
	}
}
