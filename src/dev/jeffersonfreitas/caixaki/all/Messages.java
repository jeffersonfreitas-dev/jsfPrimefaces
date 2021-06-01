package dev.jeffersonfreitas.caixaki.all;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public abstract class Messages extends FacesContext implements Serializable{
	private static final long serialVersionUID = 1L;

	public Messages() {};
	
	public static FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}
	
	
	public static void msgPersistentStatus(PersistenceStatus status) {
		if(status != null) {
			if(status.equals(PersistenceStatus.SUCCESS)) {
				msgSeverityInfo(status.toString());
			}else if(status.equals(PersistenceStatus.REFERENCE_OBJ)) {
				msgSeverityError(status.toString());
			}else {
				msgSeverityError(status.toString());
			}
		}
	}
	
	public static void msgErrorOperation() {
		if(validFacesContext()) {
			msgSeverityError(Constantes.ERROR_OPERATION);
		}
	}
	
	public static void msgSuccessOperation() {
		if(validFacesContext()) {
			msgSeverityInfo(Constantes.SUCCESS_OPERATION);
		}
	}
	
	
	private static boolean validFacesContext() {
		return getFacesContext() != null;
	}
	
	public static void msgSeverityWarn(String msg) {
		if(validFacesContext()) {
			getFacesContext().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, msg, msg));
		}
	}

	public static void msgSeverityInfo(String msg) {
		if(validFacesContext()) {
			getFacesContext().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg));
		}
	}
	
	public static void msgSeverityError(String msg) {
		if(validFacesContext()) {
			getFacesContext().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
		}
	}
}
