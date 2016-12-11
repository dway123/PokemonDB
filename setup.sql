/*
Darwin Huang
November 2016
Databases
*/

# use to setup all tables for PokemonDB

USE PokemonDB;

CREATE TABLE IF NOT EXISTS Pokemon
(
	pokemonid INTEGER,
	pname CHAR(20) UNIQUE NOT NULL,
	hp INTEGER NOT NULL,
	attack INTEGER NOT NULL,
	defense INTEGER NOT NULL,
	specialattack INTEGER NOT NULL,
	specialdefense INTEGER NOT NULL,
	speed INTEGER NOT NULL,
	PRIMARY KEY(pokemonid)
);


CREATE TABLE IF NOT EXISTS Episodes
(
	episodeid INTEGER AUTO_INCREMENT,
	ename CHAR(150) UNIQUE NOT NULL,
	episode INTEGER NOT NULL,
	season INTEGER NOT NULL,
	releasedate DATE NOT NULL,
	PRIMARY KEY(episodeid) 
);


CREATE TABLE IF NOT EXISTS Types
(
	typeid INTEGER AUTO_INCREMENT,
	tname CHAR(10) UNIQUE NOT NULL,
	PRIMARY KEY(typeid) 
);


CREATE TABLE IF NOT EXISTS Moves
( 	
	moveid INTEGER AUTO_INCREMENT,
	mname CHAR(20) UNIQUE NOT NULL,
	description CHAR(200) NOT NULL,
	category INTEGER NOT NULL,
	power INTEGER,
	accuracy INTEGER,
	PRIMARY KEY (moveid) 
);


CREATE TABLE IF NOT EXISTS Abilities
(
	abilityid INTEGER AUTO_INCREMENT,
	aname CHAR(20) UNIQUE NOT NULL,
	effect VARCHAR(3000) NOT NULL,
	PRIMARY KEY (abilityid)
);


CREATE TABLE IF NOT EXISTS appears_in
(
	pokemonid INTEGER,
	episodeid INTEGER,
	PRIMARY KEY (pokemonid, episodeid),
	FOREIGN KEY (pokemonid) REFERENCES Pokemon(pokemonid),
	FOREIGN KEY (episodeid) REFERENCES Episodes(episodeid)
);


CREATE TABLE IF NOT EXISTS learns
(
	pokemonid INTEGER,
	moveid INTEGER,
	atlevel INTEGER,
	PRIMARY KEY (pokemonid, moveid),
	FOREIGN KEY (pokemonid) REFERENCES Pokemon(pokemonid),
	FOREIGN KEY (moveid) REFERENCES Moves(moveid)
);


CREATE TABLE IF NOT EXISTS possesses
(	
	pokemonid INTEGER,
	abilityid INTEGER,
	PRIMARY KEY (pokemonid, abilityid),
	FOREIGN KEY (pokemonid) REFERENCES Pokemon(pokemonid),
	FOREIGN KEY (abilityid) REFERENCES Abilities(abilityid)
);


CREATE TABLE IF NOT EXISTS has
(
	pokemonid INTEGER,
	typeid INTEGER,
	PRIMARY KEY(pokemonid, typeid),
	FOREIGN KEY (pokemonid) REFERENCES Pokemon(pokemonid),
	FOREIGN KEY (typeid) REFERENCES Types(typeid)
);


CREATE TABLE IF NOT EXISTS is_of
(
	moveid INTEGER,
	typeid INTEGER,
	effectiveness REAL,
	PRIMARY KEY(moveid, typeid),
	FOREIGN KEY (moveid) REFERENCES Moves(moveid),
	FOREIGN KEY (typeid) REFERENCES Types(typeid)
);


CREATE TABLE IF NOT EXISTS attacking
(
	typeid1 INTEGER,
	typeid2 INTEGER,
	PRIMARY KEY(typeid1, typeid2),
	FOREIGN KEY (typeid1) REFERENCES Types(typeid),
	FOREIGN KEY (typeid2) REFERENCES Types(typeid)
);


CREATE TABLE IF NOT EXISTS defending
(
	typeid1 INTEGER,
	typeid2 INTEGER,
	effectiveness REAL,
	PRIMARY KEY(typeid1, typeid2),
	FOREIGN KEY (typeid1) REFERENCES Types(typeid),
	FOREIGN KEY (typeid2) REFERENCES Types(typeid)
);
