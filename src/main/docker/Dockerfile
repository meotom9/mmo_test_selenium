#FROM registry.access.redhat.com/ubi8/openjdk-21:1.19
#FROM docker.io/ubuntu/jre:17-22.04_edge
FROM ubuntu:22.04 AS builder

USER root

RUN apt-get update && apt-get install -y openjdk-21-jdk
# Chrome dependency Instalation
#RUN apt-get install -y \
#    fonts-liberation \
#    libasound2 \
#    libatk-bridge2.0-0 \
#    libatk1.0-0 \
#    libatspi2.0-0 \
#    libcups2 \
#    libdbus-1-3 \
#    libdrm2 \
#    libgbm1 \
#    libgtk-3-0 \
##    libgtk-4-1 \
#    libnspr4 \
#    libnss3 \
#    libwayland-client0 \
#    libxcomposite1 \
#    libxdamage1 \
#    libxfixes3 \
#    libxkbcommon0 \
#    libxrandr2 \
#    xdg-utils \
#    libu2f-udev \
#    libvulkan1 \
#    wget
 # Chrome instalation
#RUN curl -LO  https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
#RUN wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb


ENV LANGUAGE='en_US:en'


# We make four distinct layers so if there are application changes the library layers can be re-used
COPY --chown=185 target/quarkus-app/lib/ /deployments/lib/
COPY --chown=185 target/quarkus-app/*.jar /deployments/
COPY --chown=185 target/quarkus-app/app/ /deployments/app/
COPY --chown=185 target/quarkus-app/quarkus/ /deployments/quarkus/
COPY --chown=185 target/classes/chromedriver-linux64/ /deployments/chromedriver-linux64/

EXPOSE 8080
USER 185
ENV JAVA_OPTS_APPEND="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"
ENV JAVA_APP_JAR="/deployments/quarkus-run.jar"

CMD ["java -jar /deployments/quarkus-run.jar"]
#ENTRYPOINT [ "/opt/jboss/container/java/run/run-java.sh" ]