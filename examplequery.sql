/*
Darwin Huang
November 2016
Databases
*/

#an example query to show that PokemonDB works

USE PokemonDB;

SELECT  Types.tname
FROM Types, Pokemon, has
WHERE Pokemon.pname = 'Pikachu' 
AND has.pokemonid = Pokemon.pokemonid 
AND Types.typeid = has.typeid;
