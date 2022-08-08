DOCKER_IMAGE_NAME=alura/bank

docker/build:
	@ docker image build -t $(DOCKER_IMAGE_NAME):$(tag) .

local/up: local/down
	@ docker-compose up -d

local/ls:
	@ docker-compose ps

local/down:
	@ docker-compose down

local/logs:
	@ docker-compose logs -f $(service)
