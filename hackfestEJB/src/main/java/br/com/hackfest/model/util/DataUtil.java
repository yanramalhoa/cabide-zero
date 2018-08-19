package br.com.hackfest.model.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataUtil {

	public static long diasEntreDatas(Date dataIni, Date dataFim){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dataInicial = sdf.format(dataIni);
		String dataFinal   = sdf.format(dataFim);
		
	    Calendar c1 = Calendar.getInstance();  
	    
	    Date di = new Date();
	    Date df = new Date();
	    
	    //Pega a primeira data  
	    c1.set(Integer.parseInt(dataInicial.substring(0, 4)), Integer.parseInt(dataInicial.substring(4, 6)), Integer.parseInt(dataInicial.substring(6, 8)));  
	    di.setTime(c1.getTimeInMillis());  
	      
	    //Pega a segunda data  
	    c1.set(Integer.parseInt(dataFinal.substring(0, 4)), Integer.parseInt(dataFinal.substring(4, 6)), Integer.parseInt(dataFinal.substring(6, 8)));  
	    df.setTime(c1.getTimeInMillis());
	      
	    long result = (df.getTime() - di.getTime()) /1000/60/60/24; 
	    if(result<0){
	    	result = 0;
	    }
	    
	    return result;  
	} 	
}
