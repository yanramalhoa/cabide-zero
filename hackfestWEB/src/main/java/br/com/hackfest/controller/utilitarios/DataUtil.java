package br.com.hackfest.controller.utilitarios;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Classe utilitária para manipulação de CEP.
 */
public class DataUtil {

	/**
	 * Método responsável por verificar se uma data passada é igual a data atual
	 * ou futura
	 * 
	 * @param dataExemplo
	 * @return TRUE caso seja Maior ou Igual a data atual
	 * 		   FALSE caso a data exemplo seja seja no passado
	 */
	public static boolean isDataMaiorIgualHoje(Date dataExemplo){
		
		boolean isDataMaiorIgualHoje = false;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dataAtualStr = sdf.format(new Date());
		String dataExemploStr = sdf.format(dataExemplo);

		if(dataExemploStr.compareTo(dataAtualStr) >= 0) {
			isDataMaiorIgualHoje = true;
		}
		
		return isDataMaiorIgualHoje;
	}
	
	public static boolean verificaEntrePeriodos(String periodo) {
		Integer mes = 0;
		Integer ano = 0;
		Date dataPeriodo = new Date();
		Date dataAnterior = new Date();
		Date dataAtual = new Date();
		GregorianCalendar c = new GregorianCalendar();
		GregorianCalendar c1 = new GregorianCalendar();
		
		if (periodo.length() == 7) {
			mes = Integer.parseInt(periodo.substring(0, 2));
			ano = Integer.parseInt(periodo.substring(3, 7));
			dataAtual.setTime(c.getTimeInMillis());
			c1.add(Calendar.MONTH, -1);
			dataAnterior.setTime(c1.getTimeInMillis());
			int dia = c.get(GregorianCalendar.DATE);
			c.set(ano, mes - 1, dia);
			dataPeriodo.setTime(c.getTimeInMillis());
			
			if (!dataPeriodo.before(dataAnterior) && (!dataPeriodo.after(dataAtual))
					|| dataPeriodo.compareTo(dataAtual) == 0) {
				return true;
			}
		}
		return false;
	}
	
}
