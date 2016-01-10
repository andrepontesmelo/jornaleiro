'use strict';

describe('Controller: BuscaCtrl', function () {

  // load the controller's module
  beforeEach(module('frontendApp'));

  var BuscaCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    BuscaCtrl = $controller('BuscaCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(BuscaCtrl.awesomeThings.length).toBe(3);
  });
});
