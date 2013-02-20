//$HeadURL: svn+ssh://jwilden@scm.wald.intevation.org/deegree/base/trunk/src/org/deegree/ogcwebservices/wpvs/j3d/TerrainModel.java $
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

package org.deegree.ogcwebservices.wpvs.j3d;

import java.awt.image.BufferedImage;

import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.RenderingAttributes;
import javax.media.j3d.Shape3D;
import javax.vecmath.Color3f;

import org.deegree.ogcwebservices.wpvs.configuration.RenderingConfiguration;

/**
 * The <code>TerrainModel</code> class is a base class for all dgm's (terrains). It is capable of
 * loading a texture onto the geometry of it's subclasses.
 * 
 * @author <a href="mailto:bezema@lat-lon.de">Rutger Bezema</a>
 * 
 * @author last edited by: $Author: apoth $
 * 
 * @version $Revision: 9345 $, $Date: 2007-12-27 17:22:25 +0100 (Thu, 27 Dec 2007) $
 * 
 */

public abstract class TerrainModel extends Shape3D {

    private BufferedImage textureImage = null;

    /**
     * Creates a TerrainModel with a default Appearance set.
     * <p>
     * The default apearance of this terrain is defined as:
     * <ul>
     * <li> specularColor = new Color3f( 0.7f, 0.7f, 0.7f )</li>
     * <li> ambientColor = white </li>
     * <li> diffuseColor = white </li>
     * <li> shininess = 75f </li>
     * <li> lighting is enabled </li>
     * <li> matieral is writable (Material.ALLOW_COMPONENT_WRITE) </li>
     * </ul>
     * </p>
     */
    protected TerrainModel() {
        setCapability( Shape3D.ALLOW_GEOMETRY_WRITE );
        setAppearance( createDefaultApperance() );
    }

    protected TerrainModel( BufferedImage textureImage ) {
        this();
        this.textureImage = textureImage;
    }

    /**
     * This method implements all the necessary steps to generate a Shape3D Terrain (Elevation
     * model). Before rendering this Class this method should therefor be called prior.
     */
    public abstract void createTerrain();

    /**
     * Creates a J3D Appearance for the surface
     * 
     * @return a new Appearance object
     */
    private Appearance createDefaultApperance() {
        Appearance appearance = new Appearance();

        // create a material
        Color3f specular = new Color3f( 0, 0, 0 );
        Color3f white = new Color3f( 1, 1, 1 );
        Material targetMaterial = new Material();
        targetMaterial.setAmbientColor( white );
        targetMaterial.setDiffuseColor( white );

        targetMaterial.setSpecularColor( specular );
        targetMaterial.setShininess( 65f );
        targetMaterial.setLightingEnable( true );
        targetMaterial.setCapability( Material.ALLOW_COMPONENT_WRITE );
        targetMaterial.setCapability( Material.ALLOW_COMPONENT_READ );
        // targetMaterial.setColorTarget( Material.AMBIENT_AND_DIFFUSE );
        appearance.setMaterial( targetMaterial );

        RenderingConfiguration rc = RenderingConfiguration.getInstance();

        appearance.setColoringAttributes( rc.getColoringAttributes() );

        appearance.setPolygonAttributes( rc.getTerrainPolygonAttributes() );
        
        RenderingAttributes ra = new RenderingAttributes();
        ra.setDepthBufferEnable(true);
        appearance.setRenderingAttributes( ra );

        return appearance;
    }

    /**
     * @param textureImage
     *            An other texture value.
     */
    public void setTexture( BufferedImage textureImage ) {
        if ( textureImage != null ) {
            this.textureImage = textureImage;
            Appearance appearance = getAppearance();

            RenderingConfiguration rc = RenderingConfiguration.getInstance();

            appearance.setTextureAttributes( rc.getTextureAttributes() );
            appearance.setTexture( rc.getTexture( textureImage ) );

            setAppearance( appearance );
        }

    }
    
    /**
     * @return the BufferedImage which can be used as a texture or <code>null</code>if no texture was
     *         defined.
     */
    public BufferedImage getTexture() {
        return this.textureImage;
    }
    
    @Override
    public String toString( ){
        StringBuilder sb = new StringBuilder( 512 );
        sb.append( "Elevationmodel as a J3D terrainModel: " );
        if( textureImage != null ){
            sb.append( " with textureImage and ");
        }
        sb.append( "Appearance: " ).append( getAppearance() ).append("\n" );
        return sb.toString();
    }

}