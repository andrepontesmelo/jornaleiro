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

  it('deve retirar espaços da query', function () {
    expect(BuscaCtrl.retirar_espacos("andre pontes")).toBe('andre_pontes');
  });

  it('deve colocar espaços na query', function () {
    expect(BuscaCtrl.colocar_espacos("andre_pontes")).toBe('andre pontes');
  });
});
