import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.applet.Applet;
import java.applet.AudioClip;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.JPanel;

public class MainPanel extends JPanel implements Runnable, KeyListener {
	// 画面切替変数
	//タイトル画面
	static final int TITLE = 0;
	// ゲーム画面
	static final int GAME = 1;
	// ゲームオーバー画面
	static final int GAMEOVER = 2;
	// クリア画面
	static final int CLIA = 3;
	// ロード画面
	static final int SET = 4;
	
	//画面代入変数
	int mode;

	//画像代入変数
	Image MainTitleImg;
	Image VerTitleImg;
	Image EnterPushImg;
	Image DeadImg;
	Image Char1;
	Image Char2;
	Image CliarImg;
	Image LoadImg;

	private static boolean hantei = false;
	
	// パネルサイズ
	public static final int WIDTH = 750;
	public static final int HEIGHT = 350;

	// マップ
	private Map map;

	String mapname = "map1.dat";

	// プレイヤー
	private Player player;

	// アクションキー
	private ActionKey goLeftKey;
	private ActionKey goRightKey;
	private ActionKey jumpKey;

	// ゲームループ用スレッド
	private Thread gameLoop;
 
	//音楽
	private static final String[] bgmNames = {"BGM.mid", "String.mid","muon1.mid","yuugure.mid"};
	private AudioClip BGM2;

