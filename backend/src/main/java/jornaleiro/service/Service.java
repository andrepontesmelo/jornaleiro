package jornaleiro.service;

import jornaleiro.dto.DocumentXml;
import jornaleiro.dto.Session;

import java.util.ArrayList;

public class Service {

    public DocumentXml getDocumentXml(int id) throws Exception {
        return new DocumentXml(id);
    }

    public ArrayList<Session> getSessions() throws Exception {
        return jornaleiro.db.Session.getAll();
    }
}