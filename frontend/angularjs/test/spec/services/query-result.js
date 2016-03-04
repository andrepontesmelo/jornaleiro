'use strict';

describe('Service: queryResult', function () {

  // load the service's module
  beforeEach(module('frontendApp'));

  // instantiate service
  var queryResult;
  beforeEach(inject(function (_queryResult_) {
    queryResult = _queryResult_;
  }));

  it('should do something', function () {
    expect(!!queryResult).toBe(true);
  });

  it('should store the query', function() {
      queryResult.setQuery('my query');

      expect(queryResult.getQuery()).toBe('my query');
  });

});
