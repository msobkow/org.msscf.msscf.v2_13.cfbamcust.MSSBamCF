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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.msscf.msscf.v2_13.cfcore.MssCF.*;
import org.msscf.msscf.v2_13.cfcore.CFGenKbObj.*;
import org.msscf.msscf.v2_13.cflib.CFLib.*;
import org.msscf.msscf.v2_13.cfbam.CFBamObj.*;
import org.msscf.msscf.v2_13.cfbam.CFBamMssCF.*;

public class MSSBamCFEngine
	extends CFBamMssCFEngine
{
	public final static String		LinkName = "MSSBamCF";
	public final static String		LinkVersion = "2.13.11195";

	public final static String		GeneratorName = "MSS Code Factory";
	public final static String		GeneratorVersion = LinkVersion;

	public final static String		ITEMNAME_TOP = "top";

	protected String rootGenDir = null;
	public String getRootGenDir() {
		return( rootGenDir );
	}

	public MssCFGelCompiler getGelCompiler() {
		if( gelCompiler == null ) {
			gelCompiler = new MSSBamCFGelCompiler( this );
		}
		return( gelCompiler );
	}

	/**
	 *	Get the display name for the engine.
	 *	<p>
	 *	Overload this with proper tool name.
	 */
	public String getGeneratorName() {
		return( GeneratorName );
	}

	public void generate(
		String					generatingBuild,
		String					rootGenDir,
		ICFLibAnyObj			anyDef,
		List<String>			toolsDesired,
		String					initialRuleName )
	{
		List<ICFBamSchemaDefObj> schemaList;
		MSSBamCFModelValidator validator = new MSSBamCFModelValidator();
		if( anyDef instanceof ICFBamMinorVersionObj ) {
			schemaList = ((ICFBamMinorVersionObj)anyDef).getOptionalComponentsSchemaDef();
		}
		else if( anyDef instanceof ICFBamSchemaDefObj ) {
			schemaList = new LinkedList<ICFBamSchemaDefObj>();
			schemaList.add( (ICFBamSchemaDefObj)anyDef );
		}
		else {
			getLog().message( "Cannot expand \"" + anyDef.getGenDefName() + "\" named \"" + anyDef.getObjFullName() + "\" -- only versions, projects, or schema defs can be manufactured" );
			return;
		}

		getLog().message( "Validating schemas..." );

		StringBuffer buff = new StringBuffer();
		boolean allSchemasValid = true;
		boolean schemaValid;
		ICFBamSchemaDefObj schemaDef;
		Iterator<ICFBamSchemaDefObj> iterSchema = schemaList.iterator();
		while( iterSchema.hasNext() ) {
			schemaDef = iterSchema.next();
			schemaValid = validator.validateSchemaDef( schemaDef );
			if( buff.length() > 0 ) {
				buff.append( "\n" );
				buff.append( validator.getValidationResults() );
			}
			else {
				buff.append( validator.getValidationResults() );
			}
			if( ! schemaValid ) {
				allSchemasValid = false;
			}
		}

		if( allSchemasValid ) {
			getLog().message( "Schema validation was successful" );
			super.generate( generatingBuild, rootGenDir, anyDef, toolsDesired, initialRuleName );
		}
		else {
			getLog().message( "Schema validation FAILED\n" + buff.toString() );
			getLog().message( "ERROR: Cannot generate code -- schema validation FAILED" );
			CFLib.beep();
		}
	}

	/*
	 *	Normally you'll want to overload this.
	 */
	public String getAnyClassName() {
		return( "Object" );
	}

	public String fixGenDefName( String rawName ) {
		return( rawName );
	}

	public void bootstrapFirst() {
		defineToolSet( "any" );
		getCFBamSchema().getId16GenTableObj().readId16GenByDispIdx( 0L, 0L );
		getCFBamSchema().getId32GenTableObj().readId32GenByDispIdx( 0L, 0L );
		getCFBamSchema().getId64GenTableObj().readId64GenByDispIdx( 0L, 0L );
	}

	// Constructors

	public MSSBamCFEngine() {
		super();
		setTargetRuleName( ITEMNAME_TOP );
	}

	public void init( String generatingBuild,
		ICFGenKbSchemaObj argCFGenKbSchema,
		ICFGenKbTenantObj	argTenant,
		ICFBamSchemaObj argBamSchema,
		String strRootGenDir )
	{
		setCFBamSchema( argBamSchema );
		init( generatingBuild, argCFGenKbSchema, argTenant, strRootGenDir );
	}

	/**
	 *	Initialize the generation engine and its knowledge base
	 */
	public void init( String generatingBuild, ICFGenKbSchemaObj argCFGenKbSchema, ICFGenKbTenantObj argTenant, String strRootGenDir ) {
		super.init( generatingBuild, argCFGenKbSchema, argTenant, strRootGenDir );
		setGenContext( getGenContextFactory().newGenContext( generatingBuild,
			this,
			strRootGenDir,
			"any",
			"Object",
			"Object",
			getTargetRuleName()));
	}

	public ICFGenKbGenFileTableObj getGenFileTableObj() {
		if( genFileTableObj == null ) {
			genFileTableObj = new MSSBamCFGenFileTableObj( this );
		}
		return( genFileTableObj );
	}

	public MssCFGenContextFactory getGenContextFactory() {
		if( genContextFactory == null ) {
			genContextFactory = new MSSBamCFGenContextFactory();
		}
		return( genContextFactory );
	}

	public ICFGenKbGenFileObj defineFile(
		String	definedNear,
		String	toolsetName,
		String	scopeDefClassName,
		String	genDefClassName,
		String	itemName,
		String	expansionBody,
		String	generateOnce,
		String	sourceBundle,
		String	moduleName,
		String	basePackageName,
		String	subPackageName,
		String	expansionClassName,
		String	expansionKeyName,
		String	expansionFileName )
	{
		return(
			super.defineFile( definedNear,
				toolsetName,
				fixGenDefName(scopeDefClassName),
				fixGenDefName(genDefClassName),
				itemName,
				expansionBody,
				generateOnce,
				sourceBundle,
				moduleName,
				basePackageName,
				subPackageName,
				expansionClassName,
				expansionKeyName,
				expansionFileName) );
	}

	public ICFGenKbGenRuleObj defineRule(
		String	definedNear,
		String	toolsetName,
		String	scopeDefClassName,
		String	genDefClassName,
		String	itemName,
		String	expansionBody )
	{
		return(
			super.defineRule( definedNear,
				toolsetName,
				fixGenDefName(scopeDefClassName),
				fixGenDefName(genDefClassName),
				itemName,
				expansionBody ) );
	}

	public ICFGenKbGenTruncObj defineTrunc(
		String	definedNear,
		String	toolsetName,
		String	scopeDefClassName,
		String	genDefClassName,
		String	itemName,
		String	expansionBody,
		int		truncAt  )
	{
		return(
			super.defineTrunc( definedNear,
				toolsetName,
				fixGenDefName(scopeDefClassName),
				fixGenDefName(genDefClassName),
				itemName,
				expansionBody,
				truncAt) );
	}

	public void bootstrapBindings() {
		super.bootstrapBindings();

		ICFGenKbGenBindObj	bind = null;
		ICFGenKbGenBindEditObj editBind = null;

        ICFGenKbRuleCartObj ruleCart = getInternalRuleCart();
        ICFGenKbRuleTypeObj ruleTypeBind = getRuleTypeTableObj().readRuleTypeByNameIdx("Bind");

        bind = new MSSBamCFGenBindCommonLicense( this, 
	        "any", null, "SchemaDef", "CommonLicense" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindClientImplLicense( this, 
	        "any", null, "SchemaDef", "ClientImplLicense" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindClientXFaceLicense( this, 
	        "any", null, "SchemaDef", "ClientXFaceLicense" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindServerImplLicense( this, 
	        "any", null, "SchemaDef", "ServerImplLicense" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindServerXFaceLicense( this, 
	        "any", null, "SchemaDef", "ServerXFaceLicense" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindEffLicenseBody( this, 
	        "any", null, "License", "EffLicenseBody" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHasScope( this, 
	        "any", null, "Object", "HasScope" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindIsSelfReferencingRelation( this, 
	        "any", null, "Relation", "IsSelfReferencingRelation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaMethodBody( this, 
	        "any", null, "ServerMethod", "JavaMethodBody" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusMethodBody( this, 
	        "any", null, "ServerMethod", "CPlusMethodBody" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpMethodBody( this, 
	        "any", null, "ServerMethod", "CSharpMethodBody" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHasServerMethod( this, 
	        "any", null, "Table", "HasServerMethod" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHasParam( this, 
	        "any", null, "ServerMethod", "HasParam" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHasPopDep( this, 
	        "any", null, "Relation", "HasPopDep" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHasNullableColumns( this, 
	        "any", null, "Index", "HasNullableColumns" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindIsSubObjectLookup( this, 
	        "any", null, "Relation", "IsSubObjectLookup" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindStripPrevNextColumnName( this, 
	        "any", null, "Value", "StripPrevNextColumnName" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHasDelSubDep( this, 
	        "any", null, "DelDep", "HasDelSubDep" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHasClearSubDep( this, 
	        "any", null, "ClearDep", "HasClearSubDep" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHasPopSubDep( this, 
	        "any", null, "PopDep", "HasPopSubDep" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHasSubObjectLookup( this, 
	        "any", null, "Table", "HasSubObjectLookup" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindColumnInChainNext( this, 
	        "any", null, "Value", "ColumnInChainNext" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindColumnInChainPrev( this, 
	        "any", null, "Value", "ColumnInChainPrev" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHasOwner( this, 
	        "any", null, "Object", "HasOwner" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindProjectDescription( this, 
	        "any", null, "Object", "ProjectDescription" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHasVersion( this, 
	        "any", null, "Object", "HasVersion" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindPackedVersion( this, 
	        "any", null, "Object", "PackedVersion" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindPackageName( this, 
	        "any", null, "Object", "PackageName" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindIsPrimaryIndex( this, 
	        "any", null, "Index", "IsPrimaryIndex" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindIsChainRelation( this, 
	        "any", null, "Relation", "IsChainRelation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindColumnInComponentsRelation( this, 
	        "any", null, "Value", "ColumnInComponentsRelation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindColumnInContainerOrNamedLookupRelation( this, 
	        "any", null, "Value", "ColumnInContainerOrNamedLookupRelation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindInheritsBlob( this, 
	        "any", null, "Table", "InheritsBlob" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindInheritsText( this, 
	        "any", null, "Table", "InheritsText" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHasAlternateIndex( this, 
	        "any", null, "Table", "HasAlternateIndex" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHasInheritedLookupIndex( this, 
	        "any", null, "Table", "HasInheritedLookupIndex" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHasChain( this, 
	        "any", null, "Table", "HasChain" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHasContainer( this, 
	        "any", null, "Table", "HasContainer" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHasPrimaryKey( this, 
	        "any", null, "Table", "HasPrimaryKey" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHasSubClasses( this, 
	        "any", null, "Table", "HasSubClasses" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHasSuperClassRelation( this, 
	        "any", null, "Table", "HasSuperClassRelation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindIsBaseTable( this, 
	        "any", null, "Table", "IsBaseTable" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindPrimaryKeyIsConstEnum( this, 
	        "any", null, "Table", "PrimaryKeyIsConstEnum" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindBaseModelAtomClass( this, 
	        "any", null, "Value", "BaseModelAtomClass" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindColumnInAnyRelation( this, 
	        "any", null, "Value", "ColumnInAnyRelation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindColumnInPrimaryIndex( this, 
	        "any", null, "Value", "ColumnInPrimaryIndex" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaDb2LUWSchemaObjImpl( this, 
	        "any", null, "SchemaDef", "JavaDb2LUWSchemaObjImpl" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaDb2LUWSchemaObjImport( this, 
	        "any", null, "SchemaDef", "JavaDb2LUWSchemaObjImport" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaDb2LUWSchemaObjMembers( this, 
	        "any", null, "SchemaDef", "JavaDb2LUWSchemaObjMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaMSSqlSchemaObjImpl( this, 
	        "any", null, "SchemaDef", "JavaMSSqlSchemaObjImpl" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaMSSqlSchemaObjImport( this, 
	        "any", null, "SchemaDef", "JavaMSSqlSchemaObjImport" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaMSSqlSchemaObjMembers( this, 
	        "any", null, "SchemaDef", "JavaMSSqlSchemaObjMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaMySqlSchemaObjImpl( this, 
	        "any", null, "SchemaDef", "JavaMySqlSchemaObjImpl" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaMySqlSchemaObjImport( this, 
	        "any", null, "SchemaDef", "JavaMySqlSchemaObjImport" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaMySqlSchemaObjMembers( this, 
	        "any", null, "SchemaDef", "JavaMySqlSchemaObjMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaOracleSchemaObjImpl( this, 
	        "any", null, "SchemaDef", "JavaOracleSchemaObjImpl" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaOracleSchemaObjImport( this, 
	        "any", null, "SchemaDef", "JavaOracleSchemaObjImport" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaOracleSchemaObjMembers( this, 
	        "any", null, "SchemaDef", "JavaOracleSchemaObjMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaPgSqlSchemaObjImpl( this, 
	        "any", null, "SchemaDef", "JavaPgSqlSchemaObjImpl" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaPgSqlSchemaObjImport( this, 
	        "any", null, "SchemaDef", "JavaPgSqlSchemaObjImport" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaPgSqlSchemaObjMembers( this, 
	        "any", null, "SchemaDef", "JavaPgSqlSchemaObjMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaRamSchemaObjImpl( this, 
	        "any", null, "SchemaDef", "JavaRamSchemaObjImpl" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaRamSchemaObjImport( this, 
	        "any", null, "SchemaDef", "JavaRamSchemaObjImport" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaRamSchemaObjMembers( this, 
	        "any", null, "SchemaDef", "JavaRamSchemaObjMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaSchemaObjImplementation( this, 
	        "any", null, "SchemaDef", "JavaSchemaObjImplementation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaSchemaObjImport( this, 
	        "any", null, "SchemaDef", "JavaSchemaObjImport" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaSchemaObjInterface( this, 
	        "any", null, "SchemaDef", "JavaSchemaObjInterface" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaSchemaObjMembers( this, 
	        "any", null, "SchemaDef", "JavaSchemaObjMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaXMsgClientSchemaImport( this, 
	        "any", null, "SchemaDef", "JavaXMsgClientSchemaImport" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaXMsgClientSchemaBody( this, 
	        "any", null, "SchemaDef", "JavaXMsgClientSchemaBody" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaXMsgRqstSchemaBody( this, 
	        "any", null, "SchemaDef", "JavaXMsgRqstSchemaBody" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaXMsgRqstSchemaImport( this, 
	        "any", null, "SchemaDef", "JavaXMsgRqstSchemaImport" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaXMsgRqstSchemaWireParsers( this, 
	        "any", null, "SchemaDef", "JavaXMsgRqstSchemaWireParsers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaXMsgRqstSchemaXsdElementList( this, 
	        "any", null, "SchemaDef", "JavaXMsgRqstSchemaXsdElementList" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaXMsgRqstSchemaXsdSpec( this, 
	        "any", null, "SchemaDef", "JavaXMsgRqstSchemaXsdSpec" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaXMsgRspnSchemaBody( this, 
	        "any", null, "SchemaDef", "JavaXMsgRspnSchemaBody" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaXMsgRspnSchemaImport( this, 
	        "any", null, "SchemaDef", "JavaXMsgRspnSchemaImport" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaXMsgRspnSchemaWireParsers( this, 
	        "any", null, "SchemaDef", "JavaXMsgRspnSchemaWireParsers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaXMsgRspnSchemaXsdElementList( this, 
	        "any", null, "SchemaDef", "JavaXMsgRspnSchemaXsdElementList" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaXMsgRspnSchemaXsdSpec( this, 
	        "any", null, "SchemaDef", "JavaXMsgRspnSchemaXsdSpec" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaXMsgSchemaFormatters( this, 
	        "any", null, "SchemaDef", "JavaXMsgSchemaFormatters" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaXMsgSchemaImport( this, 
	        "any", null, "SchemaDef", "JavaXMsgSchemaImport" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaDb2LUWTableImplementation( this, 
	        "any", null, "Table", "JavaDb2LUWTableImplementation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaDb2LUWTableImport( this, 
	        "any", null, "Table", "JavaDb2LUWTableImport" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaDb2LUWTableMembers( this, 
	        "any", null, "Table", "JavaDb2LUWTableMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaEditObjImplementation( this, 
	        "any", null, "Table", "JavaEditObjImplementation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaEditObjImport( this, 
	        "any", null, "Table", "JavaEditObjImport" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaEditObjInterface( this, 
	        "any", null, "Table", "JavaEditObjInterface" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaEditObjMembers( this, 
	        "any", null, "Table", "JavaEditObjMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaMSSqlTableImplementation( this, 
	        "any", null, "Table", "JavaMSSqlTableImplementation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaMSSqlTableImport( this, 
	        "any", null, "Table", "JavaMSSqlTableImport" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaMSSqlTableMembers( this, 
	        "any", null, "Table", "JavaMSSqlTableMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaMySqlTableImplementation( this, 
	        "any", null, "Table", "JavaMySqlTableImplementation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaMySqlTableImport( this, 
	        "any", null, "Table", "JavaMySqlTableImport" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaMySqlTableMembers( this, 
	        "any", null, "Table", "JavaMySqlTableMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaObjImplementation( this, 
	        "any", null, "Table", "JavaObjImplementation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaObjImport( this, 
	        "any", null, "Table", "JavaObjImport" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaObjInterface( this, 
	        "any", null, "Table", "JavaObjInterface" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaObjMembers( this, 
	        "any", null, "Table", "JavaObjMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaOracleTableImplementation( this, 
	        "any", null, "Table", "JavaOracleTableImplementation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaOracleTableImport( this, 
	        "any", null, "Table", "JavaOracleTableImport" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaOracleTableMembers( this, 
	        "any", null, "Table", "JavaOracleTableMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaPgSqlTableImplementation( this, 
	        "any", null, "Table", "JavaPgSqlTableImplementation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaPgSqlTableImport( this, 
	        "any", null, "Table", "JavaPgSqlTableImport" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaPgSqlTableMembers( this, 
	        "any", null, "Table", "JavaPgSqlTableMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaRamTableImplementation( this, 
	        "any", null, "Table", "JavaRamTableImplementation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaRamTableImport( this, 
	        "any", null, "Table", "JavaRamTableImport" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaRamTableMembers( this, 
	        "any", null, "Table", "JavaRamTableMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaSaxLoaderEndElement( this, 
	        "any", null, "Table", "JavaSaxLoaderEndElement" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaSaxLoaderImport( this, 
	        "any", null, "Table", "JavaSaxLoaderImport" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaSaxLoaderStartElement( this, 
	        "any", null, "Table", "JavaSaxLoaderStartElement" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaTableImplementation( this, 
	        "any", null, "Table", "JavaTableImplementation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaTableImport( this, 
	        "any", null, "Table", "JavaTableImport" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaTableInterface( this, 
	        "any", null, "Table", "JavaTableInterface" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaTableMembers( this, 
	        "any", null, "Table", "JavaTableMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaTableObjImplementation( this, 
	        "any", null, "Table", "JavaTableObjImplementation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaTableObjImport( this, 
	        "any", null, "Table", "JavaTableObjImport" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaTableObjInterface( this, 
	        "any", null, "Table", "JavaTableObjInterface" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaTableObjMembers( this, 
	        "any", null, "Table", "JavaTableObjMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaXMsgClientTableBody( this, 
	        "any", null, "Table", "JavaXMsgClientTableBody" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaXMsgClientTableImport( this, 
	        "any", null, "Table", "JavaXMsgClientTableImport" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaXMsgRqstTableBody( this, 
	        "any", null, "Table", "JavaXMsgRqstTableBody" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaXMsgRqstTableImport( this, 
	        "any", null, "Table", "JavaXMsgRqstTableImport" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaXMsgRspnTableBody( this, 
	        "any", null, "Table", "JavaXMsgRspnTableBody" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaXMsgRspnTableImport( this, 
	        "any", null, "Table", "JavaXMsgRspnTableImport" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaXMsgTableFormatters( this, 
	        "any", null, "Table", "JavaXMsgTableFormatters" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindJavaXMsgTableImport( this, 
	        "any", null, "Table", "JavaXMsgTableImport" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusDb2LUWSchemaObjImpl( this, 
	        "any", null, "SchemaDef", "CPlusDb2LUWSchemaObjImpl" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusDb2LUWSchemaObjInclude( this, 
	        "any", null, "SchemaDef", "CPlusDb2LUWSchemaObjInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusDb2LUWSchemaObjMembers( this, 
	        "any", null, "SchemaDef", "CPlusDb2LUWSchemaObjMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusMSSqlSchemaObjImpl( this, 
	        "any", null, "SchemaDef", "CPlusMSSqlSchemaObjImpl" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusMSSqlSchemaObjInclude( this, 
	        "any", null, "SchemaDef", "CPlusMSSqlSchemaObjInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusMSSqlSchemaObjMembers( this, 
	        "any", null, "SchemaDef", "CPlusMSSqlSchemaObjMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusMySqlSchemaObjImpl( this, 
	        "any", null, "SchemaDef", "CPlusMySqlSchemaObjImpl" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusMySqlSchemaObjInclude( this, 
	        "any", null, "SchemaDef", "CPlusMySqlSchemaObjInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusMySqlSchemaObjMembers( this, 
	        "any", null, "SchemaDef", "CPlusMySqlSchemaObjMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusOracleSchemaObjImpl( this, 
	        "any", null, "SchemaDef", "CPlusOracleSchemaObjImpl" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusOracleSchemaObjInclude( this, 
	        "any", null, "SchemaDef", "CPlusOracleSchemaObjInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusOracleSchemaObjMembers( this, 
	        "any", null, "SchemaDef", "CPlusOracleSchemaObjMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusPgSqlSchemaObjImpl( this, 
	        "any", null, "SchemaDef", "CPlusPgSqlSchemaObjImpl" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusPgSqlSchemaObjInclude( this, 
	        "any", null, "SchemaDef", "CPlusPgSqlSchemaObjInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusPgSqlSchemaObjMembers( this, 
	        "any", null, "SchemaDef", "CPlusPgSqlSchemaObjMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusRamSchemaObjImpl( this, 
	        "any", null, "SchemaDef", "CPlusRamSchemaObjImpl" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusRamSchemaObjInclude( this, 
	        "any", null, "SchemaDef", "CPlusRamSchemaObjInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusRamSchemaObjMembers( this, 
	        "any", null, "SchemaDef", "CPlusRamSchemaObjMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusSchemaObjImplementation( this, 
	        "any", null, "SchemaDef", "CPlusSchemaObjImplementation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusSchemaObjInclude( this, 
	        "any", null, "SchemaDef", "CPlusSchemaObjInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusSchemaObjInterface( this, 
	        "any", null, "SchemaDef", "CPlusSchemaObjInterface" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusSchemaObjMembers( this, 
	        "any", null, "SchemaDef", "CPlusSchemaObjMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusXMsgClientSchemaInclude( this, 
	        "any", null, "SchemaDef", "CPlusXMsgClientSchemaInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusXMsgClientSchemaBody( this, 
	        "any", null, "SchemaDef", "CPlusXMsgClientSchemaBody" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusXMsgRqstSchemaBody( this, 
	        "any", null, "SchemaDef", "CPlusXMsgRqstSchemaBody" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusXMsgRqstSchemaInclude( this, 
	        "any", null, "SchemaDef", "CPlusXMsgRqstSchemaInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusXMsgRqstSchemaWireParsers( this, 
	        "any", null, "SchemaDef", "CPlusXMsgRqstSchemaWireParsers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusXMsgRqstSchemaXsdElementList( this, 
	        "any", null, "SchemaDef", "CPlusXMsgRqstSchemaXsdElementList" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusXMsgRqstSchemaXsdSpec( this, 
	        "any", null, "SchemaDef", "CPlusXMsgRqstSchemaXsdSpec" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusXMsgRspnSchemaBody( this, 
	        "any", null, "SchemaDef", "CPlusXMsgRspnSchemaBody" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusXMsgRspnSchemaInclude( this, 
	        "any", null, "SchemaDef", "CPlusXMsgRspnSchemaInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusXMsgRspnSchemaWireParsers( this, 
	        "any", null, "SchemaDef", "CPlusXMsgRspnSchemaWireParsers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusXMsgRspnSchemaXsdElementList( this, 
	        "any", null, "SchemaDef", "CPlusXMsgRspnSchemaXsdElementList" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusXMsgRspnSchemaXsdSpec( this, 
	        "any", null, "SchemaDef", "CPlusXMsgRspnSchemaXsdSpec" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusXMsgSchemaFormatters( this, 
	        "any", null, "SchemaDef", "CPlusXMsgSchemaFormatters" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusXMsgSchemaInclude( this, 
	        "any", null, "SchemaDef", "CPlusXMsgSchemaInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusDb2LUWTableImplementation( this, 
	        "any", null, "Table", "CPlusDb2LUWTableImplementation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusDb2LUWTableInclude( this, 
	        "any", null, "Table", "CPlusDb2LUWTableInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusDb2LUWTableMembers( this, 
	        "any", null, "Table", "CPlusDb2LUWTableMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusEditObjImplementation( this, 
	        "any", null, "Table", "CPlusEditObjImplementation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusEditObjInclude( this, 
	        "any", null, "Table", "CPlusEditObjInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusEditObjInterface( this, 
	        "any", null, "Table", "CPlusEditObjInterface" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusEditObjMembers( this, 
	        "any", null, "Table", "CPlusEditObjMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusMSSqlTableImplementation( this, 
	        "any", null, "Table", "CPlusMSSqlTableImplementation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusMSSqlTableInclude( this, 
	        "any", null, "Table", "CPlusMSSqlTableInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusMSSqlTableMembers( this, 
	        "any", null, "Table", "CPlusMSSqlTableMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusMySqlTableImplementation( this, 
	        "any", null, "Table", "CPlusMySqlTableImplementation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusMySqlTableInclude( this, 
	        "any", null, "Table", "CPlusMySqlTableInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusMySqlTableMembers( this, 
	        "any", null, "Table", "CPlusMySqlTableMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusObjImplementation( this, 
	        "any", null, "Table", "CPlusObjImplementation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusObjInclude( this, 
	        "any", null, "Table", "CPlusObjInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusObjInterface( this, 
	        "any", null, "Table", "CPlusObjInterface" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusObjMembers( this, 
	        "any", null, "Table", "CPlusObjMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusOracleTableImplementation( this, 
	        "any", null, "Table", "CPlusOracleTableImplementation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusOracleTableInclude( this, 
	        "any", null, "Table", "CPlusOracleTableInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusOracleTableMembers( this, 
	        "any", null, "Table", "CPlusOracleTableMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusPgSqlTableImplementation( this, 
	        "any", null, "Table", "CPlusPgSqlTableImplementation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusPgSqlTableInclude( this, 
	        "any", null, "Table", "CPlusPgSqlTableInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusPgSqlTableMembers( this, 
	        "any", null, "Table", "CPlusPgSqlTableMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusRamTableImplementation( this, 
	        "any", null, "Table", "CPlusRamTableImplementation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusRamTableInclude( this, 
	        "any", null, "Table", "CPlusRamTableInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusRamTableMembers( this, 
	        "any", null, "Table", "CPlusRamTableMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusSaxLoaderEndElement( this, 
	        "any", null, "Table", "CPlusSaxLoaderEndElement" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusSaxLoaderInclude( this, 
	        "any", null, "Table", "CPlusSaxLoaderInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusSaxLoaderStartElement( this, 
	        "any", null, "Table", "CPlusSaxLoaderStartElement" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusTableImplementation( this, 
	        "any", null, "Table", "CPlusTableImplementation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusTableInclude( this, 
	        "any", null, "Table", "CPlusTableInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusTableInterface( this, 
	        "any", null, "Table", "CPlusTableInterface" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusTableMembers( this, 
	        "any", null, "Table", "CPlusTableMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusTableObjImplementation( this, 
	        "any", null, "Table", "CPlusTableObjImplementation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusTableObjInclude( this, 
	        "any", null, "Table", "CPlusTableObjInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusTableObjInterface( this, 
	        "any", null, "Table", "CPlusTableObjInterface" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusTableObjMembers( this, 
	        "any", null, "Table", "CPlusTableObjMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusXMsgClientTableBody( this, 
	        "any", null, "Table", "CPlusXMsgClientTableBody" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusXMsgClientTableInclude( this, 
	        "any", null, "Table", "CPlusXMsgClientTableInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusXMsgRqstTableBody( this, 
	        "any", null, "Table", "CPlusXMsgRqstTableBody" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusXMsgRqstTableInclude( this, 
	        "any", null, "Table", "CPlusXMsgRqstTableInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusXMsgRspnTableBody( this, 
	        "any", null, "Table", "CPlusXMsgRspnTableBody" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusXMsgRspnTableInclude( this, 
	        "any", null, "Table", "CPlusXMsgRspnTableInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusXMsgTableFormatters( this, 
	        "any", null, "Table", "CPlusXMsgTableFormatters" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCPlusXMsgTableInclude( this, 
	        "any", null, "Table", "CPlusXMsgTableInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusDb2LUWSchemaObjImpl( this, 
	        "any", null, "SchemaDef", "HPlusDb2LUWSchemaObjImpl" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusDb2LUWSchemaObjInclude( this, 
	        "any", null, "SchemaDef", "HPlusDb2LUWSchemaObjInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusDb2LUWSchemaObjMembers( this, 
	        "any", null, "SchemaDef", "HPlusDb2LUWSchemaObjMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusMSSqlSchemaObjImpl( this, 
	        "any", null, "SchemaDef", "HPlusMSSqlSchemaObjImpl" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusMSSqlSchemaObjInclude( this, 
	        "any", null, "SchemaDef", "HPlusMSSqlSchemaObjInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusMSSqlSchemaObjMembers( this, 
	        "any", null, "SchemaDef", "HPlusMSSqlSchemaObjMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusMySqlSchemaObjImpl( this, 
	        "any", null, "SchemaDef", "HPlusMySqlSchemaObjImpl" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusMySqlSchemaObjInclude( this, 
	        "any", null, "SchemaDef", "HPlusMySqlSchemaObjInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusMySqlSchemaObjMembers( this, 
	        "any", null, "SchemaDef", "HPlusMySqlSchemaObjMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusOracleSchemaObjImpl( this, 
	        "any", null, "SchemaDef", "HPlusOracleSchemaObjImpl" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusOracleSchemaObjInclude( this, 
	        "any", null, "SchemaDef", "HPlusOracleSchemaObjInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusOracleSchemaObjMembers( this, 
	        "any", null, "SchemaDef", "HPlusOracleSchemaObjMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusPgSqlSchemaObjImpl( this, 
	        "any", null, "SchemaDef", "HPlusPgSqlSchemaObjImpl" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusPgSqlSchemaObjInclude( this, 
	        "any", null, "SchemaDef", "HPlusPgSqlSchemaObjInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusPgSqlSchemaObjMembers( this, 
	        "any", null, "SchemaDef", "HPlusPgSqlSchemaObjMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusRamSchemaObjImpl( this, 
	        "any", null, "SchemaDef", "HPlusRamSchemaObjImpl" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusRamSchemaObjInclude( this, 
	        "any", null, "SchemaDef", "HPlusRamSchemaObjInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusRamSchemaObjMembers( this, 
	        "any", null, "SchemaDef", "HPlusRamSchemaObjMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusSchemaObjImplementation( this, 
	        "any", null, "SchemaDef", "HPlusSchemaObjImplementation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusSchemaObjInclude( this, 
	        "any", null, "SchemaDef", "HPlusSchemaObjInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusSchemaObjInterface( this, 
	        "any", null, "SchemaDef", "HPlusSchemaObjInterface" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusSchemaObjMembers( this, 
	        "any", null, "SchemaDef", "HPlusSchemaObjMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusXMsgClientSchemaInclude( this, 
	        "any", null, "SchemaDef", "HPlusXMsgClientSchemaInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusXMsgClientSchemaBody( this, 
	        "any", null, "SchemaDef", "HPlusXMsgClientSchemaBody" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusXMsgRqstSchemaBody( this, 
	        "any", null, "SchemaDef", "HPlusXMsgRqstSchemaBody" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusXMsgRqstSchemaInclude( this, 
	        "any", null, "SchemaDef", "HPlusXMsgRqstSchemaInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusXMsgRqstSchemaWireParsers( this, 
	        "any", null, "SchemaDef", "HPlusXMsgRqstSchemaWireParsers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusXMsgRqstSchemaXsdElementList( this, 
	        "any", null, "SchemaDef", "HPlusXMsgRqstSchemaXsdElementList" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusXMsgRqstSchemaXsdSpec( this, 
	        "any", null, "SchemaDef", "HPlusXMsgRqstSchemaXsdSpec" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusXMsgRspnSchemaBody( this, 
	        "any", null, "SchemaDef", "HPlusXMsgRspnSchemaBody" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusXMsgRspnSchemaInclude( this, 
	        "any", null, "SchemaDef", "HPlusXMsgRspnSchemaInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusXMsgRspnSchemaWireParsers( this, 
	        "any", null, "SchemaDef", "HPlusXMsgRspnSchemaWireParsers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusXMsgRspnSchemaXsdElementList( this, 
	        "any", null, "SchemaDef", "HPlusXMsgRspnSchemaXsdElementList" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusXMsgRspnSchemaXsdSpec( this, 
	        "any", null, "SchemaDef", "HPlusXMsgRspnSchemaXsdSpec" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusXMsgSchemaFormatters( this, 
	        "any", null, "SchemaDef", "HPlusXMsgSchemaFormatters" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusXMsgSchemaInclude( this, 
	        "any", null, "SchemaDef", "HPlusXMsgSchemaInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusDb2LUWTableImplementation( this, 
	        "any", null, "Table", "HPlusDb2LUWTableImplementation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusDb2LUWTableInclude( this, 
	        "any", null, "Table", "HPlusDb2LUWTableInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusDb2LUWTableMembers( this, 
	        "any", null, "Table", "HPlusDb2LUWTableMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusEditObjImplementation( this, 
	        "any", null, "Table", "HPlusEditObjImplementation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusEditObjInclude( this, 
	        "any", null, "Table", "HPlusEditObjInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusEditObjInterface( this, 
	        "any", null, "Table", "HPlusEditObjInterface" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusEditObjMembers( this, 
	        "any", null, "Table", "HPlusEditObjMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusMSSqlTableImplementation( this, 
	        "any", null, "Table", "HPlusMSSqlTableImplementation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusMSSqlTableInclude( this, 
	        "any", null, "Table", "HPlusMSSqlTableInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusMSSqlTableMembers( this, 
	        "any", null, "Table", "HPlusMSSqlTableMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusMySqlTableImplementation( this, 
	        "any", null, "Table", "HPlusMySqlTableImplementation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusMySqlTableInclude( this, 
	        "any", null, "Table", "HPlusMySqlTableInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusMySqlTableMembers( this, 
	        "any", null, "Table", "HPlusMySqlTableMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusObjImplementation( this, 
	        "any", null, "Table", "HPlusObjImplementation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusObjInclude( this, 
	        "any", null, "Table", "HPlusObjInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusObjInterface( this, 
	        "any", null, "Table", "HPlusObjInterface" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusObjMembers( this, 
	        "any", null, "Table", "HPlusObjMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusOracleTableImplementation( this, 
	        "any", null, "Table", "HPlusOracleTableImplementation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusOracleTableInclude( this, 
	        "any", null, "Table", "HPlusOracleTableInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusOracleTableMembers( this, 
	        "any", null, "Table", "HPlusOracleTableMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusPgSqlTableImplementation( this, 
	        "any", null, "Table", "HPlusPgSqlTableImplementation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusPgSqlTableInclude( this, 
	        "any", null, "Table", "HPlusPgSqlTableInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusPgSqlTableMembers( this, 
	        "any", null, "Table", "HPlusPgSqlTableMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusRamTableImplementation( this, 
	        "any", null, "Table", "HPlusRamTableImplementation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusRamTableInclude( this, 
	        "any", null, "Table", "HPlusRamTableInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusRamTableMembers( this, 
	        "any", null, "Table", "HPlusRamTableMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusSaxLoaderEndElement( this, 
	        "any", null, "Table", "HPlusSaxLoaderEndElement" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusSaxLoaderInclude( this, 
	        "any", null, "Table", "HPlusSaxLoaderInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusSaxLoaderStartElement( this, 
	        "any", null, "Table", "HPlusSaxLoaderStartElement" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusTableImplementation( this, 
	        "any", null, "Table", "HPlusTableImplementation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusTableInclude( this, 
	        "any", null, "Table", "HPlusTableInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusTableInterface( this, 
	        "any", null, "Table", "HPlusTableInterface" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusTableMembers( this, 
	        "any", null, "Table", "HPlusTableMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusTableObjImplementation( this, 
	        "any", null, "Table", "HPlusTableObjImplementation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusTableObjInclude( this, 
	        "any", null, "Table", "HPlusTableObjInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusTableObjInterface( this, 
	        "any", null, "Table", "HPlusTableObjInterface" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusTableObjMembers( this, 
	        "any", null, "Table", "HPlusTableObjMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusXMsgClientTableBody( this, 
	        "any", null, "Table", "HPlusXMsgClientTableBody" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusXMsgClientTableInclude( this, 
	        "any", null, "Table", "HPlusXMsgClientTableInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusXMsgRqstTableBody( this, 
	        "any", null, "Table", "HPlusXMsgRqstTableBody" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusXMsgRqstTableInclude( this, 
	        "any", null, "Table", "HPlusXMsgRqstTableInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusXMsgRspnTableBody( this, 
	        "any", null, "Table", "HPlusXMsgRspnTableBody" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusXMsgRspnTableInclude( this, 
	        "any", null, "Table", "HPlusXMsgRspnTableInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusXMsgTableFormatters( this, 
	        "any", null, "Table", "HPlusXMsgTableFormatters" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindHPlusXMsgTableInclude( this, 
	        "any", null, "Table", "HPlusXMsgTableInclude" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpDb2LUWSchemaObjImpl( this, 
	        "any", null, "SchemaDef", "CSharpDb2LUWSchemaObjImpl" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpDb2LUWSchemaObjUsing( this, 
	        "any", null, "SchemaDef", "CSharpDb2LUWSchemaObjUsing" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpDb2LUWSchemaObjMembers( this, 
	        "any", null, "SchemaDef", "CSharpDb2LUWSchemaObjMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpMSSqlSchemaObjImpl( this, 
	        "any", null, "SchemaDef", "CSharpMSSqlSchemaObjImpl" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpMSSqlSchemaObjUsing( this, 
	        "any", null, "SchemaDef", "CSharpMSSqlSchemaObjUsing" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpMSSqlSchemaObjMembers( this, 
	        "any", null, "SchemaDef", "CSharpMSSqlSchemaObjMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpMySqlSchemaObjImpl( this, 
	        "any", null, "SchemaDef", "CSharpMySqlSchemaObjImpl" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpMySqlSchemaObjUsing( this, 
	        "any", null, "SchemaDef", "CSharpMySqlSchemaObjUsing" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpMySqlSchemaObjMembers( this, 
	        "any", null, "SchemaDef", "CSharpMySqlSchemaObjMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpOracleSchemaObjImpl( this, 
	        "any", null, "SchemaDef", "CSharpOracleSchemaObjImpl" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpOracleSchemaObjUsing( this, 
	        "any", null, "SchemaDef", "CSharpOracleSchemaObjUsing" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpOracleSchemaObjMembers( this, 
	        "any", null, "SchemaDef", "CSharpOracleSchemaObjMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpPgSqlSchemaObjImpl( this, 
	        "any", null, "SchemaDef", "CSharpPgSqlSchemaObjImpl" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpPgSqlSchemaObjUsing( this, 
	        "any", null, "SchemaDef", "CSharpPgSqlSchemaObjUsing" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpPgSqlSchemaObjMembers( this, 
	        "any", null, "SchemaDef", "CSharpPgSqlSchemaObjMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpRamSchemaObjImpl( this, 
	        "any", null, "SchemaDef", "CSharpRamSchemaObjImpl" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpRamSchemaObjUsing( this, 
	        "any", null, "SchemaDef", "CSharpRamSchemaObjUsing" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpRamSchemaObjMembers( this, 
	        "any", null, "SchemaDef", "CSharpRamSchemaObjMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpSchemaObjImplementation( this, 
	        "any", null, "SchemaDef", "CSharpSchemaObjImplementation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpSchemaObjUsing( this, 
	        "any", null, "SchemaDef", "CSharpSchemaObjUsing" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpSchemaObjInterface( this, 
	        "any", null, "SchemaDef", "CSharpSchemaObjInterface" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpSchemaObjMembers( this, 
	        "any", null, "SchemaDef", "CSharpSchemaObjMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpXMsgClientSchemaUsing( this, 
	        "any", null, "SchemaDef", "CSharpXMsgClientSchemaUsing" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpXMsgClientSchemaBody( this, 
	        "any", null, "SchemaDef", "CSharpXMsgClientSchemaBody" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpXMsgRqstSchemaBody( this, 
	        "any", null, "SchemaDef", "CSharpXMsgRqstSchemaBody" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpXMsgRqstSchemaUsing( this, 
	        "any", null, "SchemaDef", "CSharpXMsgRqstSchemaUsing" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpXMsgRqstSchemaWireParsers( this, 
	        "any", null, "SchemaDef", "CSharpXMsgRqstSchemaWireParsers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpXMsgRqstSchemaXsdElementList( this, 
	        "any", null, "SchemaDef", "CSharpXMsgRqstSchemaXsdElementList" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpXMsgRqstSchemaXsdSpec( this, 
	        "any", null, "SchemaDef", "CSharpXMsgRqstSchemaXsdSpec" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpXMsgRspnSchemaBody( this, 
	        "any", null, "SchemaDef", "CSharpXMsgRspnSchemaBody" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpXMsgRspnSchemaUsing( this, 
	        "any", null, "SchemaDef", "CSharpXMsgRspnSchemaUsing" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpXMsgRspnSchemaWireParsers( this, 
	        "any", null, "SchemaDef", "CSharpXMsgRspnSchemaWireParsers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpXMsgRspnSchemaXsdElementList( this, 
	        "any", null, "SchemaDef", "CSharpXMsgRspnSchemaXsdElementList" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpXMsgRspnSchemaXsdSpec( this, 
	        "any", null, "SchemaDef", "CSharpXMsgRspnSchemaXsdSpec" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpXMsgSchemaFormatters( this, 
	        "any", null, "SchemaDef", "CSharpXMsgSchemaFormatters" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpXMsgSchemaUsing( this, 
	        "any", null, "SchemaDef", "CSharpXMsgSchemaUsing" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpDb2LUWTableImplementation( this, 
	        "any", null, "Table", "CSharpDb2LUWTableImplementation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpDb2LUWTableUsing( this, 
	        "any", null, "Table", "CSharpDb2LUWTableUsing" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpDb2LUWTableMembers( this, 
	        "any", null, "Table", "CSharpDb2LUWTableMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpEditObjImplementation( this, 
	        "any", null, "Table", "CSharpEditObjImplementation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpEditObjUsing( this, 
	        "any", null, "Table", "CSharpEditObjUsing" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpEditObjInterface( this, 
	        "any", null, "Table", "CSharpEditObjInterface" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpEditObjMembers( this, 
	        "any", null, "Table", "CSharpEditObjMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpMSSqlTableImplementation( this, 
	        "any", null, "Table", "CSharpMSSqlTableImplementation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpMSSqlTableUsing( this, 
	        "any", null, "Table", "CSharpMSSqlTableUsing" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpMSSqlTableMembers( this, 
	        "any", null, "Table", "CSharpMSSqlTableMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpMySqlTableImplementation( this, 
	        "any", null, "Table", "CSharpMySqlTableImplementation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpMySqlTableUsing( this, 
	        "any", null, "Table", "CSharpMySqlTableUsing" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpMySqlTableMembers( this, 
	        "any", null, "Table", "CSharpMySqlTableMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpObjImplementation( this, 
	        "any", null, "Table", "CSharpObjImplementation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpObjUsing( this, 
	        "any", null, "Table", "CSharpObjUsing" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpObjInterface( this, 
	        "any", null, "Table", "CSharpObjInterface" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpObjMembers( this, 
	        "any", null, "Table", "CSharpObjMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpOracleTableImplementation( this, 
	        "any", null, "Table", "CSharpOracleTableImplementation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpOracleTableUsing( this, 
	        "any", null, "Table", "CSharpOracleTableUsing" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpOracleTableMembers( this, 
	        "any", null, "Table", "CSharpOracleTableMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpPgSqlTableImplementation( this, 
	        "any", null, "Table", "CSharpPgSqlTableImplementation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpPgSqlTableUsing( this, 
	        "any", null, "Table", "CSharpPgSqlTableUsing" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpPgSqlTableMembers( this, 
	        "any", null, "Table", "CSharpPgSqlTableMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpRamTableImplementation( this, 
	        "any", null, "Table", "CSharpRamTableImplementation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpRamTableUsing( this, 
	        "any", null, "Table", "CSharpRamTableUsing" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpRamTableMembers( this, 
	        "any", null, "Table", "CSharpRamTableMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpSaxLoaderEndElement( this, 
	        "any", null, "Table", "CSharpSaxLoaderEndElement" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpSaxLoaderUsing( this, 
	        "any", null, "Table", "CSharpSaxLoaderUsing" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpSaxLoaderStartElement( this, 
	        "any", null, "Table", "CSharpSaxLoaderStartElement" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpTableImplementation( this, 
	        "any", null, "Table", "CSharpTableImplementation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpTableUsing( this, 
	        "any", null, "Table", "CSharpTableUsing" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpTableInterface( this, 
	        "any", null, "Table", "CSharpTableInterface" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpTableMembers( this, 
	        "any", null, "Table", "CSharpTableMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpTableObjImplementation( this, 
	        "any", null, "Table", "CSharpTableObjImplementation" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpTableObjUsing( this, 
	        "any", null, "Table", "CSharpTableObjUsing" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpTableObjInterface( this, 
	        "any", null, "Table", "CSharpTableObjInterface" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpTableObjMembers( this, 
	        "any", null, "Table", "CSharpTableObjMembers" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpXMsgClientTableBody( this, 
	        "any", null, "Table", "CSharpXMsgClientTableBody" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpXMsgClientTableUsing( this, 
	        "any", null, "Table", "CSharpXMsgClientTableUsing" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpXMsgRqstTableBody( this, 
	        "any", null, "Table", "CSharpXMsgRqstTableBody" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpXMsgRqstTableUsing( this, 
	        "any", null, "Table", "CSharpXMsgRqstTableUsing" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpXMsgRspnTableBody( this, 
	        "any", null, "Table", "CSharpXMsgRspnTableBody" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpXMsgRspnTableUsing( this, 
	        "any", null, "Table", "CSharpXMsgRspnTableUsing" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpXMsgTableFormatters( this, 
	        "any", null, "Table", "CSharpXMsgTableFormatters" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;

        bind = new MSSBamCFGenBindCSharpXMsgTableUsing( this, 
	        "any", null, "Table", "CSharpXMsgTableUsing" );
        editBind = (ICFGenKbGenBindEditObj)(bind.beginEdit());
        editBind.setRequiredContainerCartridge( ruleCart );
        editBind.setRequiredLookupRuleType( ruleTypeBind );
        editBind.create();
        editBind = null;
}

	public void bootstrapIterators() {
		super.bootstrapIterators();

		ICFGenKbGenIteratorObj	iterator = null;
		ICFGenKbGenIteratorEditObj editIterator = null;

        ICFGenKbRuleCartObj ruleCart = getInternalRuleCart();
        ICFGenKbRuleTypeObj ruleTypeIterator = getRuleTypeTableObj().readRuleTypeByNameIdx("Iterator");

        iterator = new MSSBamCFGenIterateTableRefs(this,  "any", null, "Table", "TableRefs" );
        editIterator = (ICFGenKbGenIteratorEditObj)iterator.beginEdit();
		((ICFGenKbGenItemEditObj)editIterator).setRequiredContainerCartridge( ruleCart );
		((ICFGenKbGenItemEditObj)editIterator).setRequiredLookupRuleType( ruleTypeIterator );
        editIterator.create();
        editIterator = null;

        iterator = new MSSBamCFGenIterateSubTableRefs(this,  "any", null, "Table", "SubTableRefs" );
        editIterator = (ICFGenKbGenIteratorEditObj)iterator.beginEdit();
		((ICFGenKbGenItemEditObj)editIterator).setRequiredContainerCartridge( ruleCart );
		((ICFGenKbGenItemEditObj)editIterator).setRequiredLookupRuleType( ruleTypeIterator );
        editIterator.create();
        editIterator = null;

        iterator = new MSSBamCFGenIterateReverseClearDeps(this,  "any", null, "Table", "ReverseClearDeps" );
        editIterator = (ICFGenKbGenIteratorEditObj)iterator.beginEdit();
		((ICFGenKbGenItemEditObj)editIterator).setRequiredContainerCartridge( ruleCart );
		((ICFGenKbGenItemEditObj)editIterator).setRequiredLookupRuleType( ruleTypeIterator );
        editIterator.create();
        editIterator = null;

        iterator = new MSSBamCFGenIterateReverseDelDeps(this,  "any", null, "Table", "ReverseDelDeps" );
        editIterator = (ICFGenKbGenIteratorEditObj)iterator.beginEdit();
		((ICFGenKbGenItemEditObj)editIterator).setRequiredContainerCartridge( ruleCart );
		((ICFGenKbGenItemEditObj)editIterator).setRequiredLookupRuleType( ruleTypeIterator );
        editIterator.create();
        editIterator = null;

        iterator = new MSSBamCFGenIterateSchemaTables(this,  "any", null, "SchemaDef", "SchemaTables" );
        editIterator = (ICFGenKbGenIteratorEditObj)iterator.beginEdit();
		((ICFGenKbGenItemEditObj)editIterator).setRequiredContainerCartridge( ruleCart );
		((ICFGenKbGenItemEditObj)editIterator).setRequiredLookupRuleType( ruleTypeIterator );
        editIterator.create();
        editIterator = null;

        iterator = new MSSBamCFGenIterateId16Generators(this,  "any", null, "SchemaDef", "Id16Generators" );
        editIterator = (ICFGenKbGenIteratorEditObj)iterator.beginEdit();
		((ICFGenKbGenItemEditObj)editIterator).setRequiredContainerCartridge( ruleCart );
		((ICFGenKbGenItemEditObj)editIterator).setRequiredLookupRuleType( ruleTypeIterator );
        editIterator.create();
        editIterator = null;

        iterator = new MSSBamCFGenIterateId32Generators(this,  "any", null, "SchemaDef", "Id32Generators" );
        editIterator = (ICFGenKbGenIteratorEditObj)iterator.beginEdit();
		((ICFGenKbGenItemEditObj)editIterator).setRequiredContainerCartridge( ruleCart );
		((ICFGenKbGenItemEditObj)editIterator).setRequiredLookupRuleType( ruleTypeIterator );
        editIterator.create();
        editIterator = null;

        iterator = new MSSBamCFGenIterateId64Generators(this,  "any", null, "SchemaDef", "Id64Generators" );
        editIterator = (ICFGenKbGenIteratorEditObj)iterator.beginEdit();
		((ICFGenKbGenItemEditObj)editIterator).setRequiredContainerCartridge( ruleCart );
		((ICFGenKbGenItemEditObj)editIterator).setRequiredLookupRuleType( ruleTypeIterator );
        editIterator.create();
        editIterator = null;

        iterator = new MSSBamCFGenIterateUuidGenerators(this,  "any", null, "SchemaDef", "UuidGenerators" );
        editIterator = (ICFGenKbGenIteratorEditObj)iterator.beginEdit();
		((ICFGenKbGenItemEditObj)editIterator).setRequiredContainerCartridge( ruleCart );
		((ICFGenKbGenItemEditObj)editIterator).setRequiredLookupRuleType( ruleTypeIterator );
        editIterator.create();
        editIterator = null;

        iterator = new MSSBamCFGenIterateContainerRelations(this,  "any", null, "Table", "ContainerRelations" );
        editIterator = (ICFGenKbGenIteratorEditObj)iterator.beginEdit();
		((ICFGenKbGenItemEditObj)editIterator).setRequiredContainerCartridge( ruleCart );
		((ICFGenKbGenItemEditObj)editIterator).setRequiredLookupRuleType( ruleTypeIterator );
        editIterator.create();
        editIterator = null;

        iterator = new MSSBamCFGenIterateGeneratedTableColumns(this,  "any", null, "Table", "GeneratedTableColumns" );
        editIterator = (ICFGenKbGenIteratorEditObj)iterator.beginEdit();
		((ICFGenKbGenItemEditObj)editIterator).setRequiredContainerCartridge( ruleCart );
		((ICFGenKbGenItemEditObj)editIterator).setRequiredLookupRuleType( ruleTypeIterator );
        editIterator.create();
        editIterator = null;

        iterator = new MSSBamCFGenIterateComponentCandidates(this,  "any", null, "Relation", "ComponentCandidates" );
        editIterator = (ICFGenKbGenIteratorEditObj)iterator.beginEdit();
		((ICFGenKbGenItemEditObj)editIterator).setRequiredContainerCartridge( ruleCart );
		((ICFGenKbGenItemEditObj)editIterator).setRequiredLookupRuleType( ruleTypeIterator );
        editIterator.create();
        editIterator = null;

        iterator = new MSSBamCFGenIterateDispensedId16Generators(this,  "any", null, "Table", "DispensedId16Generators" );
        editIterator = (ICFGenKbGenIteratorEditObj)iterator.beginEdit();
		((ICFGenKbGenItemEditObj)editIterator).setRequiredContainerCartridge( ruleCart );
		((ICFGenKbGenItemEditObj)editIterator).setRequiredLookupRuleType( ruleTypeIterator );
        editIterator.create();
        editIterator = null;

        iterator = new MSSBamCFGenIterateDispensedId32Generators(this,  "any", null, "Table", "DispensedId32Generators" );
        editIterator = (ICFGenKbGenIteratorEditObj)iterator.beginEdit();
		((ICFGenKbGenItemEditObj)editIterator).setRequiredContainerCartridge( ruleCart );
		((ICFGenKbGenItemEditObj)editIterator).setRequiredLookupRuleType( ruleTypeIterator );
        editIterator.create();
        editIterator = null;

        iterator = new MSSBamCFGenIterateDispensedId64Generators(this,  "any", null, "Table", "DispensedId64Generators" );
        editIterator = (ICFGenKbGenIteratorEditObj)iterator.beginEdit();
		((ICFGenKbGenItemEditObj)editIterator).setRequiredContainerCartridge( ruleCart );
		((ICFGenKbGenItemEditObj)editIterator).setRequiredLookupRuleType( ruleTypeIterator );
        editIterator.create();
        editIterator = null;

        iterator = new MSSBamCFGenIterateOnlyOwnerRelations(this,  "any", null, "Table", "OnlyOwnerRelations" );
        editIterator = (ICFGenKbGenIteratorEditObj)iterator.beginEdit();
		((ICFGenKbGenItemEditObj)editIterator).setRequiredContainerCartridge( ruleCart );
		((ICFGenKbGenItemEditObj)editIterator).setRequiredLookupRuleType( ruleTypeIterator );
        editIterator.create();
        editIterator = null;

        iterator = new MSSBamCFGenIterateDataColumns(this,  "any", null, "Table", "DataColumns" );
        editIterator = (ICFGenKbGenIteratorEditObj)iterator.beginEdit();
		((ICFGenKbGenItemEditObj)editIterator).setRequiredContainerCartridge( ruleCart );
		((ICFGenKbGenItemEditObj)editIterator).setRequiredLookupRuleType( ruleTypeIterator );
        editIterator.create();
        editIterator = null;

        iterator = new MSSBamCFGenIterateTableSubClasses(this,  "any", null, "Table", "TableSubClasses" );
        editIterator = (ICFGenKbGenIteratorEditObj)iterator.beginEdit();
		((ICFGenKbGenItemEditObj)editIterator).setRequiredContainerCartridge( ruleCart );
		((ICFGenKbGenItemEditObj)editIterator).setRequiredLookupRuleType( ruleTypeIterator );
        editIterator.create();
        editIterator = null;

        iterator = new MSSBamCFGenIterateTableDeepSubClasses(this,  "any", null, "Table", "TableDeepSubClasses" );
        editIterator = (ICFGenKbGenIteratorEditObj)iterator.beginEdit();
		((ICFGenKbGenItemEditObj)editIterator).setRequiredContainerCartridge( ruleCart );
		((ICFGenKbGenItemEditObj)editIterator).setRequiredLookupRuleType( ruleTypeIterator );
        editIterator.create();
        editIterator = null;
	}

	public void bootstrapReferences() {
		super.bootstrapReferences();
		ICFGenKbGenReferenceObj	reference = null;
		ICFGenKbGenReferenceEditObj editReference = null;

        ICFGenKbRuleCartObj ruleCart = this.getInternalRuleCart();
        ICFGenKbRuleTypeObj ruleTypeReference = this.getRuleTypeTableObj().readRuleTypeByNameIdx("Reference");

        reference = new MSSBamCFGenReferenceScope(this,  "any", null, "Object", "Scope" );
        editReference = (ICFGenKbGenReferenceEditObj)reference.beginEdit();
		((ICFGenKbGenItemEditObj)editReference).setRequiredContainerCartridge( ruleCart );
		((ICFGenKbGenItemEditObj)editReference).setRequiredLookupRuleType( ruleTypeReference );
        editReference.create();
        editReference = null;

        reference = new MSSBamCFGenReferenceChainContainerComponents(this,  "any", null, "Table", "ChainContainerComponents" );
        editReference = (ICFGenKbGenReferenceEditObj)reference.beginEdit();
		((ICFGenKbGenItemEditObj)editReference).setRequiredContainerCartridge( ruleCart );
		((ICFGenKbGenItemEditObj)editReference).setRequiredLookupRuleType( ruleTypeReference );
        editReference.create();
        editReference = null;

        reference = new MSSBamCFGenReferencePopTopDep(this,  "any", null, "Relation", "PopTopDep" );
        editReference = (ICFGenKbGenReferenceEditObj)reference.beginEdit();
		((ICFGenKbGenItemEditObj)editReference).setRequiredContainerCartridge( ruleCart );
		((ICFGenKbGenItemEditObj)editReference).setRequiredLookupRuleType( ruleTypeReference );
        editReference.create();
        editReference = null;

        reference = new MSSBamCFGenReferenceDelSubDep(this,  "any", null, "DelDep", "DelSubDep" );
        editReference = (ICFGenKbGenReferenceEditObj)reference.beginEdit();
		((ICFGenKbGenItemEditObj)editReference).setRequiredContainerCartridge( ruleCart );
		((ICFGenKbGenItemEditObj)editReference).setRequiredLookupRuleType( ruleTypeReference );
        editReference.create();
        editReference = null;

        reference = new MSSBamCFGenReferenceClearSubDep(this,  "any", null, "ClearDep", "ClearSubDep" );
        editReference = (ICFGenKbGenReferenceEditObj)reference.beginEdit();
		((ICFGenKbGenItemEditObj)editReference).setRequiredContainerCartridge( ruleCart );
		((ICFGenKbGenItemEditObj)editReference).setRequiredLookupRuleType( ruleTypeReference );
        editReference.create();
        editReference = null;

        reference = new MSSBamCFGenReferencePopSubDep(this,  "any", null, "PopDep", "PopSubDep" );
        editReference = (ICFGenKbGenReferenceEditObj)reference.beginEdit();
		((ICFGenKbGenItemEditObj)editReference).setRequiredContainerCartridge( ruleCart );
		((ICFGenKbGenItemEditObj)editReference).setRequiredLookupRuleType( ruleTypeReference );
        editReference.create();
        editReference = null;

        reference = new MSSBamCFGenReferenceIdGenResolverRelation(this,  "any", null, "TableCol", "IdGenResolverRelation" );
        editReference = (ICFGenKbGenReferenceEditObj)reference.beginEdit();
		((ICFGenKbGenItemEditObj)editReference).setRequiredContainerCartridge( ruleCart );
		((ICFGenKbGenItemEditObj)editReference).setRequiredLookupRuleType( ruleTypeReference );
        editReference.create();
        editReference = null;

        reference = new MSSBamCFGenReferenceSatisfyWidestLookupColumn(this,  "any", null, "IndexCol", "SatisfyWidestLookupColumn" );
        editReference = (ICFGenKbGenReferenceEditObj)reference.beginEdit();
		((ICFGenKbGenItemEditObj)editReference).setRequiredContainerCartridge( ruleCart );
		((ICFGenKbGenItemEditObj)editReference).setRequiredLookupRuleType( ruleTypeReference );
        editReference.create();
        editReference = null;

        reference = new MSSBamCFGenReferenceManufacturingSchema(this,  "any", null, "Object", "ManufacturingSchema" );
        editReference = (ICFGenKbGenReferenceEditObj)reference.beginEdit();
		((ICFGenKbGenItemEditObj)editReference).setRequiredContainerCartridge( ruleCart );
		((ICFGenKbGenItemEditObj)editReference).setRequiredLookupRuleType( ruleTypeReference );
        editReference.create();
        editReference = null;

        reference = new MSSBamCFGenReferenceClusterIdColumn(this,  "any", null, "Table", "ClusterIdColumn" );
        editReference = (ICFGenKbGenReferenceEditObj)reference.beginEdit();
		((ICFGenKbGenItemEditObj)editReference).setRequiredContainerCartridge( ruleCart );
		((ICFGenKbGenItemEditObj)editReference).setRequiredLookupRuleType( ruleTypeReference );
        editReference.create();
        editReference = null;

        reference = new MSSBamCFGenReferenceTenantIdColumn(this,  "any", null, "Table", "TenantIdColumn" );
        editReference = (ICFGenKbGenReferenceEditObj)reference.beginEdit();
		((ICFGenKbGenItemEditObj)editReference).setRequiredContainerCartridge( ruleCart );
		((ICFGenKbGenItemEditObj)editReference).setRequiredLookupRuleType( ruleTypeReference );
        editReference.create();
        editReference = null;

        reference = new MSSBamCFGenReferenceSuperClassRelation(this,  "any", null, "Table", "SuperClassRelation" );
        editReference = (ICFGenKbGenReferenceEditObj)reference.beginEdit();
		((ICFGenKbGenItemEditObj)editReference).setRequiredContainerCartridge( ruleCart );
		((ICFGenKbGenItemEditObj)editReference).setRequiredLookupRuleType( ruleTypeReference );
        editReference.create();
        editReference = null;

        reference = new MSSBamCFGenReferenceAlternateIndex(this,  "any", null, "Table", "AlternateIndex" );
        editReference = (ICFGenKbGenReferenceEditObj)reference.beginEdit();
		((ICFGenKbGenItemEditObj)editReference).setRequiredContainerCartridge( ruleCart );
		((ICFGenKbGenItemEditObj)editReference).setRequiredLookupRuleType( ruleTypeReference );
        editReference.create();
        editReference = null;

        reference = new MSSBamCFGenReferenceInheritedLookupIndex(this,  "any", null, "Table", "InheritedLookupIndex" );
        editReference = (ICFGenKbGenReferenceEditObj)reference.beginEdit();
		((ICFGenKbGenItemEditObj)editReference).setRequiredContainerCartridge( ruleCart );
		((ICFGenKbGenItemEditObj)editReference).setRequiredLookupRuleType( ruleTypeReference );
        editReference.create();
        editReference = null;

        reference = new MSSBamCFGenReferenceContainerRelation(this,  "any", null, "Table", "ContainerRelation" );
        editReference = (ICFGenKbGenReferenceEditObj)reference.beginEdit();
		((ICFGenKbGenItemEditObj)editReference).setRequiredContainerCartridge( ruleCart );
		((ICFGenKbGenItemEditObj)editReference).setRequiredLookupRuleType( ruleTypeReference );
        editReference.create();
        editReference = null;

        reference = new MSSBamCFGenReferenceOwnerRelation(this,  "any", null, "Table", "OwnerRelation" );
        editReference = (ICFGenKbGenReferenceEditObj)reference.beginEdit();
		((ICFGenKbGenItemEditObj)editReference).setRequiredContainerCartridge( ruleCart );
		((ICFGenKbGenItemEditObj)editReference).setRequiredLookupRuleType( ruleTypeReference );
        editReference.create();
        editReference = null;

        reference = new MSSBamCFGenReferenceBaseClass(this,  "any", null, "Table", "BaseClass" );
        editReference = (ICFGenKbGenReferenceEditObj)reference.beginEdit();
		((ICFGenKbGenItemEditObj)editReference).setRequiredContainerCartridge( ruleCart );
		((ICFGenKbGenItemEditObj)editReference).setRequiredLookupRuleType( ruleTypeReference );
        editReference.create();
        editReference = null;

        reference = new MSSBamCFGenReferencePrimaryKeyIndex(this,  "any", null, "Table", "PrimaryKeyIndex" );
        editReference = (ICFGenKbGenReferenceEditObj)reference.beginEdit();
		((ICFGenKbGenItemEditObj)editReference).setRequiredContainerCartridge( ruleCart );
		((ICFGenKbGenItemEditObj)editReference).setRequiredLookupRuleType( ruleTypeReference );
        editReference.create();
        editReference = null;

        reference = new MSSBamCFGenReferenceOwnerContainerOrNamedLookupRelationCol(this,  "any", null, "Object", "OwnerContainerOrNamedLookupRelationCol" );
        editReference = (ICFGenKbGenReferenceEditObj)reference.beginEdit();
		((ICFGenKbGenItemEditObj)editReference).setRequiredContainerCartridge( ruleCart );
		((ICFGenKbGenItemEditObj)editReference).setRequiredLookupRuleType( ruleTypeReference );
        editReference.create();
        editReference = null;

        reference = new MSSBamCFGenReferenceObjNameColumn(this,  "any", null, "Value", "ObjNameColumn" );
        editReference = (ICFGenKbGenReferenceEditObj)reference.beginEdit();
		((ICFGenKbGenItemEditObj)editReference).setRequiredContainerCartridge( ruleCart );
		((ICFGenKbGenItemEditObj)editReference).setRequiredLookupRuleType( ruleTypeReference );
        editReference.create();
        editReference = null;

        reference = new MSSBamCFGenReferenceObjNameColumn(this,  "any", null, "Table", "ObjNameColumn" );
        editReference = (ICFGenKbGenReferenceEditObj)reference.beginEdit();
		((ICFGenKbGenItemEditObj)editReference).setRequiredContainerCartridge( ruleCart );
		((ICFGenKbGenItemEditObj)editReference).setRequiredLookupRuleType( ruleTypeReference );
        editReference.create();
        editReference = null;

        reference = new MSSBamCFGenReferenceChain(this,  "any", null, "Table", "Chain" );
        editReference = (ICFGenKbGenReferenceEditObj)reference.beginEdit();
		((ICFGenKbGenItemEditObj)editReference).setRequiredContainerCartridge( ruleCart );
		((ICFGenKbGenItemEditObj)editReference).setRequiredLookupRuleType( ruleTypeReference );
        editReference.create();
        editReference = null;

        reference = new MSSBamCFGenReferenceObjNameColumn(this,  "any", null, "Index", "ObjNameColumn" );
        editReference = (ICFGenKbGenReferenceEditObj)reference.beginEdit();
		((ICFGenKbGenItemEditObj)editReference).setRequiredContainerCartridge( ruleCart );
		((ICFGenKbGenItemEditObj)editReference).setRequiredLookupRuleType( ruleTypeReference );
        editReference.create();
        editReference = null;

        reference = new MSSBamCFGenReferenceObjNameColumn(this,  "any", null, "IndexCol", "ObjNameColumn" );
        editReference = (ICFGenKbGenReferenceEditObj)reference.beginEdit();
		((ICFGenKbGenItemEditObj)editReference).setRequiredContainerCartridge( ruleCart );
		((ICFGenKbGenItemEditObj)editReference).setRequiredLookupRuleType( ruleTypeReference );
        editReference.create();
        editReference = null;
	}
}
