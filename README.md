# Камень Ножницы Бумага Enterprise Edition

Небольшой проект реализующий игру камень ножницы бумага на стеке Spring, Spring JDBC, MySQL, KryoNet.

Состоит из двух частей, сервер и клиент

Для запуска сервера достаточно всего лишь собрать проект командой `gradlew build`, а затем запустить файл server.bat или
server.sh в папке docker. Он просто запустит docker-compose с базой данных и сервером

Чтобы запустить клиент, достаточно запустить файл client.sh

## Этапы игры:

### Этап SignUp/SignIn

Когда игрок не подключен к серверу, он находится на начальных этапах SignUp/SignIn
одновременно. В зависимости от того, какую команду он введёт, будет либо
произведена регистрация, либо авторизация.
Если игрок регистрируется, то после этого он попадает обратно на начальный этап

### SignUp/SignIn.

Далее он может авторизоваться в игре по данным, которые он использовал для
регистрации.
В момент регистрации должно устанавливаться соединение с игровым сервером по
протоколу TCP. После того, как запрос на регистрацию был обработан клиент должен
быть отключен от сервера. Был ли запрос успешным или нет - это не важно.
В момент авторизации, если учётные данные были верными, игрок либо попадает на
Этап Menu, либо на один из этапов Game Step. Это зависит от того, была ли у него уже
запущена какая-либо игра.

### Этап Menu

На этом этапе можно либо отключиться от сервера, воспользовавшись командой
logout, либо начать новую игру через команду start.
При вводе команды start игрок попадает на этап Game Step 1.

### Этапы Game Step 1, Game Step 2, Game Step 3

На данных этапах происходит сама игра. Игрок первым выбирает какой из предметов
он будет использовать. Это делается через ввод команд rock, paper, scissors. В ответ
сервер отправляет свой вариант и объявляет о том, кто выиграл на данном этапе -
игрок или сервер.
Можно использовать команду logout, чтобы отключиться от сервера.
На каждый этап даётся по 30 секунд времени. Если игрок за это время не выбрал свой
предмет, то он проигрывает и переходит на следующий этап.
Сервер предупреждает об оставшемся времени в те моменты, когда остаётся 30, 15, 5,
3 и 1 секунда до конца хода. Так же можно запросить предупреждение командой timer.
Если игрок отключается каким-либо образом от сервера в момент нахождения на
одном из этапов Game Step, то таймер перестаёт тикать. При повторном входе в игру,
таймер продолжает отсчёт. После завершения 3 раунда, сервер подводит итоги, и через 5 секунд выкидывает игрока обратно в
меню. Во время этого отсчёта он может разлогиниться командой logout

# Список доступных команд клиенту:

* Signup - регистрация игрока
  * Формат команды: signup=<login>=<password>
* Signin - авторизация игрока
  * Формат команды: signin=<login>=<password>
* Start - начать игру
  * Формат команды: start
* Logout - отключиться от сервера
  * Формат команды: logout
* Timer - Узнать сколько времени осталось до конца хода
  * Формат команды: timer
* Rock - камень
  * Формат команды: rock
* Paper - бумага
  * Формат команды: paper
* Scissors - ножницы
  * Формат команды: scissors