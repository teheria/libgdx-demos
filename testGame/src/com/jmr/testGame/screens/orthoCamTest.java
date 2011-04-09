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

public class orthoCamTest implements Screen {
	/* boolean check for if this screen is currently running */
	private boolean isRunning = false;
	
    private Mesh squareMesh;
    private Mesh nearSquare;
    
    /* base Camera class, can be extended by OrthographicCamera or PrespectiveCamera */
    private Camera camera;
    
    private float movementIncrement = 0.006f;

    /* CONSTRUCTOR */
    public orthoCamTest(Application app) {
    	/* call resize on create to set up PrespectiveCamera */
    	resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        if (squareMesh == null) {
            squareMesh = new Mesh(true, 4, 4, 
                    new VertexAttribute(Usage.Position, 3, "a_position"),
                    new VertexAttribute(Usage.ColorPacked, 4, "a_color"));

            squareMesh.setVertices(new float[] {
                    -0.5f, -0.5f, -4, Color.toFloatBits(0, 255, 255, 255),		//V0
                    0.5f, -0.5f, -4, Color.toFloatBits(255, 0, 255, 255),		//V1
                    -0.5f, 0.5f, -4, Color.toFloatBits(0, 255, 0, 255),			//V2
                    0.5f, 0.5f, -4, Color.toFloatBits(0, 0, 255, 255)});		//V3
            squareMesh.setIndices(new short[] {2, 0, 3, 1});
        }

        if (nearSquare == null) {
            nearSquare = new Mesh(true, 4, 4, 
                    new VertexAttribute(Usage.Position, 3, "a_position"),
                    new VertexAttribute(Usage.ColorPacked, 4, "a_color"));

            nearSquare.setVertices(new float[] {
                    -1, -0.5f, -1.1f, Color.toFloatBits(255, 0, 0, 128),		//V0
                    0, -0.5f, -1.1f, Color.toFloatBits(255, 255, 0, 128),		//V1
                    -1, 0.5f, -1.1f, Color.toFloatBits(0, 255, 255, 0),			//V2
                    0, 0.5f, -1.1f, Color.toFloatBits(0, 0, 255, 0)});   		//V3
            nearSquare.setIndices(new short[] { 0, 1, 2, 3});
        }
    }

    @Override
    public void dispose() {
    	squareMesh.dispose();
    	nearSquare.dispose();
    }

    @Override
    public void render(Application app) {
    	//camera.rotate(0.1f, 0, 0, 0.5f);
    	//camera.translate(movementIncrement, 0, movementIncrement);
        camera.update();
        camera.apply(Gdx.gl10);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        squareMesh.render(GL10.GL_TRIANGLE_STRIP, 0, 4);
        nearSquare.render(GL10.GL_TRIANGLE_STRIP, 0, 4);
    }

    public void resize(int width, int height) {
    	//width and height sent in from desktopExample
        float aspectRatio = (float) width / (float) height;
        camera = new PerspectiveCamera(67, 2f * aspectRatio, 2f);
    }

	@Override
	public void update(Application app) {
		//when isRunning, main update will switch to next screen
		if (Gdx.input.isKeyPressed(Keys.KEYCODE_DPAD_DOWN)) {
			isRunning = true;
		}
		//rotate about z-axis clockwise
		else if (Gdx.input.isKeyPressed(Keys.KEYCODE_DPAD_RIGHT)) {
			camera.rotate(0.5f, 0, 0, 0.5f);
		}
		//rotate about z-axis counter-clockwise
		else if (Gdx.input.isKeyPressed(Keys.KEYCODE_DPAD_LEFT)) {
			camera.rotate(-0.5f, 0, 0, 0.5f);
		}
		//translate camera in positive x and z directions
		else if (Gdx.input.isKeyPressed(Keys.KEYCODE_W)) {
			camera.translate(movementIncrement, 0, movementIncrement);
		}
		//translate camera in negative x and z directions
		else if (Gdx.input.isKeyPressed(Keys.KEYCODE_S)) {
			camera.translate(-movementIncrement, 0, -movementIncrement);
		}
		//translate camera in negative x direction (left)
		else if (Gdx.input.isKeyPressed(Keys.KEYCODE_D)) {
			camera.translate(-movementIncrement, 0, 0);
		}
		//translate camera in positive x direction (right)
		else if (Gdx.input.isKeyPressed(Keys.KEYCODE_A)) {
			camera.translate(movementIncrement, 0, 0);
		}
	}

	@Override
	public boolean isDone() {
		return isRunning;
	}
}