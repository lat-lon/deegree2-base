//$HeadURL: svn+ssh://jwilden@scm.wald.intevation.org/deegree/base/trunk/src/org/deegree/datatypes/values/Closure.java $
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
package org.deegree.datatypes.values;

import java.io.Serializable;

/**
 * 
 * 
 * @version $Revision: 10660 $
 * @author <a href="mailto:poth@lat-lon.de">Andreas Poth</a>
 * @author last edited by: $Author: apoth $
 * 
 * @version 1.0. $Revision: 10660 $, $Date: 2008-03-24 22:39:54 +0100 (Mon, 24 Mar 2008) $
 * 
 * @since 2.0
 */
public class Closure implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Comment for <code>CLOSED</code>
     */
    public static final String CLOSED = "closed";

    /**
     * Comment for <code>OPENED</code>
     */
    public static final String OPENED = "open";

    /**
     * Comment for <code>OPENED-CLOSED</code>
     */
    public static final String OPENED_CLOSED = "open-closed";

    /**
     * Comment for <code>CLOSED-OPENED</code>
     */
    public static final String CLOSED_OPENED = "closed-open";

    /**
     * Comment for <code>value</code>
     */
    public String value = CLOSED;

    /**
     * default = CLOSED
     */
    public Closure() {
    }

    /**
     * @param value
     */
    public Closure( String value ) {
        this.value = value;
    }

    /**
     * Compares the specified object with this enum for equality.
     */
    public boolean equals( Object object ) {
        if ( object != null && getClass().equals( object.getClass() ) ) {
            return ( (Closure) object ).value.equals( value );
        }
        return false;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        final long longCode = value.hashCode();
        return ( ( (int) ( longCode >>> 32 ) ) ^ (int) longCode ) + 37 * super.hashCode();
    }

}
