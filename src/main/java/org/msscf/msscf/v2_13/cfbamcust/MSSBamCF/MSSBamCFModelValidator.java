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


import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import org.msscf.msscf.v2_13.cfbam.CFBam.ICFBamSchema;
import org.msscf.msscf.v2_13.cfbam.CFBamObj.*;
import org.msscf.msscf.v2_13.cflib.CFLib.*;

public class MSSBamCFModelValidator
{
	protected boolean modelIsValid = true;
	protected StringBuffer log = new StringBuffer();

	public MSSBamCFModelValidator() {
	}

	public String getValidationResults() {
		return( log.toString() );
	}

	public boolean validateSchemaDef( ICFBamSchemaDefObj schema ) {
		modelIsValid = true;
		log = new StringBuffer();

		log.append( "Validating SchemaDef \"" + schema.getObjFullName() + "\"...\n" );

		propagateNameChanges( schema );
		validateSchemaLicenses( schema );
		validateSlices( schema );
		validateClassCodes( schema );
		validateDbNamesNotOverloaded( schema );
		validateColumnNamesNotOverloaded( schema );
		validateColumnShortNamesNotOverloaded( schema );
		validateColumnDbNamesNotOverloaded( schema );
		validateColumnsImplementsPolymorphAndIsPolymorph( schema );
		validateTablesHavePrimaryIndexes( schema );
		validateTablesIsServerOnlySetByBaseTable( schema );
		validateTablesIsMutableUsage( schema );
		validateIndexesHaveColumns( schema );
		validatePrimaryIndexesAreUnique( schema );
		validatePrimaryIndexColumnsAreRequired( schema );
		validateIndexNamesNotOverloaded( schema );
		validateIndexSuffixesNotOverloaded( schema );
		validateIndexShortNamesNotOverloaded( schema );
		validateRelationsHaveLinkages( schema );
		validateRelationNamesNotOverloaded( schema );
		validateRelationSuffixesNotOverloaded( schema );
		validateOnlyOneSuperclassPerTable( schema );
		validateRelationsHaveColumns( schema );
		validateRelationColumnCountsMatchIndexes( schema );
		validateRelationsSpecifyAllFromColumns( schema );
		validateRelationsSpecifyAllToColumns( schema );
		validateRelationAllowAddendumOnlySetByXmlContainers( schema );
		validateChains( schema );

		if( modelIsValid ) {
			log.append( "SchemaDef \"" + schema.getObjFullName() + "\" is valid.\n" );
		}
		else {
			log.append( "SchemaDef \"" + schema.getObjFullName() + "\" is not valid.\n" );
		}

		return( modelIsValid );
	}

	public void propagateNameChanges( ICFBamSchemaDefObj schema ) {
		propagateTypesAreNotNull( schema );
		propagateTableColumnNameChanges( schema );
		propagateIndexColumnNameChanges( schema );
	}

	protected void validateDbNamesNotOverloaded( ICFBamSchemaDefObj schema ) {
		HashMap<String,ICFBamScopeObj> map = new HashMap<String,ICFBamScopeObj>();
		Iterator<ICFBamTableObj> tableIter = schema.getOptionalComponentsTables().iterator();
		ICFBamTableObj table;
		String name;
		ICFBamScopeObj dup;
		boolean valid = true;
		while( tableIter.hasNext() ) {
			table = tableIter.next();
			name = table.getOptionalDbName();
			if( ( name == null ) || ( name.length() <= 0 ) ) {
				name = table.getObjName();
			}
			if( map.containsKey( name ) ) {
				dup = map.get( name );
				log.append( "ERROR: Table \"" + table.getObjName() + "\" database name \"" + name + "\" collides with that of the " + dup.getGenDefName() + " \"" + dup.getObjQualifiedName() +"\"\n" );
				valid = false;
			}
			else {
				map.put( name,  table );
			}
		}
		Iterator<ICFBamIndexObj> indexIter;
		ICFBamIndexObj index;
		tableIter = schema.getOptionalComponentsTables().iterator();
		while( tableIter.hasNext() ) {
			table = tableIter.next();
			indexIter = table.getOptionalComponentsIndex().iterator();
			while( indexIter.hasNext() ) {
				index = indexIter.next();
				name = index.getOptionalDbName();
				if( ( name == null ) || ( name.length() <= 0 ) ) {
					name = index.getObjName();
				}
				if( map.containsKey( name ) ) {
					dup = map.get( name );
					log.append( "ERROR: Index \"" + index.getObjQualifiedName() + "\" database name \"" + name + "\" collides with that of the " + dup.getGenDefName() + " \"" + dup.getObjQualifiedName() +"\"\n" );
					valid = false;
				}
				else {
					map.put( name,  index );
				}
			}
		}
		Iterator<ICFBamRelationObj> relationIter;
		ICFBamRelationObj relation;
		tableIter = schema.getOptionalComponentsTables().iterator();
		while( tableIter.hasNext() ) {
			table = tableIter.next();
			relationIter = table.getOptionalComponentsRelation().iterator();
			while( relationIter.hasNext() ) {
				relation = relationIter.next();
				name = relation.getOptionalDbName();
				if( ( name == null ) || ( name.length() <= 0 ) ) {
					name = relation.getObjName();
				}
				if( map.containsKey( name ) ) {
					dup = map.get( name );
					log.append( "ERROR: Relation \"" + relation.getObjQualifiedName() + "\" database name \"" + name + "\" collides with that of the " + dup.getGenDefName() + " \"" + dup.getObjQualifiedName() +"\"\n" );
					valid = false;
				}
				else {
					map.put( name,  relation );
				}
			}
		}
		if( valid ) {
			log.append( "Schema database names are unique\n" );
		}
		else {
			modelIsValid = false;
		}
	}

	protected void validateSchemaLicenses( ICFBamSchemaDefObj schema ) {
		boolean valid = true;

		if( schema.getRequiredCommonLicenseCode().length() < 3 ) {
			log.append( "ERROR: " + schema.getGenDefName() + " \"" + schema.getObjName() + "\" CommonLicenseCode \"" + schema.getRequiredCommonLicenseCode() + "\" must be at least 3 characters long\n" );
			valid = false;
		}

		if( schema.getRequiredCommonLicenseName().length() < 14 ) {
			log.append( "ERROR: " + schema.getGenDefName() + " \"" + schema.getObjName() + "\" CommonLicenseName \"" + schema.getRequiredCommonLicenseName() + "\" must be at least 14 characters long (enough room for 'Public License')\n" );
			valid = false;
		}

		if( schema.getRequiredCommonLicenseText().length() < 14 ) {
			log.append( "ERROR: " + schema.getGenDefName() + " \"" + schema.getObjName() + "\" CommonLicenseText \"" + schema.getRequiredCommonLicenseText() + "\" must be at least 14 characters long (enough room for 'Public License')\n" );
			valid = false;
		}

		if( ( schema.getOptionalClientXFaceLicenseCode() != null ) || ( schema.getOptionalClientXFaceLicenseName() != null ) || ( schema.getOptionalClientXFaceLicenseText() != null ) ) {
			if( ( schema.getOptionalClientXFaceLicenseCode() == null ) || ( schema.getOptionalClientXFaceLicenseName() == null ) || ( schema.getOptionalClientXFaceLicenseText() == null ) ) {
				log.append( "ERROR: " + schema.getGenDefName() + " \"" + schema.getObjName() + "\" Either all three ClientXFace license attributes must be specified or none must be specified.\n" );
				valid = false;
			}
			else {
				if( schema.getOptionalClientXFaceLicenseCode().length() < 3 ) {
					log.append( "ERROR: " + schema.getGenDefName() + " \"" + schema.getObjName() + "\" ClientXFaceLicenseCode \"" + schema.getOptionalClientXFaceLicenseCode() + "\" must be at least 3 characters long\n" );
					valid = false;
				}

				if( schema.getOptionalClientXFaceLicenseName().length() < 14 ) {
					log.append( "ERROR: " + schema.getGenDefName() + " \"" + schema.getObjName() + "\" ClientXFaceLicenseName \"" + schema.getOptionalClientXFaceLicenseName() + "\" must be at least 14 characters long (enough room for 'Public License')\n" );
					valid = false;
				}

				if( schema.getOptionalClientXFaceLicenseText().length() < 14 ) {
					log.append( "ERROR: " + schema.getGenDefName() + " \"" + schema.getObjName() + "\" ClientXFaceLicenseText \"" + schema.getOptionalClientXFaceLicenseText() + "\" must be at least 14 characters long (enough room for 'Public License')\n" );
					valid = false;
				}
			}
		}

		if( ( schema.getOptionalClientImplLicenseCode() != null ) || ( schema.getOptionalClientImplLicenseName() != null ) || ( schema.getOptionalClientImplLicenseText() != null ) ) {
			if( ( schema.getOptionalClientImplLicenseCode() == null ) || ( schema.getOptionalClientImplLicenseName() == null ) || ( schema.getOptionalClientImplLicenseText() == null ) ) {
				log.append( "ERROR: " + schema.getGenDefName() + " \"" + schema.getObjName() + "\" Either all three ClientImpl license attributes must be specified or none must be specified.\n" );
				valid = false;
			}
			else {
				if( schema.getOptionalClientImplLicenseCode().length() < 3 ) {
					log.append( "ERROR: " + schema.getGenDefName() + " \"" + schema.getObjName() + "\" ClientImplLicenseCode \"" + schema.getOptionalClientImplLicenseCode() + "\" must be at least 3 characters long\n" );
					valid = false;
				}

				if( schema.getOptionalClientImplLicenseName().length() < 14 ) {
					log.append( "ERROR: " + schema.getGenDefName() + " \"" + schema.getObjName() + "\" ClientImplLicenseName \"" + schema.getOptionalClientImplLicenseName() + "\" must be at least 14 characters long (enough room for 'Public License')\n" );
					valid = false;
				}

				if( schema.getOptionalClientImplLicenseText().length() < 14 ) {
					log.append( "ERROR: " + schema.getGenDefName() + " \"" + schema.getObjName() + "\" ClientImplLicenseText \"" + schema.getOptionalClientImplLicenseText() + "\" must be at least 14 characters long (enough room for 'Public License')\n" );
					valid = false;
				}
			}
		}

		if( ( schema.getOptionalServerXFaceLicenseCode() != null ) || ( schema.getOptionalServerXFaceLicenseName() != null ) || ( schema.getOptionalServerXFaceLicenseText() != null ) ) {
			if( ( schema.getOptionalServerXFaceLicenseCode() == null ) || ( schema.getOptionalServerXFaceLicenseName() == null ) || ( schema.getOptionalServerXFaceLicenseText() == null ) ) {
				log.append( "ERROR: " + schema.getGenDefName() + " \"" + schema.getObjName() + "\" Either all three ServerXFace license attributes must be specified or none must be specified.\n" );
				valid = false;
			}
			else {
				if( schema.getOptionalServerXFaceLicenseCode().length() < 3 ) {
					log.append( "ERROR: " + schema.getGenDefName() + " \"" + schema.getObjName() + "\" ServerXFaceLicenseCode \"" + schema.getOptionalServerXFaceLicenseCode() + "\" must be at least 3 characters long\n" );
					valid = false;
				}

				if( schema.getOptionalServerXFaceLicenseName().length() < 14 ) {
					log.append( "ERROR: " + schema.getGenDefName() + " \"" + schema.getObjName() + "\" ServerXFaceLicenseName \"" + schema.getOptionalServerXFaceLicenseName() + "\" must be at least 14 characters long (enough room for 'Public License')\n" );
					valid = false;
				}

				if( schema.getOptionalServerXFaceLicenseText().length() < 14 ) {
					log.append( "ERROR: " + schema.getGenDefName() + " \"" + schema.getObjName() + "\" ServerXFaceLicenseText \"" + schema.getOptionalServerXFaceLicenseText() + "\" must be at least 14 characters long (enough room for 'Public License')\n" );
					valid = false;
				}
			}
		}

		if( ( schema.getOptionalServerImplLicenseCode() != null ) || ( schema.getOptionalServerImplLicenseName() != null ) || ( schema.getOptionalServerImplLicenseText() != null ) ) {
			if( ( schema.getOptionalServerImplLicenseCode() == null ) || ( schema.getOptionalServerImplLicenseName() == null ) || ( schema.getOptionalServerImplLicenseText() == null ) ) {
				log.append( "ERROR: " + schema.getGenDefName() + " \"" + schema.getObjName() + "\" Either all three ServerImpl license attributes must be specified or none must be specified.\n" );
				valid = false;
			}
			else {
				if( schema.getOptionalServerImplLicenseCode().length() < 3 ) {
					log.append( "ERROR: " + schema.getGenDefName() + " \"" + schema.getObjName() + "\" ServerImplLicenseCode \"" + schema.getOptionalServerImplLicenseCode() + "\" must be at least 3 characters long\n" );
					valid = false;
				}

				if( schema.getOptionalServerImplLicenseName().length() < 14 ) {
					log.append( "ERROR: " + schema.getGenDefName() + " \"" + schema.getObjName() + "\" ServerImplLicenseName \"" + schema.getOptionalServerImplLicenseName() + "\" must be at least 14 characters long (enough room for 'Public License')\n" );
					valid = false;
				}

				if( schema.getOptionalServerImplLicenseText().length() < 14 ) {
					log.append( "ERROR: " + schema.getGenDefName() + " \"" + schema.getObjName() + "\" ServerImplLicenseText \"" + schema.getOptionalServerImplLicenseText() + "\" must be at least 14 characters long (enough room for 'Public License')\n" );
					valid = false;
				}
			}
		}

		if( valid ) {
			log.append( "Schema license specifications are valid\n" );
		}
		else {
			modelIsValid = false;
		}
	}

