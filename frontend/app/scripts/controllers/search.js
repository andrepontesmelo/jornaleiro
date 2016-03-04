'use strict';

angular.module('frontendApp')
  .controller('SearchCtrl', ['$scope', '$sce', 'searchFactory', 'sessionFactory', 'queryResult',
     function ($scope, $sce, searchFactory, sessionFactory, queryResult) {

      $scope.keypress = function(keyEvent) {
        if (keyEvent.which === 13) {

          $scope.busy = true;
          $scope.cleanedQuery = $scope.removeSpaces($scope.query);
          queryResult.setQuery($scope.cleanedQuery);
          searchFactory.get({query: $scope.cleanedQuery }, function (searchFactory) {
            $scope.hits = searchFactory.hits;
            queryResult.setResult($scope.hits);
            $scope.elapsedTime = Math.round(searchFactory.elapsedTime * 100) / 100;
            $scope.busy = false;
          });
        }
    };

    sessionFactory.get().$promise.then(function(array) {
     var arrayLength = array.length;
     var cache = {};

     for (var x = 0; x < arrayLength; x++) {
       var idItem = array[x].id;
       var item = { "session": array[x].title, "journal": array[x].journal.name };

       cache[idItem] = item;
     }

     $scope.sessions = cache;
   });


    $scope.removeSpaces = function(content) {
      return content.replace(/ /g, '_');
    };

    $scope.addSpaces = function(content) {
      return content.replace(/_/g, ' ');
    };

    $scope.renderHtml = function(html)
    {
      return $sce.trustAsHtml(html);
    };

  $scope.query = $scope.addSpaces(queryResult.getQuery());
  $scope.hits = queryResult.getResult();
}]);
