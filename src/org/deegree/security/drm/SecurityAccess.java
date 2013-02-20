//$HeadURL: svn+ssh://jwilden@scm.wald.intevation.org/deegree/base/trunk/src/org/deegree/security/drm/SecurityAccess.java $
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
package org.deegree.security.drm;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Stack;

import org.deegree.security.GeneralSecurityException;
import org.deegree.security.UnauthorizedException;
import org.deegree.security.drm.model.Group;
import org.deegree.security.drm.model.Privilege;
import org.deegree.security.drm.model.RightType;
import org.deegree.security.drm.model.Role;
import org.deegree.security.drm.model.SecurableObject;
import org.deegree.security.drm.model.SecuredObject;
import org.deegree.security.drm.model.User;

/**
 * 
 * 
 * 
 * @version $Revision: 9346 $
 * @author <a href="mailto:poth@lat-lon.de">Andreas Poth</a>
 * @author last edited by: $Author: apoth $
 * 
 * @version 1.0. $Revision: 9346 $, $Date: 2007-12-27 17:39:07 +0100 (Thu, 27 Dec 2007) $
 * 
 * @since 2.0
 */
public class SecurityAccess {

    protected User user;

    protected SecurityRegistry registry;

    SecurityAccess( User user, SecurityRegistry registry ) {
        this.user = user;
        this.registry = registry;
    }

    public User getUser() {
        return user;
    }

    public User getUserByName( String name )
                            throws GeneralSecurityException {
        return registry.getUserByName( this, name );
    }

    public User getUserById( int id )
                            throws GeneralSecurityException {
        return registry.getUserById( this, id );
    }

    public Group getGroupByName( String name )
                            throws GeneralSecurityException {
        return registry.getGroupByName( this, name );
    }

    public Group getGroupById( int id )
                            throws GeneralSecurityException {
        return registry.getGroupById( this, id );
    }

    public Role getRoleByName( String name )
                            throws GeneralSecurityException {
        return registry.getRoleByName( this, name );
    }

    public Role[] getRolesByNS( String ns )
                            throws GeneralSecurityException {
        return registry.getRolesByNS( this, ns );
    }

    public Role getRoleById( int id )
                            throws GeneralSecurityException {
        return registry.getRoleById( this, id );
    }

    public RightType getRightByName( String name )
                            throws GeneralSecurityException {
        return registry.getRightTypeByName( this, name );
    }

    public Privilege getPrivilegeByName( String name )
                            throws GeneralSecurityException {
        return registry.getPrivilegeByName( this, name );
    }

    public SecuredObject getSecuredObjectById( int id )
                            throws GeneralSecurityException {
        return registry.getSecuredObjectById( this, id );
    }

    public SecuredObject getSecuredObjectByName( String name, String type )
                            throws GeneralSecurityException {
        return registry.getSecuredObjectByName( this, name, type );
    }

    public SecuredObject[] getSecuredObjectsByNS( String ns, String type )
                            throws GeneralSecurityException {
        return registry.getSecuredObjectsByNS( this, ns, type );
    }

    public User[] getAllUsers()
                            throws GeneralSecurityException {
        return registry.getAllUsers( this );
    }

    public Group[] getAllGroups()
                            throws GeneralSecurityException {
        return registry.getAllGroups( this );
    }

    public SecuredObject[] getAllSecuredObjects( String type )
                            throws GeneralSecurityException {
        return registry.getAllSecuredObjects( this, type );
    }

    /**
     * Retrieves all <code>Role</code> s from the <code>Registry</code>, except those that are
     * only used internally (these have a namespace starting with the $ symbol);
     * 
     * @throws GeneralSecurityException
     */
    public Role[] getAllRoles()
                            throws GeneralSecurityException {
        return registry.getAllRoles( this );
    }

