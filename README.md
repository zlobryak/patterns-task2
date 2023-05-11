## Patterns homework task 2
___
### Appveyor bage
[![Build status](https://ci.appveyor.com/api/projects/status/7uwb5vqnyu2lrtg7?svg=true)](https://ci.appveyor.com/project/zlobryak/patterns-task2)
___
### How to run SUT
To run SUT use  `java -jar app-ibank.jar -P:profile=test`  
Flag `-P:profile=test` for test mode.
___
### Log example:

```Request method:    POST  
 Request URI:    http://localhost:9999/api/system/users  
 Proxy:          <none>  
 Request params: <none>  
 Query params:   <none>  
 Form params:    <none>  
 Path params:    <none>  
 Headers:        Accept=application/json, application/javascript, text/javascript, text/json  
 Content-Type=application/json; charset=UTF-8  
 Cookies:        <none>  
 Multiparts:     <none>

 Body:  
 {  
 "login": "vasya",  
 "password": "password",  
 "status": "active"  
 }
 ```
___
### To register new user use:

```
 POST /api/system/users
 Content-Type: application/json

 {  
 "login": "vasya",  
 "password": "password",  
 "status": "active"  
 }

 Возможные значения поля «Статус»:
 * «active» — пользователь активен,
 * «blocked» — пользователь заблокирован.

 В случае успешного создания пользователя возвращается код 200.
 При повторной передаче пользователя с таким же логином будет выполнена перезапись данных пользователя. 
 ```