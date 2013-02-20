package de.latlon.adv;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidParameterException;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import org.deegree.enterprise.servlet.ServletRequestWrapper;
import org.deegree.enterprise.servlet.ServletResponseWrapper;
import org.deegree.framework.log.ILogger;
import org.deegree.framework.log.LoggerFactory;
import org.deegree.framework.xml.XMLParsingException;
import org.deegree.ogcwebservices.InvalidParameterValueException;
import org.deegree.ogcwebservices.OGCRequestFactory;
import org.deegree.ogcwebservices.OGCWebServiceException;
import org.deegree.ogcwebservices.OGCWebServiceRequest;
import org.deegree.security.GeneralSecurityException;
import org.deegree.security.SecurityConfigurationException;
import org.deegree.security.UnauthorizedException;
import org.deegree.security.drm.SecurityAccessManager;
import org.deegree.security.drm.model.User;
import org.deegree.security.owsproxy.OWSProxyPolicyFilter;
import org.deegree.security.owsrequestvalidator.PolicyDocument;
import org.deegree.security.owsrequestvalidator.csw.CSWValidator;

/**
 * 
 * 
 * 
 * @author <a href="mailto:poth@lat-lon.de">Andreas Poth</a>
 * @author last edited by: $Author: bezema $
 * 
 * @version. $Revision: 1.3 $, $Date: 2007-06-21 13:54:48 $
 */
public class OWSProxyHandler {

    private static final ILogger LOG = LoggerFactory.getLogger( OWSProxyHandler.class );

    private OWSProxyPolicyFilter pFilter;

    /**
     * initialize the filter with parameters from the deployment descriptor
     * 
     * @param config
     * @throws Exception
     */
    public OWSProxyHandler( FilterConfig config ) {

        pFilter = new OWSProxyPolicyFilter();
        String proxyURL = "http://127.0.0.1/owsproxy/proxy";
        if ( config.getInitParameter( "PROXYURL" ) != null ) {
            proxyURL = config.getInitParameter( "PROXYURL" );
        }
        LOG.logDebug( " found 'PROXYURL' param: " + proxyURL );
        String paramValue = config.getInitParameter( "CSW:POLICY" );
        LOG.logDebug( " found 'CSW:POLICY' param: " + paramValue );
        paramValue = config.getServletContext().getRealPath( paramValue );
        LOG.logDebug( " 'CSW:POLICY' param converted to realPath: " + paramValue );

        try {
            URL fileURL = new File( paramValue ).toURI().toURL();
            PolicyDocument doc = new PolicyDocument( fileURL );
            CSWValidator validator = new CSWValidator( doc.getPolicy(), proxyURL );
            pFilter.addValidator( "CSW", validator );
            pFilter.addValidator( "urn:x-ogc:specification:cswebrim:Service:OGC-CSW:ebRIM", validator );
            LOG.logDebug( " added the CSW validator from: " + paramValue + " to the OWSProxyPolicyFilter." );
        } catch ( MalformedURLException e ) {
            LOG.logDebug( " couldn't create a fileURL from: " + paramValue + " because: " + e.getMessage() );
            throw new InvalidParameterException( "Couldn't create an OWSProxyhandler because: " + e.getMessage() );
        } catch ( SecurityConfigurationException e ) {
            LOG.logDebug( " couldn't create a PolicyDocument from: " + paramValue + " because: " + e.getMessage() );
            throw new InvalidParameterException( "Couldn't create an OWSProxyhandler because: " + e.getMessage() );
        } catch ( XMLParsingException e ) {
            LOG.logDebug( " couldn't get an Policy fromt the PolicyDocument from location: " + paramValue + " because: " + e.getMessage() );
            throw new InvalidParameterException( "Couldn't create an OWSProxyhandler because: " + e.getMessage() );
        }
    }

    /**
     * 
     * @param request
     * @return a request created fromt the http servlet request (e.g. calling the {@link OGCRequestFactory#create(javax.servlet.ServletRequest)}.
     * @throws OGCWebServiceException 
     * @throws ServletException
     */
    public OGCWebServiceRequest createOWSRequest( ServletRequestWrapper request ) throws OGCWebServiceException {
        OGCWebServiceRequest owsReq = null;
        try {
            owsReq = OGCRequestFactory.create( request );
        } catch ( OGCWebServiceException e ) {
            LOG.logDebug( " Couln't create an OGCWebserviceRequest because: " + e.getMessage(), e );
            throw e;
        }
        return owsReq;
    }

    /**
     * Validates if a given user may send the given request
     * @param request
     * @param user 
     * @param owsRequest created of the stream.
     * @throws UnauthorizedException if the user is not authorized to do the given request.
     * @throws InvalidParameterValueException 
     */
    public void doRequestValidation( ServletRequestWrapper request, User user, OGCWebServiceRequest owsRequest ) throws UnauthorizedException, InvalidParameterValueException{
        LOG.logDebug( " validating credentials for user: " + user.toString() );
        pFilter.validateGeneralConditions( request, request.getContentLength(), user );
        pFilter.validate( owsRequest, user );

    }

    /**
     * 
     * @param response
     * @param user
     * @param owsRequest
     * @throws IOException 
     * @throws UnauthorizedException 
     * @throws InvalidParameterValueException 
     * @throws Exception
     */
    public void doResponseValidation( ServletResponseWrapper response, User user, OGCWebServiceRequest owsRequest ) throws IOException, InvalidParameterValueException, UnauthorizedException
                             {
        // forward request to the next filter or servlet
        // get result from performing the request
        OutputStream os = response.getOutputStream();
        byte[] b = ( (ServletResponseWrapper.ProxyServletOutputStream) os ).toByteArray();
        if ( LOG.getLevel() == ILogger.LOG_DEBUG ) {
            LOG.logDebug( " response bytes as a string: \n" + new String( b ) );
        }

        // validate the result of a request performing
        String mime = response.getContentType();
        LOG.logDebug( " resonse mime type: " + mime );
        pFilter.validate( owsRequest, b, mime, user );

    }

    /**
     * 
     * @param user
     * @param password
     * @return a User identified by the user and password.
     * @throws GeneralSecurityException 
     */
    public User authentificateFromUserPw( String user, String password )
                            throws GeneralSecurityException {
        User usr = null;
        SecurityAccessManager sam;
//        try {
            sam = SecurityAccessManager.getInstance();
            usr = sam.getUserByName( user );
            usr.authenticate( password );
//        } catch ( GeneralSecurityException e ) {
//            // TODO Auto-generated catch block
//            if ( !( user.equals( "anonymous" ) ) ) {
//                throw new UnauthorizedException( "OWSProxyServletFilter.USERERROR" );
//            }
//            
//        }

//        } catch ( Exception e ) {
//            LOG.logError( e.getMessage(), e );
//        }

        return usr;
    }

//    public static void main( String[] args )
//                            throws Exception {
//        // just for demonstration how to use
//        FilterConfig config = null;
//        OWSProxyHandler fil = new OWSProxyHandler( config );
//
//        ServletRequestWrapper request = null;
//        OGCWebServiceRequest owsReq = fil.createOWSRequest( request );
//        User user = fil.authentificateFromUserPw( "poth", "myPassword" );
//        fil.doRequestValidation( request, user, owsReq );
//
//        /*
//         * here the magic of the program must be added ...
//         */
//
//        // kann sein, dass wir die response validierung nicht brauchen
//        // daher erst mal ohne versuchen ...
//        /*
//         * ServletResponseWrapper response = null; fil.doResponseValidation( response, user, owsReq );
//         */
//
//    }

}
