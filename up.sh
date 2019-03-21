#!/usr/bin/env bash

docker-compose up --build --detach
docker exec -it svn-jenkins-poc_svn-server_1 svnadmin create example-repo
docker cp docker-svn-server/authz svn-jenkins-poc_svn-server_1:/var/opt/svn/example-repo/conf/authz
docker cp docker-svn-server/passwd svn-jenkins-poc_svn-server_1:/var/opt/svn/example-repo/conf/passwd
docker cp docker-svn-server/svnserve.conf svn-jenkins-poc_svn-server_1:/var/opt/svn/example-repo/conf/svnserve.conf

svn info svn://localhost:3690/example-repo

rm -rf checkedout
mkdir checkedout
cd checkedout
svn checkout svn://localhost:3690/example-repo
cp -r ../example-repo/ ./example-repo/
cd example-repo
svn add module*
svn commit -m "Initial commit"
cd ../..