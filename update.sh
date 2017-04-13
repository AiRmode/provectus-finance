#!/usr/bin/env bash
server_port="8082"

git pull
git submodule update --recursive --remote
#git submodule update --init
#git submodule foreach git reset --hard

cp -r -f provectus-finance-ui/tax-management-ui/provectus-finance-ui/web/ tax-management-spring-boot/src/main/resources

mvn clean test package install -DskipTests=true

for i in `ps aux | grep $server_port | grep -v grep | awk {'print $2'}`; do kill -9 $i; done

nohup java -jar tax-management-spring-boot/target/tax-management-spring-boot-1.0-SNAPSHOT.jar --server.port=$server_port &