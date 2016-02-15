--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

--
-- Name: documento_id_seq; Type: SEQUENCE; Schema: public; Owner: jornaleiro
--

CREATE SEQUENCE documento_id_seq
    START WITH 320368
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 2147483648
    CACHE 1;


ALTER TABLE documento_id_seq OWNER TO jornaleiro;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: document; Type: TABLE; Schema: public; Owner: jornaleiro; Tablespace: 
--

CREATE TABLE document (
    id integer DEFAULT nextval('documento_id_seq'::regclass) NOT NULL,
    session integer,
    page integer,
    date timestamp without time zone,
    title character varying(400),
    url character varying(200),
    content text
);


ALTER TABLE document OWNER TO jornaleiro;

--
-- Name: journal; Type: TABLE; Schema: public; Owner: jornaleiro; Tablespace: 
--

CREATE TABLE journal (
    id integer NOT NULL,
    name character varying(45)
);


ALTER TABLE journal OWNER TO jornaleiro;

--
-- Name: session; Type: TABLE; Schema: public; Owner: jornaleiro; Tablespace: 
--

CREATE TABLE session (
    id integer NOT NULL,
    title character varying(50),
    journal integer
);


ALTER TABLE session OWNER TO jornaleiro;

--
-- Name: documento_data_sessao_pagina; Type: CONSTRAINT; Schema: public; Owner: jornaleiro; Tablespace: 
--

ALTER TABLE ONLY document
    ADD CONSTRAINT documento_data_sessao_pagina UNIQUE (date, session, page);


--
-- Name: documento_pkey; Type: CONSTRAINT; Schema: public; Owner: jornaleiro; Tablespace: 
--

ALTER TABLE ONLY document
    ADD CONSTRAINT documento_pkey PRIMARY KEY (id);


--
-- Name: jornal_pkey; Type: CONSTRAINT; Schema: public; Owner: jornaleiro; Tablespace: 
--

ALTER TABLE ONLY journal
    ADD CONSTRAINT jornal_pkey PRIMARY KEY (id);


--
-- Name: sessao_pkey; Type: CONSTRAINT; Schema: public; Owner: jornaleiro; Tablespace: 
--

ALTER TABLE ONLY session
    ADD CONSTRAINT sessao_pkey PRIMARY KEY (id);


--
-- Name: texto_fulltext_idx; Type: INDEX; Schema: public; Owner: jornaleiro; Tablespace: 
--

CREATE INDEX texto_fulltext_idx ON document USING gin (to_tsvector('portuguese'::regconfig, content));


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

