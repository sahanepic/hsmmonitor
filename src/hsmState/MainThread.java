package hsmState;

import org.tanukisoftware.wrapper.WrapperSimpleApp;

public class MainThread extends WrapperSimpleApp{

	protected MainThread(String[] arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		System.out.println("Hello World");
		HSMStatusMonitor ss = new HSMStatusMonitor();
		new Thread(ss).start();
	}
}
