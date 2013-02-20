//$HeadURL: svn+ssh://jwilden@scm.wald.intevation.org/deegree/base/trunk/test/junit/org/deegree/enterprise/servlet/OGCServletControllerTest.java $
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
package org.deegree.enterprise.servlet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.deegree.framework.log.ILogger;
import org.deegree.framework.log.LoggerFactory;
import org.deegree.framework.xml.XMLTools;
import org.deegree.ogcwebservices.csw.capabilities.CatalogueCapabilities;
import org.deegree.ogcwebservices.wfs.capabilities.WFSCapabilities;
import org.deegree.ogcwebservices.wfs.capabilities.WFSCapabilitiesDocument;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import alltests.AllTests;
import alltests.Configuration;

/**
 * OGCServletControllerTest requires a running Servlet Container. The server name and port is specified in the
 * <code>Configuration</code>.
 * 
 * @author <a href="mailto:tfr@users.sourceforge.net">Torsten Friebe </a>
 * 
 * @author last edited by: $Author: rbezema $
 * 
 * @version 2.0, $Revision: 10877 $, $Date: 2008-04-01 17:05:11 +0200 (Tue, 01 Apr 2008) $
 * 
 * @since 2.0
 * 
 * @see alltests.Configuration#PROTOCOL
 * @see alltests.Configuration#HOST
 * @see alltests.Configuration#PORT
 */
public class OGCServletControllerTest extends TestCase {
    private static ILogger LOG = LoggerFactory.getLogger( OGCServletControllerTest.class );

    private final static String cswCapabilitiesRequest = "?REQUEST=GetCapabilities&service=CSW&version=2.0.0&acceptversion=2.0.0&outputFormat=text/xml";

    private final static String wfsCapabilitiesRequest = "?REQUEST=GetCapabilities&service=WFS&version=1.0.0";

    public static Test suite() {
        return new TestSuite( OGCServletControllerTest.class );
    }

