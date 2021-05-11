package hsmState;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class HsmStateThread extends Thread{

	
	private Boolean isHsmState = false;
	
	private String sss = "";
 
	
		
	@Override
	public void run() {
	 
		 while (true) {	 
			 try {
				sss = getHsmStatus();
				isHsmState = true;
				if(sss.contains("Cannot initialize")) {
					isHsmState = false;
				}
			} catch (Exception e) {
				isHsmState = false;
				// TODO: handle exception
			}
			 finally {
				System.out.println("The Hsm State " + isHsmState + "  res  " + sss );
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			 
		}
		 
}
	
	
	
	public static String getHsmStatus() throws Exception {

		Runtime rt = null;
		Process pr = null;
		BufferedReader input = null;
		InputStreamReader inst = null;

		String usage = null;
		try {
			rt = Runtime.getRuntime();

			pr = rt.exec("hsmstate");

			inst = new InputStreamReader(pr.getInputStream());

			input = new BufferedReader(inst);

			String rs = "";

			if ((rs = input.readLine()) != null) {
				System.out.println(rs);
				if (rs.contains("NORMAL MODE")) {

					int i = rs.indexOf("=");
					usage = rs.substring(i + 1);

				}

			}

			int exitVal = pr.waitFor();

			if (exitVal == 0) {

			}

			rs = null;
		} catch (Exception e) {

			throw e;

		} finally {
			if (input != null)
				input.close();
			if (inst != null)
				inst.close();
			if (pr != null)
				pr.destroy();

			input = null;
			inst = null;
			pr = null;
			rt = null;
		}

		return usage;

	}
	
}
