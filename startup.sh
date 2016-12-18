#!/bin/bash


mvn package
rm -rd /var/lib/tomcat7/webapps/ROOT/
cp /app/target/giftIT-1.1.0.war /var/lib/tomcat7/webapps/ROOT.war
