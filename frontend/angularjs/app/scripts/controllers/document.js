'use strict';


angular.module('frontendApp')
  .controller('DocumentCtrl', ['$scope', '$sce', '$routeParams', 'documentFactory', 'queryResult',
  function ($scope, $sce, $routeParams, documentFactory, queryResult) {

    $scope.query = queryResult.getQuery();

    $scope.busy = true;

    documentFactory.get({id: $routeParams.id}, function (documentFactory) {
      $scope.document = documentFactory.document;
      $scope.after = documentFactory.after;
      $scope.before = documentFactory.before;
      $scope.id = documentFactory.id;
      $scope.busy = false;
    });

    $scope.highlight = function(text, search) {

      if (!search) {
        return $sce.trustAsHtml(text);
      }

      return $sce.trustAsHtml(window.unescape(window.escape(text).replace(new RegExp(window.escape(search), 'gi'),
        '<span class="highlightedText">$&</span>')));
    };
}]);
