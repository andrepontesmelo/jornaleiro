'use strict';


angular.module('frontendApp')
  .controller('DocumentoCtrl', ['$scope', '$routeParams', 'documentoFactory', function ($scope, $routeParams, documentoFactory) {

    documentoFactory.get({id: $routeParams.id}, function (documentoFactory) {
      $scope.documento = documentoFactory.documento;
      $scope.posterior = documentoFactory.posterior;
      $scope.anterior = documentoFactory.anterior;
      $scope.id = documentoFactory.id;
    });

}]);
