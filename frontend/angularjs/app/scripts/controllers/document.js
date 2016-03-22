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

      var highlightExp = new RegExp(window.escape(search), 'gi');
      var replaced = window.escape(text).replace(highlightExp, '<span class="highlightedText">$&</span>');

      replaced = replaced.replace(new RegExp(window.escape('; '), 'gi'), '<br/>');
      replaced = replaced.replace(new RegExp(window.escape(' - '), 'gi'), '<br/>');
      replaced = replaced.replace(new RegExp(window.escape('r$'), 'gi'), 'R$');

      return $sce.trustAsHtml(window.unescape(replaced));
    };
}]);
