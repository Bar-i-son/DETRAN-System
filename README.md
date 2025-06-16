========================================================
== + Informações no Final =========
========================================================
Pasta =src=
Todos os packages e todas as classes do sistema

Pasta =lib=
Tem o driver JDBC

=========================================================

Descompactar arquivo .zip
Instalar servidor MySQL


Abrir o Intellij
Criar um novo projeto
Passar a pasta src para o projeto criado
> Explorador de arquivos > 
> Disco Local 
> Usuarios >
> "Selecionar o usuario" 
> IdeaProjects > 
> "Selecionar o projeto criado" > 
> "Colar a pasta lib no projeto">
No Intellij
Abrir o seu projeto
Files > Project Structure
Modules > Dependencies
Selecionar o + (Logo abaixo de Module SDK)
Selecionar JARs or DIrectories
Selecionar o projeto atual
Abrir a pasta lib
Selecionar o drive MySQL Connector (.jar)
Dar OK
Marcar a caixinha do drive
Aplicar e dar OK

===root/Buca@2409===
Abrir o MySQL Workbench
Criar uma conexão
Criar as tabelas com dados já salvos(códigos no .zip)
===root/Buca@2409===

Executar main.java



=============================================
Windows + R
Escrever services.msc
Procurar MySQL(80)
Parar e Iniciar
=============================================
Comando pra excluir todo banco de dados:
DROP DATABASE IF EXISTS detran;
=============================================
Download MySQL Workbench
https://dev.mysql.com/downloads/workbench/
=============================================
