//$HeadURL: svn+ssh://jwilden@scm.wald.intevation.org/deegree/base/trunk/src/org/deegree/io/datastore/sql/oracle/OracleSpatialWhereBuilder.java $
/*----------------    FILE HEADER  ------------------------------------------

 This file is part of deegree.
 Copyright (C) 2001-2008 by:
 EXSE, Department of Geography, University of Bonn
 http://www.giub.uni-bonn.de/deegree/
 lat/lon GmbH
 http://www.lat-lon.de

 This library is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public
 License as published by the Free Software Foundation; either
 version 2.1 of the License, or (at your option) any later version.

 This library is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public
 License along with this library; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

 Contact:

 Andreas Poth
 lat/lon GmbH
 Aennchenstr. 19
 53115 Bonn
 Germany
 E-Mail: poth@lat-lon.de

 Prof. Dr. Klaus Greve
 Department of Geography
 University of Bonn
 Meckenheimer Allee 166
 53115 Bonn
 Germany
 E-Mail: greve@giub.uni-bonn.de

 
 ---------------------------------------------------------------------------*/
package org.deegree.io.datastore.sql.oracle;

import java.sql.Types;

import oracle.spatial.geometry.JGeometry;

import org.deegree.i18n.Messages;
import org.deegree.io.datastore.DatastoreException;
import org.deegree.io.datastore.schema.MappedFeatureType;
import org.deegree.io.datastore.schema.MappedGeometryPropertyType;
import org.deegree.io.datastore.sql.StatementBuffer;
import org.deegree.io.datastore.sql.TableAliasGenerator;
import org.deegree.io.datastore.sql.VirtualContentProvider;
import org.deegree.io.datastore.sql.wherebuilder.WhereBuilder;
import org.deegree.model.filterencoding.Filter;
import org.deegree.model.filterencoding.OperationDefines;
import org.deegree.model.filterencoding.SpatialOperation;
import org.deegree.model.spatialschema.Geometry;
import org.deegree.model.spatialschema.GeometryException;
import org.deegree.ogcbase.SortProperty;

/**
 * {@link WhereBuilder} implementation for Oracle Spatial. Supports Oracle Spatial for Oracle
 * Database 10g.
 * 
 * @author <a href="mailto:schneider@lat-lon.de">Markus Schneider </A>
 * @author <a href="mailto:tfr@users.sourceforge.net">Torsten Friebe </A>
 * @author last edited by: $Author: aschmitz $
 * 
 * @version $Revision: 10506 $, $Date: 2008-03-06 17:50:33 +0100 (Thu, 06 Mar 2008) $
 */
public class OracleSpatialWhereBuilder extends WhereBuilder {

    private static final int SRS_UNDEFINED = -1;

    private OracleDatastore ds;

    /**
     * Creates a new instance of <code>OracleSpatialWhereBuilder</code> from the given parameters.
     * 
     * @param rootFts
     *            selected feature types, more than one type means that the types are joined
     * @param aliases
     *            aliases for the feature types, may be null (must have same length as rootFts
     *            otherwise)
     * @param filter
     *            filter that restricts the matched features
     * @param sortProperties
     *            sort criteria for the result, may be null or empty
     * @param aliasGenerator
     *            used to generate unique table aliases
     * @param vcProvider
     * @throws DatastoreException
     */
    public OracleSpatialWhereBuilder( MappedFeatureType[] rootFts, String[] aliases, Filter filter,
                                      SortProperty[] sortProperties, TableAliasGenerator aliasGenerator,
                                      VirtualContentProvider vcProvider ) throws DatastoreException {
        super( rootFts, aliases, filter, sortProperties, aliasGenerator, vcProvider );
        this.ds = (OracleDatastore) rootFts[0].getGMLSchema().getDatastore();
    }

    /**
     * Generates an SQL-fragment for the given object.
     * 
     * @throws DatastoreException
     */
    @Override
    protected void appendSpatialOperationAsSQL( StatementBuffer query, SpatialOperation operation )
                            throws DatastoreException {

        try {
            switch ( operation.getOperatorId() ) {
            case OperationDefines.BBOX:
            case OperationDefines.INTERSECTS: {
                appendRelateOperationAsSQL( query, operation, "ANYINTERACT" );
                break;
            }
            case OperationDefines.EQUALS: {
                appendRelateOperationAsSQL( query, operation, "EQUAL" );
                break;
            }
            case OperationDefines.DISJOINT: {
                query.append( "NOT " );
                appendRelateOperationAsSQL( query, operation, "ANYINTERACT" );
                break;
            }
            case OperationDefines.TOUCHES: {
                appendRelateOperationAsSQL( query, operation, "TOUCH" );
                break;
            }
            case OperationDefines.WITHIN: {
                appendRelateOperationAsSQL( query, operation, "INSIDE+COVEREDBY" );
                break;
            }
            case OperationDefines.OVERLAPS: {
                appendRelateOperationAsSQL( query, operation, "OVERLAPBDYINTERSECT" );
                break;
            }
            case OperationDefines.CROSSES: {
                appendRelateOperationAsSQL( query, operation, "OVERLAPBDYDISJOINT" );
                break;
            }
            case OperationDefines.CONTAINS: {
                appendRelateOperationAsSQL( query, operation, "CONTAINS+COVERS" );
                break;
            }
            case OperationDefines.DWITHIN: {
                appendDWithinOperationAsSQL( query, operation );
                break;
            }
            case OperationDefines.BEYOND: {
                query.append( "NOT " );
                appendDWithinOperationAsSQL( query, operation );
                break;
            }
            default: {
                String msg = "Spatial operator" + OperationDefines.getNameById( operation.getOperatorId() )
                             + " not supported by '" + this.getClass().toString() + "'.";
                throw new DatastoreException( msg );
            }
            }
        } catch ( GeometryException e ) {
            throw new DatastoreException( e );
        }

    }