	protected void validateSlices( ICFBamSchemaDefObj schema ) {
		HashMap<Short,ICFBamValueObj> map = new HashMap<Short,ICFBamValueObj>();
		boolean valid = true;
		Iterator<ICFBamValueObj> typeIter = schema.getOptionalComponentsTypes().iterator();
		ICFBamValueObj type;
		ICFBamValueObj dup;
		ICFBamId16GenObj id16gen;
		ICFBamId32GenObj id32gen;
		ICFBamId64GenObj id64gen;
		ICFBamUuidGenObj uuidgen;
		Short slice;
		while( typeIter.hasNext() ) {
			type = typeIter.next();
			if( type instanceof ICFBamId16GenObj ) {
				id16gen = (ICFBamId16GenObj)type;
				slice = Short.valueOf( id16gen.getRequiredSlice() );
			}
			else if( type instanceof ICFBamId32GenObj ) {
				id32gen = (ICFBamId32GenObj)type;
				slice = Short.valueOf( id32gen.getRequiredSlice() );
			}
			else if( type instanceof ICFBamId64GenObj ) {
				id64gen = (ICFBamId64GenObj)type;
				slice = Short.valueOf( id64gen.getRequiredSlice() );
			}
			else if( type instanceof ICFBamUuidGenObj ) {
				uuidgen = (ICFBamUuidGenObj)type;
				slice = Short.valueOf( uuidgen.getRequiredSlice() );
			}
			else {
				slice = null;
			}
			if( slice != null ) {
				if( map.containsKey( slice ) ) {
					dup = map.get( slice );
					log.append( "ERROR: " + type.getGenDefName() + " \"" + type.getObjName() + "\" slice " + slice + " is already in use by the " + dup.getGenDefName() + " \"" + dup.getObjName() + "\"\n" );
					valid = false;
				}
				else {
					map.put( slice, type );
				}
			}
		}
		if( valid ) {
			log.append( "Id generator slices are valid\n" );
		}
		else {
			modelIsValid = false;
		}
	}

	protected void validateClassCodes( ICFBamSchemaDefObj schema ) {
		HashMap<String,ICFBamTableObj> map = new HashMap<String,ICFBamTableObj>();
		Iterator<ICFBamTableObj> tableIter = schema.getOptionalComponentsTables().iterator();
		ICFBamTableObj table;
		ICFBamTableObj dup;
		boolean valid = true;
		while( tableIter.hasNext() ) {
			table = tableIter.next();
			if( map.containsKey( table.getRequiredTableClassCode() ) ) {
				dup = map.get( table.getRequiredTableClassCode() );
				log.append( "ERROR: Table \"" + table.getObjName() + "\" class code \"" + table.getRequiredTableClassCode() + "\" is already in use by the table \"" + dup.getObjName() + "\"\n" );
				valid = false;
			}
			else {
				map.put( table.getRequiredTableClassCode(), table );
			}
		}
		if( valid ) {
			log.append( "Table class codes are valid\n" );
		}
		else {
			modelIsValid = false;
		}
	}

	protected void validateTablesHavePrimaryIndexes( ICFBamSchemaDefObj schema ) {
		Iterator<ICFBamTableObj> tableIter = schema.getOptionalComponentsTables().iterator();
		ICFBamTableObj table;
		boolean valid = true;
		while( tableIter.hasNext() ) {
			table = tableIter.next();
			if( table.getOptionalLookupPrimaryIndex() == null ) {
				log.append( "ERROR: Table \"" + table.getObjName() + "\" has no primary index\n" );
				valid = false;
			}
		}
		if( valid ) {
			log.append( "All tables have primary indexes\n" );
		}
		else {
			modelIsValid = false;
		}
	}

	protected void validatePrimaryIndexesAreUnique( ICFBamSchemaDefObj schema ) {
		Iterator<ICFBamTableObj> tableIter = schema.getOptionalComponentsTables().iterator();
		ICFBamTableObj table;
		ICFBamIndexObj index;
		boolean valid = true;
		while( tableIter.hasNext() ) {
			table = tableIter.next();
			index = table.getOptionalLookupPrimaryIndex();
			if( index != null ) {
				if( ! index.getRequiredIsUnique() ) {
					log.append( "ERROR: Table \"" + table.getObjName() + "\" primary index \"" + index.getObjName() + "\" is not unique\n" );
					valid = false;
				}
			}
		}
		if( valid ) {
			log.append( "Primary indexes are unique\n" );
		}
		else {
			modelIsValid = false;
		}
	}

	protected void validateTablesIsServerOnlySetByBaseTable( ICFBamSchemaDefObj schema ) {
		Iterator<ICFBamTableObj> tableIter = schema.getOptionalComponentsTables().iterator();
		ICFBamTableObj table;
		ICFBamRelationObj superRelation;
		Iterator<ICFBamRelationObj> relationIter;
		ICFBamRelationObj relation;
		
		boolean valid = true;
		while( tableIter.hasNext() ) {
			table = tableIter.next();
			superRelation = null;
			relationIter = table.getOptionalComponentsRelation().iterator();
			while( ( superRelation == null ) && relationIter.hasNext() ) {
				relation = relationIter.next();
				if( relation.getRequiredRelationType() == ICFBamSchema.RelationTypeEnum.Superclass ) {
					superRelation = relation;
				}
			}
			if( ( superRelation != null ) && table.getRequiredIsServerOnly() ) {
				log.append( "ERROR: Table \"" + table.getObjName() + "\" specifies a Superclass relation and is not allowed to set IsServerOnly\n" );
				valid = false;
			}
		}
		if( valid ) {
			log.append( "No tables that specify a Superclass relation have IsServerOnly set\n" );
		}
		else {
			modelIsValid = false;
		}
	}
	
