<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="br.com.web.model.JavaBeans"%>
    <%@ page import="java.util.ArrayList"%>
<%
	ArrayList<JavaBeans> lista = (ArrayList<JavaBeans>)
	request.getAttribute("contatos");
//teste
/* 	for(int i=0; i < lista.size(); i++){
		out.println(lista.get(i).getIdcon());
		out.println(lista.get(i).getNome());
		out.println(lista.get(i).getFone());	
		out.println(lista.get(i).getEmail());	
	} */
%> 
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<title>Agenda de Contatos</title>
<link rel="icon" href="imagens/telephone.png">
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<img class="agenda" src="imagens/agenda.png">
	<h1>Agenda de Contatos</h1>
	<a class="botao1" href="novo.html">Novo Contato</a>
	<a class="botao1" href="report">Relatório</a>
	<br></br>
	<table id="tabela">
		<thead>
			<th>Id</th>
			<th>Nome</th>
			<th>Fone</th>
			<th>Email</th>	
			<th>Opcões</th>					
		</thead>
		<tbody>
			<% for(int i=0; i < lista.size(); i++){	%>
				<tr>
					<td><%= lista.get(i).getIdcon() %></td>
					<td><%= lista.get(i).getNome() %></td>
					<td><%= lista.get(i).getFone() %></td>
					<td><%= lista.get(i).getEmail() %></td>	
					<td><a href ="select?idcon=<%= lista.get(i).getIdcon() %>" class="botao1">Editar</a>	
						<a href ="javascript: confirmar(<%= lista.get(i).getIdcon() %>)" class="botaoDelete">Excluir</a></td>		
				</tr>							
			<%} %>
		</tbody>
	</table>
	<script src="scripts/confirmador.js"></script>
</body>
</html>