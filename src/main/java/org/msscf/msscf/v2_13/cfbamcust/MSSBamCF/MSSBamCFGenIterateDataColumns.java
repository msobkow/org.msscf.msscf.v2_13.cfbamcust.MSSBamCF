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
import org.msscf.msscf.v2_13.cfbam.CFBamObj.*;

public class MSSBamCFGenIterateDataColumns
	extends MssCFGenIteratorObj
{
	private static final long serialVersionUID = 1L;

	public MSSBamCFGenIterateDataColumns() {
		super();
	}

	public MSSBamCFGenIterateDataColumns(
		MSSBamCFEngine argSchema,
		String toolset,
		String scopeDefClassName,
		String genDefClassName,
		String itemName)
	{
		super( argSchema, toolset, scopeDefClassName, genDefClassName, itemName, "Value");
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
		ICFBamIndexObj primaryIndex = tableObj.getOptionalLookupPrimaryIndex();
		if( primaryIndex == null ) {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				0,
				"genContext.(ICFBamTableObj)GenDef.PrimaryIndex" );
		}

		List<ICFBamIndexColObj> indexColumns = primaryIndex.getOptionalComponentsColumns();
		List<ICFBamValueObj> columns = tableObj.getOptionalComponentsColumns();

		ICFBamValueObj value;
		ICFBamIndexColObj indexCol;
		boolean inIndex;
		Iterator<ICFBamIndexColObj> iterIndex;
		Iterator<ICFBamValueObj> almostDone = columns.iterator();
		List<ICFLibAnyObj> list = new LinkedList<ICFLibAnyObj>();
		while (almostDone.hasNext()) {
			value = almostDone.next();
			inIndex = false;
			iterIndex = indexColumns.iterator();
			while( ( ! inIndex ) && iterIndex.hasNext() ) {
				indexCol = iterIndex.next();
				if( value == indexCol.getRequiredLookupColumn() ) {
					inIndex = true;
				}
			}
			if( ! inIndex ) {
				list.add( value );
			}
		}

		return (list.listIterator());
	}
}
