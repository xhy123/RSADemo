package com.example.rsa;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * 这个类只是用来生成私钥和秘钥
 * @author Kevin
 *
 * 2017下午1:47:18
 */
@SuppressWarnings("unused")
public class Keys
{
	public static final String KEY_ALGORITHM = "RSA";
//	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
	private static final String PUBLIC_KEY = "RSAeiKey";
	private static final String PRIVATE_KEY = "RSAevinKey";

//	public static void main(String[] args) {
//		Map<String, Object> keyMap;
//		try {
//			keyMap = initKey();
//			String publicKey = getPublicKey(keyMap);
//			System.out.println("公钥"+publicKey);
//			String privateKey = getPrivateKey(keyMap);
//			System.out.println("私钥"+privateKey);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	public static String getPublicKey(Map<String, Object> keyMap)
			throws Exception {
		Key key = (Key) keyMap.get(PUBLIC_KEY);
		byte[] publicKey = key.getEncoded();
		return encryptBASE64(key.getEncoded());
	}

	public static String getPrivateKey(Map<String, Object> keyMap)
			throws Exception {
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		byte[] privateKey = key.getEncoded();
		return encryptBASE64(key.getEncoded());
	}

	public static byte[] decryptBASE64(String key) throws Exception {
		return Base64.decode(key) /*(new BASE64Decoder()).decodeBuffer(key)*/;
	}

	public static String encryptBASE64(byte[] key) throws Exception {
		return  Base64.encode(key)  /*(new BASE64Encoder()).encodeBuffer(key)*/;
	}

	public static Map<String, Object> initKey() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator
				.getInstance(KEY_ALGORITHM);
		keyPairGen.initialize(1024);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		Map<String, Object> keyMap = new HashMap<String, Object>(2);
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}
}