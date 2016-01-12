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
    START WITH 558405
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 2147483648
    CACHE 1;


ALTER TABLE documento_id_seq OWNER TO jornaleiro;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: documento; Type: TABLE; Schema: public; Owner: jornaleiro; Tablespace: 
--

CREATE TABLE documento (
    id integer DEFAULT nextval('documento_id_seq'::regclass) NOT NULL,
    texto character varying(5592405),
    sessao integer,
    pagina integer,
    data timestamp without time zone,
    titulo character varying(400),
    url character varying(200),
    textominusculo text
);


ALTER TABLE documento OWNER TO jornaleiro;

--
-- Name: jornal; Type: TABLE; Schema: public; Owner: jornaleiro; Tablespace: 
--

CREATE TABLE jornal (
    id integer,
    nome character varying(45)
);


ALTER TABLE jornal OWNER TO jornaleiro;

--
-- Name: sessao; Type: TABLE; Schema: public; Owner: jornaleiro; Tablespace: 
--

CREATE TABLE sessao (
    id integer,
    titulo character varying(50),
    jornal integer
);


ALTER TABLE sessao OWNER TO jornaleiro;

--
-- Name: texto_fulltext_idx; Type: INDEX; Schema: public; Owner: jornaleiro; Tablespace: 
--

CREATE INDEX texto_fulltext_idx ON documento USING gin (to_tsvector('portuguese'::regconfig, textominusculo));


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

