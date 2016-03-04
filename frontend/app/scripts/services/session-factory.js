'use strict';

angular.module('frontendApp')
  .factory('sessionFactory', function ($resource) {
    var cache;

    return $resource('/jornaleiro/rest/sessions/', {}, {
          get: {
            method: 'GET',
            cache: true,
            isArray: true
          }});
  });
