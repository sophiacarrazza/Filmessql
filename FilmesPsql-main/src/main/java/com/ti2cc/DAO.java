package com.ti2cc;

import java.sql.*;

public class DAO {
    private Connection conexao;

    public DAO() {
        conexao = null;
    }

    public boolean conectar() {
        String driverName = "org.postgresql.Driver";                    
		String serverName = "localhost";
		String mydatabase = "teste";
		int porta = 5432;
		String url = "jdbc:postgresql://" + serverName + ":" + porta +"/" + mydatabase;
		String username = "ti2cc";
		String password = "ti@cc";
		boolean status = false;

		try {
			Class.forName(driverName);
			conexao = DriverManager.getConnection(url, username, password);
			status = (conexao == null);
			System.out.println("Conexão efetuada com o postgres!");
		} catch (ClassNotFoundException e) { 
			System.err.println("Conexão NÃO efetuada com o postgres -- Driver não encontrado -- " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Conexão NÃO efetuada com o postgres -- " + e.getMessage());
		}

		return status;
        
    }

    public boolean close() {
        boolean status = false;
		
		try {
			conexao.close();
			status = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return status;
    }

    public boolean inserirFilme(Filmes filme) {
        boolean status = false;
        try {
            Statement st = conexao.createStatement();
            st.executeUpdate("INSERT INTO filmes (codigo, genero, nome, duracao) "
                    + "VALUES (" + filme.getCodigo() + ", '" + filme.getGenero() + "', '"
                    + filme.getNome() + "', '" + filme.getDuracao() + "');");
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }

    public boolean atualizarFilme(Filmes filme) {
        boolean status = false;
        try {
            Statement st = conexao.createStatement();
            String sql = "UPDATE filmes SET genero = '" + filme.getGenero() + "', nome = '"
                    + filme.getNome() + "', duracao = '" + filme.getDuracao() + "'"
                    + " WHERE codigo = " + filme.getCodigo();
            st.executeUpdate(sql);
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }

    public boolean excluirFilme(int codigo) {
        boolean status = false;
        try {
            Statement st = conexao.createStatement();
            st.executeUpdate("DELETE FROM filmes WHERE codigo = " + codigo);
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }

    public Filmes[] getFilmes() {
        Filmes[] filmes = null;

        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT * FROM filmes");
            if (rs.next()) {
                rs.last();
                filmes = new Filmes[rs.getRow()];
                rs.beforeFirst();

                for (int i = 0; rs.next(); i++) {
                	filmes[i] = new Filmes(rs.getInt("codigo"), rs.getString("genero"),
                            rs.getString("nome"), rs.getString("duracao"));
                }
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return filmes;
    }

}