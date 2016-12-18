# PokemonDB
A database of insights into the amazing world of Pokemon!

JAVA IDE/DEPENDENCIES/APACHE SETUP
1. INSTALL ECLIPSE (PLEASE INSTALL ECLIPSE FOR JAVA EE TO SKIP STEP 3)
http://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/lunasr2

2. Install Apache Tomcat (use the link, everything can apply except the php part, but for here we just care about Apache)
https://www.digitalocean.com/community/tutorials/how-to-install-linux-apache-mysql-php-lamp-stack-on-ubuntu-16-04

3. Within Eclipse, add "Java EE", "JST Server Adapters" and "JST Server Adapters Extensions"
These are dependencies for java logic x Apache server power
Make sure it's good for the target environment (Ubuntu defaults to Eclipse Juno (3.8.1)
http://stackoverflow.com/questions/2000078/apache-tomcat-not-showing-in-eclipse-server-runtime-environments
NOTE: VARIOUS ECLIPSE BUGS/ETC ARE PREVENTING ME FROM GETTING JST SERVER ADAPTERS... GOOD LUCK BUT I'D RECOMMEND INSTALLING ECLIPSE JAVA EE

4. Include onto the build-path all missing jars (too lazy to document since we have this done everywhere already)
javax.servlet
org.json.simple
mysql-connector-java

5. FOLLOW THIS
http://www.journaldev.com/1854/java-web-application-tutorial-for-beginners

MySQL SETUP: 
1. INSTALL MYSQL:
Install MySQL by following MySQL install directions on the following link.
https://www.linode.com/docs/databases/mysql/install-mysql-on-ubuntu-14-04

2. CREATE PokemonDB DATABASE: 
Next we want to create the database that will hold all the PokemonDB tables.
Run these commands in terminal: 
mysql -u root -p
CREATE DATABASE PokemonDB;
SHOW DATABASES;
quit;

3. CREATE PokemonDB TABLES: 
We now want to create the tables which will hold Pokemon information.
Run this command in terminal (please replace PokemonDBSetup.sql with the full path):
mysql -u root -p PokemonDB < setup.sql

4. ENTER INFORMATION FOR PokemonDB via web-scraping or otherwise: 
//TODO: thanks sebastien
