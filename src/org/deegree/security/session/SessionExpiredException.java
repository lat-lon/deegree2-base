//$HeadURL: svn+ssh://jwilden@scm.wald.intevation.org/deegree/base/trunk/src/org/deegree/security/session/SessionExpiredException.java $
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
package org.deegree.security.session;

/**
 * This exception shall be thrown when a session(ID) will be used that has been expired.
 * 
 * @author <a href="mailto:poth@lat-lon.de">Andreas Poth </a>
 * @author last edited by: $Author: apoth $
 * 
 * @version 1.1, $Revision: 10660 $, $Date: 2008-03-24 22:39:54 +0100 (Mon, 24 Mar 2008) $
 * 
 * @since 1.1
 */
public class SessionExpiredException extends SessionStatusException {

    /**
     * 
     */
    private static final long serialVersionUID = -2293203764806399123L;

    /**
     * 
     */
    public SessionExpiredException() {
        super();
    }

    /**
     * @param arg0
     */
    public SessionExpiredException( String arg0 ) {
        super( arg0 );
    }

    /**
     * @param arg0
     * @param arg1
     */
    public SessionExpiredException( String arg0, Throwable arg1 ) {
        super( arg0, arg1 );
    }

    /**
     * @param arg0
     */
    public SessionExpiredException( Throwable arg0 ) {
        super( arg0 );
    }
}