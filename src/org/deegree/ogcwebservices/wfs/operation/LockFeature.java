//$HeadURL: svn+ssh://jwilden@scm.wald.intevation.org/deegree/base/trunk/src/org/deegree/ogcwebservices/wfs/operation/LockFeature.java $
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
package org.deegree.ogcwebservices.wfs.operation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.deegree.datatypes.QualifiedName;
import org.deegree.framework.log.ILogger;
import org.deegree.framework.log.LoggerFactory;
import org.deegree.i18n.Messages;
import org.deegree.model.filterencoding.Filter;
import org.deegree.ogcwebservices.InconsistentRequestException;
import org.deegree.ogcwebservices.InvalidParameterValueException;
import org.deegree.ogcwebservices.OGCWebServiceException;
import org.w3c.dom.Element;

/**
 * Represents a <code>LockFeature</code> request to a web feature service.
 * <p>
 * Web connections are inherently stateless. Unfortunately, this means that the semantics of
 * serializable transactions are not preserved. To understand the issue consider an UPDATE
 * operation.
 * <p>
 * The client fetches a feature instance. The feature is then modified on the client side, and
 * submitted back to the database, via a Transaction request for update. Serializability is lost
 * since there is nothing to guarantee that while the feature was being modified on the client side,
 * another client did not come along and update that same feature in the database.
 * <p>
 * One way to ensure serializability is to require that access to data be done in a mutually
 * exclusive manner; that is while one transaction accesses a data item, no other transaction can
 * modify the same data item. This can be accomplished by using locks that control access to the
 * data.
 * <p>
 * The purpose of the LockFeature interface is to expose a long term feature locking mechanism to
 * ensure consistency. The lock is considered long term because network latency would make feature
 * locks last relatively longer than native commercial database locks.
 * <p>
 * The LockFeature interface is optional and need only be implemented if the underlying datastore
 * supports (or can be made to support) data locking. In addition, the implementation of locking is
 * completely opaque to the client.
 * 
 * @author <a href="mailto:poth@lat-lon.de">Andreas Poth </a>
 * @author <a href="mailto:schneider@lat-lon.de">Markus Schneider</a>
 * @author last edited by: $Author: apoth $
 * 
 * @version $Revision: 9345 $
 */
public class LockFeature extends AbstractWFSRequest {

    private static final ILogger LOG = LoggerFactory.getLogger( LockFeature.class );

    private static final long serialVersionUID = 1407310243527517490L;

    /** Default value for expiry (in minutes). */
    public static String DEFAULT_EXPIRY = "5";

    /** Duration until timeout (in milliseconds). */
    private long expiry;

    private ALL_SOME_TYPE lockAction;

    private List<Lock> locks;

    /**
     * Known lock actions.
     */
    public static enum ALL_SOME_TYPE {

        /**
         * Acquire a lock on all requested feature instances. If some feature instances cannot be
         * locked, the operation should fail, and no feature instances should remain locked.
         */
        ALL,

        /**
         * Lock as many of the requested feature instances as possible.
         */
        SOME
    }

    /**
     * String value for {@link ALL_SOME_TYPE ALL_SOME_TYPE.ALL}.
     */
    public static String LOCK_ACTION_ALL = "ALL";

    /**
     * String value for {@link ALL_SOME_TYPE ALL_SOME_TYPE.SOME}.
     */
    public static String LOCK_ACTION_SOME = "SOME";

    /**
     * Creates a new <code>LockFeature</code> instance from the given parameters.
     * 
     * @param version
     *            request version
     * @param id
     *            id of the request
     * @param handle
     *            handle of the request
     * @param expiry
     *            the limit on how long the web feature service keeps the lock (in milliseconds)
     * @param lockAction
     *            method for lock acquisition
     * @param locks
     *            contained lock operations
     */
    LockFeature( String version, String id, String handle, long expiry, ALL_SOME_TYPE lockAction,
                 List<Lock> locks ) {
        super( version, id, handle, null );
        this.expiry = expiry;
        this.lockAction = lockAction;
        this.locks = locks;
    }

    /**
     * Creates a new <code>LockFeature</code> instance from the given parameters.
     * 
     * @param version
     *            request version
     * @param id
     *            id of the request
     * @param handle
     *            handle of the request
     * @param expiry
     *            the limit on how long the web feature service holds the lock (in milliseconds)
     * @param lockAction
     *            method for lock acquisition
     * @param locks
     *            contained lock operations
     * @return new <code>LockFeature</code> request
     */
    public static LockFeature create( String version, String id, String handle, long expiry,
                                      ALL_SOME_TYPE lockAction, List<Lock> locks ) {
        return new LockFeature( version, id, handle, expiry, lockAction, locks );
    }

