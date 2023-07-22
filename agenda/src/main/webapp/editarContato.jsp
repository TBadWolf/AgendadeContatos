<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Agenda de Contatos</title>
<link rel="icon" href="imagens/telephone.png">
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<img class="agenda" src="imagens/agenda.png">
	<h1>Editar contato</h1>
	<form class="alinha" name="frmContato" action="update">
		<table>
			<tr>
				<td><label>ID:</label> <input type="text" name="idcon" class="caixaId" readonly value="<%out.print(request.getAttribute("idcon"));%>"></td>
			</tr>
			<tr>
				<td><input type="text" name="nome" class="caixaInput" value="<%out.print(request.getAttribute("nome"));%>"></td>
			</tr>
			<tr>
				<td><input type="text" name="fone" class="caixaInput" value="<%out.print(request.getAttribute("fone"));%>"></td>
			</tr>
			<tr>
				<td><input type="text" name="email" class="caixaInput" value="<%out.print(request.getAttribute("email"));%>"></td>				
			</tr>
		</table>
		<input class="botao1" type="button" value="Salvar" onclick="validar()">	
	</form>
	<script src="scripts/validador.js"></script>
</body>
</html>