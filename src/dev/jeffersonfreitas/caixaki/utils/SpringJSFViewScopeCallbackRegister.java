package dev.jeffersonfreitas.caixaki.utils;

import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIViewRoot;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PostConstructViewMapEvent;
import javax.faces.event.PreDestroyViewMapEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.ViewMapListener;

public class SpringJSFViewScopeCallbackRegister implements ViewMapListener{

	@Override
	public boolean isListenerForSource(Object source) {
		return source instanceof UIViewRoot;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void processEvent(SystemEvent event) throws AbortProcessingException {
		if(event instanceof PostConstructViewMapEvent) {
				PostConstructViewMapEvent viewMap = (PostConstructViewMapEvent) event;
				UIViewRoot uiViewRoot = (UIViewRoot) viewMap.getComponent();
				uiViewRoot.getViewMap().put(SpringJSFViewScope.VIEW_SCOPE_CALLBACKS, new HashMap<String, Runnable>());
		
		}else if(event instanceof PreDestroyViewMapEvent) {
			PreDestroyViewMapEvent viewMap = (PreDestroyViewMapEvent) event;
			UIViewRoot viewRoot = (UIViewRoot) viewMap.getComponent();
			Map<String, Runnable> callbacks = (Map<String, Runnable>) viewRoot.getViewMap().get(SpringJSFViewScope.VIEW_SCOPE_CALLBACKS);
			if(callbacks != null) {
				for(Runnable c : callbacks.values()) {
					c.run();
				}
				callbacks.clear();
			}
		}
		
	}

}
