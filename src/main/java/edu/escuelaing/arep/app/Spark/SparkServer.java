package edu.escuelaing.arep.app.Spark;


import static spark.Spark.*;
import edu.escuelaing.arep.app.LoadBalancer.LoadBalancer;

public class SparkServer {
    private static LoadBalancer loadBalancer = new LoadBalancer();

    public static void main(String[] args) {
        port(getPort());
        staticFileLocation("/public");
        get("/messages", (request, response) -> {
            loadBalancer.loadbalancer();
            return loadBalancer.get("/messages");
        });
        post("/add", (req , resp) -> {
            loadBalancer.loadbalancer();
            loadBalancer.post(req.body(), "/send");
            return "{\"message\": \"ACCEPT\" }";
        });
    }

    public static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567; //returns default port if heroku-port isn't set (i.e. on localhost)
    }
}
