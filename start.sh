#!/bin/ksh
nohup /usr/java/jdk1.8.0_191/bin/java -jar -Dspring.cloud.bootstrap.location=/app/cce/services/cam-authentication/conf/bootstrap.yml -Dlogging.config=/app/cce/services/cam-authentication/conf/logback.xml cam-authentication-1.0-SNAPSHOT.jar > /dev/null 2>&1
