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

	// �^�C���T�C�Y
	public static final int TILE_SIZE = 32;

	// �d��
	public static final double GRAVITY = 0.6;

	// �}�b�v
	private char[][] map;
	
	// �s��
	private int row;

	// ��
	private int col;

	// ��
	private int width;

	// ����
	private int height; 

	// �u���b�N�摜
	private Image blockImage;
	private Image coinBlockImage;
	private Image coinBlockImage2;

	// �X�v���C�g���X�g
	private LinkedList sprites;

	// �R�C���u���b�N��@����
	private AudioClip coinSound;

	// �A�C�e���u���b�N��@����
	private AudioClip itemSound;

	public Map(String filename) {
		sprites = new LinkedList();

		coinSound = Applet.newAudioClip(getClass().getResource("sound/coin03.wav"));
		itemSound = Applet.newAudioClip(getClass().getResource("sound/puu69.wav"));

		// �}�b�v�̃��[�h
		load(filename);

		width = TILE_SIZE * col;
		height = TILE_SIZE * row;

		// �C���[�W�̃��[�h
		loadImage();
	}

	//�}�b�v�̕`��
	//@param g �`��I�u�W�F�N�g
	//@param offsetX X�����I�t�Z�b�g
	//@param offsetY Y�����I�t�Z�b�g
	public void draw(Graphics g, int offsetX, int offsetY) {
		// �I�t�Z�b�g�����ɕ`��͈͂����߂�
		int firstTileX = pixelsToTiles(-offsetX);
		int lastTileX = firstTileX + pixelsToTiles(MainPanel.WIDTH) + 10;

		// �`��͈͂��}�b�v�̑傫�����傫���Ȃ�Ȃ��悤�ɒ���
		lastTileX = Math.min(lastTileX, col);

		int firstTileY = pixelsToTiles(-offsetY);
		int lastTileY = firstTileY + pixelsToTiles(MainPanel.HEIGHT) + 10;

		// �`��͈͂��}�b�v�̑傫�����傫���Ȃ�Ȃ��悤�ɒ���
		lastTileY = Math.min(lastTileY, row);

		for (int i = firstTileY; i < lastTileY; i++) {
			for (int j = firstTileX; j < lastTileX; j++) {
				// map�̒l�ɉ����ĉ摜��`��
				switch (map[i][j]) {
					//�u���b�N
					case '1' :
						g.drawImage(blockImage, tilesToPixels(j) + offsetX, tilesToPixels(i) + offsetY, null);
						break;
				}
			}
		}
	}
    

	// (newX, newY)�ŏՓ˂���u���b�N�̍��W��Ԃ�
	// @param sprite �X�v���C�g�ւ̎Q��
	// @param newX X���W
	// @param newY Y���W
	// @return �Փ˂���u���b�N�̍��W
	public Point getTileCollision(Sprite sprite, double newX, double newY) {
		// �����_�ȉ��؂�グ
		// ���������_�̊֌W�Ő؂�グ���Ȃ��ƏՓ˂��ĂȂ��Ɣ��肳���\���L��
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

		// �Փ˂��Ă��邩�̔���
		for (int x = fromTileX; x <= toTileX; x++) {
			for (int y = fromTileY; y <= toTileY; y++) {
				// ��ʊO�͏Փ�
				if(x < 0 || x >= col) {
					return new Point(x, y);
				}

				if (y < 0 || y >= row) {
					return new Point(x, y);
				}

				// �u���b�N������ΏՓ�
				if(map[y][x] == '1' || map[y][x] == 'C' || map[y][x] == 'c' || map[y][x] == 'I') {
                    			return new Point(x, y);
				}
			}
		}
		return null;
	}

	// �s�N�Z���P�ʂ��^�C���P�ʂɕύX
	// @param pixels �s�N�Z���P��
	// @return �^�C���P��
	public static int pixelsToTiles(double pixels) {
		return (int)Math.floor(pixels / TILE_SIZE);
	}
    
	// �^�C���P�ʂ��s�N�Z���P�ʂɕύX
	// @param tiles �^�C���P��
	// @return �s�N�Z���P��
	public static int tilesToPixels(int tiles) {
		return tiles * TILE_SIZE;
	}
    
	// �C���[�W�̃��[�h
	private void loadImage() {
		ImageIcon icon = new ImageIcon(getClass().getResource("image/block.gif"));
		blockImage = icon.getImage();
	}

	// �}�b�v�̃��[�h
	// @param filename �}�b�v�t�@�C��
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
			// �t�@�C�����J��
			BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("map/" + filename)));

			// �s����ǂݍ���
			String line = br.readLine();
			row = Integer.parseInt(line);

			// �񐔂�ǂݍ���
			line = br.readLine();
			col = Integer.parseInt(line);

			// �}�b�v���쐬
			map = new char[row][col];

			for (int i = 0; i < row; i++) {
				line = br.readLine();

				for (int j = 0; j < col; j++) {
					map[i][j] = line.charAt(j);

					switch (map[i][j]) {
						// �u���b�N
						case '2':
							sprites.add(new Block(tilesToPixels(j), tilesToPixels(i), "block.gif", this));
							break;
						// �����锻��
						case '3':
							sprites.add(new Block(tilesToPixels(j), tilesToPixels(i), "none.png", this));
							break;
						// ���[�v1
						case 'w':
							sprites.add(new Warp(tilesToPixels(j), tilesToPixels(i), "warp_.gif", this));
							break;
						// ���[�v2
						case 'W':
							sprites.add(new Warp2(tilesToPixels(j), tilesToPixels(i), "warp_.gif", this));
							break;
						// �S�[��
						case 'g':
							sprites.add(new Goal(tilesToPixels(j), tilesToPixels(i), "stripe.gif", this));
							break;
						// �G
						case 'k':
							sprites.add(new Kuribo(tilesToPixels(j), tilesToPixels(i), enemy(), this));
							break;
						// �����A�C�e��
						case 'a':  // �����A�C�e��
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
