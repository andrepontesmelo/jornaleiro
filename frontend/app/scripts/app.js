'use strict';

/**
 * @ngdoc overview
 * @name frontendApp
 * @description
 * # frontendApp
 *
 * Main module of the application.
 */
angular
  .module('frontendApp', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch'
  ])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl',
        controllerAs: 'main'
      })
      .when('/about', {
        templateUrl: 'views/about.html',
        controller: 'AboutCtrl',
        controllerAs: 'about'
      })
      .when('/busca', {
        templateUrl: 'views/busca.html',
        controller: 'BuscaCtrl',
        controllerAs: 'busca'
      })
      .when('/documento/:id', {
        templateUrl: 'views/documento.html',
        controller: 'DocumentoCtrl',
        controllerAs: 'documento'
      })
      .otherwise({
        redirectTo: '/'
      });
  });
