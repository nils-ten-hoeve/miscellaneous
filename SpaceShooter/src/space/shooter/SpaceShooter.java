package space.shooter;


import javax.swing.JFrame;

public class SpaceShooter extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5639850068871313915L;

	public SpaceShooter() {

        add(new Board());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setTitle("Space Shooter");
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        new SpaceShooter();
    }
}
