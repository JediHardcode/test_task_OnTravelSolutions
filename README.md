Test task for OnTravelSolutions
установить в telegram.properties
bot.token=832281051:AAEPpY2LhLeTV8mccHfwg9HmRlr-mGQ1JeI
bot.username=CityGuideKirillBot 
Задать необходимые данные в application.properties  для соединения с БД
Приложение написано под MySql и автозагрузка данных для БД с помощью LuquiBase (default database name - task)
Примеры команд для Rest функционала :
1) api/v1/city POST - добавление города  (достопримечательности опционально)
{
    "name": "city name",
    "attractions": [
        {
            "name": "attraction name"
        }
    ]
}
2) api/v1/city PATCH - обновление информации о городе 
{
    "id": 1,
    "name": "new name"
}
3) api/v1/city/{id} DELETE - удаление города по айди 
4) api/v1/cities - GET - список всех городов с достопримечательностями 
5) api/v1/city/attraction POST - добавление достопримечательности в заданый город (ID города задается через параметр (cityId) реквеста)
 {
     "name": "attraction name"
 }
 6) /api/v1/attraction/{id} DELETE - удаление достопримечательности 
 7) /api/v1/attraction PATCH - обновление достопримечательности
 {
     "id": 1,
     "name": "new name"
 }