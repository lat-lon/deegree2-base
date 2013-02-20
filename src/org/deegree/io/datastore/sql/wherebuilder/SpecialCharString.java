//$HeadURL: svn+ssh://mschneider@svn.wald.intevation.org/deegree/base/trunk/src/org/deegree/io/datastore/sql/wherebuilder/AbstractPropertyNode.java $
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
package org.deegree.io.datastore.sql.wherebuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a <code>String</code> that may contain special symbols (wildCard, singleChar, escape) as a list of its
 * parts ({@link SpecialCharStringPart}).
 * <p>
 * The internal representation needs no escape symbols.
 * 
 * @author <a href="mailto:schneider@lat-lon.de">Markus Schneider </a>
 * @author last edited by: $Author: mschneider $
 * 
 * @version $Revision: 6588 $, $Date: 2007-04-11 17:31:29 +0200 (Mi, 11 Apr 2007) $
 */
public class SpecialCharString {

    private List<SpecialCharStringPart> parts;

    private String wildCard;

    private String singleChar;

    private String escape;

    /**
     * Constructs a new <code>SpecialCharString</code> instance from the given parameters.
     * 
     * @param encodedString
     * @param wildCard
     * @param singleChar
     * @param escape
     */
    public SpecialCharString( String encodedString, String wildCard, String singleChar, String escape ) {
        this.wildCard = wildCard;
        this.singleChar = singleChar;
        this.escape = escape;
        this.parts = decode( encodedString );
    }

    /**
     * Decodes the given <code>String</code> to a representation that contains explicit objects to represent wildCard
     * and singleChar symbols and has no escape symbols.
     * 
     * @param text
     *            encoded <code>String</code>, may contain wildCard, singleChar and escape symbols
     * @return decoded text
     */
    private List<SpecialCharStringPart> decode( String text ) {

        List<SpecialCharStringPart> parts = new ArrayList<SpecialCharStringPart>( text.length() );

        boolean escapeMode = false;

        StringBuffer sb = null;
        while ( text.length() > 0 ) {
            if ( escapeMode ) {
                if ( sb == null ) {
                    sb = new StringBuffer();
                }
                sb.append( text.charAt( 0 ) );
                text = text.substring( 1 );
                escapeMode = false;
            } else {
                if ( text.startsWith( wildCard ) ) {
                    if ( sb != null ) {
                        parts.add( new PlainText( sb.toString() ) );
                        sb = null;
                    }
                    parts.add( new WildCard() );
                    text = text.substring( wildCard.length() );
                } else if ( text.startsWith( singleChar ) ) {
                    if ( sb != null ) {
                        parts.add( new PlainText( sb.toString() ) );
                        sb = null;
                    }
                    parts.add( new SingleChar() );
                    text = text.substring( singleChar.length() );
                } else if ( text.startsWith( escape ) ) {
                    text = text.substring( escape.length() );
                    escapeMode = true;
                } else {
                    if ( sb == null ) {
                        sb = new StringBuffer();
                    }
                    sb.append( text.charAt( 0 ) );
                    text = text.substring( 1 );
                }
            }
        }

        if ( sb != null ) {
            parts.add( new PlainText( sb.toString() ) );
        }
        return parts;
    }

    /**
     * Returns an encoding that is suitable for arguments of "IS LIKE"-clauses in SQL.
     * <p>
     * This means:
     * <ul>
     * <li>wildCard: encoded as the '%'-character</li>
     * <li>singleChar: encoded as the '_'-character</li>
     * <li>escape: encoded as the '\'-character</li>
     * </ul>
     * 
     * @return encoded string
     */
    public String toSQLStyle() {
        StringBuffer sb = new StringBuffer();
        for ( SpecialCharStringPart part : parts ) {
            sb.append( part.toSQLStyle() );
        }
        return sb.toString();
    }

    /**
     * Returns an encoding that is suitable for arguments of "IS LIKE"-clauses in SQL.
     * <p>
     * This means:
     * <ul>
     * <li>wildCard: encoded as the '%'-character</li>
     * <li>singleChar: encoded as the '_'-character</li>
     * <li>escape: encoded as the '\'-character</li>
     * </ul>
     * 
     * @param toLowerCase
     *            true means: convert to lowercase letters
     * @return encoded string
     */
    public String toSQLStyle( boolean toLowerCase ) {
        StringBuffer sb = new StringBuffer();
        for ( SpecialCharStringPart part : parts ) {
            sb.append( part.toSQLStyle( toLowerCase ) );
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for ( SpecialCharStringPart part : parts ) {
            sb.append( part.toString() );
            sb.append( '\n' );
        }
        return sb.toString();
    }
}
