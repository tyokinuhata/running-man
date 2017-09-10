import java.applet.Applet;
import java.applet.AudioClip;

public class JumperTwo extends Sprite {
	// アイテム取得音
	private AudioClip sound;

	public JumperTwo(double x, double y, String fileName, Map map) {
		super(x, y, fileName, map);
        
		// サウンドのロード
		sound = Applet.newAudioClip(getClass().getResource("se/pyoro57_b.wav"));
	}

	public void update() {}
    
	//サウンドの再生
	public void play() {
		sound.play();
	}

	//アイテムを使う
	public void use(Player player) {
		// プレイヤーが二段ジャンプ可能
		player.setJumperTwo(true);
	}
}