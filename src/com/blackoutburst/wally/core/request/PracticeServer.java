package com.blackoutburst.wally.core.request;

import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.config.ConfigManager;
import com.blackoutburst.wally.core.logger.LogLevel;

import javax.annotation.Nonnull;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class PracticeServer {

    /**
     * Get a player score from the Practice server
     *
     * @param uuid the player uuid
     * @return the player stats as string (JSON)
     */
    public static String getPlayerStats(@Nonnull String uuid) {
        try {
            final String ip = ConfigManager.getInstance().loadValue("settings", "practice server ip");
            final short port = Short.parseShort(ConfigManager.getInstance().loadValue("settings", "practice server api port"));
            final Socket socket = new Socket(ip, port);
            final InputStreamReader inputStream = new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8);
            final BufferedReader reader = new BufferedReader(inputStream);
            final OutputStream outputStream = socket.getOutputStream();

            outputStream.write((uuid + '\n').getBytes(StandardCharsets.UTF_8));

            final String dataReceived = reader.readLine();

            inputStream.close();
            outputStream.close();
            reader.close();

            Wally.getInstance().logger.log(LogLevel.INFO, "Practice server request sent for [" + uuid + "]");

            return (dataReceived);
        } catch (Exception e) {
            Wally.getInstance().logger.log(LogLevel.ERROR, e.getMessage());
            return (null);
        }
    }
}
