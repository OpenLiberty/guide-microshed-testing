#!/bin/bash
set -euxo pipefail

##############################################################################
##
##  GH actions CI test script
##
##############################################################################

mvn -q clean verify
mvn -Dhttp.keepAlive=false 
    -Dmaven.wagon.http.pool=false 
    -Dmaven.wagon.httpconnectionManager.ttlSeconds=120 
    -q clean package liberty:create liberty:install-feature liberty:deploy
mvn liberty:start
curl http://localhost:9080/guide-microshed-testing/people
mvn liberty:stop
