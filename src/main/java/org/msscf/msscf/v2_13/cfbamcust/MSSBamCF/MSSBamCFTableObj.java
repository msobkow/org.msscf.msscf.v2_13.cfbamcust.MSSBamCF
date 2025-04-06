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

public class MSSBamCFTableObj
{
	public MSSBamCFTableObj() {
	}

    public static List<ICFBamId16GenObj> getId16Generators( ICFBamTableObj tableDef )
    {
        List<ICFBamId16GenObj> list = new LinkedList<ICFBamId16GenObj>();
        Iterator<ICFBamValueObj> childrenColumns = tableDef.getOptionalComponentsColumns().iterator();
        while (childrenColumns.hasNext())
        {
            ICFBamAtomObj atomDef = null;
            ICFLibAnyObj colDef = childrenColumns.next();
            if (colDef instanceof ICFBamTableColObj)
            {
                atomDef = (ICFBamAtomObj)((ICFBamTableColObj)colDef).getRequiredParentDataType();
            }
            else if (colDef instanceof ICFBamAtomObj)
            {
                atomDef = (ICFBamAtomObj)colDef;
            }
            if (atomDef != null)
            {
                if (atomDef instanceof ICFBamId16GenObj)
                {
                    list.add((ICFBamId16GenObj)atomDef);
                }
            }
        }
        return (list);
    }

    public static List<ICFBamId32GenObj> getId32Generators(ICFBamTableObj tableDef)
    {
        List<ICFBamId32GenObj> list = new LinkedList<ICFBamId32GenObj>();
        Iterator<ICFBamValueObj> childrenColumns = tableDef.getOptionalComponentsColumns().iterator();
        while (childrenColumns.hasNext())
        {
            ICFBamAtomObj atomDef = null;
            ICFLibAnyObj colDef = childrenColumns.next();
            if (colDef instanceof ICFBamTableColObj)
            {
                atomDef = (ICFBamAtomObj)((ICFBamTableColObj)colDef).getRequiredParentDataType();
            }
            else if (colDef instanceof ICFBamAtomObj)
            {
                atomDef = (ICFBamAtomObj)colDef;
            }
            if (atomDef != null)
            {
                if (atomDef instanceof ICFBamId32GenObj)
                {
                    list.add((ICFBamId32GenObj)atomDef);
                }
            }
        }
        return (list);
    }

    public static List<ICFBamId64GenObj> getId64Generators(ICFBamTableObj tableDef)
    {
        List<ICFBamId64GenObj> list = new LinkedList<ICFBamId64GenObj>();
        Iterator<ICFBamValueObj> childrenColumns = tableDef.getOptionalComponentsColumns().iterator();
        while (childrenColumns.hasNext())
        {
            ICFBamAtomObj atomDef = null;
            ICFLibAnyObj colDef = childrenColumns.next();
            if (colDef instanceof ICFBamTableColObj)
            {
                atomDef = (ICFBamAtomObj)((ICFBamTableColObj)colDef).getRequiredParentDataType();
            }
            else if (colDef instanceof ICFBamAtomObj)
            {
                atomDef = (ICFBamAtomObj)colDef;
            }
            if (atomDef != null)
            {
                if (atomDef instanceof ICFBamId64GenObj)
                {
                    list.add((ICFBamId64GenObj)atomDef);
                }
            }
        }
        return (list);
    }

    public static List<ICFBamId16GenObj> getDispensedId16Generators(ICFBamTableObj tableDef)
    {
        List<ICFBamId16GenObj> list = new LinkedList<ICFBamId16GenObj>();
        Iterator<ICFBamId16GenObj> dispensed = tableDef.getSchema().getId16GenTableObj().readId16GenByDispIdx(tableDef.getRequiredTenantId(), tableDef.getRequiredId()).iterator();
        while (dispensed.hasNext())
        {
            list.add(dispensed.next());
        }
        return (list);
    }

    public static List<ICFBamId32GenObj> getDispensedId32Generators(ICFBamTableObj tableDef)
    {
        List<ICFBamId32GenObj> list = new LinkedList<ICFBamId32GenObj>();
        Iterator<ICFBamId32GenObj> dispensed = tableDef.getSchema().getId32GenTableObj().readId32GenByDispIdx(tableDef.getRequiredTenantId(), tableDef.getRequiredId()).iterator();
        while (dispensed.hasNext())
        {
            list.add(dispensed.next());
        }
        return (list);
    }

