package br.com.hackfest.controller.utilitarios;

/**
 * Classe utilitária para manipulação de CPF.
 * 
 */
public class CpfUtil {


	private static final int TAMANHO_CPF = 11;

	/**
	 * Retorna o cpf formatado com a máscara 999.999.999-99 se o cpf informado
	 * for um cpf válido, caso contrário sera retornado o mesmo valor da
	 * entrada.
	 * 
	 * @param cpf
	 *            o cpf a ser formatado
	 * @return o cpf formatado com a máscara 999.999.999-99
	 */
	public static String formatar(String cpf) {

		StringBuffer sb = new StringBuffer();

		if (cpf != null) {

			sb.append(cpf);

			if (possuiTamanho(cpf)) {
				sb.insert(3, ".");
				sb.insert(7, ".");
				sb.insert(11, "-");
			}
		}

		return sb.toString();
	}

	/**
	 * Remove o formato do cpf informado.
	 * 
	 * @param cpf
	 *            o cpf no formato 999.999.999-99
	 * @return o cpf no formato 99999999999
	 */
	public static String removerFormato(String cpf) {

		StringBuffer sb = new StringBuffer(cpf);

		int posicaoPrimeioPonto = sb.indexOf(".");
		if (posicaoPrimeioPonto >= 0) {
			sb.deleteCharAt(posicaoPrimeioPonto);
		}

		int posicaoSegundoPonto = sb.indexOf(".");
		if (posicaoSegundoPonto >= 0) {
			sb.deleteCharAt(posicaoSegundoPonto);
		}

		int posicaoTraco = sb.indexOf("-");
		if (posicaoTraco >= 0) {
			sb.deleteCharAt(posicaoTraco);
		}

		return sb.toString();
	}

	/**
	 * Valida um CPF passado como parâmetro.
	 * 
	 * @param cpf
	 *            um CPF a ser validado.
	 * @return true se for um CPF válido, false caso contrário.
	 */
	public static boolean ehCpfValido(String cpf) {

		int soma = 0;
		int resto = 0;
		cpf = removerFormato(cpf);

		if ((cpf.length() != 11) || (cpf.equals("00000000000"))
				|| (cpf.equals("11111111111")) || (cpf.equals("22222222222"))
				|| (cpf.equals("33333333333")) || (cpf.equals("44444444444"))
				|| (cpf.equals("55555555555")) || (cpf.equals("66666666666"))
				|| (cpf.equals("77777777777")) || (cpf.equals("88888888888"))
				|| (cpf.equals("99999999999"))) {

			return false;
		}

		for (int i = 0; i < 9; i++) {
			soma += Integer.parseInt(Character.toString(cpf.charAt(i)))
					* (10 - i);
		}
		resto = 11 - (soma % 11);

		if (resto == 10 || resto == 11) {
			resto = 0;
		}
		if (resto != Integer.parseInt(Character.toString(cpf.charAt(9)))) {
			return false;
		}

		soma = 0;
		for (int i = 0; i < 10; i++) {
			soma += Integer.parseInt(Character.toString(cpf.charAt(i)))
					* (11 - i);
		}
		resto = 11 - (soma % 11);

		if (resto == 10 || resto == 11) {
			resto = 0;
		}
		if (resto != Integer.parseInt(Character.toString(cpf.charAt(10)))) {
			return false;
		}

		return true;
	}

	/**
	 * Retorna se o cpf informado possui o tamanho de CPF, ou seja, 11 posições.
	 * 
	 * @param cpf
	 *            o cpf a ser verificado
	 * @return <code>true</code> se o cpf informado possuir 11 posições,
	 *         <code>false</code> caso contrário.
	 */
	private static boolean possuiTamanho(String cpf) {
		return cpf != null && !cpf.trim().equals("")
				&& cpf.length() == TAMANHO_CPF;
	}
}