package Utility;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.JSONArray;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class NetworkConnection {
    private static NetworkConnection ourInstance = new NetworkConnection();

    public static NetworkConnection getInstance() {
        return ourInstance;
    }

    private NetworkConnection() {

    }

    public String getResult(URL url) throws Exception{
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setConnectTimeout(5000);
        //con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        con.setRequestProperty("Accept", "application/json");

        con.setDoOutput(true);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();

        return content.toString();
    }


    public JSONArray getResultAsJSONArray(URL url) throws Exception {
        return new JSONArray(getResult(url));
    }



    public JsonElement getResultAsJSONElement(URL url) throws Exception{

        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setConnectTimeout(5000);
        //con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        con.setRequestProperty("Accept", "application/json");

        con.setDoOutput(true);

        JsonParser jp = new JsonParser(); //from gson
        JsonElement root = null; //Convert the input stream to a json element
        try {
            root = jp.parse(new InputStreamReader((InputStream) con.getContent()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }

    //https://www.openligadb.de/api/getmatchdata/bl1
}
