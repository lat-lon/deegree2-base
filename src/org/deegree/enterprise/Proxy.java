//$HeadURL: svn+ssh://jwilden@scm.wald.intevation.org/deegree/base/trunk/src/org/deegree/enterprise/Proxy.java $
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
package org.deegree.enterprise;

import java.util.Properties;

/**
 * encapsulates proxy informations and offers a method for setting and unsetting the proxy
 * 
 * @version $Revision: 9338 $
 * @author <a href="mailto:poth@lat-lon.de">Andreas Poth</a>
 */
public class Proxy {
    private String proxyHost = null;

    private String proxyPort = null;

    /** Creates a new instance of Proxy_Impl */
    public Proxy( String proxyHost, String proxyPort ) {
        this.proxyHost = proxyHost;
        this.proxyPort = proxyPort;
    }

    /**
     * retuns the proxy host definition
     * 
     * @return the proxy host definition
     * 
     */
    public String getProxyHost() {
        return proxyHost;
    }

    /**
     * returns the proxy port definition
     * 
     * @return the proxy port definition
     */
    public String getProxyPort() {
        return proxyPort;
    }

    /**
     * sets or unsets the proxy by writing the proxyHost and the proxyPort to the system properties.
     * 
     * @param proxySet
     */
    public void setProxy( boolean proxySet ) {
        Properties sysProperties = System.getProperties();

        // Specify proxy settings
        sysProperties.put( "proxyHost", proxyHost );
        sysProperties.put( "proxyPort", proxyPort );
        sysProperties.put( "proxySet", "" + proxySet );
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        String ret = "proxyHost: " + proxyHost + "\n";
        ret += "proxyPort: " + proxyPort;
        return ret;
    }
}
