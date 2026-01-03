/*
 *	MSS Code Factory CFBam 2.13 CustMSSBamCF
 *
 *	Copyright (C) 2016-2025 Mark Stephen Sobkow (mailto:mark.sobkow@gmail.com)
 *	
 *	This program is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *	
 *	This program is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *	
 *	You should have received a copy of the GNU General Public License
 *	along with this program.  If not, see &lt;https://www.gnu.org/licenses/&gt;.
 *	
 *	If you wish to modify and use this code without publishing your changes,
 *	or integrate it with proprietary code, please contact Mark Stephen Sobkow
 *	for a commercial license at mark.sobkow@gmail.com
 */

package org.msscf.msscf.v2_13.cfbamcust.MSSBamCF;

import java.util.*;
import org.msscf.msscf.v2_13.cfbam.CFBam.ICFBamSchema.RelationTypeEnum;

import org.msscf.msscf.v2_13.cflib.CFLib.*;
import org.msscf.msscf.v2_13.cfbam.CFBamObj.*;

public class MSSBamCFAnyObj
{
	public MSSBamCFAnyObj() {
	}

    public static boolean inSuperiorCandidateRelation(ICFBamValueObj valueDef) {
        final String S_ProcName = "MSSCFBamCFAnyObj.inSuperiorCandidateRelation";
		if( valueDef == null ) {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"valueDef" );
		}
        if (!isValidTableColumn(valueDef)) {
            return false;
        }
        ICFLibAnyObj scopeDef = valueDef.getObjScope();
        if (scopeDef == null) {
            throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
            S_ProcName,
            0,
            "valueDef.scopeDef" );
        }
        else if(scopeDef instanceof ICFBamTableObj) {
            ICFBamTableObj tableDef = (ICFBamTableObj)scopeDef;
            List<ICFLibAnyObj> candidateRelations = MSSBamCFGenIterateSuperiorCandidateRelations.getSuperiorCandidateRelations(tableDef);
            if (candidateRelations != null) {
                for(ICFLibAnyObj curObj : candidateRelations) {
                    ICFBamRelationObj relationDef = (CFBamRelationObj)curObj;
                    for(ICFBamRelationColObj relationCol : relationDef.getOptionalComponentsColumns()) {
                        if(relationCol.getRequiredLookupFromCol().getRequiredLookupColumn() == valueDef) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        else {
            return false;
        }
    }
    
    public static boolean inSuperiorRelation(ICFBamValueObj valueDef) {
        final String S_ProcName = "MSSCFBamCFAnyObj.inSuperiorRelation";
		if( valueDef == null ) {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"valueDef" );
		}
        if (!isValidTableColumn(valueDef)) {
            return false;
        }
        ICFLibAnyObj scopeDef = valueDef.getObjScope();
        if (scopeDef == null) {
            throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
            S_ProcName,
            0,
            "valueDef.scopeDef" );
        }
        else if(scopeDef instanceof ICFBamTableObj) {
            ICFBamTableObj tableDef = (ICFBamTableObj)scopeDef;
            List<ICFLibAnyObj> relations = MSSBamCFGenIterateSuperiorRelations.getSuperiorRelations(tableDef);
            if (relations != null) {
                for(ICFLibAnyObj curObj : relations) {
                    ICFBamRelationObj relationDef = (CFBamRelationObj)curObj;
                    for(ICFBamRelationColObj relationCol : relationDef.getOptionalComponentsColumns()) {
                        if(relationCol.getRequiredLookupFromCol().getRequiredLookupColumn() == valueDef) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        else {
            return false;
        }
    }

	public static boolean inDataRelation(ICFBamValueObj valueDef) {
        final String S_ProcName = "MSSCFBamCFAnyObj.inDataRelation";
		if( valueDef == null ) {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"valueDef" );
		}
        if (!isValidTableColumn(valueDef)) {
            return false;
        }
        ICFLibAnyObj scopeDef = valueDef.getObjScope();
        if (scopeDef == null) {
            throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
            S_ProcName,
            0,
            "valueDef.scopeDef" );
        }
        else if(scopeDef instanceof ICFBamTableObj) {
            ICFBamTableObj tableDef = (ICFBamTableObj)scopeDef;
            List<ICFLibAnyObj> relations = MSSBamCFGenIterateDataRelations.getDataRelations(tableDef);
            if (relations != null) {
                for(ICFLibAnyObj curObj : relations) {
                    ICFBamRelationObj relationDef = (CFBamRelationObj)curObj;
					for(ICFBamRelationColObj relationCol : relationDef.getOptionalComponentsColumns()) {
						if(relationCol.getRequiredLookupFromCol().getRequiredLookupColumn() == valueDef) {
							return true;
						}
					}
                }
            }
            return false;
        }
        else {
            return false;
        }
	}

	public static boolean inPrimaryKeyRelation(ICFBamValueObj valueDef) {
        final String S_ProcName = "MSSCFBamCFAnyObj.inPrimaryKeyRelation";
		if( valueDef == null ) {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"valueDef" );
		}
        if (!isValidTableColumn(valueDef)) {
            return false;
        }
        ICFLibAnyObj scopeDef = valueDef.getObjScope();
        if (scopeDef == null) {
            throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
            S_ProcName,
            0,
            "valueDef.scopeDef" );
        }
        else if(scopeDef instanceof ICFBamTableObj) {
            ICFBamTableObj tableDef = (ICFBamTableObj)scopeDef;
            List<ICFLibAnyObj> relations = MSSBamCFGenIteratePrimaryKeyRelations.getPrimaryKeyRelations(tableDef);
            if (relations != null) {
                for(ICFLibAnyObj curObj : relations) {
                    ICFBamRelationObj relationDef = (CFBamRelationObj)curObj;
					for(ICFBamRelationColObj relationCol : relationDef.getOptionalComponentsColumns()) {
						if(relationCol.getRequiredLookupFromCol().getRequiredLookupColumn() == valueDef) {
							return true;
						}
					}
                }
            }
            return false;
        }
        else {
            return false;
        }
	}

	public static boolean inSuperiorPrimaryKeyRelation(ICFBamValueObj valueDef) {
        final String S_ProcName = "MSSCFBamCFAnyObj.inSuperiorPrimaryKeyRelation";
		if( valueDef == null ) {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"valueDef" );
		}
        if (!isValidTableColumn(valueDef)) {
            return false;
        }
        ICFLibAnyObj scopeDef = valueDef.getObjScope();
        if (scopeDef == null) {
            throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
            S_ProcName,
            0,
            "valueDef.scopeDef" );
        }
        else if(scopeDef instanceof ICFBamTableObj) {
            ICFBamTableObj tableDef = (ICFBamTableObj)scopeDef;
            List<ICFLibAnyObj> relations = MSSBamCFGenIterateSuperiorRelations.getSuperiorRelations(tableDef);
            if (relations != null) {
                for(ICFLibAnyObj curObj : relations) {
                    ICFBamRelationObj relationDef = (CFBamRelationObj)curObj;
					if (MSSBamCFGenBindIsRelationInPrimaryIndex.isRelationInPrimaryIndex(relationDef)) {
						for(ICFBamRelationColObj relationCol : relationDef.getOptionalComponentsColumns()) {
							if(relationCol.getRequiredLookupFromCol().getRequiredLookupColumn() == valueDef) {
								return true;
							}
						}
					}
                }
            }
            return false;
        }
        else {
            return false;
        }
	}

	public static boolean inSuperiorCandidatePrimaryKeyRelation(ICFBamValueObj valueDef) {
        final String S_ProcName = "MSSCFBamCFAnyObj.inSuperiorCandidatePrimaryKeyRelation";
		if( valueDef == null ) {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"valueDef" );
		}
        if (!isValidTableColumn(valueDef)) {
            return false;
        }
        ICFLibAnyObj scopeDef = valueDef.getObjScope();
        if (scopeDef == null) {
            throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
            S_ProcName,
            0,
            "valueDef.scopeDef" );
        }
        else if(scopeDef instanceof ICFBamTableObj) {
            ICFBamTableObj tableDef = (ICFBamTableObj)scopeDef;
            List<ICFLibAnyObj> candidateRelations = MSSBamCFGenIterateSuperiorCandidateRelations.getSuperiorCandidateRelations(tableDef);
            if (candidateRelations != null) {
                for(ICFLibAnyObj curObj : candidateRelations) {
                    ICFBamRelationObj relationDef = (CFBamRelationObj)curObj;
					if (MSSBamCFGenBindIsRelationInPrimaryIndex.isRelationInPrimaryIndex(relationDef)) {
						for(ICFBamRelationColObj relationCol : relationDef.getOptionalComponentsColumns()) {
							if(relationCol.getRequiredLookupFromCol().getRequiredLookupColumn() == valueDef) {
								return true;
							}
						}
					}
                }
            }
            return false;
        }
        else {
            return false;
        }
	}

	public static boolean inSuperiorNonCandidatePrimaryKeyRelation(ICFBamValueObj valueDef) {
        final String S_ProcName = "MSSCFBamCFAnyObj.inSuperiorNonCandidatePrimaryKeyRelation";
		if( valueDef == null ) {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"valueDef" );
		}
        if (!isValidTableColumn(valueDef)) {
            return false;
        }
        ICFLibAnyObj scopeDef = valueDef.getObjScope();
        if (scopeDef == null) {
            throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
            S_ProcName,
            0,
            "valueDef.scopeDef" );
        }
        else if(scopeDef instanceof ICFBamTableObj) {
            ICFBamTableObj tableDef = (ICFBamTableObj)scopeDef;
            List<ICFLibAnyObj> nonCandidateRelations = MSSBamCFGenIterateSuperiorNonCandidateRelations.getSuperiorNonCandidateRelations(tableDef);
            if (nonCandidateRelations != null) {
                for(ICFLibAnyObj curObj : nonCandidateRelations) {
                    ICFBamRelationObj relationDef = (CFBamRelationObj)curObj;
					if (MSSBamCFGenBindIsRelationInPrimaryIndex.isRelationInPrimaryIndex(relationDef)) {
						for(ICFBamRelationColObj relationCol : relationDef.getOptionalComponentsColumns()) {
							if(relationCol.getRequiredLookupFromCol().getRequiredLookupColumn() == valueDef) {
								return true;
							}
						}
					}
                }
            }
            return false;
        }
        else {
            return false;
        }
	}

	public static boolean inSuperiorDataRelation(ICFBamValueObj valueDef) {
        final String S_ProcName = "MSSCFBamCFAnyObj.inSuperiorDataRelation";
		if( valueDef == null ) {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"valueDef" );
		}
        if (!isValidTableColumn(valueDef)) {
            return false;
        }
        ICFLibAnyObj scopeDef = valueDef.getObjScope();
        if (scopeDef == null) {
            throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
            S_ProcName,
            0,
            "valueDef.scopeDef" );
        }
        else if(scopeDef instanceof ICFBamTableObj) {
            ICFBamTableObj tableDef = (ICFBamTableObj)scopeDef;
            List<ICFLibAnyObj> relations = MSSBamCFGenIterateSuperiorRelations.getSuperiorRelations(tableDef);
            if (relations != null) {
                for(ICFLibAnyObj curObj : relations) {
                    ICFBamRelationObj relationDef = (CFBamRelationObj)curObj;
					if (!MSSBamCFGenBindIsRelationInPrimaryIndex.isRelationInPrimaryIndex(relationDef)) {
						for(ICFBamRelationColObj relationCol : relationDef.getOptionalComponentsColumns()) {
							if(relationCol.getRequiredLookupFromCol().getRequiredLookupColumn() == valueDef) {
								return true;
							}
						}
					}
                }
            }
            return false;
        }
        else {
            return false;
        }
	}

	public static boolean inSuperiorCandidateDataRelation(ICFBamValueObj valueDef) {
        final String S_ProcName = "MSSCFBamCFAnyObj.inSuperiorCandidateDataRelation";
		if( valueDef == null ) {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"valueDef" );
		}
        if (!isValidTableColumn(valueDef)) {
            return false;
        }
        ICFLibAnyObj scopeDef = valueDef.getObjScope();
        if (scopeDef == null) {
            throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
            S_ProcName,
            0,
            "valueDef.scopeDef" );
        }
        else if(scopeDef instanceof ICFBamTableObj) {
            ICFBamTableObj tableDef = (ICFBamTableObj)scopeDef;
            List<ICFLibAnyObj> candidateRelations = MSSBamCFGenIterateSuperiorCandidateRelations.getSuperiorCandidateRelations(tableDef);
            if (candidateRelations != null) {
                for(ICFLibAnyObj curObj : candidateRelations) {
                    ICFBamRelationObj relationDef = (CFBamRelationObj)curObj;
					if (!MSSBamCFGenBindIsRelationInPrimaryIndex.isRelationInPrimaryIndex(relationDef)) {
						for(ICFBamRelationColObj relationCol : relationDef.getOptionalComponentsColumns()) {
							if(relationCol.getRequiredLookupFromCol().getRequiredLookupColumn() == valueDef) {
								return true;
							}
						}
					}
                }
            }
            return false;
        }
        else {
            return false;
        }
	}

	public static boolean inSuperiorNonCandidateDataRelation(ICFBamValueObj valueDef) {
        final String S_ProcName = "MSSCFBamCFAnyObj.inSuperiorNonCandidateDataRelation";
		if( valueDef == null ) {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"valueDef" );
		}
        if (!isValidTableColumn(valueDef)) {
            return false;
        }
        ICFLibAnyObj scopeDef = valueDef.getObjScope();
        if (scopeDef == null) {
            throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
            S_ProcName,
            0,
            "valueDef.scopeDef" );
        }
        else if(scopeDef instanceof ICFBamTableObj) {
            ICFBamTableObj tableDef = (ICFBamTableObj)scopeDef;
            List<ICFLibAnyObj> nonCandidateRelations = MSSBamCFGenIterateSuperiorNonCandidateRelations.getSuperiorNonCandidateRelations(tableDef);
            if (nonCandidateRelations != null) {
                for(ICFLibAnyObj curObj : nonCandidateRelations) {
                    ICFBamRelationObj relationDef = (CFBamRelationObj)curObj;
					if (!MSSBamCFGenBindIsRelationInPrimaryIndex.isRelationInPrimaryIndex(relationDef)) {
						for(ICFBamRelationColObj relationCol : relationDef.getOptionalComponentsColumns()) {
							if(relationCol.getRequiredLookupFromCol().getRequiredLookupColumn() == valueDef) {
								return true;
							}
						}
					}
                }
            }
            return false;
        }
        else {
            return false;
        }
	}

    public static ICFBamRelationObj derefFirstSuperiorRelation(ICFBamValueObj valueDef) {
        final String S_ProcName = "MSSCFBamCFAnyObj.derefFirstSuperiorRelation";
		if( valueDef == null ) {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"valueDef" );
		}
        if (!isValidTableColumn(valueDef)) {
            return null;
        }
        ICFLibAnyObj scopeDef = valueDef.getObjScope();
        if (scopeDef == null) {
            throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
            S_ProcName,
            0,
            "valueDef.scopeDef" );
        }
        else if(scopeDef instanceof ICFBamTableObj) {
            ICFBamTableObj tableDef = (ICFBamTableObj)scopeDef;
            List<ICFLibAnyObj> candidateRelations = MSSBamCFGenIterateSuperiorRelations.getSuperiorRelations(tableDef);
            if (candidateRelations != null) {
                for(ICFLibAnyObj curObj : candidateRelations) {
                    ICFBamRelationObj relationDef = (CFBamRelationObj)curObj;
                    for(ICFBamRelationColObj relationCol : relationDef.getOptionalComponentsColumns()) {
                        if(relationCol.getRequiredLookupFromCol().getRequiredLookupColumn() == valueDef) {
                            return relationDef;
                        }
                    }
                }
            }
            return null;
        }
        else {
            return null;
        }
    }

    public static ICFBamRelationObj derefFirstSuperiorCandidateRelation(ICFBamValueObj valueDef) {
        final String S_ProcName = "MSSCFBamCFAnyObj.derefFirstSuperiorCandidateRelation";
		if( valueDef == null ) {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"valueDef" );
		}
        if (!isValidTableColumn(valueDef)) {
            return null;
        }
        ICFLibAnyObj scopeDef = valueDef.getObjScope();
        if (scopeDef == null) {
            throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
            S_ProcName,
            0,
            "valueDef.scopeDef" );
        }
        else if(scopeDef instanceof ICFBamTableObj) {
            ICFBamTableObj tableDef = (ICFBamTableObj)scopeDef;
            List<ICFLibAnyObj> candidateRelations = MSSBamCFGenIterateSuperiorCandidateRelations.getSuperiorCandidateRelations(tableDef);
            if (candidateRelations != null) {
                for(ICFLibAnyObj curObj : candidateRelations) {
                    ICFBamRelationObj relationDef = (CFBamRelationObj)curObj;
                    for(ICFBamRelationColObj relationCol : relationDef.getOptionalComponentsColumns()) {
                        if(relationCol.getRequiredLookupFromCol().getRequiredLookupColumn() == valueDef) {
                            return relationDef;
                        }
                    }
                }
            }
            return null;
        }
        else {
            return null;
        }
    }

    public static ICFBamRelationColObj derefSuperiorCandidateRelationCol(ICFBamValueObj valueDef) {
        final String S_ProcName = "MSSCFBamCFAnyObj.derefSuperiorCandidateRelationCol";
		if( valueDef == null ) {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"valueDef" );
		}
        if (!isValidTableColumn(valueDef)) {
            return null;
        }
        ICFLibAnyObj scopeDef = valueDef.getObjScope();
        if (scopeDef == null) {
            throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
            S_ProcName,
            0,
            "valueDef.scopeDef" );
        }
        else if(scopeDef instanceof ICFBamTableObj) {
            ICFBamTableObj tableDef = (ICFBamTableObj)scopeDef;
            List<ICFLibAnyObj> candidateRelations = MSSBamCFGenIterateSuperiorCandidateRelations.getSuperiorCandidateRelations(tableDef);
			ICFBamRelationColObj retval = null;
            if (candidateRelations != null) {
                for(ICFLibAnyObj curObj : candidateRelations) {
                    ICFBamRelationObj relationDef = (CFBamRelationObj)curObj;
                    for(ICFBamRelationColObj relationCol : relationDef.getOptionalComponentsColumns()) {
                        if(relationCol.getRequiredLookupFromCol().getRequiredLookupColumn() == valueDef) {
							if (retval != null) {
								throw new CFLibCollisionDetectedException( MSSBamCFAnyObj.class, S_ProcName, relationCol.getPKey());
							}
							else {
								retval = relationCol;
							}
                        }
                    }
                }
            }
            return retval;
        }
        else {
            return null;
        }
    }
    
    public static ICFBamRelationColObj derefSuperiorRelationCol(ICFBamValueObj valueDef) {
        final String S_ProcName = "MSSCFBamCFAnyObj.derefSuperiorRelationCol";
		if( valueDef == null ) {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"valueDef" );
		}
        if (!isValidTableColumn(valueDef)) {
            return null;
        }
        ICFLibAnyObj scopeDef = valueDef.getObjScope();
        if (scopeDef == null) {
            throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
            S_ProcName,
            0,
            "valueDef.scopeDef" );
        }
        else if(scopeDef instanceof ICFBamTableObj) {
            ICFBamTableObj tableDef = (ICFBamTableObj)scopeDef;
            List<ICFLibAnyObj> relations = MSSBamCFGenIterateSuperiorRelations.getSuperiorRelations(tableDef);
			ICFBamRelationColObj retval = null;
            if (relations != null) {
                for(ICFLibAnyObj curObj : relations) {
                    ICFBamRelationObj relationDef = (CFBamRelationObj)curObj;
                    for(ICFBamRelationColObj relationCol : relationDef.getOptionalComponentsColumns()) {
                        if(relationCol.getRequiredLookupFromCol().getRequiredLookupColumn() == valueDef) {
							if (retval != null) {
								throw new CFLibCollisionDetectedException( MSSBamCFAnyObj.class, S_ProcName, relationCol.getPKey());
							}
							else {
								retval = relationCol;
							}
                        }
                    }
                }
            }
            return retval;
        }
        else {
            return null;
        }
    }

	public static ICFBamRelationColObj derefSuperiorPrimaryKeyRelationCol(ICFBamValueObj valueDef) {
        final String S_ProcName = "MSSCFBamCFAnyObj.derefSuperioPrimaryKeyRelationCol";
		if( valueDef == null ) {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"valueDef" );
		}
        if (!isValidTableColumn(valueDef)) {
            return null;
        }
        ICFLibAnyObj scopeDef = valueDef.getObjScope();
        if (scopeDef == null) {
            throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
            S_ProcName,
            0,
            "valueDef.scopeDef" );
        }
        else if(scopeDef instanceof ICFBamTableObj) {
            ICFBamTableObj tableDef = (ICFBamTableObj)scopeDef;
            List<ICFLibAnyObj> relations = MSSBamCFGenIterateSuperiorRelations.getSuperiorRelations(tableDef);
			ICFBamRelationColObj retval = null;
            if (relations != null) {
                for(ICFLibAnyObj curObj : relations) {
                    ICFBamRelationObj relationDef = (CFBamRelationObj)curObj;
					if (MSSBamCFGenBindIsRelationInPrimaryIndex.isRelationInPrimaryIndex(relationDef)) {
						for(ICFBamRelationColObj relationCol : relationDef.getOptionalComponentsColumns()) {
							if(relationCol.getRequiredLookupFromCol().getRequiredLookupColumn() == valueDef) {
								if (retval != null) {
									throw new CFLibCollisionDetectedException( MSSBamCFAnyObj.class, S_ProcName, relationCol.getPKey());
								}
								else {
									retval = relationCol;
								}
							}
						}
					}
                }
            }
            return retval;
        }
        else {
            return null;
        }
	}

	public static ICFBamRelationColObj derefSuperiorCandidatePrimaryKeyRelationCol(ICFBamValueObj valueDef) {
        final String S_ProcName = "MSSCFBamCFAnyObj.derefSuperiorCandidatePrimaryKeyRelationCol";
		if( valueDef == null ) {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"valueDef" );
		}
        if (!isValidTableColumn(valueDef)) {
            return null;
        }
        ICFLibAnyObj scopeDef = valueDef.getObjScope();
        if (scopeDef == null) {
            throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
            S_ProcName,
            0,
            "valueDef.scopeDef" );
        }
        else if(scopeDef instanceof ICFBamTableObj) {
            ICFBamTableObj tableDef = (ICFBamTableObj)scopeDef;
            List<ICFLibAnyObj> relations = MSSBamCFGenIterateSuperiorCandidateRelations.getSuperiorCandidateRelations(tableDef);
			ICFBamRelationColObj retval = null;
            if (relations != null) {
                for(ICFLibAnyObj curObj : relations) {
                    ICFBamRelationObj relationDef = (CFBamRelationObj)curObj;
					if (MSSBamCFGenBindIsRelationInPrimaryIndex.isRelationInPrimaryIndex(relationDef)) {
						for(ICFBamRelationColObj relationCol : relationDef.getOptionalComponentsColumns()) {
							if(relationCol.getRequiredLookupFromCol().getRequiredLookupColumn() == valueDef) {
								if (retval != null) {
									throw new CFLibCollisionDetectedException( MSSBamCFAnyObj.class, S_ProcName, relationCol.getPKey());
								}
								else {
									retval = relationCol;
								}
							}
						}
					}
                }
            }
            return retval;
        }
        else {
            return null;
        }
	}

	public static ICFBamRelationColObj derefSuperiorNonCandidatePrimaryKeyRelationCol(ICFBamValueObj valueDef) {
        final String S_ProcName = "MSSCFBamCFAnyObj.derefSuperiorNonCandidatePrimaryKeyRelationCol";
		if( valueDef == null ) {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"valueDef" );
		}
        if (!isValidTableColumn(valueDef)) {
            return null;
        }
        ICFLibAnyObj scopeDef = valueDef.getObjScope();
        if (scopeDef == null) {
            throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
            S_ProcName,
            0,
            "valueDef.scopeDef" );
        }
        else if(scopeDef instanceof ICFBamTableObj) {
            ICFBamTableObj tableDef = (ICFBamTableObj)scopeDef;
            List<ICFLibAnyObj> relations = MSSBamCFGenIterateSuperiorNonCandidateRelations.getSuperiorNonCandidateRelations(tableDef);
			ICFBamRelationColObj retval = null;
            if (relations != null) {
                for(ICFLibAnyObj curObj : relations) {
                    ICFBamRelationObj relationDef = (CFBamRelationObj)curObj;
					if (MSSBamCFGenBindIsRelationInPrimaryIndex.isRelationInPrimaryIndex(relationDef)) {
						for(ICFBamRelationColObj relationCol : relationDef.getOptionalComponentsColumns()) {
							if(relationCol.getRequiredLookupFromCol().getRequiredLookupColumn() == valueDef) {
								if (retval != null) {
									throw new CFLibCollisionDetectedException( MSSBamCFAnyObj.class, S_ProcName, relationCol.getPKey());
								}
								else {
									retval = relationCol;
								}
							}
						}
					}
                }
            }
            return retval;
        }
        else {
            return null;
        }
	}

	public static ICFBamRelationColObj derefSuperiorDataRelationCol(ICFBamValueObj valueDef) {
        final String S_ProcName = "MSSCFBamCFAnyObj.derefSuperiorDataRelationCol";
		if( valueDef == null ) {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"valueDef" );
		}
        if (!isValidTableColumn(valueDef)) {
            return null;
        }
        ICFLibAnyObj scopeDef = valueDef.getObjScope();
        if (scopeDef == null) {
            throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
            S_ProcName,
            0,
            "valueDef.scopeDef" );
        }
        else if(scopeDef instanceof ICFBamTableObj) {
            ICFBamTableObj tableDef = (ICFBamTableObj)scopeDef;
            List<ICFLibAnyObj> relations = MSSBamCFGenIterateSuperiorRelations.getSuperiorRelations(tableDef);
			ICFBamRelationColObj retval = null;
            if (relations != null) {
                for(ICFLibAnyObj curObj : relations) {
                    ICFBamRelationObj relationDef = (CFBamRelationObj)curObj;
					if ( ! MSSBamCFGenBindIsRelationInPrimaryIndex.isRelationInPrimaryIndex(relationDef)) {
						for(ICFBamRelationColObj relationCol : relationDef.getOptionalComponentsColumns()) {
							if(relationCol.getRequiredLookupFromCol().getRequiredLookupColumn() == valueDef) {
								if (retval != null) {
									throw new CFLibCollisionDetectedException( MSSBamCFAnyObj.class, S_ProcName, relationCol.getPKey());
								}
								else {
									retval = relationCol;
								}
							}
						}
					}
                }
            }
            return retval;
        }
        else {
            return null;
        }
	}

	public static ICFBamRelationColObj derefSuperiorCandidateDataRelationCol(ICFBamValueObj valueDef) {
        final String S_ProcName = "MSSCFBamCFAnyObj.derefSuperiorCandidateDataRelationCol";
		if( valueDef == null ) {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"valueDef" );
		}
        if (!isValidTableColumn(valueDef)) {
            return null;
        }
        ICFLibAnyObj scopeDef = valueDef.getObjScope();
        if (scopeDef == null) {
            throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
            S_ProcName,
            0,
            "valueDef.scopeDef" );
        }
        else if(scopeDef instanceof ICFBamTableObj) {
            ICFBamTableObj tableDef = (ICFBamTableObj)scopeDef;
            List<ICFLibAnyObj> relations = MSSBamCFGenIterateSuperiorCandidateRelations.getSuperiorCandidateRelations(tableDef);
			ICFBamRelationColObj retval = null;
            if (relations != null) {
                for(ICFLibAnyObj curObj : relations) {
                    ICFBamRelationObj relationDef = (CFBamRelationObj)curObj;
					if ( ! MSSBamCFGenBindIsRelationInPrimaryIndex.isRelationInPrimaryIndex(relationDef)) {
						for(ICFBamRelationColObj relationCol : relationDef.getOptionalComponentsColumns()) {
							if(relationCol.getRequiredLookupFromCol().getRequiredLookupColumn() == valueDef) {
								if (retval != null) {
									throw new CFLibCollisionDetectedException( MSSBamCFAnyObj.class, S_ProcName, relationCol.getPKey());
								}
								else {
									retval = relationCol;
								}
							}
						}
					}
                }
            }
            return retval;
        }
        else {
            return null;
        }
	}

	public static ICFBamRelationColObj derefSuperiorNonCandidateDataRelationCol(ICFBamValueObj valueDef) {
        final String S_ProcName = "MSSCFBamCFAnyObj.derefSuperiorNonCandidateDataRelationCol";
		if( valueDef == null ) {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"valueDef" );
		}
        if (!isValidTableColumn(valueDef)) {
            return null;
        }
        ICFLibAnyObj scopeDef = valueDef.getObjScope();
        if (scopeDef == null) {
            throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
            S_ProcName,
            0,
            "valueDef.scopeDef" );
        }
        else if(scopeDef instanceof ICFBamTableObj) {
            ICFBamTableObj tableDef = (ICFBamTableObj)scopeDef;
            List<ICFLibAnyObj> relations = MSSBamCFGenIterateSuperiorNonCandidateRelations.getSuperiorNonCandidateRelations(tableDef);
			ICFBamRelationColObj retval = null;
            if (relations != null) {
                for(ICFLibAnyObj curObj : relations) {
                    ICFBamRelationObj relationDef = (CFBamRelationObj)curObj;
					if ( ! MSSBamCFGenBindIsRelationInPrimaryIndex.isRelationInPrimaryIndex(relationDef)) {
						for(ICFBamRelationColObj relationCol : relationDef.getOptionalComponentsColumns()) {
							if(relationCol.getRequiredLookupFromCol().getRequiredLookupColumn() == valueDef) {
								if (retval != null) {
									throw new CFLibCollisionDetectedException( MSSBamCFAnyObj.class, S_ProcName, relationCol.getPKey());
								}
								else {
									retval = relationCol;
								}
							}
						}
					}
                }
            }
            return retval;
        }
        else {
            return null;
        }
	}

	public static List<ICFBamRelationObj> getInheritedRelations(ICFBamTableObj tableDef) {
        final String S_ProcName = "MSSCFBamCFAnyObj.getInheritedRelations";
		if( tableDef == null ) {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				1,
				"tableDef" );
		}
		ArrayList<ICFBamRelationObj> retlist = new ArrayList<>();
		ICFBamTableObj curTable = tableDef;
		while (curTable != null) {
			for (ICFBamRelationObj cur: curTable.getChildrenRelations()) {
				if (cur.getRequiredRelationType() != RelationTypeEnum.Superclass) {
					retlist.add(cur);
				}
			}
			ICFBamRelationObj screl = curTable.getSuperClassRelation();
			if (screl != null) {
				curTable = screl.getRequiredLookupToTable();
			}
			else {
				curTable = null;
			}
		}
		return( retlist );
	}

    public static ICFBamRelationObj derefReverseRelation(ICFBamRelationObj relationDef) {
        final String S_ProcName = "MSSCFBamCFAnyObj.derefReverseRelation";
		if( relationDef == null ) {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				1,
				"relationDef" );
		}
		ICFBamTableObj fromTable = relationDef.getRequiredContainerFromTable();
		ICFBamTableObj toTable = relationDef.getRequiredLookupToTable();
		ICFBamIndexObj fromIndex = relationDef.getRequiredLookupFromIndex();
		List<ICFBamRelationObj> inheritedToRelations = getInheritedRelations(toTable);
		List<ICFBamRelationObj> toRelationsReferencingFromIndex = new ArrayList<>();
		for (ICFBamRelationObj cur: inheritedToRelations) {
			if (cur.getRequiredLookupToIndex() == fromIndex) {
				toRelationsReferencingFromIndex.add(cur);
			}
		}
		if( toRelationsReferencingFromIndex.size() == 1) {
			return(toRelationsReferencingFromIndex.get(0));
		}
		else if( toRelationsReferencingFromIndex.isEmpty()) {
//			System.err.println("WARNING: No reverse relations matching from index for " + relationDef.getObjFullName() + " exist");
			return( null );
		}
		else {
			List<ICFBamRelationObj> toRelationsWithAllColsMatching = new ArrayList<>();
			for(ICFBamRelationObj cur: toRelationsReferencingFromIndex) {
				if (relationDef.getOptionalComponentsColumns().size() == cur.getOptionalComponentsColumns().size()) {
					boolean anymismatches = false;
					Iterator<ICFBamRelationColObj> mycoliter = relationDef.getOptionalComponentsColumns().iterator();
					Iterator<ICFBamRelationColObj> theircoliter = cur.getOptionalComponentsColumns().iterator();
					while (mycoliter.hasNext()) {
						ICFBamRelationColObj mine = mycoliter.next();
						ICFBamRelationColObj theirs = theircoliter.next();
						if (!mine.getRequiredLookupToCol().getRequiredName().equals(theirs.getRequiredLookupFromCol().getRequiredName())) {
							anymismatches = true;
						}
					}
					if (!anymismatches) {
						toRelationsWithAllColsMatching.add(cur);
					}
//					else {
//						System.err.println("Filtered out " + cur.getObjFullName() + " from candidates for " + relationDef.getObjFullName() + " due to mismatched names");
//					}
				}
//				else {
//					System.err.println("Filtered out " + cur.getObjFullName() + " from candidates for " + relationDef.getObjFullName() + " due to mismatched column counts");
//				}
			}
			if( toRelationsWithAllColsMatching.size() == 1) {
//				System.err.println("Matched reverse relationship " + toRelationsWithAllColsMatching.get(0).getObjFullName() + " for " + relationDef.getObjFullName());
				return(toRelationsWithAllColsMatching.get(0));
			}
			else if( toRelationsWithAllColsMatching.isEmpty()) {
//				System.err.println("WARNING: All " + toRelationsReferencingFromIndex.size() + " reverse relations matching from index for " + relationDef.getObjFullName() + "filtered out due to mismatched columns");
				return( null );
			}
			else {
//				System.err.println("WARNING: Multiple (" + toRelationsWithAllColsMatching.size() + " reverse relations matching from index for " + relationDef.getObjFullName() + " remain after filtering for matching columns");
				throw new CFLibUnresolvedRelationException(MSSBamCFAnyObj.class, S_ProcName, "Multiple candidate reverse relationships found for " + relationDef.getRequiredContainerFromTable().getObjName() + "." + relationDef.getRequiredName() + " in target table " + toTable.getRequiredName());
			}
		}
    }

    public static List<ICFBamRelationObj> derefReverseRelationships(ICFBamRelationObj relationDef) {
        final String S_ProcName = "MSSCFBamCFAnyObj.derefReverseRelationships";
		if( relationDef == null ) {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				1,
				"relationDef" );
		}
		ICFBamTableObj fromTable = relationDef.getRequiredContainerFromTable();
		ICFBamTableObj toTable = relationDef.getRequiredLookupToTable();
		List<ICFBamRelationObj> inheritedToRelations = getInheritedRelations(toTable);
		List<ICFBamRelationObj> toRelationsReferencingFromTable = new ArrayList<>();
		for (ICFBamRelationObj cur: inheritedToRelations) {
			if (cur.getRequiredLookupToTable() == fromTable) {
				toRelationsReferencingFromTable.add(cur);
			}
		}
		ICFBamIndexObj fromIndex = relationDef.getRequiredLookupFromIndex();
		List<ICFBamRelationObj> toRelationsReferencingFromIndex = new ArrayList<>();
		for (ICFBamRelationObj cur: toRelationsReferencingFromTable) {
			if( cur.getRequiredLookupToIndex() == fromIndex) {
				toRelationsReferencingFromIndex.add(cur);
			}
		}

		return(toRelationsReferencingFromIndex);
    }

    public static boolean inSubservientRelation(ICFBamValueObj valueDef) {
        final String S_ProcName = "MSSCFBamCFAnyObj.inSubservientRelation";
		if( valueDef == null ) {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"valueDef" );
		}
        if (!isValidTableColumn(valueDef)) {
            return false;
        }
        ICFLibAnyObj scopeDef = valueDef.getObjScope();
        if (scopeDef == null) {
            throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
            S_ProcName,
            0,
            "valueDef.scopeDef" );
        }
        else if(scopeDef instanceof ICFBamTableObj) {
            ICFBamTableObj tableDef = (ICFBamTableObj)scopeDef;
            List<ICFLibAnyObj> relations = MSSBamCFGenIterateSubservientRelations.getSubservientRelations(tableDef);
            if (relations != null) {
                for(ICFLibAnyObj curObj : relations) {
                    ICFBamRelationObj relationDef = (CFBamRelationObj)curObj;
                    for(ICFBamRelationColObj relationCol : relationDef.getOptionalComponentsColumns()) {
                        if(relationCol.getRequiredLookupFromCol().getRequiredLookupColumn() == valueDef) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        else {
            return false;
        }
    }

    public static boolean inSubservientDataRelation(ICFBamValueObj valueDef) {
        final String S_ProcName = "MSSCFBamCFAnyObj.inSubservientDataRelation";
		if( valueDef == null ) {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"valueDef" );
		}
        if (!isValidTableColumn(valueDef)) {
            return false;
        }
        ICFLibAnyObj scopeDef = valueDef.getObjScope();
        if (scopeDef == null) {
            throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
            S_ProcName,
            0,
            "valueDef.scopeDef" );
        }
        else if(scopeDef instanceof ICFBamTableObj) {
            ICFBamTableObj tableDef = (ICFBamTableObj)scopeDef;
            List<ICFLibAnyObj> relations = MSSBamCFGenIterateSubservientDataRelations.getSubservientDataRelations(tableDef);
            if (relations != null) {
                for(ICFLibAnyObj curObj : relations) {
                    ICFBamRelationObj relationDef = (CFBamRelationObj)curObj;
                    for(ICFBamRelationColObj relationCol : relationDef.getOptionalComponentsColumns()) {
                        if(relationCol.getRequiredLookupFromCol().getRequiredLookupColumn() == valueDef) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        else {
            return false;
        }
    }

    public static boolean inSubservientPrimaryKeyRelation(ICFBamValueObj valueDef) {
        final String S_ProcName = "MSSCFBamCFAnyObj.inSubservientPrimaryKeyRelation";
		if( valueDef == null ) {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"valueDef" );
		}
        if (!isValidTableColumn(valueDef)) {
            return false;
        }
        ICFLibAnyObj scopeDef = valueDef.getObjScope();
        if (scopeDef == null) {
            throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
            S_ProcName,
            0,
            "valueDef.scopeDef" );
        }
        else if(scopeDef instanceof ICFBamTableObj) {
            ICFBamTableObj tableDef = (ICFBamTableObj)scopeDef;
            List<ICFLibAnyObj> relations = MSSBamCFGenIterateSubservientPrimaryKeyRelations.getSubservientPrimaryKeyRelations(tableDef);
            if (relations != null) {
                for(ICFLibAnyObj curObj : relations) {
                    ICFBamRelationObj relationDef = (CFBamRelationObj)curObj;
                    for(ICFBamRelationColObj relationCol : relationDef.getOptionalComponentsColumns()) {
                        if(relationCol.getRequiredLookupFromCol().getRequiredLookupColumn() == valueDef) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        else {
            return false;
        }
    }

    public static boolean inSuperiorNonCandidateRelation(ICFBamValueObj valueDef) {
        final String S_ProcName = "MSSCFBamCFAnyObj.inSuperiorNonCandidateRelation";
		if( valueDef == null ) {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"valueDef" );
		}
        if (!isValidTableColumn(valueDef)) {
            return false;
        }
        ICFLibAnyObj scopeDef = valueDef.getObjScope();
        if (scopeDef == null) {
            throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
            S_ProcName,
            0,
            "valueDef.scopeDef" );
        }
        else if(scopeDef instanceof ICFBamTableObj) {
            ICFBamTableObj tableDef = (ICFBamTableObj)scopeDef;
            List<ICFLibAnyObj> nonCandidateRelations = MSSBamCFGenIterateSuperiorNonCandidateRelations.getSuperiorNonCandidateRelations(tableDef);
            if (nonCandidateRelations != null) {
                for(ICFLibAnyObj curObj : nonCandidateRelations) {
                    ICFBamRelationObj relationDef = (CFBamRelationObj)curObj;
                    for(ICFBamRelationColObj relationCol : relationDef.getOptionalComponentsColumns()) {
                        if(relationCol.getRequiredLookupFromCol().getRequiredLookupColumn() == valueDef) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        else {
            return false;
        }
    }

    public static boolean inSubservientCandidateRelation(ICFBamValueObj valueDef) {
        final String S_ProcName = "MSSCFBamCFAnyObj.inSubservientCandidateRelation";
		if( valueDef == null ) {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"valueDef" );
		}
        if (!isValidTableColumn(valueDef)) {
            return false;
        }
        ICFLibAnyObj scopeDef = valueDef.getObjScope();
        if (scopeDef == null) {
            throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
            S_ProcName,
            0,
            "valueDef.scopeDef" );
        }
        else if(scopeDef instanceof ICFBamTableObj) {
            ICFBamTableObj tableDef = (ICFBamTableObj)scopeDef;
            List<ICFLibAnyObj> candidateRelations = MSSBamCFGenIterateSubservientCandidateRelations.getSubservientCandidateRelations(tableDef);
            if (candidateRelations != null) {
                for(ICFLibAnyObj curObj : candidateRelations) {
                    ICFBamRelationObj relationDef = (CFBamRelationObj)curObj;
                    for(ICFBamRelationColObj relationCol : relationDef.getOptionalComponentsColumns()) {
                        if(relationCol.getRequiredLookupFromCol().getRequiredLookupColumn() == valueDef) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        else {
            return false;
        }
    }

    public static boolean inSubservientCandidateDataRelation(ICFBamValueObj valueDef) {
        final String S_ProcName = "MSSCFBamCFAnyObj.inSubservientCandidateDataRelation";
		if( valueDef == null ) {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"valueDef" );
		}
        if (!isValidTableColumn(valueDef)) {
            return false;
        }
        ICFLibAnyObj scopeDef = valueDef.getObjScope();
        if (scopeDef == null) {
            throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
            S_ProcName,
            0,
            "valueDef.scopeDef" );
        }
        else if(scopeDef instanceof ICFBamTableObj) {
            ICFBamTableObj tableDef = (ICFBamTableObj)scopeDef;
            List<ICFLibAnyObj> candidateRelations = MSSBamCFGenIterateSubservientCandidateDataRelations.getSubservientCandidateDataRelations(tableDef);
            if (candidateRelations != null) {
                for(ICFLibAnyObj curObj : candidateRelations) {
                    ICFBamRelationObj relationDef = (CFBamRelationObj)curObj;
                    for(ICFBamRelationColObj relationCol : relationDef.getOptionalComponentsColumns()) {
                        if(relationCol.getRequiredLookupFromCol().getRequiredLookupColumn() == valueDef) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        else {
            return false;
        }
    }

    public static boolean inSubservientCandidatePrimaryKeyRelation(ICFBamValueObj valueDef) {
        final String S_ProcName = "MSSCFBamCFAnyObj.inSubservientCandidatePrimaryKeyRelation";
		if( valueDef == null ) {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"valueDef" );
		}
        if (!isValidTableColumn(valueDef)) {
            return false;
        }
        ICFLibAnyObj scopeDef = valueDef.getObjScope();
        if (scopeDef == null) {
            throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
            S_ProcName,
            0,
            "valueDef.scopeDef" );
        }
        else if(scopeDef instanceof ICFBamTableObj) {
            ICFBamTableObj tableDef = (ICFBamTableObj)scopeDef;
            List<ICFLibAnyObj> candidateRelations = MSSBamCFGenIterateSubservientCandidatePrimaryKeyRelations.getSubservientCandidatePrimaryKeyRelations(tableDef);
            if (candidateRelations != null) {
                for(ICFLibAnyObj curObj : candidateRelations) {
                    ICFBamRelationObj relationDef = (CFBamRelationObj)curObj;
                    for(ICFBamRelationColObj relationCol : relationDef.getOptionalComponentsColumns()) {
                        if(relationCol.getRequiredLookupFromCol().getRequiredLookupColumn() == valueDef) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        else {
            return false;
        }
    }

    public static boolean inSubservientNonCandidateRelation(ICFBamValueObj valueDef) {
        final String S_ProcName = "MSSCFBamCFAnyObj.inSubservientNonCandidateRelation";
		if( valueDef == null ) {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"valueDef" );
		}
        if (!isValidTableColumn(valueDef)) {
            return false;
        }
        ICFLibAnyObj scopeDef = valueDef.getObjScope();
        if (scopeDef == null) {
            throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
            S_ProcName,
            0,
            "valueDef.scopeDef" );
        }
        else if(scopeDef instanceof ICFBamTableObj) {
            ICFBamTableObj tableDef = (ICFBamTableObj)scopeDef;
            List<ICFLibAnyObj> nonCandidateRelations = MSSBamCFGenIterateSubservientNonCandidateRelations.getSubservientNonCandidateRelations(tableDef);
            if (nonCandidateRelations != null) {
                for(ICFLibAnyObj curObj : nonCandidateRelations) {
                    ICFBamRelationObj relationDef = (CFBamRelationObj)curObj;
                    for(ICFBamRelationColObj relationCol : relationDef.getOptionalComponentsColumns()) {
                        if(relationCol.getRequiredLookupFromCol().getRequiredLookupColumn() == valueDef) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        else {
            return false;
        }
    }

    public static boolean inSubservientNonCandidateDataRelation(ICFBamValueObj valueDef) {
        final String S_ProcName = "MSSCFBamCFAnyObj.inSubservientNonCandidateDataRelation";
		if( valueDef == null ) {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"valueDef" );
		}
        if (!isValidTableColumn(valueDef)) {
            return false;
        }
        ICFLibAnyObj scopeDef = valueDef.getObjScope();
        if (scopeDef == null) {
            throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
            S_ProcName,
            0,
            "valueDef.scopeDef" );
        }
        else if(scopeDef instanceof ICFBamTableObj) {
            ICFBamTableObj tableDef = (ICFBamTableObj)scopeDef;
            List<ICFLibAnyObj> nonCandidateRelations = MSSBamCFGenIterateSubservientNonCandidateDataRelations.getSubservientNonCandidateDataRelations(tableDef);
            if (nonCandidateRelations != null) {
                for(ICFLibAnyObj curObj : nonCandidateRelations) {
                    ICFBamRelationObj relationDef = (CFBamRelationObj)curObj;
                    for(ICFBamRelationColObj relationCol : relationDef.getOptionalComponentsColumns()) {
                        if(relationCol.getRequiredLookupFromCol().getRequiredLookupColumn() == valueDef) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        else {
            return false;
        }
    }

    public static boolean inSubservientNonCandidatePrimaryKeyRelation(ICFBamValueObj valueDef) {
        final String S_ProcName = "MSSCFBamCFAnyObj.inSubservientNonCandidatePrimaryKeyRelation";
		if( valueDef == null ) {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"valueDef" );
		}
        if (!isValidTableColumn(valueDef)) {
            return false;
        }
        ICFLibAnyObj scopeDef = valueDef.getObjScope();
        if (scopeDef == null) {
            throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
            S_ProcName,
            0,
            "valueDef.scopeDef" );
        }
        else if(scopeDef instanceof ICFBamTableObj) {
            ICFBamTableObj tableDef = (ICFBamTableObj)scopeDef;
            List<ICFLibAnyObj> nonCandidateRelations = MSSBamCFGenIterateSubservientNonCandidatePrimaryKeyRelations.getSubservientNonCandidatePrimaryKeyRelations(tableDef);
            if (nonCandidateRelations != null) {
                for(ICFLibAnyObj curObj : nonCandidateRelations) {
                    ICFBamRelationObj relationDef = (CFBamRelationObj)curObj;
                    for(ICFBamRelationColObj relationCol : relationDef.getOptionalComponentsColumns()) {
                        if(relationCol.getRequiredLookupFromCol().getRequiredLookupColumn() == valueDef) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        else {
            return false;
        }
    }

    public static ICFBamRelationObj derefFirstSuperiorNonCandidateRelation(ICFBamValueObj valueDef) {
        final String S_ProcName = "MSSCFBamCFAnyObj.derefFirstSuperiorNonCandidateRelation";
		if( valueDef == null ) {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"valueDef" );
		}
        if (!isValidTableColumn(valueDef)) {
            return null;
        }
        ICFLibAnyObj scopeDef = valueDef.getObjScope();
        if (scopeDef == null) {
            throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
            S_ProcName,
            0,
            "valueDef.scopeDef" );
        }
        else if(scopeDef instanceof ICFBamTableObj) {
            ICFBamTableObj tableDef = (ICFBamTableObj)scopeDef;
            List<ICFLibAnyObj> nonCandidateRelations = MSSBamCFGenIterateSuperiorNonCandidateRelations.getSuperiorNonCandidateRelations(tableDef);
            if (nonCandidateRelations != null) {
                for(ICFLibAnyObj curObj : nonCandidateRelations) {
                    ICFBamRelationObj relationDef = (CFBamRelationObj)curObj;
                    for(ICFBamRelationColObj relationCol : relationDef.getOptionalComponentsColumns()) {
                        if(relationCol.getRequiredLookupFromCol().getRequiredLookupColumn() == valueDef) {
                            return relationDef;
                        }
                    }
                }
            }
            return null;
        }
        else {
            return null;
        }
    }

    public static ICFBamRelationColObj derefSubservientRelationCol(ICFBamValueObj valueDef) {
        final String S_ProcName = "MSSCFBamCFAnyObj.derefSubservientRelationCol";
		if( valueDef == null ) {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"valueDef" );
		}
        if (!isValidTableColumn(valueDef)) {
            return null;
        }
        ICFLibAnyObj scopeDef = valueDef.getObjScope();
        if (scopeDef == null) {
            throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
            S_ProcName,
            0,
            "valueDef.scopeDef" );
        }
        else if(scopeDef instanceof ICFBamTableObj) {
            ICFBamTableObj tableDef = (ICFBamTableObj)scopeDef;
            List<ICFLibAnyObj> relations = MSSBamCFGenIterateSubservientRelations.getSubservientRelations(tableDef);
			ICFBamRelationColObj retval = null;
            if (relations != null) {
                for(ICFLibAnyObj curObj : relations) {
                    ICFBamRelationObj relationDef = (CFBamRelationObj)curObj;
					for(ICFBamRelationColObj relationCol : relationDef.getOptionalComponentsColumns()) {
						if(relationCol.getRequiredLookupFromCol().getRequiredLookupColumn() == valueDef) {
							if (retval != null) {
								throw new CFLibCollisionDetectedException( MSSBamCFAnyObj.class, S_ProcName, relationCol.getPKey());
							}
							else {
								retval = relationCol;
							}
						}
					}
                }
            }
            return retval;
        }
        else {
            return null;
        }
    }

    public static ICFBamRelationColObj derefSubservientDataRelationCol(ICFBamValueObj valueDef) {
        final String S_ProcName = "MSSCFBamCFAnyObj.derefSubservientDataRelationCol";
		if( valueDef == null ) {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"valueDef" );
		}
        if (!isValidTableColumn(valueDef)) {
            return null;
        }
        ICFLibAnyObj scopeDef = valueDef.getObjScope();
        if (scopeDef == null) {
            throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
            S_ProcName,
            0,
            "valueDef.scopeDef" );
        }
        else if(scopeDef instanceof ICFBamTableObj) {
            ICFBamTableObj tableDef = (ICFBamTableObj)scopeDef;
            List<ICFLibAnyObj> relations = MSSBamCFGenIterateSubservientDataRelations.getSubservientDataRelations(tableDef);
			ICFBamRelationColObj retval = null;
            if (relations != null) {
                for(ICFLibAnyObj curObj : relations) {
                    ICFBamRelationObj relationDef = (CFBamRelationObj)curObj;
					if ( ! MSSBamCFGenBindIsRelationInPrimaryIndex.isRelationInPrimaryIndex(relationDef)) {
						for(ICFBamRelationColObj relationCol : relationDef.getOptionalComponentsColumns()) {
							if(relationCol.getRequiredLookupFromCol().getRequiredLookupColumn() == valueDef) {
								if (retval != null) {
									throw new CFLibCollisionDetectedException( MSSBamCFAnyObj.class, S_ProcName, relationCol.getPKey());
								}
								else {
									retval = relationCol;
								}
							}
						}
					}
                }
            }
            return retval;
        }
        else {
            return null;
        }
    }

    public static ICFBamRelationColObj derefSubservientPrimaryKeyRelationCol(ICFBamValueObj valueDef) {
        final String S_ProcName = "MSSCFBamCFAnyObj.derefSubservientPrimaryKeyRelationCol";
		if( valueDef == null ) {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"valueDef" );
		}
        if (!isValidTableColumn(valueDef)) {
            return null;
        }
        ICFLibAnyObj scopeDef = valueDef.getObjScope();
        if (scopeDef == null) {
            throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
            S_ProcName,
            0,
            "valueDef.scopeDef" );
        }
        else if(scopeDef instanceof ICFBamTableObj) {
            ICFBamTableObj tableDef = (ICFBamTableObj)scopeDef;
            List<ICFLibAnyObj> relations = MSSBamCFGenIterateSubservientPrimaryKeyRelations.getSubservientPrimaryKeyRelations(tableDef);
			ICFBamRelationColObj retval = null;
            if (relations != null) {
                for(ICFLibAnyObj curObj : relations) {
                    ICFBamRelationObj relationDef = (CFBamRelationObj)curObj;
					if ( MSSBamCFGenBindIsRelationInPrimaryIndex.isRelationInPrimaryIndex(relationDef)) {
						for(ICFBamRelationColObj relationCol : relationDef.getOptionalComponentsColumns()) {
							if(relationCol.getRequiredLookupFromCol().getRequiredLookupColumn() == valueDef) {
								if (retval != null) {
									throw new CFLibCollisionDetectedException( MSSBamCFAnyObj.class, S_ProcName, relationCol.getPKey());
								}
								else {
									retval = relationCol;
								}
							}
						}
					}
                }
            }
            return retval;
        }
        else {
            return null;
        }
    }

    public static ICFBamRelationColObj derefSuperiorNonCandidateRelationCol(ICFBamValueObj valueDef) {
        final String S_ProcName = "MSSCFBamCFAnyObj.derefSuperiorNonCandidateRelationCol";
		if( valueDef == null ) {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"valueDef" );
		}
        if (!isValidTableColumn(valueDef)) {
            return null;
        }
        ICFLibAnyObj scopeDef = valueDef.getObjScope();
        if (scopeDef == null) {
            throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
            S_ProcName,
            0,
            "valueDef.scopeDef" );
        }
        else if(scopeDef instanceof ICFBamTableObj) {
            ICFBamTableObj tableDef = (ICFBamTableObj)scopeDef;
            List<ICFLibAnyObj> relations = MSSBamCFGenIterateSuperiorNonCandidateRelations.getSuperiorNonCandidateRelations(tableDef);
			ICFBamRelationColObj retval = null;
            if (relations != null) {
                for(ICFLibAnyObj curObj : relations) {
                    ICFBamRelationObj relationDef = (CFBamRelationObj)curObj;
					for(ICFBamRelationColObj relationCol : relationDef.getOptionalComponentsColumns()) {
						if(relationCol.getRequiredLookupFromCol().getRequiredLookupColumn() == valueDef) {
							if (retval != null) {
								throw new CFLibCollisionDetectedException( MSSBamCFAnyObj.class, S_ProcName, relationCol.getPKey());
							}
							else {
								retval = relationCol;
							}
						}
					}
                }
            }
            return retval;
        }
        else {
            return null;
        }
    }

    public static ICFBamRelationColObj derefSubservientCandidateRelationCol(ICFBamValueObj valueDef) {
        final String S_ProcName = "MSSCFBamCFAnyObj.derefSubservientCandidateRelationCol";
		if( valueDef == null ) {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"valueDef" );
		}
        if (!isValidTableColumn(valueDef)) {
            return null;
        }
        ICFLibAnyObj scopeDef = valueDef.getObjScope();
        if (scopeDef == null) {
            throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
            S_ProcName,
            0,
            "valueDef.scopeDef" );
        }
        else if(scopeDef instanceof ICFBamTableObj) {
            ICFBamTableObj tableDef = (ICFBamTableObj)scopeDef;
            List<ICFLibAnyObj> relations = MSSBamCFGenIterateSubservientCandidateRelations.getSubservientCandidateRelations(tableDef);
			ICFBamRelationColObj retval = null;
            if (relations != null) {
                for(ICFLibAnyObj curObj : relations) {
                    ICFBamRelationObj relationDef = (CFBamRelationObj)curObj;
					for(ICFBamRelationColObj relationCol : relationDef.getOptionalComponentsColumns()) {
						if(relationCol.getRequiredLookupFromCol().getRequiredLookupColumn() == valueDef) {
							if (retval != null) {
								throw new CFLibCollisionDetectedException( MSSBamCFAnyObj.class, S_ProcName, relationCol.getPKey());
							}
							else {
								retval = relationCol;
							}
						}
					}
                }
            }
            return retval;
        }
        else {
            return null;
        }
    }

    public static ICFBamRelationColObj derefSubservientCandidateDataRelationCol(ICFBamValueObj valueDef) {
        final String S_ProcName = "MSSCFBamCFAnyObj.derefSubservientCandidateDataRelationCol";
		if( valueDef == null ) {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"valueDef" );
		}
        if (!isValidTableColumn(valueDef)) {
            return null;
        }
        ICFLibAnyObj scopeDef = valueDef.getObjScope();
        if (scopeDef == null) {
            throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
            S_ProcName,
            0,
            "valueDef.scopeDef" );
        }
        else if(scopeDef instanceof ICFBamTableObj) {
            ICFBamTableObj tableDef = (ICFBamTableObj)scopeDef;
            List<ICFLibAnyObj> relations = MSSBamCFGenIterateSubservientCandidateDataRelations.getSubservientCandidateDataRelations(tableDef);
			ICFBamRelationColObj retval = null;
            if (relations != null) {
                for(ICFLibAnyObj curObj : relations) {
                    ICFBamRelationObj relationDef = (CFBamRelationObj)curObj;
					if ( ! MSSBamCFGenBindIsRelationInPrimaryIndex.isRelationInPrimaryIndex(relationDef)) {
						for(ICFBamRelationColObj relationCol : relationDef.getOptionalComponentsColumns()) {
							if(relationCol.getRequiredLookupFromCol().getRequiredLookupColumn() == valueDef) {
								if (retval != null) {
									throw new CFLibCollisionDetectedException( MSSBamCFAnyObj.class, S_ProcName, relationCol.getPKey());
								}
								else {
									retval = relationCol;
								}
							}
						}
					}
                }
            }
            return retval;
        }
        else {
            return null;
        }
    }

    public static ICFBamRelationColObj derefSubservientCandidatePrimaryKeyRelationCol(ICFBamValueObj valueDef) {
        final String S_ProcName = "MSSCFBamCFAnyObj.derefSubservientCandidatePrimaryKeyRelationCol";
		if( valueDef == null ) {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"valueDef" );
		}
        if (!isValidTableColumn(valueDef)) {
            return null;
        }
        ICFLibAnyObj scopeDef = valueDef.getObjScope();
        if (scopeDef == null) {
            throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
            S_ProcName,
            0,
            "valueDef.scopeDef" );
        }
        else if(scopeDef instanceof ICFBamTableObj) {
            ICFBamTableObj tableDef = (ICFBamTableObj)scopeDef;
            List<ICFLibAnyObj> relations = MSSBamCFGenIterateSubservientCandidatePrimaryKeyRelations.getSubservientCandidatePrimaryKeyRelations(tableDef);
			ICFBamRelationColObj retval = null;
            if (relations != null) {
                for(ICFLibAnyObj curObj : relations) {
                    ICFBamRelationObj relationDef = (CFBamRelationObj)curObj;
					if ( MSSBamCFGenBindIsRelationInPrimaryIndex.isRelationInPrimaryIndex(relationDef)) {
						for(ICFBamRelationColObj relationCol : relationDef.getOptionalComponentsColumns()) {
							if(relationCol.getRequiredLookupFromCol().getRequiredLookupColumn() == valueDef) {
								if (retval != null) {
									throw new CFLibCollisionDetectedException( MSSBamCFAnyObj.class, S_ProcName, relationCol.getPKey());
								}
								else {
									retval = relationCol;
								}
							}
						}
					}
                }
            }
            return retval;
        }
        else {
            return null;
        }
    }

    public static ICFBamRelationColObj derefSubservientNonCandidateRelationCol(ICFBamValueObj valueDef) {
        final String S_ProcName = "MSSCFBamCFAnyObj.derefSubservientNonCandidateRelationCol";
		if( valueDef == null ) {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"valueDef" );
		}
        if (!isValidTableColumn(valueDef)) {
            return null;
        }
        ICFLibAnyObj scopeDef = valueDef.getObjScope();
        if (scopeDef == null) {
            throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
            S_ProcName,
            0,
            "valueDef.scopeDef" );
        }
        else if(scopeDef instanceof ICFBamTableObj) {
            ICFBamTableObj tableDef = (ICFBamTableObj)scopeDef;
            List<ICFLibAnyObj> relations = MSSBamCFGenIterateSubservientNonCandidateRelations.getSubservientNonCandidateRelations(tableDef);
			ICFBamRelationColObj retval = null;
            if (relations != null) {
                for(ICFLibAnyObj curObj : relations) {
                    ICFBamRelationObj relationDef = (CFBamRelationObj)curObj;
					for(ICFBamRelationColObj relationCol : relationDef.getOptionalComponentsColumns()) {
						if(relationCol.getRequiredLookupFromCol().getRequiredLookupColumn() == valueDef) {
							if (retval != null) {
								throw new CFLibCollisionDetectedException( MSSBamCFAnyObj.class, S_ProcName, relationCol.getPKey());
							}
							else {
								retval = relationCol;
							}
						}
					}
                }
            }
            return retval;
        }
        else {
            return null;
        }
    }

    public static ICFBamRelationColObj derefSubservientNonCandidateDataRelationCol(ICFBamValueObj valueDef) {
        final String S_ProcName = "MSSCFBamCFAnyObj.derefSubservientNonCandidateDataRelationCol";
		if( valueDef == null ) {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"valueDef" );
		}
        if (!isValidTableColumn(valueDef)) {
            return null;
        }
        ICFLibAnyObj scopeDef = valueDef.getObjScope();
        if (scopeDef == null) {
            throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
            S_ProcName,
            0,
            "valueDef.scopeDef" );
        }
        else if(scopeDef instanceof ICFBamTableObj) {
            ICFBamTableObj tableDef = (ICFBamTableObj)scopeDef;
            List<ICFLibAnyObj> relations = MSSBamCFGenIterateSubservientNonCandidateDataRelations.getSubservientNonCandidateDataRelations(tableDef);
			ICFBamRelationColObj retval = null;
            if (relations != null) {
                for(ICFLibAnyObj curObj : relations) {
                    ICFBamRelationObj relationDef = (CFBamRelationObj)curObj;
					if ( ! MSSBamCFGenBindIsRelationInPrimaryIndex.isRelationInPrimaryIndex(relationDef)) {
						for(ICFBamRelationColObj relationCol : relationDef.getOptionalComponentsColumns()) {
							if(relationCol.getRequiredLookupFromCol().getRequiredLookupColumn() == valueDef) {
								if (retval != null) {
									throw new CFLibCollisionDetectedException( MSSBamCFAnyObj.class, S_ProcName, relationCol.getPKey());
								}
								else {
									retval = relationCol;
								}
							}
						}
					}
                }
            }
            return retval;
        }
        else {
            return null;
        }
    }

    public static ICFBamRelationColObj derefSubservientNonCandidatePrimaryKeyRelationCol(ICFBamValueObj valueDef) {
        final String S_ProcName = "MSSCFBamCFAnyObj.derefSubservientNonCandidatePrimaryKeyRelationCol";
		if( valueDef == null ) {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"valueDef" );
		}
        if (!isValidTableColumn(valueDef)) {
            return null;
        }
        ICFLibAnyObj scopeDef = valueDef.getObjScope();
        if (scopeDef == null) {
            throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
            S_ProcName,
            0,
            "valueDef.scopeDef" );
        }
        else if(scopeDef instanceof ICFBamTableObj) {
            ICFBamTableObj tableDef = (ICFBamTableObj)scopeDef;
            List<ICFLibAnyObj> relations = MSSBamCFGenIterateSubservientNonCandidatePrimaryKeyRelations.getSubservientNonCandidatePrimaryKeyRelations(tableDef);
			ICFBamRelationColObj retval = null;
            if (relations != null) {
                for(ICFLibAnyObj curObj : relations) {
                    ICFBamRelationObj relationDef = (CFBamRelationObj)curObj;
					if ( MSSBamCFGenBindIsRelationInPrimaryIndex.isRelationInPrimaryIndex(relationDef)) {
						for(ICFBamRelationColObj relationCol : relationDef.getOptionalComponentsColumns()) {
							if(relationCol.getRequiredLookupFromCol().getRequiredLookupColumn() == valueDef) {
								if (retval != null) {
									throw new CFLibCollisionDetectedException( MSSBamCFAnyObj.class, S_ProcName, relationCol.getPKey());
								}
								else {
									retval = relationCol;
								}
							}
						}
					}
                }
            }
            return retval;
        }
        else {
            return null;
        }
    }

    public static boolean inSuperClassRelation(ICFBamValueObj valueDef) {
        final String S_ProcName = "MSSCFBamCFAnyObj.inSuperClassRelationn";
		if( valueDef == null ) {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"valueDef" );
		}
        if (!isValidTableColumn(valueDef)) {
            return false;
        }
        ICFLibAnyObj scopeDef = valueDef.getObjScope();
        if (scopeDef == null) {
            throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
            S_ProcName,
            0,
            "valueDef.scopeDef" );
        }
        else if(scopeDef instanceof ICFBamTableObj) {
            ICFBamTableObj tableDef = (ICFBamTableObj)scopeDef;
            ICFBamRelationObj superClassRelation = tableDef.getSuperClassRelation();
            if (superClassRelation == null) {
                return false;
            }
            for(ICFBamRelationColObj relationCol : superClassRelation.getOptionalComponentsColumns()) {
                if(relationCol.getRequiredLookupFromCol().getRequiredLookupColumn() == valueDef) {
                    return true;
                }
            }
            return false;
        }
        else {
            return false;
        }
    }

    public static boolean isValidTableColumn(ICFBamValueObj valueDef) {
        if (valueDef == null) {
            return false;
        }
        else if( valueDef instanceof ICFBamTableColObj
            || valueDef instanceof ICFBamBlobColObj
            || valueDef instanceof ICFBamBoolColObj
            || valueDef instanceof ICFBamDbKeyHash128ColObj
            || valueDef instanceof ICFBamDbKeyHash160ColObj
            || valueDef instanceof ICFBamDbKeyHash224ColObj
            || valueDef instanceof ICFBamDbKeyHash256ColObj
            || valueDef instanceof ICFBamDbKeyHash384ColObj
            || valueDef instanceof ICFBamDbKeyHash512ColObj
            || valueDef instanceof ICFBamDoubleColObj
            || valueDef instanceof ICFBamFloatColObj
            || valueDef instanceof ICFBamInt16ColObj
            || valueDef instanceof ICFBamInt32ColObj
            || valueDef instanceof ICFBamInt64ColObj
            || valueDef instanceof ICFBamUInt16ColObj
            || valueDef instanceof ICFBamUInt32ColObj
            || valueDef instanceof ICFBamUInt64ColObj
            || valueDef instanceof ICFBamDateColObj
            || valueDef instanceof ICFBamTimeColObj
            || valueDef instanceof ICFBamTimestampColObj
            || valueDef instanceof ICFBamTZDateColObj
            || valueDef instanceof ICFBamTZTimeColObj
            || valueDef instanceof ICFBamTZTimestampColObj
            || valueDef instanceof ICFBamUuidColObj
            || valueDef instanceof ICFBamUuid6ColObj
            || valueDef instanceof ICFBamStringColObj
            || valueDef instanceof ICFBamNmTokenColObj
            || valueDef instanceof ICFBamNmTokensColObj
            || valueDef instanceof ICFBamTokenColObj
            || valueDef instanceof ICFBamTextColObj
            || valueDef instanceof ICFBamNumberColObj )
        {
            return true;
        }
        else {
            return false;
        }
    }

    public static ICFBamSchemaDefObj getDefSchema(ICFLibAnyObj anyDef)
    {
		final String S_ProcName = "MSSBamCFAnyObj.getDefSchema";
    	ICFLibAnyObj curDef = anyDef;
		ICFBamSchemaDefObj defSchema;
		if( curDef == null ) {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"curDef" );
		}
		else if( curDef instanceof ICFBamValueObj ) {
			defSchema = ((ICFBamValueObj)curDef).getOptionalLookupDefSchema();
		}
		else if( curDef instanceof ICFBamSchemaDefObj ) {
			defSchema = (ICFBamSchemaDefObj)curDef;
		}
		else if( curDef instanceof ICFBamChainObj ) {
			defSchema = ((ICFBamChainObj)curDef).getOptionalLookupDefSchema();
		}
		else if( curDef instanceof ICFBamDelDepObj ) {
			defSchema = ((ICFBamDelDepObj)curDef).getOptionalLookupDefSchema();
		}
		else if( curDef instanceof ICFBamEnumTagObj ) {
			defSchema = ((ICFBamEnumTagObj)curDef).getOptionalLookupDefSchema();
		}
		else if( curDef instanceof ICFBamIndexColObj ) {
			defSchema = ((ICFBamIndexColObj)curDef).getOptionalLookupDefSchema();
		}
		else if( curDef instanceof ICFBamIndexObj ) {
			defSchema = ((ICFBamIndexObj)curDef).getOptionalLookupDefSchema();
		}
		else if( curDef instanceof ICFBamParamObj ) {
			defSchema = ((ICFBamParamObj)curDef).getOptionalLookupDefSchema();
		}
		else if( curDef instanceof ICFBamPopDepObj ) {
			defSchema = ((ICFBamPopDepObj)curDef).getOptionalLookupDefSchema();
		}
		else if( curDef instanceof ICFBamRelationColObj ) {
			defSchema = ((ICFBamRelationColObj)curDef).getOptionalLookupDefSchema();
		}
		else if( curDef instanceof ICFBamRelationObj ) {
			defSchema = ((ICFBamRelationObj)curDef).getOptionalLookupDefSchema();
		}
		else if( curDef instanceof ICFBamServerMethodObj ) {
			defSchema = ((ICFBamServerMethodObj)curDef).getOptionalLookupDefSchema();
		}
		else if( curDef instanceof ICFBamTableObj ) {
			defSchema = ((ICFBamTableObj)curDef).getOptionalLookupDefSchema();
		}
		else {
			defSchema = null;
		}

    	ICFBamSchemaDefObj topSchema = null;
    	ICFBamSchemaDefObj firstSchemaEncountered = null;
    	ICFBamSchemaDefObj lastInterruptedSchema = null;
		if( defSchema != null ) {
			firstSchemaEncountered = defSchema;
			curDef = defSchema;
		}
		else {
    		for( curDef = anyDef; curDef != null; curDef = curDef.getObjScope() ) {
    			if( curDef instanceof ICFBamSchemaDefObj ) {
    				if( lastInterruptedSchema == null ) {
    					firstSchemaEncountered = (ICFBamSchemaDefObj)curDef;
    				}
    				topSchema = (ICFBamSchemaDefObj)curDef;
    				lastInterruptedSchema = topSchema;
    			}
    			else {
    				lastInterruptedSchema = null;
    			}
    		}

    		if( topSchema != null ) {
    			defSchema = firstSchemaEncountered;
				curDef = defSchema;
    		}
		}
		return( defSchema );
	}

    public static String getDefDomainName(ICFLibAnyObj anyDef)
    {
		final String S_ProcName = "MSSBamCFAnyObj.getDefDomainName";
    	ICFLibAnyObj curDef = anyDef;
    	ICFBamSchemaDefObj defSchema;
		if( curDef != null ) {
    		defSchema = getDefSchema( curDef );
		}
		else {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"curDef" );
		}

   		if( defSchema != null ) {
			curDef = defSchema;
   		}

        for ( ; curDef != null; curDef = (curDef != null ? curDef.getObjScope() : null )) {
			if (curDef instanceof ICFBamTopDomainObj) {
				break;
			}
			else if (curDef instanceof ICFBamSubDomainObj) {
				break;
			}
			else if (curDef instanceof ICFBamTenantObj) {
				curDef = null;
                break;
			}
		}

        String s = null;
        if (curDef != null) {
            s = curDef.getObjName();
            curDef = curDef.getObjScope();
            while (curDef != null) {
                if (curDef instanceof ICFBamTldObj) {
                    s = curDef.getObjName() + "." + s;
                    curDef = null;
                }
                else if (curDef instanceof ICFBamTopDomainObj) {
                    s = curDef.getObjName() + "." + s;
                    curDef = curDef.getObjScope();
                }
                else if (curDef instanceof ICFBamSubDomainObj) {
                    s = curDef.getObjName() + "." + s;
                    curDef = curDef.getObjScope();
                }
                else if (curDef instanceof ICFBamTenantObj) {
                    curDef = null;
                }
            }
        }
		return( s );
	}

    public static ICFLibAnyObj getDefProject(ICFLibAnyObj anyDef)
    {
		final String S_ProcName = "MSSBamCFAnyObj.getDefProject";
    	ICFLibAnyObj curDef = anyDef;
    	ICFBamSchemaDefObj defSchema;
		if( curDef != null ) {
    		defSchema = getDefSchema( curDef );
		}
		else {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"curDef" );
		}

   		if( defSchema != null ) {
			curDef = defSchema;
   		}

        for ( ; curDef != null; curDef = curDef.getObjScope() ) {
			if (curDef instanceof ICFBamTopProjectObj) {
				return (curDef);
			}
			else if (curDef instanceof ICFBamSubProjectObj) {
				return (curDef);
			}
			else if (curDef instanceof ICFBamTenantObj) {
				return (null);
			}
		}

		return( null );
	}

    public static ICFLibAnyObj getDefTopProject(ICFLibAnyObj anyDef)
    {
		final String S_ProcName = "MSSBamCFAnyObj.getDefTopProject";
    	ICFLibAnyObj curDef = anyDef;
    	ICFBamSchemaDefObj defSchema;
		if( curDef != null ) {
    		defSchema = getDefSchema( curDef );
		}
		else {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"curDef" );
		}

   		if( defSchema != null ) {
			curDef = defSchema;
   		}

        for ( ; curDef != null; curDef = curDef.getObjScope() ) {
			if (curDef instanceof ICFBamTopProjectObj) {
				return (curDef);
			}
			else if (curDef instanceof ICFBamTenantObj) {
				return (null);
			}
		}

		return( null );
	}

    public static ICFLibAnyObj getDefSubProject(ICFLibAnyObj anyDef)
    {
		final String S_ProcName = "MSSBamCFAnyObj.getDefSubProject";
    	ICFLibAnyObj curDef = anyDef;
    	ICFBamSchemaDefObj defSchema;
		if( curDef != null ) {
    		defSchema = getDefSchema( curDef );
		}
		else {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"curDef" );
		}

   		if( defSchema != null ) {
			curDef = defSchema;
   		}

        for ( ; curDef != null; curDef = curDef.getObjScope() ) {
			if (curDef instanceof ICFBamSubProjectObj) {
				return (curDef);
			}
			else if (curDef instanceof ICFBamTenantObj) {
				return (null);
			}
		}

		return( null );
	}

    public static String getDefProjectName(ICFLibAnyObj anyDef)
    {
    	ICFLibAnyObj curDef = getDefProject(anyDef);
		if( curDef != null ) {
            String s = null;
            s = curDef.getObjName();
            curDef = curDef.getObjScope();
            while (curDef != null) {
                if (curDef instanceof ICFBamTldObj) {
                    s = curDef.getObjName() + "." + s;
                    curDef = null;
                }
                else if (curDef instanceof ICFBamTopDomainObj) {
                    s = curDef.getObjName() + "." + s;
                    curDef = curDef.getObjScope();
                }
                else if (curDef instanceof ICFBamSubDomainObj) {
                    s = curDef.getObjName() + "." + s;
                    curDef = curDef.getObjScope();
                }
                else if (curDef instanceof ICFBamTopProjectObj) {
                    s = curDef.getObjName() + "." + s;
                    curDef = curDef.getObjScope();
                }
                else if (curDef instanceof ICFBamSubProjectObj) {
                    s = curDef.getObjName() + "." + s;
                    curDef = curDef.getObjScope();
                }
                else if (curDef instanceof ICFBamTenantObj) {
                    curDef = null;
                }
                else {
                    curDef = curDef.getObjScope();
                }
            }
            return( s );
        }
        else {
            return( null );
        }
	}

    public static String getDefTopProjectName(ICFLibAnyObj anyDef)
    {
    	ICFLibAnyObj curDef = getDefTopProject(anyDef);
		if( curDef != null ) {
            String s = null;
            s = curDef.getObjName();
            curDef = curDef.getObjScope();
            while (curDef != null) {
                if (curDef instanceof ICFBamTldObj) {
                    s = curDef.getObjName() + "." + s;
                    curDef = null;
                }
                else if (curDef instanceof ICFBamTopDomainObj) {
                    s = curDef.getObjName() + "." + s;
                    curDef = curDef.getObjScope();
                }
                else if (curDef instanceof ICFBamSubDomainObj) {
                    s = curDef.getObjName() + "." + s;
                    curDef = curDef.getObjScope();
                }
                else if (curDef instanceof ICFBamTopProjectObj) {
                    s = curDef.getObjName() + "." + s;
                    curDef = curDef.getObjScope();
                }
                else if (curDef instanceof ICFBamTenantObj) {
                    curDef = null;
                }
                else {
                    curDef = curDef.getObjScope();
                }
            }
            return( s );
        }
        else {
            return( null );
        }
	}

    public static String getDefSubProjectName(ICFLibAnyObj anyDef)
    {
    	ICFLibAnyObj curDef = getDefSubProject(anyDef);
		if( curDef != null ) {
            String s = null;
            s = curDef.getObjName();
            return( s );
        }
        else {
            return( null );
        }
	}

    public static ICFLibAnyObj getProject(ICFLibAnyObj anyDef)
    {
    	ICFBamSchemaDefObj topSchema = null;
    	ICFBamSchemaDefObj firstSchemaEncountered = null;
    	ICFBamSchemaDefObj lastInterruptedSchema = null;
    	ICFLibAnyObj curDef;
    	for( curDef = anyDef; curDef != null; curDef = curDef.getObjScope() ) {
    		if( curDef instanceof ICFBamSchemaDefObj ) {
    			if( lastInterruptedSchema == null ) {
    				firstSchemaEncountered = (ICFBamSchemaDefObj)curDef;
    			}
    			topSchema = (ICFBamSchemaDefObj)curDef;
    			lastInterruptedSchema = topSchema;
    		}
    		else {
    			lastInterruptedSchema = null;
    		}
    	}

    	if( topSchema != null ) {
    		curDef = firstSchemaEncountered;
    	}
    	else {
    		curDef = anyDef;
    	}

        for ( ; curDef != null; curDef = curDef.getObjScope() ) {
            if( curDef instanceof ICFBamTopProjectObj ) {
                return( curDef );
            }
            else if( curDef instanceof ICFBamSubProjectObj ) {
                return( curDef );
            }
            else if( curDef instanceof ICFBamTenantObj ) {
                return (null);
            }
        }
        return (null);
    }

    public static ICFBamSchemaDefObj getTopSchema(ICFLibAnyObj anyDef)
    {
    	ICFBamSchemaDefObj topSchema = null;
    	ICFLibAnyObj curDef;
    	for( curDef = anyDef; curDef != null; curDef = curDef.getObjScope() ) {
    		if( curDef instanceof ICFBamSchemaDefObj ) {
    			topSchema = (ICFBamSchemaDefObj)curDef;
    		}
    	}
        return (topSchema);
    }

    public static ICFBamTopDomainObj getTopDomain(ICFLibAnyObj anyDef)
    {
		final String S_ProcName = "MSSBamCFAnyObj.getTopDomain";
    	ICFLibAnyObj curDef = anyDef;
    	ICFBamSchemaDefObj defSchema;
		if( curDef != null ) {
    		defSchema = getDefSchema( curDef );
		}
		else {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"curDef" );
		}

   		if( defSchema != null ) {
			curDef = defSchema;
   		}

    	for ( ; curDef != null; curDef = curDef.getObjScope())
        {
            if (curDef instanceof ICFBamTopDomainObj)
            {
                return ((ICFBamTopDomainObj)curDef);
            }
            else if (curDef instanceof ICFBamTenantObj)
            {
                return (null);
            }
        }
        return (null);
    }

    public static String getTopDomainName(ICFLibAnyObj anyDef)
    {
    	ICFLibAnyObj curDef = getTopDomain(anyDef);
		if( curDef != null ) {
            String s = null;
            s = curDef.getObjName();
            curDef = curDef.getObjScope();
            while (curDef != null) {
                if (curDef instanceof ICFBamTldObj) {
                    s = curDef.getObjName() + "." + s;
                    curDef = null;
                }
                else if (curDef instanceof ICFBamTopDomainObj) {
                    s = curDef.getObjName() + "." + s;
                    curDef = curDef.getObjScope();
                }
                else if (curDef instanceof ICFBamTenantObj) {
                    curDef = null;
                }
                else {
                    curDef = curDef.getObjScope();
                }
            }
            return( s );
        }
        else {
            return( null );
        }
	}

    public static ICFBamSchemaDefObj getSchema(ICFLibAnyObj anyDef)
    {
    	for ( ICFLibAnyObj curDef = anyDef; curDef != null; curDef = curDef.getObjScope())
        {
            if (curDef instanceof ICFBamSchemaDefObj)
            {
                return ((ICFBamSchemaDefObj)curDef);
            }
            else if (curDef instanceof ICFBamTenantObj)
            {
                return (null);
            }
        }
        return (null);
    }

    public static ICFLibAnyObj getVersionLeaf(ICFLibAnyObj anyDef)
    {
		final String S_ProcName = "MSSBamCFAnyObj.getVersionLeaf";
    	ICFLibAnyObj curDef = anyDef;
    	ICFBamSchemaDefObj defSchema;
		if( curDef != null ) {
			if( curDef instanceof ICFBamSchemaDefObj ) {
				defSchema = (ICFBamSchemaDefObj)curDef;
			}
			else {
				defSchema = getDefSchema( curDef );
			}
		}
		else {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"curDef" );
		}

   		if( defSchema != null ) {
			curDef = defSchema;
   		}

    	for ( ; curDef != null; curDef = curDef.getObjScope() ) {
            if (curDef instanceof ICFBamMinorVersionObj) {
                return (curDef);
            }
            else if (curDef instanceof ICFBamMajorVersionObj) {
                return (curDef);
            }
            else if (curDef instanceof ICFBamTopProjectObj) {
                return (null);
            }
            else if (curDef instanceof ICFBamSubProjectObj) {
                return (null);
            }
            else if (curDef instanceof ICFBamTenantObj) {
                return (null);
            }
        }
        return (null);
    }

    public static String getVersionString(ICFLibAnyObj anyDef)
    {
		final String S_ProcName = "MSSBamCFAnyObj.getVersionString";
    	ICFLibAnyObj curDef = anyDef;
    	ICFBamSchemaDefObj defSchema;
		if( curDef != null ) {
			if( curDef instanceof ICFBamSchemaDefObj ) {
				defSchema = (ICFBamSchemaDefObj)curDef;
			}
			else {
				defSchema = getDefSchema( curDef );
			}
		}
		else {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"curDef" );
		}

   		if( defSchema != null ) {
			curDef = defSchema;
   		}

    	ICFLibAnyObj scopeDef;
        ICFLibAnyObj versionLeafDef = getVersionLeaf(curDef);
        List<String> invertedNodeNames = new ArrayList<String>();
        while (versionLeafDef != null) {
            invertedNodeNames.add(versionLeafDef.getObjName());
            scopeDef = versionLeafDef.getObjScope();
            if (scopeDef == null) {
                versionLeafDef = null;
            }
            else if (scopeDef instanceof ICFBamMinorVersionObj) {
                versionLeafDef = (ICFBamMinorVersionObj)scopeDef;
            }
            else if (scopeDef instanceof ICFBamMajorVersionObj) {
                versionLeafDef = (ICFBamMajorVersionObj)scopeDef;
            }
            else {
                versionLeafDef = null;
            }
        }

        String ret = "";
        for (int idx = invertedNodeNames.size() - 1; idx >= 0; idx--) {
            if (ret.length() == 0) {
                ret = invertedNodeNames.get(idx);
            }
            else {
                ret = ret + "-" + invertedNodeNames.get(idx);
            }
        }

        return( ret );
    }

    public static String getPackedVersionString(ICFLibAnyObj anyDef)
    {
		final String S_ProcName = "MSSBamCFAnyObj.getPackedVersionString";
    	ICFLibAnyObj curDef = anyDef;
    	ICFBamSchemaDefObj defSchema;
		if( curDef != null ) {
    		defSchema = getDefSchema( curDef );
		}
		else {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"curDef" );
		}

   		if( defSchema != null ) {
			curDef = defSchema;
   		}

    	ICFLibAnyObj scopeDef;
        ICFLibAnyObj versionLeafDef = getVersionLeaf(curDef);
        List<String> invertedNodeNames = new ArrayList<String>();
        while (versionLeafDef != null)
        {
            invertedNodeNames.add(versionLeafDef.getObjName() );
            scopeDef = versionLeafDef.getObjScope();
            if (scopeDef == null) {
                versionLeafDef = null;
            }
            else if (scopeDef instanceof ICFBamMinorVersionObj) {
                versionLeafDef = (ICFBamMinorVersionObj)scopeDef;
            }
            else if (scopeDef instanceof ICFBamMajorVersionObj) {
                versionLeafDef = (ICFBamMajorVersionObj)scopeDef;
            }
            else {
                versionLeafDef = null;
            }
        }
        String ret = "";
        for (int idx = invertedNodeNames.size() - 1; idx >= 0; idx--) {
            if (ret.length() == 0) {
                ret = invertedNodeNames.get(idx);
            }
            else {
                ret = ret + invertedNodeNames.get(idx);
            }
        }
        return( ret );
    }

    public static String getFullName(ICFLibAnyObj anyDef)
    {
        String ret = anyDef.getObjName();
        ICFLibAnyObj scopeDef = anyDef.getObjScope();
        while ((scopeDef != null) && ! (scopeDef instanceof ICFBamTenantObj))
        {
            ret = scopeDef.getObjName() + "." + ret;
            scopeDef = scopeDef.getObjScope();
        }
        return (ret);
    }

    public static String getPackageName(ICFLibAnyObj anyDef)
    {
        ICFLibAnyObj pkg = getPackage(anyDef);
        String ret = pkg.getObjName();
        return (ret);
    }

    public static String getModelName(ICFLibAnyObj anyDef)
    {
        String ret = anyDef.getObjName();
        ICFLibAnyObj scopeDef = anyDef.getObjScope();
        while ((scopeDef != null) && !(scopeDef instanceof ICFBamTenantObj))
        {
            ret = scopeDef.getObjName() + "." + ret;
            scopeDef = scopeDef.getObjScope();
        }
        return (ret);
    }

    public static ICFLibAnyObj getDefPackage(ICFLibAnyObj anyDef)
    {
		final String S_ProcName = "MSSBamCFAnyObj.getDefPackage";
    	ICFLibAnyObj curDef = anyDef;
    	ICFBamSchemaDefObj defSchema;
		if( curDef != null ) {
    		defSchema = getDefSchema( curDef );
		}
		else {
			throw new CFLibNullArgumentException( MSSBamCFAnyObj.class,
				S_ProcName,
				0,
				"curDef" );
		}

   		if( defSchema != null ) {
			curDef = defSchema;
   		}

        for (; curDef != null; curDef = curDef.getObjScope()) {
            if (curDef instanceof ICFBamTopProjectObj) {
                return (curDef);
            }
            else if (curDef instanceof ICFBamSubProjectObj) {
                return (curDef);
            }
            else if (curDef instanceof ICFBamTenantObj) {
                return (null);
            }
            else {
                curDef = curDef.getObjScope();
            }
        }
        return (null);
    }

    public static ICFLibAnyObj getPackage(ICFLibAnyObj anyDef)
    {
    	ICFBamSchemaDefObj topSchema = null;
    	ICFBamSchemaDefObj firstSchemaEncountered = null;
    	ICFBamSchemaDefObj lastInterruptedSchema = null;
    	for( ICFLibAnyObj curDef = anyDef; curDef != null; curDef = curDef.getObjScope() ) {
    		if( curDef instanceof ICFBamSchemaDefObj ) {
    			if( lastInterruptedSchema == null ) {
    				firstSchemaEncountered = (ICFBamSchemaDefObj)curDef;
    			}
    			topSchema = (ICFBamSchemaDefObj)curDef;
    			lastInterruptedSchema = topSchema;
    		}
    		else {
    			lastInterruptedSchema = null;
    		}
    	}

    	ICFLibAnyObj curDef;
    	if( topSchema != null ) {
    		curDef = firstSchemaEncountered;
    	}
    	else {
    		curDef = anyDef;
    	}

        for (; curDef != null; curDef = curDef.getObjScope()) {
            if (curDef instanceof ICFBamTopProjectObj) {
                return (curDef);
            }
            else if (curDef instanceof ICFBamSubProjectObj) {
                return (curDef);
            }
            else if (curDef instanceof ICFBamTenantObj) {
                return (null);
            }
        }
        return (null);
    }

    public static ICFLibAnyObj getDefByModelName(
        ICFBamScopeObj modelScope,
        String modelName)
    {
        try
        {
            String thisName;
            String remainder;
            int idxDot = modelName.indexOf('.');
            if (idxDot > 0)
            {
                thisName = modelName.substring(0, idxDot);
                remainder = modelName.substring(idxDot + 1, modelName.length() - (idxDot + 1));
            }
            else
            {
                thisName = modelName;
                remainder = "";
            }

            ICFLibAnyObj resolved = modelScope.getNamedObject( thisName );
            if (resolved == null)
            {
                throw new RuntimeException("MSSBamCFAnyObj.getDefByModelName() Could not resolve \"" + thisName + "\" in scope \"" + getFullName(modelScope) + "\"");
            }

            if (remainder.length() > 0)
            {
                resolved = getDefByModelName((ICFBamScopeObj)resolved, remainder);
            }

            return (resolved);
        }
        catch (NullPointerException e)
        {
            System.err.append("MSSBamCFAnyObj.getDefByModelName() Caught NullPointerException\n");
            throw e;
        }
    }

    public static ICFLibAnyObj getDefByGlobalName(
        ICFBamScopeObj modelScope,
        String globalName)
    {
        final String S_ProcName = "MSSBamCFAnyObj.getDefByGlobalName() ";

        if ((globalName == null) || (globalName.length() == 0)) {
            throw new RuntimeException(S_ProcName + "Argument globalName is null or empty");
        }

        ICFBamScopeObj searchScope = modelScope;
        while ((searchScope != null)
            && (!(searchScope instanceof ICFBamTenantObj)))
        {
            searchScope = (ICFBamScopeObj)(searchScope.getObjScope());
        }
        if (searchScope == null) {
            throw new RuntimeException(S_ProcName + "Could not chain scope to a Tenant definition");
        }

        ICFLibAnyObj searchDef = searchScope;
        ICFLibAnyObj foundNext;
        String remainingName = globalName;
        String nextName;
        String nowRemaining;
        while ((remainingName != null) && (remainingName.length() > 0)) {
            int nextDot = remainingName.indexOf('.');
            if (nextDot > 0) {
                nextName = remainingName.substring(0, nextDot);
                nowRemaining = remainingName.substring(nextDot + 1);
            }
            else {
                nextName = remainingName;
                nowRemaining = "";
            }
        	foundNext = searchDef.getNamedObject( nextName );
            if (foundNext == null) {
            	throw new CFLibRuntimeException( MSSBamCFAnyObj.class,
            		S_ProcName,
            		"Could not resolve name part " + nextName + " of global name \"" + globalName + "\" in " + searchDef.getClass().getSimpleName() + " " + searchDef.getObjName() );
            }
            searchDef = foundNext;
            remainingName = nowRemaining;
        }
        return (searchDef);
    }
}
