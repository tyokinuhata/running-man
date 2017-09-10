import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Point;

public class Kuribo extends Sprite {
	// スピード
	private static final double SPEED = 1;

	// 速度
	protected double vx;
	protected double vy;

	// 踏まれたときの音
	private AudioClip sound;

	public Kuribo(double x, double y, String filename, Map map) {
		super(x, y, filename, map);

		// 左に移動を続ける
		vx = -SPEED;
		vy = 0;
        
		// サウンドのロード
		sound = Applet.newAudioClip(getClass().getResource("se/gucha.wav"));
	}

	public void update() {
		// 重力で下向きに加速度がかかる
		vy+=Map.GRAVITY;

		// x方向の当たり判定
		// 移動先座標を求める
		double newX = x + vx;

		// 移動先座標で衝突するタイルの位置を取得
		// x方向だけ考えるのでy座標は変化しないと仮定
		Point tile = map.getTileCollision(this, newX, y);

		if(tile == null) {
			// 衝突するタイルがなければ移動
			x = newX;
		} else {
			// 衝突するタイルがある場合
			// 右へ移動中なので右のブロックと衝突
			if(vx > 0) {

			// ブロックにめりこむ or 隙間がないように位置調整
			x = Map.tilesToPixels(tile.x) - width;

			// 左へ移動中なので左のブロックと衝突
			} else if(vx < 0) {
				// 位置調整
				x = Map.tilesToPixels(tile.x + 1);
            		}

			// 移動方向の反転
			vx = -vx;
        	}

	        // y方向の当たり判定
        	// 移動先座標を求める
		double newY = y + vy;

		// 移動先座標で衝突するタイルの位置を取得
		// y方向だけ考えるのでx座標は変化しないと仮定
		tile = map.getTileCollision(this, x, newY);

		if(tile == null) {
			// 衝突するタイルがなければ移動
			y = newY;
		} else {
			// 衝突するタイルがある場合
			if (vy > 0) { // 下へ移動中なので下のブロックと衝突（着地）

			// 位置調整
			y = Map.tilesToPixels(tile.y) - height;

			// 着地したのでy方向速度を0に
			vy = 0;

			// 上へ移動中の為上のブロックと天井が衝突
			} else if(vy < 0) {
				// 位置調整
				y = Map.tilesToPixels(tile.y + 1);

				// 天井にぶつかったのでy方向速度を0に
				vy = 0;
			}
		}
	}
    
	//サウンドの再生
	public void play() {
		sound.play();
	}
}