FROM nathanhowell/parquet-tools:latest
FROM confluentinc/cp-kafka-connect:7.2.2

# AWS s3 connector
COPY kafka-connect-s3/target/kafka-connect-s3-10.3.0-SNAPSHOT-development/share/java/kafka-connect-s3 /usr/share/confluent-hub-components/kafka-connect-s3

# Parquet tooling
COPY --from=0 /parquet-tools.jar /tmp
#ADD --chown=appuser https://repo1.maven.org/maven2/org/apache/parquet/parquet-tools/1.11.2/parquet-tools-1.11.2.jar /home/appuser/

USER root
WORKDIR /tmp
RUN yum install -y unzip
RUN curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
RUN unzip awscliv2.zip
RUN ./aws/install 
RUN rm -rf awscliv2.zip aws

USER appuser
