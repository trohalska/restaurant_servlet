package ua.servlet.restaurant.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonBody {

    /**
     * Get json string from request input stream
     * For registration controller (which is the only one, who takes json from request)
     * @param request request
     * @return String json
     * @throws IOException if cannot get string
     */
    public static String getBody(HttpServletRequest request) throws IOException {
        String body;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        InputStream inputStream = request.getInputStream();
        if (inputStream != null) {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            char[] charBuffer = new char[128];
            int bytesRead;
            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                stringBuilder.append(charBuffer, 0, bytesRead);
            }
        }
        if (bufferedReader != null) {
            bufferedReader.close();
        }
        body = stringBuilder.toString();
        return body;
    }
}
