/*
 * Jason Roth (jmroth79@gmail.com)
 * 
 * This is an implementation of NeHe lesson 5 - http://nehe.gamedev.net/data/lessons/lesson.asp?lesson=05
 * 
 * Creates a Screen to test openGL with. Call a new Screen of type omgCubes to create this screen.
 * Flagging isRunning to true will exit this screen and move to the next screen in testGameExample.java.
 * 
 * Please use for educational purposes.
 */


package com.jmr.testGame.screens;

import java.nio.Buffer;

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

public class omgCubes implements Screen {
	//if isRunning is true, this Screen will exit
	private boolean isRunning = false;
	//Generic Camera, is set to PerspectiveCamera in resize()
	private Camera myCam;
	//an openGL mesh
	private Mesh pyramid;
	//an openGL mesh
	private Mesh cube;
	//used to rotate the pyramid
	private float rotatePyramid;
	//used to rotate the cube
	private float rotateCube;
	
	
	/* CONSTRUCTOR */
	public omgCubes(Application app) {
		//call resize to set Camera
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		if (pyramid == null) {
			pyramid = new Mesh(true, 5, 12,
					new VertexAttribute(Usage.Position, 3, "a_position"),
                    new VertexAttribute(Usage.ColorPacked, 4, "a_color"));
		
			//set x, y, z, vertices and set Colors red, green, blue, alpha
			pyramid.setVertices(new float[] {
					 0.0f,  1.0f,  0.0f,  Color.toFloatBits(255, 0, 0, 255),		//V0
					-1.0f, -1.0f,  1.0f,  Color.toFloatBits(0, 255, 0, 255),		//V1
					 1.0f, -1.0f,  1.0f,  Color.toFloatBits(0, 0, 255, 255),		//V2
					 1.0f, -1.0f, -1.0f,  Color.toFloatBits(0, 255, 0, 255),		//V3
					-1.0f, -1.0f, -1.0f,  Color.toFloatBits(0, 0, 255, 255)			//V4
			});
			//links the indices to draw
			pyramid.setIndices(new short[] {
					0, 1, 2,	//Defines Front Face
					0, 3, 2,	//Defines Right Face
					0, 3, 4,	//Defines Back Face
					0, 1, 4,	//Defines Left Face
			});
		}
		
		if (cube == null) {
			cube = new Mesh(true, 8, 36,
					new VertexAttribute(Usage.Position, 3, "a_position"),
					new VertexAttribute(Usage.ColorPacked, 4, "a_color"));
			
			//set x, y, z, vertices and set Colors red, green, blue, alpha
			cube.setVertices(new float[] {
					-1.0f,  1.0f,  1.0f, Color.toFloatBits(255, 0, 0, 255),		//V0
					 1.0f,  1.0f,  1.0f, Color.toFloatBits(255, 0, 0, 255),		//V1
					 1.0f, -1.0f,  1.0f, Color.toFloatBits(255, 0, 0, 255),	//V2
					-1.0f, -1.0f,  1.0f, Color.toFloatBits(255, 0, 0, 255),	//V3
					-1.0f,  1.0f, -1.0f, Color.toFloatBits(255, 255, 255, 255),		//V4
					 1.0f,  1.0f, -1.0f, Color.toFloatBits(255, 255, 255, 255),		//V5
					 1.0f, -1.0f, -1.0f, Color.toFloatBits(255, 255, 255, 255),	//V6
					-1.0f, -1.0f, -1.0f, Color.toFloatBits(255, 255, 255, 255)		//V7
			});
			
			//links the indices to draw
			cube.setIndices(new short[] {
					0, 1, 5,	//half of top face
					0, 5, 4,	//other half of top face
					
					4, 6, 5,	//half of front face
					4, 6, 7,	//other half of front face
					
					0, 1, 2,	//half of back face
					0, 2, 3,	//other half of back face
					
					1, 2, 5,	//half of right face
					2, 5, 6,	//other half of right face
					
					0, 3, 4,	//half of left face
					7, 4, 3,	//other half of left face
					
					3, 6, 2,	//half of bottom face
					6, 7, 3		//other half of bottom face
			});
		}
	}

	@Override
	public void update(Application app) {
		//update rotation variable pyramid
		rotatePyramid+=Gdx.graphics.getDeltaTime()*100;
		//update rotation variable for cube
		rotateCube+=Gdx.graphics.getDeltaTime()*100;
		
		//rotate about y-axis clockwise
		if (Gdx.input.isKeyPressed(Keys.KEYCODE_DPAD_RIGHT)) {
			myCam.rotate(0.5f, 0, 0.1f, 0);
		}
		//rotate about y-axis counter-clockwise
		else if (Gdx.input.isKeyPressed(Keys.KEYCODE_DPAD_LEFT)) {
			myCam.rotate(-0.5f, 0, 0.1f, 0);
		}
		//translate in position z-axis (into)
		else if (Gdx.input.isKeyPressed(Keys.KEYCODE_W)) {
			myCam.translate(0, 0, 0.005f);
		}
		//translate in negative z-axis (out of)
		else if (Gdx.input.isKeyPressed(Keys.KEYCODE_S)) {
			myCam.translate(0, 0, -0.005f);
		}
	}

	@Override
	public void render(Application app) {
		myCam.update();
        myCam.apply(Gdx.gl10);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        Gdx.gl10.glLoadIdentity();
        Gdx.gl10.glTranslatef(-1.5f, 0.0f, -6.0f);
        Gdx.gl10.glRotatef(rotatePyramid, 0.0f, 1.0f, 0.0f);
        pyramid.render(GL10.GL_TRIANGLE_STRIP, 0, 12);
        Gdx.gl10.glLoadIdentity();
        Gdx.gl10.glTranslatef(1.5f, 0.0f, -7.0f);
        Gdx.gl10.glRotatef(rotateCube, 1.0f, 1.0f, 1.0f);
        cube.render(GL10.GL_TRIANGLE_STRIP, 0, 36);
	}

	@Override
	public boolean isDone() {
		//
		return isRunning;
	}

	@Override
	public void dispose() {
		//always need to disposed meshes
		pyramid.dispose();
		cube.dispose();
	}
	
	public void resize(int width, int height) {
		float aspectRatio = (float) width / (float) height;
        myCam = new PerspectiveCamera(67, 2f * aspectRatio, 2f);
	}
}