    /**
     * Constructor for OGCServletControllerTest.
     * 
     * @param arg0
     */
    public OGCServletControllerTest( String arg0 ) {
        super( arg0 );
    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp()
                            throws Exception {
        super.setUp();
    }

    /*
     * @see TestCase#tearDown()
     */
    protected void tearDown()
                            throws Exception {
        super.tearDown();
    }

    /**
     * Test for void doGet(HttpServletRequest, HttpServletResponse)
     */
    public void testDoGetHttpServletRequestHttpServletResponse() {
        fail( "Not implemented yet!" );// TODO Implement doGet().
    }

    /**
     * Test for void doPost(HttpServletRequest, HttpServletResponse)
     */
    public void testDoPostHttpServletRequestHttpServletResponse() {
        fail( "Not implemented yet!" );// TODO Implement doPost().
    }

    /**
     * Send WFS getCapabilities request to service.
     * 
     */
    public void testWFSGetCapabilities() {
        fail( "Not testing Get-Method because of localhost -- fixme" );
//        try {
//            URL wfsUrl = new URL( Configuration.getWFSURL(), Configuration.WFS_SERVLET + wfsCapabilitiesRequest );
//            LOG.logInfo( "GET: " + wfsUrl.toString() );
//            WFSCapabilitiesDocument capaDoc = new WFSCapabilitiesDocument();
//            capaDoc.load( wfsUrl );
//            WFSCapabilities wfsCapabilities = (WFSCapabilities) capaDoc.parseCapabilities();
//            assertNotNull( wfsCapabilities );
//            assertNotNull( wfsCapabilities.getVersion() );
//            assertEquals( "Capabilities Version is wrong", "1.0.0", wfsCapabilities.getVersion() );
//            assertNotNull( "Service name is null", wfsCapabilities.getServiceIdentification().getTitle() );
//
//            StringWriter sw = new StringWriter();
//            capaDoc.prettyPrint( sw );
//            assertTrue( sw.getBuffer().length() > 0 );
//
//            LOG.logDebug( "XML : " + sw.getBuffer() );
//
//            Document capabilitiesDoc = XMLTools.parse( new StringReader( sw.getBuffer().toString() ) );
//
//            assertNotNull( "Document is null", capabilitiesDoc );
//
//            assertEquals( "Root element is wrong", "WFS_Capabilities",
//                          capabilitiesDoc.getDocumentElement().getNodeName() );
//
//            assertEquals( "Root element is wrong", "1.0.0",
//                          capabilitiesDoc.getDocumentElement().getAttribute( "version" ) );
//
//        } catch ( Exception e ) {
//            LOG.logError( e.getMessage(), e );
//            fail( e.getMessage() );
//        }
    }

    /**
     * Send CSW getCapabilities request to server.
     * 
     */
    public void testCSWGetCapabilities() {
        fail( "Not testing csw Get-Capabilities because of localhost -- fixme" );
//        try {
//            URL cswUrl = new URL( Configuration.getCSWURL(), Configuration.CSW_SERVLET + cswCapabilitiesRequest );
//            LOG.logInfo( "GET: " + cswUrl.toString() );
//            CatalogueCapabilities cswCapabilities = (CatalogueCapabilities) CatalogueCapabilities.createCapabilities( cswUrl );
//            assertNotNull( cswCapabilities );
//            LOG.logDebug( cswCapabilities.toString() );
//            assertNotNull( cswCapabilities.getVersion() );
//            assertEquals( "2.0.0", cswCapabilities.getVersion() );
//            assertNotNull( cswCapabilities.getServiceIdentification().getServiceType().getCode() );
//            assertEquals( "CSW", cswCapabilities.getServiceIdentification().getServiceType().getCode() );
//        } catch ( Exception e ) {
//            //LOG.logError( e.getMessage(), e );
//            fail( e.getMessage() );
//        }
    }

    /**
     * Reads operations from xml files and perform request on WFS servlet. All request files are located int the
     * resource directory.
     * 
     * @throws MalformedURLException
     * 
     */
    public void testRequestList()
                            throws MalformedURLException {
        URL requestsDir = new URL( Configuration.getWFSBaseDir(), Configuration.REQUESTS_DIR + "/" );
        URL generatedDir = new URL( Configuration.getWFSBaseDir(), Configuration.GENERATED_DIR + "/" );

        File filterbase = new File( requestsDir.getFile() );
        FileFilter xmlFilter = new XMLRequestFilter();
        File[] filelist = filterbase.listFiles( xmlFilter );
        if ( filelist != null ) {
            for ( int i = 0; i < filelist.length; i++ ) {
                LOG.logInfo( "Test No.:" + i + ": " + filelist[i] );
                try {
                    // get connection
                    URL url = new URL( Configuration.getWFSURL(), Configuration.WFS_SERVLET );
                    URLConnection connection = this.openConnection( url );

                    // sends request
                    this.sendRequest( connection, this.read( new FileReader( filelist[i] ) ) );

                    // reads response
                    String responsecontent = this.read( new InputStreamReader( connection.getInputStream() ) );
                    LOG.logInfo( "Receiving response:" + responsecontent );
                    assertNotNull( "Response is null", responsecontent );
                    assertTrue( "Response is empty", responsecontent.length() > 0 );

                    // write response to file
                    this.write( new FileWriter( new File( new URL( generatedDir, filelist[i].getName() + "_response_"
                                                                                 + i ).getFile() ) ), responsecontent );

                    // compare responses
                    Document responseWfs = XMLTools.parse( new StringReader( responsecontent.toString() ) );
                    assertNotNull( "Root node is null", responseWfs.getDocumentElement() );
                    LOG.logDebug( responseWfs.getDocumentElement().toString() );
                    // test for exceptions
                    NodeList exceptionNodes = responseWfs.getElementsByTagName( "Exception" );
                    for ( int j = 0; j < exceptionNodes.getLength(); j++ ) {
                        Node exceptionNode = exceptionNodes.item( j );
                        LOG.logInfo( exceptionNode.toString() );
                        LOG.logInfo( exceptionNode.getFirstChild().getNodeValue() );
                        fail( "Exception is thrown by service" + exceptionNode.toString() );
                    }
                    // TODO: do more asserts
                    // 

                } catch ( FileNotFoundException e ) {
                    LOG.logError( e.getMessage(), e );
                    fail( e.getMessage() );
                } catch ( IOException e ) {
                    LOG.logError( e.getMessage(), e );
                    fail( e.getMessage() );
                } catch ( SAXException e ) {
                    LOG.logError( e.getMessage(), e );
                    fail( e.getMessage() );
                }
            }
        }
    }

    private void sendRequest( final URLConnection connection, String request )
                            throws IOException {
        this.write( new BufferedWriter( new OutputStreamWriter( connection.getOutputStream() ) ), request );
    }

    private URLConnection openConnection( URL url )
                            throws IOException {
        URLConnection connection = url.openConnection();
        LOG.logInfo( "Connection opened to " + url.toString() );
        connection.setDoOutput( true );
        connection.setDoInput( true );
        connection.connect();
        return connection;

    }

    private String read( Reader reader )
                            throws IOException {
        StringBuffer buffer = new StringBuffer();
        BufferedReader bufferedreader = new BufferedReader( reader );
        String line;
        while ( ( line = bufferedreader.readLine() ) != null ) {
            buffer.append( line );
        }
        reader.close();
        return buffer.toString();
    }

    private void write( Writer writer, String content )
                            throws IOException {
        writer.write( content );
        writer.flush();
        writer.close();
    }

    /**
     * 
     * XMLRequestFilter
     * 
     * @author last edited by: $Author: rbezema $
     * 
     * @version 2.0, $Revision: 10877 $, $Date: 2008-04-01 17:05:11 +0200 (Tue, 01 Apr 2008) $
     * 
     * @since 2.0
     */
    class XMLRequestFilter extends javax.swing.filechooser.FileFilter implements FileFilter {

        public boolean accept( File f ) {
            return this.accept( f, f.getName() );
        }

        /**
         * This is the one of the methods that is declared in the abstract class
         */
        public boolean accept( File f, String name ) {
            // if it is a directory -- we want to hide it so return false.
            if ( f.isDirectory() )
                return false;

            // get the extension of the file

            String extension = getExtension( f );
            // check to see if the extension is equal to "xml"
            if ( extension.equalsIgnoreCase( "xml" ) && ( f.getName().indexOf( "request" ) > 0 ) )
                return true;

            // default -- fall through. False is return on all
            // occasions except:
            // a) the file is a directory
            // b) the file's extension is what we are looking for.
            return false;
        }

        /**
         * Again, this is declared in the abstract class
         * 
         * The description of this filter
         */
        public String getDescription() {
            return "XML files";
        }

        /**
         * Method to get the extension of the file, in lowercase
         */
        private String getExtension( File f ) {
            String s = f.getName();
            int i = s.lastIndexOf( '.' );
            if ( i > 0 && i < s.length() - 1 )
                return s.substring( i + 1 ).toLowerCase();
            return "";
        }
    }

}

/***********************************************************************************************************************
 * <code>
 Changes to this class. What the people have been up to:

 $Log$
 Revision 1.22  2007/02/12 09:31:29  wanhoff
 fixed footer

 Revision 1.21  2007/02/12 09:28:05  wanhoff
 added footer, corrected header

 </code>
 **********************************************************************************************************************/
