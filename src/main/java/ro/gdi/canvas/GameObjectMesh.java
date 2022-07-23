/*
 * Copyright (c) 2022.
 * By using this source code from this project/file you agree with the therms listed at
 * https://github.com/george2209/PlanesAndShips/blob/main/LICENSE
 */

package ro.gdi.canvas;

import ro.gdi.geometry.XYZMaterial;
import ro.gdi.geometry.XYZVertex;

/**
 * the reason behind this class is to have a collection of meshes inside each object that extends
 * AbstractGameCanvan that will contain <i>separated by material</i> each characteristics for a
 * part of a graphical object that has an unique material attached.
 * Often we have a graphical object having more sub-parts, each sub-part with its own material.
 * Imagine a tank that has a turret that has its own material distinct from the tank main body.
 * In this case we know exactly what are the vertices and normals associated with that sub-part and
 * what kind of material will be used for this sub-part.
 *
 * Reason for creating this class:
 * 1. To be able to separate parts of a graphical object on sub-part, each sub-part with its
 * own unique material.
 * 2. To be able to manipulate parts of the objects in a separate way from the <i>main body</i>.
 */
public class GameObjectMesh extends AbstractGameCanvan {
    private final XYZVertex[] iVerticesArray;
    private final int[] iIndexDrawOrder;
    private final int GL_FORM_TYPE;
    private final XYZMaterial iMaterial;
    private boolean iIsDirty = false;


    /**
     *
     * @param verticesArray the vertices array
     * @param indexDrawOrder the draw order array
     * @param material the material used for this mesh
     * @param glFormType a GL_* type. Example: GLES30.GL_TRIANGLES
     */
    public GameObjectMesh(final XYZVertex[] verticesArray,
                          final int[] indexDrawOrder,
                          final XYZMaterial material,
                          final int glFormType){
        this.iMaterial = material;
        this.iVerticesArray = verticesArray;
        this.iIndexDrawOrder = indexDrawOrder;
        this.GL_FORM_TYPE = glFormType;
        super.build(verticesArray, indexDrawOrder, material);
    }

    /**
     *
     * @param verticesArray the vertices array
     * @param indexDrawOrder the draw order array
     * @param glFormType a GL_* type. Example: GLES30.GL_TRIANGLES
     */
    public GameObjectMesh(final XYZVertex[] verticesArray,
                          final int[] indexDrawOrder,
                          final int glFormType){
        this(verticesArray, indexDrawOrder,null, glFormType);
    }

    /**
     *
     * @return the number of vertices this mesh contains
     */
    public int getVerticesSize(){
        return this.iVerticesArray.length;
    }

    /**
     *
     * @param index
     * @return the instance of the respective vertex
     */
    public XYZVertex getVertex(int index){
        assert (index > -1 && index < this.iVerticesArray.length);
        return this.iVerticesArray[index];
    }

    protected void notifyVerticesChanged(){
        iIsDirty = true;
    }

    /**
     *
     * @param viewMatrix the view matrix
     * @param projectionMatrix the projection matrix
     */
    @Override
    public void draw(float[] viewMatrix, float[] projectionMatrix) {
        if(this.iIsDirty) {
            this.iIsDirty = false;
            super.buildVertexBuffer(this.iVerticesArray);
        }
        super.doDraw(viewMatrix, projectionMatrix, this.GL_FORM_TYPE);
    }

    @Override
    public void onRestore() {
        super.build(this.iVerticesArray, this.iIndexDrawOrder, this.iMaterial);
    }
}
