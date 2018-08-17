package br.com.hackfest.controller.utilitarios;

/**
 * 
 * 
 */
public class CnpjUtil {


	/**
	 * Insere máscara em CNPJ não formatado.
	 * 
	 * @param cnpjSemMascara
	 *            CNPJ sem máscara
	 * @return CNPJ com máscara
	 */
	public static String insereMascara(String cnpjSemMascara) {

		StringBuilder cnpjFormatado = new StringBuilder(cnpjSemMascara);

		cnpjFormatado.insert(2, ".");
		cnpjFormatado.insert(6, ".");
		cnpjFormatado.insert(10, "/");
		cnpjFormatado.insert(15, "-");

		return cnpjFormatado.toString();
	}

	/**
	 * Remove máscara de CNPJ formatado.
	 * 
	 * @param cnpjComMascara
	 *            CNPJ com máscara
	 * @return CNPJ sem máscara
	 */
	public static String removerFormato(String cnpjComMascara) {
		StringBuilder cnpjSB = new StringBuilder(cnpjComMascara);
		char ch = ' ';

		for (int i = 0; i < cnpjSB.length(); i++) {
			ch = cnpjSB.charAt(i);
			if (ch == '.' || ch == '/' || ch == '-') {
				cnpjSB.deleteCharAt(i);
			}
		}

		return cnpjSB.toString();
	}

	/**
	 * Valida o CNPJ.
	 * 
	 * @param cnpj
	 *            o CNPJ a ser validado.
	 * @return true se for um CNPJ válido, false caso contrário.
	 */
	public static boolean ehCnpjValido(String cnpj) {

		int num, soma;
		String dv;
		cnpj = removerFormato(cnpj);

		if (cnpj.length() != 14) {
			return false;
		}

		soma = 0;
		soma = soma + Integer.parseInt(cnpj.charAt(0) + "") * 5;
		soma = soma + Integer.parseInt(cnpj.charAt(1) + "") * 4;
		soma = soma + Integer.parseInt(cnpj.charAt(2) + "") * 3;
		soma = soma + Integer.parseInt(cnpj.charAt(3) + "") * 2;
		soma = soma + Integer.parseInt(cnpj.charAt(4) + "") * 9;
		soma = soma + Integer.parseInt(cnpj.charAt(5) + "") * 8;
		soma = soma + Integer.parseInt(cnpj.charAt(6) + "") * 7;
		soma = soma + Integer.parseInt(cnpj.charAt(7) + "") * 6;
		soma = soma + Integer.parseInt(cnpj.charAt(8) + "") * 5;
		soma = soma + Integer.parseInt(cnpj.charAt(9) + "") * 4;
		soma = soma + Integer.parseInt(cnpj.charAt(10) + "") * 3;
		soma = soma + Integer.parseInt(cnpj.charAt(11) + "") * 2;

		num = soma % 11;

		if (num < 2) {
			dv = "0";
		} else {
			dv = Integer.toString(11 - num);
		}

		if (!dv.equals(cnpj.charAt(12) + "")) {
			return false;
		}

		soma = 0;
		soma = soma + Integer.parseInt(cnpj.charAt(0) + "") * 6;
		soma = soma + Integer.parseInt(cnpj.charAt(1) + "") * 5;
		soma = soma + Integer.parseInt(cnpj.charAt(2) + "") * 4;
		soma = soma + Integer.parseInt(cnpj.charAt(3) + "") * 3;
		soma = soma + Integer.parseInt(cnpj.charAt(4) + "") * 2;
		soma = soma + Integer.parseInt(cnpj.charAt(5) + "") * 9;
		soma = soma + Integer.parseInt(cnpj.charAt(6) + "") * 8;
		soma = soma + Integer.parseInt(cnpj.charAt(7) + "") * 7;
		soma = soma + Integer.parseInt(cnpj.charAt(8) + "") * 6;
		soma = soma + Integer.parseInt(cnpj.charAt(9) + "") * 5;
		soma = soma + Integer.parseInt(cnpj.charAt(10) + "") * 4;
		soma = soma + Integer.parseInt(cnpj.charAt(11) + "") * 3;
		soma = soma + Integer.parseInt(cnpj.charAt(12) + "") * 2;

		num = soma % 11;

		if (num < 2) {
			dv = "0";
		} else {
			dv = Integer.toString(11 - num);
		}

		if (!dv.equals(cnpj.charAt(13) + "")) {
			return false;
		}

		if (cnpj.equals("00000000000000")) {
			return false;
		}

		return true;
	}
}