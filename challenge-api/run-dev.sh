#!/usr/bin/env bash

./mvnw clean package &&
java -jar ./backend/rest/target/appname-rest*.jar