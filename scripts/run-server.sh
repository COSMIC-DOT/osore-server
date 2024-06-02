# 현재 docker process 종료 및 삭제
docker rm -f $(docker ps -qa)

# gradlew 권한 설정 및 빌드
chmod +x ./gradlew
./gradlew build -x test

# docker image 빌드 및 실행
docker build --no-cache -t osore-server-app .
docker compose -f ./docker-compose-server.yml -p osore-server up -d