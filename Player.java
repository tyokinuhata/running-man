import java.awt.Graphics;
import java.awt.Point;

public class Player extends Sprite {
	// ����
	private static final int RIGHT = 0;
	private static final int LEFT = 1;

	// ���x
	protected double vx;
	protected double vy;

	// �X�s�[�h
	private double speed;
	// �W�����v��
	private double jumpSpeed;

	// ���n���Ă��邩
	private boolean onGround;
	// �ăW�����v�ł��邩
	private boolean forceJump;
	// ��i�W�����v�\�̗͂L��
	private boolean jumperTwo;
	// ��i�W�����v�ł��邩��\���t���O�i���łɓ�i�W�����v���Ȃ�ł��Ȃ��j
	private boolean canJumperTwo;

	// �����Ă������
	private int dir;

/*-------------------------------------------------------------------
�v���C���[�̑�����W�����v�X�s�[�h��ς���
-------------------------------------------------------------------*/
	public Player(double x, double y, String filename, Map map) {
		super(x, y, filename, map);
		vx = 0;
		vy = 80;
		speed = 4.7;		//�����f�t�H���g4.7
		jumpSpeed = 7.1;	//�W�����v�̍����f�t�H���g7.1
		onGround = false;
		forceJump = false;
		jumperTwo = false;
		canJumperTwo = true;
		dir = RIGHT;
	}
	
	// ��~
	public void stop() {
        	vx = 0;
	}

	// ������
	public void accelerateLeft() {
		vx = -speed;
		dir = LEFT;
	}

	// �E����
	public void accelerateRight() {
		vx = speed;
		dir = RIGHT;
	}

	// �W�����v
	public void jump() {
		// �n��ɂ��邩�ăW�����v�\�Ȃ�
		if(onGround || forceJump) {
			// ������ɑ��x��������
			vy = -jumpSpeed;
			onGround = false;
			forceJump = false;
		} else if(jumperTwo && canJumperTwo) {
			// ��i�W�����v�\�͂������A����i�W�����v���łȂ��ꍇ��i�W�����v�\
			vy = -jumpSpeed;
			// ���n����܂ő���s��
			canJumperTwo = false;
		}
	}

	// �v���C���[�̏�ԍX�V
	public void update() {
		// �d�͂ŉ������ɉ����x��������
		vy += Map.GRAVITY;

		// x�����̓����蔻��
		// �ړ�����W�����߂�
		double newX = x + vx;
		// �ړ�����W�ŏՓ˂���^�C���̈ʒu���擾
		// x���������l����̂�y���W�͕ω����Ȃ��Ɖ���
		Point tile = map.getTileCollision(this, newX, y);
		if (tile == null) {
			// �Փ˂���^�C�����Ȃ���Έړ�
			x = newX;
		} else {
			// �Փ˂���^�C��������ꍇ
			if (vx > 0) { // �E�ֈړ����Ȃ̂ŉE�̃u���b�N�ƏՓ�
				// �u���b�N�ɂ߂肱�� or ���Ԃ��Ȃ��悤�Ɉʒu����
				x = Map.tilesToPixels(tile.x) - width;
			} else if (vx < 0) { // ���ֈړ����Ȃ̂ō��̃u���b�N�ƏՓ�
				// �ʒu����
				x = Map.tilesToPixels(tile.x + 1);
			}
			vx = 0;
		}

		// y�����̓����蔻��
		// �ړ�����W�����߂�
		double newY = y + vy;
		// �ړ�����W�ŏՓ˂���^�C���̈ʒu���擾
		// y���������l����ׁAx���W�͕ω����Ȃ��Ɖ���
		tile = map.getTileCollision(this, x, newY);
		if(tile == null) {
			// �Փ˂���^�C�����Ȃ���Έړ�
			y = newY;
			// �Փ˂��ĂȂ��̂ŋ�
			onGround = false;
        	} else {
			// �Փ˂���^�C��������ꍇ
			if(vy > 0) { // ���ֈړ����Ȃ̂ŉ��̃u���b�N�ƏՓˁi���n�j
				// �ʒu����
				y = Map.tilesToPixels(tile.y) - height;
				// ���n�����̂�y�������x��0�ɕύX
				vy = 0;
				// ���n
				onGround = true;
				// ���n����΍Ăѓ�i�W�����v���\
				canJumperTwo = true;
			} else if(vy < 0) { // ��ֈړ����Ȃ̂ŏ�̃u���b�N�ƏՓˁi�V��ɏՓˁj
				// �ʒu����
				y = Map.tilesToPixels(tile.y + 1);
				// �V��ɏՓ˂����̂�y�������x��0�ɕύX
				vy = 0;
				// �R�C���u���b�N�̔���
			}
		}
	}

	// �v���C���[�̕`��i�I�[�o�[���C�h�j
	//@param g �`��I�u�W�F�N�g
	//@param offsetX X�����I�t�Z�b�g
	//@param offsetY Y�����I�t�Z�b�g

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