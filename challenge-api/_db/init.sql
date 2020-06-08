DROP DATABASE IF EXISTS challenge;
CREATE DATABASE challenge;
DROP USER IF EXISTS challenge;.
CREATE USER challenge WITH PASSWORD 'challenge';
GRANT ALL PRIVILEGES ON DATABASE "challenge" to challenge;