	protected boolean validateTableHierarchyIsMutableUsage( ICFBamTableObj table, boolean inheritsChain, boolean inheritsMutable ) {
		boolean newInheritsMutable;
		boolean newInheritsChain;
		if( table.getOptionalComponentsChains().isEmpty() ) {
			newInheritsChain = inheritsChain;
			if( table.getRequiredIsMutable() ) {
				if( inheritsMutable ) {
					log.append( "ERROR: Table \"" + table.getObjName() + "\" inherits IsMutable and is not allowed to set it again\n" );
					return( false );
				}
				newInheritsMutable = true;
			}
			else {
				newInheritsMutable = inheritsMutable;
			}
		}
		else {
			if( inheritsChain ) {
				log.append( "ERROR: Table \"" + table.getObjName() + "\" specifies a Chain, but a Chain was already specified in the class hierarchy\n" );
				return( false );
			}
			newInheritsChain = true;
			if( table.getRequiredIsMutable() ) {
				if( inheritsChain ) {
					log.append( "ERROR: Table \"" + table.getObjName() + "\" specifies IsMutable, but inherits a Chain and the chained table must be the one to specify IsMutable\n" );
					return( false );
				}
				else if( inheritsMutable ) {
					log.append( "ERROR: Table \"" + table.getObjName() + "\" specifies IsMutable, but inherits IsMutable and is not allowed to set it again");
					return( false );
				}
				newInheritsMutable = true;
			}
			else {
				newInheritsMutable = inheritsMutable;
			}
		}
		Iterator<ICFBamRelationObj> relationIter;
		ICFBamRelationObj relation;
		ICFBamTableObj subclassTable;
		relationIter = table.getOptionalChildrenReverseRelations().iterator();
		while( relationIter.hasNext() ) {
			relation = relationIter.next();
			if( relation.getRequiredRelationType() == ICFBamSchema.RelationTypeEnum.Superclass ) {
				subclassTable = relation.getRequiredContainerFromTable();
				if( ! validateTableHierarchyIsMutableUsage( subclassTable, newInheritsChain, newInheritsMutable ) ) {
					return( false );
				}
			}
		}
		return( true );
	}

	protected void validateTablesIsMutableUsage( ICFBamSchemaDefObj schema ) {
		Iterator<ICFBamTableObj> tableIter = schema.getOptionalComponentsTables().iterator();
		ICFBamTableObj table;
		ICFBamRelationObj superRelation;
		Iterator<ICFBamRelationObj> relationIter;
		ICFBamRelationObj relation;

		// We only want to process base tables, and then navigate their subclass hierarchies
		boolean valid = true;
		boolean inheritsIsMutable = false;
		boolean inheritsChain = false;
		while( tableIter.hasNext() ) {
			table = tableIter.next();
			superRelation = null;
			relationIter = table.getOptionalComponentsRelation().iterator();
			while( ( superRelation == null ) && relationIter.hasNext() ) {
				relation = relationIter.next();
				if( relation.getRequiredRelationType() == ICFBamSchema.RelationTypeEnum.Superclass ) {
					superRelation = relation;
				}
			}
			if( superRelation == null ) {
				if( ! validateTableHierarchyIsMutableUsage( table, inheritsChain, inheritsIsMutable ) ) {
					valid = false;
				}
			}
		}
		if( valid ) {
			log.append( "Table hierachies specify IsMutable without overlap in the class hierarchy\n" );
		}
		else {
			modelIsValid = false;
		}
	}

	protected void validateIndexesHaveColumns( ICFBamSchemaDefObj schema ) {
		Iterator<ICFBamTableObj> tableIter = schema.getOptionalComponentsTables().iterator();
		ICFBamTableObj table;
		ICFBamIndexObj index;
		Iterator<ICFBamIndexObj> indexIter;
		Iterator<ICFBamIndexColObj> colIter;
		boolean valid = true;
		while( tableIter.hasNext() ) {
			table = tableIter.next();
			indexIter = table.getOptionalComponentsIndex().iterator();
			while( indexIter.hasNext() ) {
				index = indexIter.next();
				colIter = index.getOptionalComponentsColumns().iterator();
				if( ! colIter.hasNext() ) {
					log.append( "ERROR: Table \"" + table.getObjName() + "\" index \"" + index.getObjName() + "\" does not specify any columns\n" );
					valid = false;
				}
			}
		}
		if( valid ) {
			log.append( "Indexes all specify columns\n" );
		}
		else {
			modelIsValid = false;
		}
	}

	protected void validatePrimaryIndexColumnsAreRequired( ICFBamSchemaDefObj schema ) {
		Iterator<ICFBamTableObj> tableIter = schema.getOptionalComponentsTables().iterator();
		ICFBamTableObj table;
		ICFBamIndexObj index;
		Iterator<ICFBamIndexColObj> colIter;
		ICFBamIndexColObj col;
		ICFBamValueObj tableCol;
		boolean valid = true;
		while( tableIter.hasNext() ) {
			table = tableIter.next();
			index = table.getOptionalLookupPrimaryIndex();
			if( index != null ) {
				colIter = index.getOptionalComponentsColumns().iterator();
				while( colIter.hasNext() ) {
					col = colIter.next();
					tableCol = col.getRequiredLookupColumn();
					if( tableCol.getRequiredIsNullable() ) {
						log.append( "ERROR: Table \"" + table.getObjName() + "\" primary index \"" + index.getObjName() + "\" column \"" + tableCol.getObjName() + "\" is nullable\n" );
						valid = false;
					}
				}
			}
		}
		if( valid ) {
			log.append( "Primary index columns are all required/not-null\n" );
		}
		else {
			modelIsValid = false;
		}
	}

	protected void validateIndexNamesNotOverloaded( ICFBamSchemaDefObj schema ) {
		LinkedList<ICFBamTableObj> tableStack;
		ICFBamTableObj worktable;
		Iterator<ICFBamTableObj> tableIter = schema.getOptionalComponentsTables().iterator();
		ICFBamTableObj table;
		Iterator<ICFBamRelationObj> relationIter;
		ICFBamRelationObj relation;
		ICFBamRelationObj superRelation;
		HashMap<String,ICFBamIndexObj> map;
		ICFBamIndexObj dup;
		Iterator<ICFBamIndexObj> indexIter;
		ICFBamIndexObj index;
		boolean valid = true;
		while( tableIter.hasNext() ) {
			table = tableIter.next();
			tableStack = new LinkedList<ICFBamTableObj>();
			tableStack.add( table );
			worktable = table;
			while( worktable != null ) {
				superRelation = null;
				relationIter = worktable.getOptionalComponentsRelation().iterator();
				while( ( superRelation == null ) && relationIter.hasNext() ) {
					relation = relationIter.next();
					if( relation.getRequiredRelationType() == ICFBamSchema.RelationTypeEnum.Superclass ) {
						superRelation = relation;
					}
				}
				if( superRelation != null ) {
					worktable = superRelation.getRequiredLookupToTable();
					tableStack.add( worktable );
				}
				else {
					worktable = null;
				}
			}
			map = new HashMap<String,ICFBamIndexObj>();
			while( tableStack.size() > 0 ) {
				worktable = tableStack.getLast();
				tableStack.removeLast();
				indexIter = worktable.getOptionalComponentsIndex().iterator();
				while( indexIter.hasNext() ) {
					index = indexIter.next();
					if( map.containsKey( index.getObjName() ) ) {
						dup = map.get( index.getObjName() );
						log.append( "ERROR: Table \"" + worktable.getObjName() + "\" index \"" + index.getObjName() + "\" overloads the name of the index \"" + dup.getRequiredContainerTable().getObjName() + "." + dup.getObjName() +"\"\n" );
						valid = false;
					}
					else {
						map.put( index.getObjName(), index );
					}
				}
			}
		}
		if( valid ) {
			log.append( "Tables do not overload index names\n" );
		}
		else {
			modelIsValid = false;
		}
	}

	protected void validateIndexShortNamesNotOverloaded( ICFBamSchemaDefObj schema ) {
		LinkedList<ICFBamTableObj> tableStack;
		ICFBamTableObj worktable;
		Iterator<ICFBamTableObj> tableIter = schema.getOptionalComponentsTables().iterator();
		ICFBamTableObj table;
		Iterator<ICFBamRelationObj> relationIter;
		ICFBamRelationObj relation;
		ICFBamRelationObj superRelation;
		HashMap<String,ICFBamIndexObj> map;
		ICFBamIndexObj dup;
		Iterator<ICFBamIndexObj> indexIter;
		ICFBamIndexObj index;
		String shortName;
		boolean valid = true;
		while( tableIter.hasNext() ) {
			table = tableIter.next();
			tableStack = new LinkedList<ICFBamTableObj>();
			tableStack.add( table );
			worktable = table;
			while( worktable != null ) {
				superRelation = null;
				relationIter = worktable.getOptionalComponentsRelation().iterator();
				while( ( superRelation == null ) && relationIter.hasNext() ) {
					relation = relationIter.next();
					if( relation.getRequiredRelationType() == ICFBamSchema.RelationTypeEnum.Superclass ) {
						superRelation = relation;
					}
				}
				if( superRelation != null ) {
					worktable = superRelation.getRequiredLookupToTable();
					tableStack.add( worktable );
				}
				else {
					worktable = null;
				}
			}
			map = new HashMap<String,ICFBamIndexObj>();
			while( tableStack.size() > 0 ) {
				worktable = tableStack.getLast();
				tableStack.removeLast();
				indexIter = worktable.getOptionalComponentsIndex().iterator();
				while( indexIter.hasNext() ) {
					index = indexIter.next();
					shortName = index.getOptionalShortName();
					if( ( shortName == null ) || ( shortName.length() <= 0 ) ) {
						shortName = index.getObjName();
					}
					if( worktable.getOptionalLookupPrimaryIndex() != index ) {
						if( map.containsKey( shortName ) ) {
							dup = map.get( shortName );
							log.append( "ERROR: Table \"" + worktable.getObjName() + "\" index \"" + index.getObjName() + "\" overloads the short name \"" + shortName + "\" of the index \"" + dup.getRequiredContainerTable().getObjName() + "." + dup.getObjName() +"\"\n" );
							valid = false;
						}
						else {
							map.put( shortName, index );
						}
					}
				}
			}
		}
		if( valid ) {
			log.append( "Tables do not overload index short names\n" );
		}
		else {
			modelIsValid = false;
		}
	}

