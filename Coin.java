import java.applet.Applet;
import java.applet.AudioClip;

public class Coin extends Sprite {
	// �R�C���擾��
	private AudioClip sound;

	public Coin(double x, double y, String fileName, Map map) {
		super(x, y, fileName, map);

		// �T�E���h�̃��[�h
		sound = Applet.newAudioClip(getClass().getResource("sound/coin03.wav"));
	}

	public void update() {}

	// �T�E���h�̍Đ�
	public void play() {
		sound.play();
	}
}
