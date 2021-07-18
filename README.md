crudddemo

rest application with spring boot and embedded h2 database
usage -> mvn clean install and jar run

Details:

 - endpoint zwracajacy wszystkie dostepne cytaty - GET /v1/api/quotes
 - endpoint pozwalajacy na zapis cytatu - POST /v1/api/quotes
 DTO structure example:
{
    "author": "Robert",
    "content": "testowy"
}
id will be auto generated 

 - endpoint pozwalajacy zaktualizowac istniejacy cytatu - PUT /v1/api/quotes/{id}
 dto like above
 
 - endpoint pozwalajacy usunac wybrany cytat - DELETE /v1/api/quotes/{id}
 
 ALSO Get by id is supported GET /v1/api/quotes/{id}