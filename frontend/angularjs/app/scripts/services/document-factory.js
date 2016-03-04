'use strict';

angular.module('frontendApp')
  .factory('documentFactory', function ($resource) {
    return $resource('/jornaleiro/rest/document/:id', {id: 'id'}, {
      get: {
        method: 'GET',
        cache: true,
        isArray: false
      }
    });
  });
