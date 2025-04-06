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
        final String S_ProcName = "RMValueDefObj.getColumnInComponentsRelations() ";

        ICFBamScopeObj container = valueDef.getRequiredContainerScope();
        ICFBamTableObj tableDef = null;
        if( container == null ) {
            throw new RuntimeException("RMValueDefObj.getColumnInComponentsRelations() RequiredContainerVContainer has no value");
        }
        else if( container instanceof ICFBamTableObj ) {
        	tableDef = (ICFBamTableObj)container;
        }
        else {
        	throw new RuntimeException( "RMValueDefObj.getColumnInComponentsRelations() RequiredContainerVContainer is not an ICFBamTableObj");
        }

        ICFBamRelationColObj relationCol;
        ICFBamRelationObj relation;
        ICFBamIndexColObj indexCol;
        Iterator<ICFBamRelationColObj> relationColumns;
        String relationTypeName;
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
