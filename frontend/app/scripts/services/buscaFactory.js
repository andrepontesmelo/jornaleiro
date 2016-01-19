'use strict';

angular.module('frontendApp')
  .factory('buscaFactory', function ($resource) {
    return $resource('http://www.jornaleirooficial.com\\:8080/jornaleiro/rest/busca/:busca', {busca: 'busca'}, {
      get: {
        method: 'GET',
        cache: true,
        isArray: false
      }
    });
  });

