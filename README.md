#Klijent -> Server
* `QUIT`
* `ONLINE all`
* `NAME <my_name>`
* `SEND [all|<recipient_name>] <message>`

# Server -> Klijent
* `NAME [ok|taken]`
* `ERROR recipient`
* `OFFLINE <client_name>`
* `ONLINE new <new_client_name>`
* `ONLINE all <all_client_names>`
* `RECEIVE <sender_name> <message>`
