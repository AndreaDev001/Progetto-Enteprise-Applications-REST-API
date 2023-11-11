# Progetto-Enteprise-Applications-REST-API
Progetto individuale per il corso di Enterprise-Applications, back-end che offre una REST Api che verrà utilizzata da un'applicazione mobile </br>
Questa applicazione offre degli end-point REST che permettono di recuperare e modificare dati dal database, utilizza Java e il framework Spring Boot, sviluppato da Andrea Marchio 223401.</br>
Per eseguire l'applicazione è necessario specificare le seguenti variabili d'ambiente:
+ **SERVER_PORT**
+ **DB_USERNAME**
+ **DB_PASSWORD** </br>

E' possibile provare gli end-point senza utilizzare il relativo client Android utilizzando l'interfaccia di Swagger al seguente url http://localhost:8080/api/v1/documentation/ui.</br>
Gli end-point sono divisi in due categorie: /public e /private, è possibile provare i /public senza dover essere autenticati, mentre quelli privati richiedono un access token per autenticare l'utente e verificare relativi permessi.</br>
L'applizazione utilizza un authorization server, il cui codice è disponibile nella seguente repository: [Progetto-Enteprise-Applications-Authentication](https://github.com/AndreaDev001/Progetto-Enterprise-Applications-Authentication) </br>

E' disponibile un dump sql per popolare il database, se non utilizzato bisogna assicurarsi di aver disponibile uno schema con nome **enterpriseapplications** all'interno del proprio database
E' possibile ottenere un access token utilizzando il client android o postman:</br>
**Auth URL**: http://enterpriseapplications.live:9000/oauth2/authorize</br>
**Token URL**: http://enterpriseapplications.live:9000/oauth2/token</br>
**Callback URL**: https://oauth.pstmn.io/v1/callback </br>
**Client**: client </br>
**Secret**: secret </br>

![Alt text](https://i.imgur.com/JSnPvFf.png)
