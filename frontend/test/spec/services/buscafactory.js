'use strict';

describe('Service: buscaFactory', function () {

  // load the service's module
  beforeEach(module('frontendApp'));

  // instantiate service
  var buscaFactory;
  beforeEach(inject(function (_buscaFactory_) {
    buscaFactory = _buscaFactory_;
  }));

  it('should do something', function () {
    expect(!!buscaFactory).toBe(true);
  });

});
