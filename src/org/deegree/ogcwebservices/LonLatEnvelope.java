// $HeadURL: svn+ssh://jwilden@scm.wald.intevation.org/deegree/base/trunk/src/org/deegree/ogcwebservices/LonLatEnvelope.java $
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

package org.deegree.ogcwebservices;

import java.io.Serializable;

import org.deegree.datatypes.time.TimePosition;
import org.deegree.framework.util.StringTools;
import org.deegree.model.spatialschema.Envelope;
import org.deegree.model.spatialschema.GeometryFactory;
import org.deegree.model.spatialschema.Point;

/**
 * @version $Revision: 9345 $
 * @author <a href="mailto:poth@lat-lon.de">Andreas Poth</a>
 * @author last edited by: $Author: apoth $
 * 
 * @version 1.0. $Revision: 9345 $, $Date: 2007-12-27 17:22:25 +0100 (Thu, 27 Dec 2007) $
 * 
 * @since 2.0
 */

public class LonLatEnvelope implements Cloneable, Serializable {

    private static final long serialVersionUID = 6225897903182806919L;

    private Point min = null;

    private Point max = null;

    private TimePosition[] timePositions = new TimePosition[0];

    private String srs = "WGS84(DD)";

    /**
     * @param min
     * @param max
     */
    public LonLatEnvelope( Point min, Point max ) {
        this.min = min;
        this.max = max;
    }

    /**
     * @param min
     * @param max
     * @param srs
     */
    public LonLatEnvelope( Point min, Point max, String srs ) {
        this.min = min;
        this.max = max;
        this.srs = srs;
    }

    /**
     * @param min
     * @param max
     * @param timePositions
     * @param srs
     */
    public LonLatEnvelope( Point min, Point max, TimePosition[] timePositions, String srs ) {
        this.min = min;
        this.max = max;
        this.timePositions = timePositions;
        this.srs = srs;
    }

    /**
     * @param minx
     * @param miny
     * @param maxx
     * @param maxy
     */
    public LonLatEnvelope( double minx, double miny, double maxx, double maxy ) {
        this.min = GeometryFactory.createPoint( minx, miny, null );
        this.max = GeometryFactory.createPoint( maxx, maxy, null );
    }

    /**
     * @param envelope
     */
    public LonLatEnvelope( Envelope envelope ) {
        this.min = GeometryFactory.createPoint( envelope.getMin().getX(), envelope.getMin().getY(), null );
        this.max = GeometryFactory.createPoint( envelope.getMax().getX(), envelope.getMax().getY(), null );
    }

    /**
     * @return Returns the max.
     */
    public Point getMax() {
        return max;
    }

    /**
     * @param max
     *            The max to set.
     */
    public void setMax( Point max ) {
        this.max = max;
    }

    /**
     * @return Returns the min.
     */
    public Point getMin() {
        return min;
    }

    /**
     * @param min
     *            The min to set.
     */
    public void setMin( Point min ) {
        this.min = min;
    }

    /**
     * @return Returns the srs.
     */
    public String getSrs() {
        return srs;
    }

    /**
     * @param srs
     *            The srs to set.
     */
    public void setSrs( String srs ) {
        this.srs = srs;
    }

    /**
     * @return Returns the timePositions.
     */
    public TimePosition[] getTimePositions() {
        return timePositions;
    }

    /**
     * @param timePositions
     *            The timePositions to set.
     */
    public void setTimePositions( TimePosition[] timePositions ) {
        if ( timePositions == null ) {
            timePositions = new TimePosition[0];
        }
        this.timePositions = timePositions;
    }

    public Object clone() {
        TimePosition[] timePositions_ = new TimePosition[timePositions.length];
        for ( int i = 0; i < timePositions_.length; i++ ) {
            timePositions_[i] = (TimePosition) timePositions[i].clone();
        }

        return new LonLatEnvelope( min, max, timePositions_, new String( srs ) );
    }

    public String toString() {
        return StringTools.concat( 200, "min: ", min, " max: ", max );
    }
}