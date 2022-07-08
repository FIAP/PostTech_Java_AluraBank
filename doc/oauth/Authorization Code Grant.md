# Authorization Code Grant


Esse grant é usado em cenários onde o **resource owner** (usuário) 
quer utilizar um **agent** (aplicação cliente como applicativo mobile ou navegador) de __terceiros__ 
para acessar suas informações no **resource server** (servidor com as informações do usuário).

O **agent** vai se comunicar com o **client application** (seu próprio backend) e este   
vai requisitar as informações do **resource owner** ao **resource server**.

Nesse caso, não existe um alto grau de confiança entre o **client application** e o **resource server**.

[Documentação oficial](https://oauth.net/2/grant-types/authorization-code/)


## Flows

## Obter o token
```mermaid
sequenceDiagram
    actor       C   as Rita [Resource Owner]
    participant A   as Alura App [Agent]
    participant CL  as Alura Backend [ClientApp]
    participant AS  as Facebook [AuthZ Server]

    C->>A: Inicia o login com Facebook
    A->>C: Abre o navegador com URL do FB
    C->>AS: Quero me autenticar GET client_id, redirect_uri
    AS->>C: Tela de login 302/307
    C->>AS: Usuario/senha
    AS->>AS: Valida credenciais do Resource Owner
    AS->>C: 302/307 -> redirect uri ? code - Solicita confirmação 
    C->>CL: POST Confimo - code
    CL->>AS: POST code, client id+secret 
    AS->>AS: Valida credenciais do Client App
    AS->>CL: Access Token
    CL->>CL: Salvar o Token associado ao Resource Owner
```

## Buscar/Enviar informações
```mermaid
sequenceDiagram
    actor       C   as Rita [Resource Owner]
    participant A   as Alura App [Agent]
    participant CL  as Alura Backend [ClientApp]
    participant RS  as Facebook [Resource Server]
    participant DB  as Database da Facebook
    
    C->>A: Quero acessar meu perfil
    A->>CL: Busca informações do perfil
    CL->>CL: Busca dados de Perfil no banco de dados
    CL->>RS: Buscar Foto, Nome e Email [Authorization: "Bearer access token"]
    RS->>RS: Valida token JWT
    RS->>DB: Busca Foto, Nome e Email
    DB->>RS: Foto, Nome e Email
    RS->>CL: Foto, Nome e Email
    CL->>A: Agrega informações do banco de dados + facebook
```
