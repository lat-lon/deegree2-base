//$HeadURL: svn+ssh://jwilden@scm.wald.intevation.org/deegree/base/trunk/src/org/deegree/model/metadata/iso19115/TypeCode.java $
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

package org.deegree.model.metadata.iso19115;

import java.io.Serializable;
import java.net.URI;

import org.deegree.datatypes.Code;

/**
 * Defines a semantic category.
 * 
 * TypeCodes defined by ISO19115 are:
 * <ul>
 * <li>discipline
 * <li>place
 * <li>stratum
 * <li>temporal
 * <li>theme
 * </ul>
 * 
 * @author <a href="mailto:schaefer@lat-lon.de">Axel Schaefer </a>
 * @author <a href="mailto:schneider@lat-lon.de">Markus Schneider </a>
 * @version $Revision: 9343 $ $Date: 2007-12-27 14:30:32 +0100 (Thu, 27 Dec 2007) $
 */
public class TypeCode extends Code implements Serializable {

    public TypeCode( String code ) {
        super( code );
    }

    public TypeCode( String code, URI codeSpace ) {
        super( code, codeSpace );
    }
}