#!/usr/bin/env bash

VERSION=1.0

./mvnw clean install;

docker rm event-log:${VERSION};

docker build -t event-log:${VERSION} .;