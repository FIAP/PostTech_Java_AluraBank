# Resource Owner Password Credentials Grant

Esse grant geralmente é usado em cenários onde o **resource owner** (usuário) quer utilizar um **agent** (aplicação cliente) 
para acessar seus dados no **resource server** (servidor com as informações do usuário).

Porém deve existir um alto grau de confiança entre o **agent** e o **authorization server**.

## Flows

## Obter o token
```mermaid
sequenceDiagram
    actor       C   as Usuario [Resource Owner]
    participant A   as iFood App [Agent]
    participant AS  as Facebook [AuthZ Server]

    C->>A: Inicia o login com Facebook
    A->>C: Tela de login
    C->>A: Usuario/senha
    A->>AS: Usuario/senha e client id/client secret
    AS->>AS: Valida credenciais
    AS->>A: Access Token     
    A->>A: Salva o token     
```

## Buscar/Enviar informações
```mermaid
sequenceDiagram
    actor       C   as Usuario [Resource Owner]
    participant A   as iFood App [Agent]
    participant AS  as Facebook [AuthZ Server]
    participant RS  as Facebook [Resource Server]
    participant DB  as Database da Timeline
    
    C->>A: Quero acessar minha Timeline
    A->>RS: Timeline [Authorization: "Bearer access token"]
    RS->>RS: Valida o token
    RS->>DB: Busca informações
    RS->>A: Timeline
  
```
