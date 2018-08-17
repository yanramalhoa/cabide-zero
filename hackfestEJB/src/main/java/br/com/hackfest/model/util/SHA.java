package br.com.hackfest.model.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA {

	public static byte[] hash256(String data){
		try {
            return MessageDigest.getInstance("SHA-256").digest(data.getBytes("UTF-8"));
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
	}

	public static String bytesToHex(byte[] bytes) {
		StringBuffer result = new StringBuffer();
		for (byte byt : bytes)
			result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(
					1));
		return result.toString();
	}
	
	/**
	 * M�todo para criptografar uma String em MD5
	 * 
	 * @param s String plana
	 * @return String criptografada em MD5
	 */
	public static String encodeMd5(String s) {
		String s2 = null;

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			BigInteger hash = new BigInteger(1, md.digest(s.getBytes("UTF-8")));
			s2 = hash.toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return s2;
	}
	
	public static String encriptaNumeroValidacaoCRF(String cpfCnpj, String numeroCRF){
		String codigoMd5 = "";
		String numeroCRFEncriptado = encodeMd5(numeroCRF);
		StringBuffer valorDecriptografado = new StringBuffer();
		valorDecriptografado.append(cpfCnpj);
		valorDecriptografado.append(numeroCRF);
		valorDecriptografado.append(numeroCRFEncriptado);

		codigoMd5 = encodeMd5(valorDecriptografado.toString());

		//corre��o para MD5 menores que 32
		while(codigoMd5.length() < 32){
			codigoMd5 = codigoMd5 + "0";
		}

		codigoMd5 = codigoMd5.toUpperCase();
		return codigoMd5;
	}
	
}