	protected void validateIndexSuffixesNotOverloaded( ICFBamSchemaDefObj schema ) {
		LinkedList<ICFBamTableObj> tableStack;
		ICFBamTableObj worktable;
		Iterator<ICFBamTableObj> tableIter = schema.getOptionalComponentsTables().iterator();
		ICFBamTableObj table;
		Iterator<ICFBamRelationObj> relationIter;
		ICFBamRelationObj relation;
		ICFBamRelationObj superRelation;
		HashMap<String,ICFBamIndexObj> map;
		ICFBamIndexObj dup;
		Iterator<ICFBamIndexObj> indexIter;
		ICFBamIndexObj index;
		String suffix;
		boolean valid = true;
		while( tableIter.hasNext() ) {
			table = tableIter.next();
			tableStack = new LinkedList<ICFBamTableObj>();
			tableStack.add( table );
			worktable = table;
			while( worktable != null ) {
				superRelation = null;
				relationIter = worktable.getOptionalComponentsRelation().iterator();
				while( ( superRelation == null ) && relationIter.hasNext() ) {
					relation = relationIter.next();
					if( relation.getRequiredRelationType() == ICFBamSchema.RelationTypeEnum.Superclass ) {
						superRelation = relation;
					}
				}
				if( superRelation != null ) {
					worktable = superRelation.getRequiredLookupToTable();
					tableStack.add( worktable );
				}
				else {
					worktable = null;
				}
			}
			map = new HashMap<String,ICFBamIndexObj>();
			while( tableStack.size() > 0 ) {
				worktable = tableStack.getLast();
				tableStack.removeLast();
				indexIter = worktable.getOptionalComponentsIndex().iterator();
				while( indexIter.hasNext() ) {
					index = indexIter.next();
					if( worktable.getOptionalLookupPrimaryIndex() != index ) {
						suffix = index.getOptionalSuffix();
						if( ( suffix == null ) || ( suffix.length() <= 0 ) ) {
							suffix = index.getObjName();
						}
						if( map.containsKey( suffix ) ) {
							dup = map.get( suffix );
							log.append( "ERROR: Table \"" + worktable.getObjName() + "\" index \"" + index.getObjName() + "\" overloads the suffix \"" + suffix + "\" of the index \"" + dup.getRequiredContainerTable().getObjName() + "." + dup.getObjName() +"\"\n" );
							valid = false;
						}
						else {
							map.put( suffix, index );
						}
					}
				}
			}
		}
		if( valid ) {
			log.append( "Tables do not overload index short names\n" );
		}
		else {
			modelIsValid = false;
		}
	}

	protected void validateColumnNamesNotOverloaded( ICFBamSchemaDefObj schema ) {
		LinkedList<ICFBamTableObj> tableStack;
		ICFBamTableObj worktable;
		Iterator<ICFBamTableObj> tableIter = schema.getOptionalComponentsTables().iterator();
		ICFBamTableObj table;
		Iterator<ICFBamIndexColObj> indexColIter;
		Iterator<ICFBamValueObj> colIter;
		ICFBamValueObj col;
		Iterator<ICFBamRelationObj> relationIter;
		ICFBamRelationObj relation;
		ICFBamRelationObj superRelation;
		HashMap<String,ICFBamValueObj> map;
		ICFBamValueObj dup;
		boolean notInPrimaryIndex;
		boolean valid = true;
		while( tableIter.hasNext() ) {
			table = tableIter.next();
			tableStack = new LinkedList<ICFBamTableObj>();
			tableStack.add( table );
			worktable = table;
			while( worktable != null ) {
				superRelation = null;
				relationIter = worktable.getOptionalComponentsRelation().iterator();
				while( ( superRelation == null ) && relationIter.hasNext() ) {
					relation = relationIter.next();
					if( relation.getRequiredRelationType() == ICFBamSchema.RelationTypeEnum.Superclass ) {
						superRelation = relation;
					}
				}
				if( superRelation != null ) {
					worktable = superRelation.getRequiredLookupToTable();
					tableStack.add( worktable );
				}
				else {
					worktable = null;
				}
			}
			map = new HashMap<String,ICFBamValueObj>();
			while( tableStack.size() > 0 ) {
				worktable = tableStack.getLast();
				tableStack.removeLast();
				colIter = worktable.getOptionalComponentsColumns().iterator();
				while( colIter.hasNext() ) {
					col = colIter.next();
					indexColIter = worktable.getOptionalLookupPrimaryIndex().getOptionalComponentsColumns().iterator();
					notInPrimaryIndex = true;
					while( notInPrimaryIndex && indexColIter.hasNext() ) {
						if( col == indexColIter.next().getRequiredLookupColumn() ) {
							notInPrimaryIndex = false;
						}
					}
					if( notInPrimaryIndex ) {
						if( map.containsKey( col.getObjName() ) ) {
							dup = map.get( col.getObjName() );
							log.append( "ERROR: Table \"" + worktable.getObjName() + "\" column \"" + col.getObjName() + "\" overloads the one defined by \"" + dup.getRequiredContainerScope().getObjName() + "." + dup.getObjName() +"\"\n" );
							valid = false;
						}
						else {
							map.put( col.getObjName(), col );
						}
					}
				}
			}
		}
		if( valid ) {
			log.append( "Tables do not overload column names\n" );
		}
		else {
			modelIsValid = false;
		}
	}

	protected void validateColumnShortNamesNotOverloaded( ICFBamSchemaDefObj schema ) {
		LinkedList<ICFBamTableObj> tableStack;
		ICFBamTableObj worktable;
		Iterator<ICFBamTableObj> tableIter = schema.getOptionalComponentsTables().iterator();
		ICFBamTableObj table;
		Iterator<ICFBamIndexColObj> indexColIter;
		Iterator<ICFBamValueObj> colIter;
		ICFBamValueObj col;
		Iterator<ICFBamRelationObj> relationIter;
		ICFBamRelationObj relation;
		ICFBamRelationObj superRelation;
		HashMap<String,ICFBamValueObj> map;
		ICFBamValueObj dup;
		String colShortName;
		boolean notInPrimaryIndex;
		boolean valid = true;
		while( tableIter.hasNext() ) {
			table = tableIter.next();
			tableStack = new LinkedList<ICFBamTableObj>();
			tableStack.add( table );
			worktable = table;
			while( worktable != null ) {
				superRelation = null;
				relationIter = worktable.getOptionalComponentsRelation().iterator();
				while( ( superRelation == null ) && relationIter.hasNext() ) {
					relation = relationIter.next();
					if( relation.getRequiredRelationType() == ICFBamSchema.RelationTypeEnum.Superclass ) {
						superRelation = relation;
					}
				}
				if( superRelation != null ) {
					worktable = superRelation.getRequiredLookupToTable();
					tableStack.add( worktable );
				}
				else {
					worktable = null;
				}
			}
			map = new HashMap<String,ICFBamValueObj>();
			while( tableStack.size() > 0 ) {
				worktable = tableStack.getLast();
				tableStack.removeLast();
				colIter = worktable.getOptionalComponentsColumns().iterator();
				while( colIter.hasNext() ) {
					col = colIter.next();
					colShortName = col.getOptionalShortName();
					if( ( colShortName == null ) || ( colShortName.length() <= 0 ) ) {
						colShortName = col.getObjName();
					}
					indexColIter = worktable.getOptionalLookupPrimaryIndex().getOptionalComponentsColumns().iterator();
					notInPrimaryIndex = true;
					while( notInPrimaryIndex && indexColIter.hasNext() ) {
						if( col == indexColIter.next().getRequiredLookupColumn() ) {
							notInPrimaryIndex = false;
						}
					}
					if( notInPrimaryIndex ) {
						if( map.containsKey( colShortName ) ) {
							dup = map.get( colShortName );
							log.append( "ERROR: Table \"" + worktable.getObjName() + "\" column \"" + col.getObjName() + "\" overloads the short name \"" + colShortName + "\" defined by \"" + dup.getRequiredContainerScope().getObjName() + "." + dup.getObjName() +"\"\n" );
							valid = false;
						}
						else {
							map.put( colShortName, col );
						}
					}
				}
			}
		}
		if( valid ) {
			log.append( "Tables do not overload column short names\n" );
		}
		else {
			modelIsValid = false;
		}
	}

