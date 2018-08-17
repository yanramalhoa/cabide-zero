package br.com.hackfest.controller.validator;

import java.util.Calendar;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import br.com.hackfest.controller.utilitarios.DataUtil;

@FacesValidator("dataFuturaValidator")
public class DataFuturaValidator implements Validator{

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) {
		Calendar dataExemplo = Calendar.getInstance();
		dataExemplo.setTime((Date)value);
		dataExemplo.add(Calendar.DATE, +1);
		
		if (value!=null && !DataUtil.isDataMaiorIgualHoje(dataExemplo.getTime())){
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Digite apenas datas futuras", null);
			throw new ValidatorException(msg);
		}
	}
}
