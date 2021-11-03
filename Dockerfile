FROM debian:stable-slim AS builder


COPY distr/graal.gz graal.gz
RUN mkdir /opt/graal
RUN tar -xvf graal.gz -C /opt/graal --strip-components=1
RUN apt-get update && apt-get install wget -y \
    && mkdir /opt/maven \
    && wget https://archive.apache.org/dist/maven/maven-3/3.6.2/binaries/apache-maven-3.6.2-bin.tar.gz -O - | tar xz -C /opt/maven --strip-components=1

RUN ls -l /opt/graal/bin

#ENV JAVA_HOME=/opt/graal
#ENV M2_HOME=/opt/maven
#ENV MAVEN_HOME=/opt/maven
#ENV PATH=${M2_HOME}/bin:${PATH}
#ENV PATH=${JAVA_HOME}/bin:${PATH}
#
RUN /opt/graal/bin/gu install native-image
#COPY
#RUN ln -s /opt/apache-maven-3.6.2 /opt/maven \
# && ln -s /opt/graalvm-ce-java11-19.3.2 /opt/graalvm

#RUN gu install native-image
#ENV JAVA_HOME=/opt/graalvm
#ENV M2_HOME=/opt/maven
#ENV MAVEN_HOME=/opt/maven
#ENV PATH=${M2_HOME}/bin:${PATH}
#ENV PATH=${JAVA_HOME}/bin:${PATH}