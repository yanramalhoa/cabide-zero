package br.com.hackfest.controller.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
  
@FacesConverter("DateTimeSimpleConverter")
public class DateTimeSimpleConverter implements Converter {  
   
    @Override  
    public Object getAsObject(FacesContext fc, UIComponent ui, String value) {
    	
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date data = null;  
        try {  
            data = df.parse(value);  
        } catch (ParseException e) {  
            e.printStackTrace();  
        }  
        return data;  
  
    }  
  
    @Override  
    public String getAsString(FacesContext fc, UIComponent ui, Object value) {  
        DateFormat newStyle = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        if (value != null && !value.toString().trim().isEmpty()) {  
	        try {  
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
                //DateFormat df = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);  
                //Thu Oct 23 00:00:00 BRT 2014
  
                Date data = df.parse(value.toString().trim());  
  
                String formatedDate = newStyle.format(data);  
                return formatedDate;  
	        } catch (Exception e) { 
	        	try {
	                String formatedDate = newStyle.format(new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy", Locale.US).parse(value.toString()));  
	                return formatedDate;  
				} catch (Exception e2) {
					// TODO: handle exception
		            e2.printStackTrace();  
				}
	            e.printStackTrace();  
	        }  
        }  
        return null;  
    }  
  
}  