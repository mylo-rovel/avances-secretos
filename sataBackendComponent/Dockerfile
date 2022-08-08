# Pull base image.
FROM openjdk:17-alpine
#FROM ubuntu:latest

# Define working directory.
RUN mkdir -p /data/app
WORKDIR /data/app

COPY . /data/app

# Define volume: your local app code directory can be mounted here
VOLUME ["/data/app"]

# Allow the host to use gradle cache, otherwise gradle will always download plugins & artifacts on every build
#VOLUME ["/root/.gradle/caches/"]

RUN cd /usr/local
RUN wget https://services.gradle.org/distributions/gradle-7.5.1-bin.zip #-o gradle-7.5.1-bin.zip
RUN unzip gradle-7.5.1-bin.zip
RUN rm gradle-7.5.1-bin.zip

ENV GRADLE_HOME=/usr/local/gradle-7.5.1
ENV JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
ENV PATH=$PATH:$GRADLE_HOME/bin

# Define default command.
CMD ["sh"]