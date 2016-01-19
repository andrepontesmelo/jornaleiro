'use strict';

describe('Service: dadosBusca', function () {

  // load the service's module
  beforeEach(module('frontendApp'));

  // instantiate service
  var dadosBusca;
  beforeEach(inject(function (_dadosBusca_) {
    dadosBusca = _dadosBusca_;
  }));

  it('should do something', function () {
    expect(!!dadosBusca).toBe(true);
  });

  it('deve armazenar a query', function() {
      dadosBusca.setQuery('minha query');

      expect(dadosBusca.getQuery()).toBe('minha query');
  });




});
