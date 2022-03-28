package com.blackoutburst.wally.core.request;

import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.logger.LogLevel;

import javax.annotation.Nonnull;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class ConnectionManager {

    /**
     * Perform an URL request
     *
     * @param urlString the url that should be explored
     * @return the read result
     */
    public static String readFromURL(@Nonnull String urlString) {
        try {
            final URL url = new URL(urlString);
            final URLConnection connection = url.openConnection();
            final InputStream stream = connection.getInputStream();
            final BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            final StringBuilder builder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) builder.append(line).append(System.getProperty("line.separator"));

            stream.close();
            reader.close();

            return (builder.toString());
        } catch (Exception e) {
            Wally.getInstance().logger.log(LogLevel.ERROR, e.getMessage());
            return (null);
        }
    }
}
