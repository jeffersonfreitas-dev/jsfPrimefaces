package dev.jeffersonfreitas.caixaki.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.web.context.request.FacesRequestAttributes;

public class SpringJSFViewScope implements Scope, Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String VIEW_SCOPE_CALLBACKS = "viewScope.callBacks";

	@Override
	public Object get(String name, ObjectFactory<?> factory) {
		Object instance = getViewMap().get(name);
		if(instance == null) {
			instance = factory.getObject();
			getViewMap().put(name, instance);
		}
		return instance;
	}
		

	@Override
	public String getConversationId() {
		FacesContext faces = FacesContext.getCurrentInstance();
		FacesRequestAttributes attributes = new FacesRequestAttributes(faces);
		return attributes.getSessionId() + " - " + faces.getViewRoot().getViewId();
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public void registerDestructionCallback(String name, Runnable runnable) {
		Map<String, Runnable> callbacks = (Map<String, Runnable>) getViewMap().get(VIEW_SCOPE_CALLBACKS);
		if(callbacks != null) {
			callbacks.put(VIEW_SCOPE_CALLBACKS, runnable);
		}
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public Object remove(String name) {
		Object instance = getViewMap().remove(name);

		if(instance != null) {
			Map<String, Runnable> callBacks = (Map<String, Runnable>) getViewMap().get(VIEW_SCOPE_CALLBACKS);
			if(callBacks != null) {
				callBacks.remove(name);
			}
		}
		return instance;
	}

	
	@Override
	public Object resolveContextualObject(String name) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesRequestAttributes attributes = new FacesRequestAttributes(context);
		return attributes.resolveReference(name);
	}
	
	
	private Map<String, Object> getViewMap(){
		return FacesContext.getCurrentInstance() != null ? FacesContext.getCurrentInstance().getViewRoot().getViewMap()
				: new HashMap<String, Object>();
	}

}
