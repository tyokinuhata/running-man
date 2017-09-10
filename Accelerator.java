import java.applet.Applet;
import java.applet.AudioClip;

public class Accelerator extends Sprite {
	// アイテム取得音
	private AudioClip sound;

	public Accelerator(double x, double y, String fileName, Map map) {
		super(x, y, fileName, map);

		// サウンドをロード
		sound = Applet.newAudioClip(getClass().getResource("se/chari13_c.wav"));
	}

	public void update() {}
    
	// サウンドを再生
	public void play() {
		sound.play();
	}
    
	// アイテムを使う
	public void use(Player player) {
		// プレイヤーのスピードがアップ！
		player.setSpeed(11.0);
		player.setJump(14.0);
		player.setJumperTwo(true);
	}
}