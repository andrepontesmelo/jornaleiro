'use strict';

describe('Controller: SearchCtrl', function () {

  beforeEach(module('frontendApp'));

  var SearchCtrl,
    scope;

  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    SearchCtrl = $controller('SearchCtrl', {
      $scope: scope
    });
  }));

  it('should remove spaces within query', function () {
    expect(scope.removeSpaces('andre pontes')).toBe('andre_pontes');
  });

  it('should add spaces in query', function () {
    expect(scope.addSpaces('andre_pontes')).toBe('andre pontes');
  });
});
