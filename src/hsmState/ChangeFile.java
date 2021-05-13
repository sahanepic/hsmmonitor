package hsmState;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class ChangeFile {
	
	public static Boolean hsm1connect = false;
	public static Boolean hsm2connect = false;
	
	public static void main(String[] args) throws Exception {
//		 hsm1set();   
		String us = getHsmStatus();
		
		if(us == null) {
			ChangeFile.hsm1connect =false;
			ChangeFile.hsm2connect =false;
		    hsm1set();
		    us = getHsmStatus();
		    if(us != null) {
		    	ChangeFile.hsm1connect =true;
		    	System.out.println("hhhhhhhhhhhhh Hsm 1  connected");
		    }
		    Thread.sleep(9000);
		    if(ChangeFile.hsm1connect ==false) {
		    	hsm2set();
		    	us = getHsmStatus();
			    if(us != null) {
			    	ChangeFile.hsm2connect =true;
			    	System.out.println("hhhhhhhhhhhhh Hsm 2  connected");
			    }
		    	
		    }
		    
		}
		
		
	}
	
	public static void hsm1set() {
		try {
			FileWriter myWriter = new FileWriter("/etc/default/et_hsm");
		      myWriter.write("ET_HSM_NETCLIENT_SERVERLIST=\n#192.168.20.121\r\n"
		      		+ "#ET_HSM_NETCLIENT_SERVERLIST=192.168.1.215\r\n #sahanbcs ");
		      myWriter.close();
		      System.out.println("Successfully wrote to the file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		
	}

	
	public static void hsm2set() {
		try {
		      FileWriter myWriter = new FileWriter("/etc/default/et_hsm");
		      myWriter.write("#ET_HSM_NETCLIENT_SERVERLIST=192.168.20.121\r\n"
		      		+ "ET_HSM_NETCLIENT_SERVERLIST=192.168.1.215\r\n#sahanbcs");
		      myWriter.close();
		      System.out.println("Successfully wrote to the file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
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
