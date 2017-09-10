import java.applet.Applet;
import java.applet.AudioClip;

public class Coin extends Sprite {
	// コイン取得音
	private AudioClip sound;

	public Coin(double x, double y, String fileName, Map map) {
		super(x, y, fileName, map);

		// サウンドのロード
		sound = Applet.newAudioClip(getClass().getResource("se/coin03.wav"));
	}

	public void update() {}

	// サウンドの再生
	public void play() {
		sound.play();
	}
}
