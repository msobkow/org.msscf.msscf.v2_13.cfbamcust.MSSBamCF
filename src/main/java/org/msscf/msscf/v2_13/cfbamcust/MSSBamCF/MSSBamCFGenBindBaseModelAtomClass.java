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
import org.msscf.msscf.v2_13.cfcore.CFGenKbObj.ICFGenKbGelExecutableObj;
import org.msscf.msscf.v2_13.cfcore.MssCF.*;
import org.msscf.msscf.v2_13.cfbam.CFBamObj.*;

public class MSSBamCFGenBindBaseModelAtomClass
	extends MssCFGenBindObj
{
	private static final long serialVersionUID = 1L;

	public MSSBamCFGenBindBaseModelAtomClass() {
		super();
	}

	public MSSBamCFGenBindBaseModelAtomClass(
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

		String ret = recurseExpandBody( genDef );
		return( ret );
	}

	private String recurseExpandBody( ICFLibAnyObj genDef )
	{
		String		ret = null;
		final String S_ProcName = "recurseExpandBody";

		if( genDef instanceof ICFBamAtomObj ) {
			if( genDef instanceof ICFBamBlobDefObj ) {
				ret = "BlobDef";
			}
			else if( genDef instanceof ICFBamBoolDefObj ) {
				ret = "BoolDef";
			}
			else if (genDef instanceof ICFBamId16GenObj) {
				ret = "Id16Gen";
			}
			else if (genDef instanceof ICFBamId32GenObj) {
				ret = "Id32Gen";
			}
			else if (genDef instanceof ICFBamId64GenObj) {
				ret = "Id64Gen";
			}
			else if (genDef instanceof ICFBamUuidGenObj) {
				ret = "UuidGen";
			}
			else if (genDef instanceof ICFBamEnumDefObj) {
				ret = "EnumDef";
			}
			else if (genDef instanceof ICFBamUInt16DefObj) {
				ret = "UInt16Def";
			}
			else if (genDef instanceof ICFBamUInt32DefObj) {
				ret = "UInt32Def";
			}
			else if (genDef instanceof ICFBamUInt64DefObj) {
				ret = "UInt64Def";
			}
			else if (genDef instanceof ICFBamInt16DefObj) {
				ret = "Int16Def";
			}
			else if( genDef instanceof ICFBamInt32DefObj ) {
				ret = "Int32Def";
			}
			else if( genDef instanceof ICFBamInt64DefObj ) {
				ret = "Int64Def";
			}
			else if( genDef instanceof ICFBamFloatDefObj ) {
				ret = "FloatDef";
			}
			else if( genDef instanceof ICFBamDoubleDefObj ) {
				ret = "DoubleDef";
			}
			else if( genDef instanceof ICFBamNumberDefObj ) {
				ret = "NumberDef";
			}
			else if (genDef instanceof ICFBamNmTokensDefObj)
			{
				ret = "NmTokensDef";
			}
			else if (genDef instanceof ICFBamNmTokenDefObj) {
				ret = "NmTokenDef";
			}
			else if (genDef instanceof ICFBamTokenDefObj) {
				ret = "TokenDef";
			}
			else if (genDef instanceof ICFBamStringDefObj) {
				ret = "StringDef";
			}
			else if( genDef instanceof ICFBamTextDefObj ) {
				ret = "TextDef";
			}
			else if( genDef instanceof ICFBamDateDefObj ) {
				ret = "DateDef";
			}
			else if( genDef instanceof ICFBamTimeDefObj ) {
				ret = "TimeDef";
			}
			else if( genDef instanceof ICFBamTimestampDefObj ) {
				ret = "TimestampDef";
			}
			else if (genDef instanceof ICFBamTZDateDefObj) {
				ret = "TZDateDef";
			}
			else if (genDef instanceof ICFBamTZTimeDefObj) {
				ret = "TZTimeDef";
			}
			else if (genDef instanceof ICFBamTZTimestampDefObj) {
				ret = "TZTimestampDef";
			}
			else if( genDef instanceof ICFBamUuidDefObj ) {
				ret = "UuidDef";
			}
			else
			{
				throw new RuntimeException( S_ProcName + "Instance of "
					+ genDef.getClass().getSimpleName()
					+ " is not recognized");
			}
		}
		else if( genDef instanceof ICFBamTableColObj ) {
			ICFLibAnyObj dataDef = ((ICFBamTableColObj)genDef).getRequiredParentDataType();
			if (dataDef == null)
			{
				throw new RuntimeException(S_ProcName + "TableColDef.getLookupData() is null");
			}
			ret = recurseExpandBody( dataDef );
		}
		else if( genDef instanceof ICFBamIndexColObj ) {
			ICFLibAnyObj dataDef = ((ICFBamIndexColObj)genDef).getRequiredLookupColumn();
			if (dataDef == null)
			{
				throw new RuntimeException(S_ProcName + "IndexColDef.getRequiredLookupColumn() is null");
			}
			ret = recurseExpandBody(dataDef);
		}
		else {
			throw new RuntimeException(S_ProcName + "Instance of "
				+ genDef.getClass().getSimpleName()
				+ " does not derive from a supported definition class"  );
		}

		return( ret );
	}	
}
