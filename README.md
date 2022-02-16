# mina bonyadi

## mankala game board application

## Description
This project is a kind of Mankala gaming board for real to robot player including an Api.

## files
```
clone https://gitlab.com/bolcom/mina-bonyad/-/tree/master
git remote add origin https://gitlab.com/bolcom/mina-bonyad.git
git branch -M master
git push -uf origin master
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
- [ ] Docker
- [ ] Docker Compose

## Test and Deploy

- [ ] [GitLab CI/CD](https://gitlab.com/bolcom/mina-bonyad/-/pipelines)
- [ ] [docker-compose up --build](https://gitlab.com/bolcom/mina-bonyad/-/blob/master/docker-compose.yml)

***

## Features

- Game is ready just for (Real to Bot) player strategy right now 
- Including Api for exposing this application services to the client
- With swagger-ui which helps to call Api's services (swagger-ui add: [http://localhost:8081/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config])
- Strategy design pattern has implemented for different types of playing like (REAL TO REAL, BOT TO BOT, REAL TO BOT)
- Rules and regulations are implementing abstractly to use generally
- Providing unit-test for test (Real to Bot) player rules from both side real move or bot move
- plus, including integration-test for test (Real to Bot) player Api

## Installation

- After cloning this project just run this command( docker-compose up --build ) in your intellij terminal to install and up this application 

## Usage

 In this project you can play a Mankala game board with a robot
 But this application does not have UI. You should up docker compose file and then 
 ,you should call this application services from this swagger-ui
 address (http://localhost:8081/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config)
 After taht, call createGame() service like this (/games/create), from swagger ui for creating a new game and
 then call makeTurn() service that is including request body which is our board data and 
 a pit id as a path variable in our service call for example (/games/make-turn/1).

## Roadmap

- add spring security
- add more design pattern

## Authors and acknowledgment

 This is Mina. I am a java backend developer which have more than five years experience in this career.
