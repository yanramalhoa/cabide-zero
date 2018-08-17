package br.com.hackfest.controller.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.interceptor.InterceptorBinding;

/**
 * @author Igo Coutinho
 * @since 2014-08-26
 * @see Annotation criada para demarcação de propridades que serão utilizadas para indicar o status do registro.
 * 		Mais claramente, servirá para um controle de exclusão lógica.
 * */

@Inherited
@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface EntityForm {
	
	String descricao();

}