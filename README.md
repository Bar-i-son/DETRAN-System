Projeto para gerenciamento de veículos inspirado no sistema do Detran
Integrantes: Pedro Barison, Guilherme Duarte, Davi Barcelos, Eduardo Américo, Lucas Mendes

Escopo dos Testes:
Cadastrar veiculo - tranferir veiculo - dar baixa em um veiculo

CT01
- Funcionalidade: Cadastro de veículos
- Descrição: Validar cadastro de um novo veículo
- Entrada: Dados do carro e proprietário atual
- Resultado Esperado: Carro cadastrado e salvo no banco de dados
- Tipo de Teste: Unitário - Caixa Preta

CT02
- Funcionalidade: Transferência de veículos
- Descrição: Fazer a transferência de um veículo para o seu novo proprietário
- Entrada: Dados do carro e do seu proprietário atual, junto com os dados do novo proprietário
- Resultado Esperado: Proprietário antigo perde a posse do carro no sistema, e o veículo passa a ser associado ao novo proprietário
- Tipo de Teste: Unitário - Caixa Preta

CT03
- Funcionalidade: Dar baixa de um veículo no sistema
- Descrição: Retirar um veículo do banco de dados do sistema
- Entrada: Placa do carro
- Resultado Esperado: O veículo é apagado do banco de dados
- Tipo de Teste: Unitário - Caixa Preta


Nas 3 funcionalidades, iremos testar os valores limites, para verificar quais são aceitos para que o sistema funcione, criando grupos de valores diferentes (ex: CPF com 10, 11 e 12 digitos numéricos e CPF com letras)

=========================================================

Pasta =src=
- Todos os packages e todas as classes do sistema

Pasta =lib=
- Tem o driver JDBC

=========================================================

Descompactar arquivo .zip

Instalar servidor MySQL


Abrir o Intellij
- Criar um novo projeto
- Criar um Package dentro de src chamado detran 
- Passar os 4 Packages e a classe main para o dentro do Package detran

Abrir o Explorador de Arquivos
> Disco Local >
> Usuarios >
> "Selecionar o usuario" >
> IdeaProjects > 
> "Selecionar o projeto criado" > 
> "Colar a pasta lib no projeto"

No Intellij
- Abrir o seu projeto
> Files > Project Structure
> Modules > Dependencies
- Selecionar o + (Logo abaixo de Module SDK)
- Selecionar JARs or Directories
- Selecionar o projeto atual
- Abrir a pasta lib
- Selecionar o drive MySQL Connector (.jar)
- Dar OK
- Marcar a caixinha do drive
- Aplicar e dar OK
- Abrir a classe conexaoSQL
- Colocar a senha como root

=============================================

Abrir o MySQL Workbench
- Criar uma conexão
- Criar as tabelas com dados já salvos
> comandosSQL >
> Segundo_comandoSQL >
> Terceiro_comandoSQL

=============================================

Executar main.java

=============================================

Windows + R
- Escrever services.msc
- Procurar MySQL(80)
- Parar e Iniciar
 
=============================================

Comando pra excluir todo banco de dados:
DROP DATABASE IF EXISTS detran;

=============================================

Download MySQL Workbench
https://dev.mysql.com/downloads/workbench/

=============================================