	protected void validateColumnDbNamesNotOverloaded( ICFBamSchemaDefObj schema ) {
		final String S_ProcName = "validateColumnDbNamesNotOverloaded";
		LinkedList<ICFBamTableObj> tableStack;
		ICFBamTableObj worktable;
		Iterator<ICFBamTableObj> tableIter = schema.getOptionalComponentsTables().iterator();
		ICFBamTableObj table;
		Iterator<ICFBamIndexColObj> indexColIter;
		Iterator<ICFBamValueObj> colIter;
		ICFBamValueObj col;
		Iterator<ICFBamRelationObj> relationIter;
		ICFBamRelationObj relation;
		ICFBamRelationObj superRelation;
		HashMap<String,ICFBamValueObj> map;
		ICFBamValueObj dup;
		String colDbName;
		boolean notInPrimaryIndex;
		boolean valid = true;
		while( tableIter.hasNext() ) {
			table = tableIter.next();
			tableStack = new LinkedList<ICFBamTableObj>();
			tableStack.add( table );
			worktable = table;
			while( worktable != null ) {
				superRelation = null;
				relationIter = worktable.getOptionalComponentsRelation().iterator();
				while( ( superRelation == null ) && relationIter.hasNext() ) {
					relation = relationIter.next();
					if( relation.getRequiredRelationType() == ICFBamSchema.RelationTypeEnum.Superclass ) {
						superRelation = relation;
					}
				}
				if( superRelation != null ) {
					worktable = superRelation.getRequiredLookupToTable();
					tableStack.add( worktable );
				}
				else {
					worktable = null;
				}
			}
			map = new HashMap<String,ICFBamValueObj>();
			while( tableStack.size() > 0 ) {
				worktable = tableStack.getLast();
				tableStack.removeLast();
				colIter = worktable.getOptionalComponentsColumns().iterator();
				while( colIter.hasNext() ) {
					col = colIter.next();
					if( col instanceof ICFBamTableColObj ) {
						colDbName = ((ICFBamTableColObj)col).getOptionalDbName();
					}
					else if( col instanceof ICFBamBlobColObj ) {
						colDbName = ((ICFBamBlobColObj)col).getOptionalDbName();
					}
					else if( col instanceof ICFBamBoolColObj ) {
						colDbName = ((ICFBamBoolColObj)col).getOptionalDbName();
					}
					else if( col instanceof ICFBamInt16ColObj ) {
						colDbName = ((ICFBamInt16ColObj)col).getOptionalDbName();
					}
					else if( col instanceof ICFBamInt32ColObj ) {
						colDbName = ((ICFBamInt32ColObj)col).getOptionalDbName();
					}
					else if( col instanceof ICFBamInt64ColObj ) {
						colDbName = ((ICFBamInt64ColObj)col).getOptionalDbName();
					}
					else if( col instanceof ICFBamUInt16ColObj ) {
						colDbName = ((ICFBamUInt16ColObj)col).getOptionalDbName();
					}
					else if( col instanceof ICFBamUInt32ColObj ) {
						colDbName = ((ICFBamUInt32ColObj)col).getOptionalDbName();
					}
					else if( col instanceof ICFBamUInt64ColObj ) {
						colDbName = ((ICFBamUInt64ColObj)col).getOptionalDbName();
					}
					else if( col instanceof ICFBamFloatColObj ) {
						colDbName = ((ICFBamFloatColObj)col).getOptionalDbName();
					}
					else if( col instanceof ICFBamDoubleColObj ) {
						colDbName = ((ICFBamDoubleColObj)col).getOptionalDbName();
					}
					else if( col instanceof ICFBamNumberColObj ) {
						colDbName = ((ICFBamNumberColObj)col).getOptionalDbName();
					}
					else if( col instanceof ICFBamStringColObj ) {
						colDbName = ((ICFBamStringColObj)col).getOptionalDbName();
					}
					else if( col instanceof ICFBamTextColObj ) {
						colDbName = ((ICFBamTextColObj)col).getOptionalDbName();
					}
					else if( col instanceof ICFBamTokenColObj ) {
						colDbName = ((ICFBamTokenColObj)col).getOptionalDbName();
					}
					else if( col instanceof ICFBamNmTokenColObj ) {
						colDbName = ((ICFBamNmTokenColObj)col).getOptionalDbName();
					}
					else if( col instanceof ICFBamNmTokensColObj ) {
						colDbName = ((ICFBamNmTokensColObj)col).getOptionalDbName();
					}
					else if( col instanceof ICFBamDateColObj ) {
						colDbName = ((ICFBamDateColObj)col).getOptionalDbName();
					}
					else if( col instanceof ICFBamTimeColObj ) {
						colDbName = ((ICFBamTimeColObj)col).getOptionalDbName();
					}
					else if( col instanceof ICFBamTimestampColObj ) {
						colDbName = ((ICFBamTimestampColObj)col).getOptionalDbName();
					}
					else if( col instanceof ICFBamTZDateColObj ) {
						colDbName = ((ICFBamTZDateColObj)col).getOptionalDbName();
					}
					else if( col instanceof ICFBamTZTimeColObj ) {
						colDbName = ((ICFBamTZTimeColObj)col).getOptionalDbName();
					}
					else if( col instanceof ICFBamTZTimestampColObj ) {
						colDbName = ((ICFBamTZTimestampColObj)col).getOptionalDbName();
					}
					else if( col instanceof ICFBamUuidColObj ) {
						colDbName = ((ICFBamUuidColObj)col).getOptionalDbName();
					}
					else {
						throw new CFLibUnsupportedClassException( getClass(),
							S_ProcName,
							"col",
							col,
							"definitely not " + col.getGenDefName() );
					}
					if( ( colDbName == null ) || ( colDbName.length() <= 0 ) ) {
						colDbName = col.getObjName();
					}
					indexColIter = worktable.getOptionalLookupPrimaryIndex().getOptionalComponentsColumns().iterator();
					notInPrimaryIndex = true;
					while( notInPrimaryIndex && indexColIter.hasNext() ) {
						if( col == indexColIter.next().getRequiredLookupColumn() ) {
							notInPrimaryIndex = false;
						}
					}
					if( notInPrimaryIndex ) {
						if( map.containsKey( colDbName ) ) {
							dup = map.get( colDbName );
							log.append( "ERROR: Table \"" + worktable.getObjName() + "\" column \"" + col.getObjName() + "\" overloads the database name \"" + colDbName + "\" defined by \"" + dup.getRequiredContainerScope().getObjName() + "." + dup.getObjName() +"\"\n" );
							valid = false;
						}
						else {
							map.put( colDbName, col );
						}
					}
				}
			}
		}
		if( valid ) {
			log.append( "Tables do not overload column database names\n" );
		}
		else {
			modelIsValid = false;
		}
	}

	protected void validateColumnsImplementsPolymorphAndIsPolymorph( ICFBamSchemaDefObj schema ) {
		LinkedList<ICFBamTableObj> tableStack;
		ICFBamTableObj worktable;
		Iterator<ICFBamTableObj> tableIter = schema.getOptionalComponentsTables().iterator();
		ICFBamTableObj table;
		Iterator<ICFBamValueObj> colIter;
		ICFBamValueObj col;
		Iterator<ICFBamRelationObj> relationIter;
		ICFBamRelationObj relation;
		ICFBamRelationObj superRelation;
		boolean valid = true;
		while( tableIter.hasNext() ) {
			table = tableIter.next();
			tableStack = new LinkedList<ICFBamTableObj>();
			tableStack.add( table );
			worktable = table;
			while( worktable != null ) {
				superRelation = null;
				relationIter = worktable.getOptionalComponentsRelation().iterator();
				while( ( superRelation == null ) && relationIter.hasNext() ) {
					relation = relationIter.next();
					if( relation.getRequiredRelationType() == ICFBamSchema.RelationTypeEnum.Superclass ) {
						superRelation = relation;
					}
				}
				if( superRelation != null ) {
					worktable = superRelation.getRequiredLookupToTable();
					tableStack.add( worktable );
				}
				else {
					worktable = null;
				}
			}
			String colName;
			String polyName;
			ICFLibAnyObj polyObj;
			ICFBamStringDefObj stringDef;
			while( tableStack.size() > 0 ) {
				worktable = tableStack.getLast();
				tableStack.removeLast();
				colIter = worktable.getOptionalComponentsColumns().iterator();
				while( colIter.hasNext() ) {
					col = colIter.next();
					if( col.getRequiredImplementsPolymorph() ) {
						colName = col.getRequiredName();
						polyName = colName + "Str";
						polyObj = worktable.getNamedObject( polyName );
						if( polyObj != null ) {
							if( polyObj instanceof ICFBamStringDefObj ) {
								stringDef = (ICFBamStringDefObj)polyObj;
								if( ! stringDef.getRequiredIsPolymorph() ) {
									log.append( "ERROR: Table \"" + worktable.getObjName() + "\" column \"" + col.getObjName() + "\" specifies ImplementsPolymorph, but the resolved named StringDef doesn't have IsPolymorph set.\n" );
									valid = false;
								}
							}
							else {
								log.append( "ERROR: Table \"" + worktable.getObjName() + "\" column \"" + col.getObjName() + "\" specifies ImplementsPolymorph, but the resolved column is not a StringDef.\n" );
								valid = false;
							}
						}
						else {
							log.append( "ERROR: Table \"" + worktable.getObjName() + "\" column \"" + col.getObjName() + "\" specifies ImplementsPolymorph, but there is no inherited column named \"" + polyName + "\".\n" );
							valid = false;
						}
					}
				}
			}
		}
		if( valid ) {
			log.append( "Table columns which specify ImplementsPolymorph all have valid [Name]Str inherited StringDef columns with IsPolymorph set.\n" );
		}
		else {
			modelIsValid = false;
		}
	}

	protected void validateRelationsHaveLinkages( ICFBamSchemaDefObj schema ) {
		Iterator<ICFBamTableObj> tableIter = schema.getOptionalComponentsTables().iterator();
		ICFBamTableObj table;
		ICFBamRelationObj relation;
		Iterator<ICFBamRelationObj> relationIter;
		boolean valid = true;
		while( tableIter.hasNext() ) {
			table = tableIter.next();
			relationIter = table.getOptionalComponentsRelation().iterator();
			while( relationIter.hasNext() ) {
				relation = relationIter.next();
				if( relation.getRelationTable() == null ) {
					log.append( "ERROR: Table \"" + table.getObjName() + "\" relation \"" + relation.getObjName() + "\" does not specify its table\n" );
					valid = false;
				}
				if( relation.getRequiredLookupToTable() == null ) {
					log.append( "ERROR: Table \"" + table.getObjName() + "\" relation \"" + relation.getObjName() + "\" does not specify a to table\n" );
					valid = false;
				}
				if( relation.getRequiredLookupFromIndex() == null ) {
					log.append( "ERROR: Table \"" + table.getObjName() + "\" relation \"" + relation.getObjName() + "\" does not specify a from index\n" );
					valid = false;
				}
				if( relation.getRequiredLookupToIndex() == null ) {
					log.append( "ERROR: Table \"" + table.getObjName() + "\" relation \"" + relation.getObjName() + "\" does not specify a to index\n" );
					valid = false;
				}
			}
		}
		if( valid ) {
			log.append( "Relations have proper linkages\n" );
		}
		else {
			modelIsValid = false;
		}
	}

