'use strict';

describe('Controller: DocumentCtrl', function () {

  beforeEach(module('frontendApp'));

  var DocumentCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    DocumentCtrl = $controller('DocumentCtrl', {
      $scope: scope
    });
  }));

});
