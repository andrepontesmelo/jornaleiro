'use strict';

angular.module('frontendApp')
  .factory('documentoFactory', function ($resource) {
    return $resource('http://192.168.1.4\\:8080/jornaleiro/rest/documento/:id', {id: 'id'}, {
      get: {
        method: 'GET',
        cache: true,
        isArray: false
      }
    });
  });