	protected void validateRelationNamesNotOverloaded( ICFBamSchemaDefObj schema ) {
		LinkedList<ICFBamTableObj> tableStack;
		ICFBamTableObj worktable;
		Iterator<ICFBamTableObj> tableIter = schema.getOptionalComponentsTables().iterator();
		ICFBamTableObj table;
		Iterator<ICFBamRelationObj> relationIter;
		ICFBamRelationObj relation;
		ICFBamRelationObj superRelation;
		HashMap<String,ICFBamRelationObj> map;
		ICFBamRelationObj dup;
		boolean valid = true;
		while( tableIter.hasNext() ) {
			table = tableIter.next();
			tableStack = new LinkedList<ICFBamTableObj>();
			tableStack.add( table );
			worktable = table;
			while( worktable != null ) {
				superRelation = null;
				relationIter = worktable.getOptionalComponentsRelation().iterator();
				while( ( superRelation == null ) && relationIter.hasNext() ) {
					relation = relationIter.next();
					if( relation.getRequiredRelationType() == ICFBamSchema.RelationTypeEnum.Superclass ) {
						superRelation = relation;
					}
				}
				if( superRelation != null ) {
					worktable = superRelation.getRequiredLookupToTable();
					tableStack.add( worktable );
				}
				else {
					worktable = null;
				}
			}
			map = new HashMap<String,ICFBamRelationObj>();
			while( tableStack.size() > 0 ) {
				worktable = tableStack.getLast();
				tableStack.removeLast();
				relationIter = worktable.getOptionalComponentsRelation().iterator();
				while( relationIter.hasNext() ) {
					relation = relationIter.next();
					if( relation.getRequiredRelationType() != ICFBamSchema.RelationTypeEnum.Superclass ) {
						if( map.containsKey( relation.getObjName() ) ) {
							dup = map.get( relation.getObjName() );
							log.append( "ERROR: Table \"" + worktable.getObjName() + "\" relation \"" + relation.getObjName() + "\" overloads the name of the relationship \"" + dup.getRequiredContainerFromTable().getObjName() + "." + dup.getObjName() +"\"\n" );
							valid = false;
						}
						else {
							map.put( relation.getObjName(), relation );
						}
					}
				}
			}
		}
		if( valid ) {
			log.append( "Tables do not overload relation names\n" );
		}
		else {
			modelIsValid = false;
		}
	}

	protected void validateRelationSuffixesNotOverloaded( ICFBamSchemaDefObj schema ) {
		LinkedList<ICFBamTableObj> tableStack;
		ICFBamTableObj worktable;
		Iterator<ICFBamTableObj> tableIter = schema.getOptionalComponentsTables().iterator();
		ICFBamTableObj table;
		Iterator<ICFBamRelationObj> relationIter;
		ICFBamRelationObj relation;
		ICFBamRelationObj superRelation;
		HashMap<String,ICFBamRelationObj> map;
		ICFBamRelationObj dup;
		String suffix;
		boolean valid = true;
		while( tableIter.hasNext() ) {
			table = tableIter.next();
			tableStack = new LinkedList<ICFBamTableObj>();
			tableStack.add( table );
			worktable = table;
			while( worktable != null ) {
				superRelation = null;
				relationIter = worktable.getOptionalComponentsRelation().iterator();
				while( ( superRelation == null ) && relationIter.hasNext() ) {
					relation = relationIter.next();
					if( relation.getRequiredRelationType() == ICFBamSchema.RelationTypeEnum.Superclass ) {
						superRelation = relation;
					}
				}
				if( superRelation != null ) {
					worktable = superRelation.getRequiredLookupToTable();
					tableStack.add( worktable );
				}
				else {
					worktable = null;
				}
			}
			map = new HashMap<String,ICFBamRelationObj>();
			while( tableStack.size() > 0 ) {
				worktable = tableStack.getLast();
				tableStack.removeLast();
				relationIter = worktable.getOptionalComponentsRelation().iterator();
				while( relationIter.hasNext() ) {
					relation = relationIter.next();
					if( relation.getRequiredRelationType() != ICFBamSchema.RelationTypeEnum.Superclass ) {
						suffix = relation.getOptionalSuffix();
						if( ( suffix == null ) || ( suffix.length() <= 0 ) ) {
							suffix = relation.getObjName();
						}
						if( map.containsKey( suffix ) ) {
							dup = map.get( suffix );
							log.append( "ERROR: Table \"" + worktable.getObjName() + "\" relation \"" + relation.getObjName() + "\" overloads the suffix \"" + suffix + "\" of the relationship \"" + dup.getRequiredContainerFromTable().getObjName() + "." + dup.getObjName() +"\"\n" );
							valid = false;
						}
						else {
							map.put( suffix, relation );
						}
					}
				}
			}
		}
		if( valid ) {
			log.append( "Tables do not overload relation suffixes\n" );
		}
		else {
			modelIsValid = false;
		}
	}

	protected void validateOnlyOneSuperclassPerTable( ICFBamSchemaDefObj schema ) {
		Iterator<ICFBamTableObj> tableIter = schema.getOptionalComponentsTables().iterator();
		ICFBamTableObj table;
		ICFBamRelationObj relation;
		Iterator<ICFBamRelationObj> relationIter;
		ICFBamRelationObj superclassRelation = null;
		boolean valid = true;
		while( tableIter.hasNext() ) {
			table = tableIter.next();
			superclassRelation = null;
			relationIter = table.getOptionalComponentsRelation().iterator();
			while( relationIter.hasNext() ) {
				relation = relationIter.next();
				if( relation.getRequiredRelationType() == ICFBamSchema.RelationTypeEnum.Superclass ) {
					if( superclassRelation != null ) {
						log.append( "ERROR: Table \"" + table.getObjName() + "\" relation \"" + relation.getObjName() + "\" is a Superclass relation, but \"" + superclassRelation.getObjName() + "\" has already been defined as the Superclass relation\n" );
						valid = false;
					}
					else {
						superclassRelation = relation;
					}
				}
			}
		}
		if( valid ) {
			log.append( "Tables have at most one Superclass relation\n" );
		}
		else {
			modelIsValid = false;
		}
	}

	protected void validateRelationsHaveColumns( ICFBamSchemaDefObj schema ) {
		Iterator<ICFBamTableObj> tableIter = schema.getOptionalComponentsTables().iterator();
		ICFBamTableObj table;
		ICFBamRelationObj relation;
		Iterator<ICFBamRelationObj> relationIter;
		Iterator<ICFBamRelationColObj> colIter;
		boolean valid = true;
		while( tableIter.hasNext() ) {
			table = tableIter.next();
			relationIter = table.getOptionalComponentsRelation().iterator();
			while( relationIter.hasNext() ) {
				relation = relationIter.next();
				colIter = relation.getOptionalComponentsColumns().iterator();
				if( ! colIter.hasNext() ) {
					log.append( "ERROR: Table \"" + table.getObjName() + "\" relation \"" + relation.getObjName() + "\" does not specify any columns\n" );
					valid = false;
				}
			}
		}
		if( valid ) {
			log.append( "Relations all specify columns\n" );
		}
		else {
			modelIsValid = false;
		}
	}

	protected void validateRelationsSpecifyAllFromColumns( ICFBamSchemaDefObj schema ) {
		HashMap<String,ICFBamIndexColObj> map;
		Iterator<ICFBamTableObj> tableIter = schema.getOptionalComponentsTables().iterator();
		ICFBamTableObj table;
		ICFBamRelationObj relation;
		Iterator<ICFBamRelationObj> relationIter;
		Iterator<ICFBamRelationColObj> colIter;
		ICFBamRelationColObj relationCol;
		ICFBamIndexColObj indexCol;
		Iterator<ICFBamIndexColObj> indexColIter;
		boolean valid = true;
		while( tableIter.hasNext() ) {
			table = tableIter.next();
			relationIter = table.getOptionalComponentsRelation().iterator();
			while( relationIter.hasNext() ) {
				relation = relationIter.next();
				map = new HashMap<String,ICFBamIndexColObj>();
				colIter = relation.getOptionalComponentsColumns().iterator();
				while( colIter.hasNext() ) {
					relationCol = colIter.next();
					indexCol = relationCol.getRequiredLookupFromCol();
					if( map.containsKey( indexCol.getObjName() ) ) {
						log.append( "ERROR: Table \"" + table.getObjName() + "\" relation \"" + relation.getObjName() + "\" specifies the from index column \"" + indexCol.getObjName() + "\" more than once\n" );
						valid = false;
					}
					else {
						map.put( indexCol.getObjName(), indexCol );
					}
				}
				indexColIter = relation.getRequiredLookupFromIndex().getOptionalComponentsColumns().iterator();
				while( indexColIter.hasNext() ) {
					indexCol = indexColIter.next();
					if( indexCol != map.get( indexCol.getObjName() ) ) {
						log.append( "ERROR: Table \"" + table.getObjName() + "\" relation \"" + relation.getObjName() + "\" does not reference the from index column \"" + indexCol.getObjName() + "\"\n" );
						valid = false;
					}
				}
			}
		}
		if( valid ) {
			log.append( "Relations bind all from index columns exactly once\n" );
		}
		else {
			modelIsValid = false;
		}
	}

