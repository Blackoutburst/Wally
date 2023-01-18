package com.blackoutburst.wally.core.api;

import com.blackoutburst.wally.main.Main;
import spark.Spark;

public class Core {

    private Core() {}

    public static void start() {
        Spark.port(20920);

        Spark.get("/user", (req, res) -> {
            final String token = req.queryParams("token");
            final String uuid = req.queryParams("uuid");

            if (token == null || !token.equals(Main.API_TOKEN)) {
                res.status(401);
                return "Invalid token";
            }

            if (uuid == null) {
                res.status(400);
                return "Missing parameter 'uuid'";
            }

            return JSONMaker.INSTANCE.createUserJson(uuid);
        });

        Spark.get("/users", (req, res) -> {
            final String token = req.queryParams("token");

            if (token == null || !token.equals(Main.API_TOKEN)) {
                res.status(401);
                return "Invalid token";
            }

            return JSONMaker.INSTANCE.createUserListJson();
        });
    }
}
