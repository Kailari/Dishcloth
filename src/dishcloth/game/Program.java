package dishcloth.game;

import dishcloth.random.WtfMain;

public class Program {

	public static void main(String[] args) {

		// Just don't care about this, okay? :D
		// DummyMain.run();
		new WtfMain();

	}

/*
* T�ss� on se loop
*
* public void run() {
*		long lastTime = System.nanoTime();
*		final double ns = 1000000000.0 / 60.0;
*		double delta = 0;
*		requestFocus();
*		while (running) {
*			long now = System.nanoTime();
*			delta += (now - lastTime) / ns;
*			lastTime = now;
*			while (delta >= 1) {
*				update();
*				render();
*				delta--;
*			}
*           // render(); voidaan siirt�� t�h�n, mutta en tii� onko mit��n j�rke�
*		}
*		stop();
*	}
*
* */

}
