/*
 * Copyright (c) 2022.
 * By using this source code from this project/file you agree with the therms listed at
 * https://github.com/george2209/PlanesAndShips/blob/main/LICENSE
 */

package ro.gdi.canvas.blender.collada;

public class ColladaFileObjectDescriptor {

    public ColladaFileObjectDescriptor(final String fileName, final String objectName)
    {
        this.fileName = fileName;
        this.objectName = objectName;
    }

    /**
     * fileName the .dae Collada file inside <code>/assets/obj/</code>
     */
    public String fileName;

    /**
     * objectNames the name attached to the created object. Example "small_tree".
     */
    public String objectName;
}
