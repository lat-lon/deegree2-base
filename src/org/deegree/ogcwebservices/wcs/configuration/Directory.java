// $HeadURL: svn+ssh://jwilden@scm.wald.intevation.org/deegree/base/trunk/src/org/deegree/ogcwebservices/wcs/configuration/Directory.java $
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
package org.deegree.ogcwebservices.wcs.configuration;

import org.deegree.model.crs.CoordinateSystem;
import org.deegree.model.spatialschema.Envelope;

/**
 * An instance of <tt>Directory</tt> describes a directory in the file system containing coverages
 * within the envelope assigned to the <tt>Directory</tt>. The name of the <tt>Directory</tt>
 * may is build from variable indicated by a leadin '$' (e.g.
 * C:/rasterdata/luftbilder/775165/$YEAR/$MONTH/$DAY/$ELEVATION/l0.5) in this case the variable
 * parts of the name can be replaced by an application with concrete values. It is in the
 * responsibility of the application to use valid values for the variables. Known variable names
 * are:
 * <ul>
 * <li>$YEAR
 * <li>$MONTH
 * <li>$DAY
 * <li>$HOUR
 * <li>$MINUTE
 * <li>$ELEVATION
 * </ul>
 * 
 * @version $Revision: 9345 $
 * @author <a href="mailto:poth@lat-lon.de">Andreas Poth</a>
 * @author last edited by: $Author: apoth $
 * 
 * @version 1.0. $Revision: 9345 $, $Date: 2007-12-27 17:22:25 +0100 (Thu, 27 Dec 2007) $
 * 
 * @since 2.0
 */

public class Directory extends CoverageSource {

    private String name = null;

    private Envelope envelope = null;

    private String[] fileExtensions = null;

    /**
     * @param name
     * @param envelope
     */
    public Directory( String name, Envelope envelope, CoordinateSystem crs ) {
        super( crs );
        this.name = name;
        this.envelope = envelope;
        this.fileExtensions = new String[0];
    }

    /**
     * @param name
     * @param envelope
     */
    public Directory( String name, Envelope envelope, CoordinateSystem crs, String[] fileExtensions ) {
        super( crs );
        this.name = name;
        this.envelope = envelope;
        this.fileExtensions = fileExtensions;
    }

    /**
     * @return Returns the envelope.
     */
    public Envelope getEnvelope() {
        return envelope;
    }

    /**
     * @param envelope
     *            The envelope to set.
     */
    public void setEnvelope( Envelope envelope ) {
        this.envelope = envelope;
    }

    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            The name to set.
     */
    public void setName( String name ) {
        this.name = name;
    }

    /**
     * @return Returns the fileExtensions.
     */
    public String[] getFileExtensions() {
        return fileExtensions;
    }

    /**
     * @param fileExtensions
     *            The fileExtensions to set.
     */
    public void setFileExtensions( String[] fileExtensions ) {
        this.fileExtensions = fileExtensions;
    }

}