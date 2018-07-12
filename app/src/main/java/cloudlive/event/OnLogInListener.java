package cloudlive.event;

public interface OnLogInListener {
	void logInStart();
	void logInCancel();
	void logInCompleted();
}
