'use strict';


angular.module('frontendApp')
  .controller('BuscaCtrl', ['$scope', 'buscaFactory', function ($scope, buscaFactory) {

    buscaFactory.get({busca: "de_macedo_e_melo"}, function (buscaFactory) {
      $scope.resultadoBusca = buscaFactory.resultadoBusca;
    });
  }]);
