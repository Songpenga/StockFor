# EC2(저사양) 메모리 부족으로 컨테이너 내부 gradle 빌드가 불가능해
# 로컬에서 미리 빌드한 JAR(build/libs/*.jar)을 그대로 복사하는 방식으로 전환.
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY build/libs/core-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
