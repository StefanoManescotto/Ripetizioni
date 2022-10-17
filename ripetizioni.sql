drop table if exists Utenti, Corsi, Docenti, Corso_docente;

create table if not exists Utenti(
	idUtente int primary key AUTO_INCREMENT,
	nome VARCHAR(30) not null,
	cognome VARCHAR(30) not null,
	email VARCHAR(50) not null,
	password VARCHAR(50) not null,
	ruolo VARCHAR(15) default "cliente"
);

create table if not exists Corsi(
	titolo VARCHAR(30) primary key,
	descrizione VARCHAR(30)
);

create table if not exists Docenti(
	idDocente int primary key AUTO_INCREMENT,
	nome VARCHAR(30),
	cognome VARCHAR(30)
);

create table if not exists Corso_docente(
	titolo VARCHAR(30),
	idDocente int,
	primary key(titolo, idDocente),
	foreign key (titolo) references Corsi (titolo),
	foreign key (idDocente) references Docenti (idDocente)
);

create table if not exists Prenotazioni(
	idPrenotazione int primary key AUTO_INCREMENT,
	idUtente int,
	idCorso VARCHAR(30),
	idDocente int,
	dataPren DATE,
	oraPren TIME,
	foreign key (idUtente) references Utenti(idUtente),
	foreign key (idCorso) references Corsi(titolo),
	foreign key (idDocente) references Docenti(idDocente)
);


insert into Utenti (nome, cognome, email, password) values("nu1", "cu1", "nu1@gmail.com", "psw");
insert into Utenti (nome, cognome, email, password) values("nu2", "cu2", "nu2@gmail.com", "psw");
insert into Utenti (nome, cognome, email, password, ruolo) values("nu3", "cu3", "nu3@gmail.com", "psw", "amministratore");
insert into Utenti (nome, cognome, email, password) values("nu4", "cu4", "nu4@gmail.com", "psw");
	
drop table Utenti;