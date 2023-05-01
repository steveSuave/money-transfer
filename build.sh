#!/usr/bin/env bash

docker-compose -f docker/docker-compose.yml up -d

while ! mysql -uroot -p'kal1mer4!' -h localhost -P 3306 --protocol=tcp -e "show databases" &>/dev/null; do
    sleep 1
done

mvn clean package

mvn exec:java \
    -Dexec.classpathScope=test \
    -Dexec.mainClass=io.cucumber.core.cli.Main \
    -Dexec.args="src/test/resources/Feature --glue com.agileactors.moneytransfer.cucumber"

docker-compose -f docker/docker-compose.yml down --remove-orphans
