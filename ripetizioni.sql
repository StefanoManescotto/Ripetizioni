create table if not exists Utenti(
	idUtente int primary key AUTO_INCREMENT,
	nome VARCHAR(30) not null,
	cognome VARCHAR(30) not null,
	email VARCHAR(50) not null,
	password VARCHAR(64) not null,
	ruolo VARCHAR(15) default "cliente"
);

create table if not exists Corsi(
	titolo VARCHAR(30) primary key,
	descrizione VARCHAR(30),
	attiva TINYINT(1) default 1
);

create table if not exists Docenti(
	idDocente int primary key AUTO_INCREMENT,
	nome VARCHAR(30),
	cognome VARCHAR(30),
	attiva TINYINT(1) default 1
);

create table if not exists Corso_docente(
	idCorso VARCHAR(30),
	idDocente int,
	primary key(idCorso, idDocente),
	foreign key (idCorso) references Corsi (titolo),
	foreign key (idDocente) references Docenti (idDocente)
);

create table if not exists Prenotazioni(
	idPrenotazione int primary key AUTO_INCREMENT,
	idUtente int,
	idCorso VARCHAR(30),
	idDocente int,
	dataPren DATE,
	oraPren SMALLINT(6),
	stato VARCHAR(20) default "attiva",
	foreign key (idUtente) references Utenti(idUtente),
	foreign key (idCorso) references Corsi(titolo),
	foreign key (idDocente) references Docenti(idDocente)
);


insert into Utenti (nome, cognome, email, password) values("nu1", "cu1", "nu1@gmail.com", "psw");
insert into Utenti (nome, cognome, email, password) values("nu2", "cu2", "nu2@gmail.com", "psw");
insert into Utenti (nome, cognome, email, password, ruolo) values("nu3", "cu3", "nu3@gmail.com", "psw", "amministratore");
insert into Utenti (nome, cognome, email, password) values("nu4", "cu4", "nu4@gmail.com", "psw");

insert into Corsi (titolo, descrizione) values ("matematica", "corso di matematica");
insert into Corsi (titolo, descrizione) values ("italiano", "corso di italiano");
insert into Corsi (titolo, descrizione) values ("informatica", "corso di informatica");
insert into Corsi (titolo, descrizione) values ("inglese", "corso di inglese");

insert into Docenti (nome, cognome) values ("Mario", "Rossi");
insert into Docenti (nome, cognome) values ("Marco", "Verdi");
insert into Docenti (nome, cognome) values ("Claudio", "Bianchi");

insert into Corso_docente (idCorso, idDocente) values ("matematica", 1);
insert into Corso_docente (idCorso, idDocente) values ("inglese", 2);
insert into Corso_docente (idCorso, idDocente) values ("informatica", 3);
insert into Corso_docente (idCorso, idDocente) values ("italiano", 2);
insert into Corso_docente (idCorso, idDocente) values ("matematica", 3);

insert into Prenotazioni (idUtente, idCorso, idDocente, dataPren, oraPren) values (2, "matematica", 1, "2022-2-11", 17);
