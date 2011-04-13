package com.jmr.constGameSpeedTest;

import com.badlogic.gdx.Application;

public interface Screen {
	
	public void update (Application app);
	
	public void render (Application app);
	
	public boolean isDone ();
	
	public void dispose ();
}
