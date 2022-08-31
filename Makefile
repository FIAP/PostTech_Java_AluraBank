DOCKER_IMAGE_NAME=alura/bank
HEROKU_APP_NAME=alura-bank

docker/build:
	@ docker image build -t $(DOCKER_IMAGE_NAME):$(tag) .


docker/retag/heroku:
	@ docker tag $(DOCKER_IMAGE_NAME):$(tag) registry.heroku.com/$(HEROKU_APP_NAME)/web
	@ docker push registry.heroku.com/$(HEROKU_APP_NAME)/web

local/up: local/down
	@ docker-compose up -d

local/ls:
	@ docker-compose ps

local/down:
	@ docker-compose down

local/logs:
	@ docker-compose logs -f $(service)

lint:
	@ ./mvnw verify -DskipTests -Djacoco.skip=true

test:
	@ ./mvnw test


deploy:
	IMAGE_ID=$(shell docker inspect registry.heroku.com/$(HEROKU_APP_NAME)/web -f "{{.Id}}") \
		curl --netrc -X PATCH https://api.heroku.com/apps/$(HEROKU_APP_NAME)/formation \
		-H "Authorization: Bearer $(auth_token)" \
		-H "Content-Type: application/json" \
		-H "Accept: application/vnd.heroku+json; version=3.docker-releases" \
        -d '{ "updates": [{"type": "web","docker_image": "$$IMAGE_ID"}]}'
