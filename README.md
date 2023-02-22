# elastic-search application 

---
 - Download : `https://github.com/taylan32/elastic-search.git`
 - Elasticsearch version: 7.17.9
 - Docker command: Docker compose up
 - swagger url:  `http://localhost:8080/swagger-ui/index.html`


endpoints

- create person `POST http://localhost:8080/api/person/`

example request body
```json
{
  "name": "string",
  "lastName": "string",
  "address": "string",
  "birthOfDate": "2023-02-22T17:23:17.514Z"
}
```
- search-by-name `GET http://localhost:8080/api/person/search-by-name/{name}`
> - score search `GET http://localhost:8080/api/person/score-search`
> ### queries:
> - fieldName (required)
> - searchKey (required)
> - matchRate (optional)
> - #### example 
> - http://localhost:8080/api/person/score-search?fieldName=name&searchKey={nameToSearch}&matchRate=80


> - fuzzy search `GET http://localhost:8080/api/person/fuzzy-search`
> ### queries:
> - fieldName (required)
> - searchKey (required)
> - #### example
> - http://localhost:8080/api/person/fuzzy-search?fieldName=name&searchKey={nameToSearch}

