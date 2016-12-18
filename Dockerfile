FROM ubuntu:latest

#pretty much works, but this approach is ugly. Doesn't like it. to start tomcat you have to connect to the container and
#run 'service tomcat7 start' it will say that failed, but don't believe it.
#to make this work rename startup.sh to startup.sh

RUN apt-get update
RUN apt-get -y install software-properties-common
RUN add-apt-repository ppa:webupd8team/java
RUN apt-get -y update
# Accept the license
RUN echo "oracle-java7-installer shared/accepted-oracle-license-v1-1 boolean true" | debconf-set-selections

RUN apt-get -y install oracle-java8-installer

# Here comes the tomcat installation
RUN apt-get -y install tomcat7
RUN echo "JAVA_HOME=/usr/lib/jvm/java-8-oracle" >> /etc/default/tomcat7
RUN mkdir /usr/share/tomcat7/logs \
    &&  mkdir /usr/share/tomcat7/temp \
    &&  mkdir /usr/share/tomcat7/conf
#
ADD ./server.xml /usr/share/tomcat7/conf/server.xml
# Expose the default tomcat port
EXPOSE 8080



RUN apt-get install maven -y
RUN apt-get install git -y

ADD table.sql /opt/table.sql

RUN DEBIAN_FRONTEND=noninteractive apt-get -y install mysql-server \
    &&  service mysql start \
    &&  mysqladmin -u root password root \
    &&  mysql -u root -proot < /opt/table.sql
RUN mysqld &
ADD pom.xml /app/
ADD src/ /app/src
#ADD table.sql /opt/table.sql
#RUN mysql -u root -proot < /opt/table.sql

WORKDIR /app/
RUN service mysql start \
    &&  mvn package

RUN rm -rd /var/lib/tomcat7/webapps/ROOT
RUN mv /app/target/giftIT-1.1.0.war /var/lib/tomcat7/webapps/ROOT.war

#ADD startup.sh /opt/startup.sh
#RUN chmod 777 /opt/startup.sh
#CMD ["sh", "/opt/startup.sh"]
ENV CATALINA_HOME /usr/share/tomcat7
ENV PATH $CATALINA_HOME/bin:$PATH

EXPOSE 3306

#CMD ["catalina.sh", "run"]
#/usr/share/tomcat7/logs/catalina.out
ENTRYPOINT ["sh"]