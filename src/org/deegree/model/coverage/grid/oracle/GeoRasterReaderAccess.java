//$HeadURL$
/*----------------    FILE HEADER  ------------------------------------------
 This file is part of deegree.
 Copyright (C) 2001-2008 by:
 Department of Geography, University of Bonn
 http://www.giub.uni-bonn.de/deegree/
 lat/lon GmbH
 http://www.lat-lon.de

 This library is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public
 License as published by the Free Software Foundation; either
 version 2.1 of the License, or (at your option) any later version.
 This library is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 Lesser General Public License for more details.
 You should have received a copy of the GNU Lesser General Public
 License along with this library; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 Contact:

 Andreas Poth
 lat/lon GmbH
 Aennchenstr. 19
 53177 Bonn
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

package org.deegree.model.coverage.grid.oracle;

import java.io.IOException;
import java.lang.reflect.Constructor;

import org.deegree.framework.log.ILogger;
import org.deegree.framework.log.LoggerFactory;
import org.deegree.io.oraclegeoraster.GeoRasterDescription;
import org.deegree.model.coverage.grid.Format;
import org.deegree.model.coverage.grid.GCReaderAccess;
import org.deegree.model.coverage.grid.GridCoverageReader;
import org.deegree.model.spatialschema.Envelope;
import org.deegree.ogcwebservices.wcs.describecoverage.CoverageOffering;

/**
 * 
 * 
 * @author <a href="mailto:poth@lat-lon.de">Andreas Poth</a>
 * @author last edited by: $Author: poth $
 * 
 * @version $Revision: 6251 $, $Date: 2007-03-19 16:59:28 +0100 (Mo, 19 Mrz 2007) $
 */
public class GeoRasterReaderAccess implements GCReaderAccess {

    private static final ILogger LOG = LoggerFactory.getLogger( GeoRasterReaderAccess.class );

    /**
     * Creates a OracleGeoRasterGridCoverageReader instance from the given parameters.
     * 
     * @param grDesc
     * @param description
     * @param envelope
     * @param format
     * @return the GridCoverageReader
     * @throws IOException
     */
    public GridCoverageReader createGridCoverageReader( Object descriptor, CoverageOffering description,
                                                                       Envelope envelope, Format format )
                            throws IOException {
        GeoRasterDescription grDesc = (GeoRasterDescription) descriptor;
        GridCoverageReader gcr = null;
        try {
            Class gridCoverageReaderClass = Class.forName( "org.deegree.model.coverage.grid.oracle.OracleGeoRasterGridCoverageReader" );

            // get constructor
            Class[] parameterTypes = new Class[] { GeoRasterDescription.class, CoverageOffering.class, Envelope.class,
                                                  Format.class };
            Constructor constructor = gridCoverageReaderClass.getConstructor( parameterTypes );

            // call constructor
            Object arglist[] = new Object[] { grDesc, description, envelope, format };
            gcr = (GridCoverageReader) constructor.newInstance( arglist );
        } catch ( ClassNotFoundException e ) {
            LOG.logError( e.getMessage(), e );
            throw new IOException( "Cannot find Oracle raster library: " + e.getMessage() );
        } catch ( Exception e ) {
            LOG.logError( e.getMessage(), e );
            throw new IOException( e.getMessage() );
        }
        return gcr;

    }
}