    public static List<ICFBamId64GenObj> getDispensedId64Generators(ICFBamTableObj tableDef)
    {
        List<ICFBamId64GenObj> list = new LinkedList<ICFBamId64GenObj>();
        Iterator<ICFBamId64GenObj> dispensed = tableDef.getSchema().getId64GenTableObj().readId64GenByDispIdx(tableDef.getRequiredTenantId(), tableDef.getRequiredId()).iterator();
        while (dispensed.hasNext())
        {
            list.add(dispensed.next());
        }
        return (list);
    }

    public static List<ICFBamRelationObj> getOnlyOwnerRelations( ICFBamTableObj tableDef )
    {
        ICFBamRelationObj relationDef = null;
        List<ICFBamRelationObj> list = new LinkedList<ICFBamRelationObj>();
        Iterator<ICFBamRelationObj> childrenRelations = tableDef.getOptionalComponentsRelation().iterator();
        while (childrenRelations.hasNext()) {
            relationDef = childrenRelations.next();
            if( relationDef.getRequiredRelationType() == ICFBamSchema.RelationTypeEnum.Owner ) {
                list.add(relationDef);
            }
        }

        ICFBamRelationObj superClass = getSuperClassRelation( tableDef );
        while( superClass != null ) {
        	tableDef = superClass.getRequiredLookupToTable();
            childrenRelations = tableDef.getOptionalComponentsRelation().iterator();
            while (childrenRelations.hasNext()) {
                relationDef = childrenRelations.next();
                if( relationDef.getRequiredRelationType() == ICFBamSchema.RelationTypeEnum.Owner ) {
                    list.add(relationDef);
                }
            }
        	superClass = getSuperClassRelation( tableDef );
        }

        childrenRelations = tableDef.getOptionalComponentsRelation().iterator();
        while (childrenRelations.hasNext()) {
            relationDef = childrenRelations.next();
            if( relationDef.getRequiredRelationType() == ICFBamSchema.RelationTypeEnum.Container ) {
                list.add(relationDef);
            }
        }

        superClass = getSuperClassRelation( tableDef );
        while( superClass != null ) {
        	tableDef = superClass.getRequiredLookupToTable();
            childrenRelations = tableDef.getOptionalComponentsRelation().iterator();
            while (childrenRelations.hasNext()) {
                relationDef = childrenRelations.next();
                if( relationDef.getRequiredRelationType() == ICFBamSchema.RelationTypeEnum.Container ) {
                    list.add(relationDef);
                }
            }
        	superClass = getSuperClassRelation( tableDef );
        }

        return (list);
    }

    public static List<ICFBamRelationObj> getContainerOwnerRelations( ICFBamTableObj tableDef )
    {
        ICFBamRelationObj relationDef = null;
        String relationTag = null;
        List<ICFBamRelationObj> list = new LinkedList<ICFBamRelationObj>();
        Iterator<ICFBamRelationObj> childrenRelations = tableDef.getOptionalComponentsRelation().iterator();
        while (childrenRelations.hasNext()) {
            relationDef = childrenRelations.next();
            if( ( relationDef.getRequiredRelationType() == ICFBamSchema.RelationTypeEnum.Owner )
             || ( relationDef.getRequiredRelationType() == ICFBamSchema.RelationTypeEnum.Parent )
             || ( relationDef.getRequiredRelationType() == ICFBamSchema.RelationTypeEnum.Container ) )
            {
                list.add(relationDef);
            }
        }

        ICFBamRelationObj superClass = getSuperClassRelation( tableDef );
        while( superClass != null ) {
        	tableDef = superClass.getRequiredLookupToTable();
            childrenRelations = tableDef.getOptionalComponentsRelation().iterator();
            while (childrenRelations.hasNext()) {
                relationDef = childrenRelations.next();
                if( ( relationDef.getRequiredRelationType() == ICFBamSchema.RelationTypeEnum.Owner )
                 || ( relationDef.getRequiredRelationType() == ICFBamSchema.RelationTypeEnum.Parent )
                 || ( relationDef.getRequiredRelationType() == ICFBamSchema.RelationTypeEnum.Container ) )
                {
                	list.add(relationDef);
                }
            }
        	superClass = getSuperClassRelation( tableDef );
        }

        return (list);
    }

