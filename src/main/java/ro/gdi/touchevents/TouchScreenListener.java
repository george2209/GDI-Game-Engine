/*
 * Copyright (c) 2022.
 * By using this source code from this project/file you agree with the therms listed at
 * https://github.com/george2209/PlanesAndShips/blob/main/LICENSE
 */

package ro.gdi.touchevents;

public interface TouchScreenListener {
    public void fireMovement(final float xPercent, final float zPercent);
    public void fireTouchClick(final float[] clickVector);
    public void fireZoom(final float zoomingFactor);
}
