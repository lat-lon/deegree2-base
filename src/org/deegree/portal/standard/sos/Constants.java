//$HeadURL: svn+ssh://jwilden@scm.wald.intevation.org/deegree/base/trunk/src/org/deegree/portal/standard/sos/Constants.java $

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
package org.deegree.portal.standard.sos;

/**
 * Constants for SOSClient.
 * 
 * @author <a href="mailto:che@wupperverband.de.de">Christian Heier</a>
 * @version 0.1
 */
public class Constants {

    private Constants(){
        // prevent instantiation
    }
    
    /**
     * Comment for <code>TYPENAME</code>
     */
    public static final String TYPENAME = "TypeName";

    /**
     * Comment for <code>PLATFORMDESCRIPTION</code>
     */
    public static final String PLATFORMDESCRIPTION = "PlatformDescription";

    /**
     * Comment for <code>PLATFORMDESCRIPTION</code>
     */
    public static final String SENSORDESCRIPTION = "SensorDescription";

    public static final String OBSERVATION_DATA = "ObservationData";
    
}