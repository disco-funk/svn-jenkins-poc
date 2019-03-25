# svn-jenkins-poc

### Run docker compose locally

```
./up.sh
```
### Manual configuration steps after running:

* Need to create pipeline in Jenkins manually

New Item -> Multibranch Pipeline -> example-pipeline

Branch Sources -> Add Source -> Subversion
Repository Base: svn://svn-server/example-repo
Credentials -> Add -> example-pipeline -> username/pw: admin admin
Select added credentials in dropdown

Build will fail first time as Groovy methods need to be authorised in Jenkins sandbox:
http://localhost:8080/scriptApproval/
Approve signatures (top box):
```
method jenkins.scm.RunWithSCM getChangeSets
method org.jenkinsci.plugins.workflow.support.steps.build.RunWrapper getRawBuild
```

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