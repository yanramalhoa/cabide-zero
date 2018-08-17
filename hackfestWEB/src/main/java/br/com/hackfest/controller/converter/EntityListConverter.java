package br.com.hackfest.controller.converter;

import java.lang.reflect.Field;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import br.com.hackfest.controller.annotation.SortId;


/**
 * Converter para entidades JPA. Baseia-se na anotação @Id para identificar o
 * atributo que representa a identidade da entidade.
 * 
 * @since 2014-08-12
 */
public class EntityListConverter implements Converter {

	public Object getAsObject(FacesContext ctx, UIComponent component, String value) {
		if (value != null) {
			return component.getAttributes().get(value);
		}
		return null;
	}

	public String getAsString(FacesContext ctx, UIComponent component, Object obj) {
		if (obj != null && !"".equals(obj)) {
			String id;
			try {
				id = this.getId(getClazz(ctx, component), obj);
				if (id == null)
					id = "";
				id = id.trim();
				component.getAttributes().put(id, getClazz(ctx, component).cast(obj));
				return id;
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private Class<?> getClazz(FacesContext facesContext, UIComponent component) {
		return component.getValueExpression("value").getType(
				facesContext.getELContext());
	}

	public String getId(Class<?> clazz, Object obj) throws SecurityException,
			NoSuchFieldException, IllegalArgumentException,
			IllegalAccessException {
		for (Field field : clazz.getDeclaredFields()) {
			if ((field.getAnnotation(SortId.class)) != null) {
				Field privateField = clazz.getDeclaredField(field.getName());
				privateField.setAccessible(true);
				if (privateField.get(clazz.cast(obj)) != null) {
					return String.valueOf(privateField.getInt(obj)); 
							
							//field.getType()
							//.cast(privateField.get(clazz.cast(obj))).toString();
				} else {
					return null;
				}
			}
		}
		return null;
	}
}