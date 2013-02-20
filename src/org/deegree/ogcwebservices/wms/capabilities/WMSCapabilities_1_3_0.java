//$HeadURL: svn+ssh://jwilden@scm.wald.intevation.org/deegree/base/trunk/src/org/deegree/ogcwebservices/wms/capabilities/WMSCapabilities_1_3_0.java $
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
package org.deegree.ogcwebservices.wms.capabilities;

import java.util.LinkedList;
import java.util.List;

import org.deegree.owscommon_new.OperationsMetadata;
import org.deegree.owscommon_new.ServiceIdentification;
import org.deegree.owscommon_new.ServiceProvider;

/**
 * This class is an 1.3.0 extension of the WMSCapabilities class.
 * 
 * @author <a href="mailto:schmitz@lat-lon.de">Andreas Schmitz</a>
 * @author last edited by: $Author: rbezema $
 * 
 * @version 2.0, $Revision: 11335 $, $Date: 2008-04-22 09:15:25 +0200 (Tue, 22 Apr 2008) $
 * 
 * @since 2.0
 */

public class WMSCapabilities_1_3_0 extends WMSCapabilities {

    private static final long serialVersionUID = 1081688101474553998L;

    private int maxWidth;

    private int maxHeight;

    private int layerLimit;

    /**
     * Constructs a new object with the given values. The difference to the 1.1.1 capabilities is
     * that no user defined symbolization can be used, and layerLimit, maxWidth and maxHeight were
     * added.
     * @param version 
     * @param updateSequence 
     * @param serviceIdentification 
     * @param serviceProvider 
     * @param metadata 
     * @param layer 
     * @param layerLimit 
     * @param maxWidth 
     * @param maxHeight 
     */
    protected WMSCapabilities_1_3_0( String version, String updateSequence,
                                     ServiceIdentification serviceIdentification, ServiceProvider serviceProvider,
                                     OperationsMetadata metadata, Layer layer, int layerLimit, int maxWidth,
                                     int maxHeight ) {
        super( version, updateSequence, serviceIdentification, serviceProvider, null, metadata, layer );
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.layerLimit = layerLimit;
        exceptions = new LinkedList<String>();
        if ( version.equals( "1.3.0" ) ) {
            exceptions.add( "XML" );
        } else {
            exceptions.add( "application/vnd.ogc.se_xml" );
        }
    }

    protected WMSCapabilities_1_3_0( String version, String updateSequence,
                                     ServiceIdentification serviceIdentification, ServiceProvider serviceProvider,
                                     OperationsMetadata metadata, Layer layer, int layerLimit, int maxWidth,
                                     int maxHeight, List<String> exceptions ) {
        this( version, updateSequence, serviceIdentification, serviceProvider, metadata, layer, maxWidth, maxHeight,
              layerLimit );
        this.exceptions = exceptions;
    }

    /**
     * @return the layerLimit.
     */
    public int getLayerLimit() {
        return layerLimit;
    }

    /**
     * @return the maxHeight.
     */
    public int getMaxHeight() {
        return maxHeight;
    }

    /**
     * @return the maxWidth.
     */
    public int getMaxWidth() {
        return maxWidth;
    }

}
