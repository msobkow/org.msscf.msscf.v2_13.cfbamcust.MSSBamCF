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

public class MSSBamCFTableColObj
{
	public MSSBamCFTableColObj() {
	}

    public static ICFBamRelationObj getIdGenResolverRelation(ICFBamTableColObj tableColDef)
    {
        final String S_ProcName = "RMTableColDefObj.getIdGenResolverRelation() ";

	    // Determine the target dispenser table

	    ICFBamTableObj dispenserDef = null;
        ICFBamValueObj valueDef = tableColDef.getRequiredParentDataType();
        if( valueDef instanceof ICFBamId16GenObj ) {
	        dispenserDef = ((ICFBamId16GenObj)valueDef).getOptionalLookupDispenser();
        }
        else if( valueDef instanceof ICFBamId32GenObj ) {
	        dispenserDef = ((ICFBamId32GenObj)valueDef).getOptionalLookupDispenser();
        }
        else if( valueDef instanceof ICFBamId64GenObj ) {
	        dispenserDef = ((ICFBamId64GenObj)valueDef).getOptionalLookupDispenser();
        }

        // If there is no dispenser table, we have nothing to resolve
        if( dispenserDef == null ) {
            throw new RuntimeException(S_ProcName + "No dispenser specified for tableColDef \"" + tableColDef.getRequiredName() + "\"!");
        }

        ICFBamTableObj tableDef = tableColDef.getRequiredContainerTable();
        ICFBamRelationObj superClassRelation = MSSBamCFTableObj.getSuperClassRelation( tableDef );

        Iterator<ICFBamRelationObj> ownerRelations;
        ICFBamRelationObj ownerRelation;
        ICFBamTableObj ownedByTable;
        Boolean ownerDerivesFromDispenser;

        /*
         *	Build a list of candidate relations which reference a ToTableDef that
         *	derives from the dispenser table definition.
         */
        List<ICFBamRelationObj> candidates = new ArrayList<ICFBamRelationObj>();
        while( tableDef != null ) {
            ownerRelations = MSSBamCFTableObj.getContainerOwnerRelations(tableDef).iterator();
	        while( ownerRelations.hasNext() ) {
		        ownerRelation = ownerRelations.next();
		        ownedByTable = ownerRelation.getRequiredLookupToIndex().getRequiredContainerTable();
		        ownerDerivesFromDispenser = false;
		        while( ( ! ownerDerivesFromDispenser ) && ( ownedByTable != null ) ) {
			        if( ownedByTable == dispenserDef ) {
				        ownerDerivesFromDispenser = true;
			        }
			        else {
				        superClassRelation = MSSBamCFTableObj.getSuperClassRelation( ownedByTable );
				        if( superClassRelation != null ) {
					        ownedByTable = superClassRelation.getRequiredLookupToIndex().getRequiredContainerTable();
				        }
				        else {
					        ownedByTable = null;
				        }
			        }
		        }
		        if( ownerDerivesFromDispenser ) {
			        candidates.add( ownerRelation );
		        }
	        }

	        superClassRelation = MSSBamCFTableObj.getSuperClassRelation( tableDef );
	        if( superClassRelation != null ) {
		        tableDef = superClassRelation.getRequiredLookupToIndex().getRequiredContainerTable();
	        }
	        else {
		        tableDef = null;
	        }
        }

        if (candidates.size() == 0)
        {
            throw new RuntimeException(S_ProcName + "Could not find any candidate owner relations");
        }

        int idx = candidates.size() - 1;
        ICFBamRelationObj idGenResolverRelation = candidates.get(idx);

        // If we don't find one at all, the engine will complain in due time

        return (idGenResolverRelation);
    }
}
