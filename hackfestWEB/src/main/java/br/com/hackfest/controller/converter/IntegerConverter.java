/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.hackfest.controller.converter;

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
@FacesConverter("integerConverter")
public class IntegerConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2)  
            throws ConverterException {  
        return arg2;  
    }  

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2)  
            throws ConverterException {  
        if(arg2==null){
            return "";
        }else if((Integer)arg2==0){
            return "";
        }
        return arg2.toString();  
    }  
    
}
