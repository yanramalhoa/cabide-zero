package br.com.hackfest.controller.utilitarios;

import br.com.hackfest.model.util.StringUtil;

/**
 * Classe utilitária para manipulação de CEP.
 */
public class CepUtil {


	private static final int TAMANHO_CEP_COM_FORMATO = 9;
	private static final int TAMANHO_CEP_5_POSICOES = 5;
	private static final int TAMANHO_CEP_8_POSICOES = 8;

	/**
	 * Retorna o CEP formatado com a máscara 58000-100 se o CEP informado for um
	 * CEP válido, caso contrário sera retornado o mesmo valor da entrada.
	 * 
	 * @param cep
	 *            o CEP no formato 58000100.
	 * @return o CEP formatado com a máscara 58000-100.
	 */
	public static String formatar(String cep) {

		StringBuffer sb = new StringBuffer();

		if (cep != null) {

			sb.append(cep);

			if (isValido(cep) && cep.length() == TAMANHO_CEP_8_POSICOES) {
				sb.insert(5, "-");
			}
		}

		return sb.toString();
	}

	/**
	 * Remove o formato do CEP informado.
	 * 
	 * @param cep
	 *            o CEP no formato 58000-100 ou 58000.
	 * @return o CEP no formato 58000100 ou 58000.
	 */
	public static String removerFormato(String cep) {

		StringBuffer sb = new StringBuffer();

		if (cep != null) {
			sb.append(cep);

			if (possuiTamanhoComFormato(cep)) {

				int posicao = sb.indexOf("-");
				if (posicao > 0) {
					sb.deleteCharAt(posicao);
				}
			}
		}

		return sb.toString();
	}

	/**
	 * Retorna se um CEP eh válido ou não. O CEP a ser verificado pode estar ou
	 * não com máscara.
	 * 
	 * @param cep
	 *            O CEP a ser validado.
	 * @return <code>true</code> se o CEP informado eh válido,
	 *         <code>false</code> caso contrário.
	 */
	public static boolean isValido(String cep) {

		boolean isValido = false;

		if (cep != null) {

			String cepSemFormato = removerFormato(cep);

			isValido = possuiTamanhoSemFormato(cepSemFormato)
					&& StringUtil.possuiApenasDigitos(cepSemFormato);

		}

		return isValido;
	}

	/**
	 * Retorna se o CEP informado possui o tamanho do cep com formato.
	 * 
	 * @param cep
	 *            o CEP
	 * @return <code>true</code> se o cep informado possuir 9 posições,
	 *         <code>false</code> caso contrário.
	 */
	private static boolean possuiTamanhoComFormato(String cep) {
		return cep != null && !cep.trim().equals("")
				&& cep.length() == TAMANHO_CEP_COM_FORMATO;
	}

	/**
	 * Retorna se o CEP informado possui o tamanho do cep sem formato.
	 * 
	 * @param cep
	 *            Pode ser em dois formatos 58000 ou 58000100
	 * @return <code>true</code> se o CEP informado possuir 5 ou 8 posições,
	 *         <code>false</code> caso contrário.
	 */
	private static boolean possuiTamanhoSemFormato(String cep) {
		return cep != null
				&& !cep.trim().equals("")
				&& (cep.length() == TAMANHO_CEP_8_POSICOES || cep.length() == TAMANHO_CEP_5_POSICOES);
	}

}
