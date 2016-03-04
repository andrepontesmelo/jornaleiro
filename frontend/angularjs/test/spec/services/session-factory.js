'use strict';

describe('Service: sessionFactory', function () {

  // load the service's module
  beforeEach(module('frontendApp'));

  // instantiate service
  var sessionFactory;
  beforeEach(inject(function (_sessionFactory_) {
    sessionFactory = _sessionFactory_;
  }));

  it('should do something', function () {
    expect(!!sessionFactory).toBe(true);
  });

});
