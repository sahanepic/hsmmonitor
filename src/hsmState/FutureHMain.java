package hsmState;

public class FutureHMain extends Thread{

	
	public static void main(String[] args) {
		
		new Thread(new FutureHsmState()).start();;
	}
	
}
