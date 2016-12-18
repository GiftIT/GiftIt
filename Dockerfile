FROM tomcat:8


#doesn't work. cannot install jdk-8 to compile project
#also you need to run mysql container. for that
#docker run --name mysql -e MYSQL_ROOT_PASSWORD=root -d mysql:5.7
#and I haven't had a chance to add command for creating the table in mysql

RUN apt-get update && apt-get install -y software-properties-common
RUN add-apt-repository ppa:openjdk-r/ppa

RUN apt-get update && apt-get install -y openjdk-8-jdk
#RUN apt-get install openjdk-8-jdk -y
RUN apt-get install maven -y
RUN apt-get install git -y

WORKDIR /app
ADD pom.xml /app/
ADD src/ /app/src

RUN ["mvn", "package"]
CMD ["catalina.sh", "run"]