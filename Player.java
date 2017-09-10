import java.awt.Graphics;
import java.awt.Point;

public class Player extends Sprite {
	// 方向
	private static final int RIGHT = 0;
	private static final int LEFT = 1;

	// 速度
	protected double vx;
	protected double vy;

	// スピード
	private double speed;
	// ジャンプ力
	private double jumpSpeed;

	// 着地しているか
	private boolean onGround;
	// 再ジャンプできるか
	private boolean forceJump;
	// 二段ジャンプ能力の有無
	private boolean jumperTwo;
	// 二段ジャンプできるかを表すフラグ（すでに二段ジャンプ中ならできない）
	private boolean canJumperTwo;

	// 向いている方向
	private int dir;

/*-------------------------------------------------------------------
プレイヤーの速さやジャンプスピードを変える
-------------------------------------------------------------------*/
	public Player(double x, double y, String filename, Map map) {
		super(x, y, filename, map);
		vx = 0;
		vy = 80;
		speed = 4.7;		//速さデフォルト4.7
		jumpSpeed = 7.1;	//ジャンプの高さデフォルト7.1
		onGround = false;
		forceJump = false;
		jumperTwo = false;
		canJumperTwo = true;
		dir = RIGHT;
	}
	
	// 停止
	public void stop() {
        	vx = 0;
	}

	// 左加速
	public void accelerateLeft() {
		vx = -speed;
		dir = LEFT;
	}

	// 右加速
	public void accelerateRight() {
		vx = speed;
		dir = RIGHT;
	}

	// ジャンプ
	public void jump() {
		// 地上にいるか再ジャンプ可能なら
		if(onGround || forceJump) {
			// 上向きに速度を加える
			vy = -jumpSpeed;
			onGround = false;
			forceJump = false;
		} else if(jumperTwo && canJumperTwo) {
			// 二段ジャンプ能力を持ち、かつ二段ジャンプ中でない場合二段ジャンプ可能
			vy = -jumpSpeed;
			// 着地するまで操作不可
			canJumperTwo = false;
		}
	}

	// プレイヤーの状態更新
	public void update() {
		// 重力で下向きに加速度がかかる
		vy += Map.GRAVITY;

		// x方向の当たり判定
		// 移動先座標を求める
		double newX = x + vx;
		// 移動先座標で衝突するタイルの位置を取得
		// x方向だけ考えるのでy座標は変化しないと仮定
		Point tile = map.getTileCollision(this, newX, y);
		if (tile == null) {
			// 衝突するタイルがなければ移動
			x = newX;
		} else {
			// 衝突するタイルがある場合
			if (vx > 0) { // 右へ移動中なので右のブロックと衝突
				// ブロックにめりこむ or 隙間がないように位置調整
				x = Map.tilesToPixels(tile.x) - width;
			} else if (vx < 0) { // 左へ移動中なので左のブロックと衝突
				// 位置調整
				x = Map.tilesToPixels(tile.x + 1);
			}
			vx = 0;
		}

		// y方向の当たり判定
		// 移動先座標を求める
		double newY = y + vy;
		// 移動先座標で衝突するタイルの位置を取得
		// y方向だけ考える為、x座標は変化しないと仮定
		tile = map.getTileCollision(this, x, newY);
		if(tile == null) {
			// 衝突するタイルがなければ移動
			y = newY;
			// 衝突してないので空中
			onGround = false;
        	} else {
			// 衝突するタイルがある場合
			if(vy > 0) { // 下へ移動中なので下のブロックと衝突（着地）
				// 位置調整
				y = Map.tilesToPixels(tile.y) - height;
				// 着地したのでy方向速度を0に変更
				vy = 0;
				// 着地
				onGround = true;
				// 着地すれば再び二段ジャンプが可能
				canJumperTwo = true;
			} else if(vy < 0) { // 上へ移動中なので上のブロックと衝突（天井に衝突）
				// 位置調整
				y = Map.tilesToPixels(tile.y + 1);
				// 天井に衝突したのでy方向速度を0に変更
				vy = 0;
				// コインブロックの判定
			}
		}
	}

	// プレイヤーの描画（オーバーライド）
	//@param g 描画オブジェクト
	//@param offsetX X方向オフセット
	//@param offsetY Y方向オフセット

	public void draw(Graphics g, int offsetX, int offsetY) {
        	g.drawImage(image, (int) x + offsetX, (int) y + offsetY,
                	    (int) x + offsetX + width, (int) y + offsetY + height,
                	    count * width, dir * height,count * width + width, dir * height + height, null);
	}

	//@param forceJump The forceJump to set.
	public void setForceJump(boolean forceJump) {
		this.forceJump = forceJump;
	}
    
	//@return Returns the speed.
	public double getSpeed() {
		return speed;
	}


	//@param speed The speed to set.
	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public void setJump(double jumpSpeed) {
		this.jumpSpeed = jumpSpeed;
	}

	//@param jumperTwo The jumperTwo to set
	public void setJumperTwo(boolean jumperTwo) {
		this.jumperTwo = jumperTwo;
	}
}