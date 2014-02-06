package org.CommunityService.Validators;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordHash {

	public static String getHash(String pass, String saltString) throws NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] salt = saltString.getBytes(Charset.forName("UTF-8"));
		PBEKeySpec spec = new PBEKeySpec(pass.toCharArray(), salt, 1000, 64 * 8);
		SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		return toHex(skf.generateSecret(spec).getEncoded())+"";
	}

	private static String toHex(byte[] array) {
		BigInteger bi = new BigInteger(1, array);
		String hex = bi.toString(16);
		int paddingLength = (array.length * 2) - hex.length();
		if (paddingLength > 0)
			return String.format("%0" + paddingLength + "d", 0) + hex;
		else
			return hex;
	}
}
