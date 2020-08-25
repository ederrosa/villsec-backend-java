<h1 align="center">V1LLSEC - SERVER</h1>

![V1LLSEC](https://github.com/ederrosa/villsec-backend-java/blob/master/src/main/resources/assets/img/camada1.png)

<h2 align="left">Descrição do Projeto</h2>
<p align="justify">Esta aplicação JAVA tem como objetivo servir o Fontend-V1LLSEC de dados, serviços e segurança necessários para o funcionamento pleno. A aplicação como um todo busca promover a interação entre o DJ V1llsec e seus seguidores, divulgação de músicas, vídeos, fotos e datas de eventos, bem como marketing profissional do DJ.</p>

<h2 align="left">Badges</h2>

![Badge](https://img.shields.io/static/v1?label=AMAZONAWS&message=File_Repository&color=ff8000&labelColor=000000&style=for-the-badge&logo=AMAZON)
[![GitHub forks](https://img.shields.io/github/forks/ederrosa/villsec-backend-java?style=for-the-badge)](https://github.com/ederrosa/villsec-backend-java/network)
![Badge](https://img.shields.io/static/v1?label=HIBERNATE&message=Framework_ORM&color=59666c&labelColor=bcae79&style=for-the-badge)
[![GitHub issues](https://img.shields.io/github/issues/ederrosa/villsec-backend-java?style=for-the-badge)](https://github.com/ederrosa/villsec-backend-java/issues)
![Badge](https://img.shields.io/static/v1?label=JAVA&message=Language&color=5382a1&labelColor=f89820&style=for-the-badge&logo=JAVA)
![Badge](https://img.shields.io/static/v1?label=JJWT&message=Secury_Token&style=for-the-badge&logo)
![APM](https://img.shields.io/apm/l/vim-mode?style=for-the-badge)
![Badge](https://img.shields.io/static/v1?label=MAVEN&message=Comprehension_Tool&color=000000&labelColor=ff8000&style=for-the-badge)
![Badge](https://img.shields.io/static/v1?label=SPRING&message=Framework&color=<COLOR>&labelColor=000000&style=for-the-badge&logo=SPRING)
[![GitHub stars](https://img.shields.io/github/stars/ederrosa/villsec-backend-java?style=for-the-badge)](https://github.com/ederrosa/villsec-backend-java/stargazers)
![Badge](https://img.shields.io/static/v1?label=THYMELEAF&message=Server-Side_Template&color=aea99f&labelColor=005f0f&style=for-the-badge)

<h2 align="left">Status do Projeto</h2>
Concluido :heavy_check_mark:

<h2 align="left">Tabela de Conteúdos</h2>

<!--ts-->
* [default - sem autenticação](https://v1llsec.herokuapp.com/)
   * [Albúns](https://v1llsec.herokuapp.com/#albuns)
   * [Eventos](https://v1llsec.herokuapp.com/#eventos)
   * [Galeria de Fotos](https://v1llsec.herokuapp.com/#fotos)
   * [Galeria de Vídeos](https://v1llsec.herokuapp.com/#videos)
   * [Contato](https://v1llsec.herokuapp.com/#rodape)
   * [Login](https://v1llsec.herokuapp.com/login)
   * [Registrar-se: apenas seguidores](https://v1llsec.herokuapp.com/registrar)  
* Proprietário Logado
   * Albúns: [CRUD]
   * Eventos: [CRUD]
   * Fotos: [CRUD]
   * Galerias: [CRUD]
   * Músicas: [CRUD]
   * Proprietário: [RUD]
   * Seguidor: [RD]
   * Vídeos: [CRUD]
   * Alerta de Eventos: Envia email a todos da mesma cidade do evento
* Seguidor Logado   
   * Albúns: [R]
   * Eventos: [R]
   * Fotos: [R]
   * Galerias: [R]
   * Músicas: [R]
   * Seguidor: [R] - propríos dados
   * Vídeos: [R]
* Admin Logado
   * Proprietário: [CRUD]
   * Alerta de Eventos: Reseta limite de envios
<!--te-->

<h2 align="left">Features</h2>

- [x] Cadastro de Proprietário
- [x] Cadastro de Seguidor
- [x] Cadastro de Galeria Foto
- [x] Cadastro de Galeria de Vídeo
- [x] Cadastro de Albúns
- [x] Cadastro de Músicas
- [x] Cadastro de Eventos
- [x] Notificação de Evento Via Email
- [ ] Loja

<h2 align="left">Pré-requisitos</h2>

  * [java-jdk-1.8](https://www.oracle.com/br/java/technologies/javase/javase-jdk8-downloads.html)
  * [mysql](https://www.mysql.com/downloads/)
  * [apache-tomcat](https://tomcat.apache.org/download-80.cgi)
  * [Conta Amazon-AWS](https://aws.amazon.com/pt/)
  * [STS ou Plugins](https://spring.io/tools)

<h2 align="left">Tecnologias Utilizadas</h2>

  * [AWS](https://aws.amazon.com/pt/)
  * [GIT](https://git-scm.com/)
  * [GITHUB](https://github.com/)
  * [HEROKU](https://www.heroku.com/)
  * [HIBERNATE](https://hibernate.org/)
  * [JAVA](https://www.java.com/)
  * [MAVEN](https://maven.apache.org/)
  * [MYSQL](https://www.mysql.com/)
  * [TOMCAT](https://tomcat.apache.org/)  
  * [SPRING-BOOT](https://spring.io/) 
 
### 🎲 Rodando o Back End (servidor)

```bash
# Clone este repositório
$ git clone <git@github.com:ederrosa/villsec-backend-java.git>

# Acesse a pasta do projeto no terminal/cmd
$ cd villsec-backend-java
```
* Baixe as dependências

* Crie um Bucket no S3-AWS

* Abra o arquivo application.properties

* preecha todos os campo em branco no application.properties
  * spring.datasource.url = ?????
  * spring.datasource.username = ?????
  * spring.datasource.password = ?????
  * jwt.secret = ?????
  * jwt.expiration = ?????
  * aws.access_key_id = ?????
  * aws.secret_access_key = ?????
  * s3.bucket = ?????
  * s3.region = ?????
  * spring.mail.username = ?????
  * spring.mail.password = ?????
  * default.sender = ?????
  * default.recipient = ?????
  * default.principal = ?????
  
* execute o arquivo SQL no Mysql
  * [SQL-SCRIPT](https://github.com/ederrosa/villsec-backend-java/blob/master/docs/v1llsec-bd.sql)

* P.S: todo a segurança esta ativa por tanto é necesserario criar um usuário do tipo admin no banco de dados.
* ou ir até o arquivo:
  * Adicione um novo "seguidor/**"
  * [use o PostMain](https://www.postman.com/downloads/)
  * envie um JSON para seu_local_host/seguidores/insert
  * Troque o tipo de usuario no Banco para Admin, proprietário

<h2 align="left">DOCS</h2>

  *[Documento de Arquitetura de Software](https://github.com/ederrosa/villsec-backend-java/blob/master/docs/Documento%20de%20Arquitetura.pdf) 

## Autor
---
<img style="border-radius: 50%;" src="https://github.com/ederrosa.png" width="100px;" alt=""/>
<br />
<sub><b>Eder Rosa</b></sub></a> <a href="https://github.com/ederrosa" title="github"></a>

Desenvolvido por Eder Rosa 👋🏽 Entre em contato!

[![Badge](https://img.shields.io/static/v1?label=GitHub&message=Eder_Rosa&style=for-the-badge&logo=GITHUB)](https://github.com/ederrosa)
[![Badge](https://img.shields.io/static/v1?label=GitHub&message=Eder_Rosa&color=000000&labelColor=0e76a8&style=for-the-badge&logo=LINKEDIN)](https://www.linkedin.com/in/eder-xavier-de-souza-rosa-370a4b74/)
![Badge](https://img.shields.io/static/v1?label=Hotmail&message=eder-x@hotmail.com&&color=000000&labelColor=00A4EF&style=for-the-badge)
![Badge](https://img.shields.io/static/v1?label=Outlook&message=eder.rosa01@fatec.sp.gov.br&&color=000000&labelColor=FFB900&style=for-the-badge)
