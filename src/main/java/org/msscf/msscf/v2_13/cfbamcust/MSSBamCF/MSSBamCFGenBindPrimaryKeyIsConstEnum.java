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

import java.util.*;

import org.msscf.msscf.v2_13.cflib.CFLib.*;
import org.msscf.msscf.v2_13.cfcore.MssCF.*;
import org.msscf.msscf.v2_13.cfbam.CFBam.*;
import org.msscf.msscf.v2_13.cfbam.CFBamObj.*;

public class MSSBamCFGenBindPrimaryKeyIsConstEnum
	extends MssCFGenBindObj
{
	private static final long serialVersionUID = 1L;

	public MSSBamCFGenBindPrimaryKeyIsConstEnum() {
		super();
	}

	public MSSBamCFGenBindPrimaryKeyIsConstEnum(
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

		if( ! ( genDef instanceof ICFBamTableObj ) ) { 
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"genDef",
				genDef,
				"ICFBamTableObj" );
		}
		ICFBamTableObj tableObj = (ICFBamTableObj)genDef;
		String ret;

		if (genDef instanceof ICFBamTableObj) {
			ICFBamIndexObj primaryIndex = MSSBamCFTableObj.getPrimaryKeyIndex( (ICFBamTableObj)genDef );
			if( primaryIndex != null ) {
				List<ICFBamIndexColObj> indexColumnMap = primaryIndex.getOptionalComponentsColumns();
				Iterator<ICFBamIndexColObj> indexColumns = indexColumnMap.iterator();
				if( indexColumns.hasNext() ) {
					ICFBamIndexColObj indexCol = indexColumns.next();
					if( indexColumns.hasNext() ) {
						ret = "no";
					}
					else {
						ICFBamValueObj indexedColumn = indexCol.getRequiredLookupColumn();
						if( indexedColumn instanceof ICFBamTableColObj ) {
							ICFBamTableColObj tableCol = (ICFBamTableColObj)indexedColumn;
							Boolean generateId = tableCol.getOptionalGenerateId();
							if( ( generateId != null ) && generateId.booleanValue() ) {
								// If the Id is generated it's not a constant
								ret = "no";
							}
							else {
								ICFBamValueObj dataDef = tableCol.getRequiredParentDataType();
								if( dataDef instanceof ICFBamEnumDefObj ) {
									ret = "yes";
								}
								else {
									ret = "no";
								}
							}
						}
						else {
							ret = "no";
						}
					}
				}
				else {
					ret = "no";
				}
			}
			else {
				ret = "no";
			}
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"genContext.GenDef",
				genDef,
				"ICFBamTableObj");
		}

		return (ret);
	}
}
