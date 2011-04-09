package com.jmr.testGame;

import com.badlogic.gdx.backends.jogl.JoglApplication;

public class desktopExample {
	public static void main(String[] argv) {
		new JoglApplication(new testGameExample(), "fancy title name", 1024, 512, false);
	}
}
