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

import java.math.*;
import java.text.*;
import java.util.*;

import org.msscf.msscf.v2_13.cflib.CFLib.*;
import org.msscf.msscf.v2_13.cfbam.CFBam.*;
import org.msscf.msscf.v2_13.cfbam.CFBamObj.*;

public class MSSBamCFAnyObj
{
	public MSSBamCFAnyObj() {
	}

    public static ICFBamSchemaDefObj getDefSchema(ICFLibAnyObj anyDef)
    {
		final String S_ProcName = "MSSBamCFAnyObj.getDefSchema";
    	ICFLibAnyObj curDef = anyDef;
		ICFBamSchemaDefObj defSchema;
		if( curDef == null ) {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"curDef" );
		}
		else if( curDef instanceof ICFBamValueObj ) {
			defSchema = ((ICFBamValueObj)curDef).getOptionalLookupDefSchema();
		}
		else if( curDef instanceof ICFBamSchemaDefObj ) {
			defSchema = (ICFBamSchemaDefObj)curDef;
		}
		else if( curDef instanceof ICFBamChainObj ) {
			defSchema = ((ICFBamChainObj)curDef).getOptionalLookupDefSchema();
		}
		else if( curDef instanceof ICFBamDelDepObj ) {
			defSchema = ((ICFBamDelDepObj)curDef).getOptionalLookupDefSchema();
		}
		else if( curDef instanceof ICFBamEnumTagObj ) {
			defSchema = ((ICFBamEnumTagObj)curDef).getOptionalLookupDefSchema();
		}
		else if( curDef instanceof ICFBamIndexColObj ) {
			defSchema = ((ICFBamIndexColObj)curDef).getOptionalLookupDefSchema();
		}
		else if( curDef instanceof ICFBamIndexObj ) {
			defSchema = ((ICFBamIndexObj)curDef).getOptionalLookupDefSchema();
		}
		else if( curDef instanceof ICFBamParamObj ) {
			defSchema = ((ICFBamParamObj)curDef).getOptionalLookupDefSchema();
		}
		else if( curDef instanceof ICFBamPopDepObj ) {
			defSchema = ((ICFBamPopDepObj)curDef).getOptionalLookupDefSchema();
		}
		else if( curDef instanceof ICFBamRelationColObj ) {
			defSchema = ((ICFBamRelationColObj)curDef).getOptionalLookupDefSchema();
		}
		else if( curDef instanceof ICFBamRelationObj ) {
			defSchema = ((ICFBamRelationObj)curDef).getOptionalLookupDefSchema();
		}
		else if( curDef instanceof ICFBamServerMethodObj ) {
			defSchema = ((ICFBamServerMethodObj)curDef).getOptionalLookupDefSchema();
		}
		else if( curDef instanceof ICFBamTableObj ) {
			defSchema = ((ICFBamTableObj)curDef).getOptionalLookupDefSchema();
		}
		else {
			defSchema = null;
		}

