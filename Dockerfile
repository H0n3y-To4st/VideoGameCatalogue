FROM payara/server-full:6.2024.7-jdk21

COPY target/*.war $DEPLOY_DIR/videogame-catalogue.war

#COPY postgresql*.jar /tmp
#RUN echo 'add-library /tmp/postgresql-42.2.8.jar' > $POSTBOOT_COMMANDS