    public static ICFBamRelationObj getContainerRelation(ICFBamTableObj tableDef)
    {
        ICFBamRelationObj relationDef;
        Iterator<ICFBamRelationObj> childrenRelations = tableDef.getOptionalComponentsRelation().iterator();
        while (childrenRelations.hasNext()) {
            relationDef = childrenRelations.next();
            if( relationDef.getRequiredRelationType() == ICFBamSchema.RelationTypeEnum.Container ) {
                return (relationDef);
            }
        }
        return (null);
    }

    public static ICFBamRelationObj getInheritedContainerRelation(ICFBamTableObj tableDef)
    {
    	ICFBamRelationObj inheritedContainerRelation = null;
        ICFBamRelationObj relationDef;
        Iterator<ICFBamRelationObj> childrenRelations;
    	while( ( inheritedContainerRelation == null ) && ( tableDef != null ) ) {
	        childrenRelations = tableDef.getOptionalComponentsRelation().iterator();
	        while (childrenRelations.hasNext()) {
	            relationDef = childrenRelations.next();
	            if( relationDef.getRequiredRelationType() == ICFBamSchema.RelationTypeEnum.Container ) {
            		return( relationDef );
	            }
	        }
	        relationDef = getSuperClassRelation( tableDef );
	        if( relationDef == null ) {
	        	tableDef = null;
	        }
	        else {
	        	tableDef = relationDef.getRequiredLookupToTable();
	        }
    	}
        return (null);
    }

    public static ICFBamRelationObj getOwnerRelation(ICFBamTableObj tableDef)
    {
        ICFBamRelationObj relationDef;
        Iterator<ICFBamRelationObj> childrenRelations = tableDef.getOptionalComponentsRelation().iterator();
        while (childrenRelations.hasNext()) {
            relationDef = childrenRelations.next();
            if( relationDef.getRequiredRelationType() == ICFBamSchema.RelationTypeEnum.Owner ) {
                return (relationDef);
            }
        }
        return (null);
    }

    public static ICFBamRelationObj getInheritedOwnerRelation(ICFBamTableObj tableDef)
    {
    	ICFBamRelationObj inheritedOwnerRelation = null;
        ICFBamRelationObj relationDef;
        Iterator<ICFBamRelationObj> childrenRelations;
    	while( ( inheritedOwnerRelation == null ) && ( tableDef != null ) ) {
	        childrenRelations = tableDef.getOptionalComponentsRelation().iterator();
	        while (childrenRelations.hasNext()) {
	            relationDef = childrenRelations.next();
	            if( relationDef.getRequiredRelationType() == ICFBamSchema.RelationTypeEnum.Owner ) {
            		return( relationDef );
	            }
	        }
	        relationDef = getSuperClassRelation( tableDef );
	        if( relationDef == null ) {
	        	tableDef = null;
	        }
	        else {
	        	tableDef = relationDef.getRequiredLookupToTable();
	        }
    	}
        return (null);
    }

    public static List<ICFBamRelationObj> getOwnerLookupRelations(ICFBamTableObj tableDef)
    {
        List<ICFBamRelationObj> list = new LinkedList<ICFBamRelationObj>();
        Iterator<ICFBamRelationObj> childrenRelations = tableDef.getOptionalComponentsRelation().iterator();
        while (childrenRelations.hasNext())
        {
            ICFBamRelationObj relationDef = childrenRelations.next();
            if( ( relationDef.getRequiredRelationType() == ICFBamSchema.RelationTypeEnum.Parent )
             || ( relationDef.getRequiredRelationType() == ICFBamSchema.RelationTypeEnum.Container )
             || ( relationDef.getRequiredRelationType() == ICFBamSchema.RelationTypeEnum.Lookup )
             || ( relationDef.getRequiredRelationType() == ICFBamSchema.RelationTypeEnum.Owner ) )
            {
                list.add(relationDef);
            }
        }
        return (list);
    }

