/*
 * Copyright (c) 2022.
 * By using this source code from this project/file you agree with the therms listed at
 * https://github.com/george2209/PlanesAndShips/blob/main/LICENSE
 */

package ro.gdi.canvas.primitives;

import android.opengl.GLES30;

import ro.gdi.canvas.GameObject;
import ro.gdi.canvas.GameObjectComponent;
import ro.gdi.canvas.GameObjectMesh;
import ro.gdi.geometry.XYZColor;
import ro.gdi.geometry.XYZCoordinate;
import ro.gdi.geometry.XYZVertex;

public class Line extends GameObject
{
    private static class LineComponent extends GameObjectComponent {
        private final LineMesh iLineMesh;
        public LineComponent(final XYZCoordinate start, final XYZCoordinate end, final XYZColor color) {
            super("line component", 1);
            final XYZVertex[] verticesArray = new XYZVertex[2];
            verticesArray[0] = new XYZVertex(start, color);
            verticesArray[1] = new XYZVertex(end, color);
            iLineMesh = new LineMesh(verticesArray, new int[] {0,1}, GLES30.GL_LINES);
            super.addMesh(iLineMesh);
        }

        public void refreshCoordinates(final XYZCoordinate start, final XYZCoordinate end, final XYZColor color){
            final XYZVertex[] verticesArray = new XYZVertex[2];
            verticesArray[0] = new XYZVertex(start, color);
            verticesArray[1] = new XYZVertex(end, color);
            this.iLineMesh.refreshCoordinates(verticesArray);
        }
    }

    private static class LineMesh extends GameObjectMesh {
        public LineMesh(XYZVertex[] verticesArray, int[] indexDrawOrder, int glFormType) {
            super(verticesArray, indexDrawOrder, glFormType);
        }

        public void refreshCoordinates(final XYZVertex[] verticesArray){
            super.buildVertexBuffer(verticesArray);
        }
    }

    private final XYZCoordinate iStart;
    private final XYZCoordinate iEnd;
    private final XYZColor iColor;
    private final LineComponent iLineComponent;
    private boolean iIsLineVisible = false;
    private boolean iIsLineDirty = false;

    /**
     *
     * @param start
     * @param end
     */
    public Line(final XYZCoordinate start, final XYZCoordinate end) {
        this(start, end, new XYZColor(0.5f, 0.1f, 0.1f, XYZColor.OPAQUE));
    }

    /**
     *
     * @param start
     * @param end
     * @param color
     */
    public Line(final XYZCoordinate start, final XYZCoordinate end, final XYZColor color) {
        super("line", 1);
        this.iStart = start;
        this.iEnd = end;
        this.iColor = color;
        this.iLineComponent = new LineComponent(this.iStart, this.iEnd, this.iColor);
        super.addComponent(this.iLineComponent);
    }

    public void setVisible(final boolean isVisible){
        this.iIsLineVisible = isVisible;
    }

    public void updateCoordinates(final XYZCoordinate start, final XYZCoordinate end){
        this.iStart.setX(start.x());
        this.iStart.setY(start.y());
        this.iStart.setZ(start.z());

        this.iEnd.setX(end.x());
        this.iEnd.setY(end.y());
        this.iEnd.setZ(end.z());

        iIsLineDirty = true;
    }

    @Override
    public void draw(float[] viewMatrix, float[] projectionMatrix) {
        if(this.iIsLineVisible) {
            if(iIsLineDirty){
                this.iIsLineDirty = false;
                this.iLineComponent.refreshCoordinates(this.iStart, this.iEnd, this.iColor);
            }
            super.draw(viewMatrix, projectionMatrix);
        }
    }
}
