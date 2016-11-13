# GiftIt
Instructions for running project
Foreword All steps are provided according to Intellij IDEA.

First of all verify whether you install: JDK 1.8, Tomcat8, maven, mysql; if yes, let's run application

1. Go to main/resources and set up db properties which is established in your data base.

2. Open in your idea project and add to class path 2 extrenal lib:
  
  2.1. You can download lib through next link:http://java-ml.sourceforge.net/; https://sourceforge.net/projects/ajt/  

3. After downloading add it to lib as external libraries
i.e - in Intelije IDEA click on your project and choose setting menu, then choose library section and add two *jar file which you have downloaded through link in point (1).

4. Check your project for compilation error, if everything is OK, configure your tomcat server in IDEA. Click  on edit configuration=>click on green plus in left-right corner=> choose TOMCAT EE SERVER=> remain all configuration by default=>go to Deployment and add artifact giftIT:war exploded.

5. After that run your application and provding of successful deployment you will be redirected to main page of project(index.html)
