package br.com.hackfest.controller.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.interceptor.InterceptorBinding;

/**
 * @author Igo Coutinho
 * @since 2015-07-21
 * @see Annotation criada para demarcação de propridades que serão utilizadas para indicar um atributo de chave primária que é uma String.
 * */

@Inherited
@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface StringKey {

}