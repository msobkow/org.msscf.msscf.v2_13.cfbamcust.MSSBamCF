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

public class MSSBamCFGenIterateGeneratedTableColumns
	extends MssCFGenIteratorObj
{
	private static final long serialVersionUID = 1L;

	public MSSBamCFGenIterateGeneratedTableColumns() {
		super();
	}

	public MSSBamCFGenIterateGeneratedTableColumns(
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

		ICFBamValueObj col;
		Boolean maybeGenerated;
		boolean isGenerated;
		List<ICFLibAnyObj> list = new LinkedList<ICFLibAnyObj>();
		Iterator<ICFBamValueObj> childColumns = tableObj.getOptionalComponentsColumns().iterator();
		while (childColumns.hasNext()) {
			col = childColumns.next();
			maybeGenerated = col.getOptionalGenerateId();
			if( maybeGenerated == null ) {
				isGenerated = false;
			}
			else {
				isGenerated = maybeGenerated.booleanValue();
			}

			if (isGenerated) {
				list.add( col );
			}
		}

		return( list.listIterator() );
	}
}
