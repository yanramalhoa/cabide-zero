package br.com.hackfest.model.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {

	public static boolean isNumber(String s) {
		try {
			boolean isNumber = s.matches("([0-9])+");

			if (isNumber) {
				return true;
			}

			return false;
		} catch (PatternSyntaxException p) {
			return false;
		}
	}
	
	public static String removerFomatacao(String value){
		if(value != null && !value.equals("")){
			value = value.replace(".", "").replace("-", "").replace("/", "").replace(",", "").replace("(", "").replace(")", "");
		}else{
			value = "";
		}
		return value;
	}
	
	public static String formataNumeroValidacaoCRF(String numeroValidacao){
		StringBuffer modCodigoVerificador = new StringBuffer();
		modCodigoVerificador.append(numeroValidacao.substring(0, 8));
		modCodigoVerificador.append("-");
		modCodigoVerificador.append(numeroValidacao.substring(8, 16));
		modCodigoVerificador.append("-");
		modCodigoVerificador.append(numeroValidacao.substring(16, 24));
		modCodigoVerificador.append("-");
		modCodigoVerificador.append(numeroValidacao.substring(24, 32));

		return modCodigoVerificador.toString();
	}
	
	public static List<String> ordenaMesAno(List<String> meses) {
		List<String> auxiliar = new ArrayList<String>();

		for (String mesAno : meses) {
			auxiliar.add(mesAno.substring(2) + mesAno.substring(0, 2));
		}

		Collections.sort(auxiliar);
		meses.clear();

		for (String mesAno : auxiliar) {
			meses.add(mesAno.substring(4) + mesAno.substring(0, 4));
		}

		return meses;
	}

	public String limitString(String value, int limitBegin, int limitEnd) {
		if (value != null) {
			if (!value.isEmpty() && value.length() > limitEnd) {
				return value.substring(limitBegin, limitEnd) + "...";
			}
		}
		return value;
	}

	public static boolean isUltimoDiaMes() {
		int ultimoDia;
		int diaHoje;
		int mesHoje;

		Calendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		diaHoje = cal.get(Calendar.DAY_OF_MONTH);
		mesHoje = cal.get(Calendar.MONTH) + 1;

		if ((mesHoje == 1) || (mesHoje == 3) || (mesHoje == 5)
				|| (mesHoje == 7) || (mesHoje == 8) || (mesHoje == 10)
				|| (mesHoje == 12)) {
			ultimoDia = 31;
		} else {
			if (mesHoje == 2) {
				ultimoDia = 28;
			} else {
				ultimoDia = 30;
			}
		}

		if (ultimoDia == diaHoje) {
			return true;
		} else {
			return false;
		}
	}

	public static float formataMoedaStringParaFloat(String valor) {
		valor = valor.replace(",", ".");
		valor = valor.replace(".", "");

		String[] ponto = new String['.'];
		if (!contemString(valor, ponto)) {
			int tamanho = valor.length();
			valor = adicionaCaracter(valor, tamanho - 2, '.');
		}
		float valorFormatado = Float.parseFloat(valor);

		return valorFormatado;
	}

	public static String formataMoeda(String valor, boolean moeda) {
		String resultado = "";

		if ((valor != null) && (valor.length() >= 3)) {
			String valorString = valor.substring(0, valor.length() - 2) + "."
					+ valor.substring(valor.length() - 2);
			resultado = NumberFormat.getCurrencyInstance().format(
					Float.parseFloat(valorString));

			if (!moeda) {
				resultado = resultado.substring(3, resultado.length());
			}
		}
		return resultado;
	}

	public static String formataMoeda(double valor, boolean moeda) {
		String resultado = "";

		if (valor >= 0) {
			resultado = NumberFormat.getCurrencyInstance().format(valor);

			if (!moeda) {
				resultado = resultado.substring(3, resultado.length());
			}
		}
		return resultado;
	}

	public static String formataCnpjCpf(String cnpjCpf) {
		StringBuffer modCnpjCpf = new StringBuffer();
		// CNPJ
		if (cnpjCpf.length() == 14) {
			modCnpjCpf.append(cnpjCpf.substring(0, 2));
			modCnpjCpf.insert(2, ".");
			modCnpjCpf.append(cnpjCpf.substring(2, 5));
			modCnpjCpf.insert(6, ".");
			modCnpjCpf.append(cnpjCpf.substring(5, 8));
			modCnpjCpf.insert(10, "/");
			modCnpjCpf.append(cnpjCpf.substring(8, 12));
			modCnpjCpf.insert(15, "-");
			modCnpjCpf.append(cnpjCpf.substring(12, 14));
			return modCnpjCpf.toString();
		} else {
			// CPF
			if (cnpjCpf.length() == 11) {
				modCnpjCpf.append(cnpjCpf.substring(0, 3));
				modCnpjCpf.insert(3, ".");
				modCnpjCpf.append(cnpjCpf.substring(3, 6));
				modCnpjCpf.insert(7, ".");
				modCnpjCpf.append(cnpjCpf.substring(6, 9));
				modCnpjCpf.insert(11, "-");
				modCnpjCpf.append(cnpjCpf.substring(9, 11));
				return modCnpjCpf.toString();
			} else {
				return cnpjCpf = "";
			}
		}
	}

	public static String formataPis(String pis) {
		StringBuffer modPis = new StringBuffer();
		// PIS
		if (pis.length() == 11) {
			modPis.append(pis.substring(0, 3));
			modPis.insert(3, ".");
			modPis.append(pis.substring(3, 8));
			modPis.insert(9, ".");
			modPis.append(pis.substring(8, 10));
			modPis.insert(12, "/");
			modPis.append(pis.substring(10, 11));
			return modPis.toString();
		} else {
			return pis = "";
		}
	}

	public static String formataMesAno(String mesAno) {
		StringBuffer modMesAno = new StringBuffer();
		// PIS
		if (mesAno.length() == 6) {
			modMesAno.append(mesAno.substring(0, 2));
			modMesAno.insert(2, "/");
			modMesAno.append(mesAno.substring(2, 6));
			return modMesAno.toString();
		} else {
			return mesAno = "";
		}
	}

	public static boolean validatorCPF(String cpf) {
		int pos;
		int mult;
		int somaCpf = 0;
		int dv1;
		int dv2;

		if (cpf == null || cpf.isEmpty()) {
			return true;
		}

		try {
			cpf = removerFomatacao(cpf);
		} catch (Exception e) {
			e.getStackTrace();
			return false;
		}

		if (cpf.length() < 11) {
			return false;
		}

		if (cpf.length() > 11) {
			return false;
		}

		if (!isNumber(cpf) || cpf.equals("00000000000")
				|| cpf.equals("11111111111") || cpf.equals("22222222222")
				|| cpf.equals("33333333333") || cpf.equals("44444444444")
				|| cpf.equals("55555555555") || cpf.equals("66666666666")
				|| cpf.equals("77777777777") || cpf.equals("88888888888")
				|| cpf.equals("99999999999")) {

			return false;
		}

		for (pos = 0, mult = 10; pos <= 8 & mult >= 2; pos++, mult--) {
			somaCpf += Integer.parseInt(String.valueOf(cpf.charAt(pos))) * mult;
		}

		int cont = somaCpf % 11;

		if (cont < 2) {
			dv1 = 0;
		} else {
			dv1 = 11 - cont;
		}

		somaCpf = 0;

		for (pos = 0, mult = 11; pos <= 9 & mult >= 2; pos++, mult--) {
			somaCpf += Integer.parseInt(String.valueOf(cpf.charAt(pos))) * mult;
		}

		cont = somaCpf % 11;

		if (cont < 2) {
			dv2 = 0;
		} else {
			dv2 = 11 - cont;
		}

		if (Integer.parseInt(String.valueOf(cpf.charAt(9))) == dv1
				& Integer.parseInt(String.valueOf(cpf.charAt(10))) == dv2) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean validatorCNPJ(String cnpj) {
		int soma = 0, num, dv;

		cnpj = removerFomatacao(cnpj);

		if (cnpj.isEmpty() || cnpj == null) {
			return false;
		}

		if (cnpj.length() < 14) {
			return false;
		}

		if (cnpj.length() > 14) {
			return false;
		}

		if (!isNumber(cnpj) || cnpj.equals("00000000000000")
				|| cnpj.equals("11111111111111")
				|| cnpj.equals("22222222222222")
				|| cnpj.equals("33333333333333")
				|| cnpj.equals("44444444444444")
				|| cnpj.equals("55555555555555")
				|| cnpj.equals("66666666666666")
				|| cnpj.equals("77777777777777")
				|| cnpj.equals("88888888888888")
				|| cnpj.equals("99999999999999")) {
			return false;
		}

		soma += (Integer.parseInt(String.valueOf(cnpj.charAt(0))) * 5);
		soma += (Integer.parseInt(String.valueOf(cnpj.charAt(1))) * 4);
		soma += (Integer.parseInt(String.valueOf(cnpj.charAt(2))) * 3);
		soma += (Integer.parseInt(String.valueOf(cnpj.charAt(3))) * 2);
		soma += (Integer.parseInt(String.valueOf(cnpj.charAt(4))) * 9);
		soma += (Integer.parseInt(String.valueOf(cnpj.charAt(5))) * 8);
		soma += (Integer.parseInt(String.valueOf(cnpj.charAt(6))) * 7);
		soma += (Integer.parseInt(String.valueOf(cnpj.charAt(7))) * 6);
		soma += (Integer.parseInt(String.valueOf(cnpj.charAt(8))) * 5);
		soma += (Integer.parseInt(String.valueOf(cnpj.charAt(9))) * 4);
		soma += (Integer.parseInt(String.valueOf(cnpj.charAt(10))) * 3);
		soma += (Integer.parseInt(String.valueOf(cnpj.charAt(11))) * 2);

		num = soma % 11;

		if (num < 2) {
			dv = 0;
		} else
			dv = 11 - num;

		if (dv != Integer.parseInt(String.valueOf(cnpj.charAt(12)))) {
			return false;
		}

		soma = 0;

		soma += (Integer.parseInt(String.valueOf(cnpj.charAt(0))) * 6);
		soma += (Integer.parseInt(String.valueOf(cnpj.charAt(1))) * 5);
		soma += (Integer.parseInt(String.valueOf(cnpj.charAt(2))) * 4);
		soma += (Integer.parseInt(String.valueOf(cnpj.charAt(3))) * 3);
		soma += (Integer.parseInt(String.valueOf(cnpj.charAt(4))) * 2);
		soma += (Integer.parseInt(String.valueOf(cnpj.charAt(5))) * 9);
		soma += (Integer.parseInt(String.valueOf(cnpj.charAt(6))) * 8);
		soma += (Integer.parseInt(String.valueOf(cnpj.charAt(7))) * 7);
		soma += (Integer.parseInt(String.valueOf(cnpj.charAt(8))) * 6);
		soma += (Integer.parseInt(String.valueOf(cnpj.charAt(9))) * 5);
		soma += (Integer.parseInt(String.valueOf(cnpj.charAt(10))) * 4);
		soma += (Integer.parseInt(String.valueOf(cnpj.charAt(11))) * 3);
		soma += (Integer.parseInt(String.valueOf(cnpj.charAt(12))) * 2);

		num = 0;
		num = soma % 11;

		if (num < 2)
			dv = 0;
		else
			dv = 11 - num;

		if (dv != Integer.parseInt(String.valueOf(cnpj.charAt(13)))) {
			return false;
		}

		return true;
	}
	
	public static boolean validatorCpfCnpj(String cpfCnpj){
		cpfCnpj = removerFomatacao(cpfCnpj);
		if(cpfCnpj.isEmpty()){
			return false;
		}else if(cpfCnpj.length()==11){
			return validatorCPF(cpfCnpj);
		}else if(cpfCnpj.length()==14){
			return validatorCNPJ(cpfCnpj);
		}
		return false;
	}

	/**
	 * M�todo para criptografar uma String em MD5
	 * 
	 * @param s
	 *            String plana
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

	public static String adicionaCaracter(String palavra, int posicao,
			char caracter) {
		StringBuffer novaPalavra = new StringBuffer(palavra);
		novaPalavra.insert(posicao, caracter);
		return novaPalavra.toString();
	}

	public static boolean contemString(String palavra, String[] lista) {
		boolean contem = false;

		for (int i = 0; i < lista.length; i++) {
			if (palavra.equals(lista[i])) {
				contem = true;
			}
		}

		return contem;
	}

	public static String rightTrim(String string) {
		Pattern rightTrimPattern = Pattern.compile("\\s+$");
		return rightTrimPattern.matcher(string).replaceAll("");
	}

	public static String leftTrim(String string) {
		Pattern leftTrimPattern = Pattern.compile("^\\s+");
		return leftTrimPattern.matcher(string).replaceAll("");
	}

	public static String removerAcentos(String str) {
		str = Normalizer.normalize(str, Normalizer.Form.NFD);
		str = str.replaceAll("[^\\p{ASCII}]", "");
		return str;
	}

	/**
	 * Verifica se ums String eh nula ou vazia
	 * 
	 * @param texto
	 * @return boolean
	 */
	public static boolean isEmpty(String texto) {
		if (texto == null || texto.equalsIgnoreCase("")) {
			return true;
		}
		return false;
	}

	/**
	 * Obtem o CNPJ do Fornecedor e retorna o Cnpj formatado com a máscara
	 * 99.999.999/9999-99
	 * 
	 * @param cnpj
	 * @return o CNPJ formatado
	 */
	public static String obterCNPJFormatado(String cnpj) {
		char[] array = cnpj.toCharArray();
		String cnpjFormatado = "";

		try {
			cnpjFormatado = String.valueOf(array[0]) + String.valueOf(array[1])
					+ "." + String.valueOf(array[2]) + String.valueOf(array[3])
					+ String.valueOf(array[4]) + "." + String.valueOf(array[5])
					+ String.valueOf(array[6]) + String.valueOf(array[7]) + "/"
					+ String.valueOf(array[8]) + String.valueOf(array[9])
					+ String.valueOf(array[10]) + String.valueOf(array[11])
					+ "-" + String.valueOf(array[12])
					+ String.valueOf(array[13]);
		} catch (Exception e) {
			cnpjFormatado = cnpj;
		}

		return cnpjFormatado;
	}

	public final static String convertNormalCase(String text) {
		StringBuilder str = new StringBuilder();
		String[] textSplit = text.toLowerCase().trim().split("\\s+");
		for (String string : textSplit) {
			str.append(convertFirstLetterUpperCase(string)).append(" ");
		}
		return str.toString();
	}

	public final static String convertFirstLetterToLowerCase(String palavra) {
		char[] palavraArray = palavra.toCharArray();
		palavraArray[0] = Character.toLowerCase(palavraArray[0]);
		return String.valueOf(palavraArray);
	}

	public final static String convertFirstLetterUpperCase(String palavra) {
		char[] palavraArray = palavra.toCharArray();
		palavraArray[0] = Character.toLowerCase(palavraArray[0]);
		return String.valueOf(palavraArray);
	}

	/**
	 * Retorna se a String informada possui letras e digitos.
	 * 
	 * @param string
	 *            a string
	 * @return <code>true</code> se a String informada possuir letras e digitos,
	 *         <code>false</code> caso contrário.
	 */
	public static boolean possuiDigitosLetras(String string) {

		boolean possuiLetras = false;
		boolean possuiDigitos = false;

		for (int i = 0; i < string.length(); i++) {
			if (Character.isDigit(string.charAt(i))) {
				possuiDigitos = true;
			}
			if (Character.isLetter(string.charAt(i))) {
				possuiLetras = true;
			}
		}

		return (possuiLetras && possuiDigitos);
	}

	/**
	 * Retorna se a String informada possui apenas digitos.
	 * 
	 * @param string
	 *            a string
	 * @return <code>true</code> se a String informada possuir apenas digitos,
	 *         <code>false</code> caso contrário.
	 */
	public static boolean possuiApenasDigitos(String string) {
		return StringUtils.isNumeric(string);
	}

	/**
	 * Retorna se a String informada possui letras.
	 * 
	 * @param string
	 *            a string a ser pesquisado.
	 * @return <code>true</code> se a String informada possuir letras,
	 *         <code>false</code> caso contrário.
	 */
	public static boolean possuiLetras(String string) {

		boolean possuiLetras = false;

		for (int i = 0; i < string.length(); i++) {
			if (Character.isLetter(string.charAt(i))) {
				possuiLetras = true;
			}
		}

		return possuiLetras;
	}

	/**
	 * Completa a string informada com zeros a esquerda até que ela tenha o
	 * tamanho informado nos parametros.
	 * 
	 * @param string
	 *            a string
	 * @param tamanho
	 *            o tamanho limite da string
	 * @return a string informada com zeros a esquerda e do tamanho informado.
	 */
	public static String completarZerosEsquerda(String string, int tamanho) {
		return preencherEsquerda(string, tamanho, '0');
	}

	/**
	 * Verifica se uma string passada tem valor nulo ou esta em branco.
	 * 
	 * @param string
	 *            string a ser testada
	 * @return <code>true</code> caso a string passada seja nula ou esteja em
	 *         branco, <code>false</code> caso a string passada seja diferente
	 *         de nulo ou nao esteja em branco
	 */
	public static boolean ehBrancoOuNulo(String string) {
		return string == null || string.trim().equals("");
	}

	/**
	 * Retorna uma string contendo o valor inteiro informado e com o tamanho
	 * desejado com zeros a esquerda.
	 * 
	 * @param inteiro
	 *            um int contendo o número a ser convertido
	 * @param tamanho
	 *            o tamanho limite da string
	 * @return a string informada com zeros a esquerda e do tamanho informado.
	 */
	public static String completarZerosEsquerda(int inteiro, int tamanho) {
		return completarZerosEsquerda(String.valueOf(inteiro), tamanho);
	}

	/**
	 * Inverte o conteúdo da String informada. Se o valor for null, uma String
	 * vazia sera retornada.
	 * 
	 * @param valor
	 *            A String que dever� ser utilizada para inversão.
	 * @return Uma String com o conteúdo do parametro informado em ordem inversa
	 */
	public static String inverterString(String valor) {

		StringBuffer sb = new StringBuffer();

		if (valor != null) {
			for (int i = valor.length() - 1; i >= 0; i--) {
				sb.append(valor.charAt(i));
			}
		}

		return sb.toString();
	}

	/**
	 * Preenche a string com o filler a direita até que o seu tamanho seja o
	 * tamanho informado. Ex: preencherDireita("teste", 10, '#') = "teste#####"
	 * 
	 * @param s
	 *            A string original.
	 * @param tamanho
	 *            O tamanho que deve ter a string ao final do método.
	 * @param filler
	 *            O character que sera utilizado para preencher a String.
	 * @return A string informada com o filler a direita até que o seu tamanho
	 *         seja o tamanho informado.
	 */
	public static String preencherDireita(String s, int tamanho, char filler) {

		StringBuffer sb = new StringBuffer(s);

		for (int i = sb.length(); i < tamanho; i++) {
			sb.append(filler);
		}

		return sb.toString();
	}

	/**
	 * Preenche a string com o filler a esquerda até que o seu tamanho seja o
	 * tamanho informado. Ex: preencherEsquerda("teste", 10, '#') = "#####teste"
	 * 
	 * @param s
	 *            A string original.
	 * @param tamanho
	 *            O tamanho que deve ter a string ao final do método.
	 * @param filler
	 *            O character que sera utilizado para preencher a String.
	 * @return A string informada com o filler a esquerda até que o seu tamanho
	 *         seja o tamanho informado.
	 */
	public static String preencherEsquerda(String s, int tamanho, char filler) {

		int repeticoes = tamanho - s.length();
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < repeticoes; i++) {
			sb.append(filler);
		}

		sb.append(s);

		return sb.toString();

	}

	/**
	 * Retorna se o email informado eh válido.
	 * 
	 * @param email
	 *            uma String contendo o e-mail a ser validado.
	 * @return <code>true</code> se o email informado eh válido,
	 *         <code>false</code> caso contrário.
	 */
	public static boolean ehEmailValido(String email) {

		boolean ehEmailValido = false;

		Pattern padrao = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher pesquisa = padrao.matcher(email);

		if (pesquisa.matches()) {
			ehEmailValido = true;
		}

		return ehEmailValido;
	}

	/**
	 * Retorna uma String apenas com os digitos e as letras da String passada
	 * como argumento.
	 * 
	 * @param string
	 *            uma String contendo a string a ser "refinada"
	 * @return String contendo apenas digitos e letras
	 */
	public static String apenasDigitosLetras(String string) {

		StringBuffer letrasDigitos = new StringBuffer();

		for (int i = 0; i < string.length(); i++) {
			char caracter = string.charAt(i);
			if (Character.isDigit(caracter) || Character.isLetter(caracter)) {
				letrasDigitos.append(caracter);
			}
		}

		return letrasDigitos.toString();
	}

	/**
	 * Retorna uma String apenas com os digitos da String passada como
	 * argumento.
	 * 
	 * @param string
	 *            uma String contendo a string a ser "refinada"
	 * @return String contendo apenas digitos
	 */
	public static String apenasDigitos(String string) {

		StringBuffer digitos = new StringBuffer();

		for (int i = 0; i < string.length(); i++) {
			char caracter = string.charAt(i);
			if (Character.isDigit(caracter)) {
				digitos.append(caracter);
			}
		}

		return digitos.toString();
	}

	/**
	 * Remove os espa�os duplicados de uma String.
	 * 
	 * @param string
	 *            uma String contendo a string a ser "refinada"
	 * @return String contendo apenas sem espa�os duplicados
	 */
	public static String removeSpacosDuplicados(String string) {

		Pattern pattern = Pattern.compile("[\\s]+");

		Matcher matcher = pattern.matcher(string);

		return matcher.replaceAll(" ");
	}

	/**
	 * Retorna uma String apenas com as letras da String passada como argumento.
	 * 
	 * @param string
	 *            uma String contendo a string a ser "refinada"
	 * @return String contendo apenas letras
	 */
	public static String apenasLetras(String string) {

		StringBuffer letras = new StringBuffer();

		for (int i = 0; i < string.length(); i++) {
			char caracter = string.charAt(i);
			if (Character.isLetter(caracter)) {
				letras.append(caracter);
			}
		}

		return letras.toString();
	}

	/**
	 * Retorna <code>true</code> se uma String vem com o valor zero.
	 * 
	 * @param s
	 *            um tipo String contendo o valor a ser verificado
	 * @return <code>true</code> se uma String vem com o valor zero
	 */
	public static boolean preenchidaSoComZero(String s) {
		boolean retorno = true;
		BigInteger bigInteger = null;
		try {
			bigInteger = new BigInteger(s.trim());
		} catch (NumberFormatException e) {
			retorno = false;
		}
		if (bigInteger == null) {
			retorno = false;
		} else if (!bigInteger.equals(new BigInteger("0"))) {
			retorno = false;
		}
		return retorno;
	}
	
	public static boolean validaCPF(String cpf) {
		int pos;
		int mult;
		int somaCpf = 0;
		int dv1;
		int dv2;

		if (cpf == null || cpf.isEmpty()) {
			return true;
		}

		try {
			cpf = removerFomatacao(cpf);
		} catch (Exception e) {
			e.getStackTrace();
			return false;
		}

		if (cpf.length() < 11) {
			return false;
		}

		if(cpf.length() > 11){
			return false;
		}		

		if (!isNumber(cpf) || cpf.equals("00000000000") || cpf.equals("11111111111")
				|| cpf.equals("22222222222") || cpf.equals("33333333333")
				|| cpf.equals("44444444444") || cpf.equals("55555555555")
				|| cpf.equals("66666666666") || cpf.equals("77777777777")
				|| cpf.equals("88888888888") || cpf.equals("99999999999")) {

			return false;
		}

		for (pos = 0, mult = 10; pos <= 8 & mult >= 2; pos++, mult--) {
			somaCpf += Integer.parseInt(String.valueOf(cpf.charAt(pos))) * mult;
		}

		int cont = somaCpf % 11;

		if (cont < 2) {
			dv1 = 0;
		} else {
			dv1 = 11 - cont;
		}

		somaCpf = 0;

		for (pos = 0, mult = 11; pos <= 9 & mult >= 2; pos++, mult--) {
			somaCpf += Integer.parseInt(String.valueOf(cpf.charAt(pos))) * mult;
		}

		cont = somaCpf % 11;

		if (cont < 2) {
			dv2 = 0;
		} else {
			dv2 = 11 - cont;
		}

		if (Integer.parseInt(String.valueOf(cpf.charAt(9))) == dv1
				& Integer.parseInt(String.valueOf(cpf.charAt(10))) == dv2) {
			return true;
		} else {
			return false;
		}
	}
	
}
