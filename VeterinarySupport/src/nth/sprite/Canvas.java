package nth.sprite;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Canvas extends JPanel implements ActionListener {

	private static final long serialVersionUID = -50577849622934499L;

	private Timer timer;

	private SpriteCollection spriteCollection;

	public Canvas() {
		setFocusable(true);
		setBackground(Color.BLACK);
		setDoubleBuffered(true);
		setBackground(Color.white);
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				spriteCollection.keyReleased(e);
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				spriteCollection.keyPressed(e);
			}
		});
		timer = new Timer(10, this);
	}

	public void setSpriteCollection(SpriteCollection spriteCollection) {
		this.spriteCollection = spriteCollection;
		if (spriteCollection != null) {
			timer.start();
		}
	}

	public void paint(Graphics graphics) {
		super.paint(graphics);
		if (spriteCollection != null) {
			spriteCollection.paint(graphics, this);
		}
		Toolkit.getDefaultToolkit().sync();
		graphics.dispose();
	}

	public void actionPerformed(ActionEvent e) {
		synchronized (this) {
			spriteCollection.process();
			repaint();	
		}
	}

	private class TAdapter extends KeyAdapter {

		public void keyReleased(KeyEvent e) {
			
		}

		public void keyPressed(KeyEvent e) {
			
		}
	}

}
