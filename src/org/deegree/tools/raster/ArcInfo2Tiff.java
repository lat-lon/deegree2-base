//$HeadURL: svn+ssh://jwilden@scm.wald.intevation.org/deegree/base/trunk/src/org/deegree/tools/raster/ArcInfo2Tiff.java $
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
package org.deegree.tools.raster;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Properties;

import org.deegree.framework.util.ImageUtils;
import org.deegree.io.arcinfo_raster.ArcInfoTextRasterReader;
import org.deegree.model.coverage.grid.WorldFile;
import org.deegree.processing.raster.converter.RawData2Image;

/**
 * converts an ArcInfor raster file (text format):
 * 
 * <pre>
 *  ncols         2404
 *  nrows         2307
 *  xllcorner     2627130
 *  yllcorner     5686612
 *  cellsize      10
 *  NODATA_value  -9999
 *  20636 20593 20569 20573 20571 20564 20564 20558 ...
 *  ...
 * </pre>
 * 
 * into a Tiff image that may have 16 or 32 bit pixel depth
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
public class ArcInfo2Tiff {

    public static void main( String[] args )
                            throws Exception {

        Properties map = new Properties();
        for ( int i = 0; i < args.length; i += 2 ) {
            map.put( args[i], args[i + 1] );
        }
        File arcInfo = new File( map.getProperty( "-inFile" ) );
        String o = map.getProperty( "-outFile" );
        if ( !o.toLowerCase().endsWith( ".tiff" ) && !o.toLowerCase().endsWith( ".tif" ) ) {
            throw new Exception( "output image format must be tiff or tiff" );
        }
        File out = new File( o );

        String depth = map.getProperty( "-type" );

        ArcInfoTextRasterReader reader = new ArcInfoTextRasterReader( arcInfo );
        WorldFile wf = reader.readMetadata();
        o = o.substring( 0, o.lastIndexOf( '.' ) );
        WorldFile.writeWorldFile( wf, o );
        float[][] data = reader.readData();
        BufferedImage bi = RawData2Image.rawData2Image( data, "32".equals( depth ) );

        ImageUtils.saveImage( bi, out, 1 );
    }

}
