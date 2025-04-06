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
import java.util.SortedMap;

import org.msscf.msscf.v2_13.cflib.CFLib.*;
import org.msscf.msscf.v2_13.cfbam.CFBam.CFBamChainPKey;
import org.msscf.msscf.v2_13.cfbam.CFBamObj.ICFBamChainObj;
import org.msscf.msscf.v2_13.cfbam.CFBamObj.ICFBamIndexObj;
import org.msscf.msscf.v2_13.cfbam.CFBamObj.ICFBamRelationObj;
import org.msscf.msscf.v2_13.cfbam.CFBamObj.ICFBamTableObj;
import org.msscf.msscf.v2_13.cfcore.MssCF.*;

public class MSSBamCFGenReferenceAlternateIndex
	extends MssCFGenReferenceObj
{
	private static final long serialVersionUID = 1L;

	public MSSBamCFGenReferenceAlternateIndex() {
		super();
	}

	public MSSBamCFGenReferenceAlternateIndex(
		MSSBamCFEngine argSchema,
		String toolset,
		String scopeDefClassName,
		String genDefClassName,
		String itemName)
	{
		super(argSchema, toolset, scopeDefClassName, genDefClassName, itemName, "Chain");
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

		if( ! ( genDef instanceof ICFBamTableObj ) ) { 
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"genDef",
				genDef,
				"ICFBamTableObj" );
		}

		ICFBamTableObj curTable = (ICFBamTableObj)genDef;

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

		return( alternateIndex );
	}
}
