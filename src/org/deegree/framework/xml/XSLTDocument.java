//$HeadURL: svn+ssh://jwilden@scm.wald.intevation.org/deegree/base/trunk/src/org/deegree/framework/xml/XSLTDocument.java $
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
 Aennchenstraße 19
 53177 Bonn
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
package org.deegree.framework.xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.deegree.framework.log.ILogger;
import org.deegree.framework.log.LoggerFactory;
import org.deegree.framework.util.BootLogger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Encapsulates the DOM representation of an XSLT stylesheet.
 * 
 * @author <a href="mailto:schneider@lat-lon.de">Markus Schneider </a>
 * 
 * @author last edited by: $Author: apoth $
 * 
 * @version $Revision: 10660 $, $Date: 2008-03-24 22:39:54 +0100 (Mon, 24 Mar 2008) $
 * 
 */
public class XSLTDocument extends XMLFragment {

    private static final long serialVersionUID = -2079718341146916400L;

    private static final ILogger LOG = LoggerFactory.getLogger( XSLTDocument.class );

    static {
        LOG.logDebug( "XSLT implementation in use (TransformerFactory): "
                      + TransformerFactory.newInstance().getClass().getName() );
        try {
            LOG.logDebug( "XSLT implementation in use (Transformer): "
                          + TransformerFactory.newInstance().newTransformer().getClass().getName() );
        } catch ( Exception e ) {
            BootLogger.logError( "Error creating test Transformer instance.", e );
        }
    }

    /**
     * An empty xslt document.
     */
    public XSLTDocument() {
        super();
    }

    /**
     * 
     * @param url
     * @throws IOException
     * @throws SAXException
     */
    public XSLTDocument( URL url ) throws IOException, SAXException {
        super( url );
    }

    /**
     * Transforms the given <code>XMLFragment</code> instance.
     * 
     * @param xmlDocument
     *            can not be null
     * @param systemId
     *            SystemID for the resulting <code>XMLFragment</code>, may be null
     * @param outputProperties
     *            transformation properties, may be null
     * @param params
     *            transformation parameters, may be null
     * @return the transformed xml file.
     * @throws TransformerException
     * @throws MalformedURLException
     *             if systemId is no valid <code>URL</code>
     */
    public XMLFragment transform( XMLFragment xmlDocument, String systemId, Properties outputProperties,
                                  Map<String, ?> params )
                            throws TransformerException, MalformedURLException {

        XMLFragment resultFragment = null;
        DOMSource xmlSource = new DOMSource( xmlDocument.getRootElement() );
        DOMSource xslSource = new DOMSource( this.getRootElement().getOwnerDocument(),
                                             this.getSystemId() == null ? null : this.getSystemId().toString() );
        Result result = transform( xmlSource, xslSource, new DOMResult(), outputProperties, params );
        Document resultDocument = (Document) ( (DOMResult) result ).getNode();
        resultFragment = new XMLFragment( resultDocument, systemId );

        return resultFragment;
    }

    /**
     * Transforms the given <code>XMLFragment</code> instance.
     * 
     * @param xmlDocument
     *            can not be null
     * @return the transformed xml file.
     * @throws TransformerException
     */
    public XMLFragment transform( XMLFragment xmlDocument )
                            throws TransformerException {

        XMLFragment resultFragment;
        try {
            resultFragment = transform( xmlDocument, null, null, null );
        } catch ( MalformedURLException e ) {
            LOG.logError( "Internal Error. This should not happen." );
            throw new TransformerException( "Internal Error. This should not happen.", e );
        }

        return resultFragment;
    }

    /**
     * Transforms the given <code>XMLFragment</code> instance.
     * 
     * @param xmlDocument
     *            can not be null
     * @param target
     *            output stream where the result of the transformation will be written to
     * @throws TransformerException
     */
    public void transform( XMLFragment xmlDocument, OutputStream target )
                            throws TransformerException {

        DOMSource xmlSource = new DOMSource( xmlDocument.getRootElement() );
        DOMSource xslSource = new DOMSource( this.getRootElement().getOwnerDocument(),
                                             this.getSystemId() == null ? null : this.getSystemId().toString() );
        StreamResult sr = new StreamResult( target );
        transform( xmlSource, xslSource, sr, null, null );

    }

