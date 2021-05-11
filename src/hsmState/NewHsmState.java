package hsmState;

public class NewHsmState {

	public static void main(String[] args) {
		new Thread(new HsmStateThread()).start();
	}
}
