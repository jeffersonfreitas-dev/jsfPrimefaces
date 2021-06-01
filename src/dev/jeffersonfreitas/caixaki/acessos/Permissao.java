package dev.jeffersonfreitas.caixaki.acessos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum Permissao {

	ADMIN("ADMIN", "Administrador"), USER("USER", "Usuário Padrão"),
	CADASTRO_ACESSAR("CADASTRO_ACESSAR", "Cadastro - Acessar"),
	FINANCEIRO_ACESSAR("FINANCEIRO_ACESSAR", "Financeiro - Acessar"),
	MENSAGEM_ACESSAR("MENSAGEM_ACESSAR", "Mensagem recebida - Acessar");

	private String valor = "";
	private String descricao = "";

	private Permissao(String valor, String descricao) {
		this.valor = valor;
		this.descricao = descricao;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Override
	public String toString() {
		return getValor();
	}
	
	public static List<Permissao> getListPermissao(){
		List<Permissao> permissoes = new ArrayList<Permissao>();
		
		for(Permissao p : Permissao.values()) {
			permissoes.add(p);
		}
		
		Collections.sort(permissoes, new Comparator<Permissao>() {

			@SuppressWarnings("deprecation")
			@Override
			public int compare(Permissao o1, Permissao o2) {
				return new Integer(o1.ordinal()).compareTo(new Integer(o2.ordinal()));
			}
		});
		return permissoes;
	}

}
