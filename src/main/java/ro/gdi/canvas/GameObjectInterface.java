/*
 * Copyright (c) 2022.
 * By using this source code from this project/file you agree with the therms listed at
 * https://github.com/george2209/PlanesAndShips/blob/main/LICENSE
 */

package ro.gdi.canvas;

/**
 * calls to this class methods shall be made only on the OpenGL thread
 */
public interface GameObjectInterface {
    public void draw(float[] viewMatrix, float[] projectionMatrix);
    public void onDestroy();
    public void onRestore();
}
