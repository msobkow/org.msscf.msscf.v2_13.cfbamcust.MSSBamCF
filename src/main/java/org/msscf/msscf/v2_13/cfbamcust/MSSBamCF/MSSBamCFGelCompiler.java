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

import java.util.ArrayList;
import org.msscf.msscf.v2_13.cflib.CFLib.*;
import org.msscf.msscf.v2_13.cfsec.CFSecObj.ICFSecTenantObj;
import org.msscf.msscf.v2_13.cfcore.MssCF.*;
import org.msscf.msscf.v2_13.cfcore.CFGenKbObj.*;
import org.msscf.msscf.v2_13.cfbam.CFBamObj.*;

public class MSSBamCFGelCompiler
extends MssCFGelCompiler
{
	public final static String	_BUILTIN_PROJECTNAME = "ProjectName";
	public final static String _BUILTIN_JAVAPACKAGE = "JavaPackage";
	public final static String _BUILTIN_JAVADEFPACKAGE = "JavaDefPackage";
	public final static String _BUILTIN_GENPACKAGE = "GenPackage";
	public final static String	_BUILTIN_GENBASEPACKAGE = "GenBasePackage";
	public final static String	_BUILTIN_GENFULLPACKAGE = "GenFullPackage";
	public final static String _BUILTIN_GENPACKAGEDIR = "GenPackageDir";
	public final static String	_BUILTIN_GENPACKAGEFULLDIR = "GenPackageFullDir";
	public final static String _BUILTIN_CODEFACTORYVERSION = "CodeFactoryVersion";
	public final static String _BUILTIN_SCHEMAHOSTNAME = "SchemaHostName";
	public final static String _BUILTIN_SCHEMATWEAK = "schematweak";
	public final static String _BUILTIN_TABLETWEAK = "tabletweak";
	protected static String codeFactoryVersion = MSSBamCFEngine.LinkVersion;

	/**
	 *	Construct an instance.  Only invoked to create binding entries.
	 */
	public MSSBamCFGelCompiler( MSSBamCFEngine engine )	{
		super( engine );
	}

	public static void setCodeFactoryVersion( String value ) {
		final String S_ProcName = "setCodeFactoryVersion";
		if( ( value == null ) || ( value.length() <= 0 ) ) {
			throw new CFLibEmptyArgumentException( MSSBamCFGelCompiler.class,
				S_ProcName,
				1,
				"value" );
		}
		codeFactoryVersion = new String( value );
	}

	public static String getCodeFactoryVersion() {
		return( codeFactoryVersion );
	}

	protected class MssCFBuiltinCodeFactoryVersion
	extends CFGenKbGelBuiltinObj
	{
		public MssCFBuiltinCodeFactoryVersion( ICFGenKbSchemaObj schemaObj ) {
			super( schemaObj );
		}

		public String expand( MssCFGenContext genContext ) {
			String retval = MSSBamCFGelCompiler.getCodeFactoryVersion();
			return retval;
		}
	}

	protected class MssCFBuiltinProjectName
	extends CFGenKbGelBuiltinObj
	{
		public MssCFBuiltinProjectName( ICFGenKbSchemaObj schemaObj ) {
			super( schemaObj );
		}

		public String expand( MssCFGenContext genContext ) {
			String retval = null;
			ICFLibAnyObj anobj = genContext.getGenDef();
			ICFBamSchemaDefObj schema = MSSBamCFAnyObj.getSchema( anobj );
			ICFLibAnyObj project = MSSBamCFAnyObj.getProject( schema );
			if( ( project != null ) && ( project instanceof ICFBamTopProjectObj ) ) {
				retval = project.getObjName();
			}
			else if( ( project != null ) && ( project instanceof ICFBamSubProjectObj ) ) {
				retval = project.getObjName();
			}
			else {
				retval = schema.getObjName();
			}
			return retval;
		}
	}

	protected class MssCFBuiltinDefProjectName
	extends CFGenKbGelBuiltinObj
	{
		public MssCFBuiltinDefProjectName( ICFGenKbSchemaObj schemaObj ) {
			super( schemaObj );
		}

		public String expand( MssCFGenContext genContext ) {
			String retval = null;
			ICFLibAnyObj anobj = genContext.getGenDef();
			ICFLibAnyObj project = MSSBamCFAnyObj.getDefProject( anobj );
			if( ( project != null ) && ( project instanceof ICFBamTopProjectObj ) ) {
				retval = project.getObjName();
			}
			else if( ( project != null ) && ( project instanceof ICFBamSubProjectObj ) ) {
				retval = project.getObjName();
			}
			return retval;
		}
	}

	protected class MssCFBuiltinJavaDefPackage
	extends CFGenKbGelBuiltinObj
	{
		public MssCFBuiltinJavaDefPackage( ICFGenKbSchemaObj schemaObj ) {
			super( schemaObj );
		}

		public String expand( MssCFGenContext genContext ) {
			String ret = null;
			ICFLibAnyObj genObj;
			MssCFGenContext fileContext = genContext.getGenFileContext();
			if( fileContext != null) {
				genObj = (ICFLibAnyObj)(fileContext.getGenDef());
			}
			else {
				genObj = (ICFLibAnyObj)genContext.getGenDef();
			}

			if (genObj != null) {
				ICFLibAnyObj projectDef = MSSBamCFAnyObj.getDefProject(genObj);
				if (projectDef != null) {
					String projectDotName = MSSBamCFAnyObj.getModelName(projectDef);
					String versionString = MSSBamCFAnyObj.getVersionString(genObj);
					if ((versionString != null) && (versionString.length() > 0)) {
						switch (versionString.charAt(0)) {
							case '0':
								ret = projectDotName + ".v" + versionString.replace('-', '_');
								break;
							case '1':
								ret = projectDotName + ".v" + versionString.replace('-', '_');
								break;
							case '2':
								ret = projectDotName + ".v" + versionString.replace('-', '_');
								break;
							case '3':
								ret = projectDotName + ".v" + versionString.replace('-', '_');
								break;
							case '4':
								ret = projectDotName + ".v" + versionString.replace('-', '_');
								break;
							case '5':
								ret = projectDotName + ".v" + versionString.replace('-', '_');
								break;
							case '6':
								ret = projectDotName + ".v" + versionString.replace('-', '_');
								break;
							case '7':
								ret = projectDotName + ".v" + versionString.replace('-', '_');
								break;
							case '8':
								ret = projectDotName + ".v" + versionString.replace('-', '_');
								break;
							case '9':
								ret = projectDotName + ".v" + versionString.replace('-', '_');
								break;
							default:
								ret = projectDotName + "." + versionString.replace('-', '_');
								break;
						}
					}
					else {
						ret = projectDotName;
					}
				}
			}
			return( ret );
		}
	}

	protected class MssCFBuiltinJavaPackage
	extends CFGenKbGelBuiltinObj
	{
		public MssCFBuiltinJavaPackage( ICFGenKbSchemaObj schemaObj ) {
			super( schemaObj );
		}

		public String expand( MssCFGenContext genContext ) {
			ICFBamSchemaDefObj manufacturingSchema = MSSBamCFGenContext.getManufacturingSchema();
			ICFBamMinorVersionObj minorVersion = null;
			ICFBamMajorVersionObj majorVersion = null;
			ICFBamSubProjectObj subProject = null;
			ICFBamTopProjectObj topProject = null;
			ICFBamTopDomainObj topDomain = null;
			ICFBamTldObj tld = null;

			String ret = null;
			ICFLibAnyObj scopeDef = manufacturingSchema.getObjScope();
			while (scopeDef != null) {
				if (scopeDef instanceof ICFBamMajorVersionObj) {
					if (majorVersion == null) {
						majorVersion = (ICFBamMajorVersionObj)scopeDef;
					}
					else {
						throw new CFLibRuntimeException("MssCFBuiltinJavaPackage.expand() - Found multiple major version objects in scope chain.");
					}
					scopeDef = scopeDef.getObjScope();
				}
				else if (scopeDef instanceof ICFBamMinorVersionObj) {
					if (minorVersion == null) {
						minorVersion = (ICFBamMinorVersionObj)scopeDef;
					}
					else {
						throw new CFLibRuntimeException("MssCFBuiltinJavaPackage.expand() - Found multiple minor version objects in scope chain.");
					}
					scopeDef = scopeDef.getObjScope();
				}
				else if (scopeDef instanceof ICFBamSubProjectObj) {
					if (subProject == null) {
						subProject = (ICFBamSubProjectObj)scopeDef;
					}
					else {
						throw new CFLibRuntimeException("MssCFBuiltinJavaPackage.expand() - Found multiple sub-project objects in scope chain.");
					}
					scopeDef = scopeDef.getObjScope();
				}
				else if (scopeDef instanceof ICFBamTopProjectObj) {
					if (topProject == null) {
						topProject = (ICFBamTopProjectObj)scopeDef;
					}
					else {
						throw new CFLibRuntimeException("MssCFBuiltinJavaPackage.expand() - Found multiple top-project objects in scope chain.");
					}
					scopeDef = scopeDef.getObjScope();
				}
				else if (scopeDef instanceof ICFBamTopDomainObj) {
					if (topDomain == null) {
						topDomain = (ICFBamTopDomainObj)scopeDef;
					}
					else {
						throw new CFLibRuntimeException("MssCFBuiltinJavaPackage.expand() - Found multiple top-domain objects in scope chain.");
					}
					scopeDef = scopeDef.getObjScope();
				}
				else if (scopeDef instanceof ICFBamTldObj) {
					if (tld == null) {
						tld = (ICFBamTldObj)scopeDef;
					}
					else {
						throw new CFLibRuntimeException("MssCFBuiltinJavaPackage.expand() - Found multiple TLD objects in scope chain.");
					}
					scopeDef = null;
				}
				else if (scopeDef instanceof ICFSecTenantObj) {
					scopeDef = null;
				}
			}
			if (tld == null) {
				throw new CFLibRuntimeException("MssCFBuiltinJavaPackage.expand() - Failed to find TLD object in scope chain.");
			}
			if (topDomain == null) {
				throw new CFLibRuntimeException("MssCFBuiltinJavaPackage.expand() - Failed to find top-domain object in scope chain.");
			}
			String versionString;
			if (majorVersion != null) {
				if (minorVersion != null) {
					versionString = ".v" + majorVersion.getObjName().toLowerCase() + "_" + minorVersion.getObjName().toLowerCase();
				}
				else {
					versionString = ".v" + majorVersion.getObjName().toLowerCase();
				}
			}
			else {
				versionString = "";
			}
			if (topProject != null) {
				if (subProject != null) {
					ret = tld.getObjName().toLowerCase() + "." + topDomain.getObjName().toLowerCase() + "." + topProject.getObjName().toLowerCase() + versionString + "." + subProject.getObjName().toLowerCase();
				}
				else {
					ret = tld.getObjName().toLowerCase() + "." + topDomain.getObjName().toLowerCase() + "." + topProject.getObjName().toLowerCase() + versionString;
				}
			}
			else {
				ret = tld.getObjName().toLowerCase() + "." + topDomain.getObjName().toLowerCase() + versionString;
			}
			return( ret );
		}
	}

	protected class MssCFBuiltinGenPackage
	extends CFGenKbGelBuiltinObj
	{
		public MssCFBuiltinGenPackage( ICFGenKbSchemaObj schemaObj ) {
			super( schemaObj );
		}

		public String expand( MssCFGenContext genContext ) {
			String ret = null;
			MssCFGenContext fileContext = genContext.getGenFileContext();
			if( fileContext != null ) {
				ICFLibAnyObj tmp = (ICFLibAnyObj)(fileContext.getGenDef());
				if( tmp != null ) {
					ICFLibAnyObj pkg = MSSBamCFAnyObj.getPackage(tmp);
					if( pkg != null ) {
						ret = MSSBamCFAnyObj.getModelName( pkg );
					}
				}
			}
			return( ret );
		}
	}

	protected class MssCFBuiltinGenBasePackage
	extends CFGenKbGelBuiltinObj
	{
		public MssCFBuiltinGenBasePackage( ICFGenKbSchemaObj schemaObj ) {
			super( schemaObj );
		}

		public String expand( MssCFGenContext genContext ) {
			String ret = null;
			MssCFGenContext fileContext = genContext.getGenFileContext();
			if( fileContext != null ) {
				ICFLibAnyObj tmp = (ICFLibAnyObj)(fileContext.getGenDef());
				if( tmp != null ) {
					ICFLibAnyObj pkg = MSSBamCFAnyObj.getPackage(tmp);
					if( pkg != null ) {
						String pkgName = MSSBamCFAnyObj.getModelName( pkg );
						if( pkgName != null ) {
							String basePackageName = genContext.getGenBasePackageName();
							if( ( basePackageName != null ) && ( basePackageName.length() > 0 ) ) {
								ret = pkgName + "." + basePackageName;
							}
							else {
								ret = pkgName;
							}
						}
					}
				}
			}
			return( ret );
		}
	}

	protected class MssCFBuiltinGenFullPackage
	extends CFGenKbGelBuiltinObj
	{
		public MssCFBuiltinGenFullPackage( ICFGenKbSchemaObj schemaObj ) {
			super( schemaObj );
		}

		public String expand( MssCFGenContext genContext ) {
			final String S_ProcName = "MssCFBuiltingGenFullPackage.expand() ";
			String ret = null;
			ICFLibAnyObj genDef;
			MssCFGenContext fileContext = genContext.getGenFileContext();
			if( fileContext != null ) {
				genDef = (ICFLibAnyObj)(fileContext.getGenDef());
			}
			else {
				genDef = (ICFLibAnyObj)genContext.getGenDef();
			}
			ICFLibAnyObj pkg = MSSBamCFAnyObj.getPackage(genDef);
			if( pkg != null ) {
				String pkgName = MSSBamCFAnyObj.getFullName( pkg );
				if( pkgName == null ) {
					throw new RuntimeException( S_ProcName +  "GetFullName() returned null" );
				}
				String basePackageName = genContext.getGenBasePackageName();
				if( basePackageName != null ) {
					String subPackageName = genContext.getGenSubPackageName();
					if( ( subPackageName != null ) && ( subPackageName.length() > 0 ) ) {
						ret = pkgName + "." + basePackageName + "." + subPackageName;
					}
					else {
						ret = pkgName + "." + basePackageName;
					}
				}
				else {
					ret = pkgName;
				}
			}
			return( ret );
		}
	}

	protected class MssCFBuiltinGenPackageDir
	extends CFGenKbGelBuiltinObj
	{
		public MssCFBuiltinGenPackageDir( ICFGenKbSchemaObj schemaObj ) {
			super( schemaObj );
		}

		public String expand( MssCFGenContext genContext ) {
			String ret = null;
			MssCFGenContext fileContext = genContext.getGenFileContext();
			if( fileContext != null ) {
				String basePackageName = genContext.getGenBasePackageName();
				if( ( basePackageName != null ) && ( basePackageName.length() > 0 ) ) {
					String subPackageName = genContext.getGenSubPackageName();
					if( ( subPackageName != null ) && ( subPackageName.length() > 0 ) ) {
						String sourceBundle = genContext.getSourceBundle();
						if( ( sourceBundle != null ) && ( sourceBundle.length() > 0 ) ) {
							String unmassaged = sourceBundle + "." + basePackageName + "." + subPackageName;
							ret = unmassaged.replace( '.', '/' );
						}
						else {
							String unmassaged = basePackageName + "." + subPackageName;
							ret = unmassaged.replace( '.', '/' );
						}
					}
					else {
						String sourceBundle = genContext.getSourceBundle();
						if( ( sourceBundle != null ) && ( sourceBundle.length() > 0 ) ) {
							String unmassaged = sourceBundle + "." + basePackageName;
							ret = unmassaged.replace( '.', '/' );
						}
						else {
							String unmassaged = basePackageName;
							ret = unmassaged.replace( '.', '/' );
						}
					}
				}
				else {
					ret = "";
				}
			}
			return( ret );
		}
	}

	protected class MssCFBuiltinGenPackageFullDir
	extends CFGenKbGelBuiltinObj
	{
		public MssCFBuiltinGenPackageFullDir( ICFGenKbSchemaObj schemaObj ) {
			super( schemaObj );
		}

		public String expand( MssCFGenContext genContext ) {
			String ret = null;
			MssCFGenContext fileContext = genContext.getGenFileContext();
			if( fileContext != null ) {
				ICFLibAnyObj tmp = (ICFLibAnyObj)(fileContext.getGenDef());
				if( tmp != null ) {
					ICFLibAnyObj pkg = MSSBamCFAnyObj.getPackage(tmp);
					if( pkg != null ) {
						String pkgDir = MSSBamCFAnyObj.getModelName( pkg );
						if( pkgDir != null ) {
							if( tmp instanceof ICFBamSchemaObj ) {
								String		schemaPkg = ret + "." + tmp.getObjName().toLowerCase();
								pkgDir = schemaPkg;
							}

							String fileSep = "/";
							String rootGenDir = genContext.getRootGenDir();
							if( rootGenDir != null ) {
								if( fileSep.length() == 1 ) {
									String str =  rootGenDir + '/' + pkgDir;
									ret = str.replace( '/', fileSep.charAt( 0 ) );
								}
								else {
									ret =  rootGenDir + '/' + pkgDir;
								}
							}
							else {
								if( fileSep.length() == 1 ) {
									ret = pkgDir.replace( '/', fileSep.charAt( 0 ) );
								}
								else {
									ret =  rootGenDir + '/' + pkgDir;
								}
							}
						}
					}
				}
			}
			return( ret );
		}
	}

	protected class MssCFBuiltinSchemaHostName
	extends CFGenKbGelBuiltinObj
	{
		public MssCFBuiltinSchemaHostName( ICFGenKbSchemaObj schemaObj ) {
			super( schemaObj );
		}

		public String expand( MssCFGenContext genContext ) {
			String ret = null;
//			MssCFGenContext fileContext = genContext.getGenFileContext();
//			if( fileContext != null ) {
				ICFLibAnyObj tmp = (ICFLibAnyObj)(genContext.getGenDef());
				if( tmp != null ) {
					while ((tmp != null) && !(tmp instanceof ICFBamSchemaDefObj)) {
						tmp = tmp.getObjScope();
					}
					if ((tmp != null) && (tmp instanceof ICFBamSchemaDefObj)) {
						ICFBamSchemaDefObj schemaDef = (ICFBamSchemaDefObj)tmp;
						ret = schemaDef.getRequiredContainerMinorVersion().getRequiredContainerParentMajVer().getRequiredContainerParentSPrj().getRequiredContainerParentTPrj().getRequiredName()
							+ "." + schemaDef.getRequiredContainerMinorVersion().getRequiredContainerParentMajVer().getRequiredContainerParentSPrj().getRequiredContainerParentTPrj().getRequiredContainerParentSDom().getRequiredName()
						    + "." + schemaDef.getRequiredContainerMinorVersion().getRequiredContainerParentMajVer().getRequiredContainerParentSPrj().getRequiredContainerParentTPrj().getRequiredContainerParentSDom().getRequiredContainerParentTld().getRequiredName();
					}
					else {
						ret = System.getenv("HOSTNAME");
					}
				}
//			}
			return( ret );
		}
	}

	protected class MssCFBuiltinSchemaTweak
	extends CFGenKbGelBuiltinObj
	{
		protected final String tweakName;

		public MssCFBuiltinSchemaTweak( ICFGenKbSchemaObj schemaObj, String tweakName ) {
			super( schemaObj );
			this.tweakName = tweakName;
		}

		public String expand( MssCFGenContext genContext ) {
			String ret = null;

			ICFLibAnyObj genDef;
			final String S_ProcName = "expandSchemaTweak";

			if (genContext == null) {
				throw new CFLibNullArgumentException( getClass(),
					S_ProcName,
					1,
					"genContext" );
			}

			genDef = genContext.getGenDef();
			if (genDef == null) {
				throw new CFLibNullArgumentException( getClass(),
					S_ProcName,
					0,
					"genContext.GenDef" );
			}

			if( ! ( genDef instanceof ICFBamSchemaDefObj ) ) {
				throw new CFLibUnsupportedClassException( getClass(),
					S_ProcName,
					"genContext.GenDef",
					genDef,
					"ICFBamSchemaDefObj" );
			}

			ArrayList<ICFBamSchemaTweakObj> arrTweaks = new ArrayList<>();

			ICFBamSchemaDefObj schema = (ICFBamSchemaDefObj)genDef;
			for (ICFBamSchemaRefObj refSchema : schema.getOptionalComponentsSchemaRefs()) {
				ICFBamSchemaDefObj forSchema = refSchema.getOptionalLookupRefSchema();
				if (forSchema != null) {
					for (ICFBamTweakObj tweak : forSchema.getOptionalComponentsTweaks()) {
						ICFBamSchemaTweakObj rtweak = (ICFBamSchemaTweakObj)tweak;
						if (rtweak.getRequiredName().equals(tweakName)) {
							if (!arrTweaks.contains(rtweak)) {
								arrTweaks.add(rtweak);
							}
						}
					}
				}
			}
			for (ICFBamTweakObj tweak : schema.getOptionalComponentsTweaks()) {
				ICFBamSchemaTweakObj rtweak = (ICFBamSchemaTweakObj)tweak;
				if (rtweak.getRequiredName().equals(tweakName)) {
					if (!arrTweaks.contains(rtweak)) {
						arrTweaks.add(rtweak);
					}
				}
			}

			StringBuilder buff = new StringBuilder();
			for (ICFBamSchemaTweakObj tweak: arrTweaks) {
				if (tweak.getRequiredReplacesInherited()) {
					buff.setLength(0);
				}
				buff.append(tweak.getRequiredTweakGelText());
			}

			if(! buff.isEmpty()) {
				StringBuilder execName = new StringBuilder( genDef.getGenDefName() );
				execName.append( "::" );
				execName.append(	genDef.getObjFullName() );
				execName.append( "::" );
				execName.append( tweakName );
				try {
					ICFGenKbGelExecutableObj bin = genContext.getGenEngine().getGelCompiler().compileExecutable( execName.toString(), buff.toString(), null );
					ret = bin.expand( genContext );
				}
				catch (Throwable th) {
					genContext.getGenEngine().getLog().message("ERROR: Could not compile " + genDef.getObjFullName() + " schematweak " + execName.toString() + " text='" + buff.toString() + "' - " + th.getMessage());
					ret = "";
				}
			}
			else {
				ret = "";
			}

			return (ret);
		}
	}

	protected class MssCFBuiltinTableTweak
	extends CFGenKbGelBuiltinObj
	{
		protected final String tweakName;

		public MssCFBuiltinTableTweak( ICFGenKbSchemaObj schemaObj, String tweakName ) {
			super( schemaObj );
			this.tweakName = tweakName;
		}

		public String expand( MssCFGenContext genContext ) {
			String ret = null;

			ICFLibAnyObj genDef;
			final String S_ProcName = "expandTableTweak";

			if (genContext == null) {
				throw new CFLibNullArgumentException( getClass(),
					S_ProcName,
					1,
					"genContext" );
			}

			genDef = genContext.getGenDef();
			if (genDef == null) {
				throw new CFLibNullArgumentException( getClass(),
					S_ProcName,
					0,
					"genContext.GenDef" );
			}

			if( ! ( genDef instanceof ICFBamTableObj ) ) {
				throw new CFLibUnsupportedClassException( getClass(),
					S_ProcName,
					"genContext.GenDef",
					genDef,
					"ICFBamTableObj" );
			}

			ArrayList<ICFBamTableTweakObj> arrTweaks = new ArrayList<>();

			ICFBamTableObj table = (ICFBamTableObj)genDef;
			while (table != null) {
				for (ICFBamTweakObj tweak : table.getOptionalComponentsTweaks()) {
					ICFBamTableTweakObj rtweak = (ICFBamTableTweakObj)tweak;
					if (rtweak.getRequiredName().equals(tweakName)) {
						if (!arrTweaks.contains(rtweak)) {
							arrTweaks.addFirst(rtweak);
						}
					}
				}
				if (table.getOptionalDefSchemaTenantId() != null && table.getOptionalDefSchemaId() != null) {
					ICFBamSchemaDefObj schemaDef = table.getSchema().getSchemaDefTableObj().readSchemaDefByIdIdx(table.getOptionalDefSchemaTenantId(), table.getOptionalDefSchemaId());
					for( ICFBamTableObj curTable : schemaDef.getOptionalComponentsTables()) {
						if (curTable.getRequiredName().equals(table.getRequiredName())) {
							table = curTable;
							break;
						}
					}
				}
				else {
					table = null;
				}
			}

			StringBuilder buff = new StringBuilder();
			for (ICFBamTableTweakObj tweak: arrTweaks) {
				if (tweak.getRequiredReplacesInherited()) {
					buff.setLength(0);
				}
				buff.append(tweak.getRequiredTweakGelText());
			}

			if(! buff.isEmpty()) {
				StringBuilder execName = new StringBuilder( genDef.getGenDefName() );
				execName.append( "::" );
				execName.append(	genDef.getObjFullName() );
				execName.append( "::" );
				execName.append( tweakName );
				try {
					ICFGenKbGelExecutableObj bin = genContext.getGenEngine().getGelCompiler().compileExecutable( execName.toString(), buff.toString(), null );
					ret = bin.expand( genContext );
				}
				catch (Throwable th) {
					genContext.getGenEngine().getLog().message("ERROR: Could not compile " + genDef.getObjFullName() + " tabletweak " + execName.toString() + " text='" + buff.toString() + "' - " + th.getMessage());
					ret = "";
				}
			}
			else {
				ret = "";
			}

			return (ret);
		}
	}

	protected ICFGenKbGelInstructionObj compileMacro( String macro )
	{
		boolean noSuperCompiler = true;
		ICFGenKbGelInstructionObj ret = null;

		if( macro == null ) {
			ret = super.compileMacro( "" );
			noSuperCompiler = false;
		}
		else if( macro.length() == 0 ) {
			ret = super.compileMacro( macro );
			noSuperCompiler = false;
		}
		else if( macro.startsWith( _BUILTIN_SCHEMATWEAK)) {
			String afterKeyword = macro.substring(_BUILTIN_SCHEMATWEAK.length());
			int afterWhite = 0;
			while (afterWhite < afterKeyword.length() && Character.isWhitespace(afterKeyword.charAt(afterWhite))) {
				afterWhite ++;
			}
			if (afterWhite > 0 && afterWhite < afterKeyword.length()) {
				afterKeyword = afterKeyword.substring(afterWhite);
			}
			else if(afterWhite > 0) {
				afterKeyword = "";
			}
			if (!afterKeyword.isEmpty() && !afterKeyword.isBlank()) {
				ICFGenKbGelBuiltinObj builtinObj = new MssCFBuiltinSchemaTweak( genEngine, afterKeyword ); 
				ICFGenKbGelBuiltinEditObj builtinEdit = (ICFGenKbGelBuiltinEditObj)builtinObj.beginEdit();
				builtinEdit.setRequiredOwnerTenant( myGelCache.getRequiredOwnerTenant() );
				builtinEdit.setRequiredContainerGelCache( myGelCache );
				builtinEdit.setRequiredSourceText( macro );
				builtinObj = (ICFGenKbGelBuiltinObj)builtinEdit.create();
				ret = builtinObj;
			}
			else {
				ICFGenKbGelErrorObj errObj = new CFGenKbGelErrorObj(genEngine);
				ICFGenKbGelErrorEditObj errEdit = (ICFGenKbGelErrorEditObj)errObj.beginEdit();
				errEdit.setRequiredOwnerTenant( myGelCache.getRequiredOwnerTenant() );
				errEdit.setRequiredContainerGelCache( myGelCache );
				errEdit.setRequiredSourceText( macro );
				errObj = (ICFGenKbGelErrorObj)errEdit.create();
				ret = errObj;
			}
		}
		else if( macro.startsWith( _BUILTIN_TABLETWEAK)) {
			String afterKeyword = macro.substring(_BUILTIN_TABLETWEAK.length());
			int afterWhite = 0;
			while (afterWhite < afterKeyword.length() && Character.isWhitespace(afterKeyword.charAt(afterWhite))) {
				afterWhite ++;
			}
			if (afterWhite > 0) {
				if (afterWhite < afterKeyword.length()) {
					afterKeyword = afterKeyword.substring(afterWhite);
				}
				else {
					afterKeyword = "";
				}
			}
			else {
				afterKeyword = "";
			}
			if (!afterKeyword.isEmpty() && !afterKeyword.isBlank()) {
				ICFGenKbGelBuiltinObj builtinObj = new MssCFBuiltinTableTweak( genEngine, afterKeyword ); 
				ICFGenKbGelBuiltinEditObj builtinEdit = (ICFGenKbGelBuiltinEditObj)builtinObj.beginEdit();
				builtinEdit.setRequiredOwnerTenant( myGelCache.getRequiredOwnerTenant() );
				builtinEdit.setRequiredContainerGelCache( myGelCache );
				builtinEdit.setRequiredSourceText( macro );
				builtinObj = (ICFGenKbGelBuiltinObj)builtinEdit.create();
				ret = builtinObj;
			}
			else {
				ICFGenKbGelErrorObj errObj = new CFGenKbGelErrorObj(genEngine);
				ICFGenKbGelErrorEditObj errEdit = (ICFGenKbGelErrorEditObj)errObj.beginEdit();
				errEdit.setRequiredOwnerTenant( myGelCache.getRequiredOwnerTenant() );
				errEdit.setRequiredContainerGelCache( myGelCache );
				errEdit.setRequiredSourceText( macro );
				errObj = (ICFGenKbGelErrorObj)errEdit.create();
				ret = errObj;
			}
		}
		else if( macro.equals( _BUILTIN_CODEFACTORYVERSION ) ) {
			ICFGenKbGelBuiltinObj builtinObj = new MssCFBuiltinCodeFactoryVersion( genEngine ); 
			ICFGenKbGelBuiltinEditObj builtinEdit = (ICFGenKbGelBuiltinEditObj)builtinObj.beginEdit();
			builtinEdit.setRequiredOwnerTenant( myGelCache.getRequiredOwnerTenant() );
			builtinEdit.setRequiredContainerGelCache( myGelCache );
			builtinEdit.setRequiredSourceText( macro );
			builtinObj = (ICFGenKbGelBuiltinObj)builtinEdit.create();
			ret = builtinObj;
		}
		else if( macro.equals( _BUILTIN_PROJECTNAME ) ) {
			ICFGenKbGelBuiltinObj builtinObj = new MssCFBuiltinProjectName( genEngine ); 
			ICFGenKbGelBuiltinEditObj builtinEdit = (ICFGenKbGelBuiltinEditObj)builtinObj.beginEdit();
			builtinEdit.setRequiredOwnerTenant( myGelCache.getRequiredOwnerTenant() );
			builtinEdit.setRequiredContainerGelCache( myGelCache );
			builtinEdit.setRequiredSourceText( macro );
			builtinObj = (ICFGenKbGelBuiltinObj)builtinEdit.create();
			ret = builtinObj;
		}
		else if (macro.equals(_BUILTIN_JAVAPACKAGE)) {
			ICFGenKbGelBuiltinObj builtinObj = new MssCFBuiltinJavaPackage( genEngine ); 
			ICFGenKbGelBuiltinEditObj builtinEdit = (ICFGenKbGelBuiltinEditObj)builtinObj.beginEdit();
			builtinEdit.setRequiredOwnerTenant( myGelCache.getRequiredOwnerTenant() );
			builtinEdit.setRequiredContainerGelCache( myGelCache );
			builtinEdit.setRequiredSourceText( macro );
			builtinObj = (ICFGenKbGelBuiltinObj)builtinEdit.create();
			ret = builtinObj;
		}
		else if (macro.equals(_BUILTIN_JAVADEFPACKAGE)) {
			ICFGenKbGelBuiltinObj builtinObj = new MssCFBuiltinJavaDefPackage( genEngine ); 
			ICFGenKbGelBuiltinEditObj builtinEdit = (ICFGenKbGelBuiltinEditObj)builtinObj.beginEdit();
			builtinEdit.setRequiredOwnerTenant( myGelCache.getRequiredOwnerTenant() );
			builtinEdit.setRequiredContainerGelCache( myGelCache );
			builtinEdit.setRequiredSourceText( macro );
			builtinObj = (ICFGenKbGelBuiltinObj)builtinEdit.create();
			ret = builtinObj;
		}
		else if (macro.equals(_BUILTIN_GENPACKAGE)) {
			ICFGenKbGelBuiltinObj builtinObj = new MssCFBuiltinGenPackage( genEngine ); 
			ICFGenKbGelBuiltinEditObj builtinEdit = (ICFGenKbGelBuiltinEditObj)builtinObj.beginEdit();
			builtinEdit.setRequiredOwnerTenant( myGelCache.getRequiredOwnerTenant() );
			builtinEdit.setRequiredContainerGelCache( myGelCache );
			builtinEdit.setRequiredSourceText( macro );
			builtinObj = (ICFGenKbGelBuiltinObj)builtinEdit.create();
			ret = builtinObj;
		}
		else if( macro.equals( _BUILTIN_GENBASEPACKAGE ) ) {
			ICFGenKbGelBuiltinObj builtinObj = new MssCFBuiltinGenBasePackage( genEngine ); 
			ICFGenKbGelBuiltinEditObj builtinEdit = (ICFGenKbGelBuiltinEditObj)builtinObj.beginEdit();
			builtinEdit.setRequiredOwnerTenant( myGelCache.getRequiredOwnerTenant() );
			builtinEdit.setRequiredContainerGelCache( myGelCache );
			builtinEdit.setRequiredSourceText( macro );
			builtinObj = (ICFGenKbGelBuiltinObj)builtinEdit.create();
			ret = builtinObj;
		}
		else if( macro.equals( _BUILTIN_GENFULLPACKAGE ) ) {
			ICFGenKbGelBuiltinObj builtinObj = new MssCFBuiltinGenFullPackage( genEngine ); 
			ICFGenKbGelBuiltinEditObj builtinEdit = (ICFGenKbGelBuiltinEditObj)builtinObj.beginEdit();
			builtinEdit.setRequiredOwnerTenant( myGelCache.getRequiredOwnerTenant() );
			builtinEdit.setRequiredContainerGelCache( myGelCache );
			builtinEdit.setRequiredSourceText( macro );
			builtinObj = (ICFGenKbGelBuiltinObj)builtinEdit.create();
			ret = builtinObj;
		}
		else if( macro.equals( _BUILTIN_GENPACKAGEDIR ) ) {
			ICFGenKbGelBuiltinObj builtinObj = new MssCFBuiltinGenPackageDir( genEngine ); 
			ICFGenKbGelBuiltinEditObj builtinEdit = (ICFGenKbGelBuiltinEditObj)builtinObj.beginEdit();
			builtinEdit.setRequiredOwnerTenant( myGelCache.getRequiredOwnerTenant() );
			builtinEdit.setRequiredContainerGelCache( myGelCache );
			builtinEdit.setRequiredSourceText( macro );
			builtinObj = (ICFGenKbGelBuiltinObj)builtinEdit.create();
			ret = builtinObj;
		}
		else if( macro.equals( _BUILTIN_GENPACKAGEFULLDIR ) ) {
			ICFGenKbGelBuiltinObj builtinObj = new MssCFBuiltinGenPackageFullDir( genEngine ); 
			ICFGenKbGelBuiltinEditObj builtinEdit = (ICFGenKbGelBuiltinEditObj)builtinObj.beginEdit();
			builtinEdit.setRequiredOwnerTenant( myGelCache.getRequiredOwnerTenant() );
			builtinEdit.setRequiredContainerGelCache( myGelCache );
			builtinEdit.setRequiredSourceText( macro );
			builtinObj = (ICFGenKbGelBuiltinObj)builtinEdit.create();
			ret = builtinObj;
		}
		else if( macro.equals( _BUILTIN_SCHEMAHOSTNAME )) {
			ICFGenKbGelBuiltinObj builtinObj = new MssCFBuiltinSchemaHostName(genEngine);
			ICFGenKbGelBuiltinEditObj builtinEdit = (ICFGenKbGelBuiltinEditObj)builtinObj.beginEdit();
			builtinEdit.setRequiredOwnerTenant( myGelCache.getRequiredOwnerTenant() );
			builtinEdit.setRequiredContainerGelCache( myGelCache );
			builtinEdit.setRequiredSourceText( macro );
			builtinObj = (ICFGenKbGelBuiltinObj)builtinEdit.create();
			ret = builtinObj;
		}
		else {
			ret = super.compileMacro( macro );
			noSuperCompiler = false;
		}
		if( ( ret == null ) && noSuperCompiler ) {
			ret = super.compileMacro( macro );
		}
		return( ret );
	}
}
