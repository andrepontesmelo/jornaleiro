'use strict';

angular.module('frontendApp')
  .factory('searchFactory', function ($resource) {
    return $resource('/jornaleiro/rest/search/:query', {query: 'query'}, {
      get: {
        method: 'GET',
        cache: true,
        isArray: false
      }
    });
  });

