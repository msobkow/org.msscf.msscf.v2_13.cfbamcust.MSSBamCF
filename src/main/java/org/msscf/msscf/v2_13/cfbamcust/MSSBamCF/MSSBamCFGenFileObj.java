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

import org.msscf.msscf.v2_13.cflib.CFLib.*;
import org.msscf.msscf.v2_13.cfcore.MssCF.*;
import org.msscf.msscf.v2_13.cfsec.CFSecObj.*;
import org.msscf.msscf.v2_13.cfint.CFIntObj.*;
import org.msscf.msscf.v2_13.cfbam.CFBamObj.*;

public class MSSBamCFGenFileObj extends MssCFGenFileObj {

	public MSSBamCFGenFileObj() {
		super();
	}

	public MSSBamCFGenFileObj(MssCFEngine engine) {
		super( engine );
	}

	public String evaluateProjectDirName( MssCFGenContext genContext, ICFLibAnyObj genDef ) 	{
		final String S_ProcName = "MSSBamCFGenFileObj.evaluateProjectDirName() ";

		ICFLibAnyObj project;
		ICFLibAnyObj topproject;
		ICFLibAnyObj subproject;
		ICFLibAnyObj version;
		String projectDirName;
		ICFBamSchemaDefObj manufacturingSchema = ((MSSBamCFGenContext)genContext).getManufacturingSchema();
		ICFLibAnyObj anyObj = null;
		if( manufacturingSchema != null ) {
			anyObj = manufacturingSchema;
		}
		else {
			anyObj = (ICFLibAnyObj)genDef;
		}

	    project = MSSBamCFAnyObj.getProject(anyObj);
	    if (project == null) {
			topproject = null;
			subproject = null;
	        throw new RuntimeException(S_ProcName + "Could not get Project for generating GenFile( \""
	            + getRequiredLookupToolSet().getRequiredName()
	            + "\", \""
	            + ((getOptionalLookupScopeDef() != null)
	                ? getOptionalLookupScopeDef().getRequiredName()
	                : "")
	            + "\", \""
	            + getRequiredLookupGenDef().getRequiredName()
	            + "\", \""
	            + getRequiredName()
	            + "\" )");
	    }
		else {
			if( project instanceof ICFIntSubProjectObj ) {
				subproject = (ICFIntSubProjectObj)project;
				topproject = (ICFIntTopProjectObj)(subproject.getObjScope());
			}
			else if( project instanceof ICFIntTopProjectObj ) {
				topproject = (ICFIntTopProjectObj)project;
				subproject = null;
			}
			else {
				topproject = null;
				subproject = null;
			}
		}

		if( topproject == null ) {
	        throw new RuntimeException(S_ProcName + "Could not determine TopProject for generating GenFile( \""
	            + getRequiredLookupToolSet().getRequiredName()
	            + "\", \""
	            + ((getOptionalLookupScopeDef() != null)
	                ? getOptionalLookupScopeDef().getRequiredName()
	                : "")
	            + "\", \""
	            + getRequiredLookupGenDef().getRequiredName()
	            + "\", \""
	            + getRequiredName()
	            + "\" )");
		}

		if( subproject == null ) {
	        throw new RuntimeException(S_ProcName + "Could not determine SubProject for generating GenFile( \""
	            + getRequiredLookupToolSet().getRequiredName()
	            + "\", \""
	            + ((getOptionalLookupScopeDef() != null)
	                ? getOptionalLookupScopeDef().getRequiredName()
	                : "")
	            + "\", \""
	            + getRequiredLookupGenDef().getRequiredName()
	            + "\", \""
	            + getRequiredName()
	            + "\" )");
		}

	    version = MSSBamCFAnyObj.getVersionLeaf(anyObj);

		ICFIntMajorVersionObj majorVersion;
		ICFIntMinorVersionObj minorVersion;

		if( version instanceof ICFIntMajorVersionObj ) {
			majorVersion = (ICFIntMajorVersionObj)version;
			minorVersion = null;
		}
		else if( version instanceof ICFIntMinorVersionObj ) {
			minorVersion = (ICFIntMinorVersionObj)version;
			majorVersion = (ICFIntMajorVersionObj)(version.getObjScope());
		}
		else {
			minorVersion = null;
			majorVersion = null;
		}

		if( majorVersion == null ) {
	        throw new RuntimeException(S_ProcName + "Could not determine MajorVersion for generating GenFile( \""
	            + getRequiredLookupToolSet().getRequiredName()
	            + "\", \""
	            + ((getOptionalLookupScopeDef() != null)
	                ? getOptionalLookupScopeDef().getRequiredName()
	                : "")
	            + "\", \""
	            + getRequiredLookupGenDef().getRequiredName()
	            + "\", \""
	            + getRequiredName()
	            + "\" )");
		}

		if( minorVersion == null ) {
	        throw new RuntimeException(S_ProcName + "Could not determine MinorVersion for generating GenFile( \""
	            + getRequiredLookupToolSet().getRequiredName()
	            + "\", \""
	            + ((getOptionalLookupScopeDef() != null)
	                ? getOptionalLookupScopeDef().getRequiredName()
	                : "")
	            + "\", \""
	            + getRequiredLookupGenDef().getRequiredName()
	            + "\", \""
	            + getRequiredName()
	            + "\" )");
		}

//		projectDirName = subproject.getObjName().toLowerCase() + "_" + majorVersion.getObjName() + "_" + minorVersion.getObjName();
		projectDirName = "";//subproject.getObjName().toLowerCase();// + "_" + majorVersion.getObjName() + "_" + minorVersion.getObjName();

	    return( projectDirName );
	}
}
