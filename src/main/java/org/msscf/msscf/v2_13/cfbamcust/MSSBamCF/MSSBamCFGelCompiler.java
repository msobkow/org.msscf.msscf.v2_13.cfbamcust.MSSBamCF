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

import org.msscf.msscf.v2_13.cflib.CFLib.*;
import org.msscf.msscf.v2_13.cfcore.MssCF.*;
import org.msscf.msscf.v2_13.cfcore.CFGenKbObj.*;
import org.msscf.msscf.v2_13.cfbam.CFBam.*;
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
			String ret = null;

			ICFBamSchemaDefObj manufacturingSchema = MSSBamCFGenContext.getManufacturingSchema();
			ICFLibAnyObj projectDef = MSSBamCFAnyObj.getProject( manufacturingSchema );
			if (projectDef != null) {
				ret = MSSBamCFAnyObj.getModelName(projectDef);
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
		else if( macro.equals( _BUILTIN_CODEFACTORYVERSION ) ) {
			ICFGenKbGelBuiltinObj builtinObj = new MssCFBuiltinCodeFactoryVersion( genEngine ); 
			ICFGenKbGelBuiltinEditObj builtinEdit = (ICFGenKbGelBuiltinEditObj)builtinObj.beginEdit();
			builtinEdit.setRequiredOwnerTenant( myGelCache.getRequiredOwnerTenant() );
			builtinEdit.setRequiredContainerGelCache( myGelCache );
			builtinEdit.setRequiredSourceText( macro );
			builtinObj = (ICFGenKbGelBuiltinObj)builtinEdit.create();
			builtinEdit = null;
			ret = builtinObj;
		}
		else if( macro.equals( _BUILTIN_PROJECTNAME ) ) {
			ICFGenKbGelBuiltinObj builtinObj = new MssCFBuiltinProjectName( genEngine ); 
			ICFGenKbGelBuiltinEditObj builtinEdit = (ICFGenKbGelBuiltinEditObj)builtinObj.beginEdit();
			builtinEdit.setRequiredOwnerTenant( myGelCache.getRequiredOwnerTenant() );
			builtinEdit.setRequiredContainerGelCache( myGelCache );
			builtinEdit.setRequiredSourceText( macro );
			builtinObj = (ICFGenKbGelBuiltinObj)builtinEdit.create();
			builtinEdit = null;
			ret = builtinObj;
		}
		else if (macro.equals(_BUILTIN_JAVAPACKAGE)) {
			ICFGenKbGelBuiltinObj builtinObj = new MssCFBuiltinJavaPackage( genEngine ); 
			ICFGenKbGelBuiltinEditObj builtinEdit = (ICFGenKbGelBuiltinEditObj)builtinObj.beginEdit();
			builtinEdit.setRequiredOwnerTenant( myGelCache.getRequiredOwnerTenant() );
			builtinEdit.setRequiredContainerGelCache( myGelCache );
			builtinEdit.setRequiredSourceText( macro );
			builtinObj = (ICFGenKbGelBuiltinObj)builtinEdit.create();
			builtinEdit = null;
			ret = builtinObj;
		}
		else if (macro.equals(_BUILTIN_JAVADEFPACKAGE)) {
			ICFGenKbGelBuiltinObj builtinObj = new MssCFBuiltinJavaDefPackage( genEngine ); 
			ICFGenKbGelBuiltinEditObj builtinEdit = (ICFGenKbGelBuiltinEditObj)builtinObj.beginEdit();
			builtinEdit.setRequiredOwnerTenant( myGelCache.getRequiredOwnerTenant() );
			builtinEdit.setRequiredContainerGelCache( myGelCache );
			builtinEdit.setRequiredSourceText( macro );
			builtinObj = (ICFGenKbGelBuiltinObj)builtinEdit.create();
			builtinEdit = null;
			ret = builtinObj;
		}
		else if (macro.equals(_BUILTIN_GENPACKAGE)) {
			ICFGenKbGelBuiltinObj builtinObj = new MssCFBuiltinGenPackage( genEngine ); 
			ICFGenKbGelBuiltinEditObj builtinEdit = (ICFGenKbGelBuiltinEditObj)builtinObj.beginEdit();
			builtinEdit.setRequiredOwnerTenant( myGelCache.getRequiredOwnerTenant() );
			builtinEdit.setRequiredContainerGelCache( myGelCache );
			builtinEdit.setRequiredSourceText( macro );
			builtinObj = (ICFGenKbGelBuiltinObj)builtinEdit.create();
			builtinEdit = null;
			ret = builtinObj;
		}
		else if( macro.equals( _BUILTIN_GENBASEPACKAGE ) ) {
			ICFGenKbGelBuiltinObj builtinObj = new MssCFBuiltinGenBasePackage( genEngine ); 
			ICFGenKbGelBuiltinEditObj builtinEdit = (ICFGenKbGelBuiltinEditObj)builtinObj.beginEdit();
			builtinEdit.setRequiredOwnerTenant( myGelCache.getRequiredOwnerTenant() );
			builtinEdit.setRequiredContainerGelCache( myGelCache );
			builtinEdit.setRequiredSourceText( macro );
			builtinObj = (ICFGenKbGelBuiltinObj)builtinEdit.create();
			builtinEdit = null;
			ret = builtinObj;
		}
		else if( macro.equals( _BUILTIN_GENFULLPACKAGE ) ) {
			ICFGenKbGelBuiltinObj builtinObj = new MssCFBuiltinGenFullPackage( genEngine ); 
			ICFGenKbGelBuiltinEditObj builtinEdit = (ICFGenKbGelBuiltinEditObj)builtinObj.beginEdit();
			builtinEdit.setRequiredOwnerTenant( myGelCache.getRequiredOwnerTenant() );
			builtinEdit.setRequiredContainerGelCache( myGelCache );
			builtinEdit.setRequiredSourceText( macro );
			builtinObj = (ICFGenKbGelBuiltinObj)builtinEdit.create();
			builtinEdit = null;
			ret = builtinObj;
		}
		else if( macro.equals( _BUILTIN_GENPACKAGEDIR ) ) {
			ICFGenKbGelBuiltinObj builtinObj = new MssCFBuiltinGenPackageDir( genEngine ); 
			ICFGenKbGelBuiltinEditObj builtinEdit = (ICFGenKbGelBuiltinEditObj)builtinObj.beginEdit();
			builtinEdit.setRequiredOwnerTenant( myGelCache.getRequiredOwnerTenant() );
			builtinEdit.setRequiredContainerGelCache( myGelCache );
			builtinEdit.setRequiredSourceText( macro );
			builtinObj = (ICFGenKbGelBuiltinObj)builtinEdit.create();
			builtinEdit = null;
			ret = builtinObj;
		}
		else if( macro.equals( _BUILTIN_GENPACKAGEFULLDIR ) ) {
			ICFGenKbGelBuiltinObj builtinObj = new MssCFBuiltinGenPackageFullDir( genEngine ); 
			ICFGenKbGelBuiltinEditObj builtinEdit = (ICFGenKbGelBuiltinEditObj)builtinObj.beginEdit();
			builtinEdit.setRequiredOwnerTenant( myGelCache.getRequiredOwnerTenant() );
			builtinEdit.setRequiredContainerGelCache( myGelCache );
			builtinEdit.setRequiredSourceText( macro );
			builtinObj = (ICFGenKbGelBuiltinObj)builtinEdit.create();
			builtinEdit = null;
			ret = builtinObj;
		}
		else {
			ret = super.compileMacro( macro );
			noSuperCompiler = false;
		}

		return( ret );
	}
}
