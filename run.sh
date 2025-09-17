#!/bin/bash
cd /Users/sharabf/Downloads/project-ledger

# Set JAVA_HOME if not set
if [ -z "$JAVA_HOME" ]; then
    export JAVA_HOME=$(/usr/libexec/java_home)
fi

# Build classpath from Maven repository
CLASSPATH="target/classes"
for jar in $(find ~/.m2/repository -name "*.jar" | grep -E "(spring-boot|spring-web|spring-context|spring-core|spring-beans|spring-aop|spring-expression|spring-data|spring-security|hibernate|h2|postgresql|logback|slf4j|jackson|tomcat)" | head -100); do
    CLASSPATH="$CLASSPATH:$jar"
done

# Run the application
java -Dspring.profiles.active=default \
     -Dlogging.level.root=INFO \
     -Dspring.jpa.hibernate.ddl-auto=create-drop \
     -Dspring.datasource.url=jdbc:h2:mem:testdb \
     -Dspring.datasource.username=sa \
     -Dspring.datasource.password=password \
     -cp "$CLASSPATH" \
     com.project_ledger.project_ledger.ProjectLedgerApplication