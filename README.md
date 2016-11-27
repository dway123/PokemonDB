# PokemonDB
A database of insights into the amazing world of Pokemon!

1. MySQL SETUP
Install MySQL by following MySQL install directions on the following link.
https://www.linode.com/docs/databases/mysql/install-mysql-on-ubuntu-14-04

2. CREATE PokemonDB DATABASE
Next we want to create the database that will hold all the PokemonDB tables.
Run these commands in terminal: 
mysql -u root -p
CREATE DATABASE PokemonDB;
SHOW DATABASES;
quit;

3. CREATE PokemonDB TABLES
We now want to create the tables which will hold Pokemon information.
Run this command in terminal (please replace PokemonDBSetup.sql with the full path):
mysql -u root -p PokemonDB < setup.sql

4. ENTER INFORMATION FOR PokemonDB via web-scraping or otherwise 
//TODO: this lol
