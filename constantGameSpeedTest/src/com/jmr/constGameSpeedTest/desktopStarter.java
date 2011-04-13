package com.jmr.constGameSpeedTest;

import com.badlogic.gdx.backends.jogl.JoglApplication;

public class desktopStarter {
	public static void main(String[] argv) {
		new JoglApplication(new constGameSpeedTest(), "Constant Game Speed Test", 480, 320, false);
	}
}
