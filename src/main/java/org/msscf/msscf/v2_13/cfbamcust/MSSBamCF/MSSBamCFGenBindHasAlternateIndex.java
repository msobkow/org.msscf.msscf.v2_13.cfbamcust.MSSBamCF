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

import java.util.List;

import org.msscf.msscf.v2_13.cflib.CFLib.*;
import org.msscf.msscf.v2_13.cfcore.MssCF.*;
import org.msscf.msscf.v2_13.cfbam.CFBamObj.*;

public class MSSBamCFGenBindHasAlternateIndex
	extends MssCFGenBindObj
{
	private static final long serialVersionUID = 1L;

	public MSSBamCFGenBindHasAlternateIndex() {
		super();
	}

	public MSSBamCFGenBindHasAlternateIndex(
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

		ICFBamTableObj curTable = null;

		if (genDef instanceof ICFBamTableObj) {
			curTable = (ICFBamTableObj)genDef;
		}
		else if (genDef instanceof ICFBamAtomObj) {
			ICFLibAnyObj atomScope = ((ICFBamAtomObj)genDef).getObjScope();
			if ((atomScope != null) && (atomScope instanceof ICFBamTableObj)) {
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
				throw new RuntimeException(S_ProcName + "genContext.getGenDef()<IndexColDef>.getRequiredContainerIndex() is null");
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

		ICFBamIndexObj alternateIndex = null;

		while ((alternateIndex == null) && (curTable != null)) {
			alternateIndex = curTable.getOptionalLookupAltIndex();
			if( alternateIndex == null ) {
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

		String ret;

		if ( alternateIndex != null ) {
			ret = "yes";
		}
		else {
			ret = "no";
		}

		return (ret);
	}
}
