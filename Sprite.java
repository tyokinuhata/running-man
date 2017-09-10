import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public abstract class Sprite {
	// �ʒu
	protected double x;
	protected double y;
    
	// ��
	protected int width;
	// ����
	protected int height;
    
	// �X�v���C�g�摜
	protected Image image;

	// �A�j���[�V�����p�J�E���^
	protected int count;

	// �}�b�v�ւ̎Q��
	protected Map map;
    
	int ani = 0;

	public Sprite(double x, double y, String fileName, Map map) {
		this.x = x;
		this.y = y;
		this.map = map;

		width = 32;
		height = 32;

		// �C���[�W�̃��[�h
		loadImage(fileName);

		count = 0;
        
		// �A�j���[�V�����p�X���b�h�̊J�n
		AnimationThread thread = new AnimationThread();
		thread.start();
	}

	public Sprite(double x, double y, String fileName, Map map,int i) {
		this.x = x;
		this.y = y;
		this.map = map;

		width = 32;
		height = 32;

		// �C���[�W�̃��[�h
		loadImage(fileName);

		count = 0;
		ani = i;
        
		// �A�j���[�V�����p�X���b�h�̊J�n
		AnimationThread thread = new AnimationThread();
		thread.start();
	}

	//�X�v���C�g�̏�Ԃ��X�V����
	public abstract void update();

	//�X�v���C�g�̕`��
	//@param g �`��I�u�W�F�N�g
	//@param offsetX X�����I�t�Z�b�g
	//@param offsetY Y�����I�t�Z�b�g
	public void draw(Graphics g, int offsetX, int offsetY) {
		g.drawImage(image, (int) x + offsetX, (int) y + offsetY,
			    (int) x + offsetX + width, (int) y + offsetY + height,
			    count * width, 0, count * width + width, height, null);
	}

	// ���̃X�v���C�g�ƐڐG���Ă��邩�̔���
	// @param sprite �X�v���C�g
	public boolean isCollision(Sprite sprite) {
		Rectangle playerRect = new Rectangle((int)x, (int)y, width, height);
		Rectangle spriteRect = new Rectangle((int)sprite.getX(), (int)sprite.getY(), sprite.getWidth(), sprite.getHeight());

		// �����̋�`�Ƒ���̋�`���d�Ȃ��Ă��邩�̔���
		if(playerRect.intersects(spriteRect)) {
			return true;
		}
		return false;
	}

	// @return Returns the x.
	public double getX() {
		return x;
	}

	// @return Returns the y.
	public double getY() {
		return y;
	}

	// @return Returns the width.
	public int getWidth() {
		return width;
	}

	// @return Returns the height.
	public int getHeight() {
		return height;
	}

	// �C���[�W�̃��[�h
	// @param filename �C���[�W�t�@�C����
	private void loadImage(String filename) {
		ImageIcon icon = new ImageIcon(getClass().getResource("image/" + filename));
		image = icon.getImage();
	}

	// �A�j���[�V�����p�X���b�h
	protected class AnimationThread extends Thread {
		public void run() {
			while (true) {
				// count�̐؂�ւ�
				if(ani != 1){
					if (count == 0) {
						count = 1;
					} else if (count == 1) {
						count = 0;
					}
				// 300�~���b�x�~(300�~���b�����ɗE�҂̊G��؂�ւ�)
				} try {
					Thread.sleep(300);
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}