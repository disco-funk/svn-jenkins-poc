# svn-jenkins-poc

### Run docker compose locally

```
./up.sh
```
### Manual configuration steps after running:

* Need to create pipeline in Jenkins manually

### Configuration details:

#### Jenkins:
Username ```admin```

Password ```admin```

URL: http://localhost:8080

#### SVN Server:
Username ```admin```

Password ```admin```

URL (from within container): svn://svn-server:3690/example-repo

URL (from host laptop): svn://localhost:3690/example-repo

### Common commands

Log in to shell inside SVN Server container:
```
docker exec -it svn-jenkins-poc_svn-server_1 /bin/sh
```

Log in to shell inside Jenkins container:
```
docker exec -it svn-jenkins-poc_jenkins_1 /bin/sh
```

Tail Jenkins logs:
```
docker logs -f svn-jenkins-poc_jenkins_1
```

svn info svn://svn-server:3690/example-repo