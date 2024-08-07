FROM payara/server-full:6.2024.7-jdk21
COPY target/videogame-catalogue-3.9.8.war $DEPLOY_DIR
