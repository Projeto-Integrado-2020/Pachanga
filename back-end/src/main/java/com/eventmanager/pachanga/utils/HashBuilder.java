package com.eventmanager.pachanga.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashBuilder { 

	public static String gerarSenha(String senha) {
		String hash = ""; 
		try {
			hash = gerarHash(senha, getSalt());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hash;
	}
	
	public static Boolean compararSenha(String senhaLogin, String senhaBanco){
		String salt = senhaBanco.substring(0,32);
		senhaLogin = gerarHash(senhaLogin, hexStringToByteArray(salt));
		return senhaBanco.equals(senhaLogin);
	}

	private static String gerarHash(String passwordToHash, byte[] salt)
	{
		String generatedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(salt);
			byte[] bytes = md.digest(passwordToHash.getBytes());
			StringBuilder sb = new StringBuilder();
			for(int i=0; i< bytes.length ;i++)
			{
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}

			StringBuilder sbSalt = new StringBuilder();
			for(int i=0; i< salt.length ;i++)
			{
				sbSalt.append(Integer.toString((salt[i] & 0xff) + 0x100, 16).substring(1));
			}

			generatedPassword = sbSalt.toString() + sb.toString();
		} 
		catch (NoSuchAlgorithmException e) 
		{
			e.printStackTrace();
		}
		return generatedPassword;
	}


	//Add salt
	private static byte[] getSalt() throws NoSuchAlgorithmException{
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		return salt;
	}

	private static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
					+ Character.digit(s.charAt(i+1), 16));
		}
		return data;
	}

}
