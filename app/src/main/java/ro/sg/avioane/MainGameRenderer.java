/*
 * Copyright (c) 2021.
 * By using this source code from this project/file you agree with the therms listed at
 * https://github.com/george2209/PlanesAndShips/blob/main/LICENSE
 */

package ro.sg.avioane;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import ro.sg.avioane.cavans.primitives.Square;
import ro.sg.avioane.cavans.primitives.XYZAxis;
import ro.sg.avioane.game.TouchScreenListener;
import ro.sg.avioane.game.TouchScreenProcessor;
import ro.sg.avioane.game.WorldCamera;
import ro.sg.avioane.game.WorldScene;
import ro.sg.avioane.geometry.XYZColor;
import ro.sg.avioane.geometry.XYZCoordinate;
import ro.sg.avioane.geometry.XYZVertex;
import ro.sg.avioane.geometry.XYZTexture;
import ro.sg.avioane.util.MathGLUtils;
import ro.sg.avioane.util.OpenGLProgramUtils;
import ro.sg.avioane.util.OpenGLUtils;
import ro.sg.avioane.util.TextureUtils;

public class MainGameRenderer implements GLSurfaceView.Renderer, TouchScreenListener {

    private final Context iContext;

    //put here some object for test:
    //private GameTerrain iGamePlane = null;

    //private XYZPoint iPoint = null;
    private XYZAxis iWorldAxis = null;
    //private Line iMovingLine = null;
    private Square iSquare = null;

    //private Bundle iPersistenceObject = null; //will be used later for persistence
    //private boolean isGLContext = false;

    private final WorldCamera iCamera = new WorldCamera();
    private final WorldScene iWorld;
    private int iScreenWidth = 0;
    private int iScreenHeight = 0;

    private final TouchScreenProcessor iTouchProcessor = new TouchScreenProcessor();

    public MainGameRenderer(Context context) {
        this.iContext = context;
        this.iWorld = new WorldScene(this.iCamera);
        this.iTouchProcessor.addTouchScreenListener(this);

//        final XYZVertex t1 = MathGLUtils.getTriangleNormal(
//                new XYZVertex(-113.748047f, -13.510292f, 250.230896f),
//                new XYZVertex(-111.487404f, -45.606571f, 225.793869f),
//                new XYZVertex(-123.862007f, -14.288765f, 244.758011f)
//        );
//
//        final XYZVertex t2 = MathGLUtils.getTriangleNormal(
//                new XYZVertex(-113.748047f, -13.510292f, 250.230896f),
//                new XYZVertex(-98.904160f, -44.577564f, 230.430054f),
//                new XYZVertex(-111.487404f, -45.606571f, 225.793869f)
//        );
//
//        final XYZVertex t3 = MathGLUtils.getTriangleNormal(
//                new XYZVertex(-98.904160f, -44.577564f, 230.430054f),
//                new XYZVertex(-113.748047f, -13.510292f, 250.230896f),
//                new XYZVertex(-113.202438f, -13.474716f, 250.301163f)
//        );
//
//        final XYZVertex nor = new XYZVertex(
//                MathGLUtils.matrixNormalize(MathGLUtils.add(
//                        MathGLUtils.add(t1, t2), t3
//                ).asArray()));
//
//        System.out.println("\n\n\n*******************************************");
//        System.out.println("Nx=" + nor.x() + " Ny=" + nor.y() + " Nz=" + nor.z());
//        System.out.println("\n\n\n*******************************************");
    }

