#!/usr/bin/env bash

docker-compose up --build --detach
docker exec -it svn-jenkins-poc_svn-server_1 svnadmin create example-repo

svn info svn://localhost:3690/example-repo

rm -rf checkedout
mkdir checkedout
cd checkedout
svn checkout svn://localhost:3690/example-repo
cp -r ../example-repo/ ./example-repo/
cd example-repo
svn commit -m "Initial commit"
cd ../..