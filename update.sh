#!/usr/bin/env bash
server_port="8082"

git pull
git submodule update --recursive --remote

cp -r -f provectus-finance-ui/tax-management-ui/provectus-finance-ui/web/ tax-management-spring-boot/src/main/resources

mvn clean test install package

cd tax-management-spring-boot/target

pid=$(ps aux | grep $server_port | awk 'FNR == 1{print $2}')
echo "killing tomcat PID: $pid"
kill -9 $pid

pid=$(ps aux | grep $server_port | awk 'FNR == 2{print $2}')
echo "killing tomcat PID: $pid"
kill -9 $pid

nohup java -jar tax-management-spring-boot/target/tax-management-spring-boot-1.0-SNAPSHOT.jar --server.port=$server_port &