/*
 * Copyright (c) 2022.
 * By using this source code from this project/file you agree with the therms listed at
 * https://github.com/george2209/PlanesAndShips/blob/main/LICENSE
 */

package ro.gdi.canvas;

import java.util.ArrayList;

public class GameObject implements GameObjectInterface{
    private final String iObjName;
    private final GameObjectComponent[] iComponents;
    private final int SIZE;
    private int iInsertionPointer = -1;


    /**
     *
     * @param objName the name of this object.
     */
    public GameObject(final String objName, final int noOfChild) {
        this.iObjName = objName;
        this.SIZE = noOfChild;
        if(noOfChild > 0)
            this.iComponents = new GameObjectComponent[noOfChild];
        else
            this.iComponents = null;
    }

    /**
     * adds a component into the game object.
     * @param component the component (part of the object) that shall be added.
     *                  see "ProjectDocumentation" inside docs for more information
     */
    public void addComponent(final GameObjectComponent component){
        this.iComponents[++iInsertionPointer] = component;
    }

    /**
     * used by importer from Blender..
     * TODO: get rid of this method.
     * @return the last inserted component
     */
    public GameObjectComponent getLastComponent(){
        return this.iComponents[iInsertionPointer];
    }

    public boolean isFull(){
        return this.SIZE == this.iInsertionPointer + 1;
    }


    /**
     * @return the name of the object as it comes from Blender or as it was set at the initialize
     * time (in case of objects not imported from Blender)
     */
    public String getObjectName() {
        return this.iObjName;
    }

    /**
     *
     * @param viewMatrix the view matrix
     * @param projectionMatrix the projection matrix
     */
    public void draw(float[] viewMatrix, float[] projectionMatrix) {
        if(this.iComponents !=null){
            for (GameObjectComponent c:iComponents) {
                c.draw(viewMatrix, projectionMatrix);
            }
        }

    }

    /**
     * from GameObjectInterface
     */
    public void onDestroy() {
        if(this.iComponents !=null){
            for (GameObjectComponent c:iComponents) {
                c.onDestroy();
            }
        }
    }

    /**
     * from GameObjectInterface
     */
    public void onRestore() {
        if(this.iComponents !=null){
            for (GameObjectComponent c:iComponents) {
                c.onRestore();
            }
        }
    }
}
