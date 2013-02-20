//$HeadURL: svn+ssh://jwilden@scm.wald.intevation.org/deegree/base/trunk/src/org/deegree/owscommon_1_1_0/DomainType.java $
/*----------------    FILE HEADER  ------------------------------------------
 This file is part of deegree.
 Copyright (C) 2001-2008 by:
 Department of Geography, University of Bonn
 http://www.giub.uni-bonn.de/deegree/
 lat/lon GmbH
 http://www.lat-lon.de

 This library is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public
 License as published by the Free Software Foundation; either
 version 2.1 of the License, or (at your option) any later version.
 This library is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 Lesser General Public License for more details.
 You should have received a copy of the GNU Lesser General Public
 License along with this library; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
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

package org.deegree.owscommon_1_1_0;

import java.util.List;

import org.deegree.framework.util.Pair;

/**
 * <code>DomainType</code> encapsulation of the domaintype parameters, used in a operationmetadata
 * of ows 1.1.0
 * 
 * @author <a href="mailto:bezema@lat-lon.de">Rutger Bezema</a>
 * 
 * @author last edited by: $Author: rbezema $
 * 
 * @version $Revision: 10830 $, $Date: 2008-03-31 11:33:56 +0200 (Mon, 31 Mar 2008) $
 * 
 */

public class DomainType {

    private final List<String> values;

    private final List<Range> ranges;

    private final String defaultValue;

    private final Pair<String, String> meaning;

    private final Pair<String, String> dataType;

    private final Pair<String, String> uom;

    private final Pair<String, String> referenceSystem;

    private final List<Pair<String, String>> metadataAttribs;

    private final String name;

    private final boolean anyValue;

    private final boolean noValues;

    private final Pair<String, String> valuesReference;

    /**
     * @param values
     *            <code>null</code> if allowedvalues was not set
     * @param ranges
     *            <code>null</code> if allowedvalues was not set
     * @param valuesReference
     *            <code>null</code> if not set
     * @param noValues
     *            true if the noValues element was set.
     * @param anyValue
     *            true if the anyvalues element was set.
     * @param defaultValue
     * @param meaning
     *            a pair containing &lt;text(), reference-attribute&gt; values
     * @param dataType
     *            a pair containing &lt;text(), reference-attribute&gt; values
     * @param uom
     *            a pair containing &lt;text(), reference-attribute&gt; values
     * @param referenceSystem
     *            a pair containing &lt;text(), reference-attribute&gt; values
     * @param metadataAttribs
     *            list containing &lt;xlink:href,about&gt; attribute pairs.
     * @param name
     *            attribute.
     */
    public DomainType( List<String> values, List<Range> ranges, boolean anyValue, boolean noValues,
                       Pair<String, String> valuesReference, String defaultValue, Pair<String, String> meaning,
                       Pair<String, String> dataType, Pair<String, String> uom, Pair<String, String> referenceSystem,
                       List<Pair<String, String>> metadataAttribs, String name ) {
        this.values = values;
        this.ranges = ranges;
        this.anyValue = anyValue;
        this.noValues = noValues;
        this.valuesReference = valuesReference;

        this.defaultValue = defaultValue;
        this.meaning = meaning;
        this.dataType = dataType;
        this.uom = uom;
        this.referenceSystem = referenceSystem;
        this.metadataAttribs = metadataAttribs;
        this.name = name;
    }

    /**
     * @return true if the allowedValues was defined.
     */
    public boolean hasAllowedValues() {
        return ( values != null || ranges != null );
    }

    /**
     * @return the values <code>null</code> if none present
     */
    public final List<String> getValues() {
        return values;
    }

    /**
     * @return the ranges <code>null</code> if none present
     */
    public final List<Range> getRanges() {
        return ranges;
    }

    /**
     * @return the defaultValues <code>null</code> if none present
     */
    public final String getDefaultValue() {
        return defaultValue;
    }

    /**
     * @return the meaning &lt;text(), reference-attribute&gt; or <code>null</code> if none
     *         present
     */
    public final Pair<String, String> getMeaning() {
        return meaning;
    }

    /**
     * @return the dataType &lt;text(), reference-attribute&gt; or <code>null</code> if none
     *         present
     */
    public final Pair<String, String> getDataType() {
        return dataType;
    }

    /**
     * @return true if the DomaintType valuesUnit was set (uom || referencesystem != null ).
     */
    public final boolean hasValuesUnit() {
        return ( uom != null || referenceSystem != null );
    }

    /**
     * @return the uom, &lt;text(), reference-attribute&gt; or <code>null</code> if none present
     */
    public final Pair<String, String> getUom() {
        return uom;
    }

    /**
     * @return the referenceSystem &lt;text(), reference-attribute&gt; or <code>null</code> if
     *         none present
     */
    public final Pair<String, String> getReferenceSystem() {
        return referenceSystem;
    }

    /**
     * @return the metadataAttribs, &lt;xlink,about&gt; or either may be <code>null</code>.
     */
    public final List<Pair<String, String>> getMetadataAttribs() {
        return metadataAttribs;
    }

    /**
     * @return the name, mandatory not <code>null</code>
     */
    public final String getName() {
        return name;
    }

    /**
     * @return true if the anyValue element was set.
     */
    public final boolean hasAnyValue() {
        return anyValue;
    }

    /**
     * @return true if the noValues element was set.
     */
    public final boolean hasNoValues() {
        return noValues;
    }

    /**
     * @return the valuesReference &lt;text(), reference-attribute&gt; or <code>null</code> if
     *         none present
     */
    public final Pair<String, String> getValuesReference() {
        return valuesReference;
    }

}