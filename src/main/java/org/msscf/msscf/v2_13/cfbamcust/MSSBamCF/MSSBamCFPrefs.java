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

import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.ArrayList;
import java.util.Iterator;

import org.msscf.msscf.v2_13.cflib.CFLib.*;

/**
 *	The WorkbenchUPrefs for the workbench defines the standard properties
 *	to be stored in the file $HOME/.msisarc for the user.
 *	<p>
 *	When more than one attribute such as the modeldir/modelpkg and ruledir/rulepkg pairs
 *	is defined in the preferences, the 1..n'th instances have a string version of their
 *	index appended.
 */
public class MSSBamCFPrefs
{

/**
 *	We use the user's login name for their default models.
 */
protected String						UserName;

/**
 *	The best place for user preferences is in their $HOME directory.
 *	<p>
 *	The location of the home directory is resolved through Java system properties.
 */
protected String						HomeDirName;
protected File							HomeDir;

/**
 *	The user's preferences are stored in the user's $HOME/.msisarc
 */
public final static String				FILENAME_MSSCFRC = ".msscfrc";
protected String						UPrefFileName;

/**
 *	User preferences are stored as a property file.
 */
protected Properties					UPref;
protected File							UPrefFile;

/**
 *	Where should code be generated to?
 */
public final static String				PROPNAME_ROOTGENDIR = "msscf.rootgendir";
protected String						RootGenDir;

/**
 *	The model directories and packages are stored as PathDirInfo instances.
 *	<p>
 *	As properties, the numeric index is appended to the property name to
 *	maintain sequencing of the entries.
 */
protected ArrayList<String>				ModelPath;
public final static String				PROPNAME_MODELDIR = "msscf.modeldir";

/**
 *	The XML rule cartridge directories are stored as PathDirInfo instances.
 *	<p>
 *	As properties, the numeric index is appended to the property name to
 *	maintain sequencing of the entries.
 */
protected ArrayList<String>				CartridgePath;
public final static String				PROPNAME_CARTRIDGEDIR = "msscf.cartridgedir";

/**
 *	Jar files specify a package of "jar".  In this case the package
 *	names are implicit in the jar structure.
 */
public final static String				PACKAGE_JAR = MSSBamCFPathDirInfo.PACKAGE_JAR;

/***************************************** Constructors *********************/

/**
 *	Construct an instance for the running user.
 */
public MSSBamCFPrefs()
{
	UserName = null;
	HomeDirName = null;
	HomeDir = null;
	UPrefFileName = null;
	UPref = null;
	UPrefFile = null;

	RootGenDir = null;
    ModelPath = new ArrayList<String>();
    CartridgePath = new ArrayList<String>();
}

/**
 *	Get the Properties used for the user preferences.
 *	<p>
 *	The properties are exposed in this fashion so that toolkit preferences
 *	(such as InterAgent) can also be stored in the user preferences.
 */
protected Properties getProperties()
{
	return( UPref );
}

/**
 *	Synchronized peer for getUserName().
 */
private synchronized void s_resolveUserName( ICFLibMessageLog log )
{
	if( UserName != null ) {
		return;
	}

	UserName = System.getProperty( "user.name" );
	if( UserName == null ) {
		if( log != null ) {
			log.message( "User name not set" );
		}
	}
}

/**
 *	The name of the user should always exist.
 */
public String getUserName( ICFLibMessageLog log )
{
	if( UserName == null ) {
		s_resolveUserName( log );
	}

	return( UserName );
}

/**
 *	Synchronized peer for getHomeDirName().
 */
private synchronized void s_resolveHomeDirName( ICFLibMessageLog log )
{
	if( HomeDirName != null ) {
		return;
	}

	HomeDirName = System.getProperty( "HOME" );
    if( HomeDirName == null ) {
		HomeDirName = System.getProperty( "user.home" );
		if( HomeDirName == null ) {
			if( log != null ) {
				log.message( "Home directory not set" );
			}
		}
    }
}

/**
 *	The name of the user's home directory always exists.
 */
public String getHomeDirName( ICFLibMessageLog log )
{
	if( HomeDirName == null ) {
		s_resolveHomeDirName( log );
	}

	return( HomeDirName );
}

/**
 *	Synchronized peer for getHomeDir().
 */
private synchronized void s_resolveHomeDir( ICFLibMessageLog log )
{
	if( HomeDir != null ) {
		return;
	}

	getHomeDirName( log );
	if( HomeDirName == null ) {
		return;
	}

	HomeDir = new File( HomeDirName );
	if( ! HomeDir.exists() ) {
		if( log != null ) {
			log.message( "Home directory " + HomeDir.getPath() + " does not exist" );
		}
		return;
	}

	// Use localized file names when we can
	HomeDirName = new String( HomeDir.getPath() );

	if( ! HomeDir.isDirectory() ) {
		if( log != null ) {
			log.message( "Home directory " + HomeDirName + " is not a directory" );
		}
		return;
	}

}

/**
 *	The user's home directory should always exist.
 */
public File getHomeDir( ICFLibMessageLog log )
{
	if( HomeDir == null ) {
		s_resolveHomeDir( log );
	}

	return( HomeDir );
}

/**
 *	Get the root generation directory.
 */
public String getRootGenDir()
{
    if( RootGenDir == null ) {
    	RootGenDir = UPref.getProperty( PROPNAME_ROOTGENDIR );
    }
    return( RootGenDir );
}

/**
 *	Set the root generation directory
 */
public void setRootGenDir( String value )
{
	final String S_ProcName = "MSSBamCFPrefs.setRootGenDir() ";
	if( ( value == null ) || ( value.length() == 0 ) ) {
    	throw new RuntimeException( S_ProcName + "Argument 1 value is null or empty");
    }

    RootGenDir = new String( value );
    UPref.put( PROPNAME_ROOTGENDIR, RootGenDir );
}

/**
 *	Add a model directory and package to the model path.
 */
public void addModelPath( String dirname )
{
	final String S_ProcName = "MSSBamCFPrefs.addModelPath() ";

	if( ( dirname == null ) || ( dirname.length() == 0 ) ) {
    	throw new RuntimeException( S_ProcName + "dirname is null or empty");
    }

    MSSBamCFPathDirInfo pathdir = new MSSBamCFPathDirInfo( dirname, null );
	ModelPath.add( pathdir.getPathName() );

    String propName;
    int seq = ModelPath.size() - 1;
   	if( seq > 0 ) {
		propName = new String( PROPNAME_MODELDIR + Integer.toString( seq ) );
        UPref.put( propName, pathdir.getPathName() );
    }
    else {
		propName = new String( PROPNAME_MODELDIR );
        UPref.put( propName, pathdir.getPathName() );
    }
}

/**
 *	Get an iteration of the cartridge path directories.
 *
 *	@return Iteration of String directory names.
 */
public Iterator<String> getModelPathIterator()
{
	int len = ModelPath.size();
	ArrayList<String> list = ( len > 0 ) ? new ArrayList<String>( len ) : new ArrayList<String>();
	for( int idx = 0; idx < len; idx ++ ) {
		String pathdir = ModelPath.get( idx );
		list.add( pathdir );
	}
	return( list.iterator() );
}

/**
 *	Add an XML rule cartridge directory the cartridge path.
 */
public void addCartridgePath( String dirname )
{
	final String S_ProcName = "MSSBamCFPrefs.addCartridgePath() ";
	if( ( dirname == null ) || ( dirname.length() == 0 ) ) {
    	throw new RuntimeException( S_ProcName + "Argument 1 dirname is null or empty");
    }

	String pathname = new String( dirname );
	CartridgePath.add( pathname );

    String propName;
    int seq = CartridgePath.size() - 1;
   	if( seq > 0 ) {
		propName = new String( PROPNAME_CARTRIDGEDIR + Integer.toString( seq ) );
        UPref.put( propName, pathname );
    }
    else {
		propName = new String( PROPNAME_CARTRIDGEDIR );
        UPref.put( propName, pathname );
    }
}

/**
 *	Get an iteration of the cartridge path directories.
 *
 *	@return Iteration of Strings.
 */
public Iterator<String> getCartridgePathIterator()
{
	Iterator<String> iter = CartridgePath.iterator();
	return( iter );
}

/**
 *	Default values are set for the user properties if no file
 *	is found.
 */
public synchronized void reset( ICFLibMessageLog log )
{

//	We need the user name and the home directory to set up a user's defaults

	String userName = getUserName( log );
	File homeDir = getHomeDir( log );

//	If we can't get the user name or home directory the defaults are null

	if( ( homeDir == null )
	 || ( userName == null ) || ( userName.length() == 0 ) )
	{
		RootGenDir = null;
        ModelPath.clear();
        CartridgePath.clear();
		return;
	}

//	Set the default development directory

	String msisaDir = new String( homeDir.getPath() + File.separator + "msscf" + File.separator );

//	Set the root generation directory

	setRootGenDir( msisaDir );

//	Set the application models and pacakges

	addModelPath( msisaDir + "/net-sourceforge-MssCF-2-11/java/model-2.13" );

//	Set the rule directories and packages

	addCartridgePath( msisaDir + "/net-sourceforge-MssCF-2-11/java/cartridge-2.13" );
}

/**
 *	Parse the properties into user preference values.
 */
protected void parseProperties(
	ICFLibMessageLog		log,
    boolean						reload )
{
	int							i;
    String						pathDir;
	String						prop;
	String						propName;
	MSSBamCFPathDirInfo			pathInfo;

    ModelPath.clear();
   	CartridgePath.clear();

//	Try to load the user's display preferences

//	DisplayOptions.reset();
//	DisplayOptions.mergeProperties( UPref, PROPBASE_OMFDISPLAY );

//	Get the root generation directory from the properties

	prop = UPref.getProperty( PROPNAME_ROOTGENDIR );
    if( prop != null ) {
    	RootGenDir = new String( prop );
    }

//	LOOP each model path property

	for( i = 0; i >= 0; i++ ) {

//		Try to get the model directory

    	if( i > 0 ) {
			propName = new String( PROPNAME_MODELDIR + Integer.toString( i ) );
        }
        else {
        	propName = PROPNAME_MODELDIR;
        }

        pathDir = UPref.getProperty( propName );

//		Allow for null package implying a jar file when adding the path entry

		if( ( pathDir != null ) && ( pathDir.length() > 0 ) )
        {
			ModelPath.add( new String( pathDir ) );
        }
        else {
        	break;
        }
    }

//	LOOP each cartridge path property

	for( i = 0; i >= 0; i++ ) {

//		Try to get the cartridge directory

    	if( i > 0 ) {
			propName = new String( PROPNAME_CARTRIDGEDIR + Integer.toString( i ) );
        }
        else {
        	propName = PROPNAME_CARTRIDGEDIR;
        }

        pathDir = UPref.getProperty( propName );

//		Allow for null package implying a jar file when adding the path entry

		if( ( pathDir != null ) && ( pathDir.length() > 0 ) ) {
			CartridgePath.add( new String( pathDir ) );
        }
        else {
        	break;
        }
    }
}

/**
 *	Save the user preferences file.
 */
public boolean savePreferences( ICFLibMessageLog log )
{
	final String S_ProcName = "MSSBamCFPrefs.savePreferences() ";
	boolean retStatus = false;

//	Push the user's display properties to the user preferences

//	DisplayOptions.putProperties( UPref, PROPBASE_OMFDISPLAY );

//	Create the file handle if necessary

	if( UPrefFile == null ) {
		UPrefFile = new File( UPrefFileName );
    }

//	Make sure existing files have valid permissions

	if( UPrefFile.exists() ) {
		if( ! UPrefFile.isFile() ) {
			UPrefFile = null;
			log.message( UPrefFile.getPath() + "is not a file." );
			return( false );
		}

		if( ! UPrefFile.canRead() ) {
			UPrefFile = null;
			log.message( "User preferences file " + UPrefFileName + " is not readable" );
			return( false );
		}

		if( ! UPrefFile.canWrite() ) {
			UPrefFile = null;
			log.message( "User preferences file " + UPrefFileName + " is not writable" );
			return( false );
		}
	}

//	Open the file for writing

    FileOutputStream fileWriter = null;
	try {
		fileWriter = new FileOutputStream( UPrefFileName );
		if( fileWriter == null ) {
			UPrefFile = null;
			log.message( "Could not write user preferences file " + UPrefFileName );
			return( false );
		}
    }
	catch ( IOException e ) {
		UPrefFile = null;
		throw new RuntimeException( S_ProcName + "Could not write user preferences file "
        	+ UPrefFileName
    		+ ": "
			+ e.getMessage() );
    }

//	Save the properties to the file

	try {
		UPref.store( fileWriter,
        	"MSSBamCF Preferences for " + getUserName( null ) );

		fileWriter.flush();
		fileWriter.close();

    	retStatus = true;

		log.message( "Saved user preferences file " + UPrefFileName );
	}
	catch ( IOException e ) {
		UPrefFile = null;
		log.message( "Error creating default user preferences file: "
              	+ e.getMessage() );
	}

	return( retStatus );
}

/**
 *	Load the user preferences from their home directory.
 *	<p>
 *	If there is no existing preferences file, defaults are
 *	set and the file is created.
 *
 *	@param	reload	If set the preferences are always reread or reset.
 *	@return	True if preferences were properly loaded, false if errors occured.
 */
public boolean loadPreferences( ICFLibMessageLog log, boolean reload )
//throws IOException
{
	final String S_ProcName = "MSSBamCFPrefs.loadPreferences() ";
	if( ( UPref != null ) && ( ! reload ) ) {
		return( true );
	}

//    DisplayOptions.reset();
    ModelPath.clear();

	UPref = new Properties();

	log.message( "Loading user preferences..." );
	log.indent();

	File homeDir = getHomeDir( log );
	if( homeDir == null ) {
		log.dedent();
		return( false );
	}

	if( ( UPrefFileName == null ) || reload ) {
		UPrefFileName = new String( homeDir.getPath() + File.separator + FILENAME_MSSCFRC );
		UPrefFile = new File( UPrefFileName );
    	reset( log );
	}

    if( ! UPrefFile.exists() ) {
		return( savePreferences( log ) );
	}

//	Verify that we have a valid user preferences file

	if( ! UPrefFile.isFile() ) {
		UPrefFile = null;
		log.message( UPrefFile.getPath() + "is not a file." );
		log.dedent();
		return( false );
	}

	if( ! UPrefFile.canRead() ) {
		UPrefFile = null;
		log.message( "Permission denied attempting to read file " + UPrefFileName );
		log.dedent();
		return( false );
	}

//	Open the user preferences file for reading

    FileInputStream fileReader = null;
	try {
		fileReader = new FileInputStream( UPrefFileName );
		if( fileReader == null ) {
			UPrefFile = null;
			log.message( "Could not load user preferences file " + UPrefFileName );
			log.dedent();
			return( false );
		}
	}
    catch ( IOException e ) {
		UPrefFile = null;
		throw new RuntimeException( S_ProcName + "Could not read user preferences file "
			+ UPrefFileName
            + ": "
            + e.getMessage() );
    }

//	Load the user preferences from the file

	try {
		log.message( "Loading user preferences from " + UPrefFileName );
		UPref.load( fileReader );
	}
	catch( IOException e ) {
		UPrefFile = null;
		log.message( "Error reading preferences file " + UPrefFileName + ": "
				+ e.getMessage() );
	}

//	Close the user preferences

	try {
		fileReader.close();
	}
	catch ( IOException e ) {
		UPrefFile = null;
	}

//	Parse the properties

	parseProperties( log, reload );

//	Now that we've read the user preferences file we assume it will remain valid.

	log.dedent();
    return( true );
}

}
