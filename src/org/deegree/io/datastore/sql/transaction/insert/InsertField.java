//$HeadURL: svn+ssh://jwilden@scm.wald.intevation.org/deegree/base/trunk/src/org/deegree/io/datastore/sql/transaction/insert/InsertField.java $
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
package org.deegree.io.datastore.sql.transaction.insert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.deegree.datatypes.Types;
import org.deegree.datatypes.UnknownTypeException;

/**
 * A field of an {@link InsertRow}.
 * 
 * @author <a href="mailto:schneider@lat-lon.de">Markus Schneider</a>
 * @author last edited by: $Author: apoth $
 * 
 * @version $Revision: 9342 $, $Date: 2007-12-27 13:32:57 +0100 (Thu, 27 Dec 2007) $
 */
public class InsertField {

    private String columnName;

    private Object value;

    private int sqlType;

    private boolean isPK;

    private InsertField referencedField;

    private List<InsertField> referencingFields = new ArrayList<InsertField>();

    private InsertRow row;

    InsertField( InsertRow row, String columnName, int sqlType, Object value, boolean isPK ) {
        this.row = row;
        this.columnName = columnName;
        this.sqlType = sqlType;
        this.value = value;
        this.isPK = isPK;
    }

    InsertField( InsertRow row, String columnName, InsertField referencedField ) {
        this.row = row;
        this.columnName = columnName;
        this.referencedField = referencedField;
    }

    /**
     * Returns the {@link InsertRow} that this <code>InsertField</code> belongs to.
     * 
     * @return the {@link InsertRow} that this <code>InsertField</code> belongs to
     */
    public InsertRow getRow() {
        return this.row;
    }    
    
    /**
     * Returns the name of the table that this <code>InsertField</code> belongs to.
     * 
     * @return the name of the table that this <code>InsertField</code> belongs to
     */
    public String getTable() {
        return this.row.getTable();
    }

    /**
     * Returns whether this <code>InsertField</code> is a primary key of the table.
     * 
     * @return true, if this InsertField is a primary key of the table, false otherwise
     */
    public boolean isPK() {
        return this.isPK;
    }

    /**
     * Returns the name of the column that this <code>InsertField</code> represents.
     * 
     * @return the name of the column that this <code>InsertField</code> represents
     */
    public String getColumnName() {
        return this.columnName;
    }

    /**
     * Returns the sql type code for the column.
     * 
     * @return the sql type code for the column
     */
    public int getSQLType() {
        if ( this.referencedField != null ) {
            return this.referencedField.getSQLType();
        }
        return this.sqlType;
    }

    /**
     * Returns the value to be inserted.
     * 
     * @return the value to be inserted
     */
    public Object getValue() {
        if ( this.referencedField != null ) {
            return this.referencedField.getValue();
        }
        return this.value;
    }

    /**
     * Returns the <code>InsertField</code> that this <code>InsertField</code> references.
     * 
     * @return the referenced InsertField or null, if no field is referenced
     */
    public InsertField getReferencedField() {
        return this.referencedField;
    }

    InsertRow getReferencedRow() {
        if ( this.referencedField == null ) {
            return null;
        }
        return this.referencedField.row;
    }

    /**
     * Sets this <code>InsertField</code> to be a reference to the given <code>InsertField</code>.
     * 
     * @param field
     */
    public void relinkField( InsertField field ) {
        if ( referencedField != null ) {
            this.referencedField.removeReferencingField( this );
        }
        this.referencedField = field;
        field.addReferencingField( this );
    }

    void addReferencingField( InsertField referencingField ) {
        if ( referencingField == null ) {
            InsertRow.LOG.logError( "addReferencingField() called with null value" );

        } else {
            this.referencingFields.add( referencingField );
        }
    }

    /**
     * Removes the given <code>InsertField</code> from the list of references to this
     * <code>InsertField</code>.
     * 
     * @param referencingField
     */
    public void removeReferencingField( InsertField referencingField ) {
        this.referencingFields.remove( referencingField );
    }

    Collection<InsertField> getReferencingFields() {
        return this.referencingFields;
    }

    Collection<InsertRow> getReferencingRows() {
        List<InsertRow> referencingRows = new ArrayList<InsertRow>();
        Iterator<InsertField> iter = this.referencingFields.iterator();
        while ( iter.hasNext() ) {
            referencingRows.add( iter.next().row );
        }
        return referencingRows;
    }

    /**
     * Returns a string representation of the object.
     * 
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        String stringValue = getValue().toString();
        if ( stringValue.length() > 20 ) {
            stringValue = stringValue.substring( 0, 19 );
            stringValue += "...";
        }

        int type = getSQLType();
        String typeString = "" + type;
        try {
            typeString = Types.getTypeNameForSQLTypeCode( type );
        } catch ( UnknownTypeException e ) {
            InsertRow.LOG.logError( e.getMessage(), e );
        }
        sb.append( getColumnName() );
        sb.append( "(" );
        sb.append( typeString );
        sb.append( ")='" );
        sb.append( stringValue );
        sb.append( '\'' );

        InsertField referencedField = getReferencedField();
        if ( referencedField != null ) {
            sb.append( " [fk to '" );
            sb.append( referencedField.getTable() );
            sb.append( '.' );
            sb.append( referencedField.getColumnName() );
            sb.append( "']" );
        }
        return sb.toString();
    }
}