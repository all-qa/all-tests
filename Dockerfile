FROM openjdk

ADD  target/testng-docker.jar testng-docker.jar
ADD  target/testng-docker-tests.jar testng-docker-tests.jar
ADD  target/libs libs

ADD target/test-classes/smoke.xml smoke.xml

ARG aspectjVersion
ENV aspectjVersion ${aspectjVersion}

ENTRYPOINT java -cp testng-docker.jar:testng-docker-tests.jar:libs/* \
-javaagent:"/libs/aspectjweaver-$aspectjVersion.jar" \
-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=8000 \
-Dallure.results.directory=/allure-results \
org.testng.TestNG "smoke.xml"
