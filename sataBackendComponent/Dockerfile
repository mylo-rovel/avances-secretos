# Pull base image.
FROM openjdk:17-alpine

RUN mkdir -p /data/app

WORKDIR /data/app

COPY . /data/app

# Define volume: your local app code directory can be mounted here
VOLUME ["/data/app"]

# Allow the host to use gradle cache, otherwise gradle will always download plugins & artifacts on every build
VOLUME ["/root/.gradle/caches/"]

RUN wget https://services.gradle.org/distributions/gradle-7.5.1-bin.zip #-o gradle-7.5.1-bin.zip
RUN unzip gradle-7.5.1-bin.zip
RUN rm gradle-7.5.1-bin.zip

RUN /data/app/gradle-7.5.1/bin/gradle clean
#RUN /data/app/gradle-7.5.1/bin/gradle build --status

# Define default command. Command executed at launch
CMD ["sh"]
#CMD ["jshell"]
#CMD ["/data/app/gradle-7.5.1/bin/gradle", "bootRun"]
