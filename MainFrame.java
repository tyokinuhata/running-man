import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.ImageIcon;

public class MainFrame extends JFrame {
	public static void main(String[] args) {
		// MainFrame�N���X�̃C���X�^���X��
		MainFrame mf = new MainFrame("RunningMan4J");

		// �t���[���̏I������
		mf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// �t���[���̉���
		mf.setVisible(true);

		// �t���[�����𒆉��\��
		mf.setLocationRelativeTo(null);
	}

	// �t���[���̏ڍאݒ�
	public MainFrame(String title) {

		// �^�C�g����ݒ�
		setTitle(title);

        	// �T�C�Y�ύX�s��
	        setResizable(false);

		// �A�C�R��
		ImageIcon icon = new ImageIcon("./image/PlayerT.gif");
		setIconImage(icon.getImage());

		// MainPanel�N���X�̃C���X�^���X��
		MainPanel panel = new MainPanel();

		// Container�N���X�̃C���X�^���X��
		Container contentPane = getContentPane();

		// panel���R���|�[�l���g�ɒǉ�
		contentPane.add(panel);

		// �p�l���T�C�Y�ɍ��킹�ăt���[���T�C�Y����������
		pack();
	}
}