# Chat

Domaći iz računarskih mreža. Prosta klijent-server čet aplikacija u Javi.

![screenshot](doc/screenshot.png)

## Bildovanje

1. Otvorite projekat u [IntelliJ IDEA](https://www.jetbrains.com/idea/download/).
2. Kliknite na dugme za bildovanje.

Ili bacite pogled na [How do I compile and run Intelij IDEA java project without IDE?](https://stackoverflow.com/questions/22857478/how-do-i-compile-and-run-intelij-idea-java-project-without-ide).

## Poruke

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
