/*
Darwin Huang
November 2016
Databases
*/

#use to insert some example data into the PokemonDB

USE PokemonDB;

INSERT INTO Pokemon
(pokemonid, pname, hp, attack, defense, specialattack, specialdefense, speed)
VALUES
(25, 'Pikachu', 35, 55, 40, 50, 50, 90);

INSERT INTO Episodes
(ename, episode, season, releasedate)
VALUES
('Pokémon, I Choose You!', 1, 1, '1998-09-08');

INSERT INTO Types
(tname)
VALUES
('Electric');

INSERT INTO Moves
(mname, description, category, power, accuracy)
VALUES
('Thunder Shock', 'Quite a shocking move', 1, 40, 100);

INSERT INTO Abilities
(aname, effect)
VALUES
('Static', 'If a Pokémon with Static is hit by a move making contact, there is a 30% chance the foe will become paralyzed.');

INSERT INTO appears_in
(pokemonid, episodeid)
VALUES
(25, 1);

INSERT INTO learns
(pokemonid, moveid, atlevel)
VALUES
(25, 1, 1);

INSERT INTO possesses
(pokemonid, abilityid)
VALUES
(25, 1);

INSERT INTO has
(pokemonid, typeid)
VALUES
(25, 1);

INSERT INTO is_of
(moveid, typeid)
VALUES
(1, 1);

INSERT INTO Types
(tname)
VALUES
('Water');

INSERT INTO attacking
(typeid1, typeid2)
VALUES
(1,2);

INSERT INTO defending
(typeid1, typeid2)
VALUES
(1,2);