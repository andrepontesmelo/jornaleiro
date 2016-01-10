package jornaleiro.dto;

import junit.framework.TestCase;


public class ResultadoBuscaTest extends TestCase {

    public void testFiltrarTexto() throws Exception {

        // Deve filtrar texto Exato
        ResultadoBusca resultado = new ResultadoBusca();
        resultado.setTexto("Texto Exato");
        resultado.filtrarTexto("Texto Exato");

        assertEquals("Texto Exato", resultado.getTexto());
    }

}