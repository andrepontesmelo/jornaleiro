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

  it('should attach a list of awesomeThings to the scope', function () {
    expect(DocumentoCtrl.awesomeThings.length).toBe(3);
  });
});
