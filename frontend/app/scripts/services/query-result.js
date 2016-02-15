'use strict';

/**
 * @ngdoc service
 * @name frontendApp.queryResult
 * @description
 * # queryResult
 * Service in the frontendApp.
 */
angular.module('frontendApp')
  .service('queryResult', function () {
    var query = '';
    var result = [];

    return {
        getQuery: function () {
            return query;
        },
        setQuery: function(value) {
            query = value;
        },
        getResult: function() {
          return result;
          },
        setResult: function(value) {
          result = value;
        }
    };
  });
