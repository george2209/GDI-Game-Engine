package ro.sg.avioane;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import ro.sg.avioane.cavans.GameTerrain;
import ro.sg.avioane.cavans.primitives.Line;
import ro.sg.avioane.cavans.primitives.XYZAxis;
import ro.sg.avioane.cavans.primitives.XYZPoint;
import ro.sg.avioane.game.TouchScreenProcessor;
import ro.sg.avioane.game.WorldCamera;
import ro.sg.avioane.game.WorldScene;
import ro.sg.avioane.geometry.XYZColor;
import ro.sg.avioane.geometry.XYZCoordinate;
import ro.sg.avioane.util.DebugUtils;
import ro.sg.avioane.util.MathGLUtils;

public class MainGameRenderer implements GLSurfaceView.Renderer {

    //put here some object for test:
    //private GameTerrain iGameTerrian = null;

    private XYZPoint iPoint = null;
    private XYZAxis iWorldAxis = null;
    private Line iMovingLine = null;

    private Bundle iPersistenceObject = null; //will be used later for persistence
    private final WorldCamera iCamera = new WorldCamera();
    private final WorldScene iWorld;
    private int iScreenWidth = 0;
    private int iScreenHeight = 0;

    private final TouchScreenProcessor iTouchProcessor = new TouchScreenProcessor();

    public MainGameRenderer() {
        super();
        this.iWorld = new WorldScene(this.iCamera);
    }


    private void addDrawObjects() {

        this.iWorldAxis = new XYZAxis();
        this.iWorld.add(this.iWorldAxis);

        {
            final XYZCoordinate coordinate = new XYZCoordinate(1.0f, 0.0f, 0.0f);
            coordinate.color = new XYZColor(1.0f, 1.0f, 1.0f, 1.0f);
            this.iPoint = new XYZPoint(coordinate);
            this.iCamera.setLookAtPosition(coordinate);
        }
        this.iWorld.add(this.iPoint);


        this.iMovingLine = new Line(
                new XYZCoordinate(0, 0, 0),
                new XYZCoordinate(5, 5, 5)
        );
        this.iWorld.add(this.iMovingLine);


        //this.iGameTerrian = new GameTerrain(50,3);
        //this.iWorld.add(this.iGameTerrian);
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        if (this.iPersistenceObject == null) {
            this.iPersistenceObject = new Bundle();
            // Set the background frame color
            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.8f);
            this.addDrawObjects();
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        this.iScreenWidth = width;
        this.iScreenHeight = height;
        GLES20.glViewport(0, 0, width, height);
        iWorld.doRecalibration(width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GL10.GL_COLOR_BUFFER_BIT |
                GL10.GL_DEPTH_BUFFER_BIT);
        this.iWorld.onDraw();
    }

    private boolean wasLine = false;

    public void onTouch(MotionEvent e) {
        final float x = e.getX();
        final float y = e.getY();

        if(wasLine) {
            this.iCamera.doMoveCamera(x, y);
        } else {


            //final float[] touchedVector = this.iTouchProcessor.processScreenTouch(x, y, this.iScreenWidth, this.iScreenHeight,
            //        this.iWorld.getProjectionMatrix(),
            //        this.iCamera.getViewMatrix());


            final float[] touchedVector = this.iWorld.onTouch(e);

            System.out.println("\n\n--------touchedVector:--------------\n\n");



            final XYZCoordinate cameraPosition = this.iCamera.getCameraPosition();
            final float[] cameraPositionArr = cameraPosition.asArray();
            final float[] p1 = cameraPositionArr;//getPointOnVector(touchedVector, cameraPositionArr, 0.0f);
            final float[] p2 = getPointOnVector(touchedVector, cameraPositionArr, 10.0f);

            System.out.println("\n\n--------touchedVector END:--------------\n\n");



            this.iMovingLine.updateCoordinates(
                    new XYZCoordinate(p1),
                    new XYZCoordinate(p2)
            );
            wasLine = true;
        }


    }

    private float[] getPointOnVector(float[] vector, final float[] camera, float length) {
        final float[] pointOnVector = MathGLUtils.matrixMultiplyWithValue(vector, length);
        return MathGLUtils.matrixAddMatrix(camera, pointOnVector);
    }

//    private void tmpComputeIntoWorldCoordinates(final float x, final float y){
//        float xOrigin = (float)this.iScreenWidth / 2.0f;
//        float yOrigin = (float)this.iScreenHeight / 2.0f;
//
//        float newX = (x - xOrigin) / xOrigin;
//        float newY = (y - yOrigin) / yOrigin;
//
//        System.out.println("newX=" + newX + " newY=" + newY);
//    }

}
