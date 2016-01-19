'use strict';

/**
 * @ngdoc service
 * @name frontendApp.dadosBusca
 * @description
 * # dadosBusca
 * Service in the frontendApp.
 */
angular.module('frontendApp')
  .service('dadosBusca', function () {
    var query = '';
    var resultadoBusca = [];

    return {
        getQuery: function () {
            return query;
        },
        setQuery: function(value) {
            query = value;
        },
        getResultadoBusca: function() {
          return resultadoBusca;
          },
        setResultadoBusca: function(value) {
          resultadoBusca = value;
        }
    };
  });
