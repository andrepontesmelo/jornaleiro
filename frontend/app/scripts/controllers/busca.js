'use strict';



angular.module('frontendApp')
  .controller('BuscaCtrl', ['$scope', '$sce', 'buscaFactory', 'dadosBusca',
     function ($scope, $sce, buscaFactory, dadosBusca) {

    $scope.keypress = function(keyEvent) {
      if (keyEvent.which === 13) {

        $scope.ocupado = true;
        $scope.query_limpo = $scope.retirar_espacos($scope.query);
        dadosBusca.setQuery($scope.query_limpo);
        buscaFactory.get({busca: $scope.query_limpo }, function (buscaFactory) {

          $scope.resultadoBusca = buscaFactory.resultadoBusca;

          dadosBusca.setResultadoBusca($scope.resultadoBusca);

          $scope.tempoDecorrido = Math.round(buscaFactory.tempoDecorrido * 100) / 100;

          $scope.ocupado= false
        });
      }
  };

    $scope.retirar_espacos = function(texto) {
      return texto.replace(/ /g, '_');
    };

    $scope.colocar_espacos = function(texto) {
      return texto.replace(/_/g, ' ');
    };


    $scope.highlight = function(text, search) {

        if (!search) {
          return $sce.trustAsHtml(text);
        }

        return $sce.trustAsHtml(unescape(escape(text).replace(new RegExp(escape(search), 'gi'), '<span class="highlightedText">$&</span>')));
      };

  $scope.query = $scope.colocar_espacos(dadosBusca.getQuery());
  $scope.resultadoBusca = dadosBusca.getResultadoBusca();
}]);
