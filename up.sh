#!/usr/bin/env bash

docker-compose up --build --detach
docker exec -it svn-jenkins-poc_svn-server_1 svnadmin create new-repo

svn info svn://localhost:3690/new-repo