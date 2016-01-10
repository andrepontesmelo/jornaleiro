# O que é

Jornaleiro é uma máquina de busca de diários oficiais brasileiros.

# Entidades

 * **Jornal:** Representa um jornal oficinal, pode ser o da união, dos estados ou municipal.  
 * **Sessão:** Um jornal têm várias sessões. É uma subdivisão do jornal.
 * **Documento:** Representa uma página de um documento de um dia de uma sessão de um jornal. Nem todo jornal tem o conceito de *página*.  
 * **Termo:** Cada usuário possui uma lista de termos de interesse.

# Tecnologias

 * **backend** Backend em Java para métodos REST.
 * **frontend** Aplicação AngularJS criado com yeoman para buscas síncronas.
 * **crawler** Aplicação Ruby com Capybara e PhantomJS para obtenção dos jornais oficiais.
