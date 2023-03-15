FROM eclipse-temurin:17.0.6_10-jre-alpine

# copiando o jar gerado para a imagem
COPY "fc3/Projeto prático - Java (Back-end)/fc3-admin-do-catalago/build/libs/*.jar" /opt/app/application.jar

# criando grupo e um usuario, para que nao seja executado criado um container com o usuario root
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# rodando jar da aplicacao
CMD java -jar /opt/app/application.jar