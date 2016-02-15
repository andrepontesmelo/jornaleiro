'use strict';

describe('Service: documentFactory', function () {

  // load the service's module
  beforeEach(module('frontendApp'));

  // instantiate service
  var documentFactory;
  beforeEach(inject(function (_documentFactory_) {
    documentFactory = _documentFactory_;
  }));

  it('should do something', function () {
    expect(!!documentFactory).toBe(true);
  });

});
