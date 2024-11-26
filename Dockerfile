FROM mysql:8.0

# 환경 변수를 설정
ENV MYSQL_ROOT_PASSWORD=root123
ENV MYSQL_DATABASE=book_db
ENV MYSQL_USER=user
ENV MYSQL_PASSWORD=user123

VOLUME [/var/lib/mysql]

EXPOSE 3306

CMD ["mysqld"]
