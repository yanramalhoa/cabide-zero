/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.hackfest.controller.converter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Igo
 * br.gov.pb.joaopessoa.converter.
 */
@FacesConverter("bigDecimalConverter")
public class BigDecimalConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String value)  
            throws ConverterException {  
    	
		if (value.equals(""))
			return null;
		try {
			String resultado = value.replace(".", "");
			resultado = resultado.replace(",", ".");
			return new BigDecimal(resultado);
		} catch (Exception e) {
			//FacesMessages.instance().add("Erro " + e.getMessage());
		}
    	
    	
        return null;  
    }  

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object value)  
            throws ConverterException {  
    	
		if (value == null || value.toString().trim().equals("")) {
			return "0,00";
		} else {
			DecimalFormat df = new DecimalFormat("###,##0.00");
			df.setDecimalFormatSymbols(new DecimalFormatSymbols(new Locale("PT", "BR")));
			return df.format(value);
		}
    }  
    
}
