'use strict';

describe('Service: searchFactory', function () {

  // load the service's module
  beforeEach(module('frontendApp'));

  // instantiate service
  var searchFactory;
  beforeEach(inject(function (_searchFactory_) {
    searchFactory = _searchFactory_;
  }));

  it('should do something', function () {
    expect(!!searchFactory).toBe(true);
  });

});
