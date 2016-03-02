package jornaleiro.service;
import jornaleiro.dto.Elastic.SearchRoot;
import org.codehaus.jackson.map.ObjectMapper;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ElasticSearch {
    private static final String ELASTICSEARCH_HOST = "http://localhost:9200";

    private static ElasticSearch ourInstance = new ElasticSearch();

    public static ElasticSearch getInstance() {
        return ourInstance;
    }

    private ElasticSearch() {
    }

    private String getJson(String query)
    {
        String json = "{\"query\": { \"match_phrase\": { \"content\": \"" +
                query +  "\"" +
                " } }, \"sort\": { \"date\": { \"order\": \"desc\" } }, \"highlight\" : { " +
                " \"fields\" : { \"content\": {}  } }, \"fields\":[\"id\", \"session\", \"date\", \"highlight\"]  }";

        return json;
    }

    private SearchRoot readJsonData(HttpURLConnection conn) throws IOException {
        conn.connect();
        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "  + conn.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        StringBuilder str = new StringBuilder();

        String output;

        while ((output = br.readLine()) != null)
            str.append(output);

        SearchRoot result = new ObjectMapper().readValue(str.toString(), SearchRoot.class);

        conn.disconnect();

        return result;
    }

    private void setJson(HttpURLConnection conn, String json) throws IOException
    {
        if (json != null) {
            //set the content length of the body
            conn.setRequestProperty("Content-length", json.getBytes().length + "");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);

            //send the json as body of the request
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(json.getBytes("UTF-8"));
            outputStream.close();
        }
    }

    private HttpURLConnection getConnection(String query) throws IOException {
        URL url = new URL(ELASTICSEARCH_HOST + "/jornaleiro/_search?pretty");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setAllowUserInteraction(false);

        setJson(conn, getJson(query));
        return conn;
    }

    public SearchRoot search(String query)  throws IOException {
        return readJsonData(getConnection(query));
    }
}
