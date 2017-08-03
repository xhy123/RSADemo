package com.example.rsa;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import javax.crypto.Cipher;

public class MainActivity extends Activity implements OnClickListener
{
	private Button btn1, btn2;// 加密，解密
	private EditText et1, et2, et3;// 需加密的内容，加密后的内容，解密后的内容

	/* 密钥内容 base64 code */
	private static String PUCLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCfRTdcPIH10gT9f31rQuIInLwe"
			+ "\r" + "7fl2dtEJ93gTmjE9c2H+kLVENWgECiJVQ5sonQNfwToMKdO0b3Olf4pgBKeLThra" + "\r"
			+ "z/L3nYJYlbqjHC3jTjUnZc0luumpXGsox62+PuSGBlfb8zJO6hix4GV/vhyQVCpG" + "\r"
			+ "9aYqgE7zyTRZYX9byQIDAQAB" + "\r";
	private static String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJ9FN1w8gfXSBP1/"
			+ "\r" + "fWtC4gicvB7t+XZ20Qn3eBOaMT1zYf6QtUQ1aAQKIlVDmyidA1/BOgwp07Rvc6V/" + "\r"
			+ "imAEp4tOGtrP8vedgliVuqMcLeNONSdlzSW66alcayjHrb4+5IYGV9vzMk7qGLHg" + "\r"
			+ "ZX++HJBUKkb1piqATvPJNFlhf1vJAgMBAAECgYA736xhG0oL3EkN9yhx8zG/5RP/" + "\r"
			+ "WJzoQOByq7pTPCr4m/Ch30qVerJAmoKvpPumN+h1zdEBk5PHiAJkm96sG/PTndEf" + "\r"
			+ "kZrAJ2hwSBqptcABYk6ED70gRTQ1S53tyQXIOSjRBcugY/21qeswS3nMyq3xDEPK" + "\r"
			+ "XpdyKPeaTyuK86AEkQJBAM1M7p1lfzEKjNw17SDMLnca/8pBcA0EEcyvtaQpRvaL" + "\r"
			+ "n61eQQnnPdpvHamkRBcOvgCAkfwa1uboru0QdXii/gUCQQDGmkP+KJPX9JVCrbRt" + "\r"
			+ "7wKyIemyNM+J6y1ZBZ2bVCf9jacCQaSkIWnIR1S9UM+1CFE30So2CA0CfCDmQy+y" + "\r"
			+ "7A31AkB8cGFB7j+GTkrLP7SX6KtRboAU7E0q1oijdO24r3xf/Imw4Cy0AAIx4KAu" + "\r"
			+ "L29GOp1YWJYkJXCVTfyZnRxXHxSxAkEAvO0zkSv4uI8rDmtAIPQllF8+eRBT/deD" + "\r"
			+ "JBR7ga/k+wctwK/Bd4Fxp9xzeETP0l8/I+IOTagK+Dos8d8oGQUFoQJBAI4Nwpfo" + "\r"
			+ "MFaLJXGY9ok45wXrcqkJgM+SN6i8hQeujXESVHYatAIL/1DgLi+u46EFD69fw0w+" + "\r" + "c7o0HLlMsYPAzJw="
			+ "\r";

	private static String PUCLIC_KEY1;
	private static String PRIVATE_KEY1;
	private Button btn3;
	private EditText et4;
	private EditText et5;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView()
	{
		btn1 = (Button) findViewById(R.id.btn1);
		btn2 = (Button) findViewById(R.id.btn2);
		btn3 = (Button) findViewById(R.id.btn3);
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);

