'use strict';

describe('Controller: DocumentoCtrl', function () {

  // load the controller's module
  beforeEach(module('frontendApp'));

  var DocumentoCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    DocumentoCtrl = $controller('DocumentoCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

});
