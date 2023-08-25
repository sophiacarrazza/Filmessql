package com.ti2cc;

public class Principal {
	
	public static void main(String[] args) {
		
		DAO dao = new DAO();
		
		dao.conectar();

		
		// Inserir um elemento na tabela
		Filmes filme = new Filmes(89, "terror", "Midsommar", "147mins");
		if (dao.inserirFilme(filme)) {
			System.out.println("Inserção com sucesso -> " + filme.toString());
		}
		
		// Mostrar filmes
		System.out.println("==== Mostrar filmes === ");
		Filmes[] filmes = dao.getFilmes();
		for (int i = 0; i < filmes.length; i++) {
			System.out.println(filmes[i].toString());
		}

		// Atualizar o filme
		filme.setNome("Inception");
		dao.atualizarFilme(filme);

		// Mostrar os filmes
		System.out.println("==== Mostrar filmes === ");
		filmes = dao.getFilmes();
		for (int i = 0; i < filmes.length; i++) {
			System.out.println(filmes[i].toString());
		}
		
		// Excluir o filme
		dao.excluirFilme(filme.getCodigo());
		
		// Mostrar os filmes
		filmes = dao.getFilmes();
		System.out.println("==== Mostrar filmes === ");		
		for (int i = 0; i < filmes.length; i++) {
			System.out.println(filmes[i].toString());
		}
		
		dao.close();
	}
}
