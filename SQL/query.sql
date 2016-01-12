
# Sintaxe MySQL:

select * from documento where match(texto) against('+"Martins Pontes"' in boolean mode);

UPDATE documento SET texto = replace(replace(column,CHAR(0),' '),CHAR(1),' ')

# Sintaxe PostgreSQL:

ALTER TABLE documento  ADD COLUMN textoMinusculo text;

update documento set textoMinusculo=lower(texto);

CREATE INDEX texto_fulltext_idx ON documento   USING gin(to_tsvector('portuguese', textoMinusculo));

select count(*) from documento where id is null;
delete from documento where id is null;
ALTER TABLE documento ALTER COLUMN id SET NOT NULL;

SELECT max(id) FROM documento;

CREATE SEQUENCE documento_id_seq
        INCREMENT 1
        MINVALUE 1
        MAXVALUE 2147483648 START 558405
        CACHE 1;

ALTER TABLE documento ALTER COLUMN id SET DEFAULT nextval('documento_id_seq'::regclass);
        
   
select * from documento where to_tsvector('portuguese'::regconfig, textoMinusculo) @@ to_tsquery('macedo & melo') AND textoMinusculo like '%macedo e melo%';


\copy (select * from documento where to_tsvector('portuguese'::regconfig, textoMinusculo) @@ to_tsquery('pontes & melo') AND textoMinusculo like '%andré pontes melo%') To '/tmp/test.csv' With CSV;

  