    	ICFBamSchemaDefObj topSchema = null;
    	ICFBamSchemaDefObj firstSchemaEncountered = null;
    	ICFBamSchemaDefObj lastInterruptedSchema = null;
		if( defSchema != null ) {
			firstSchemaEncountered = defSchema;
			curDef = defSchema;
		}
		else {
    		for( curDef = anyDef; curDef != null; curDef = curDef.getObjScope() ) {
    			if( curDef instanceof ICFBamSchemaDefObj ) {
    				if( lastInterruptedSchema == null ) {
    					firstSchemaEncountered = (ICFBamSchemaDefObj)curDef;
    				}
    				topSchema = (ICFBamSchemaDefObj)curDef;
    				lastInterruptedSchema = topSchema;
    			}
    			else {
    				lastInterruptedSchema = null;
    			}
    		}

    		if( topSchema != null ) {
    			defSchema = firstSchemaEncountered;
				curDef = defSchema;
    		}
		}
		return( defSchema );
	}

    public static ICFLibAnyObj getDefProject(ICFLibAnyObj anyDef)
    {
		final String S_ProcName = "MSSBamCFAnyObj.getDefProject";
    	ICFLibAnyObj curDef = anyDef;
    	ICFBamSchemaDefObj defSchema;
		if( curDef != null ) {
    		defSchema = getDefSchema( curDef );
		}
		else {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"curDef" );
		}

   		if( defSchema != null ) {
			curDef = defSchema;
   		}

        for ( ; curDef != null; curDef = curDef.getObjScope() ) {
			if (curDef instanceof ICFBamTopProjectObj) {
				return (curDef);
			}
			else if (curDef instanceof ICFBamSubProjectObj) {
				return (curDef);
			}
			else if (curDef instanceof ICFBamTenantObj) {
				return (null);
			}
		}

		return( null );
	}

    public static ICFLibAnyObj getProject(ICFLibAnyObj anyDef)
    {
    	ICFBamSchemaDefObj topSchema = null;
    	ICFBamSchemaDefObj firstSchemaEncountered = null;
    	ICFBamSchemaDefObj lastInterruptedSchema = null;
    	ICFLibAnyObj curDef;
    	for( curDef = anyDef; curDef != null; curDef = curDef.getObjScope() ) {
    		if( curDef instanceof ICFBamSchemaDefObj ) {
    			if( lastInterruptedSchema == null ) {
    				firstSchemaEncountered = (ICFBamSchemaDefObj)curDef;
    			}
    			topSchema = (ICFBamSchemaDefObj)curDef;
    			lastInterruptedSchema = topSchema;
    		}
    		else {
    			lastInterruptedSchema = null;
    		}
    	}

    	if( topSchema != null ) {
    		curDef = firstSchemaEncountered;
    	}
    	else {
    		curDef = anyDef;
    	}

        for ( ; curDef != null; curDef = curDef.getObjScope() ) {
            if( curDef instanceof ICFBamTopProjectObj ) {
                return( curDef );
            }
            else if( curDef instanceof ICFBamSubProjectObj ) {
                return( curDef );
            }
            else if( curDef instanceof ICFBamTenantObj ) {
                return (null);
            }
        }
        return (null);
    }

    public static ICFBamSchemaDefObj getTopSchema(ICFLibAnyObj anyDef)
    {
    	ICFBamSchemaDefObj topSchema = null;
    	ICFLibAnyObj curDef;
    	for( curDef = anyDef; curDef != null; curDef = curDef.getObjScope() ) {
    		if( curDef instanceof ICFBamSchemaDefObj ) {
    			topSchema = (ICFBamSchemaDefObj)curDef;
    		}
    	}
        return (topSchema);
    }

    public static ICFBamTopDomainObj getTopDomain(ICFLibAnyObj anyDef)
    {
		final String S_ProcName = "MSSBamCFAnyObj.getTopDomain";
    	ICFLibAnyObj curDef = anyDef;
    	ICFBamSchemaDefObj defSchema;
		if( curDef != null ) {
    		defSchema = getDefSchema( curDef );
		}
		else {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"curDef" );
		}

   		if( defSchema != null ) {
			curDef = defSchema;
   		}

    	for ( ; curDef != null; curDef = curDef.getObjScope())
        {
            if (curDef instanceof ICFBamTopDomainObj)
            {
                return ((ICFBamTopDomainObj)curDef);
            }
            else if (curDef instanceof ICFBamTenantObj)
            {
                return (null);
            }
        }
        return (null);
    }

    public static ICFBamSchemaDefObj getSchema(ICFLibAnyObj anyDef)
    {
    	for ( ICFLibAnyObj curDef = anyDef; curDef != null; curDef = curDef.getObjScope())
        {
            if (curDef instanceof ICFBamSchemaDefObj)
            {
                return ((ICFBamSchemaDefObj)curDef);
            }
            else if (curDef instanceof ICFBamTenantObj)
            {
                return (null);
            }
        }
        return (null);
    }

    public static ICFLibAnyObj getVersionLeaf(ICFLibAnyObj anyDef)
    {
		final String S_ProcName = "MSSBamCFAnyObj.getVersionLeaf";
    	ICFLibAnyObj curDef = anyDef;
    	ICFBamSchemaDefObj defSchema;
		if( curDef != null ) {
			if( curDef instanceof ICFBamSchemaDefObj ) {
				defSchema = (ICFBamSchemaDefObj)curDef;
			}
			else {
				defSchema = getDefSchema( curDef );
			}
		}
		else {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"curDef" );
		}

   		if( defSchema != null ) {
			curDef = defSchema;
   		}

    	for ( ; curDef != null; curDef = curDef.getObjScope() ) {
            if (curDef instanceof ICFBamMinorVersionObj) {
                return (curDef);
            }
            else if (curDef instanceof ICFBamMajorVersionObj) {
                return (curDef);
            }
            else if (curDef instanceof ICFBamTopProjectObj) {
                return (null);
            }
            else if (curDef instanceof ICFBamSubProjectObj) {
                return (null);
            }
            else if (curDef instanceof ICFBamTenantObj) {
                return (null);
            }
        }
        return (null);
    }

    public static String getVersionString(ICFLibAnyObj anyDef)
    {
		final String S_ProcName = "MSSBamCFAnyObj.getVersionString";
    	ICFLibAnyObj curDef = anyDef;
    	ICFBamSchemaDefObj defSchema;
		if( curDef != null ) {
			if( curDef instanceof ICFBamSchemaDefObj ) {
				defSchema = (ICFBamSchemaDefObj)curDef;
			}
			else {
				defSchema = getDefSchema( curDef );
			}
		}
		else {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"curDef" );
		}

   		if( defSchema != null ) {
			curDef = defSchema;
   		}

    	ICFLibAnyObj scopeDef;
        ICFLibAnyObj versionLeafDef = getVersionLeaf(curDef);
        List<String> invertedNodeNames = new ArrayList<String>();
        while (versionLeafDef != null) {
            invertedNodeNames.add(versionLeafDef.getObjName());
            scopeDef = versionLeafDef.getObjScope();
            if (scopeDef == null) {
                versionLeafDef = null;
            }
            else if (scopeDef instanceof ICFBamMinorVersionObj) {
                versionLeafDef = (ICFBamMinorVersionObj)scopeDef;
            }
            else if (scopeDef instanceof ICFBamMajorVersionObj) {
                versionLeafDef = (ICFBamMajorVersionObj)scopeDef;
            }
            else {
                versionLeafDef = null;
            }
        }

        String ret = "";
        for (int idx = invertedNodeNames.size() - 1; idx >= 0; idx--) {
            if (ret.length() == 0) {
                ret = invertedNodeNames.get(idx);
            }
            else {
                ret = ret + "-" + invertedNodeNames.get(idx);
            }
        }

        return( ret );
    }

    public static String getPackedVersionString(ICFLibAnyObj anyDef)
    {
		final String S_ProcName = "MSSBamCFAnyObj.getPackedVersionString";
    	ICFLibAnyObj curDef = anyDef;
    	ICFBamSchemaDefObj defSchema;
		if( curDef != null ) {
    		defSchema = getDefSchema( curDef );
		}
		else {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"curDef" );
		}

   		if( defSchema != null ) {
			curDef = defSchema;
   		}

    	ICFLibAnyObj scopeDef;
        ICFLibAnyObj versionLeafDef = getVersionLeaf(curDef);
        List<String> invertedNodeNames = new ArrayList<String>();
        while (versionLeafDef != null)
        {
            invertedNodeNames.add(versionLeafDef.getObjName() );
            scopeDef = versionLeafDef.getObjScope();
            if (scopeDef == null) {
                versionLeafDef = null;
            }
            else if (scopeDef instanceof ICFBamMinorVersionObj) {
                versionLeafDef = (ICFBamMinorVersionObj)scopeDef;
            }
            else if (scopeDef instanceof ICFBamMajorVersionObj) {
                versionLeafDef = (ICFBamMajorVersionObj)scopeDef;
            }
            else {
                versionLeafDef = null;
            }
        }
        String ret = "";
        for (int idx = invertedNodeNames.size() - 1; idx >= 0; idx--) {
            if (ret.length() == 0) {
                ret = invertedNodeNames.get(idx);
            }
            else {
                ret = ret + invertedNodeNames.get(idx);
            }
        }
        return( ret );
    }

    public static String getFullName(ICFLibAnyObj anyDef)
    {
        String ret = anyDef.getObjName();
        ICFLibAnyObj scopeDef = anyDef.getObjScope();
        while ((scopeDef != null) && ! (scopeDef instanceof ICFBamTenantObj))
        {
            ret = scopeDef.getObjName() + "." + ret;
            scopeDef = scopeDef.getObjScope();
        }
        return (ret);
    }

    public static String getPackageName(ICFLibAnyObj anyDef)
    {
        ICFLibAnyObj pkg = getPackage(anyDef);
        String ret = pkg.getObjName();
        return (ret);
    }

    public static String getModelName(ICFLibAnyObj anyDef)
    {
        String ret = anyDef.getObjName();
        ICFLibAnyObj scopeDef = anyDef.getObjScope();
        while ((scopeDef != null) && !(scopeDef instanceof ICFBamTenantObj))
        {
            ret = scopeDef.getObjName() + "." + ret;
            scopeDef = scopeDef.getObjScope();
        }
        return (ret);
    }

    public static ICFLibAnyObj getDefPackage(ICFLibAnyObj anyDef)
    {
		final String S_ProcName = "MSSBamCFAnyObj.getDefPackage";
    	ICFLibAnyObj curDef = anyDef;
    	ICFBamSchemaDefObj defSchema;
		if( curDef != null ) {
    		defSchema = getDefSchema( curDef );
		}
		else {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"curDef" );
		}

   		if( defSchema != null ) {
			curDef = defSchema;
   		}

    	ICFLibAnyObj scopeDef;

        for (; curDef != null; curDef = curDef.getObjScope()) {
            if (curDef instanceof ICFBamTopProjectObj) {
                return (curDef);
            }
            else if (curDef instanceof ICFBamSubProjectObj) {
                return (curDef);
            }
            else if (curDef instanceof ICFBamTenantObj) {
                return (null);
            }
        }
        return (null);
    }

    public static ICFLibAnyObj getPackage(ICFLibAnyObj anyDef)
    {
    	ICFBamSchemaDefObj topSchema = null;
    	ICFBamSchemaDefObj firstSchemaEncountered = null;
    	ICFBamSchemaDefObj lastInterruptedSchema = null;
    	for( ICFLibAnyObj curDef = anyDef; curDef != null; curDef = curDef.getObjScope() ) {
    		if( curDef instanceof ICFBamSchemaDefObj ) {
    			if( lastInterruptedSchema == null ) {
    				firstSchemaEncountered = (ICFBamSchemaDefObj)curDef;
    			}
    			topSchema = (ICFBamSchemaDefObj)curDef;
    			lastInterruptedSchema = topSchema;
    		}
    		else {
    			lastInterruptedSchema = null;
    		}
    	}

    	ICFLibAnyObj curDef;
    	if( topSchema != null ) {
    		curDef = firstSchemaEncountered;
    	}
    	else {
    		curDef = anyDef;
    	}

        for (; curDef != null; curDef = curDef.getObjScope()) {
            if (curDef instanceof ICFBamTopProjectObj) {
                return (curDef);
            }
            else if (curDef instanceof ICFBamSubProjectObj) {
                return (curDef);
            }
            else if (curDef instanceof ICFBamTenantObj) {
                return (null);
            }
        }
        return (null);
    }

    public static ICFLibAnyObj getDefByModelName(
        ICFBamScopeObj modelScope,
        String modelName)
    {
        try
        {
            String thisName;
            String remainder;
            int idxDot = modelName.indexOf('.');
            if (idxDot > 0)
            {
                thisName = modelName.substring(0, idxDot);
                remainder = modelName.substring(idxDot + 1, modelName.length() - (idxDot + 1));
            }
            else
            {
                thisName = modelName;
                remainder = "";
            }

            ICFLibAnyObj resolved = modelScope.getNamedObject( thisName );
            if (resolved == null)
            {
                throw new RuntimeException("MSSBamCFAnyObj.getDefByModelName() Could not resolve \"" + thisName + "\" in scope \"" + getFullName(modelScope) + "\"");
            }

            if (remainder.length() > 0)
            {
                resolved = getDefByModelName((ICFBamScopeObj)resolved, remainder);
            }

            return (resolved);
        }
        catch (NullPointerException e)
        {
            System.err.append("MSSBamCFAnyObj.getDefByModelName() Caught NullPointerException\n");
            throw e;
        }
    }

    public static ICFLibAnyObj getDefByGlobalName(
        ICFBamScopeObj modelScope,
        String globalName)
    {
        final String S_ProcName = "MSSBamCFAnyObj.getDefByGlobalName() ";

        if ((globalName == null) || (globalName.length() == 0)) {
            throw new RuntimeException(S_ProcName + "Argument globalName is null or empty");
        }

        ICFBamScopeObj searchScope = modelScope;
        while ((searchScope != null)
            && (!(searchScope instanceof ICFBamTenantObj)))
        {
            searchScope = (ICFBamScopeObj)(searchScope.getObjScope());
        }
        if (searchScope == null) {
            throw new RuntimeException(S_ProcName + "Could not chain scope to a Tenant definition");
        }

        ICFLibAnyObj searchDef = searchScope;
        ICFLibAnyObj foundNext;
        String remainingName = globalName;
        String nextName;
        String nowRemaining;
        while ((remainingName != null) && (remainingName.length() > 0)) {
            int nextDot = remainingName.indexOf('.');
            if (nextDot > 0) {
                nextName = remainingName.substring(0, nextDot);
                nowRemaining = remainingName.substring(nextDot + 1);
            }
            else {
                nextName = remainingName;
                nowRemaining = "";
            }
        	foundNext = searchDef.getNamedObject( nextName );
            if (foundNext == null) {
            	throw new CFLibRuntimeException( MSSBamCFAnyObj.class,
            		S_ProcName,
            		"Could not resolve name part " + nextName + " of global name \"" + globalName + "\" in " + searchDef.getClass().getSimpleName() + " " + searchDef.getObjName() );
            }
            searchDef = foundNext;
            remainingName = nowRemaining;
        }
        return (searchDef);
    }
}
