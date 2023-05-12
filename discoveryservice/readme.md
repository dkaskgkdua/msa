```
# maven
mvn clean compile package
```

### docker 세팅
* [도커 명령어 모음](https://song8420.tistory.com/394)
* image 생성
```shell
docker build -t dkaskgkdua/discovery-service:1.0 .
```

* docker hub push
```shell
docker push dkaskgkdua/discovery-service:1.0
```

* container 실행
```shell
# network 정보는 미리 만들어놓은 network 명을 적는 것
# config ip는 같은 네트워크로 구성된 config의 서비스명을 적어주면 된다.
docker run -d -p 8761:8761 --network com-network -e "spring.cloud.config.uri=http://config-service:8888" --name discovery-service dkaskgkdua/discovery-service:1.0
```

* network에 할당되어 있는 것인가 확인
```shell
docker network inspect com-network
```
