'use strict';

angular.module('frontendApp')
  .factory('buscaFactory', function ($resource) {
    return $resource('http://localhost\\:8080/jornaleiro/rest/busca/:busca', {busca: 'busca'}, {
      get: {
        method: 'GET',
        cache: true,
        isArray: false
      }
    });
  });

