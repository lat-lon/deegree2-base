//$HeadURL: svn+ssh://jwilden@scm.wald.intevation.org/deegree/base/trunk/src/org/deegree/ogcwebservices/wfs/capabilities/FormatType.java $
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
package org.deegree.ogcwebservices.wfs.capabilities;

import java.net.URI;

/**
 * Defines the format (for feature types) to be is served by the WFS.
 * <p>
 * This includes: <table>
 * <tr>
 * <td>inFilter</td>
 * <td>optional: location of an XSL-script to be applied to requests</td>
 * </tr>
 * <tr>
 * <td>outFilter</td>
 * <td>optional: location of an XSL-script to be applied to responses</td>
 * </tr>
 * <tr>
 * <td>schemaLocation</td>
 * <td>optional: location of schema document (for
 * {@link org.deegree.ogcwebservices.wfs.operation.DescribeFeatureType DescribeFeatureType}
 * requests)</td>
 * </tr>
 * <tr>
 * <td>value</td>
 * <td>name of the format (e.g. <code>text/xml; subtype=gml/3.1.1</code>)</td>
 * </tr>
 * </table>
 * 
 * @author <a href="mailto:schneider@lat-lon.de">Markus Schneider </a>
 * @author last edited by: $Author: apoth $
 * 
 * @version $Revision: 9345 $ $Date: 2007-12-27 17:22:25 +0100 (Thu, 27 Dec 2007) $
 */
public class FormatType {

    private URI inFilter, outFilter, schemaLocation;

    private String value;

    /**
     * Creates a new instance of <code>FormatType<code> from the given parameters.
     *
     * @param inFilter
     *            location of an XSL-script to be applied to requests, may be null
     * @param outFilter
     *            location of an XSL-script to be applied to responses, may be null
     * @param schemaLocation
     *            location of schema document (for {@link org.deegree.ogcwebservices.wfs.operation.DescribeFeatureType DescribeFeatureType} requests, may be null
     * @param value
     *            name of the format (e.g. <code>text/xml; subtype=gml/3.1.1</code>)
     */
    public FormatType( URI inFilter, URI outFilter, URI schemaLocation, String value ) {
        this.inFilter = inFilter;
        this.outFilter = outFilter;
        this.schemaLocation = schemaLocation;
        this.value = value;
    }

    /**
     * Returns the location of the XSL-script to be applied to requests.
     * 
     * @return the location of the XSL-script to be applied to requests, may be null
     */
    public URI getInFilter() {
        return inFilter;
    }

    /**
     * Returns the location of the XSL-script to be applied to responses.
     * 
     * @return the location of the XSL-script to be applied to responses, may be null
     */
    public URI getOutFilter() {
        return outFilter;
    }

    /**
     * Returns the location of the schema document (for
     * {@link org.deegree.ogcwebservices.wfs.operation.DescribeFeatureType DescribeFeatureType}
     * requests).
     * 
     * @return the location of the schema document (for
     *         {@link org.deegree.ogcwebservices.wfs.operation.DescribeFeatureType DescribeFeatureType}
     *         requests), may be null
     */
    public URI getSchemaLocation() {
        return schemaLocation;
    }

    /**
     * Returns the name of the format.
     * 
     * @return the name of the format
     */
    public String getValue() {
        return value;
    }

    /**
     * Returns whether the format definition is virtual, i.e. it is processed using an (input)
     * XSLT-script.
     * 
     * @return true, if the format is virtual, false otherwise
     */
    public boolean isVirtual() {
        return this.inFilter != null;
    }
}
