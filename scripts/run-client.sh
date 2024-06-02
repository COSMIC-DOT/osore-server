# 현재 docker process 종료 및 삭제
docker rm -f $(docker ps -qa)

# 기존 docker image 삭제
docker rmi minseokoh/osore:latest

# docker image 빌드 및 실행
docker compose -f ./docker-compose-client.yml -p osore-server up -d