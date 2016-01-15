'use strict';

describe('Service: documentoFactory', function () {

  // load the service's module
  beforeEach(module('frontendApp'));

  // instantiate service
  var documentoFactory;
  beforeEach(inject(function (_documentoFactory_) {
    documentoFactory = _documentoFactory_;
  }));

  it('should do something', function () {
    expect(!!documentoFactory).toBe(true);
  });

});
