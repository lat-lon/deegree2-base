//$HeadURL: svn+ssh://jwilden@scm.wald.intevation.org/deegree/base/trunk/src/org/deegree/io/dbaseapi/DBaseIndexException.java $
/*

 This file is part of deegree.

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

 Copyright (C) May 2003 by IDgis BV, The Netherlands - www.idgis.nl
 */

package org.deegree.io.dbaseapi;

/**
 * 
 * 
 * 
 * @author <a href="mailto:poth@lat-lon.de">Andreas Poth</a>
 * @author last edited by: $Author: mschneider $
 * 
 * @version $Revision: 10547 $, $Date: 2008-03-11 09:40:28 +0100 (Tue, 11 Mar 2008) $
 */
public abstract class DBaseIndexException extends Exception {
    private Comparable key;

    private DBaseIndex index;

    public DBaseIndexException( String error, Comparable key, DBaseIndex index ) {
        super( error );

        this.key = key;
        this.index = index;
    }

    /**
     * 
     * @return key
     */
    public Comparable getKey() {
        return key;
    }

    /**
     * 
     * @return dbase index object
     */
    public DBaseIndex getIndex() {
        return index;
    }

}