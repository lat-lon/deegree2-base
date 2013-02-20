//$HeadURL: svn+ssh://developername@svn.wald.intevation.org/deegree/base/trunk/src/org/deegree/ogcwebservices/wms/capabilities/WMSCapabilities.java $
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
 * <code>WMSCapabilities</code> is the data class for the WMS version of capabilities. Since WMS
 * is not yet using the OWS commons implementation, it is more or less just a copy of the old
 * version.
 * 
 * @author <a href="mailto:poth@lat-lon.de">Andreas Poth</a>
 * @version $Revision: 8307 $
 */
public class WMSCapabilities_1_0_0 extends WMSCapabilities {

    private static final long serialVersionUID = 7178105896289110746L;

    protected List<String> exceptions;

    /**
     * constructor initializing the class with the <code>WMSCapabilities</code>
     * 
     * @param updateSequence
     * @param serviceIdentification
     * @param serviceProvider
     * @param metadata
     * @param layer
     */
    protected WMSCapabilities_1_0_0( String updateSequence, ServiceIdentification serviceIdentification,
                                     ServiceProvider serviceProvider, OperationsMetadata metadata, Layer layer ) {
        super( "1.0.0", updateSequence, serviceIdentification, serviceProvider, null, metadata, layer );

        setServiceProvider( serviceProvider );
        setServiceIdentification( serviceIdentification );
        setOperationMetadata( metadata );
        setLayer( layer );
        exceptions = new LinkedList<String>();
        exceptions.add( "WMS_XML" );

    }

    /**
     * @param updateSequence
     * @param serviceIdentification
     * @param serviceProvider
     * @param metadata
     * @param layer
     * @param exceptions
     */
    protected WMSCapabilities_1_0_0( String updateSequence, ServiceIdentification serviceIdentification,
                                     ServiceProvider serviceProvider, OperationsMetadata metadata, Layer layer,
                                     List<String> exceptions ) {
        this( updateSequence, serviceIdentification, serviceProvider, metadata, layer );
        this.exceptions = exceptions;
    }

}
