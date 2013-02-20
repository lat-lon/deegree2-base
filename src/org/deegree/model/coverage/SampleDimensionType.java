//$HeadURL: svn+ssh://jwilden@scm.wald.intevation.org/deegree/base/trunk/src/org/deegree/model/coverage/SampleDimensionType.java $
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
package org.deegree.model.coverage;

/**
 * Specifies the various dimension types for coverage values. For grid coverages, these correspond
 * to band types.
 * 
 * @UML codelist CV_SampleDimensionType
 * @author <A HREF="http://www.opengis.org">OpenGIS&reg; consortium</A>
 * @version <A HREF="http://www.opengis.org/docs/01-004.pdf">Grid Coverage specification 1.0</A>
 * 
 * @see SampleDimension
 */
public final class SampleDimensionType extends CodeList {
    /**
     * Serial number for compatibility with different versions.
     */
    private static final long serialVersionUID = -4153433145134818506L;

    /**
     * Unsigned 1 bit integers.
     * 
     * @UML conditional CV_1BIT
     * @rename Renamed <code>CV_1BIT</code> as <code>UNSIGNED_1BIT</code> since we drop the
     *         prefix, but can't get a name starting with a digit.
     */
    public static final SampleDimensionType UNSIGNED_1BIT = new SampleDimensionType( "UNSIGNED_1BIT", 0 );

    /**
     * Unsigned 2 bits integers.
     * 
     * @UML conditional CV_2BIT
     * @rename Renamed <code>CV_2BIT</code> as <code>UNSIGNED_2BITS</code> since we drop the
     *         prefix, but can't get a name starting with a digit.
     */
    public static final SampleDimensionType UNSIGNED_2BITS = new SampleDimensionType( "UNSIGNED_2BITS", 1 );

    /**
     * Unsigned 4 bits integers.
     * 
     * @UML conditional CV_4BIT
     * @rename Renamed <code>CV_4BIT</code> as <code>UNSIGNED_4BITS</code> since we drop the
     *         prefix, but can't get a name starting with a digit.
     */
    public static final SampleDimensionType UNSIGNED_4BITS = new SampleDimensionType( "UNSIGNED_4BITS", 2 );

    /**
     * Unsigned 8 bits integers.
     * 
     * @UML conditional CV_8BIT_U
     * @rename Renamed <code>CV_8BIT_U</code> as <code>UNSIGNED_8BITS</code> since we drop the
     *         prefix, but can't get a name starting with a digit.
     * 
     * @see #SIGNED_8BITS
     * @see java.awt.image.DataBuffer#TYPE_BYTE
     */
    public static final SampleDimensionType UNSIGNED_8BITS = new SampleDimensionType( "UNSIGNED_8BITS", 3 );

    /**
     * Signed 8 bits integers.
     * 
     * @UML conditional CV_8BIT_S
     * @rename Renamed <code>CV_8BIT_S</code> as <code>SIGNED_8BITS</code> since we drop the
     *         prefix, but can't get a name starting with a digit.
     * 
     * @see #UNSIGNED_8BITS
     */
    public static final SampleDimensionType SIGNED_8BITS = new SampleDimensionType( "SIGNED_8BITS", 4 );

    /**
     * Unsigned 16 bits integers.
     * 
     * @UML conditional CV_16BIT_U
     * @rename Renamed <code>CV_16BIT_U</code> as <code>UNSIGNED_16BITS</code> since we drop the
     *         prefix, but can't get a name starting with a digit.
     * 
     * @see #SIGNED_16BITS
     * @see java.awt.image.DataBuffer#TYPE_USHORT
     */
    public static final SampleDimensionType UNSIGNED_16BITS = new SampleDimensionType( "UNSIGNED_16BITS", 5 );

    /**
     * Signed 16 bits integers.
     * 
     * @UML conditional CV_16BIT_S
     * @rename Renamed <code>CV_16BIT_S</code> as <code>SIGNED_16BITS</code> since we drop the
     *         prefix, but can't get a name starting with a digit.
     * 
     * @see #UNSIGNED_16BITS
     * @see java.awt.image.DataBuffer#TYPE_SHORT
     */
    public static final SampleDimensionType SIGNED_16BITS = new SampleDimensionType( "SIGNED_16BITS", 6 );

    /**
     * Unsigned 32 bits integers.
     * 
     * @UML conditional CV_32BIT_U
     * @rename Renamed <code>CV_32BIT_U</code> as <code>UNSIGNED_32BITS</code> since we drop the
     *         prefix, but can't get a name starting with a digit.
     * 
     * @see #SIGNED_32BITS
     */
    public static final SampleDimensionType UNSIGNED_32BITS = new SampleDimensionType( "UNSIGNED_32BITS", 7 );

    /**
     * Signed 32 bits integers.
     * 
     * @UML conditional CV_32BIT_S
     * @rename Renamed <code>CV_32BIT_S</code> as <code>SIGNED_32BITS</code> since we drop the
     *         prefix, but can't get a name starting with a digit.
     * 
     * @see #UNSIGNED_32BITS
     * @see java.awt.image.DataBuffer#TYPE_INT
     */
    public static final SampleDimensionType SIGNED_32BITS = new SampleDimensionType( "SIGNED_32BITS", 8 );

    /**
     * Simple precision floating point numbers.
     * 
     * @UML conditional CV_32BIT_REAL
     * @rename Renamed <code>CV_32BIT_REAL</code> as <code>REAL_32BITS</code> since we drop the
     *         prefix, but can't get a name starting with a digit.
     * 
     * @see #REAL_64BITS
     * @see java.awt.image.DataBuffer#TYPE_FLOAT
     */
    public static final SampleDimensionType REAL_32BITS = new SampleDimensionType( "REAL_32BITS", 9 );

    /**
     * Double precision floating point numbers.
     * 
     * @UML conditional CV_64BIT_REAL
     * @rename Renamed <code>CV_64BIT_REAL</code> as <code>REAL_64BITS</code> since we drop the
     *         prefix, but can't get a name starting with a digit.
     * 
     * @see #REAL_32BITS
     * @see java.awt.image.DataBuffer#TYPE_DOUBLE
     */
    public static final SampleDimensionType REAL_64BITS = new SampleDimensionType( "REAL_64BITS", 10 );

    /**
     * List of all enumerations of this type.
     */
    private static final SampleDimensionType[] VALUES = new SampleDimensionType[] { UNSIGNED_1BIT, UNSIGNED_2BITS,
                                                                                   UNSIGNED_4BITS, UNSIGNED_8BITS,
                                                                                   SIGNED_8BITS, UNSIGNED_16BITS,
                                                                                   SIGNED_16BITS, UNSIGNED_32BITS,
                                                                                   SIGNED_32BITS, REAL_32BITS,
                                                                                   REAL_64BITS };

    /**
     * Constructs an enum with the given name.
     */
    private SampleDimensionType( final String name, final int ordinal ) {
        super( name, ordinal );
    }

    /**
     * Returns the list of <code>SampleDimensionType</code>s.
     */
    public static SampleDimensionType[] values() {
        return VALUES.clone();
    }

    /**
     * Returns the list of enumerations of the same kind than this enum.
     */
    public CodeList[] family() {
        return values();
    }
}
