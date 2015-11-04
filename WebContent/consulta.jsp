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
			<form action="Controller" method="post">
				<input type="hidden" id="cmd" required name="cmd" value="Logout" />
				<input type="submit" value="Logout">
			</form>

			<div class="button"></div>
			<!-- button -->
		</section>
		<!-- content -->
	</div>
	<!-- container -->
</body>
</html>
