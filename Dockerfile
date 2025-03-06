FROM payara/server-full:6.2024.11-jdk21

COPY postgresql*.jar /tmp
RUN echo 'add-library /tmp/postgresql-42.7.5.jar' > $POSTBOOT_COMMANDS

RUN echo 'create-jdbc-connection-pool --datasourceclassname org.postgresql.ds.PGSimpleDataSource --driverclassname org.postgresql.Driver --restype javax.sql.DataSource --property user=postgres:password=d3f4ult:URL="jdbc\\:postgresql\\://videogamecatalogue-db/videogamecatalogue" videogamecataloguePool' >> $POSTBOOT_COMMANDS

RUN echo 'ping-connection-pool videogamecataloguePool' >> $POSTBOOT_COMMANDS

RUN echo 'create-jdbc-resource --connectionpoolid videogamecataloguePool jdbc/videogamecatalogue' >> $POSTBOOT_COMMANDS

RUN echo 'create-auth-realm --classname=com.sun.enterprise.security.auth.realm.jdbc.JDBCRealm --property jaas-context=jdbcRealm:datasource-jndi=jdbc/videogamecatalogue:user-table=user_account:user-name-column=username:password-column=password:group-table=user_roles:group-table-user-name-column=username:group-name-column=groupname:digest-algorithm=SHA-256:encoding=Base64:charset=UTF-8: jdbcRealm' >> $POSTBOOT_COMMANDS

COPY target/*.war $DEPLOY_DIR/videogame-catalogue.war