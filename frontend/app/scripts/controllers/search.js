'use strict';

angular.module('frontendApp')
  .controller('SearchCtrl', ['$scope', '$sce', 'searchFactory', 'queryResult',
     function ($scope, $sce, searchFactory, queryResult) {

      $scope.keypress = function(keyEvent) {
        if (keyEvent.which === 13) {

          $scope.busy = true;
          $scope.cleanedQuery = $scope.removeSpaces($scope.query);
          queryResult.setQuery($scope.cleanedQuery);
          searchFactory.get({query: $scope.cleanedQuery }, function (searchFactory) {

            $scope.searchResult = searchFactory.searchResult;

            queryResult.setResult($scope.searchResult);

            $scope.elapsedTime = Math.round(searchFactory.elapsedTime * 100) / 100;

            $scope.busy = false;
          });
        }
    };

    $scope.removeSpaces = function(content) {
      return content.replace(/ /g, '_');
    };

    $scope.addSpaces = function(content) {
      return content.replace(/_/g, ' ');
    };


    $scope.highlight = function(text, search) {

        if (!search) {
          return $sce.trustAsHtml(text);
        }

        return $sce.trustAsHtml(window.unescape(window.escape(text).replace(new RegExp(window.escape(search), 'gi'),
          '<span class="highlightedText">$&</span>')));
      };

  $scope.query = $scope.addSpaces(queryResult.getQuery());
  $scope.queryResult = queryResult.getResult();
}]);
