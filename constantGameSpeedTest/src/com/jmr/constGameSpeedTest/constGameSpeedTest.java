package com.jmr.constGameSpeedTest;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;

public class constGameSpeedTest implements ApplicationListener {
	
	private long beginTime;
	private long timeDiff;
	private FPS60 fps;
	
	private Screen screen;

	@Override
	public void create() {
		screen = new testScreen(Gdx.app);
		fps = new FPS60();
	}

	@Override
	public void dispose() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void render() {
		Application app = Gdx.app;
		//time before we update and render
		beginTime = System.currentTimeMillis();
		
		screen.update(app);
		screen.render(app);
		//time difference between before and after the update and render cycle
		timeDiff = System.currentTimeMillis() - beginTime;
		//check if we need to put the thread to sleep, or catch up
		fps.sleep(timeDiff, screen, app);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void resume() {
	}

}
