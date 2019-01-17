start:
	docker-compose up -d

start-watch:
	docker-compose up
	
end:
	docker-compose down

restart:
	make end
	make start

watch:
	docker-compose logs

bash:
	docker exec -it server bash