'use strict';


angular.module('frontendApp')
  .controller('DocumentoCtrl', ['$scope', '$sce', '$routeParams', 'documentoFactory', 'dadosBusca',
  function ($scope, $sce, $routeParams, documentoFactory, dadosBusca) {

    $scope.query = dadosBusca.getQuery();

    documentoFactory.get({id: $routeParams.id}, function (documentoFactory) {
      $scope.documento = documentoFactory.documento;
      $scope.posterior = documentoFactory.posterior;
      $scope.anterior = documentoFactory.anterior;
      $scope.id = documentoFactory.id;
    });

    $scope.highlight = function(text, search) {

      if (!search) {
        return $sce.trustAsHtml(text);
      }

      return $sce.trustAsHtml(unescape(escape(text).replace(new RegExp(escape(search), 'gi'), '<span class="highlightedText">$&</span>')));
    };


}]);
