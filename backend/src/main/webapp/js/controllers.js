'use strict';

/* Controllers */


var app = angular.module('ngdemo.controllers', []);


// Clear browser cache (in development mode)
//
// http://stackoverflow.com/questions/14718826/angularjs-disable-partial-caching-on-dev-machine
app.run(function ($rootScope, $templateCache) {
    $rootScope.$on('$viewContentLoaded', function () {
        $templateCache.removeAll();
    });
});


app
.controller('MyCtrl1', ['$scope', 'UserFactory', function ($scope, UserFactory) {

    UserFactory.get({}, function (userFactory) {
        $scope.firstname = "userFactory.firstName";
    })
}]);
