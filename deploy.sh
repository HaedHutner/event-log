#!/usr/bin/env bash

sh build.sh;

docker stop $(docker ps -a -q -f "name=event-log");

docker rm $(docker ps -a -q -f "name=event-log");

docker run -d --name event-log --expose 8080 event-log;