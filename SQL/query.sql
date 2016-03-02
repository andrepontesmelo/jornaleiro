# PostgreSQL full text search
select * from documento where to_tsvector('portuguese'::regconfig, textoMinusculo) @@ to_tsquery('ellen & carolina & deus & andrade') AND textoMinusculo like '%ellen carolina de deus andrade%';

# Show biggest words
select unnest(string_to_array(textoMinusculo,' ')) from documento where data='2016-01-16' order by length(unnest(string_to_array(textoMinusculo,' ')) ) desc limit 1000;

## Database cleanup 

# Replace several spaces by one space.
update document set content = regexp_replace(content, '[ \t\n\r]+', ' ', 'g');

# Replace several dots by one dot.
update document set content = regexp_replace(content, '[.]+', '.', 'g');

# Replace several slashes by one slash.
update document set content = regexp_replace(content, '[-]+', '-', 'g');
