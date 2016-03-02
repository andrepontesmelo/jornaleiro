package jornaleiro.service;

import jornaleiro.dto.DocumentXml;

public class Service {

    public DocumentXml getDocumentXml(int id) throws Exception {
        return new DocumentXml(id);
    }
}