		et1 = (EditText) findViewById(R.id.et1);
		et2 = (EditText) findViewById(R.id.et2);
		et3 = (EditText) findViewById(R.id.et3);
		et4 = (EditText) findViewById(R.id.et4);
		et5 = (EditText) findViewById(R.id.et5);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		// 加密
		case R.id.btn1:
			String source = et1.getText().toString().trim();
			try
			{
				// 从字符串中得到公钥
				 PublicKey publicKey = RSAUtils.loadPublicKey(PUCLIC_KEY1);
				// 从文件中得到公钥
//				InputStream inPublic = getResources().getAssets().open("rsa_public_key.pem");
//				PublicKey publicKey = RSAUtils.loadPublicKey(inPublic);
				// 加密
				byte[] encryptByte = RSAUtils.encryptData(source.getBytes(), publicKey);
				// 为了方便观察吧加密后的数据用base64加密转一下，要不然看起来是乱码,所以解密是也是要用Base64先转换
				String afterencrypt = Base64Utils.encode(encryptByte);
				et2.setText(afterencrypt);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			break;
		// 解密
		case R.id.btn2:
			String encryptContent = et2.getText().toString().trim();
			try
			{
				// 从字符串中得到私钥
				 PrivateKey privateKey = RSAUtils.loadPrivateKey(PRIVATE_KEY1);
				// 从文件中得到私钥
				//InputStream inPrivate = getResources().getAssets().open("pkcs8_rsa_private_key.pem");
				//PrivateKey privateKey = RSAUtils.loadPrivateKey(inPrivate);
				// 因为RSA加密后的内容经Base64再加密转换了一下，所以先Base64解密回来再给RSA解密
				byte[] decryptByte = RSAUtils.decryptData(Base64Utils.decode(encryptContent), privateKey);
				String decryptStr = new String(decryptByte);
				et3.setText(decryptStr);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			break;
			case R.id.btn3:
				Map<String, Object> keyMap;
				try {
					keyMap = Keys.initKey();
					String publicKey = Keys.getPublicKey(keyMap);
					PUCLIC_KEY1 = publicKey;
					System.out.println("公钥" + publicKey);
					et4.setText("公钥 \n" + publicKey);
					String privateKey = Keys.getPrivateKey(keyMap);
					PRIVATE_KEY1 = privateKey;
					System.out.println("私钥" + privateKey);
					et5.setText("私钥\n" + privateKey);


				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
		default:
			break;
		}
	}




	public static final String RSA = "RSA";// 非对称加密密钥算法
	public static final String ECB_PKCS1_PADDING = "RSA/ECB/PKCS1Padding";//加密填充方式
	public static final int DEFAULT_KEY_SIZE = 2048;//秘钥默认长度
	public static final byte[] DEFAULT_SPLIT = "#PART#".getBytes();    // 当要加密的内容超过bufferSize，则采用partSplit进行分块加密
	public static final int DEFAULT_BUFFERSIZE = (DEFAULT_KEY_SIZE / 8) - 11;// 当前秘钥支持加密的最大字节数

	/**
	 *  随机生成RSA密钥对
	 * @param keyLength
	 * @return
     */
	public static KeyPair generateRSAKeyPair(int keyLength) {
		try {
			KeyPairGenerator kpg = KeyPairGenerator.getInstance(RSA);
			kpg.initialize(keyLength);
			return kpg.genKeyPair();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 用公钥对字符串进行加密
	 * @param data
	 * @param publicKey
	 * @return
	 * @throws Exception
     */
	public static byte[] encryptByPublicKey(byte[] data, byte[] publicKey) throws Exception {
		// 得到公钥
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
		KeyFactory kf = KeyFactory.getInstance(RSA);
		PublicKey keyPublic = kf.generatePublic(keySpec);
		// 加密数据
		Cipher cp = Cipher.getInstance(ECB_PKCS1_PADDING);
		cp.init(Cipher.ENCRYPT_MODE, keyPublic);
		return cp.doFinal(data);
	}


	/**
	 * 私钥加密
	 * @param data
	 * @param privateKey
	 * @return
	 * @throws Exception
     */
	public static byte[] encryptByPrivateKey(byte[] data, byte[] privateKey) throws Exception {
		// 得到私钥
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey);
		KeyFactory kf = KeyFactory.getInstance(RSA);
		PrivateKey keyPrivate = kf.generatePrivate(keySpec);
		// 数据加密
		Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING);
		cipher.init(Cipher.ENCRYPT_MODE, keyPrivate);
		return cipher.doFinal(data);
	}


	/**
	 * 公钥解密
	 * @param data
	 * @param publicKey
	 * @return
	 * @throws Exception
     */
	public static byte[] decryptByPublicKey(byte[] data, byte[] publicKey) throws Exception {
		// 得到公钥
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
		KeyFactory kf = KeyFactory.getInstance(RSA);
		PublicKey keyPublic = kf.generatePublic(keySpec);
		// 数据解密
		Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING);
		cipher.init(Cipher.DECRYPT_MODE, keyPublic);
		return cipher.doFinal(data);
	}


