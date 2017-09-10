/**
 * 
 */
package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author zhaowei
 * 
 */
public final class EncryptUtil {

	public EncryptUtil() {
	}

	/**
	 * MD2加密算法
	 */
	private static final String ENCRYPT_TYPE_MD2 = "MD2";

	/**
	 * MD5加密算法
	 */
	private static final String ENCRYPT_TYPE_MD5 = "MD5";

	/**
	 * SHA-1加密算法
	 */
	private static final String ENCRYPT_TYPE_SHA1 = "SHA-1";

	/**
	 * SHA-256加密算法
	 */
	private static final String ENCRYPT_TYPE_SHA256 = "SHA-256";

	/**
	 * SHA-384加密算法
	 */
	private static final String ENCRYPT_TYPE_MD384 = "SHA-384";

	/**
	 * SHA-512加密算法
	 */
	private static final String ENCRYPT_TYPE_MD512 = "SHA-512";

	/**
	 * MD2加密算法
	 * 
	 * @param text
	 *            需要加密的字符串
	 * @return String型 加密后的字符串
	 */
	public static String getMD2EncryptString(String text) {
		return getEncryptString(text, ENCRYPT_TYPE_MD2);
	}

	/**
	 * MD5加密算法
	 * 
	 * @param text
	 *            需要加密的字符串
	 * @return String型 加密后的字符串
	 */
	public static String getMD5EncryptString(String text) {
		return getEncryptString(text, ENCRYPT_TYPE_MD5);
	}

	/**
	 * SHA-1加密算法
	 * 
	 * @param text
	 *            需要加密的字符串
	 * @return String型 加密后的字符串
	 */
	public static String getSHA1EncryptString(String text) {
		return getEncryptString(text, ENCRYPT_TYPE_SHA1);
	}

	/**
	 * SHA-256加密算法
	 * 
	 * @param text
	 *            需要加密的字符串
	 * @return String型 加密后的字符串
	 */
	public static String getSHA256EncryptString(String text) {
		return getEncryptString(text, ENCRYPT_TYPE_SHA256);
	}

	/**
	 * SHA-384加密算法
	 * 
	 * @param text
	 *            需要加密的字符串
	 * @return String型 加密后的字符串
	 */
	public static String getSHA384EncryptString(String text) {
		return getEncryptString(text, ENCRYPT_TYPE_MD384);
	}

	/**
	 * SHA-512加密算法
	 * 
	 * @param plantText
	 *            需要加密的字符串
	 * @return String型 加密后的字符串
	 */
	public static String getSHA512EncryptString(String text) {
		return getEncryptString(text, ENCRYPT_TYPE_MD512);
	}

	/**
	 * 数据加密算法
	 * 
	 * @param text
	 *            加密字符串(32位)
	 * @param type
	 *            加密算法，支持SHA-1,SHA-2(SHA-256,SHA-384,SHA-512),MD2,MD5
	 * @return 加密结果
	 */
	private static String getEncryptString(String text, String type) {

		try {
			MessageDigest md = MessageDigest.getInstance(type);
			md.update(text.getBytes());
			byte[] b = md.digest();
			StringBuilder output = new StringBuilder(32);
			for (int i = 0; i < b.length; i++) {
				String temp = Integer.toHexString(b[i] & 0xff);
				if (temp.length() < 2) {
					output.append("0");// 不足两位，补0
				}
				output.append(temp);
			}
			return output.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		String encrypedText = getSHA256EncryptString("1");
		System.out.println(encrypedText);
	}

}