    private void appendRelateOperationAsSQL( StatementBuffer query, SpatialOperation operation, String mask )
                            throws GeometryException, DatastoreException {
        query.append( "MDSYS.SDO_RELATE(" );
        appendPropertyNameAsSQL( query, operation.getPropertyName() );
        query.append( ',' );
        appendGeometryArgument( query, getGeometryProperty( operation.getPropertyName() ), operation.getGeometry() );
        query.append( ",'MASK=" + mask + " QUERYTYPE=WINDOW')='TRUE'" );
    }

    private void appendDWithinOperationAsSQL( StatementBuffer query, SpatialOperation operation )
                            throws GeometryException, DatastoreException {

        query.append( "SDO_WITHIN_DISTANCE(" );
        appendPropertyNameAsSQL( query, operation.getPropertyName() );
        query.append( ',' );
        appendGeometryArgument( query, getGeometryProperty( operation.getPropertyName() ), operation.getGeometry() );
        query.append( ",'DISTANCE=" + operation.getDistance() + "')='TRUE'" );
    }

    /**
     * Construct and append the geometry argument using the correct internal SRS and perform a
     * transform call to the internal SRS of the {@link MappedGeometryPropertyType} if necessary.
     * 
     * @param query
     * @param geoProperty
     * @param geometry
     * @throws DatastoreException
     * @throws GeometryException
     */
    private void appendGeometryArgument( StatementBuffer query, MappedGeometryPropertyType geoProperty,
                                         Geometry geometry )
                            throws DatastoreException, GeometryException {

        String argumentSRS = null;
        if ( geometry.getCoordinateSystem() != null ) {
            argumentSRS = geometry.getCoordinateSystem().getIdentifier();
        }
        String propertySRS = geoProperty.getCS().getIdentifier();
        int internalSRS = geoProperty.getMappingField().getSRS();

        int createSRSCode = getArgumentSRSCode( argumentSRS, propertySRS, internalSRS );
        JGeometry argument = JGeometryAdapter.export( geometry, createSRSCode );

        int targetSRSCode = getTargetSRSCode( argumentSRS, propertySRS, internalSRS );
        if ( targetSRSCode != SRS_UNDEFINED ) {
            query.append( ds.buildSRSTransformCall( "?", targetSRSCode ) );
        } else {
            query.append( '?' );
        }
        query.addArgument( argument, Types.STRUCT );
    }

    /**
     * Returns the internal SRS code that must be used for the creation of a geometry argument used
     * in a spatial operator.
     * 
     * @param literalSRS
     * @param propertySRS
     * @param internalSrs
     * @return the internal SRS code that must be used for the creation of a geometry argument
     * @throws DatastoreException
     */
    private int getArgumentSRSCode( String argumentSRS, String propertySRS, int internalSrs )
                            throws DatastoreException {
        int argumentSRSCode = internalSrs;
        if ( argumentSRS == null ) {
            argumentSRSCode = internalSrs;
        } else if ( !propertySRS.equals( argumentSRS ) ) {
            argumentSRSCode = this.ds.getNativeSRSCode( argumentSRS );
            if ( argumentSRSCode == SRS_UNDEFINED ) {
                String msg = Messages.getMessage( "DATASTORE_SQL_NATIVE_CT_UNKNOWN_SRS",
                                                  OracleDatastore.class.getName(), argumentSRS );
                throw new DatastoreException( msg );
            }
        }
        return argumentSRSCode;
    }

    /**
     * Returns the internal SRS code that must be used for the transform call for a geometry
     * argument used in a spatial operator.
     * 
     * @param literalSRS
     * @param propertySRS
     * @param internalSrs
     * @return the internal SRS code that must be used for the transform call of a geometry
     *         argument, or -1 if no transformation is necessary
     */
    private int getTargetSRSCode( String argumentSRS, String propertySRS, int internalSrs )
                            throws DatastoreException {
        int targetSRS = SRS_UNDEFINED;
        if ( argumentSRS != null && !argumentSRS.equals( propertySRS ) ) {
            if ( internalSrs == SRS_UNDEFINED ) {
                String msg = Messages.getMessage( "DATASTORE_SRS_NOT_SPECIFIED2", argumentSRS, propertySRS );
                throw new DatastoreException( msg );
            }
            targetSRS = internalSrs;
        }
        return targetSRS;
    }
}