	public MainPanel() {
		// パネルの推奨サイズを設定、pack()するときに必要
		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		// パネルがキー入力を受け付けるようにする
		setFocusable(true);

		// アクションキーの作成
		goLeftKey = new ActionKey();
		goRightKey = new ActionKey();

		// ジャンプはキーを押し続けても1回のみジャンプ
		jumpKey = new ActionKey();

		// マップの作成
		map = new Map(mapname);

		// プレイヤーの作成
		player = new Player(192, 32, "player.gif", map);

		// BGMのロード
		for(int i=0; i<bgmNames.length; i++) {
			try {
				MidiPlayer.load("se/" + bgmNames[i]);
			} catch (MidiUnavailableException e) {
				e.printStackTrace();
			} catch (InvalidMidiDataException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// キーイベントリスナーを登録
		addKeyListener(this);
	
		// ゲームループ開始
		gameLoop = new Thread(this);
		gameLoop.start();

		setBackground(Color.white);
		//タイトル画面（初期）
		mode = TITLE;
    	}
    
	//タイトル画面
	private void drawTitle(Graphics g) {
		setBackground(Color.WHITE);

		//タイトル画像（MainTitleImgに画像を代入する処理）
		if(MainTitleImg == null) {
			MainTitleImg = Toolkit.getDefaultToolkit().getImage("image/MainTitle.png");
		}
			
		//サブタイトル画像（VerTitleImgに画像を代入する処理）
		if(VerTitleImg == null) {
			VerTitleImg = Toolkit.getDefaultToolkit().getImage("image/VerTitle.png");
		}
			
		//Enterを押すがよい（EnterPushImgに画像を代入する処理）
		if(EnterPushImg == null) {
			EnterPushImg = Toolkit.getDefaultToolkit().getImage("image/EnterPush.png");
		}

		if(Char1 == null) {
			Char1 = Toolkit.getDefaultToolkit().getImage("image/digchanT.gif");
		}

		if(Char2 == null) {
			Char2 = Toolkit.getDefaultToolkit().getImage("image/PlayerT.gif");
		}
			
		// 画像表示
		g.drawImage(MainTitleImg, 170, 10, this);
		g.drawImage(VerTitleImg, 335, 80, this);
		g.drawImage(Char1, 360, 150, this);
		g.drawImage(Char2, 330, 150, this);
		g.drawImage(EnterPushImg, 170, 240, this);
	}
	
	public void warp(){
	// マップの作成
		map = new Map("map2.dat");        
		player = new Player(192, 32, "player.gif", map);
	}

	public void warp2(){
	// マップの作成
		map = new Map("map3.dat");        	
		player = new Player(192, 32, "player.gif", map);
	}

	// ゲームオーバー
	// GameOverの処理
	private void drawGameOver(Graphics g) {
		setBackground(Color.BLACK);
		
		//タイトル画像（DeadImgに画像を代入する処理）
		if(DeadImg == null) {
			DeadImg = Toolkit.getDefaultToolkit().getImage("image/Dead.png");
		}
		
		// 画像表示
		g.drawImage(DeadImg, 220, 110, this);
	}

	private void drawCliar(Graphics g) {
		setBackground(Color.WHITE);
		// タイトル画像（CliarImgに画像を代入する処理）
		if(CliarImg == null) {
			CliarImg = Toolkit.getDefaultToolkit().getImage("image/Omedeto.png");
		}
		
		// 画像表示
		g.drawImage(CliarImg, 220, 110, this);
	
	}

	private void drawSetup(Graphics g) {
		setBackground(Color.BLACK);
		// タイトル画像（CliarImgに画像を代入する処理）
		if (LoadImg == null) {
			LoadImg = Toolkit.getDefaultToolkit().getImage("image/loadNow.png");
		}
		
		// 画像の表示
		g.drawImage(LoadImg, 190, 110, this);	
	}
	
	// 画面切替（modeがTITLEならばdrawTitleメソッド, modeがGAMEならばPlayGAMEメソッドに移動）
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(mode == 0) {
			drawTitle(g);
		} else if(mode == 1) {
			// 描画処理
			// @param 描画オブジェクト

			g.setColor(Color.WHITE);
			g.fillRect(0, 0, getWidth(), getHeight());

			// X方向のオフセットの計算
			int offsetX = MainPanel.WIDTH / 2 - (int)player.getX();

			// マップの端ではスクロールしないようにする
			offsetX = Math.min(offsetX, 0);
			offsetX = Math.max(offsetX, MainPanel.WIDTH - map.getWidth());
	
			// Y方向のオフセットの計算
			int offsetY = MainPanel.HEIGHT / 2 - (int)player.getY();
			// マップの端ではスクロールしないようにする
	       	 	offsetY = Math.min(offsetY, 0);
	       	 	offsetY = Math.max(offsetY, MainPanel.HEIGHT - map.getHeight());

			// マップの描画
			map.draw(g, offsetX, offsetY);

			// プレイヤーの描画
			player.draw(g, offsetX, offsetY);

			// スプライトの描画
			// マップにいるスプライトを取得
			LinkedList sprites = map.getSprites();
			Iterator iterator = sprites.iterator();

			while(iterator.hasNext()) {
				Sprite sprite = (Sprite)iterator.next();
				sprite.draw(g, offsetX, offsetY);
			}
		}else if(mode == 2){
			drawGameOver(g);
		}else if(mode == 3){
			drawCliar(g);
		}else if(mode == 4){
			drawSetup(g);
		}
	}

	// ゲームループ
    	public void run() {
		while (true) {
			if(mode == 0){
				MidiPlayer.play(1);
				repaint();
			} else if(mode == 1){        		
				// 右キーが押されていれば右向きに加速
				player.accelerateRight();

				if(jumpKey.isPressed()) {
					// ジャンプする
					player.jump();
				}
 
	            		// プレイヤーの状態の更新
          	  		player.update();

				// マップにいるスプライトを取得
          			LinkedList sprites = map.getSprites();            
			        Iterator iterator = sprites.iterator();
			        
				while(iterator.hasNext()) {
					Sprite sprite = (Sprite)iterator.next();

					// スプライトの状態の更新
					sprite.update();

					// プレイヤーと接触してたら
					if(player.isCollision(sprite)) {
						//敵
						if (sprite instanceof Kuribo) {
							Kuribo kuribo = (Kuribo)sprite;

							// 上から踏まれてたら
							if((int)player.getY() < (int)kuribo.getY()) {
								// 敵は消える
								sprites.remove(kuribo);

								// サウンド
								kuribo.play();

								// 踏むとプレイヤーは再ジャンプ
								player.setForceJump(true);
								player.jump();
								break;
							} else {
								// ゲームオーバー
								mode = 2;
								repaint();
							}

						}else if(sprite instanceof Block) {
							// 敵
							Block block = (Block)sprite;

							// 上から踏まれてたら
							if((int)player.getY() > (int)block.getY()) {
								break;
							} else {
								// ゲームオーバー
								mode = 2;
								repaint();
							}
						} else if  (sprite instanceof Warp) {
							//ワープ
							warp();
							repaint();
						} else if  (sprite instanceof Warp2) {
							// ワープ2
							warp2();
							repaint();
						}

						if(sprite instanceof Goal) {
							// 敵
							Goal goal = (Goal)sprite;        			
							mode = 3;
							repaint();
						} else if(sprite instanceof Accelerator) {  // 加速アイテム
							// アイテムは消える
							sprites.remove(sprite);
							Accelerator accelerator = (Accelerator)sprite;

							// サウンド
							accelerator.play();

							// アイテムをその場で使う
							accelerator.use(player);
							break;
						// 二段ジャンプアイテム
						} else if(sprite instanceof JumperTwo) {
							// アイテムは消える
							sprites.remove(sprite);
							JumperTwo jumperTwo = (JumperTwo)sprite;

							// サウンド
							jumperTwo.play();

							// アイテムをその場で使う
							jumperTwo.use(player);
							break;                        
						}
					}
				}

			}else if(mode == 2){

	        	}else if(mode == 3){
          			// マップの作成
                		map = new Map("map1.dat");

                		// プレイヤーを作成
	                	player = new Player(192, 32, "player.gif", map);
    				repaint();
        		}else if(mode == 4){
				MidiPlayer.play(2);

				// マップを作成
          			map = new Map(mapname);

				// プレイヤーを作成
				player = new Player(192, 32, "player.gif", map);
				mode = 0;
    	      			repaint();
				
			}

            		// 再描画
            		repaint();

	            	// 休止
          	  	try {
             			Thread.sleep(20);
          	  	} catch(InterruptedException e) {
	                	e.printStackTrace();
            		}
        	}
	}

	// キーが押されたらキーを押された状態に変更
	// @param e キーイベント
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			goLeftKey.press();
		}

		if (key == KeyEvent.VK_RIGHT) {
			goRightKey.press();
		}

		if (key == KeyEvent.VK_SPACE) {
			jumpKey.press();
		}

		if(mode == 0) {
			switch(key) {
				case KeyEvent.VK_ENTER:
				mode = GAME;
				break;
			}
		} else if(mode == 2){
			switch(key) {
				case KeyEvent.VK_ENTER:
				mode = SET;
				break;
		}

		// 再描画
		repaint();

		} else if(mode == 3){
			switch(key) {
				case KeyEvent.VK_ENTER:
					mode = TITLE;
				
					break;
			}
			// 再描画
			repaint();
		}
	}

	//キーが離されたらキーの状態を「離された」に変える
	//@param e キーイベント
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if(key == KeyEvent.VK_LEFT) {
			goLeftKey.release();
		}

		if(key == KeyEvent.VK_RIGHT) {
			goRightKey.release();
		}

		if (key == KeyEvent.VK_SPACE) {
			jumpKey.release();
		}
 	}

    	public void keyTyped(KeyEvent e) {}
}