    public static List<ICFBamRelationObj> getOwnerContainerNamedLookupChainRelations(ICFBamTableObj tableDef)
    {
        List<ICFBamRelationObj> list = new LinkedList<ICFBamRelationObj>();
        List<ICFBamChainObj> componentChains = tableDef.getOptionalComponentsChains();
        Iterator<ICFBamRelationObj> childrenRelations = tableDef.getOptionalComponentsRelation().iterator();
        while (childrenRelations.hasNext()) {
            ICFBamRelationObj relationDef = childrenRelations.next();
            if( relationDef.getRequiredRelationType() == ICFBamSchema.RelationTypeEnum.Superclass ) {
            	;
            }
            else if( ( relationDef.getRequiredRelationType() == ICFBamSchema.RelationTypeEnum.Container )
             || ( relationDef.getRequiredRelationType() == ICFBamSchema.RelationTypeEnum.Owner )
             || ( relationDef.getRequiredRelationType() == ICFBamSchema.RelationTypeEnum.Container )
             || ( relationDef.getRequiredRelationType() == ICFBamSchema.RelationTypeEnum.Parent )
             || ( relationDef.getRequiredRelationType() == ICFBamSchema.RelationTypeEnum.Lookup ) )
            {
               list.add(relationDef);
            }
            else {
            	if( ! relationDef.getRequiredIsXsdContainer() ) {
	            	ICFBamIndexObj toIndex = relationDef.getRequiredLookupToIndex();
	            	if( toIndex.getRequiredIsUnique() ) {
	            		boolean referencedByChain = false;
	            		Iterator<ICFBamChainObj> iterChain = componentChains.iterator();
	            		while( ( ! referencedByChain ) && iterChain.hasNext() ) {
	            			ICFBamChainObj chain = iterChain.next();
	            			if( chain.getRequiredLookupPrevRel() == relationDef ) {
	            				referencedByChain = true;
	            			}
	            			else if( chain.getRequiredLookupNextRel() == relationDef ) {
	            				referencedByChain = true;
	            			}
	            		}
	            		if( referencedByChain ) {
	            			list.add( relationDef );
	            		}
	            		else {
			            	ICFBamTableObj toTable = relationDef.getRequiredLookupToTable();
			            	ICFBamIndexObj lookupIndex = toTable.getOptionalLookupLookupIndex();
			            	while( ( lookupIndex == null ) && ( toTable != null ) ) {
			            		ICFBamRelationObj superRelation = getSuperClassRelation( toTable );
			            		if( superRelation != null ) {
			            			toTable = superRelation.getRequiredLookupToTable();
				            		if( toTable != null ) {
				            			lookupIndex = toTable.getOptionalLookupLookupIndex();
				            		}
			            		}
			            		else {
			            			toTable = null;
			            		}
			            	}
			            	if( lookupIndex != null ) {
			            		list.add( relationDef );
			            	}
	            		}
	            	}
            	}
            }
        }
        return (list);
    }

    public static List<ICFBamAtomObj> getChildrenAtoms( ICFBamTableObj tableDef )
    {
        List<ICFBamAtomObj> list = new LinkedList<ICFBamAtomObj>();
        Iterator<ICFBamValueObj> childrenColumns = tableDef.getOptionalComponentsColumns().iterator();
        while (childrenColumns.hasNext())
        {
            ICFBamValueObj colDef = childrenColumns.next();
            if (colDef instanceof ICFBamAtomObj)
            {
                list.add( (ICFBamAtomObj)colDef);
            }
        }
        return (list);
    }

    public static ICFBamRelationObj getSuperClassRelation( ICFBamTableObj tableDef )
    {
        Iterator<ICFBamRelationObj> childrenRelations = tableDef.getOptionalComponentsRelation().iterator();
        while (childrenRelations.hasNext())
        {
            ICFBamRelationObj relationDef = childrenRelations.next();
            if( relationDef.getRequiredRelationType() == ICFBamSchema.RelationTypeEnum.Superclass ) {
                return( relationDef );
            }
        }
        return (null);
    }

    public static List<ICFBamRelationObj> getSubClassRelations(ICFBamTableObj tableDef)
    {
        List<ICFBamRelationObj> list = new LinkedList<ICFBamRelationObj>();
        Iterator<ICFBamRelationObj> reverseRelations = tableDef.getOptionalChildrenReverseRelations().iterator();
        while (reverseRelations.hasNext())
        {
            ICFBamRelationObj reverseRelation = reverseRelations.next();
            if( reverseRelation.getRequiredRelationType() == ICFBamSchema.RelationTypeEnum.Superclass ) {
                list.add(reverseRelation);
            }
        }
        return (list);
    }

