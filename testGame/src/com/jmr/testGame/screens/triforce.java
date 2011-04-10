/*
 * Jason Roth (jmroth79@gmail.com)
 * 
 * This is an openGL version of a rotating triforce lol, my friend asked me to do this, so I did.
 * 
 * Creates a Screen to test openGL with. Call a new Screen of type omgCubes to create this screen.
 * Flagging isRunning to true will exit this screen and move to the next screen in testGameExample.java.
 * 
 * Please use for educational purposes.
 */

package com.jmr.testGame.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;

public class triforce implements Screen {
	//if isRunning is true, this Screen will exit
	private boolean isRunning = false;
	//Generic Camera, is set to PerspectiveCamera in resize()
	private Camera camera;
	//our 3 triangle meshes to make the triforce with
	private Mesh tri1;
	private Mesh tri2;
	private Mesh tri3;
	//arrays for vertices and indices
	private float[] triVertices;
	private short[] triIndices;
	//used to rotate the triangles
	private float rotateTri;
	
	/* CONSTRUCTOR */
	public triforce(Application app) {
		//call resize to set Camera
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		//set up vertices for each triangle
		triVertices = new float[] {
				 0.0f,  1.0f, 0.0f, Color.toFloatBits(255, 215, 0, 255),	//TOP
				-1.0f, -1.0f, 0.0f,	Color.toFloatBits(255, 215, 0, 255),	//bottom left
				 1.0f, -1.0f, 0.0f,	Color.toFloatBits(255, 215, 0, 255)		//bottom right
		};
		//set up indices for each triangle
		triIndices = new short[] {
				0, 1, 2
		};
		//set up triangle one
		if (tri1 == null) {
			tri1 = new Mesh(true, 3, 3,
					new VertexAttribute(Usage.Position, 3, "a_position"),
					new VertexAttribute(Usage.ColorPacked, 4, "a_color"));
			//set vertices and indices based on our arrays
			tri1.setVertices(triVertices);
			tri1.setIndices(triIndices);
		}
		//set up triangle two
		if (tri2 == null) {
			tri2 = new Mesh(true, 3, 3,
					new VertexAttribute(Usage.Position, 3, "a_position"),
					new VertexAttribute(Usage.ColorPacked, 4, "a_color"));
			//set vertices and indices based on our arrays
			tri2.setVertices(triVertices);
			tri2.setIndices(triIndices);
		}
		//set up triangle three
		if (tri3 == null) {
			tri3 = new Mesh(true, 3, 3,
					new VertexAttribute(Usage.Position, 3, "a_position"),
					new VertexAttribute(Usage.ColorPacked, 4, "a_color"));
			//set vertices and indices based on our arrays
			tri3.setVertices(triVertices);
			tri3.setIndices(triIndices);
		}
	}

	@Override
	public void update(Application app) {
		//update rotation variable each update
		//if (Gdx.input.isKeyPressed(Keys.KEYCODE_DPAD_UP)) {
			rotateTri+=Gdx.graphics.getDeltaTime()*100;
		//}
	}

	@Override
	public void render(Application app) {
		camera.update();
		camera.apply(Gdx.gl10);
		//render triangle one
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        Gdx.gl10.glLoadIdentity();
        Gdx.gl10.glTranslatef(-1.0f, 0.0f, -6.0f);
        Gdx.gl10.glRotatef(rotateTri, 0.0f, 1.0f, 0.0f);
        tri1.render(GL10.GL_TRIANGLES, 0, 3);
        //render triangle two
        Gdx.gl10.glLoadIdentity();
        Gdx.gl10.glTranslatef(1.0f, 0.0f, -6.0f);
        Gdx.gl10.glRotatef(rotateTri, 0.0f, 1.0f, 0.0f);
        tri2.render(GL10.GL_TRIANGLES, 0, 3);
        //render triangle three
        Gdx.gl10.glLoadIdentity();
        Gdx.gl10.glTranslatef(0.0f, 2.0f, -6.0f);
        Gdx.gl10.glRotatef(rotateTri, 0.0f, -1.0f, 0.0f);
        tri3.render(GL10.GL_TRIANGLES, 0, 3);
	}

	@Override
	public boolean isDone() {
		return isRunning;
	}

	@Override
	public void dispose() {
		//always need to disposed meshes
		tri1.dispose();
		tri2.dispose();
		tri3.dispose();
	}
	
	public void resize(int width, int height) {
		float aspectRatio = (float) width / (float) height;
        camera = new PerspectiveCamera(67, 2f * aspectRatio, 2f);
	}

}
