/*
 * Copyright (c) 2022.
 * By using this source code from this project/file you agree with the therms listed at
 * https://github.com/george2209/PlanesAndShips/blob/main/LICENSE
 */

package ro.gdi.canvas;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.HashMap;

import ro.gdi.BuildConfig;

public abstract class AbstractWorldScene<T extends AbstractWorldCamera> {
    private HashMap<String,GameObject> iGameObjects = new HashMap<>();

    private final float NEAR_CAMERA_FIELD;
    private final float FAR_CAMERA_FIELD;
    private final float[] iProjectionMatrix = new float[16];
    private final T iCamera;

    public AbstractWorldScene(final T worldCamera,
                              final float NEAR_CAMERA_FIELD,
                              final float FAR_CAMERA_FIELD){
        this.iCamera = worldCamera;
        this.NEAR_CAMERA_FIELD = NEAR_CAMERA_FIELD;
        this.FAR_CAMERA_FIELD = FAR_CAMERA_FIELD;
    }

    public float getNearCameraField() {
        return NEAR_CAMERA_FIELD;
    }

    public float getFarCameraField() {
        return FAR_CAMERA_FIELD;
    }

    /**
     *
     * @return the currently used projection matrix
     */
    protected float[] getProjectionMatrix(){
        return this.iProjectionMatrix;
    }

    /**
     * set a new projection matrix
     * @param projectionMatrix a matrix from what the values will be copy into the "this" class
     *                         projection matrix.
     */
    protected void setProjectionMatrix(final float[] projectionMatrix){
        if(BuildConfig.DEBUG)
            if(projectionMatrix.length != 16)
                throw new AssertionError("projection matrix must be 16 elements long as it is a 4x4 matrix!");

        for (int i = 0; i < 16; i++) {
            this.iProjectionMatrix[i] = projectionMatrix[i];
        }
    }

    public T getCamera(){
        return this.iCamera;
    }

    /**
     * insert a new spirit into the world
     * @param gameObject the instance of the new spirit.
     */
    public void add(@NonNull final GameObject gameObject){
        if(BuildConfig.DEBUG){
            if(this.iGameObjects.get(gameObject.getObjectName()) != null)
                throw new AssertionError("an object with name " +
                        gameObject.getObjectName() + " already exists");
        }

        this.iGameObjects.put(gameObject.getObjectName(), gameObject);
    }

    /**
     * take care on rendering this world and all its objects and camera.
     * TODO: draw only when something was changed. Implement a "dirty" semaphore or notification
     */
    public void onDraw(){
        //call this to have the view matrix build depending on the camera movement
        //we can choose to call this only if the camera was moved
        //TODO: draw only when camera was moved!
        this.iCamera.onDraw();

        //draw all entities of the map on the projection clip
        for (final GameObject entity: this.iGameObjects.values()) {
            entity.draw(this.iCamera.getViewMatrix(), this.iProjectionMatrix);
        }
    }
}
