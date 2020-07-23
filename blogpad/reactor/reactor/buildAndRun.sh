#!/bin/sh
mvn clean package && docker build -t com.alushkja/reactor .
docker rm -f reactor || true && docker run -d -p 8080:8080 -p 4848:4848 --name reactor com.alushkja/reactor 
