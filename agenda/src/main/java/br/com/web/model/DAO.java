package br.com.web.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DAO {
	/**Módulo de Conexão **/
	//Parametros de conexão
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://127.0.0.1:3306/dbagenda?useTimezone=true&serverTimezone=UTC";
	private String user = "root";
	private String password = "Ba179449";
	
	//Métodos de conexão
	private Connection conectar() {
		Connection con = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			return con;
		}catch(Exception e){
			System.out.println(e);
			return null;
		}
	}
	
	/*CRUD CREATE */
	public void inserirContato(JavaBeans contato) {
		String create = "insert into contatos (nome, fone, email) values(?, ?, ?)";
		
		try {
			//abrir conexão com o bd
			Connection con = conectar();
			//executar comando sql
			PreparedStatement pst = con.prepareStatement(create);
			//substituir parametros pelo conteudo das variaveis javabeans
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			//executar a query
			pst.executeUpdate();
			//encerrar a conexão bd
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	//teste de conexão
	public void testeConexao() {
		try {
			Connection con = conectar();
			System.out.println(con);
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	//CRUD READ para criar a listagem
	public ArrayList<JavaBeans> listarContatos(){
		//Criando objeto de acesso para acesso da classe JavaBeans
		ArrayList<JavaBeans> contatos = new ArrayList<>();
		String read ="select * from contatos order by nome";
		try {
			//abrir conexão com o bd
			Connection con = conectar();
			//query para executar comando no bd
			PreparedStatement pst = con.prepareStatement(read);
			ResultSet rs = pst.executeQuery();
			//laço abaixo será executado enquanto houver contatos no bd
			while(rs.next()) {
				//variaveis de apoio que recebem os dados do banco
				String idcon = rs.getString(1);
				String nome = rs.getString(2);
				String fone = rs.getString(3);
				String email = rs.getString(4);
				//popular tudo q vem no banco dentro do ArrayList, colocou no arrayList um novo
				//objeto JavaBeans com os retornos realizados acima.
				contatos.add(new JavaBeans(idcon, nome, fone, email));
			}
			con.close();
			return contatos;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
		
	}
	
	//CRUD UPDATE parte 1
	//selecionar o contato
	public void selecionarContato(JavaBeans contato) {
		String read2 = "select * from contatos where idcon = ?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read2);
			pst.setString(1, contato.getIdcon());
			ResultSet rs = pst.executeQuery();	
			while(rs.next()) {
				contato.setIdcon(rs.getString(1));
				contato.setNome(rs.getString(2));
				contato.setFone(rs.getString(3));
				contato.setEmail(rs.getString(4));
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	//CRUD UPDATE parte 2 - editar o contato
	
	public void alterarContato(JavaBeans contato) {
		String create = "update contatos set nome=?, fone=?, email=? where idcon=?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(create);
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			pst.setString(4, contato.getIdcon());
			pst.executeUpdate();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
		
	//CRUD DELETE
	public void deletarContato(JavaBeans contato) {
		String delete = "delete from contatos where idcon=?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(delete);
			pst.setString(1, contato.getIdcon());
			pst.executeUpdate();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
}
