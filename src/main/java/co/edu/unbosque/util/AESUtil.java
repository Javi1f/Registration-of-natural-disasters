package co.edu.unbosque.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static org.apache.commons.codec.binary.Base64.*;

public class AESUtil {

	private static final String ALGORITHM = "AES";
	private static final String CIPHER_TYPE = "AES/CBC/PKCS5Padding";

	public static String encrypt(String txt) {
		String iv = "helloworldivfunc";
		String key = "llavequeserecuer";

		return encrypt(key, iv, txt);
	}

	public static String encryptForFront(String txt) {
		String iv = "milloscampeon016";
		String key = "novaaganarosital";

		return encrypt(key, iv, txt);
	}

	public static String encrypt(String key, String iv, String text) {

		Cipher cipher = null;
		byte[] encripted = null;

		try {
			cipher = Cipher.getInstance(CIPHER_TYPE);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), ALGORITHM);
		IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());

		try {
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
		} catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			encripted = cipher.doFinal(text.getBytes());
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new String(encodeBase64(encripted));
	}

	public static String decrypt(String txt) {
		String iv = "helloworldivfunc";
		String key = "llavequeserecuer";

		return decrypt(key, iv, txt);
	}

	public static String decryptFromFront(String txt) {
		String iv = "milloscampeon016";
		String key = "novaaganarosital";

		return decrypt(key, iv, txt);
	}

	public static String decrypt(String key, String iv, String text) {
		Cipher cipher = null;

		try {
			cipher = Cipher.getInstance(CIPHER_TYPE);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		}

		SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), ALGORITHM);
		IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());

		try {
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
		} catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] encripted = decodeBase64(text);
		byte[] decrypted = null;

		try {
			decrypted = cipher.doFinal(encripted);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new String(decrypted);
	}

//	public static void main(String[] args) {
//		String prueba = "Hello world";
//
//		String encriptado = encrypt(prueba);
//
//		System.out.println(encriptado);
//
//		String desencriptado = decrypt(encriptado);
//
//		System.out.println(desencriptado);
//	}

}
