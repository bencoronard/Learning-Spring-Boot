# CRUD API using Java Spring Boot

This application exposes REST API endpoints to perform basic CRUD operations on relational databases. The theme centers around a Secret Intelligence Service (SIS) management system where users with different access levels can assign Missions to Agents.

## Table of Contents

- [Security](#security)
- [Model](#model)
- [API](#api-endpoints)
- [Remarks](#remarks)

## Security
The application is secured with basic login authentication using "username" and "password", and authorization is based on the access level tied to the login credentials.

There are three access levels listed from lowest to highest:
 1. `USER`: { username = "user", password = "1234" }
 2. `ADMIN`: { username = "admin", password = "1234" }
 3. `SUPERUSER`: { username = "superuser", password = "1234" }

Upon first accessing an API endpoint, the user will be presented with a login form. Enter one of the credentials listed above.

## Model
There are two main entities in the application:
- `Agent`{
  - id: unique ID assigned by AgentRepository
  - name: full name of agent
  - alias: secret name used to identify agent
  - missionId: ID of mission assigned to the agent }
- `Mission`{
  - id: unique ID assigned by MissionRepository
  - name: name of mission
  - status: status of mission—UNASSIGNED, ASSIGNED, COMPLETED}

Based on their authority, users can fetch, update, and delete Agent and Mission records. Updating an Agent means assigning a Mission to them, and updating a Mission means changing its status.

## API Endpoints

- GET `/`
  - Description: homepage
  - Access: USER, ADMIN, SUPERUSER

- GET `/h2-console`
  - Description: database console for managing records
  - Access: SUPERUSER

- GET `/api/agents`
  - Description: list all Agent records in database
  - Access: ADMIN, SUPERUSER
  - Response: list of Agent objects

- GET `/api/missions`
  - Description: list all Mission records in database
  - Access: ADMIN, SUPERUSER
  - Response: list of Mission objects

- GET `/api/agent/{alias}`
  - Description: fetch an Agent record based on its alias from database
  - Access: ADMIN, SUPERUSER
  - Input: String alias
  - Response: Agent object

- GET `/api/mission/{name}`
  - Description: fetch a Mission record based on its name from database
  - Access: USER, ADMIN, SUPERUSER
  - Input: String name
  - Response: Mission object

- POST `/api/agent`
  - Description: insert an Agent record to database
  - Access: SUPERUSER
  - Input: { name: String, alias: String}
  - Response: HTTP OK

- POST `/api/mission`
  - Description: insert a Mission record to database
  - Access: ADMIN, SUPERUSER
  - Input: { name: String }
  - Response: HTTP OK

- PUT `/api/agent/{alias}/mission/{name}`
  - Description: assign an Agent a Mission
  - Access: ADMIN, SUPERUSER
  - Input: String alias, String name
  - Response: HTTP OK

- PUT `/api/mission/{name}/status/{status}`
  - Description: update Mission status
  - Access: USER, ADMIN, SUPERUSER
  - Input: String name, String{UNASSIGNED/ASSIGNED/COMPLETED} status
  - Response: HTTP OK

- DELETE `/api/agent/{alias}`
  - Description: delete an Agent record based on its alias from database
  - Access: SUPERUSER
  - Input: String alias
  - Response: HTTP OK

- DELETE `/api/mission/{name}`
  - Description: delete a Mission record based on its name from database
  - Access: ADMIN, SUPERUSER
  - Input: String name
  - Response: HTTP OK

## Remarks
Due to time constraints, the API has not been thoroughly tested.

