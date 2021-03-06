/*
 * Copyright (c) 2022.
 * By using this source code from this project/file you agree with the therms listed at
 * https://github.com/george2209/PlanesAndShips/blob/main/LICENSE
 */

package ro.gdi.canvas.primitives;

import javax.microedition.khronos.opengles.GL10;

import ro.gdi.canvas.AbstractGameCanvan;
import ro.gdi.geometry.XYZVertex;

public class XYZPoint extends AbstractGameCanvan {

    private XYZVertex iCoordinate;

    public XYZPoint(XYZVertex coordinate){
        iCoordinate = coordinate;
        super.build(new XYZVertex[]{this.iCoordinate}, new int[]{0}, null);
    }

    @Override
    public void draw(float[] viewMatrix, float[] projectionMatrix) {
        super.doDraw(viewMatrix, projectionMatrix, GL10.GL_POINTS);
    }

    @Override
    public void onRestore() {
        super.build(new XYZVertex[]{this.iCoordinate}, new int[]{0}, null);
    }
}
