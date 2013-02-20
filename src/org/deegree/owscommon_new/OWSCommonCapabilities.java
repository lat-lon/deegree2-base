//$HeadURL: svn+ssh://jwilden@scm.wald.intevation.org/deegree/base/trunk/src/org/deegree/owscommon_new/OWSCommonCapabilities.java $
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
package org.deegree.owscommon_new;

import java.util.Map;

/**
 * <code>OWSCommonCapabilities</code> stores the basic information common to all OGC capabilities
 * documents according to the OWS common specification version 1.0.0. It is designed to be used by
 * all OGC web services. The service specific content should go into a subclass of the
 * <code>Content</code> class.
 * 
 * @see Content
 * 
 * @author <a href="mailto:schmitz@lat-lon.de">Andreas Schmitz</a>
 * @author last edited by: $Author: apoth $
 * 
 * @version 2.0, $Revision: 10660 $, $Date: 2008-03-24 22:39:54 +0100 (Mon, 24 Mar 2008) $
 * 
 * @since 2.0
 */

public class OWSCommonCapabilities {

    private String version = null;

    private String updateSequence = null;

    private ServiceIdentification serviceIdentification = null;

    private ServiceProvider serviceProvider = null;

    private OperationsMetadata operationsMetadata = null;

    private Map<String, Content> contents = null;

    /**
     * Standard constructor that initializes all encapsulated data.
     * 
     * @param version
     * @param updateSequence
     * @param serviceIdentification
     * @param serviceProvider
     * @param operationsMetadata
     * @param contents
     */
    public OWSCommonCapabilities( String version, String updateSequence, ServiceIdentification serviceIdentification,
                                  ServiceProvider serviceProvider, OperationsMetadata operationsMetadata,
                                  Map<String, Content> contents ) {
        this.version = version;
        this.updateSequence = updateSequence;
        this.serviceIdentification = serviceIdentification;
        this.serviceProvider = serviceProvider;
        this.operationsMetadata = operationsMetadata;
        this.contents = contents;
    }

    /**
     * @return Returns the contents.
     */
    public Map<String, Content> getContents() {
        return contents;
    }

    /**
     * @return Returns the operationsMetadata.
     */
    public OperationsMetadata getOperationsMetadata() {
        return operationsMetadata;
    }

    /**
     * @return Returns the serviceIdentification.
     */
    public ServiceIdentification getServiceIdentification() {
        return serviceIdentification;
    }

    /**
     * @return Returns the serviceProvider.
     */
    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    /**
     * @return Returns the updateSequence.
     */
    public String getUpdateSequence() {
        return updateSequence;
    }

    /**
     * @return Returns the version.
     */
    public String getVersion() {
        return version;
    }

}
