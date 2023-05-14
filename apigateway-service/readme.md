### docker 세팅
* [도커 명령어 모음](https://song8420.tistory.com/394)
* image 생성
```shell
docker build -t dkaskgkdua/apigateway-service:1.0 .
```

* docker hub push
```shell
docker push dkaskgkdua/apigateway-service:1.0
```

* container 삭제
```shell
# rm 뒤에 컨테이너 id
docker rm 0d9e43a3614cf2cd631ef42511eba597bb9bd5077a52e3500264866b2a506318
```

* container 실행
```shell
# network 정보는 미리 만들어놓은 network 명을 적는 것
#rabbitmq host는 ip가 아닌 컨테이너명으로 한다.
docker run -d -p 8000:8000 --network com-network -e "spring.rabbitmq.host=rabbitmq" -e "spring.cloud.config.uri=http://config-service:8888" -e "spring.cloud.config.name=config-service-docker" -e "eureka.client.serviceUrl.defaultZone=http://discovery-service:8761/eureka/" --name apigateway-service dkaskgkdua/apigateway-service:1.0
```

* network에 할당되어 있는 것인가 확인
```shell
docker network inspect com-network
```

* container log 확인
```shell
docker logs apigateway-service
```