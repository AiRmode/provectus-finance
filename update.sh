#!/usr/bin/env bash
git pull master

mvn clean test install package

cd tax-management-spring-boot/target

nohup java -jar tax-management-spring-boot-1.0-SNAPSHOT.jar --server.port=8082 &