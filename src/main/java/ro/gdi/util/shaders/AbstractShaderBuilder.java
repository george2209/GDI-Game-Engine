/*
 * Copyright (c) 2022.
 * By using this source code from this project/file you agree with the therms listed at
 * https://github.com/george2209/PlanesAndShips/blob/main/LICENSE
 */

package ro.gdi.util.shaders;

import androidx.annotation.CallSuper;


public abstract class AbstractShaderBuilder {
    protected final StringBuilder iStrShaderBody = new StringBuilder();
    protected final StringBuilder iStrShaderDeclarations = new StringBuilder();

    protected boolean isStreamEditable = true;

    public AbstractShaderBuilder(){
        iStrShaderBody.append("void main() {\n");
    }




    /**
     *
     * @return the complete shader code as String.
     */
    @CallSuper
    public String asString(){
        if(this.isStreamEditable) {
            this.isStreamEditable = false;
            iStrShaderBody.append("\t}\n");
        }
        return this.iStrShaderDeclarations.toString().concat(iStrShaderBody.toString());
    }

    // /**
    // * @param shaderType
    // */
    //public abstract AbstractShaderBuilder withBackground(final int shaderType);

    public abstract AbstractShaderBuilder build(final int shaderType);
}
