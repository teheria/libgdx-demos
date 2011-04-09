package com.jmr.testGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;

public class artStuff {
	
	private static Mesh myMesh;
	private static Texture myTexture;
	
	public void create() {
		if (myMesh == null) {
            myMesh = new Mesh(true, 3, 3, 
                    new VertexAttribute(Usage.Position, 3, "a_position"),
                    new VertexAttribute(Usage.ColorPacked, 4, "a_color"),
                    new VertexAttribute(Usage.TextureCoordinates, 2, "a_textCoords"));
                    
            myMesh.setVertices(new float[] { -0.5f, -0.5f, 0, Color.toFloatBits(255, 0, 0, 255), 0, 1,	//V0
                                            0.5f, -0.5f, 0, Color.toFloatBits(0, 255, 0, 255), 1, 1,	//V1
                                            0, 0.5f, 0, Color.toFloatBits(0, 0, 255, 255), 0.5f, 0});		//V2                          
            myMesh.setIndices(new short[] { 0, 1, 2 });
            FileHandle imageFileHandle = Gdx.files.internal("data/samus1.jpg");
            myTexture = new Texture(imageFileHandle);
        }
	}
	
	public void render() {
		Gdx.graphics.getGL10().glEnable(GL10.GL_TEXTURE_2D);
		myTexture.bind();
		myMesh.render(GL10.GL_TRIANGLES, 0, 3);
	}

}