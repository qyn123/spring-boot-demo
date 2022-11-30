FROM openjdk:8


#将打成的jar包拷贝成名为 app.jar的jar包
COPY *.jar /app.jar

#默认就是8080
CMD ["--server.port=8089"]

#暴露端口
EXPOSE 8089

#容器运行的命令
ENTRYPOINT ["java","-jar","/app.jar"]