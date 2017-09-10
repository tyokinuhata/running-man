import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.ImageIcon;

public class MainFrame extends JFrame {
	public static void main(String[] args) {
		// MainFrameクラスのインスタンス化
		MainFrame mf = new MainFrame("RunningMan4J");

		// フレームの終了処理
		mf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// フレームの可視化
		mf.setVisible(true);

		// フレームをを中央表示
		mf.setLocationRelativeTo(null);
	}

	// フレームの詳細設定
	public MainFrame(String title) {

		// タイトルを設定
		setTitle(title);

        	// サイズ変更不可
	        setResizable(false);

		// アイコン
		ImageIcon icon = new ImageIcon("./image/PlayerT.gif");
		setIconImage(icon.getImage());

		// MainPanelクラスのインスタンス化
		MainPanel panel = new MainPanel();

		// Containerクラスのインスタンス化
		Container contentPane = getContentPane();

		// panelをコンポーネントに追加
		contentPane.add(panel);

		// パネルサイズに合わせてフレームサイズを自動調節
		pack();
	}
}