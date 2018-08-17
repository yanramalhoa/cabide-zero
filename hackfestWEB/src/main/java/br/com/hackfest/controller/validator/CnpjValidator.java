package br.com.hackfest.controller.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import br.com.hackfest.model.util.StringUtil;

public class CnpjValidator implements Validator{

	@Override
	public void validate(FacesContext context, UIComponent comp, Object obj){
		String strCnpj = obj.toString();

		if(!StringUtil.validatorCNPJ(strCnpj)){
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "CNPJ inválido", "O CNPJ não está correto.");
			throw new ValidatorException(msg);
		}
	}

}
