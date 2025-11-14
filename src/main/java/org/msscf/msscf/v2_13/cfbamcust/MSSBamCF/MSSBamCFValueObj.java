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

import java.util.*;

import org.msscf.msscf.v2_13.cfbam.CFBam.*;
import org.msscf.msscf.v2_13.cfbam.CFBamObj.*;
import org.msscf.msscf.v2_13.cflib.CFLib.CFLibNullArgumentException;
import org.msscf.msscf.v2_13.cflib.CFLib.CFLibUnsupportedClassException;

public class MSSBamCFValueObj
{
	public MSSBamCFValueObj() {
	}

    public static List<ICFBamRelationObj> getAffectedRelations(ICFBamValueObj valueDef)
    {
        List<ICFBamRelationObj> list = new LinkedList<ICFBamRelationObj>();
        ICFBamScopeObj container = valueDef.getRequiredContainerScope();
        ICFBamTableObj tableDef = null;
        if( container == null ) {
            throw new RuntimeException("ValueDefObj.getAffectedRelations() RequiredContainerScope has no value");
        }
        else if( container instanceof ICFBamTableObj ) {
        	tableDef = (ICFBamTableObj)container;
        }
        else {
        	throw new RuntimeException( "ValueDefObj.getAffectedRelations() RequiredContainerScope is not an ICFBamTableObj");
        }
        Iterator<ICFBamRelationObj> cursorRelationDef = tableDef.getOptionalComponentsRelation().iterator();
        while (cursorRelationDef.hasNext())
        {
            ICFBamRelationObj relationDef = cursorRelationDef.next();
            Iterator<ICFBamRelationColObj> cursorRelationColDef = relationDef.getOptionalComponentsColumns().iterator();
            while (cursorRelationColDef.hasNext())
            {
                ICFBamRelationColObj relationColDef = cursorRelationColDef.next();
                ICFBamIndexColObj indexCol = relationColDef.getRequiredLookupFromCol();
                if (indexCol.getRequiredLookupColumn().equals(valueDef))
                {
                    list.add(relationDef);
                }
            }
        }
        return (list);
    }

    public static List<ICFBamRelationObj> getColumnInComponentsRelations(ICFBamValueObj valueDef)
    {
        final String S_ProcName = "getColumnInComponentsRelations";

        ICFBamScopeObj container = valueDef.getRequiredContainerScope();
        ICFBamTableObj tableDef = null;
        if( container == null ) {
            throw new CFLibNullArgumentException(MSSBamCFValueObj.class, S_ProcName, 0, "valueDef.getRequiredContainerScope()");
        }
        else if( container instanceof ICFBamTableObj ) {
        	tableDef = (ICFBamTableObj)container;
        }
        else {
            throw new CFLibUnsupportedClassException(MSSBamCFValueObj.class, S_ProcName, "valueDef.getRequiredContainerScope()", container, "ICFBamTableObj");
        }

        ICFBamRelationColObj relationCol;
        ICFBamRelationObj relation;
        ICFBamIndexColObj indexCol;
        Iterator<ICFBamRelationColObj> relationColumns;
        List<ICFBamRelationObj> list = new LinkedList<ICFBamRelationObj>();
        Iterator<ICFBamRelationObj> relations = tableDef.getOptionalComponentsRelation().iterator();

        while( relations.hasNext() ) {
        	relation = relations.next();
        	if( relation.getRequiredRelationType() == ICFBamSchema.RelationTypeEnum.Components ) {
                relationColumns = relation.getOptionalComponentsColumns().iterator();
                while (relationColumns.hasNext()) {
                    relationCol = relationColumns.next();
                    indexCol = relationCol.getRequiredLookupFromCol();
                    if( valueDef == indexCol.getRequiredLookupColumn() ) {
                        list.add(relation);
                        break;
                    }
                }
        	}
        }

        return( list );
    }
}