    public static ICFBamIndexObj getPrimaryKeyIndex(ICFBamTableObj tableDef)
    {
        ICFBamIndexObj primaryIndex = tableDef.getOptionalLookupPrimaryIndex();
        return (primaryIndex);
    }

    public static List<ICFBamRelationObj> getFactoryOwnerRelations(ICFBamTableObj tableDef)
    {
        List<ICFBamRelationObj> list = new LinkedList<ICFBamRelationObj>();
        Iterator<ICFBamRelationObj> ownerRelations = getContainerOwnerRelations(tableDef).iterator();
        while (ownerRelations.hasNext())
        {
            // throw new NotImplementedException();
            ICFBamRelationObj ownerRelation = ownerRelations.next();
            if( ownerRelation.getRequiredRelationType() == ICFBamSchema.RelationTypeEnum.Superclass ) {
                list.add(ownerRelation);
            }
        }
        return (list);
    }

    public static List<ICFBamIndexObj> getInheritedIndexes(ICFBamTableObj tableDef)
    {
        List<ICFBamIndexObj> list = new LinkedList<ICFBamIndexObj>();
        ICFBamRelationObj superClassRelation = getSuperClassRelation(tableDef);
        if (superClassRelation != null)
        {
            Iterator<ICFBamIndexObj> superClassInheritedIndexes
                = getInheritedIndexes(superClassRelation.getRequiredLookupToTable()).iterator();
            while (superClassInheritedIndexes.hasNext())
            {
                list.add(superClassInheritedIndexes.next());
            }
            Iterator<ICFBamIndexObj> myIndexes
                = getChildrenIndexes( tableDef ).iterator();
            while( myIndexes.hasNext() )
            {
                list.add( myIndexes.next() );
            }
        }
        return (list);
    }

    public static List<ICFBamIndexObj> getChildrenIndexes(ICFBamTableObj tableDef)
    {
        Iterator<ICFBamIndexObj> iter = tableDef.getOptionalComponentsIndex().iterator();
        List<ICFBamIndexObj> list = new LinkedList<ICFBamIndexObj>();
        while( iter.hasNext() ) {
        	list.add( iter.next() );
        }
        return( list );
    }

    public static List<ICFBamRelationObj> getInheritedRelations(ICFBamTableObj tableDef)
    {
        List<ICFBamRelationObj> list = new LinkedList<ICFBamRelationObj>();
        ICFBamRelationObj superClassRelation = getSuperClassRelation(tableDef);
        if (superClassRelation != null)
        {
            Iterator<ICFBamRelationObj> superClassInheritedRelations
                = getInheritedRelations(superClassRelation.getRequiredLookupToTable()).iterator();
            while (superClassInheritedRelations.hasNext())
            {
                list.add(superClassInheritedRelations.next());
            }
            Iterator<ICFBamRelationObj> myRelations
                = getChildrenRelations(tableDef).iterator();
            while (myRelations.hasNext())
            {
                list.add(myRelations.next());
            }
        }
        return (list);
    }

    public static List<ICFBamRelationObj> getChildrenRelations(ICFBamTableObj tableDef)
    {
        List<ICFBamRelationObj> childrenRelations = new LinkedList<ICFBamRelationObj>();
        Collection<ICFBamRelationObj> cltn = tableDef.getOptionalComponentsRelation();
        Iterator<ICFBamRelationObj> iter = cltn.iterator();
        while( iter.hasNext() ) {
        	childrenRelations.add( iter.next() );
        }
        return (childrenRelations);
    }

    public static List<ICFBamValueObj> getTableIdGenerators(ICFBamTableObj tableDef)
    {
        List<ICFBamValueObj> list = new LinkedList<ICFBamValueObj>();
        Iterator<ICFBamId16GenObj> id16Generators = getId16Generators(tableDef).iterator();
        while (id16Generators.hasNext())
        {
            list.add(id16Generators.next());
        }
        Iterator<ICFBamId32GenObj> id32Generators = getId32Generators(tableDef).iterator();
        while (id32Generators.hasNext())
        {
            list.add(id32Generators.next());
        }
        Iterator<ICFBamId64GenObj> id64Generators = getId64Generators(tableDef).iterator();
        while (id64Generators.hasNext())
        {
            list.add(id64Generators.next());
        }
        return (list);
    }
}
