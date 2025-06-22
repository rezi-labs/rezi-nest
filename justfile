import? 'tests/hurl.just'
image_name := "ghcr.io/lunchtimecode/nest"


tools:
    brew install ktlint

run:
    ./gradlew run

verify: lint build-and-tests
    ./gradlew check

lint:
    ktlint

fmt:
    ktlint --format

down:
    docker compose down

up d="": down build
    docker compose up {{d}}


build version="latest":
    ./gradlew jibDockerBuild --image {{ image_name }}:{{ version }}

push:
    just _d_push $(just get_version)

_d_push version:
    docker build -t {{image_name}}:{{version}} .
    docker push {{image_name}}:{{version}}

get_version:
    git rev-parse HEAD