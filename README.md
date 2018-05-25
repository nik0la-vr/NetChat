# Client -> Server
* QUIT
* ONLINE all
* NAME <my_name>
* SEND <recipient_name> <message>

# Server -> Client
* NAME [ok|taken]
* OFFLINE <client_name>
* ONLINE new <new_client_name>
* ONLINE all <all_client_names>
* RECEIVE <sender_name> <message>
