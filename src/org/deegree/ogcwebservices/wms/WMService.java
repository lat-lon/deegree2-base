// $HeadURL: /cvsroot/deegree/src/org/deegree/ogcwebservices/wms/WMService.java,v
// 1.3 2004/07/12 06:12:11 ap Exp $
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
package org.deegree.ogcwebservices.wms;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.deegree.enterprise.Proxy;
import org.deegree.framework.log.ILogger;
import org.deegree.framework.log.LoggerFactory;
import org.deegree.framework.trigger.TriggerProvider;
import org.deegree.ogcwebservices.CurrentUpdateSequenceException;
import org.deegree.ogcwebservices.InvalidUpdateSequenceException;
import org.deegree.ogcwebservices.OGCWebService;
import org.deegree.ogcwebservices.OGCWebServiceException;
import org.deegree.ogcwebservices.OGCWebServiceRequest;
import org.deegree.ogcwebservices.OGCWebServiceResponse;
import org.deegree.ogcwebservices.getcapabilities.OGCCapabilities;
import org.deegree.ogcwebservices.wms.configuration.WMSConfigurationType;
import org.deegree.ogcwebservices.wms.operation.DescribeLayer;
import org.deegree.ogcwebservices.wms.operation.GetFeatureInfo;
import org.deegree.ogcwebservices.wms.operation.GetLegendGraphic;
import org.deegree.ogcwebservices.wms.operation.GetLegendGraphicResult;
import org.deegree.ogcwebservices.wms.operation.GetMap;
import org.deegree.ogcwebservices.wms.operation.GetStyles;
import org.deegree.ogcwebservices.wms.operation.GetStylesResult;
import org.deegree.ogcwebservices.wms.operation.PutStyles;
import org.deegree.ogcwebservices.wms.operation.WMSGetCapabilities;
import org.deegree.ogcwebservices.wms.operation.WMSGetCapabilitiesResult;
import org.deegree.ogcwebservices.wms.operation.WMSProtocolFactory;

/**
 * @author <a href="mailto:poth@lat-lon.de">Andreas Poth</a>
 * @author last edited by: $Author: aschmitz $
 * 
 * @version 1.0. $Revision: 10168 $, $Date: 2008-02-19 17:02:37 +0100 (Tue, 19 Feb 2008) $
 * 
 * @since 1.1
 * 
 */

public class WMService implements OGCWebService {

    private static final ILogger LOG = LoggerFactory.getLogger( WMService.class );

    private static final TriggerProvider TP = TriggerProvider.create( WMService.class );

    private WMSConfigurationType configuration = null;

    /**
     * Creates a new WMService object.
     * 
     * @param configuration
     */
    public WMService( WMSConfigurationType configuration ) {
        this.configuration = configuration;
        Proxy proxy = configuration.getDeegreeParams().getProxy();
        if ( proxy != null ) {
            proxy.setProxy( true );
        }
    }

    /**
     * 
     */
    public OGCCapabilities getCapabilities() {
        // should a class implement the WMSConfigurationType and not be an instance
        // of OGCCapabilities, here's where it'll fail ;-)
        return (OGCCapabilities) configuration;
    }

    /**
     * the method performs the handling of the passed OGCWebServiceEvent directly and returns the
     * result to the calling class/method
     * 
     * @param request
     *            request to perform
     * 
     * @throws OGCWebServiceException
     */
    public Object doService( OGCWebServiceRequest request )
                            throws OGCWebServiceException {

        request = (OGCWebServiceRequest) TP.doPreTrigger( this, request )[0];

        LOG.logDebug( "Handling WMS request with ID ", request.getId() );

        Object result = null;
        if ( request instanceof GetMap ) {
            GetMapHandler gmh = (GetMapHandler) createHandler( request, GetMap.class,
                                                               HandlerMapping.getString( "WMService.GETMAP" ) );
            result = gmh.performGetMap();
        } else if ( request instanceof GetFeatureInfo ) {
            GetFeatureInfoHandler gmh = (GetFeatureInfoHandler) createHandler(
                                                                               request,
                                                                               GetFeatureInfo.class,
                                                                               HandlerMapping.getString( "WMService.GETFEATUREINFO" ) );
            result = gmh.performGetFeatureInfo();
        } else if ( request instanceof WMSGetCapabilities ) {
            result = handleGetCapabilities( (WMSGetCapabilities) request );
        } else if ( request instanceof GetStyles ) {
            handleGetStyles( (GetStyles) request );
        } else if ( request instanceof PutStyles ) {
            handlePutStyles( (PutStyles) request );
        } else if ( request instanceof DescribeLayer ) {
            result = new DescribeLayerHandler().perform( (DescribeLayer) request, configuration );
        } else if ( request instanceof GetLegendGraphic ) {
            result = handleGetLegendGraphic( (GetLegendGraphic) request );
        }

        LOG.logDebug( "Handled WMS request with ID ", request.getId() );

        return TP.doPostTrigger( this, result )[0];
    }

