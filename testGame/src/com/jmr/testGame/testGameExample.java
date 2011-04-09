package com.jmr.testGame;



import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.RemoteInput;
import com.jmr.testGame.screens.AccelerometerTest;
import com.jmr.testGame.screens.JumpTest;
import com.jmr.testGame.screens.MainLoop;
import com.jmr.testGame.screens.Screen;
import com.jmr.testGame.screens.SmiulationTest;
import com.jmr.testGame.screens.TitleScreen;
import com.jmr.testGame.screens.omgCubes;
import com.jmr.testGame.screens.orthoCamTest;

public class testGameExample implements ApplicationListener {
	//counts how many times the render loop is ran
	int counter;
	private boolean isRunning = false;
	private Screen myScreen;
	

	@Override
	public void create() {
		//RemoteInput receiver = new RemoteInput(8190); //remove when testing on phone
		//Gdx.input = receiver;						  //remove when testing on phone
		if (!isRunning) {
			myScreen = new omgCubes(Gdx.app);
			isRunning = true;
		}
	}
	
	@Override
	public void render() {
		counter++;
		Application app = Gdx.app;
		//update the screen
		myScreen.update(app);
		//render the screen
		myScreen.render(app);
		
		if (myScreen.isDone()) {
			myScreen.dispose();
			
			if (myScreen instanceof TitleScreen) {
				myScreen = new orthoCamTest(app);
			}
			
			else if(myScreen instanceof orthoCamTest) {
				myScreen = new AccelerometerTest(app);
			}
			
			else if (myScreen instanceof AccelerometerTest) {
				myScreen = new SmiulationTest(app);
			}
			
			else if (myScreen instanceof SmiulationTest) {
				myScreen = new JumpTest(app);
			}
			
			else if (myScreen instanceof JumpTest) {
				myScreen = new orthoCamTest(app);
			}
		}
	}

	@Override
	public void dispose() {
		Gdx.app.log("testGameExample", "dispose()");
		Gdx.app.log("testGameExample", "render() was ran: " + counter + " times");
	}

	@Override
	public void pause() {
		Gdx.app.log("testGameExample", "pause()");
	}

	@Override
	public void resize(int width, int height) {
		Gdx.app.log("testGameExample", "resize(): " + width + ", " + height);
	}

	@Override
	public void resume() {
		Gdx.app.log("testGameExample", "resume()");
	}

}