    /**
     * Transforms the XML from the given <code>InputStream</code>.
     * <p>
     * NOTE: You have to make sure that the <code>InputStream</code> provides a valid XML
     * document.
     * 
     * @param instream
     * @param systemId
     *            SystemID for the resulting <code>XMLFragment</code>
     * @param outputProperties
     *            transformation properties, may be null
     * @param params
     *            transformation parameters, may be null
     * @return the transformed xml file.
     * @throws TransformerException
     *             if transformation fails
     * @throws MalformedURLException
     *             if given systemId is no valid <code>URL</code>
     */
    public XMLFragment transform( InputStream instream, String systemId, Properties outputProperties,
                                  Map<String, ?> params )
                            throws TransformerException, MalformedURLException {

        DOMSource xslSource = new DOMSource( getRootElement().getOwnerDocument(),
                                             this.getSystemId() == null ? null : this.getSystemId().toString() );
        Result result = transform( new StreamSource( instream ), xslSource, new DOMResult(), outputProperties, params );
        Document resultDocument = (Document) ( (DOMResult) result ).getNode();

        return new XMLFragment( resultDocument, systemId );
    }

    /**
     * Transforms the XML from the given <code>Reader</code>.
     * <p>
     * NOTE: You have to make sure that the <code>Reader</code> provides a valid XML document.
     * 
     * @param reader
     * @param systemId
     *            SystemID for the resulting <code>XMLFragment</code>
     * @param outputProperties
     *            transformation properties, may be null
     * @param params
     *            transformation parameters, may be null
     * @return the transformed xml file.
     * @throws TransformerException
     *             if transformation fails
     * @throws MalformedURLException
     *             if given systemId is no valid <code>URL</code>
     */
    public XMLFragment transform( Reader reader, String systemId, Properties outputProperties, Map<String, ?> params )
                            throws TransformerException, MalformedURLException {

        DOMSource xslSource = new DOMSource( getRootElement().getOwnerDocument(),
                                             this.getSystemId() == null ? null : this.getSystemId().toString() );
        Result result = transform( new StreamSource( reader ), xslSource, new DOMResult(), outputProperties, params );
        Document resultDocument = (Document) ( (DOMResult) result ).getNode();

        return new XMLFragment( resultDocument, systemId );
    }

    /**
     * Transforms the given XML <code>Source</code> instance using the also submitted XSLT
     * stylesheet <code>Source</code>.
     * 
     * @param xmlSource
     * @param xslSource
     * @param result
     * @param outputProperties
     *            may be null
     * @param params
     *            may be null
     * @return the transformed xml file.
     * @throws TransformerException
     * @throws TransformerException
     */
    public static Result transform( Source xmlSource, Source xslSource, Result result, Properties outputProperties,
                                    Map<String, ?> params )
                            throws TransformerException {

        TransformerFactory factory = TransformerFactory.newInstance();
        try {
            Transformer transformer = factory.newTransformer( xslSource );

            if ( params != null ) {
                Iterator<String> it = params.keySet().iterator();
                while ( it.hasNext() ) {
                    String key = it.next();
                    transformer.setParameter( key, params.get( key ) );
                }
            }
            if ( outputProperties != null ) {
                transformer.setOutputProperties( outputProperties );
            }

            transformer.transform( xmlSource, result );
        } catch ( TransformerException e ) {
            String transformerClassName = null;
            String transformerFactoryClassName = TransformerFactory.newInstance().getClass().getName();
            try {
                transformerClassName = TransformerFactory.newInstance().newTransformer().getClass().getName();
            } catch ( Exception e2 ) {
                LOG.logError( "Error creating Transformer instance." );
            }
            String errorMsg = "XSL transformation using stylesheet with systemId '" + xslSource.getSystemId()
                              + "' and xml source with systemId '" + xmlSource.getSystemId()
                              + "' failed. TransformerFactory class: " + transformerFactoryClassName
                              + "', Transformer class: " + transformerClassName;
            LOG.logError( errorMsg, e );
            throw new TransformerException( errorMsg, e );
        }

        return result;
    }
}
