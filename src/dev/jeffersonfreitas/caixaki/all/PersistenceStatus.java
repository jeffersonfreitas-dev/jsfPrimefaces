package dev.jeffersonfreitas.caixaki.all;

public enum PersistenceStatus {
	
	ERROR("Erro"),
	SUCCESS("Sucesso"),
	REFERENCE_OBJ("Esse objeto não pode ser apagado por possuir referencia para outro objeto");
	
	private String description;
	
	private PersistenceStatus(String s) {
		this.description = s;
	}
	
	@Override
	public String toString() {
		return this.description;
	}

}
