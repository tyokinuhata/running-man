import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.ImageIcon;

public class MainFrame extends JFrame {
	public static void main(String[] args) {
		MainFrame mf = new MainFrame("RunningMan4J");

		mf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mf.setVisible(true);

		mf.setLocationRelativeTo(null);
	}

	public MainFrame(String title) {

		setTitle(title);

	        setResizable(false);

		ImageIcon icon = new ImageIcon("./image/PlayerT.gif");
		setIconImage(icon.getImage());

		MainPanel panel = new MainPanel();

		Container contentPane = getContentPane();

		contentPane.add(panel);

		pack();
	}
}