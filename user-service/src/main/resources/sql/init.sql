
# shell mysql -u root -p
# 비밀번호 입력

create user 'msa'@'%' identified by '1234';

grant all privileges on *.* to 'msa'@'%';

create database msa;

show databases;

use msa;