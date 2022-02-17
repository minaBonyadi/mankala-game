# mina bonyadi

## mankala game board application

## Description
This project is a kind of Mankala gaming board for real to robot player including an Api.

## files
```
clone https://gitlab.com/bolcom/mina-bonyad/-/tree/master
git remote add origin https://gitlab.com/bolcom/mina-bonyad.git
```

## tools

- [ ] Java 11
- [ ] Maven
- [ ] Spring Boot
- [ ] Redis
- [ ] Git
- [ ] Swagger-ui  
- [ ] Openapi
- [ ] Jupiter  
- [ ] Mockito
- [ ] Docker
- [ ] Docker Compose

## Test and Deploy

- [ ] [docker-compose up --build](https://gitlab.com/bolcom/mina-bonyad/-/blob/master/docker-compose.yml)

***

## Features

- Game is ready just for (Real to Bot) player strategy, the board designed for 1 to 6 pits with 6 maximum stones right now 
- Including Api for exposing this application services to the client
- With swagger-ui which helps to call Api's services (swagger-ui add: [http://localhost:8081/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config])
- Strategy design pattern has implemented for different types of playing like (REAL TO REAL, BOT TO BOT, REAL TO BOT)
- Rules and regulations are implementing abstractly to use generally
- Providing unit-test for test (Real to Bot) player rules from both side real move or bot move
- plus, including integration-test for test (Real to Bot) player Api


## Game Model

    All pits are filled by 6 stones in the begining of a game and both bot and real storage filled by zero.

    real pits ids->     bot Storage      1(6), 2(6), 3(6), 4(6), 5(6), 6(6)     real storage
    bot pits ids->               0       1(6), 2(6), 3(6), 4(6), 5(6), 6(6)       0

## Installation

- After cloning this project just run this command( docker-compose up --build ) in your intellij terminal to install and up this application 

## Usage

 In this project you can play a Mankala game board with a robot
 But this application does not have UI. You should up docker compose file and then 
 ,you should call this application services from the swagger-ui
 address (http://localhost:8081/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config)
 After taht, call createGame() service like this (/games/create), from swagger ui for creating a new game and
 then call makeTurn() service that is including request body which is our board data and 
 a pit id as a path variable in our service call for example (/games/make-turn/1).

## Using Curl To Check Api

-Game creation service [http://localhost:8081/games/create]

```
curl -X 'POST' \
'http://localhost:8081/games/create' \
-H 'accept: */*' \
-d ''
```

-Game make turn service [http://localhost:8081/games/make-turn/2],
(Tip: you should get the "id" from creation service response body)
```
curl -X 'POST' \
  'http://localhost:8081/games/make-turn/2' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "",
  "realPits": {
    "1": 6,
    "2": 6,
    "3": 6,
    "4": 6,
    "5": 6,
    "6": 6
  },
  "botPits": {
    "1": 6,
    "2": 6,
    "3": 6,
    "4": 6,
    "5": 6,
    "6": 6
  },
  "botStorage": 0,
  "realStorage": 0
}'
```

## Roadmap

- add winning message
- add more log
- add spring security
- add more design pattern

## Authors and acknowledgment

 This is Mina. I am a java back-end developer which have more than five years experience in this career,
 and I have a bachelor degree in software engineering.
