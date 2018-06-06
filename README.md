# Chat

Domaći iz računarskih mreža. Prosta klijent-server čet aplikacija u Javi.

![screenshot](doc/screenshot.png)

## Korišćenje

Bildovanje:
 * Iz [IntelliJ IDEA](https://www.jetbrains.com/idea/download/) *(preporučeno)*.
 * Alternativno pogledajte [How do I compile and run Intelij IDEA java project without IDE?](https://stackoverflow.com/questions/22857478/how-do-i-compile-and-run-intelij-idea-java-project-without-ide).

Server palite iz komandne linije. Broj porta je opcioni argument.

```
java -jar Server.jar [port_number=8818]
```

## Poruke

Server takoreći služi kao middleman između klijenata. 

### Klijent → Server

* `QUIT`
* `ONLINE all`
* `NAME <my_name>`
* `SEND [all|<recipient_name>] <message>`

### Server → Klijent
* `NAME [ok|taken]`
* `ERROR recipient`
* `OFFLINE <client_name>`
* `ONLINE new <new_client_name>`
* `ONLINE all <all_client_names>`
* `RECEIVE <sender_name> <message>`