	protected void validateRelationsSpecifyAllToColumns( ICFBamSchemaDefObj schema ) {
		HashMap<String,ICFBamIndexColObj> map;
		Iterator<ICFBamTableObj> tableIter = schema.getOptionalComponentsTables().iterator();
		ICFBamTableObj table;
		ICFBamRelationObj relation;
		Iterator<ICFBamRelationObj> relationIter;
		Iterator<ICFBamRelationColObj> colIter;
		ICFBamRelationColObj relationCol;
		ICFBamIndexColObj indexCol;
		Iterator<ICFBamIndexColObj> indexColIter;
		boolean valid = true;
		while( tableIter.hasNext() ) {
			table = tableIter.next();
			relationIter = table.getOptionalComponentsRelation().iterator();
			while( relationIter.hasNext() ) {
				relation = relationIter.next();
				map = new HashMap<String,ICFBamIndexColObj>();
				colIter = relation.getOptionalComponentsColumns().iterator();
				while( colIter.hasNext() ) {
					relationCol = colIter.next();
					indexCol = relationCol.getRequiredLookupToCol();
					if( map.containsKey( indexCol.getObjName() ) ) {
						log.append( "ERROR: Table \"" + table.getObjName() + "\" relation \"" + relation.getObjName() + "\" specifies the to index column \"" + indexCol.getObjName() + "\" more than once\n" );
						valid = false;
					}
					else {
						map.put( indexCol.getObjName(), indexCol );
					}
				}
				indexColIter = relation.getRequiredLookupToIndex().getOptionalComponentsColumns().iterator();
				while( indexColIter.hasNext() ) {
					indexCol = indexColIter.next();
					if( indexCol != map.get( indexCol.getObjName() ) ) {
						log.append( "ERROR: Table \"" + table.getObjName() + "\" relation \"" + relation.getObjName() + "\" does not reference the to index column \"" + indexCol.getObjName() + "\"\n" );
						valid = false;
					}
				}
			}
		}
		if( valid ) {
			log.append( "Relations bind all to index columns exactly once\n" );
		}
		else {
			modelIsValid = false;
		}
	}

	protected void validateRelationAllowAddendumOnlySetByXmlContainers( ICFBamSchemaDefObj schema ) {
		Iterator<ICFBamTableObj> tableIter = schema.getOptionalComponentsTables().iterator();
		ICFBamTableObj table;
		ICFBamRelationObj relation;
		Iterator<ICFBamRelationObj> relationIter;
		boolean valid = true;
		while( tableIter.hasNext() ) {
			table = tableIter.next();
			relationIter = table.getOptionalComponentsRelation().iterator();
			while( relationIter.hasNext() ) {
				relation = relationIter.next();
				if( ( ! relation.getRequiredIsXsdContainer() ) && relation.getRequiredAllowAddendum() ) {
					log.append( "ERROR: Table \"" + table.getObjName() + "\" relation \"" + relation.getObjName() + "\" is not an XSD Container relationship, but specifies AllowAddendum\"\n" );
					valid = false;
				}
			}
		}
		if( valid ) {
			log.append( "Only XSD Container relations specify AllowAddendum\n" );
		}
		else {
			modelIsValid = false;
		}
	}

	protected void validateRelationColumnCountsMatchIndexes( ICFBamSchemaDefObj schema ) {
		Iterator<ICFBamTableObj> tableIter = schema.getOptionalComponentsTables().iterator();
		ICFBamTableObj table;
		ICFBamRelationObj relation;
		Iterator<ICFBamRelationObj> relationIter;
		boolean valid = true;
		while( tableIter.hasNext() ) {
			table = tableIter.next();
			relationIter = table.getOptionalComponentsRelation().iterator();
			while( relationIter.hasNext() ) {
				relation = relationIter.next();
				if( relation.getRequiredLookupFromIndex().getOptionalComponentsColumns().size() != relation.getOptionalComponentsColumns().size() ) {
					log.append( "ERROR: Table \"" + table.getObjName() + "\" relation \"" + relation.getObjName() + "\" does not specify the same number of columns as in the from index \"" + relation.getRequiredLookupFromIndex().getObjName() + "\"\n" );
					valid = false;
				}
				if( relation.getRequiredLookupToIndex().getOptionalComponentsColumns().size() != relation.getOptionalComponentsColumns().size() ) {
					log.append( "ERROR: Table \"" + table.getObjName() + "\" relation \"" + relation.getObjName() + "\" does not specify the same number of columns as in the to index \"" + relation.getRequiredLookupToTable().getObjName() + "." + relation.getRequiredLookupToIndex().getObjName() + "\"\n" );
					valid = false;
				}
			}
		}
		if( valid ) {
			log.append( "Relations specify the same number of columns as their to/from indexes\n" );
		}
		else {
			modelIsValid = false;
		}
	}

	protected void validateChains( ICFBamSchemaDefObj schema ) {
		ICFBamTableObj worktable;
		Iterator<ICFBamTableObj> tableIter = schema.getOptionalComponentsTables().iterator();
		ICFBamTableObj table;
		Iterator<ICFBamChainObj> chainIter;
		ICFBamChainObj chain;
		Iterator<ICFBamRelationObj> relationIter;
		ICFBamRelationObj relation;
		ICFBamRelationObj superRelation;
		ICFBamRelationObj prevRelation;
		ICFBamRelationObj nextRelation;
		Iterator<ICFBamRelationColObj> relationColIter;
		ICFBamRelationColObj relationCol;
		String fromColName;
		String toColName;
		boolean valid = true;
		while( tableIter.hasNext() ) {
			table = tableIter.next();
			chainIter = table.getOptionalComponentsChains().iterator();
			if( chainIter.hasNext() ) {
				chain = chainIter.next();
				if( chainIter.hasNext() ) {
					log.append( "ERROR: Table \"" + table.getObjName() + "\" defines more than one chain\n" );
					valid = false;
				}
				worktable = table;
				while( worktable != null ) {
					superRelation = null;
					relationIter = worktable.getOptionalComponentsRelation().iterator();
					while( ( superRelation == null ) && relationIter.hasNext() ) {
						relation = relationIter.next();
						if( relation.getRequiredRelationType() == ICFBamSchema.RelationTypeEnum.Superclass ) {
							superRelation = relation;
						}
					}
					if( superRelation != null ) {
						worktable = superRelation.getRequiredLookupToTable();
						chainIter = worktable.getOptionalComponentsChains().iterator();
						if( chainIter.hasNext() ) {
							log.append( "ERROR: Table \"" + table.getObjName() + "\" overloads the chain definition from the table \"" + worktable.getObjName() + "\" -- only one chain can be defined by an object hierarchy\n" );
							valid = false;
						}
					}
					else {
						worktable = null;
					}
				}
				prevRelation = chain.getRequiredLookupPrevRel();
				nextRelation = chain.getRequiredLookupNextRel();
				if( ! prevRelation.getObjName().equals( "Prev" ) ) {
					log.append( "ERROR: Table \"" + table.getObjName() + "\" chain \"" + chain.getObjName() + "\" previous relation must be named \"Prev\"\n" );
					valid = false;
				}
				if( ! nextRelation.getObjName().equals( "Next" ) ) {
					log.append( "ERROR: Table \"" + table.getObjName() + "\" chain \"" + chain.getObjName() + "\" next relation must be named \"Next\"\n" );
					valid = false;
				}
				if( prevRelation.getRequiredIsRequired() ) {
					log.append( "ERROR: Table \"" + table.getObjName() + "\" chain \"" + chain.getObjName() + "\" previous relation must be optional\n" );
					valid = false;
				}
				if( nextRelation.getRequiredIsRequired() ) {
					log.append( "ERROR: Table \"" + table.getObjName() + "\" chain \"" + chain.getObjName() + "\" next relation must be optional\n" );
					valid = false;
				}
				if( prevRelation.getRequiredContainerFromTable() != table ) {
					log.append( "ERROR: Table \"" + table.getObjName() + "\" chain \"" + chain.getObjName() + "\" references a previous relation that is inherited by the table -- it must be defined by the table\n" );
					valid = false;
				}
				if( nextRelation.getRequiredContainerFromTable() != table ) {
					log.append( "ERROR: Table \"" + table.getObjName() + "\" chain \"" + chain.getObjName() + "\" references a next relation that is inherited by the table -- it must be defined by the table\n" );
					valid = false;
				}
				if( prevRelation.getRequiredLookupFromIndex().getRequiredContainerTable() != table ) {
					log.append( "ERROR: Table \"" + table.getObjName() + "\" chain \"" + chain.getObjName() + "\" references a previous relation whose from index is not defined by the table -- inherited indexes can not be referenced\n" );
					valid = false;
				}
				if( prevRelation.getRequiredLookupToIndex().getRequiredContainerTable() != table ) {
					log.append( "ERROR: Table \"" + table.getObjName() + "\" chain \"" + chain.getObjName() + "\" references a previous relation that does not target an index defined by the table -- inherited indexes can not be referenced\n" );
					valid = false;
				}
				if( nextRelation.getRequiredLookupFromIndex().getRequiredContainerTable() != table ) {
					log.append( "ERROR: Table \"" + table.getObjName() + "\" chain \"" + chain.getObjName() + "\" references a next relation whose from index is not defined by the table -- inherited indexes can not be referenced\n" );
					valid = false;
				}
				if( nextRelation.getRequiredLookupToIndex().getRequiredContainerTable() != table ) {
					log.append( "ERROR: Table \"" + table.getObjName() + "\" chain \"" + chain.getObjName() + "\" references a next relation that does not target an index defined by the table -- inherited indexes can not be referenced\n" );
					valid = false;
				}
				if( prevRelation.getRequiredLookupToIndex() != table.getOptionalLookupPrimaryIndex() ) {
					log.append( "ERROR: Table \"" + table.getObjName() + "\" chain \"" + chain.getObjName() + "\" references a previous relation that does not target the primary index of the table\n" );
					valid = false;
				}
				if( nextRelation.getRequiredLookupToIndex() != table.getOptionalLookupPrimaryIndex() ) {
					log.append( "ERROR: Table \"" + table.getObjName() + "\" chain \"" + chain.getObjName() + "\" references a next relation that does not target the primary index of the table\n" );
					valid = false;
				}
				relationColIter = prevRelation.getOptionalComponentsColumns().iterator();
				while( relationColIter.hasNext() ) {
					relationCol = relationColIter.next();
					if( ! relationCol.getRequiredLookupFromCol().getRequiredLookupColumn().getRequiredIsNullable() ) {
						log.append( "ERROR: Table \"" + table.getObjName() + "\" chain \"" + chain.getObjName() + "\" previous relation column \"" + relationCol.getObjName() + " references a required from column -- all from columns of the referenced previous relationship must be nullable\n" );
						valid = false;
					}
				}
				relationColIter = nextRelation.getOptionalComponentsColumns().iterator();
				while( relationColIter.hasNext() ) {
					relationCol = relationColIter.next();
					if( ! relationCol.getRequiredLookupFromCol().getRequiredLookupColumn().getRequiredIsNullable() ) {
						log.append( "ERROR: Table \"" + table.getObjName() + "\" chain \"" + chain.getObjName() + "\" next relation column \"" + relationCol.getObjName() + " references a required from column -- all from columns of the referenced next relationship must be nullable\n" );
						valid = false;
					}
				}
				relationColIter = prevRelation.getOptionalComponentsColumns().iterator();
				while( relationColIter.hasNext() ) {
					relationCol = relationColIter.next();
					fromColName = relationCol.getRequiredLookupFromCol().getObjName();
					toColName = relationCol.getRequiredLookupToCol().getObjName();
					if( ! fromColName.equals( "Prev" + toColName ) ) {
						log.append( "ERROR: Table \"" + table.getObjName() + "\" chain \"" + chain.getObjName() + "\" prev relation column \"" + relationCol.getObjName() + " from column name must be the to column name prepended by \"Prev\"; the same restriction applies to the database names\n" );
						valid = false;
					}
				}
				relationColIter = nextRelation.getOptionalComponentsColumns().iterator();
				while( relationColIter.hasNext() ) {
					relationCol = relationColIter.next();
					fromColName = relationCol.getRequiredLookupFromCol().getObjName();
					toColName = relationCol.getRequiredLookupToCol().getObjName();
					if( ! fromColName.equals( "Next" + toColName ) ) {
						log.append( "ERROR: Table \"" + table.getObjName() + "\" chain \"" + chain.getObjName() + "\" next relation column \"" + relationCol.getObjName() + " from column name must be the to column name prepended by \"Next\"; the same restriction applies to the database names\n" );
						valid = false;
					}
				}
			}
		}
		if( valid ) {
			log.append( "Chains are valid\n" );
		}
		else {
			modelIsValid = false;
		}
	}