    /**
     * Returns all <code>Role</code> s that the given <code>User</code> is associated with
     * (directly and via group memberships).
     * 
     * @param user
     * @throws GeneralSecurityException
     */
    public Role[] getAllRolesForUser( User user )
                            throws GeneralSecurityException {

        HashSet<Group> allGroups = new HashSet<Group>();
        Stack<Group> groupStack = new Stack<Group>();
        Group[] groups = registry.getGroupsForUser( this, user );
        for ( int i = 0; i < groups.length; i++ ) {
            groupStack.push( groups[i] );
        }

        // collect all groups that user is member of
        while ( !groupStack.isEmpty() ) {
            Group currentGroup = groupStack.pop();
            allGroups.add( currentGroup );
            groups = registry.getGroupsForGroup( this, currentGroup );
            for ( int i = 0; i < groups.length; i++ ) {
                if ( !allGroups.contains( groups[i] ) ) {
                    allGroups.add( groups[i] );
                    groupStack.push( groups[i] );
                }
            }
        }

        HashSet<Role> allRoles = new HashSet<Role>();

        // add all directly associated roles
        Role[] roles = registry.getRolesForUser( this, user );
        for ( int i = 0; i < roles.length; i++ ) {
            allRoles.add( roles[i] );
        }

        // add all roles that are associated via group membership
        Iterator it = allGroups.iterator();
        while ( it.hasNext() ) {
            Group group = (Group) it.next();
            roles = registry.getRolesForGroup( this, group );
            for ( int i = 0; i < roles.length; i++ ) {
                allRoles.add( roles[i] );
            }
        }
        return allRoles.toArray( new Role[allRoles.size()] );
    }

    /**
     * Returns all <code>Role</code> s that the given <code>Group</code> is associated with
     * (directly and via group memberships).
     * 
     * @param group
     * @throws GeneralSecurityException
     */
    public Role[] getAllRolesForGroup( Group group )
                            throws GeneralSecurityException {

        HashSet<Group> allGroups = new HashSet<Group>();
        Stack<Group> groupStack = new Stack<Group>();
        groupStack.push( group );

        while ( !groupStack.isEmpty() ) {
            Group currentGroup = groupStack.pop();
            Group[] groups = registry.getGroupsForGroup( this, currentGroup );
            for ( int i = 0; i < groups.length; i++ ) {
                if ( !allGroups.contains( groups[i] ) ) {
                    allGroups.add( groups[i] );
                    groupStack.push( groups[i] );
                }
            }
        }

        HashSet<Role> allRoles = new HashSet<Role>();
        Iterator it = allGroups.iterator();
        while ( it.hasNext() ) {
            Role[] roles = registry.getRolesForGroup( this, (Group) it.next() );
            for ( int i = 0; i < roles.length; i++ ) {
                allRoles.add( roles[i] );
            }
        }
        return allRoles.toArray( new Role[allRoles.size()] );
    }

    /**
     * Tries to find a cyle in the groups relations of the <code>Registry</code>.
     * 
     * @return indicates the cycle's nodes (groups)
     */
    public Group[] findGroupCycle()
                            throws GeneralSecurityException {
        Group[] allGroups = getAllGroups();
        for ( int i = 0; i < allGroups.length; i++ ) {
            Stack<Group> path = new Stack<Group>();
            if ( findGroupCycle( allGroups[i], path ) ) {
                return path.toArray( new Group[path.size()] );
            }
        }
        return null;
    }

    /**
     * Recursion part for the <code>findGroupCycle</code> -algorithm.
     * <p>
     * Modified depth first search.
     * 
     * @param group
     * @param path
     * @return
     * @throws GeneralSecurityException
     */
    private boolean findGroupCycle( Group group, Stack<Group> path )
                            throws GeneralSecurityException {
        if ( path.contains( group ) ) {
            path.push( group );
            return true;
        }
        path.push( group );
        Group[] members = registry.getGroupsForGroup( this, group );
        for ( int i = 0; i < members.length; i++ ) {
            if ( findGroupCycle( members[i], path ) ) {
                return true;
            }
        }
        path.pop();
        return false;
    }

    /**
     * Checks if the associated <code>User</code> has a certain <code>Privilege</code>.
     * 
     * @param privilege
     * @throws GeneralSecurityException
     *             if holder does not have the privilege
     */
    protected void checkForPrivilege( Privilege privilege )
                            throws GeneralSecurityException {
        if ( !user.hasPrivilege( this, privilege ) ) {
            throw new GeneralSecurityException( "The requested operation requires the privilege '"
                                                + privilege.getName() + "'." );
        }
    }

    /**
     * Checks if the associated <code>User</code> has a certain <code>Right</code> on the given
     * <code>SecurableObject</code>.
     * 
     * @param right
     * @param object
     * @throws GeneralSecurityException
     *             this is a UnauthorizedException if the holder does not have the right
     */
    protected void checkForRight( RightType right, SecurableObject object )
                            throws UnauthorizedException, GeneralSecurityException {
        if ( !user.hasRight( this, right, object ) ) {
            throw new UnauthorizedException( "The requested operation requires the right '" + right.getName()
                                             + "' on the object '" + object.getName() + "'." );
        }
    }
}