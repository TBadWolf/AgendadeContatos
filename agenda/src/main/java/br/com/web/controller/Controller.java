package br.com.web.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import br.com.web.model.DAO;
import br.com.web.model.*;

@WebServlet(urlPatterns = {"/Controller", "/main", "/insert", "/select", "/update", "/delete", "/report"})
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
    DAO dao = new DAO();   
    JavaBeans contato = new JavaBeans();
    public Controller() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		if(action.equals("/main")) {
			contatos(request, response);
		}else if (action.equals("/insert")) {
			novoContato(request, response);
		}else if (action.equals("/select")) {
			listarEditarContato(request, response);
		}else if (action.equals("/update")) {
			editarContato(request, response);
		}else if (action.equals("/delete")) {
			excluirContato(request, response);
		}else if (action.equals("/report")) {
			gerarRelatorio(request, response);
		}else {
			response.sendRedirect("index.html");
		}
	}
	
	//Listar contatos
	protected void contatos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//criar objeto que irá receber os dados JavaBeans
		ArrayList<JavaBeans> lista = dao.listarContatos();
		//Encaminhamento da lista ao documento agenda.jsp
		request.setAttribute("contatos", lista);
		RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");
		rd.forward(request, response);
	}
	
	//Inserir contatos
	protected void novoContato(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		//invocar o método inserir contato pssando contato como objeto
		dao.inserirContato(contato);
		//redirecionar para o doc agenda.jsp
		response.sendRedirect("main");
	}
	
	//Editar contato, esse listar mostra as informações do contato selecionado para edição
	protected void listarEditarContato(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Recebimento do Id do contato que será editado
		String idcon = request.getParameter("idcon");
		//Setar variavel JavaBeans
		contato.setIdcon(idcon);
		//executar o método selecionarContato(DAO)
		dao.selecionarContato(contato);
		//setar os atributos do formulário com o conteudo JavaBeans
		request.setAttribute("idcon", contato.getIdcon());
		request.setAttribute("nome", contato.getNome());
		request.setAttribute("fone", contato.getFone());
		request.setAttribute("email", contato.getEmail());
		//Encaminhar ao email editarContato.jsp
		RequestDispatcher rd = request.getRequestDispatcher("editarContato.jsp");
		rd.forward(request, response);	
	}
	
	protected void editarContato(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//setar as variaveis JavaBeans
		contato.setIdcon(request.getParameter("idcon"));
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));	
		dao.alterarContato(contato);
		//redirecionar para o doc agenda.jsp atualizando o contato
		response.sendRedirect("main");
	}
	
	protected void excluirContato(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//recebimento do id do contato a ser excluido (confirmador.js)
		String idcon = request.getParameter("idcon");
		//setar a variavel Icon com a JavaBeans
		contato.setIdcon(idcon);
		//executar o metodo deletar contato passando oobjeto contato como parametro
		dao.deletarContato(contato);
		response.sendRedirect("main");
	}
	
	//Gerar relatório em pdf
	protected void gerarRelatorio(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Document documento = new Document();
		try {
			//tipo de conteúdo
			response.setContentType("apllication/pdf");
			//nome para o doc
			response.addHeader("Content-Disposition", "inline;filename=" + "contatos.pdf");
			//criar o doc
			PdfWriter.getInstance(documento, response.getOutputStream());
			//abrir o doc
			documento.open();
			documento.add(new Paragraph("Lista de contatos: "));
			documento.add(new Paragraph(" "));
			//criar uma tabela
			PdfPTable tabela = new PdfPTable(3);
			//cabeçalho
			PdfPCell col1 = new PdfPCell(new Paragraph("Nome"));
			PdfPCell col2 = new PdfPCell(new Paragraph("Fone"));
			PdfPCell col3 = new PdfPCell(new Paragraph("Email"));
			tabela.addCell(col1);
			tabela.addCell(col2);
			tabela.addCell(col3);
			//Popular tabelas com contatos
			ArrayList<JavaBeans> lista = dao.listarContatos();
			for(int i=0; i<lista.size(); i++) {
				tabela.addCell(lista.get(i).getNome());
				tabela.addCell(lista.get(i).getFone());
				tabela.addCell(lista.get(i).getEmail());
			}
			
			documento.add(tabela);
			documento.close();
		} catch (Exception e) {
			System.out.println(e);
			documento.close();
		}
	}
}
