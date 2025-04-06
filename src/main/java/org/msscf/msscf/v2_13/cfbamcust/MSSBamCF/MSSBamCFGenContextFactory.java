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
import org.msscf.msscf.v2_13.cfcore.MssCF.*;

public class MSSBamCFGenContextFactory
extends MssCFGenContextFactory
{
	public MSSBamCFGenContextFactory()
	{
		super();
	}

	public MssCFGenContext newGenContext(
		String				generatingBuild,
		MssCFGenContext		genContext,
		String				subClassGenDefName,
		ICFLibAnyObj		subObject )
	{
		return( new MSSBamCFGenContext( generatingBuild, genContext, subClassGenDefName, subObject ) );
	}

	public MssCFGenContext newGenContext(
		String				generatingBuild,
		MssCFEngine			engine,
		String				argRootGenDir,
		String				toolset,
		String				scopeDefClassName,
		String				genDefClassName,
		String				itemName )
	{
		return( new MSSBamCFGenContext( generatingBuild,
			engine,
			argRootGenDir,
			toolset,
			scopeDefClassName,
			genDefClassName,
			itemName ) );
	}

	public MssCFGenContext newGenContext(
		String				generatingBuild,
		MssCFEngine			engine,
		String				argRootGenDir,
		String				toolset,
		String				scopeDefClassName,
		String				genDefClassName,
		String				itemName,
		ICFLibAnyObj		argGenDef,
		ICFLibAnyObj		argScopeDef )
	{
		return( new MSSBamCFGenContext( generatingBuild,
			engine,
			argRootGenDir,
			toolset,
			scopeDefClassName,
			genDefClassName,
			itemName,
			argGenDef,
			argScopeDef ) );
	}
}
