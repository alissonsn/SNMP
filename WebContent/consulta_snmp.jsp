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
				<li><a href="consulta_snmp.jsp">Consultas SNMP</a></li>
				<li><a href="switches.jsp">Switches</a></li>

			</ol>

			<br>

			<form name="formConta" method="post" action="Controller">

				<input type="hidden" id="cmd" required name="cmd" value="consulta_snmp" />


				<div>
					<input type="text" name="tombo"
						placeholder="Tombamento do Switch"><br>
				</div>
				<div>
					<input type="text" name="ip"
						placeholder="IP do switch"><br>
				</div>
				<div>
					<input type="text" name="usuario"
						placeholder="Usuario snmp"><br>
				</div>
				<div>
					<input type="password" name="senha" placeholder="Senha do usuario snmp"><br>
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
