import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Point;

public class Kuribo extends Sprite {
	// �X�s�[�h
	private static final double SPEED = 1;

	// ���x
	protected double vx;
	protected double vy;

	// ���܂ꂽ�Ƃ��̉�
	private AudioClip sound;

	public Kuribo(double x, double y, String filename, Map map) {
		super(x, y, filename, map);

		// ���Ɉړ��𑱂���
		vx = -SPEED;
		vy = 0;
        
		// �T�E���h�̃��[�h
		sound = Applet.newAudioClip(getClass().getResource("sound/gucha.wav"));
	}

	public void update() {
		// �d�͂ŉ������ɉ����x��������
		vy+=Map.GRAVITY;

		// x�����̓����蔻��
		// �ړ�����W�����߂�
		double newX = x + vx;

		// �ړ�����W�ŏՓ˂���^�C���̈ʒu���擾
		// x���������l����̂�y���W�͕ω����Ȃ��Ɖ���
		Point tile = map.getTileCollision(this, newX, y);

		if(tile == null) {
			// �Փ˂���^�C�����Ȃ���Έړ�
			x = newX;
		} else {
			// �Փ˂���^�C��������ꍇ
			// �E�ֈړ����Ȃ̂ŉE�̃u���b�N�ƏՓ�
			if(vx > 0) {

			// �u���b�N�ɂ߂肱�� or ���Ԃ��Ȃ��悤�Ɉʒu����
			x = Map.tilesToPixels(tile.x) - width;

			// ���ֈړ����Ȃ̂ō��̃u���b�N�ƏՓ�
			} else if(vx < 0) {
				// �ʒu����
				x = Map.tilesToPixels(tile.x + 1);
            		}

			// �ړ������̔��]
			vx = -vx;
        	}

	        // y�����̓����蔻��
        	// �ړ�����W�����߂�
		double newY = y + vy;

		// �ړ�����W�ŏՓ˂���^�C���̈ʒu���擾
		// y���������l����̂�x���W�͕ω����Ȃ��Ɖ���
		tile = map.getTileCollision(this, x, newY);

		if(tile == null) {
			// �Փ˂���^�C�����Ȃ���Έړ�
			y = newY;
		} else {
			// �Փ˂���^�C��������ꍇ
			if (vy > 0) { // ���ֈړ����Ȃ̂ŉ��̃u���b�N�ƏՓˁi���n�j

			// �ʒu����
			y = Map.tilesToPixels(tile.y) - height;

			// ���n�����̂�y�������x��0��
			vy = 0;

			// ��ֈړ����̈׏�̃u���b�N�ƓV�䂪�Փ�
			} else if(vy < 0) {
				// �ʒu����
				y = Map.tilesToPixels(tile.y + 1);

				// �V��ɂԂ������̂�y�������x��0��
				vy = 0;
			}
		}
	}
    
	//�T�E���h�̍Đ�
	public void play() {
		sound.play();
	}
}