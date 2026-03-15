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
import java.util.Iterator;

import org.msscf.msscf.v2_13.cflib.CFLib.*;

/**
 *	The internal PathDirInfo entries are used to track members of the
 *	model and rule paths.
 */
class MSSBamCFPathDirInfo {

/**
 *	Jar files specify a package of "jar".  In this case the package
 *	names are implicit in the jar structure.
 */
public final static String		PACKAGE_JAR = "jar";

/**
 *	Has the target been validated?
 */
boolean							Validated;

/**
 *	Is the target valid?
 */
boolean							Valid;

/**
 *	Is the target expected to be a directory or a jar?
 */
boolean							IsJarFile;

/**
 *	The name of the directory to be searched.
 */
String							PathName;

/**
 *	The package prefix to be applied to names in the directory.
 *	<p>
 *	A package name of "jar" means the path name refers to a jar
 *	file instead of a system directory.  The jar files should
 *	have their full path/package included.
 */
String							PackageName;

/**
 *	You must specify the path name and package when creating the instance.
 */
public MSSBamCFPathDirInfo(
	String				pathName,
	String				packageName )
{
	final String S_ProcName = "PathDirInfo.PathDirInfo() ";

	if( ( pathName == null ) || ( pathName.length() == 0 ) ) {
    	throw new RuntimeException( S_ProcName + "Argument 1 pathName is null or empty");
    }

	Validated = false;
    Valid = false;

	PathName = new String( pathName );

    if( ( packageName == null ) || packageName.equals( PACKAGE_JAR ) ) {
    	PackageName = PACKAGE_JAR;
        IsJarFile = true;
    }
    else {
		PackageName = new String( packageName );
    	IsJarFile = false;
    }
}

/**
 *	Get the path name
 */
public String getPathName()
{
	return( PathName );
}

/**
 *	Get the package prefix.  If the path is a jar null is returned.
 */
public String getPackageName()
{
	return( PackageName );
}

/**
 *	Is the path to a jar file?
 */
public boolean isJarFile()
{
	return( IsJarFile );
}

/**
 *	Try to validate the path information, loading reference
 *	information as we go.
 */
public boolean validate(
	ICFLibMessageLog	log,
    boolean					reload )
{
	if( Validated && ( ! reload ) ) {
    	return( Valid );
    }

   	Valid = false;
	Validated = false;

    return( Valid );
}

}
