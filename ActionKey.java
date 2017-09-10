public class ActionKey {
	// �L�[�̃��[�h
	// �L�[��������Ă���Ԃ�isPressed()��true��Ԃ�
	public static final int NORMAL = 0;

	// �L�[���͂��߂ɉ����ꂽ�Ƃ�����isPressed()��true��Ԃ�
	// �L�[�������ꑱ���Ă�2��ڈȍ~��false��Ԃ�
	// ���̃��[�h���g���ƃW�����v�{�^�������������Ă��W�����v���J��Ԃ��Ȃ�
	public static final int DETECT_INITIAL_PRESS_ONLY = 1;
    
	// �L�[�̏��
	// �L�[�������ꂽ
	private static final int STATE_RELEASED = 0;

	// �L�[��������Ă���
	private static final int STATE_PRESSED = 1;

	// �L�[���������̂�҂��Ă���
	private static final int STATE_WAITING_FOR_RELEASE = 2;

	// �L�[�̃��[�h
	private int mode;

	// �L�[�������ꂽ��
	private int amount;

	// �L�[�̏��
	private int state;
    
	// ���ʂ̃R���X�g���N�^�ł̓m�[�}�����[�h
	public ActionKey() {
		this(NORMAL);
	}
    
	// ���[�h���w��ł���R���X�g���N�^
	// @param mode �L�[�̃��[�h
	public ActionKey(int mode) {
		this.mode = mode;

		reset();
	}

	// �L�[�̏�Ԃ����Z�b�g
	public void reset() {
		state = STATE_RELEASED;

		amount = 0;
	}
    
	// �L�[�������ꂽ�Ƃ��Ăяo��
	public void press() {
		// STATE_WAITING_FOR_RELEASE�̂Ƃ��͉����ꂽ���ƂɂȂ�Ȃ�

		if(state != STATE_WAITING_FOR_RELEASE) {
			amount++;
			state = STATE_PRESSED;
		}
	}
    
	// �L�[�������ꂽ�Ƃ��Ăяo��
	public void release() {
		state = STATE_RELEASED;
	}
    
	// �L�[�������ꂽ��
	// @return �����ꂽ��true��Ԃ�
	public boolean isPressed() {
		if (amount != 0) {
			if(state == STATE_RELEASED) {
				amount = 0;
			} else if(mode == DETECT_INITIAL_PRESS_ONLY) {
				// �ŏ���1�񂾂�true��Ԃ��ĉ����ꂽ���Ƃɂ���
				// ���񂩂��STATE_WAITING_FOR_RELEASE�ɂȂ邽��
				// �L�[�����������Ă������ꂽ���ƂɂȂ�Ȃ�
				state = STATE_WAITING_FOR_RELEASE;

				amount = 0;
			}
			return true;
		}
		return false;
	}
}