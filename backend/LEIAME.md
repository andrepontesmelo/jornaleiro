# O que é

Jornaleiro é uma máquina de busca de diários oficiais brasileiros.

Este é o backend Java que faz um papel de fachada para várias plataformas cliente (iOS/Android/AngularJS). O usuário pode através deste serviço informar os *termos* de interesse e realizar buscas síncronas ou assíncronas. O resultado de busca contém os *snippets* de texto que contém os termos de interesse, além de ponteiros para os documentos relevantes.

# O que não é

Para as buscas assíncronas sejam possíveis, é necessário uma aplicação (console ou serviço do sistema operacional) que opere como produtor-consumidor, lendo os termos de interesse e produzindo resultados de busca. Este processamento não é feito aqui.

# Entidades

 * **Jornal:** Representa um jornal oficinal, pode ser o da união, dos estados ou municipal.  
 * **Sessão:** Um jornal têm várias sessões. É uma subdivisão do jornal.
 * **Documento:** Representa uma página de um documento de um dia de uma sessão de um jornal. Nem todo jornal tem o conceito de *página*.  
 * **Termo:** Cada usuário possui uma lista de termos de interesse.

# Rest - métodos genéricos

```
GET /jornal
GET /sessao
GET /documento/1234
GET /busca/exemplo_de_palavras_chaves
```

# Rest - Termos e resultado  

Cada usuário é indetificado por um uuid que pode ser gerado pela aplicação cliente. Não é necessário autenticação ou login. Protocolo https é utilizado para transmissão de dados criptografados.
```
[ GET | PUT ] /usuario/uuid/termo
GET /usuario/uuid/termo/Exemplo_Termo/resultado
GET /usuario/uuid/resultado
```
