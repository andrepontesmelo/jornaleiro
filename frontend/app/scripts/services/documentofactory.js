'use strict';

angular.module('frontendApp')
  .factory('documentoFactory', function ($resource) {
    return $resource('http:/www.jornaleirooficial.com\\:8080/jornaleiro/rest/documento/:id', {id: 'id'}, {
      get: {
        method: 'GET',
        cache: true,
        isArray: false
      }
    });
  });

