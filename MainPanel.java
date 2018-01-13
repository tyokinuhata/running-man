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
	// ��ʐؑ֕ϐ�
	//�^�C�g�����
	static final int TITLE = 0;
	// �Q�[�����
	static final int GAME = 1;
	// �Q�[���I�[�o�[���
	static final int GAMEOVER = 2;
	// �N���A���
	static final int CLIA = 3;
	// ���[�h���
	static final int SET = 4;
	
	//��ʑ���ϐ�
	int mode;

	//�摜����ϐ�
	Image MainTitleImg;
	Image VerTitleImg;
	Image EnterPushImg;
	Image DeadImg;
	Image Char1;
	Image Char2;
	Image CliarImg;
	Image LoadImg;

	private static boolean hantei = false;
	
	// �p�l���T�C�Y
	public static final int WIDTH = 750;
	public static final int HEIGHT = 350;

	// �}�b�v
	private Map map;

	String mapname = "map1.dat";

	// �v���C���[
	private Player player;

	// �A�N�V�����L�[
	private ActionKey goLeftKey;
	private ActionKey goRightKey;
	private ActionKey jumpKey;

	// �Q�[�����[�v�p�X���b�h
	private Thread gameLoop;
 
	//���y
	private static final String[] bgmNames = {"BGM.mid", "String.mid","muon1.mid","yuugure.mid"};
	private AudioClip BGM2;

	public MainPanel() {
		// �p�l���̐����T�C�Y��ݒ�Apack()����Ƃ��ɕK�v
		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		// �p�l�����L�[���͂��󂯕t����悤�ɂ���
		setFocusable(true);

		// �A�N�V�����L�[�̍쐬
		goLeftKey = new ActionKey();
		goRightKey = new ActionKey();

		// �W�����v�̓L�[�����������Ă�1��̂݃W�����v
		jumpKey = new ActionKey();

		// �}�b�v�̍쐬
		map = new Map(mapname);

		// �v���C���[�̍쐬
		player = new Player(192, 32, "player.gif", map);

		// BGM�̃��[�h
		for(int i=0; i<bgmNames.length; i++) {
			try {
				MidiPlayer.load("sound/" + bgmNames[i]);
			} catch (MidiUnavailableException e) {
				e.printStackTrace();
			} catch (InvalidMidiDataException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// �L�[�C�x���g���X�i�[��o�^
		addKeyListener(this);
	
		// �Q�[�����[�v�J�n
		gameLoop = new Thread(this);
		gameLoop.start();

		setBackground(Color.white);
		//�^�C�g����ʁi�����j
		mode = TITLE;
    	}
    
	//�^�C�g�����
	private void drawTitle(Graphics g) {
		setBackground(Color.WHITE);

		//�^�C�g���摜�iMainTitleImg�ɉ摜�������鏈���j
		if(MainTitleImg == null) {
			MainTitleImg = Toolkit.getDefaultToolkit().getImage("image/MainTitle.png");
		}
			
		//�T�u�^�C�g���摜�iVerTitleImg�ɉ摜�������鏈���j
		if(VerTitleImg == null) {
			VerTitleImg = Toolkit.getDefaultToolkit().getImage("image/VerTitle.png");
		}
			
		//Enter���������悢�iEnterPushImg�ɉ摜�������鏈���j
		if(EnterPushImg == null) {
			EnterPushImg = Toolkit.getDefaultToolkit().getImage("image/EnterPush.png");
		}

		if(Char1 == null) {
			Char1 = Toolkit.getDefaultToolkit().getImage("image/digchanT.gif");
		}

		if(Char2 == null) {
			Char2 = Toolkit.getDefaultToolkit().getImage("image/PlayerT.gif");
		}
			
		// �摜�\��
		g.drawImage(MainTitleImg, 170, 10, this);
		g.drawImage(VerTitleImg, 335, 80, this);
		g.drawImage(Char1, 360, 150, this);
		g.drawImage(Char2, 330, 150, this);
		g.drawImage(EnterPushImg, 170, 240, this);
	}
	
	public void warp(){
	// �}�b�v�̍쐬
		map = new Map("map2.dat");        
		player = new Player(192, 32, "player.gif", map);
	}

	public void warp2(){
	// �}�b�v�̍쐬
		map = new Map("map3.dat");        	
		player = new Player(192, 32, "player.gif", map);
	}

	// �Q�[���I�[�o�[
	// GameOver�̏���
	private void drawGameOver(Graphics g) {
		setBackground(Color.BLACK);
		
		//�^�C�g���摜�iDeadImg�ɉ摜�������鏈���j
		if(DeadImg == null) {
			DeadImg = Toolkit.getDefaultToolkit().getImage("image/Dead.png");
		}
		
		// �摜�\��
		g.drawImage(DeadImg, 220, 110, this);
	}

	private void drawCliar(Graphics g) {
		setBackground(Color.WHITE);
		// �^�C�g���摜�iCliarImg�ɉ摜�������鏈���j
		if(CliarImg == null) {
			CliarImg = Toolkit.getDefaultToolkit().getImage("image/Omedeto.png");
		}
		
		// �摜�\��
		g.drawImage(CliarImg, 220, 110, this);
	
	}

	private void drawSetup(Graphics g) {
		setBackground(Color.BLACK);
		// �^�C�g���摜�iCliarImg�ɉ摜�������鏈���j
		if (LoadImg == null) {
			LoadImg = Toolkit.getDefaultToolkit().getImage("image/loadNow.png");
		}
		
		// �摜�̕\��
		g.drawImage(LoadImg, 190, 110, this);	
	}
	
	// ��ʐؑցimode��TITLE�Ȃ��drawTitle���\�b�h, mode��GAME�Ȃ��PlayGAME���\�b�h�Ɉړ��j
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(mode == 0) {
			drawTitle(g);
		} else if(mode == 1) {
			// �`�揈��
			// @param �`��I�u�W�F�N�g

			g.setColor(Color.WHITE);
			g.fillRect(0, 0, getWidth(), getHeight());

			// X�����̃I�t�Z�b�g�̌v�Z
			int offsetX = MainPanel.WIDTH / 2 - (int)player.getX();

			// �}�b�v�̒[�ł̓X�N���[�����Ȃ��悤�ɂ���
			offsetX = Math.min(offsetX, 0);
			offsetX = Math.max(offsetX, MainPanel.WIDTH - map.getWidth());
	
			// Y�����̃I�t�Z�b�g�̌v�Z
			int offsetY = MainPanel.HEIGHT / 2 - (int)player.getY();
			// �}�b�v�̒[�ł̓X�N���[�����Ȃ��悤�ɂ���
	       	 	offsetY = Math.min(offsetY, 0);
	       	 	offsetY = Math.max(offsetY, MainPanel.HEIGHT - map.getHeight());

			// �}�b�v�̕`��
			map.draw(g, offsetX, offsetY);

			// �v���C���[�̕`��
			player.draw(g, offsetX, offsetY);

			// �X�v���C�g�̕`��
			// �}�b�v�ɂ���X�v���C�g���擾
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

	// �Q�[�����[�v
    	public void run() {
		while (true) {
			if(mode == 0){
				MidiPlayer.play(1);
				repaint();
			} else if(mode == 1){        		
				// �E�L�[��������Ă���ΉE�����ɉ���
				player.accelerateRight();

				if(jumpKey.isPressed()) {
					// �W�����v����
					player.jump();
				}
 
	            		// �v���C���[�̏�Ԃ̍X�V
          	  		player.update();

				// �}�b�v�ɂ���X�v���C�g���擾
          			LinkedList sprites = map.getSprites();            
			        Iterator iterator = sprites.iterator();
			        
				while(iterator.hasNext()) {
					Sprite sprite = (Sprite)iterator.next();

					// �X�v���C�g�̏�Ԃ̍X�V
					sprite.update();

					// �v���C���[�ƐڐG���Ă���
					if(player.isCollision(sprite)) {
						//�G
						if (sprite instanceof Kuribo) {
							Kuribo kuribo = (Kuribo)sprite;

							// �ォ�瓥�܂�Ă���
							if((int)player.getY() < (int)kuribo.getY()) {
								// �G�͏�����
								sprites.remove(kuribo);

								// �T�E���h
								kuribo.play();

								// ���ނƃv���C���[�͍ăW�����v
								player.setForceJump(true);
								player.jump();
								break;
							} else {
								// �Q�[���I�[�o�[
								mode = 2;
								repaint();
							}

						}else if(sprite instanceof Block) {
							// �G
							Block block = (Block)sprite;

							// �ォ�瓥�܂�Ă���
							if((int)player.getY() > (int)block.getY()) {
								break;
							} else {
								// �Q�[���I�[�o�[
								mode = 2;
								repaint();
							}
						} else if  (sprite instanceof Warp) {
							//���[�v
							warp();
							repaint();
						} else if  (sprite instanceof Warp2) {
							// ���[�v2
							warp2();
							repaint();
						}

						if(sprite instanceof Goal) {
							// �G
							Goal goal = (Goal)sprite;        			
							mode = 3;
							repaint();
						} else if(sprite instanceof Accelerator) {  // �����A�C�e��
							// �A�C�e���͏�����
							sprites.remove(sprite);
							Accelerator accelerator = (Accelerator)sprite;

							// �T�E���h
							accelerator.play();

							// �A�C�e�������̏�Ŏg��
							accelerator.use(player);
							break;
						// ��i�W�����v�A�C�e��
						} else if(sprite instanceof JumperTwo) {
							// �A�C�e���͏�����
							sprites.remove(sprite);
							JumperTwo jumperTwo = (JumperTwo)sprite;

							// �T�E���h
							jumperTwo.play();

							// �A�C�e�������̏�Ŏg��
							jumperTwo.use(player);
							break;                        
						}
					}
				}

			}else if(mode == 2){

	        	}else if(mode == 3){
          			// �}�b�v�̍쐬
                		map = new Map("map1.dat");

                		// �v���C���[���쐬
	                	player = new Player(192, 32, "player.gif", map);
    				repaint();
        		}else if(mode == 4){
				MidiPlayer.play(2);

				// �}�b�v���쐬
          			map = new Map(mapname);

				// �v���C���[���쐬
				player = new Player(192, 32, "player.gif", map);
				mode = 0;
    	      			repaint();
				
			}

            		// �ĕ`��
            		repaint();

	            	// �x�~
          	  	try {
             			Thread.sleep(20);
          	  	} catch(InterruptedException e) {
	                	e.printStackTrace();
            		}
        	}
	}

	// �L�[�������ꂽ��L�[�������ꂽ��ԂɕύX
	// @param e �L�[�C�x���g
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

		// �ĕ`��
		repaint();

		} else if(mode == 3){
			switch(key) {
				case KeyEvent.VK_ENTER:
					mode = TITLE;
				
					break;
			}
			// �ĕ`��
			repaint();
		}
	}

	//�L�[�������ꂽ��L�[�̏�Ԃ��u�����ꂽ�v�ɕς���
	//@param e �L�[�C�x���g
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










