'use strict';



angular.module('frontendApp')
  .controller('BuscaCtrl', ['$scope', '$sce', 'buscaFactory',  function ($scope, $sce, buscaFactory) {

    $scope.change = function(){
        buscaFactory.get({busca: $scope.query.replace(/ /g, '_')}, function (buscaFactory) {

          $scope.resultadoBusca = buscaFactory.resultadoBusca;
        });

    $scope.highlight = function(text, search) {

      if (!search) {
        return $sce.trustAsHtml(text);
      }

      return $sce.trustAsHtml(unescape(escape(text).replace(new RegExp(escape(search), 'gi'), '<span class="highlightedText">$&</span>')));
    };

  };

}]);