    /**
     * Creates a new <code>LockFeature</code> instance from a document that contains the DOM
     * representation of the request.
     * 
     * @param id
     *            of the request
     * @param root
     *            element that contains the DOM representation of the request
     * @return new <code>LockFeature</code> request
     * @throws OGCWebServiceException
     */
    public static LockFeature create( String id, Element root )
                            throws OGCWebServiceException {
        LockFeatureDocument doc = new LockFeatureDocument();
        doc.setRootElement( root );
        LockFeature request;
        try {
            request = doc.parse( id );
        } catch ( Exception e ) {
            LOG.logError( e.getMessage(), e );
            throw new OGCWebServiceException( "LockFeature", e.getMessage() );
        }
        return request;
    }

    /**
     * Creates a new <code>LockFeature</code> request from the given parameter map.
     * 
     * @param kvp
     *            key-value pairs, keys have to be uppercase
     * @return new <code>LockFeature</code> request
     * @throws InconsistentRequestException
     * @throws InvalidParameterValueException
     */
    public static LockFeature create( Map<String, String> kvp )
                            throws InconsistentRequestException, InvalidParameterValueException {

        // SERVICE
        checkServiceParameter( kvp );

        // ID (deegree specific)
        String id = kvp.get( "ID" );

        // VERSION
        String version = checkVersionParameter( kvp );

        // TYPENAME
        QualifiedName[] typeNames = extractTypeNames( kvp );
        if ( typeNames == null ) {
            // no TYPENAME parameter -> FEATUREID must be present
            String featureId = kvp.get( "FEATUREID" );
            if ( featureId != null ) {
                String msg = Messages.getMessage( "WFS_FEATUREID_PARAM_UNSUPPORTED" );
                throw new InvalidParameterValueException( msg );
            }
            String msg = Messages.getMessage( "WFS_TYPENAME+FID_PARAMS_MISSING" );
            throw new InvalidParameterValueException( msg );
        }

        // EXPIRY
        String expiryString = getParam( "EXPIRY", kvp, DEFAULT_EXPIRY );
        int expiry = 0;
        try {
            expiry = Integer.parseInt( expiryString );
            if ( expiry < 1 ) {
                throw new NumberFormatException();
            }
        } catch ( NumberFormatException e ) {
            String msg = Messages.getMessage( "WFS_PARAMETER_INVALID_INT", expiryString, "EXPIRY" );
            throw new InvalidParameterValueException( msg );
        }

        // LOCKACTION
        String lockActionString = getParam( "LOCKACTION", kvp, "ALL" );
        ALL_SOME_TYPE lockAction = validateLockAction( lockActionString );

        // BBOX
        Filter bboxFilter = extractBBOXFilter( kvp );

        // FILTER (prequisite: TYPENAME)
        Map<QualifiedName, Filter> filterMap = extractFilters( kvp, typeNames );
        if ( bboxFilter != null && filterMap.size() > 0 ) {
            String msg = Messages.getMessage( "WFS_BBOX_FILTER_INVALID" );
            throw new InvalidParameterValueException( msg );
        }

        // build a Lock instance for each requested feature type (later also for each featureid...)
        List<Lock> locks = new ArrayList<Lock>( typeNames.length );
        for ( QualifiedName ftName : typeNames ) {
            Filter filter;
            if ( bboxFilter != null ) {
                filter = bboxFilter;
            } else {
                filter = filterMap.get( ftName );
            }
            locks.add( new Lock( null, ftName, filter ) );
        }
        return new LockFeature( version, id, null, expiry, lockAction, locks );
    }

    /**
     * Returns the limit on how long the web feature service holds the lock in the event that a
     * transaction is never issued that would release the lock. The expiry limit is specified in
     * milliseconds.
     * 
     * @return the limit on how long the web feature service holds the lock (in milliseconds)
     */
    public long getExpiry() {
        return this.expiry;
    }

    /**
     * Returns the mode for lock acquisition.
     * 
     * @see ALL_SOME_TYPE
     * 
     * @return the mode for lock acquisition
     */
    public ALL_SOME_TYPE getLockAction() {
        return this.lockAction;
    }

    /**
     * Returns whether this request requires that all features have to be lockable in order to be
     * performed succesfully.
     * 
     * @see ALL_SOME_TYPE
     * 
     * @return true, if all features have to be lockable, false otherwise
     */
    public boolean lockAllFeatures() {
        return this.lockAction == ALL_SOME_TYPE.ALL;
    }

    /**
     * Returns the contained lock operations.
     * 
     * @return the contained lock operations
     */
    public List<Lock> getLocks() {
        return this.locks;
    }

    static ALL_SOME_TYPE validateLockAction( String lockActionString )
                            throws InvalidParameterValueException {
        ALL_SOME_TYPE lockAction = ALL_SOME_TYPE.ALL;
        if ( LOCK_ACTION_ALL.equals( lockActionString ) ) {
            // nothing to do
        } else if ( LOCK_ACTION_SOME.equals( lockActionString ) ) {
            lockAction = ALL_SOME_TYPE.SOME;
        } else {
            String msg = Messages.getMessage( "WFS_LOCKACTION_INVALID", lockActionString,
                                              LOCK_ACTION_ALL, LOCK_ACTION_SOME );
            throw new InvalidParameterValueException( "LOCKACTION", msg );
        }
        return lockAction;
    }
}
