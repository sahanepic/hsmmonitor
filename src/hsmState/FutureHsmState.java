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

public class FutureHsmState extends Thread {
	
	
private Boolean isHsmState = false;
	
	private String sss = "";
	ExecutorService executor = Executors.newCachedThreadPool();
	Callable<Object> task = new Callable<Object>() {
		   public Object call() throws Exception {
		      return  getHsmStatus();
		   }
		};
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		System.out.println("Hello new Future ");
		
		Future<Object> future = executor.submit(task);
		 while (true) {
			 try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 try {
				   Object result = future.get(1, TimeUnit.MINUTES); 
				  System.out.println("after minits");
				   sss =  (String) result;
				   if(sss != null)
					   isHsmState = true;
				   else {
					   isHsmState = false;
				}
			 } catch (TimeoutException ex) {
				   // handle the timeout
					System.out.println("The Time Out Exception");
					isHsmState = false;
				} catch (InterruptedException e) {
					System.out.println("The Time Out InterruptedException");
					isHsmState = false;
				} catch (ExecutionException e) {
				   // handle other exceptions
					System.out.println("The Time Out ExecutionException");
					isHsmState = false;
				}catch (Exception e) {
					System.out.println("The Exception");
					isHsmState = false;
				}finally {
					
				   future.cancel(true); // may or may not desire this
				   System.out.println("The Result is " + sss + " The State " + isHsmState);
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
