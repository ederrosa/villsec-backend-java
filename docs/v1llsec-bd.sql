-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 25-Ago-2020 às 15:38
-- Versão do servidor: 10.1.21-MariaDB
-- PHP Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `villsec`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `album`
--

CREATE TABLE `album` (
  `id` bigint(20) NOT NULL,
  `dt_criacao` datetime DEFAULT NULL,
  `dt_ultima_alteracao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `verification_code` bigint(20) DEFAULT NULL,
  `ano` varchar(255) DEFAULT NULL,
  `codigo` varchar(255) DEFAULT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `genero` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `file_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `arquivo`
--

CREATE TABLE `arquivo` (
  `id` bigint(20) NOT NULL,
  `dt_criacao` datetime DEFAULT NULL,
  `dt_ultima_alteracao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `verification_code` bigint(20) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `url` longblob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `autenticacaoss`
--

CREATE TABLE `autenticacaoss` (
  `id` bigint(20) NOT NULL,
  `dt_criacao` datetime DEFAULT NULL,
  `dt_ultima_alteracao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `verification_code` bigint(20) DEFAULT NULL,
  `login` varchar(50) DEFAULT NULL,
  `matricula` varchar(20) DEFAULT NULL,
  `nome_img_perfil` varchar(255) DEFAULT NULL,
  `senha` varchar(255) DEFAULT NULL,
  `tipo_usuario` int(11) DEFAULT NULL,
  `uri_img_perfil` longblob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `email`
--

CREATE TABLE `email` (
  `id` bigint(20) NOT NULL,
  `dt_criacao` datetime DEFAULT NULL,
  `dt_ultima_alteracao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `verification_code` bigint(20) DEFAULT NULL,
  `email` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `endereco`
--

CREATE TABLE `endereco` (
  `id` bigint(20) NOT NULL,
  `dt_criacao` datetime DEFAULT NULL,
  `dt_ultima_alteracao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `verification_code` bigint(20) DEFAULT NULL,
  `bairro` varchar(50) DEFAULT NULL,
  `cep` varchar(10) DEFAULT NULL,
  `cidade` varchar(50) DEFAULT NULL,
  `estado` varchar(50) DEFAULT NULL,
  `logradouro` varchar(100) DEFAULT NULL,
  `pais` varchar(50) DEFAULT NULL,
  `google_maps_url` longblob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `evento`
--

CREATE TABLE `evento` (
  `id` bigint(20) NOT NULL,
  `dt_criacao` datetime DEFAULT NULL,
  `dt_ultima_alteracao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `verification_code` bigint(20) DEFAULT NULL,
  `classificacao` varchar(255) DEFAULT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `dia_inicio` datetime DEFAULT NULL,
  `dia_termino` datetime DEFAULT NULL,
  `hora_inicio` varchar(255) DEFAULT NULL,
  `hora_termino` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `tipo_evento` int(11) DEFAULT NULL,
  `file_id` bigint(20) DEFAULT NULL,
  `endereco_id` bigint(20) DEFAULT NULL,
  `ingresso_url` longblob,
  `alerta` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `galeria`
--

CREATE TABLE `galeria` (
  `id` bigint(20) NOT NULL,
  `dt_criacao` datetime DEFAULT NULL,
  `dt_ultima_alteracao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `verification_code` bigint(20) DEFAULT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `status` tinyint(1) DEFAULT '1',
  `titulo` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `imagem`
--

CREATE TABLE `imagem` (
  `id` bigint(20) NOT NULL,
  `dt_criacao` datetime DEFAULT NULL,
  `dt_ultima_alteracao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `verification_code` bigint(20) DEFAULT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `titulo` varchar(100) DEFAULT NULL,
  `file_id` bigint(20) DEFAULT NULL,
  `galeria_id` bigint(20) DEFAULT NULL,
  `imagem_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `musica`
--

CREATE TABLE `musica` (
  `id` bigint(20) NOT NULL,
  `dt_criacao` datetime DEFAULT NULL,
  `dt_ultima_alteracao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `verification_code` bigint(20) DEFAULT NULL,
  `autor` varchar(255) DEFAULT NULL,
  `bpm` int(11) DEFAULT NULL,
  `coautor` varchar(255) DEFAULT NULL,
  `copyright` bit(1) DEFAULT NULL,
  `duracao` varchar(255) DEFAULT NULL,
  `faixa` int(11) DEFAULT NULL,
  `idioma` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `album_id` bigint(20) DEFAULT NULL,
  `file_id` bigint(20) DEFAULT NULL,
  `musica_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `perfis`
--

CREATE TABLE `perfis` (
  `autenticacaoss_id` bigint(20) NOT NULL,
  `perfil` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `pessoa`
--

CREATE TABLE `pessoa` (
  `id` bigint(20) NOT NULL,
  `dt_criacao` datetime DEFAULT NULL,
  `dt_ultima_alteracao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `verification_code` bigint(20) DEFAULT NULL,
  `data_nascimento` datetime DEFAULT NULL,
  `genero` varchar(20) DEFAULT NULL,
  `nome` varchar(100) DEFAULT NULL,
  `status_pessoa` tinyint(1) DEFAULT '1',
  `autenticacaoss_id` bigint(20) DEFAULT NULL,
  `email_id` bigint(20) DEFAULT NULL,
  `endereco_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `proprietario`
--

CREATE TABLE `proprietario` (
  `facebook` longblob,
  `instagram` longblob,
  `sobre_mim` text,
  `spotify` longblob,
  `twitch` longblob,
  `twitter` longblob,
  `youtube` longblob,
  `id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `seguidor`
--

CREATE TABLE `seguidor` (
  `id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `telefone`
--

CREATE TABLE `telefone` (
  `id` bigint(20) NOT NULL,
  `dt_criacao` datetime DEFAULT NULL,
  `dt_ultima_alteracao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `verification_code` bigint(20) DEFAULT NULL,
  `numero_telefone` varchar(20) DEFAULT NULL,
  `tipo_telefone` int(11) DEFAULT NULL,
  `pessoa_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `video`
--

CREATE TABLE `video` (
  `id` bigint(20) NOT NULL,
  `dt_criacao` datetime DEFAULT NULL,
  `dt_ultima_alteracao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `verification_code` bigint(20) DEFAULT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `embed` varchar(255) DEFAULT NULL,
  `titulo` varchar(255) DEFAULT NULL,
  `file_id` bigint(20) DEFAULT NULL,
  `galeria_id` bigint(20) DEFAULT NULL,
  `video_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `album`
--
ALTER TABLE `album`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK2a83sqfqccof5nafv67klu1cu` (`file_id`);

--
-- Indexes for table `arquivo`
--
ALTER TABLE `arquivo`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `autenticacaoss`
--
ALTER TABLE `autenticacaoss`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_maklgxai0atd2ph1iynbidefw` (`login`),
  ADD UNIQUE KEY `UK_lfkvupsb3fvlkarl7h7nqpj0h` (`matricula`);

--
-- Indexes for table `email`
--
ALTER TABLE `email`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_ek29mh30yo2rxnsy4svwbgogh` (`email`);

--
-- Indexes for table `endereco`
--
ALTER TABLE `endereco`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `evento`
--
ALTER TABLE `evento`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKt357sfqnmedknbohdsc54230r` (`file_id`),
  ADD KEY `FKqd0alhw4xw95c617h4kgil7qs` (`endereco_id`);

--
-- Indexes for table `galeria`
--
ALTER TABLE `galeria`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `imagem`
--
ALTER TABLE `imagem`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK9f3nep4cltsboxgdsjqhnl0pb` (`file_id`),
  ADD KEY `FKe4odh8odih8h1oflfyk6jmmid` (`galeria_id`),
  ADD KEY `FKi09psjcsm8hgtrvhpdq2fxdvu` (`imagem_id`);

--
-- Indexes for table `musica`
--
ALTER TABLE `musica`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKqc7nayr65xaoxxcxw5nliassj` (`album_id`),
  ADD KEY `FKka5dcck8k50k5lckxdabmomti` (`file_id`),
  ADD KEY `FK4fecn2osrsrjv60q2o96hqb43` (`musica_id`);

--
-- Indexes for table `perfis`
--
ALTER TABLE `perfis`
  ADD KEY `FKrvyojm0gssq1tdk7y5b7hv9qa` (`autenticacaoss_id`);

--
-- Indexes for table `pessoa`
--
ALTER TABLE `pessoa`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKha68xauc2ju9f4ghs3xr97mts` (`autenticacaoss_id`),
  ADD KEY `FK33mhw0bawaiddp4eji4pk6mby` (`email_id`),
  ADD KEY `FKei4abnsw085kx27j89rp796ny` (`endereco_id`);

--
-- Indexes for table `proprietario`
--
ALTER TABLE `proprietario`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `seguidor`
--
ALTER TABLE `seguidor`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `telefone`
--
ALTER TABLE `telefone`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKdfopyen4k14hhqgi17u5ct0h3` (`pessoa_id`);

--
-- Indexes for table `video`
--
ALTER TABLE `video`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKp1ucgnrpysrkexu7gmaofw5me` (`file_id`),
  ADD KEY `FKc52ti97o7gnw1tp4raoe347dd` (`galeria_id`),
  ADD KEY `FKlgf61f1ingkynjml3pgft0m2r` (`video_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `album`
--
ALTER TABLE `album`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `arquivo`
--
ALTER TABLE `arquivo`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
--
-- AUTO_INCREMENT for table `autenticacaoss`
--
ALTER TABLE `autenticacaoss`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `email`
--
ALTER TABLE `email`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `endereco`
--
ALTER TABLE `endereco`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `evento`
--
ALTER TABLE `evento`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `galeria`
--
ALTER TABLE `galeria`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `imagem`
--
ALTER TABLE `imagem`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `musica`
--
ALTER TABLE `musica`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `pessoa`
--
ALTER TABLE `pessoa`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `telefone`
--
ALTER TABLE `telefone`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `video`
--
ALTER TABLE `video`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- Constraints for dumped tables
--

--
-- Limitadores para a tabela `album`
--
ALTER TABLE `album`
  ADD CONSTRAINT `FK2a83sqfqccof5nafv67klu1cu` FOREIGN KEY (`file_id`) REFERENCES `arquivo` (`id`);

--
-- Limitadores para a tabela `evento`
--
ALTER TABLE `evento`
  ADD CONSTRAINT `FKqd0alhw4xw95c617h4kgil7qs` FOREIGN KEY (`endereco_id`) REFERENCES `endereco` (`id`),
  ADD CONSTRAINT `FKt357sfqnmedknbohdsc54230r` FOREIGN KEY (`file_id`) REFERENCES `arquivo` (`id`);

--
-- Limitadores para a tabela `imagem`
--
ALTER TABLE `imagem`
  ADD CONSTRAINT `FK9f3nep4cltsboxgdsjqhnl0pb` FOREIGN KEY (`file_id`) REFERENCES `arquivo` (`id`),
  ADD CONSTRAINT `FKe4odh8odih8h1oflfyk6jmmid` FOREIGN KEY (`galeria_id`) REFERENCES `galeria` (`id`),
  ADD CONSTRAINT `FKi09psjcsm8hgtrvhpdq2fxdvu` FOREIGN KEY (`imagem_id`) REFERENCES `galeria` (`id`);

--
-- Limitadores para a tabela `musica`
--
ALTER TABLE `musica`
  ADD CONSTRAINT `FK4fecn2osrsrjv60q2o96hqb43` FOREIGN KEY (`musica_id`) REFERENCES `album` (`id`),
  ADD CONSTRAINT `FKka5dcck8k50k5lckxdabmomti` FOREIGN KEY (`file_id`) REFERENCES `arquivo` (`id`),
  ADD CONSTRAINT `FKqc7nayr65xaoxxcxw5nliassj` FOREIGN KEY (`album_id`) REFERENCES `album` (`id`);

--
-- Limitadores para a tabela `perfis`
--
ALTER TABLE `perfis`
  ADD CONSTRAINT `FKrvyojm0gssq1tdk7y5b7hv9qa` FOREIGN KEY (`autenticacaoss_id`) REFERENCES `autenticacaoss` (`id`);

--
-- Limitadores para a tabela `pessoa`
--
ALTER TABLE `pessoa`
  ADD CONSTRAINT `FK33mhw0bawaiddp4eji4pk6mby` FOREIGN KEY (`email_id`) REFERENCES `email` (`id`),
  ADD CONSTRAINT `FKei4abnsw085kx27j89rp796ny` FOREIGN KEY (`endereco_id`) REFERENCES `endereco` (`id`),
  ADD CONSTRAINT `FKha68xauc2ju9f4ghs3xr97mts` FOREIGN KEY (`autenticacaoss_id`) REFERENCES `autenticacaoss` (`id`);

--
-- Limitadores para a tabela `proprietario`
--
ALTER TABLE `proprietario`
  ADD CONSTRAINT `FKtiitt6pjrdbcejlhtu5txi4g9` FOREIGN KEY (`id`) REFERENCES `pessoa` (`id`);

--
-- Limitadores para a tabela `seguidor`
--
ALTER TABLE `seguidor`
  ADD CONSTRAINT `FK8i1o378bp1heibb16aegp70u0` FOREIGN KEY (`id`) REFERENCES `pessoa` (`id`);

--
-- Limitadores para a tabela `telefone`
--
ALTER TABLE `telefone`
  ADD CONSTRAINT `FKdfopyen4k14hhqgi17u5ct0h3` FOREIGN KEY (`pessoa_id`) REFERENCES `pessoa` (`id`);

--
-- Limitadores para a tabela `video`
--
ALTER TABLE `video`
  ADD CONSTRAINT `FKc52ti97o7gnw1tp4raoe347dd` FOREIGN KEY (`galeria_id`) REFERENCES `galeria` (`id`),
  ADD CONSTRAINT `FKlgf61f1ingkynjml3pgft0m2r` FOREIGN KEY (`video_id`) REFERENCES `galeria` (`id`),
  ADD CONSTRAINT `FKp1ucgnrpysrkexu7gmaofw5me` FOREIGN KEY (`file_id`) REFERENCES `arquivo` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
