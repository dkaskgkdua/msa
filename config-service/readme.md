### docker 세팅
* [도커 명령어 모음](https://song8420.tistory.com/394)
* image 생성
```shell
docker build -t dkaskgkdua/config-service:1.0
```

* container 실행
```shell
# network 정보는 미리 만들어놓은 network 명을 적는 것
#rabbitmq host는 ip가 아닌 컨테이너명으로 한다.
docker run -d -p 8888:8888 --network com-network -e "spring.rabbitmq.host=rabbitmq" -e "spring.profiles.active=default" --name config-service dkaskgkdua/config-service:1.0
```

* network에 할당되어 있는 것인가 확인
```shell
docker network inspect com-network
```
