import java.applet.Applet;
import java.applet.AudioClip;

public class Accelerator extends Sprite {
	// �A�C�e���擾��
	private AudioClip sound;

	public Accelerator(double x, double y, String fileName, Map map) {
		super(x, y, fileName, map);

		// �T�E���h�����[�h
		sound = Applet.newAudioClip(getClass().getResource("sound/chari13_c.wav"));
	}

	public void update() {}
    
	// �T�E���h���Đ�
	public void play() {
		sound.play();
	}
    
	// �A�C�e�����g��
	public void use(Player player) {
		// �v���C���[�̃X�s�[�h���A�b�v�I
		player.setSpeed(11.0);
		player.setJump(14.0);
		player.setJumperTwo(true);
	}
}