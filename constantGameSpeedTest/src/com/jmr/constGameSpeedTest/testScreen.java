/*
 * Jason Roth (jmroth79@gmail.com)
 * 
 * This is to show an example of constant game speed, by locking in the FPS & UPS. The speed will be dependent on the
 * aspect ratio. For this example, I have set the world units to 48x32 meters (1.5 aspect ratio). If it's less than this,
 * the images will be squished a little bit.
 * 
 * NOTE - this example does not yet account for aspect ratios that are above 1.5, like 1.66 for example. In that
 * case you have two options. Create assets based on different aspect ratios, or fill in the size difference on the ends
 * of the screen with black bars.
 * 
 * Please use for educational purposes.
 */

package com.jmr.constGameSpeedTest;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;

public class testScreen implements Screen {
	//the devices width and height
	private float deviceWidth;
	private float deviceHeight;
	//the devices aspectRatio
	private float aspectRatio;
	//the modified game width
	private float gameWidth;

	private Camera camera;
	private Mesh mesh;
	private float meshRotate;
	private float meshTranslate;
	//x-direction unit vector
	private float meshDirection = 1;

	//used for debugging
	private long rightEnd = 0;
	private long leftEnd = 0;

	public testScreen (Application app) {
		//get device width and height
		deviceWidth = Gdx.graphics.getWidth();
		deviceHeight = Gdx.graphics.getHeight();
		//find aspect ratio (this example is in landscape mode)
		aspectRatio = deviceWidth / deviceHeight;
		gameWidth = 32*aspectRatio;

		if (aspectRatio < 1.5f) {
			gameWidth = 48;
		}
		//set our camera in world units, gameWidth x 32 meters
		camera = new OrthographicCamera(gameWidth, 32);
		//center camera
		camera.translate(gameWidth / 2, 32 / 2, 0);
		camera.update();

		if (mesh == null) {
            mesh = new Mesh(true, 4, 4,
                    new VertexAttribute(Usage.Position, 3, "a_position"),
                    new VertexAttribute(Usage.ColorPacked, 4, "a_color"));

            mesh.setVertices(new float[] {
                    -2.5f, -2.5f, 0.0f, Color.toFloatBits(0, 255, 255, 255),		//V0
                     2.5f, -2.5f, 0.0f, Color.toFloatBits(255, 0, 255, 255),		//V1
                    -2.5f,  2.5f, 0.0f, Color.toFloatBits(0, 255, 0, 255),			//V2
                     2.5f,  2.5f, 0.0f, Color.toFloatBits(0, 0, 255, 255)});		//V3
            mesh.setIndices(new short[] {2, 0, 3, 1});
        }
	}

	@Override
	public void update(Application app) {
		meshRotate+=2;
		//if we want to move 48 world units (the width of the camera/world) in 1 second, we have
		//60 frames to do it. Which means we need to move 0.8 units every update
		//48 world units / 60 frames = 0.8f world units per frame
		meshTranslate += 0.8f * meshDirection;
		if (meshTranslate > gameWidth / 2) {
			meshDirection = -meshDirection;
			rightEnd = System.currentTimeMillis();
		}
		
		if (meshTranslate < -gameWidth / 2) {
			meshDirection = -meshDirection;
			leftEnd = System.currentTimeMillis() - rightEnd;
			Gdx.app.log("update()", "ratio across screen: " + leftEnd / (gameWidth / 2));
			Gdx.app.log("update()", "time across screen: " + leftEnd + "ms");
			Gdx.app.log("update()", "gameWidth: " + gameWidth / 2 + " : " + Gdx.graphics.getWidth());
		}
	}

	@Override
	public void render(Application app) {
		camera.update();
        camera.apply(Gdx.gl10);
		app.getGraphics().getGL10().glClear(GL10.GL_COLOR_BUFFER_BIT);
		Gdx.gl10.glLoadIdentity();
		Gdx.gl10.glTranslatef(meshTranslate, 0.0f, 0.0f);
		Gdx.gl10.glRotatef(meshRotate, 0.0f, 0.0f, 1.0f);
		mesh.render(GL10.GL_TRIANGLE_STRIP, 0, 4);
	}

	@Override
	public boolean isDone() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void dispose() {
		//meshes always need to be disposed
		mesh.dispose();
	}
}
