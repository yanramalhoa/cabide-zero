package br.com.hackfest.controller.utilitarios;

public class FoneUtil {

	private static final int TAMANHO_FONE = 10;
	
	/**
	 * Retorna o telefone formatado com a máscara (99)9999-9999.
	 * 
	 * @param fone
	 *            o telefone a ser formatado
	 * @return o telefone formatado com a máscara (99)9999-9999
	 */
	
	public String formatFone(String fone){

		StringBuffer sb = new StringBuffer();

		if (fone != null) {

			sb.append(fone);

			if (possuiTamanho(fone)) {
				sb.insert(0, "(");
				sb.insert(3, ")");
				sb.insert(8, "-");
			}
		}

		return sb.toString();
	}
	
	/**
	 * Remove o formato do telefone informado.
	 * 
	 * @param fone
	 *            o telefone no formato (99)9999-9999
	 * @return o telefone no formato 9999999999
	 */
	public static String removerFormato(String fone) {

		StringBuffer sb = new StringBuffer(fone);

		int posicaoPrimeioParentese = sb.indexOf("(");
		if (posicaoPrimeioParentese >= 0) {
			sb.deleteCharAt(posicaoPrimeioParentese);
		}

		int posicaoSegundoParentese = sb.indexOf(")");
		if (posicaoSegundoParentese >= 0) {
			sb.deleteCharAt(posicaoSegundoParentese);
		}

		int posicaoTraco = sb.indexOf("-");
		if (posicaoTraco >= 0) {
			sb.deleteCharAt(posicaoTraco);
		}

		return sb.toString();
	}
	
	/**
	 * Retorna se o telefone informado possui 8 posições.
	 * 
	 * @param fone
	 *            o telefone a ser verificado
	 * @return <code>true</code> se o telefone informado possuir 8 posições,
	 *         <code>false</code> caso contrário.
	 */
	private static boolean possuiTamanho(String cpf) {
		return cpf != null && !cpf.trim().equals("")
				&& cpf.length() == TAMANHO_FONE;
	}
}
