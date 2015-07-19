package projet_annuel.esgi.sigma;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Jordan on 18/07/2015.
 */
public class WebService {

    public WebService() {}

    public JSONObject connexion(String email, StringBuilder password) throws JSONException, IOException {
        JSONObject result;

        URL url = new URL("http://sigma.fabien-cote.fr/api/auth/login");
        URLConnection conn = url.openConnection();

        conn.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

        writer.write("email=" + email + "&password=" + password);
        writer.flush();
        String output;
        StringBuffer str = new StringBuffer();
        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            str.append(line);
        }
        System.out.println(str);

        String strObject = String.valueOf(str);

        result = new JSONObject(strObject);
        System.out.println(result);

        writer.close();
        reader.close();

        return result;
    }

    public JSONObject UserProjectList(String token) throws JSONException, IOException {
        JSONObject obj;
        try {
            URL project = new URL("http://sigma.fabien-cote.fr/api/project/user/manager?token=" + token);
            HttpURLConnection conn = (HttpURLConnection) project.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output;
            StringBuffer str = new StringBuffer();
            while ((output = br.readLine()) != null) {
                str.append(output);
            }
            String strObject = String.valueOf(str);

            obj = new JSONObject(strObject);

            conn.disconnect();
            return obj;
        } catch (MalformedURLException ex) {
            Logger.getLogger(WebService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(WebService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(WebService.class.getName()).log(Level.SEVERE, null, ex);
        }

        obj = new JSONObject();

        return obj;
    }

    public JSONObject ShowProject(String token, String id) throws JSONException, IOException {
        JSONObject obj;
        try {
            URL project = new URL("http://private-anon-17b101c44-sigma.apiary-mock.com/api/project/" + id + "?token=" + token + "");
            HttpURLConnection conn = (HttpURLConnection) project.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            String output;
            StringBuffer str = new StringBuffer();
            while ((output = br.readLine()) != null) {
                str.append(output);
            }
            String tempStr = str.substring(0, (str.indexOf("<!--")));

            obj = new JSONObject(tempStr);


            conn.disconnect();
            return obj;
        } catch (MalformedURLException ex) {
            Logger.getLogger(WebService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(WebService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(WebService.class.getName()).log(Level.SEVERE, null, ex);
        }


        obj = new JSONObject();

        return obj;

    }

    public void StoreVersion(String token) throws JSONException, IOException {
        JSONObject obj;
        URL url = new URL("http://sigma.fabien-cote.fr/api/version");
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

        writer.write("?token=" + token);
        writer.flush();
        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        writer.close();
        reader.close();


    }

    public JSONObject ProjectTaskList(String token, String projectId) throws JSONException, IOException {
        JSONObject obj;
        try {
            URL task = new URL("http://sigma.fabien-cote.fr/api/task/project/" + projectId + "?token=" + token + "");
            HttpURLConnection conn = (HttpURLConnection) task.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            String output;
            StringBuffer str = new StringBuffer();
            while ((output = br.readLine()) != null) {
                str.append(output);
            }
            String tempStr = str.substring(0, (str.indexOf("<!--")));

            conn.disconnect();
            return new JSONObject(tempStr);
        } catch (MalformedURLException ex) {
            Logger.getLogger(WebService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(WebService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(WebService.class.getName()).log(Level.SEVERE, null, ex);
        }

        obj = new JSONObject();

        return obj;
    }

    public JSONObject ShowTask(String token, String idtask) throws JSONException, IOException {
        JSONObject obj;
        try {
            URL task = new URL("http://private-anon-17b101c44-sigma.apiary-mock.com/api/task/" + idtask + "?token=" + token + "");
            HttpURLConnection conn = (HttpURLConnection) task.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            String output;
            StringBuffer str = new StringBuffer();
            while ((output = br.readLine()) != null) {
                str.append(output);
            }
            String tempStr = str.substring(0, (str.indexOf("<!--")));

            obj = new JSONObject(tempStr);


            conn.disconnect();
            return obj;
        } catch (MalformedURLException ex) {
            Logger.getLogger(WebService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(WebService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(WebService.class.getName()).log(Level.SEVERE, null, ex);
        }


        obj = new JSONObject();

        return obj;
    }


}
