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
import org.msscf.msscf.v2_13.cfcore.MssCF.*;
import org.msscf.msscf.v2_13.cfbam.CFBamObj.*;

public class MSSBamCFGenReferencePopSubDep
	extends MssCFGenReferenceObj
{
	private static final long serialVersionUID = 1L;

	public MSSBamCFGenReferencePopSubDep() {
		super();
	}

	public MSSBamCFGenReferencePopSubDep(
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

		ICFLibAnyObj ret = null;
		if( genDef instanceof ICFBamPopTopDepObj ) {
			ICFBamPopTopDepObj topDep = (ICFBamPopTopDepObj)genDef;
			Iterator<ICFBamPopSubDep1Obj> iter = topDep.getOptionalComponentsPopDep().iterator();
			if( iter.hasNext() ) {
				ret = iter.next();
			}
		}
		else if( genDef instanceof ICFBamPopSubDep1Obj ) {
			ICFBamPopSubDep1Obj subDep = (ICFBamPopSubDep1Obj)genDef;
			Iterator<ICFBamPopSubDep2Obj> iter = subDep.getOptionalComponentsPopDep().iterator();
			if( iter.hasNext() ) {
				ret = iter.next();
			}
		}
		else if( genDef instanceof ICFBamPopSubDep2Obj ) {
			ICFBamPopSubDep2Obj subDep = (ICFBamPopSubDep2Obj)genDef;
			Iterator<ICFBamPopSubDep3Obj> iter = subDep.getOptionalComponentsPopDep().iterator();
			if( iter.hasNext() ) {
				ret = iter.next();
			}
		}
		else if( genDef instanceof ICFBamPopSubDep3Obj ) {
			ret = null;
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"genContext.GenDef",
				genDef,
				"ICFBamPopTopDepObj, ICFBamPopSubDep1Obj, ICFBamPopSubDep2Obj, ICFBamPopSubDep3Obj" );
		}

		return (ret);
	}
}
