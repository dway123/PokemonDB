/*
Darwin Huang
November 2016
Databases
*/

# use to teardown all tables in PokemonDB

USE PokemonDB;

#first drop relation tables

DROP TABLE IF EXISTS appears_in;

DROP TABLE IF EXISTS learns;

DROP TABLE IF EXISTS possesses;

DROP TABLE IF EXISTS has;

DROP TABLE IF EXISTS is_of;

DROP TABLE IF EXISTS attacking;

DROP TABLE IF EXISTS defending;

#then drop entity tables

DROP TABLE IF EXISTS Pokemon;

DROP TABLE IF EXISTS Episodes;

DROP TABLE IF EXISTS Types;

DROP TABLE IF EXISTS Moves;

DROP TABLE IF EXISTS Abilities;