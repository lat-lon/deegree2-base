//$HeadURL$
/*----------------------------------------------------------------------------
 This file is part of deegree, http://deegree.org/
 Copyright (C) 2001-2009 by:
   Department of Geography, University of Bonn
 and
   lat/lon GmbH

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

package org.deegree.model.filterencoding;

import org.deegree.model.feature.Feature;

/**
 *
 * @author <a href="mailto:schneider@lat-lon.de">Markus Schneider</a>
 * @author last edited by: $Author$
 *
 * @version $Revision$, $Date$
 */
public interface Operation {

    /**
     * Returns the name of the operator.
     *
     * @return the name of the operator.
     */
    String getOperatorName();

    /**
     * Returns the operator's id.
     *
     * @return the operator's id.
     */
    int getOperatorId();

    /**
     * Calculates the <tt>Filter</tt>'s logical value based on the certain property values of the given feature.
     *
     * @param feature
     *            that determines the values of <tt>PropertyNames</tt> in the expression
     * @return true, if the <tt>Filter</tt> evaluates to true, else false
     * @throws FilterEvaluationException
     *             if the evaluation fails
     */
    public boolean evaluate( Feature feature )
                            throws FilterEvaluationException;
   
    /**
     * Produces an XML representation of this object that complies to Filter Encoding specification 1.0.0.
     *
     * @return an XML representation of this object
     */
    public StringBuffer to100XML();

    /**
     * Produces an XML representation of this object that complies to Filter Encoding specification 1.1.0.
     *
     * @return an XML representation of this object
     */
    public StringBuffer to110XML();
}
