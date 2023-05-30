### User Server docker 세팅
* [도커 명령어 모음](https://song8420.tistory.com/394)
* image 생성
```shell
docker build -t dkaskgkdua/catalog-service:1.0 .
```

* docker hub push
```shell
docker push dkaskgkdua/catalog-service:1.0
```

* container 실행
```shell
# network 정보는 미리 만들어놓은 network 명을 적는 것
#rabbitmq host는 ip가 아닌 컨테이너명으로 한다.
docker run -d --network com-network -e "spring.cloud.config.uri=http://config-service:8888" -e "spring.rabbitmq.host=rabbitmq" -e "spring.zipkin.base-url=http://zipkin:9411" -e "eureka.client.service-url.defaultZone=http://discovery-service:8761/eureka/" -e "spring.cloud.config.name=config-service-docker" -e "logging.file=/api-logs/catalogs-ws.log" --name catalog-service dkaskgkdua/catalog-service:1.0
```

* network에 할당되어 있는 것인가 확인
```shell
docker network inspect com-network
```