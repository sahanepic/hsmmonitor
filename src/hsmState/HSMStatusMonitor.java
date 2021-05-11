package hsmState;

import java.security.KeyStore;
import java.security.Provider;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import org.jpos.iso.ISOUtil;

 

import au.com.safenet.crypto.WrappingKeyStore;

public class HSMStatusMonitor implements Runnable {

	private static Provider p = new au.com.safenet.crypto.provider.SAFENETProvider(0);
	private static byte DATA[] = ISOUtil.hex2byte("8FA623E44821B10299F2701809F3303");
	private static boolean hsmStatus = false;
	private static KeyStore KEYSTORE = null;
	private static WrappingKeyStore WRAPKEYSTORE = null;
	private static SecretKey LMK = null;

	public static boolean isHsmStatus() {
		return hsmStatus;
	}

	private static void hsmTest() throws Exception {
		Security.addProvider(p);
		KEYSTORE = KeyStore.getInstance("CRYPTOKI", p.getName());
		KEYSTORE.load(null,  "123456".toCharArray());
		WRAPKEYSTORE = WrappingKeyStore.getInstance("CRYPTOKI", p.getName());
		LMK = (SecretKey) KEYSTORE.getKey("LMK", null);
		
		Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding", p.getName());
//		KeyGenerator kgen = KeyGenerator.getInstance("AES");
//		kgen.init(256);
//		SecretKey skey = kgen.generateKey();
		cipher.init(Cipher.ENCRYPT_MODE, LMK , new IvParameterSpec(new byte[8]));

		cipher.doFinal(DATA);

	}

	@Override
	public void run() {

		System.out.println("Provider Adding");
		Security.addProvider(p);

		while (true) {
			System.out.println("HHHHHHH");
			try {
				Thread.sleep(1000);
				hsmTest();
				hsmStatus = true;

			} catch (Exception e) {
				System.out.println("Exception Occur");
				 e.printStackTrace();
				hsmStatus = false;

			}finally {
				System.out.println("The Status is" + hsmStatus);
			}

		}

	}

}