//$HeadURL$
/*----------------    FILE HEADER  ------------------------------------------
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
 * @version $Revision$
 */
public class KeyAlreadyExistException extends DBaseIndexException {
    /**
     * Creates a new KeyAlreadyExistException object.
     * 
     * @param key
     * @param index
     */
    public KeyAlreadyExistException( Comparable key, DBaseIndex index ) {
        super( "Key " + key + " already exist in index " + index, key, index );
    }
}