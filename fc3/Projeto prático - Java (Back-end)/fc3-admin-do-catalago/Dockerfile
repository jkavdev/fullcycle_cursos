FROM eclipse-temurin:17.0.6_10-jre-alpine

# WORKDIR /home/runner/work/fullcycle_cursos/fullcycle_cursos/fc3/Projeto prático - Java (Back-end)/fc3-admin-do-catalago

# ARG src="fc3/Projeto prático - Java (Back-end)/fc3-admin-do-catalago/build/libs/*.jar"
# ARG src="/home/runner/work/fullcycle_cursos/fullcycle_cursos/fc3/Projeto prático - Java (Back-end)/fc3-admin-do-catalago/build/libs/*.jar"

# copiando o jar gerado para a imagem
RUN echo "SSSSSSSSSSSSSS"
RUN ls
COPY build/libs/*.jar /opt/app/application.jar
# COPY /home/runner/work/fullcycle_cursos/fullcycle_cursos/fc3/Projeto prático - Java (Back-end)/fc3-admin-do-catalago/build/libs/*.jar /opt/app/application.jar
# COPY ${src} /opt/app/application.jar

# criando grupo e um usuario, para que nao seja executado criado um container com o usuario root
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# rodando jar da aplicacao
CMD java -jar /opt/app/application.jar