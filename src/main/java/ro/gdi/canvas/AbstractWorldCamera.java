/*
 * Copyright (c) 2022.
 * By using this source code from this project/file you agree with the therms listed at
 * https://github.com/george2209/PlanesAndShips/blob/main/LICENSE
 */

package ro.gdi.canvas;

import android.opengl.Matrix;

import ro.gdi.geometry.XYZCoordinate;

public class AbstractWorldCamera {
    private final float[] iViewMatrix = new float[16];
    private final XYZCoordinate iCameraPosition = new XYZCoordinate(0.0f,50.0f,-5.0f);
    private final XYZCoordinate iLookAtPosition = new XYZCoordinate(0.0f,0.0f,0.0f);
    private final XYZCoordinate iCameraUpPosition = new XYZCoordinate(0.0f,1.0f,0.0f);

    public AbstractWorldCamera(final XYZCoordinate cameraPosition,
                               final XYZCoordinate lookAtPosition,
                               final XYZCoordinate cameraUpPosition){
        this.iCameraPosition.setX(cameraPosition.x());
        this.iCameraPosition.setY(cameraPosition.y());
        this.iCameraPosition.setZ(cameraPosition.z());

        this.iLookAtPosition.setX(lookAtPosition.x());
        this.iLookAtPosition.setY(lookAtPosition.y());
        this.iLookAtPosition.setZ(lookAtPosition.z());

        this.iCameraUpPosition.setX(cameraUpPosition.x());
        this.iCameraUpPosition.setY(cameraUpPosition.y());
        this.iCameraUpPosition.setZ(cameraUpPosition.z());
    }

    /**
     * Sets the x,y,z of the camera vector. Consider the camera the point where "you" will look at the
     * scene.
     * @param cameraPosition the new camera position against the scene
     */
    public void setCameraPosition(final XYZCoordinate cameraPosition){
        this.iCameraPosition.setX(cameraPosition.x());
        this.iCameraPosition.setY(cameraPosition.y());
        this.iCameraPosition.setZ(cameraPosition.z());
        System.out.println("new camera position x=" + this.iCameraPosition.x() + " y=" + this.iCameraPosition.y() + " z=" + this.iCameraPosition.z());
    }

    /**
     *
     * @return the camera position x,y,z coordinates
     */
    public XYZCoordinate getCameraPosition(){
        return this.iCameraPosition;
    }

    /**
     * Sets the x,y,z of the center vector where the camera is pointing to on the far clip (
     * world scene).
     * @param position the pointing vector coordinates against the scene
     */
    public void setLookAtPosition(final XYZCoordinate position){
        this.iLookAtPosition.setX(position.x());
        this.iLookAtPosition.setY(position.y());
        this.iLookAtPosition.setZ(position.z());
    }

    /**
     *
     * @return a vector where the camera is pointing to
     */
    public XYZCoordinate getLookAtPosition(){
        return this.iLookAtPosition;
    }

    /**
     * execute the "draw" event
     */
    public void onDraw(){
        //System.out.println("Camera position: x=" + this.iCameraPosition.x + " y=" + this.iCameraPosition.y + " z=" + this.iCameraPosition.z);
        Matrix.setIdentityM(iViewMatrix,0);
        Matrix.setLookAtM(this.iViewMatrix, 0, iCameraPosition.x(), iCameraPosition.y(), iCameraPosition.z(),
                iLookAtPosition.x(), iLookAtPosition.y(), iLookAtPosition.z(),
                iCameraUpPosition.x(), iCameraUpPosition.y(), iCameraUpPosition.z());

    }

    /**
     *
     * @return the view matrix with the camera position set.
     */
    public float[] getViewMatrix(){
        return this.iViewMatrix;
    }
}
