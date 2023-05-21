### Config Server docker 세팅
* [도커 명령어 모음](https://song8420.tistory.com/394)
* image 생성
```shell
docker build -t dkaskgkdua/config-service:1.0 .
```

* docker hub push
```shell
docker push dkaskgkdua/config-service:1.0
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

---

### mysql db docker 세팅
* 도커파일
```dockerfile
FROM mysql
ENV MYSQL_ROOT_PASSWORD 1234
ENV MYSQL_DATABASE msa
# ./mysql에서 /var/lib/mysql로 카피
COPY ./mysql /var/lib/mysql
EXPOSE 3306
ENTRYPOINT ["mysqld", "--initialize", "--user=root"]
```

* image 생성
```shell
docker build -t dkaskgkdua/my_mysql:1.1 -f ./Dockerfile-Mysql .
```

* docker hub push
```shell
docker push dkaskgkdua/my_mysql:1.1
```

* container 실행
```shell
docker run -d -p 3306:3306 --network com-network --name mysqldb dkaskgkdua/my_mysql:1.1
```

* container 접속
```shell
docker exec mysqldb /bin/bash
```

* 별첨
```shell
docker run --name mysqldb --hostname=d00abfe314a1 --network com-network --mac-address=02:42:ac:11:00:02 --env=MYSQL_SHELL_VERSION=8.0.30-1.el8 --env=MYSQL_ROOT_PASSWORD=1234 --env=PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin --env=GOSU_VERSION=1.14 --env=MYSQL_MAJOR=8.0 --env=MYSQL_VERSION=8.0.30-1.el8 --volume=/var/lib/mysql -p 3306:3306 --restart=no --runtime=runc -d mysql
```


### zipkin
```shell
docker run -d -p 9411:9411 --network com-network --name zipkin openzipkin/zipkin
```

### prometheus
```shell
# 상대경로 세팅 불가능, 절대경로로 세팅 필요
docker run -d -p 9090:9090 --network com-network --name prometheus -v C:\Users\dkask\IdeaProjects\msa\config-service/prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus
```

### grafana
```shell
docker run -d -p 3000:3000 --network com-network --name grafana grafana/grafana
```