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

import java.util.*;

import org.msscf.msscf.v2_13.cflib.CFLib.*;
import org.msscf.msscf.v2_13.cfcore.MssCF.*;
import org.msscf.msscf.v2_13.cfbam.CFBam.*;
import org.msscf.msscf.v2_13.cfbam.CFBamObj.*;

public class MSSBamCFGenIterateComponentCandidates
	extends MssCFGenIteratorObj
{
	private static final long serialVersionUID = 1L;

	public MSSBamCFGenIterateComponentCandidates() {
		super();
	}

	public MSSBamCFGenIterateComponentCandidates(
		MSSBamCFEngine argSchema,
		String toolset,
		String scopeDefClassName,
		String genDefClassName,
		String itemName)
	{
		super( argSchema, toolset, scopeDefClassName, genDefClassName, itemName, "Table");
	}

	public static boolean derivesFrom( ICFBamTableObj obj, ICFBamTableObj target ) {
		if( obj == target ) {
			return( true );
		}

		ICFBamRelationObj superClassRelation = MSSBamCFTableObj.getSuperClassRelation( obj );
		ICFBamTableObj cur;
		while( superClassRelation != null ) {
			cur = superClassRelation.getRequiredLookupToTable();
			if( cur == target ) {
				return( true );
			}
			superClassRelation = MSSBamCFTableObj.getSuperClassRelation( cur );
		}

		return( false );
	}

	public static boolean canBeContainedBy( ICFBamTableObj container, ICFBamTableObj component ) {
		if( ! component.getRequiredIsInstantiable() ) {
			return( false );
		}

		ICFBamRelationObj containerRelation = MSSBamCFTableObj.getInheritedContainerRelation( component );
		if( containerRelation == null ) {
			return( false );
		}

		ICFBamTableObj containedBy = containerRelation.getRequiredLookupToTable();

		boolean ret = derivesFrom( container, containedBy );
		return( ret );
	}

	public static void addAndWalkSubClasses( List<ICFLibAnyObj> list,
		ICFBamTableObj startTable,
		ICFBamTableObj containedBy )
	{
		if( canBeContainedBy( containedBy, startTable ) ) {
			list.add( startTable );
		}

		ListIterator<ICFBamRelationObj> subClassRelations = MSSBamCFTableObj.getSubClassRelations( startTable ).listIterator();
		ICFBamRelationObj subClassRelation = null;
		while( subClassRelations.hasNext() ) {
			subClassRelation = subClassRelations.next();
			// Note that the sub-class relations are reverse relations, so the meaning of
			// from and to are reversed.  The relation specifies from the subclass to the
			// superclass.
			addAndWalkSubClasses( list, subClassRelation.getRequiredContainerFromTable(), containedBy );
		}
	}

	public ListIterator<ICFLibAnyObj> enumerateDetails( MssCFGenContext genContext)
	{
		ICFLibAnyObj genDef;
		final String S_ProcName = "enumerateDetails";

		if( genContext == null ) {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				1,
				"genContext" );
		}

		genDef = genContext.getGenDef();
		if( genDef == null ) {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				0,
				"genContext.GenDef" );
		}

		if (!(genDef instanceof ICFBamRelationObj)) {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"genContext.GenDef",
				genDef,
				"ICFBamRelationObj" );
		}

		ICFBamRelationObj relationDef = (ICFBamRelationObj)genDef;
		ICFBamTableObj containedBy = relationDef.getRequiredContainerFromTable();
		ICFBamTableObj startTable = relationDef.getRequiredLookupToTable();
		List<ICFLibAnyObj> list = new LinkedList<ICFLibAnyObj>();
		addAndWalkSubClasses( list, startTable, containedBy );

		return (list.listIterator());
	}
}
