//$HeadURL: svn+ssh://jwilden@scm.wald.intevation.org/deegree/base/trunk/src/org/deegree/portal/portlet/modules/map/actions/portlets/LayerListViewPortletPerform.java $
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
package org.deegree.portal.portlet.modules.map.actions.portlets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.jetspeed.portal.Portlet;
import org.apache.jetspeed.portal.PortletConfig;
import org.deegree.framework.util.StringTools;
import org.deegree.portal.portlet.modules.actions.IGeoPortalPortletPerform;

/**
 * class more or less independ from a concrete porztal implementation that handles action assigned
 * to LayerListView portlet
 * 
 * @version $Revision: 9346 $
 * @author <a href="mailto:poth@lat-lon.de">Andreas Poth</a>
 * @author last edited by: $Author: apoth $
 * 
 * @version 1.0. $Revision: 9346 $, $Date: 2007-12-27 17:39:07 +0100 (Thu, 27 Dec 2007) $
 * 
 * @since 2.0
 */
public class LayerListViewPortletPerform extends IGeoPortalPortletPerform {

    private static String INIT_SELECTABLELIST = "SelectableList";

    private static String INIT_FORCELINEBREAK = "forceLineBreak";

    private Map<String, String> selectableList = null;

    /**
     * @param request
     * @param portlet
     */
    public LayerListViewPortletPerform( HttpServletRequest request, Portlet portlet, ServletContext sc ) {
        super( request, portlet, sc );
        String tmp = getInitParam( INIT_SELECTABLELIST );
        String[] tmps = StringTools.toArray( tmp, ",", false );
        selectableList = new HashMap<String, String>( tmps.length );
        for ( int i = 0; i < tmps.length; i++ ) {
            String[] s = StringTools.toArray( tmps[i], "|", false );
            selectableList.put( s[0], s[1] );
        }
    }

    /**
     * writes all parameters required by the assigned JSP page to the forwarded http request.
     * 
     */
    void putParametersToRequest() {

        PortletConfig pc = portlet.getPortletConfig();
        request.setAttribute( "PORTLETID", portlet.getID() );
        request.setAttribute( "SELECTABLELIST", selectableList );
        String tmp = pc.getInitParameter( INIT_FORCELINEBREAK );
        List<String> list = new ArrayList<String>();
        if ( tmp != null ) {
            for ( int i = 0; i < tmp.length(); i++ ) {
                list.add( "" + tmp.charAt( i ) );
            }
        }
        request.setAttribute( "LINEBREAK", list );

    }

}