	/**
	 * 使用私钥进行解密
	 * @param encrypted
	 * @param privateKey
	 * @return
	 * @throws Exception
     */
	public static byte[] decryptByPrivateKey(byte[] encrypted, byte[] privateKey) throws Exception {
		// 得到私钥
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey);
		KeyFactory kf = KeyFactory.getInstance(RSA);
		PrivateKey keyPrivate = kf.generatePrivate(keySpec);

		// 解密数据
		Cipher cp = Cipher.getInstance(ECB_PKCS1_PADDING);
		cp.init(Cipher.DECRYPT_MODE, keyPrivate);
		byte[] arr = cp.doFinal(encrypted);
		return arr;
	}

	/**
	 * 用公钥对字符串进行分段加密
	 * @param data
	 * @param publicKey
	 * @return
	 * @throws Exception
     */
	public static byte[] encryptByPublicKeyForSpilt(byte[] data, byte[] publicKey) throws Exception {
		int dataLen = data.length;
		if (dataLen <= DEFAULT_BUFFERSIZE) {
			return encryptByPublicKey(data, publicKey);
		}
		List<Byte> allBytes = new ArrayList<Byte>(2048);
		int bufIndex = 0;
		int subDataLoop = 0;
		byte[] buf = new byte[DEFAULT_BUFFERSIZE];
		for (int i = 0; i < dataLen; i++) {
			buf[bufIndex] = data[i];
			if (++bufIndex == DEFAULT_BUFFERSIZE || i == dataLen - 1) {
				subDataLoop++;
				if (subDataLoop != 1) {
					for (byte b : DEFAULT_SPLIT) {
						allBytes.add(b);
					}
				}
				byte[] encryptBytes = encryptByPublicKey(buf, publicKey);
				for (byte b : encryptBytes) {
					allBytes.add(b);
				}
				bufIndex = 0;
				if (i == dataLen - 1) {
					buf = null;
				} else {
					buf = new byte[Math.min(DEFAULT_BUFFERSIZE, dataLen - i - 1)];
				}
			}
		}
		byte[] bytes = new byte[allBytes.size()];
		{
			int i = 0;
			for (Byte b : allBytes) {
				bytes[i++] = b.byteValue();
			}
		}
		return bytes;
	}


	/**
	 * 分段加密
	 * @param data
	 * @param privateKey
	 * @return
	 * @throws Exception
     */
	public static byte[] encryptByPrivateKeyForSpilt(byte[] data, byte[] privateKey) throws Exception {
		int dataLen = data.length;
		if (dataLen <= DEFAULT_BUFFERSIZE) {
			return encryptByPrivateKey(data, privateKey);
		}
		List<Byte> allBytes = new ArrayList<Byte>(2048);
		int bufIndex = 0;
		int subDataLoop = 0;
		byte[] buf = new byte[DEFAULT_BUFFERSIZE];
		for (int i = 0; i < dataLen; i++) {
			buf[bufIndex] = data[i];
			if (++bufIndex == DEFAULT_BUFFERSIZE || i == dataLen - 1) {
				subDataLoop++;
				if (subDataLoop != 1) {
					for (byte b : DEFAULT_SPLIT) {
						allBytes.add(b);
					}
				}
				byte[] encryptBytes = encryptByPrivateKey(buf, privateKey);
				for (byte b : encryptBytes) {
					allBytes.add(b);
				}
				bufIndex = 0;
				if (i == dataLen - 1) {
					buf = null;
				} else {
					buf = new byte[Math.min(DEFAULT_BUFFERSIZE, dataLen - i - 1)];
				}
			}
		}
		byte[] bytes = new byte[allBytes.size()];
		{
			int i = 0;
			for (Byte b : allBytes) {
				bytes[i++] = b.byteValue();
			}
		}
		return bytes;
	}


	/**
	 * 公钥分段解密
	 * @param encrypted
	 * @param publicKey
	 * @return
	 * @throws Exception
     */
	public static byte[] decryptByPublicKeyForSpilt(byte[] encrypted, byte[] publicKey) throws Exception {
		int splitLen = DEFAULT_SPLIT.length;
		if (splitLen <= 0) {
			return decryptByPublicKey(encrypted, publicKey);
		}
		int dataLen = encrypted.length;
		List<Byte> allBytes = new ArrayList<Byte>(1024);
		int latestStartIndex = 0;
		for (int i = 0; i < dataLen; i++) {
			byte bt = encrypted[i];
			boolean isMatchSplit = false;
			if (i == dataLen - 1) {
				// 到data的最后了
				byte[] part = new byte[dataLen - latestStartIndex];
				System.arraycopy(encrypted, latestStartIndex, part, 0, part.length);
				byte[] decryptPart = decryptByPublicKey(part, publicKey);
				for (byte b : decryptPart) {
					allBytes.add(b);
				}
				latestStartIndex = i + splitLen;
				i = latestStartIndex - 1;
			} else if (bt == DEFAULT_SPLIT[0]) {
				// 这个是以split[0]开头
				if (splitLen > 1) {
					if (i + splitLen < dataLen) {
						// 没有超出data的范围
						for (int j = 1; j < splitLen; j++) {
							if (DEFAULT_SPLIT[j] != encrypted[i + j]) {
								break;
							}
							if (j == splitLen - 1) {
								// 验证到split的最后一位，都没有break，则表明已经确认是split段
								isMatchSplit = true;
							}
						}
					}
				} else {
					// split只有一位，则已经匹配了
					isMatchSplit = true;
				}
			}
			if (isMatchSplit) {
				byte[] part = new byte[i - latestStartIndex];
				System.arraycopy(encrypted, latestStartIndex, part, 0, part.length);
				byte[] decryptPart = decryptByPublicKey(part, publicKey);
				for (byte b : decryptPart) {
					allBytes.add(b);
				}
				latestStartIndex = i + splitLen;
				i = latestStartIndex - 1;
			}
		}
		byte[] bytes = new byte[allBytes.size()];
		{
			int i = 0;
			for (Byte b : allBytes) {
				bytes[i++] = b.byteValue();
			}
		}
		return bytes;
	}


	/**
	 * 使用私钥分段解密
	 * @param encrypted
	 * @param privateKey
	 * @return
	 * @throws Exception
     */
	public static byte[] decryptByPrivateKeyForSpilt(byte[] encrypted, byte[] privateKey) throws Exception {
		int splitLen = DEFAULT_SPLIT.length;
		if (splitLen <= 0) {
			return decryptByPrivateKey(encrypted, privateKey);
		}
		int dataLen = encrypted.length;
		List<Byte> allBytes = new ArrayList<Byte>(1024);
		int latestStartIndex = 0;
		for (int i = 0; i < dataLen; i++) {
			byte bt = encrypted[i];
			boolean isMatchSplit = false;
			if (i == dataLen - 1) {
				// 到data的最后了
				byte[] part = new byte[dataLen - latestStartIndex];
				System.arraycopy(encrypted, latestStartIndex, part, 0, part.length);
				byte[] decryptPart = decryptByPrivateKey(part, privateKey);
				for (byte b : decryptPart) {
					allBytes.add(b);
				}
				latestStartIndex = i + splitLen;
				i = latestStartIndex - 1;
			} else if (bt == DEFAULT_SPLIT[0]) {
				// 这个是以split[0]开头
				if (splitLen > 1) {
					if (i + splitLen < dataLen) {
						// 没有超出data的范围
						for (int j = 1; j < splitLen; j++) {
							if (DEFAULT_SPLIT[j] != encrypted[i + j]) {
								break;
							}
							if (j == splitLen - 1) {
								// 验证到split的最后一位，都没有break，则表明已经确认是split段
								isMatchSplit = true;
							}
						}
					}
				} else {
					// split只有一位，则已经匹配了
					isMatchSplit = true;
				}
			}
			if (isMatchSplit) {
				byte[] part = new byte[i - latestStartIndex];
				System.arraycopy(encrypted, latestStartIndex, part, 0, part.length);
				byte[] decryptPart = decryptByPrivateKey(part, privateKey);
				for (byte b : decryptPart) {
					allBytes.add(b);
				}
				latestStartIndex = i + splitLen;
				i = latestStartIndex - 1;
			}
		}
		byte[] bytes = new byte[allBytes.size()];
		{
			int i = 0;
			for (Byte b : allBytes) {
				bytes[i++] = b.byteValue();
			}
		}
		return bytes;
	}


}