    /**
     * creates a handler class for performing the incomming request. The instance that will be
     * created depends on the responsible class the for the submitted request in the WMS
     * capabilities/configuration.
     * 
     * @param request
     *            request to be performed
     * @param requestClass
     *            of the request (GetStyles, WMSFeatureInfoRequest etc.)
     * @param operationType
     *            type of the operation to perform by the handler
     */
    private Object createHandler( OGCWebServiceRequest request, Class<?> requestClass, String className )
                            throws OGCWebServiceException {
        // describes the signature of the required constructor
        Class[] cl = new Class[2];
        cl[0] = WMSConfigurationType.class;
        cl[1] = requestClass;

        // set parameter to submitt to the constructor
        Object[] o = new Object[2];
        o[0] = configuration;
        o[1] = request;

        Object handler = null;

        try {
            // get constructor
            Class<?> creator = Class.forName( className );
            Constructor<?> con = creator.getConstructor( cl );

            // call constructor and instantiate a new DataStore
            handler = con.newInstance( o );
        } catch ( ClassNotFoundException cce ) {
            throw new OGCWebServiceException( "Couldn't instantiate " + className + "! \n" + cce.toString() );
        } catch ( NoSuchMethodException nsme ) {
            throw new OGCWebServiceException( "Couldn't instantiate " + className + "! \n" + nsme.toString() );
        } catch ( InstantiationException ie ) {
            throw new OGCWebServiceException( "Couldn't instantiate " + className + "! \n" + ie.toString() );
        } catch ( IllegalAccessException iae ) {
            throw new OGCWebServiceException( "Couldn't instantiate " + className + "! \n" + iae.toString() );
        } catch ( InvocationTargetException ite ) {
            // causes need not be OGCWebService exceptions
            Throwable cause = ite.getCause();
            if ( cause instanceof OGCWebServiceException ) {
                throw (OGCWebServiceException) cause;
            }
            LOG.logError( "Unknown error", cause );
            throw new OGCWebServiceException( getClass().getName(), cause.getMessage() );
        } catch ( Exception e ) {
            throw new OGCWebServiceException( "Couldn't instantiate " + className + "! \n" + e.toString() );
        }

        return handler;
    }

    /**
     * reads/creates the capabilities of the WMS.
     * 
     * @param request
     *            capabilities request
     */
    private WMSGetCapabilitiesResult handleGetCapabilities( WMSGetCapabilities request )
                            throws OGCWebServiceException {

        String rUp = request.getUpdateSequence();
        String cUp = configuration.getUpdateSequence();

        if ( ( rUp != null ) && ( cUp != null ) && ( rUp.compareTo( cUp ) == 0 ) ) {
            throw new CurrentUpdateSequenceException( "request update sequence (" + rUp + ") is equal to capabilities"
                                                      + " update sequence " + cUp );
        }

        if ( ( rUp != null ) && ( cUp != null ) && ( rUp.compareTo( cUp ) > 0 ) ) {
            throw new InvalidUpdateSequenceException( "request update sequence: " + rUp + " is higher than the "
                                                      + "capabilities update sequence " + cUp );
        }

        WMSGetCapabilitiesResult res = null;
        res = WMSProtocolFactory.createGetCapabilitiesResponse( request, null, configuration );

        return res;
    }

    /**
     * @param request
     *            get styles request (WMS 1.1.1 - SLD)
     */
    private GetStylesResult handleGetStyles( GetStyles request ) {
        throw new UnsupportedOperationException( "handleGetStyles(" + request.getClass().getSimpleName()
                                                 + ") not implemented" );
    }

    /**
     * @param request
     *            put styles request (WMS 1.1.1 - SLD)
     */
    private void handlePutStyles( PutStyles request ) {
        throw new UnsupportedOperationException( "handlePutStyles(" + request.getClass().getSimpleName()
                                                 + ") not implemented" );
    }

    /**
     * @param request
     * 
     * @return the response
     * @throws WebServiceException
     */
    private OGCWebServiceResponse handleGetLegendGraphic( GetLegendGraphic request )
                            throws OGCWebServiceException {
        OGCWebServiceResponse result;

        Object o = createHandler( request, GetLegendGraphic.class,
                                  HandlerMapping.getString( "WMService.GETLEGENDGRAPHIC" ) );
        GetLegendGraphicHandler glgh = (GetLegendGraphicHandler) o;
        result = glgh.performGetLegendGraphic();
        if ( ( (GetLegendGraphicResult) result ).getLegendGraphic() != null ) {
            // cache.push(request, ((GetLegendGraphicResult) result).getLegendGraphic());
        }

        return result;
    }

}
