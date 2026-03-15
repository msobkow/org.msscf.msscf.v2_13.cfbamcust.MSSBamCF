/*
 *	MSS Code Factory CFBam 2.13 CustMSSBamCF
 *
 *	Copyright (c) 2016-2026 Mark Stephen Sobkow
 *	
 *	Mark's Code Fractal CFBam 2.13 Business Application Model
 *	
 *	Copyright 2016-2026 Mark Stephen Sobkow
 *	
 *	This file is part of Mark's Code Fractal CFBam.
 *	
 *	Mark's Code Fractal CFBam is available under dual commercial license from
 *	Mark Stephen Sobkow, or under the terms of the GNU General Public License,
 *	Version 3 or later with classpath and static linking exceptions.
 *	
 *	As a special exception, Mark Sobkow gives you permission to link this library
 *	with independent modules to produce an executable, provided that none of them
 *	conflict with the intent of the GPLv3; that is, you are not allowed to invoke
 *	the methods of this library from non-GPLv3-compatibly licensed code. You may not
 *	implement an LPGLv3 "wedge" to try to bypass this restriction. That said, code which
 *	does not rely on this library is free to specify whatever license its authors decide
 *	to use. Mark Sobkow specifically rejects the infectious nature of the GPLv3, and
 *	considers the mere act of including GPLv3 modules in an executable to be perfectly
 *	reasonable given tools like modern Java's single-jar deployment options.
 *	
 *	Mark's Code Fractal CFBam is free software: you can redistribute it and/or
 *	modify it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *	
 *	Mark's Code Fractal CFBam is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *	
 *	You should have received a copy of the GNU General Public License
 *	along with Mark's Code Fractal CFBam.  If not, see <https://www.gnu.org/licenses/>.
 *	
 *	If you wish to modify and use this code without publishing your changes,
 *	or integrate it with proprietary code, please contact Mark Stephen Sobkow
 *	for a commercial license at mark.sobkow@gmail.com
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
