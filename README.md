# Sections and GeoClasses project

## Technology stack

- Java Core,
- Spring Boot,
- Spring Data,
- Spring Security,
- Hibernate,
- PostgreSQL,
- Apache POI.

## Sections and geological classes

Each Section has structure:

![image](https://user-images.githubusercontent.com/119116584/224563576-bfab3764-65b1-480e-b7af-55ae9b0477c3.png)

Each XLS file contains headers and list of sections with it’s geological classes. Example:

![image](https://user-images.githubusercontent.com/119116584/224563622-452d4a2c-1c7d-4617-96d8-d7449fa4fd39.png)

## Web interface
- Add API GET /sections/by-code?code=... that returns a list of all Sections that have geologicalClasses with the specified code.
- API POST /import (file) returns ID of the Async Job and launches importing.
- API GET /import/{id} returns result of importing by Job ID ("DONE", "IN PROGRESS", "ERROR").
- API GET /export returns ID of the Async Job and launches exporting.
- API GET /export/{id} returns result of parsed file by Job ID ("DONE", "IN PROGRESS", "ERROR").
- API GET /export/{id}/file returns a file by Job ID (throw an exception if exporting is in process).

## Set up before starting

- "application.properties" file.
- Add tables to the database through the console.
CREATE TABLE section(
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name varchar);
CREATE TABLE geologicalClass(
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name varchar,
    code varchar,
    section_id int REFERENCES section(id));
