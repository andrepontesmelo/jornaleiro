'use strict';

angular.module('frontendApp')
  .factory('sessionFactory', function ($resource) {
    return $resource('/jornaleiro/rest/sessions/', {}, {
          get: {
            method: 'GET',
            cache: true,
            isArray: true
          }});
  });
