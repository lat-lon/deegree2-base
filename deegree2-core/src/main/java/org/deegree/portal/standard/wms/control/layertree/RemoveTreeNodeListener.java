//$HeadURL$
/*----------------------------------------------------------------------------
 This file is part of deegree, http://deegree.org/
 Copyright (C) 2001-2009 by:
 - Department of Geography, University of Bonn -
 and
 - lat/lon GmbH -

 This library is free software; you can redistribute it and/or modify it under
 the terms of the GNU Lesser General Public License as published by the Free
 Software Foundation; either version 2.1 of the License, or (at your option)
 any later version.
 This library is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 details.
 You should have received a copy of the GNU Lesser General Public License
 along with this library; if not, write to the Free Software Foundation, Inc.,
 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

 Contact information:

 lat/lon GmbH
 Aennchenstr. 19, 53177 Bonn
 Germany
 http://lat-lon.de/

 Department of Geography, University of Bonn
 Prof. Dr. Klaus Greve
 Postfach 1147, 53001 Bonn
 Germany
 http://www.geographie.uni-bonn.de/deegree/

 e-mail: info@deegree.org
 ----------------------------------------------------------------------------*/
package org.deegree.portal.standard.wms.control.layertree;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.deegree.enterprise.control.ajax.AbstractListener;
import org.deegree.enterprise.control.ajax.ResponseHandler;
import org.deegree.enterprise.control.ajax.WebEvent;
import org.deegree.framework.log.ILogger;
import org.deegree.framework.log.LoggerFactory;
import org.deegree.portal.Constants;
import org.deegree.portal.context.Layer;
import org.deegree.portal.context.LayerGroup;
import org.deegree.portal.context.MMLayer;
import org.deegree.portal.context.MapModel;
import org.deegree.portal.context.MapModelEntry;
import org.deegree.portal.context.ViewContext;

/**
 * TODO add class documentation here
 * 
 * @author <a href="mailto:name@deegree.org">Andreas Poth</a>
 * @author last edited by: $Author$
 * 
 * @version $Revision$, $Date$
 */
public class RemoveTreeNodeListener extends AbstractListener {

    private static final ILogger LOG = LoggerFactory.getLogger( RemoveTreeNodeListener.class );

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.deegree.enterprise.control.ajax.AbstractListener#actionPerformed(org.deegree.enterprise.control.ajax.WebEvent
     * , org.deegree.enterprise.control.ajax.ResponseHandler)
     */
    @SuppressWarnings("unchecked")
    public void actionPerformed( WebEvent event, ResponseHandler responseHandler )
                            throws IOException {
        Map<Object, Object> parameter = event.getParameter();
        String node = (String) parameter.get( "node" );
        ViewContext vc = (ViewContext) event.getSession().getAttribute( Constants.CURRENTMAPCONTEXT );
        MapModel mapModel = vc.getGeneral().getExtension().getMapModel();
        MapModelEntry mme = mapModel.getMapModelEntryByIdentifier( node );
        mapModel.remove( mme );
        try {
            if ( mme instanceof MMLayer ) {
                Layer layer = ( (MMLayer) mme ).getLayer();

                vc.getLayerList().removeLayer( layer.getName(),
                                               layer.getServer().getOnlineResource().toURI().toASCIIString() );

                
                responseHandler.writeAndClose( false, new String[] {( (MMLayer) mme ).getLayer().getName()} );
            } else {
                LayerGroup layerGroup = (LayerGroup) mme;
                List<Layer> layers = new ArrayList<Layer>();
                collectLayers( layers, layerGroup );
                String[] s = new String[layers.size()];
                int i = 0;
                for ( Layer layer : layers ) {
                    s[i++] = layer.getName();
                    vc.getLayerList().removeLayer( layer.getName(),
                                                   layer.getServer().getOnlineResource().toURI().toASCIIString() );
                }

                responseHandler.writeAndClose( false, s );
            }
        } catch ( URISyntaxException e ) {
            LOG.logError( e );
            responseHandler.writeAndClose( "ERROR: " + e.getMessage() );
        }

    }

    private void collectLayers( List<Layer> layers, LayerGroup layerGroup ) {
        List<MMLayer> list;
        list = layerGroup.getLayers();
        for ( MMLayer mmLayer : list ) {
            layers.add( mmLayer.getLayer() );
        }
        List<LayerGroup> lgList = layerGroup.getLayerGroups();
        for ( LayerGroup lg : lgList ) {
            collectLayers( layers, lg );
        }
    }

}