    private void addDrawObjects() {

        System.out.println("************addDrawObjects*********************");

        this.iWorldAxis = new XYZAxis();
        this.iWorld.add(this.iWorldAxis);

        /*{
            final XYZVertex coordinate = new XYZVertex(1.0f, 0.0f, 0.0f);
            coordinate.color = new XYZColor(1.0f, 1.0f, 1.0f, 0.0f);
            this.iPoint = new XYZPoint(coordinate);
            this.iCamera.setLookAtPosition(coordinate);
        }
        this.iWorld.add(this.iPoint);*/


//        this.iMovingLine = new Line(
//                new XYZVertex(0, 0, 0),
//                new XYZVertex(5, 5, 5),
//                new XYZColor(0.9f,0.9f,0.9f,1.0f) //white = 1,1,1,1
//        );
//        this.iWorld.add(this.iMovingLine);
//
//
//        this.iGamePlane = new GameTerrain(100,100);
//        this.iWorld.add(this.iGamePlane);




        final XYZVertex leftUpperSquare = new XYZVertex( new XYZCoordinate(0,0,0));
        leftUpperSquare.color = new XYZColor(1,0,0,1);
        leftUpperSquare.texture = new XYZTexture(0.0f, 0.0f, "test", this.iContext);
        this.iSquare = new Square( leftUpperSquare, 10);
        this.iWorld.add(this.iSquare);
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        boolean isRestoreNeeded = TextureUtils.getInstance().onDestroy();
        isRestoreNeeded |= OpenGLProgramUtils.getInstance().onDestroy();
        if(isRestoreNeeded && this.iWorld.count() > 0){
            System.out.println("A restore of the world is needed***");
            this.iWorld.onRestoreWorld();
        } else {
            System.out.println("Build world is needed***");
            this.addDrawObjects();
        }
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.8f);



//        if (!this.isGLContext) {
//            this.isGLContext = true;
//            // Set the background frame color
//            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.8f);
//            this.addDrawObjects();
//        } else if(BuildConfig.DEBUG) {
//                throw new AssertionError("this.isGLContext ************ALREADY LOADED??????");
//        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        this.iScreenWidth = width;
        this.iScreenHeight = height;
        GLES20.glViewport(0, 0, width, height);
        this.iTouchProcessor.doRecalibration(this.iScreenWidth, this.iScreenHeight);
        iWorld.doRecalibration(width, height);

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GL10.GL_COLOR_BUFFER_BIT |
                GL10.GL_DEPTH_BUFFER_BIT);
        this.iWorld.onDraw();
    }



    public void onTouch(MotionEvent e) {
        this.iWorld.onTouch(e, this.iTouchProcessor);
    }


    @Override
    public void fireMovement(float xPercent, float zPercent) {
        final XYZCoordinate cameraPosition = this.iCamera.getCameraPosition();
        final float ratio = TouchScreenProcessor.TOUCH_MOVEMENT_ACCELERATION_FACTOR * (float) this.iScreenWidth / (float) this.iScreenHeight;

        //System.out.println("xPercent=" + xPercent + " zPercent=" + zPercent);
        cameraPosition.setX( cameraPosition.x() - (ratio/100.0f) * xPercent * 2.0f) ;
        cameraPosition.setZ( cameraPosition.z() - (ratio/100.0f) * zPercent );
        this.iCamera.setCameraPosition(cameraPosition);

        final XYZCoordinate lookAtPosition = this.iCamera.getiLookAtPosition();
        lookAtPosition.setX( lookAtPosition.x() - (ratio/100.0f) * xPercent * 2.0f );
        lookAtPosition.setZ( lookAtPosition.z() - (ratio/100.0f) * zPercent );
        this.iCamera.setLookAtPosition(lookAtPosition);

    }

    @Override
    public void fireTouchClick(final float[] clickVector) {
        final float[] p1 = this.iCamera.getCameraPosition().asArray();
        final float[] p2 = MathGLUtils.getPointOnVector(clickVector, p1, 50.0f);

        final XYZVertex p1Line = new XYZVertex(new XYZCoordinate(p1));
        final XYZVertex p2Line = new XYZVertex(new XYZCoordinate(p2));
        p1Line.color = new XYZColor(1.0f, 0 , 0.0f, 1);
        p2Line.color = new XYZColor(1.0f, 0 , 0.0f, 1);

//        this.iMovingLine.updateCoordinates(
//                    p1Line,
//                    p2Line
//        );
//
//        this.iGamePlane.processClickOnObject(this.iCamera.getCameraPosition(), new XYZVertex(clickVector));

    }

    /**
     *
     * @param zoomingFactor the zoom factor in percent as real subunit number in range [0..1]
     */
    @Override
    public void fireZoom(float zoomingFactor) {
        //System.out.println("ZOOM FACTOR=" + zoomingFactor);
        final XYZCoordinate cameraPosition = this.iCamera.getCameraPosition();
        cameraPosition.setY(cameraPosition.y()*(1-zoomingFactor));
        this.iCamera.setCameraPosition(cameraPosition);
    }

//    protected void onPause(){
//        this.isGLContext = false;
//        this.iWorld.clear();
//        TextureUtils.getInstance().onDestroy();
//        OpenGLProgramUtils.getInstance().onDestroy();
//        //this.iWorld.onPause();
//    }

}
