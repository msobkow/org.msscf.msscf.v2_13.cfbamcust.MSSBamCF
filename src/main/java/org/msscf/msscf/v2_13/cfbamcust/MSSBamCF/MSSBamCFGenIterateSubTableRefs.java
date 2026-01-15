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

public class MSSBamCFGenIterateSubTableRefs
	extends MssCFGenIteratorObj
{
	private static final long serialVersionUID = 1L;

	public MSSBamCFGenIterateSubTableRefs() {
		super();
	}

	public MSSBamCFGenIterateSubTableRefs(
		MSSBamCFEngine argSchema,
		String toolset,
		String scopeDefClassName,
		String genDefClassName,
		String itemName)
	{
		super( argSchema, toolset, scopeDefClassName, genDefClassName, itemName, "SubTableRefs");
	}

	public ListIterator<ICFLibAnyObj> enumerateDetails( MssCFGenContext genContext)
	{
		ICFLibAnyObj genDef;
		final String S_ProcName = "enumerateDetails";

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

		if (!(genDef instanceof ICFBamTableObj)) {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"genContext.GenDef",
				genDef,
				"ICFBamTableObj" );
		}

		ICFBamTableObj tableObj = (ICFBamTableObj)genDef;
		String tableName = tableObj.getRequiredName();
		ICFBamSchemaDefObj schemaDefObj = (ICFBamSchemaDefObj)tableObj.getRequiredContainerSchemaDef();
		String schemaName = schemaDefObj.getRequiredName();
		Iterator<ICFBamSchemaRefObj> schemaRefIterator = schemaDefObj.getOptionalComponentsSchemaRefs().iterator();
		ICFBamSchemaRefObj schemaRefObj;
		ICFBamSchemaDefObj referencedSchemaObj;
		String referencedSchemaName;
		ICFLibAnyObj namedObj;
		LinkedList<ICFLibAnyObj> toBeIterated = new LinkedList<ICFLibAnyObj>();
		while( schemaRefIterator.hasNext() ) {
			schemaRefObj = schemaRefIterator.next();
			referencedSchemaObj = schemaRefObj.getOptionalLookupRefSchema();
			if( referencedSchemaObj == null ) {
				throw new CFLibNullArgumentException( getClass(),
					S_ProcName,
					0,
					"schemaRefObj.referencedSchemaObj" );
			}
			referencedSchemaName = referencedSchemaObj.getRequiredName();
			if( ! referencedSchemaName.equals( schemaName ) ) {
				namedObj = referencedSchemaObj.getNamedObject( tableName );
				if( namedObj != null ) {
					if( namedObj instanceof ICFBamTableObj ) {
						toBeIterated.add( namedObj );
					}
				}
			}
		}

		return( toBeIterated.listIterator());
	}
}