	protected void propagateTypesAreNotNull( ICFBamSchemaDefObj schema ) {
		Iterator<ICFBamValueObj> typeIter = schema.getOptionalComponentsTypes().iterator();
		ICFBamValueObj type;
		ICFBamValueEditObj editType;
		while( typeIter.hasNext() ) {
			type = typeIter.next();
			if( type instanceof ICFBamBlobTypeObj ) {
				if( ! type.getRequiredIsNullable() ) {
					editType = type.beginEdit();
					editType.setRequiredIsNullable( true );
					editType.update();
					editType = null;
				}
			}
			else {
				if( type.getRequiredIsNullable() ) {
					editType = type.beginEdit();
					editType.setRequiredIsNullable( false );
					editType.update();
					editType = null;
				}
			}
		}
	}

	protected void propagateTableColumnNameChanges( ICFBamSchemaDefObj schema ) {
		Iterator<ICFBamTableObj> tableIter = schema.getOptionalComponentsTables().iterator();
		ICFBamTableObj table;
		Iterator<ICFBamIndexObj> indexIter;
		ICFBamIndexObj index;
		Iterator<ICFBamIndexColObj> indexColIter;
		ICFBamIndexColObj indexCol;
		ICFBamIndexColEditObj editIndexCol;
		ICFBamValueObj column;
		while( tableIter.hasNext() ) {
			table = tableIter.next();
			indexIter = table.getOptionalComponentsIndex().iterator();
			while( indexIter.hasNext() ) {
				index = indexIter.next();
				indexColIter = index.getOptionalComponentsColumns().iterator();
				while( indexColIter.hasNext() ) {
					indexCol = indexColIter.next();
					column = indexCol.getRequiredLookupColumn();
					if( ( ! indexCol.getRequiredName().equals( column.getRequiredName() ) )
						|| ( ( indexCol.getOptionalShortName() == null )
								&& ( column.getOptionalShortName() != null ) )
						|| ( ( indexCol.getOptionalShortName() != null )
								&& ( column.getOptionalShortName() == null ) )
						|| ( ( indexCol.getOptionalShortName() != null )
								&& ( column.getOptionalShortName() != null )
								&& ( ! indexCol.getOptionalShortName().equals( column.getOptionalShortName() ) ) ) 
						|| ( ( indexCol.getOptionalLabel() == null )
								&& ( column.getOptionalLabel() != null ) )
						|| ( ( indexCol.getOptionalLabel() != null )
								&& ( column.getOptionalLabel() == null ) )
						|| ( ( indexCol.getOptionalLabel() != null )
								&& ( column.getOptionalLabel() != null )
								&& ( ! indexCol.getOptionalLabel().equals( column.getOptionalLabel() ) ) ) 
						|| ( ( indexCol.getOptionalDescription() == null )
								&& ( column.getOptionalDescription() != null ) )
						|| ( ( indexCol.getOptionalDescription() != null )
								&& ( column.getOptionalDescription() == null ) )
						|| ( ( indexCol.getOptionalDescription() != null )
								&& ( column.getOptionalDescription() != null )
								&& ( ! indexCol.getOptionalDescription().equals( column.getOptionalDescription() ) ) ) 
						|| ( ( indexCol.getOptionalShortDescription() == null )
								&& ( column.getOptionalShortDescription() != null ) )
						|| ( ( indexCol.getOptionalShortDescription() != null )
								&& ( column.getOptionalShortDescription() == null ) )
						|| ( ( indexCol.getOptionalShortDescription() != null )
								&& ( column.getOptionalShortDescription() != null )
								&& ( ! indexCol.getOptionalShortDescription().equals( column.getOptionalShortDescription() ) ) ) )
					{
						editIndexCol = indexCol.beginEdit();
						editIndexCol.setRequiredName( column.getRequiredName() );
						editIndexCol.setOptionalShortName( column.getOptionalShortName() );
						editIndexCol.setOptionalDescription( column.getOptionalDescription() );
						editIndexCol.setOptionalLabel( column.getOptionalLabel() );
						editIndexCol.setOptionalShortDescription( column.getOptionalShortDescription() );
						editIndexCol.update();
						editIndexCol = null;
					}
				}
			}
		}
	}

	protected void propagateIndexColumnNameChanges( ICFBamSchemaDefObj schema ) {
		Iterator<ICFBamTableObj> tableIter = schema.getOptionalComponentsTables().iterator();
		ICFBamTableObj table;
		Iterator<ICFBamRelationObj> relationIter;
		ICFBamRelationObj relation;
		Iterator<ICFBamRelationColObj> relationColIter;
		ICFBamRelationColObj relationCol;
		ICFBamRelationColEditObj editRelationCol;
		ICFBamIndexColObj fromColumn;
		while( tableIter.hasNext() ) {
			table = tableIter.next();
			relationIter = table.getOptionalComponentsRelation().iterator();
			while( relationIter.hasNext() ) {
				relation = relationIter.next();
				relationColIter = relation.getOptionalComponentsColumns().iterator();
				while( relationColIter.hasNext() ) {
					relationCol = relationColIter.next();
					fromColumn = relationCol.getRequiredLookupFromCol();
					if( ( ! relationCol.getRequiredName().equals( fromColumn.getRequiredName() ) )
						|| ( ( relationCol.getOptionalLabel() == null )
								&& ( fromColumn.getOptionalLabel() != null ) )
						|| ( ( relationCol.getOptionalLabel() != null )
								&& ( fromColumn.getOptionalLabel() == null ) )
						|| ( ( relationCol.getOptionalLabel() != null )
								&& ( fromColumn.getOptionalLabel() != null )
								&& ( ! relationCol.getOptionalLabel().equals( fromColumn.getOptionalLabel() ) ) ) 
						|| ( ( relationCol.getOptionalDescription() == null )
								&& ( fromColumn.getOptionalDescription() != null ) )
						|| ( ( relationCol.getOptionalDescription() != null )
								&& ( fromColumn.getOptionalDescription() == null ) )
						|| ( ( relationCol.getOptionalDescription() != null )
								&& ( fromColumn.getOptionalDescription() != null )
								&& ( ! relationCol.getOptionalDescription().equals( fromColumn.getOptionalDescription() ) ) ) 
						|| ( ( relationCol.getOptionalShortName() == null )
								&& ( fromColumn.getOptionalShortName() != null ) )
						|| ( ( relationCol.getOptionalShortName() != null )
								&& ( fromColumn.getOptionalShortName() == null ) )
						|| ( ( relationCol.getOptionalShortName() != null )
								&& ( fromColumn.getOptionalShortName() != null )
								&& ( ! relationCol.getOptionalShortName().equals( fromColumn.getOptionalShortName() ) ) ) 
						|| ( ( relationCol.getOptionalShortDescription() == null )
								&& ( fromColumn.getOptionalShortDescription() != null ) )
						|| ( ( relationCol.getOptionalShortDescription() != null )
								&& ( fromColumn.getOptionalShortDescription() == null ) )
						|| ( ( relationCol.getOptionalShortDescription() != null )
								&& ( fromColumn.getOptionalShortDescription() != null )
								&& ( ! relationCol.getOptionalShortDescription().equals( fromColumn.getOptionalShortDescription() ) ) ) )
					{
						editRelationCol = relationCol.beginEdit();
						editRelationCol.setRequiredName( fromColumn.getRequiredName() );
						editRelationCol.setOptionalShortName( fromColumn.getOptionalShortName() );
						editRelationCol.setOptionalDescription( fromColumn.getOptionalDescription() );
						editRelationCol.setOptionalLabel( fromColumn.getOptionalLabel() );
						editRelationCol.setOptionalShortDescription( fromColumn.getOptionalShortDescription() );
						editRelationCol.update();
						editRelationCol = null;
					}
				}
			}
		}
	}
}
