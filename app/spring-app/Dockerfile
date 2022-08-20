FROM amd64/eclipse-temurin:17-jre

ARG VERSION

ENV SERVER_PORT=80

COPY build/libs/service-template-spring-app-${VERSION}.jar /opt/app.jar

CMD java \
  -Djava.security.egd=file:/dev/./urandom \
  -Dserver.port=${SERVER_PORT} \
  -Dauth.issuer.url=${AUTH_ISSUER_URL} \
  -jar /opt/app.jar