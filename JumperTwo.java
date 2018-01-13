import java.applet.Applet;
import java.applet.AudioClip;

public class JumperTwo extends Sprite {
	// �A�C�e���擾��
	private AudioClip sound;

	public JumperTwo(double x, double y, String fileName, Map map) {
		super(x, y, fileName, map);
        
		// �T�E���h�̃��[�h
		sound = Applet.newAudioClip(getClass().getResource("sound/pyoro57_b.wav"));
	}

	public void update() {}
    
	//�T�E���h�̍Đ�
	public void play() {
		sound.play();
	}

	//�A�C�e�����g��
	public void use(Player player) {
		// �v���C���[����i�W�����v�\
		player.setJumperTwo(true);
	}
}