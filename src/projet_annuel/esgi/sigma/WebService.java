package projet_annuel.esgi.sigma;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class WebService {
    public static final String SERVER_URL = "http://sigma.fabien-cote.fr/";
    public static final String LOGIN_URI = "api/auth/login";
    public static final String LOGOUT_URI = "api/auth/logout?token={token}";
    public static final String USER_LIST = "api/user?token={token}";
    public static final String STORE_PROJECT_URI = "api/project?token={token}";
    public static final String SYNC_USER_ACCESS_PROJECT_URI = "api/project/{id}/user?token={token}";
    public static final String USER_PROJECT_LIST_URI = "api/project/user/manager?token={token}";
    public static final String PROJECT_TASK_LIST_URI = "api/project/{id}/task?token={token}";
    public static final String PROJECT_VERSION_LIST_URI = "api/project/{id}/version?token={token}";
    public static final String PROJECT_USER_LIST_URI = "api/project/{id}/user?token={token}";
    public static final String PROJECT_GROUP_LIST = "api/project-group?token={token}";
    public static final String STORE_TASK_URI = "api/task?token={token}";
    public static final String TASK_URI = "api/task/{id}?token={token}";
    public static final String STORE_VERSION_URI = "api/version?token={token}";
    public static final String VERSION_URI = "api/version/{id}?token={token}";

    public static final String GET_METHOD = "GET";
    public static final String POST_METHOD = "POST";
    public static final String PUT_METHOD = "PUT";
    public static final String DELETE_METHOD = "DELETE";

    public WebService() {}

    public URL getFullUrlWithUriAndParams(String uri, HashMap<String, String> getParams) throws MalformedURLException {
        String url = SERVER_URL.concat(uri);

        if(getParams == null)
            return new URL(url);

        for(Map.Entry<String, String> param : getParams.entrySet())
            url = url.replace("{" + param.getKey() + "}", param.getValue());

        return new URL(url);
    }

    public JSONObject call(String method, String uri, HashMap<String, String> getParams,
                           HashMap<String, Object> postParams) {
        JSONObject result = new JSONObject();

        try {
            URL url = getFullUrlWithUriAndParams(uri, getParams);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

            if(method.equals(POST_METHOD) || method.equals(PUT_METHOD)) {
                conn.setDoOutput(true);
                conn.getOutputStream().write(getPostDataBytes(postParams));
            }

            if (conn.getResponseCode() >= 500)
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());

            BufferedReader br;
            String jsonResponse;
            if(conn.getResponseCode() >= 400) {
                br = new BufferedReader(new InputStreamReader((conn.getErrorStream())));
                jsonResponse =  br.lines().collect(Collectors.joining(System.lineSeparator()));
            } else {
                br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                jsonResponse =  br.lines().collect(Collectors.joining(System.lineSeparator()));
            }

            result = new JSONObject(jsonResponse);

            conn.disconnect();

            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    private byte[] getPostDataBytes(HashMap<String, Object> postParams) throws UnsupportedEncodingException {
        StringBuilder postData = new StringBuilder();

        for (Map.Entry<String, Object> param : postParams.entrySet()) {
            if (postData.length() != 0)
                postData.append('&');

            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }

        return postData.toString().getBytes("UTF-8");
    }
}