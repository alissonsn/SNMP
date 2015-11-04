<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="ie6 ielt8"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="ie7 ielt8"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="ie8"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!-->
<html lang="en">
<!--<![endif]-->
<head>
<meta charset="utf-8">
<title>SNMP</title>
<link rel="stylesheet" type="text/css" href="./design/css/style.css" />
<link rel="stylesheet" type="text/css" href="./design/css/estilo.css" />

</head>
<body>


	<div class="container">
		<section id="content">
			<ol id="toc">
				<li><a href="index.jsp">Home</a></li>
				<li><a href="cadastrados.jsp">Modelos Cadastrados</a></li>
				<li><a href="consulta.jsp">Consulta Switch</a></li>
				<li><a href="registro.jsp">Registrar Modelos</a></li>

			</ol>

			<br>

			<form name="formConta" method="post" action="Controller">

				<input type="hidden" id="cmd" required name="cmd" value="Registro" />

				<div>
					<input type="text" name="enterprise"
						placeholder="O Numero Enterprise equivalente para o modelo"><br>

				</div>
				<div>
					<input type="text" name="modelo" placeholder="Modelo do Switch"><br>
				</div>
				<div>
					<input type="text" name="portas"
						placeholder="Nome da classe que implementa a captura das portas dos switches"><br>
				</div>
				<div>
					<input type="text" name="vlans"
						placeholder="Nome da classe que implementa a captura das vlans dos switches"><br>
				</div>

				<div>

					<input type="submit" id="enviar" value="Enviar">


				</div>

			</form>














			<div class="button"></div>
			<!-- button -->
		</section>
		<!-- content -->
	</div>
	<!-- container -->
</body>
</html>
