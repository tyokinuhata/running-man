import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.ImageIcon;

public class Map {

	String rea = "rea.gif";
	String kuri = "kuri.gif";
	String mos = "monster.gif";
	String dig = "digchan.gif";
	String sebe = "sebemushi.gif";

	// タイルサイズ
	public static final int TILE_SIZE = 32;

	// 重力
	public static final double GRAVITY = 0.6;

	// マップ
	private char[][] map;
	
	// 行数
	private int row;

	// 列数
	private int col;

	// 幅
	private int width;

	// 高さ
	private int height; 

	// ブロック画像
	private Image blockImage;
	private Image coinBlockImage;
	private Image coinBlockImage2;

	// スプライトリスト
	private LinkedList sprites;

	// コインブロックを叩く音
	private AudioClip coinSound;

	// アイテムブロックを叩く音
	private AudioClip itemSound;

	public Map(String filename) {
		sprites = new LinkedList();

		coinSound = Applet.newAudioClip(getClass().getResource("se/coin03.wav"));
		itemSound = Applet.newAudioClip(getClass().getResource("se/puu69.wav"));

		// マップのロード
		load(filename);

		width = TILE_SIZE * col;
		height = TILE_SIZE * row;

		// イメージのロード
		loadImage();
	}

	//マップの描画
	//@param g 描画オブジェクト
	//@param offsetX X方向オフセット
	//@param offsetY Y方向オフセット
	public void draw(Graphics g, int offsetX, int offsetY) {
		// オフセットを元に描画範囲を求める
		int firstTileX = pixelsToTiles(-offsetX);
		int lastTileX = firstTileX + pixelsToTiles(MainPanel.WIDTH) + 10;

		// 描画範囲がマップの大きさより大きくならないように調整
		lastTileX = Math.min(lastTileX, col);

		int firstTileY = pixelsToTiles(-offsetY);
		int lastTileY = firstTileY + pixelsToTiles(MainPanel.HEIGHT) + 10;

		// 描画範囲がマップの大きさより大きくならないように調整
		lastTileY = Math.min(lastTileY, row);

		for (int i = firstTileY; i < lastTileY; i++) {
			for (int j = firstTileX; j < lastTileX; j++) {
				// mapの値に応じて画像を描く
				switch (map[i][j]) {
					//ブロック
					case '1' :
						g.drawImage(blockImage, tilesToPixels(j) + offsetX, tilesToPixels(i) + offsetY, null);
						break;
				}
			}
		}
	}
    

	// (newX, newY)で衝突するブロックの座標を返す
	// @param sprite スプライトへの参照
	// @param newX X座標
	// @param newY Y座標
	// @return 衝突するブロックの座標
	public Point getTileCollision(Sprite sprite, double newX, double newY) {
		// 小数点以下切り上げ
		// 浮動小数点の関係で切り上げしないと衝突してないと判定される可能性有り
		newX = Math.ceil(newX);
		newY = Math.ceil(newY);

		double fromX = Math.min(sprite.getX(), newX);
		double fromY = Math.min(sprite.getY(), newY);
		double toX = Math.max(sprite.getX(), newX);
		double toY = Math.max(sprite.getY(), newY);

		int fromTileX = pixelsToTiles(fromX);
		int fromTileY = pixelsToTiles(fromY);
		int toTileX = pixelsToTiles(toX + sprite.getWidth() - 1);
		int toTileY = pixelsToTiles(toY + sprite.getHeight() - 1);

		// 衝突しているかの判定
		for (int x = fromTileX; x <= toTileX; x++) {
			for (int y = fromTileY; y <= toTileY; y++) {
				// 画面外は衝突
				if(x < 0 || x >= col) {
					return new Point(x, y);
				}

				if (y < 0 || y >= row) {
					return new Point(x, y);
				}

				// ブロックがあれば衝突
				if(map[y][x] == '1' || map[y][x] == 'C' || map[y][x] == 'c' || map[y][x] == 'I') {
                    			return new Point(x, y);
				}
			}
		}
		return null;
	}

	// ピクセル単位をタイル単位に変更
	// @param pixels ピクセル単位
	// @return タイル単位
	public static int pixelsToTiles(double pixels) {
		return (int)Math.floor(pixels / TILE_SIZE);
	}
    
	// タイル単位をピクセル単位に変更
	// @param tiles タイル単位
	// @return ピクセル単位
	public static int tilesToPixels(int tiles) {
		return tiles * TILE_SIZE;
	}
    
	// イメージのロード
	private void loadImage() {
		ImageIcon icon = new ImageIcon(getClass().getResource("image/block.gif"));
		blockImage = icon.getImage();
	}

	// マップのロード
	// @param filename マップファイル
	private String enemy(){
		Random r = new Random();

		int random = r.nextInt(10);
		String enemy = "";

		switch(random){
			case 5 :
			case 8 :
			case 0 :
				enemy = dig;
				break;
			case 3 :
			case 7 :
				enemy = kuri;
				break;
			case 9 :
			case 2 :
				enemy = mos;
				break;
			case 1 :
			case 4 :
				enemy = sebe;
				break;
			case 6 :
				enemy = rea;
				break;
		}
		return enemy;
	}

	private void load(String filename) {
		try {
			// ファイルを開く
			BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("map/" + filename)));

			// 行数を読み込む
			String line = br.readLine();
			row = Integer.parseInt(line);

			// 列数を読み込む
			line = br.readLine();
			col = Integer.parseInt(line);

			// マップを作成
			map = new char[row][col];

			for (int i = 0; i < row; i++) {
				line = br.readLine();

				for (int j = 0; j < col; j++) {
					map[i][j] = line.charAt(j);

					switch (map[i][j]) {
						// ブロック
						case '2':
							sprites.add(new Block(tilesToPixels(j), tilesToPixels(i), "block.gif", this));
							break;
						// 落ちる判定
						case '3':
							sprites.add(new Block(tilesToPixels(j), tilesToPixels(i), "none.png", this));
							break;
						// ワープ1
						case 'w':
							sprites.add(new Warp(tilesToPixels(j), tilesToPixels(i), "warp_.gif", this));
							break;
						// ワープ2
						case 'W':
							sprites.add(new Warp2(tilesToPixels(j), tilesToPixels(i), "warp_.gif", this));
							break;
						// ゴール
						case 'g':
							sprites.add(new Goal(tilesToPixels(j), tilesToPixels(i), "stripe.gif", this));
							break;
						// 敵
						case 'k':
							sprites.add(new Kuribo(tilesToPixels(j), tilesToPixels(i), enemy(), this));
							break;
						// 加速アイテム
						case 'a':  // 加速アイテム
							sprites.add(new Accelerator(tilesToPixels(j), tilesToPixels(i), "accelerator.gif", this));
							break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// @return Returns the width.
	public int getWidth() {
		return width;
	}

	// @return Returns the height.
	public int getHeight() {
		return height;
	}

	// @return Returns the sprites.
	public LinkedList getSprites() {
		return sprites;
	}
}
