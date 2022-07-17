/*
 * Copyright (c) 2022.
 * By using this source code from this project/file you agree with the therms listed at
 * https://github.com/george2209/PlanesAndShips/blob/main/LICENSE
 */

package ro.gdi.canvas;

import java.util.ArrayList;

import ro.gdi.canvas.util.GameObjectArray;

public class GameObjectComponent implements GameObjectInterface{
    private final String iName;
    private final GameObjectMesh[] iMeshes;
    private final int SIZE;
    private int iInsertionPointer = -1;

    /**
     *
     * @param name the name of this component of the object.
     *             It shall be unique "per <i>GameObject</i>" in order to be able to quickly
     *             identify and locate it if you want to apply movements / rotation / animations for
     *             a certain component of a game object.
     */
    public GameObjectComponent(final String name, final int noOfMeshes) {
        this.iName = name;
        this.SIZE = noOfMeshes;
        if(noOfMeshes > 0)
            this.iMeshes = new GameObjectMesh[noOfMeshes];
        else
            this.iMeshes = null;
    }

    /**
     * adds a new mesh to this component
     * @param mesh
     */
    public void addMesh(final GameObjectMesh mesh){
        this.iMeshes[++iInsertionPointer] = mesh;
    }

    public boolean isFull(){
        return this.SIZE == this.iInsertionPointer + 1;
    }

    /**
     *
     * @return the last inserted component
     */
    public GameObjectMesh getLastComponent(){
        return this.iMeshes[iInsertionPointer];
    }


    public void draw(float[] viewMatrix, float[] projectionMatrix) {
        if(this.iMeshes != null)
            for (GameObjectMesh m:this.iMeshes) {
//            { //EXAMPLE:
//                //rotate it every 10 seconds
//                long time = SystemClock.uptimeMillis() % 10000L;
//                float angleInDegrees = (360.0f / 10000.0f) * ((int) time);
//                m.resetModelMatrix();
//                m.rotate(angleInDegrees, 0, 1, 0);
//            }
            m.draw(viewMatrix, projectionMatrix);
        }
    }

    public void onDestroy() {
        if(this.iMeshes != null)
            for (GameObjectMesh m:this.iMeshes) {
                m.destroy();
            }
    }

    public void onRestore() {
        if(this.iMeshes != null)
            for (GameObjectMesh m:this.iMeshes) {
                m.onRestore();
            }
    }
}
