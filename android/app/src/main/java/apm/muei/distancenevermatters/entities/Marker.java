package apm.muei.distancenevermatters.entities;

import java.net.URL;

public class Marker {

    private long id;
    private String name;
    private URL url;

    public Marker() {
    }

    public Marker(String name, URL url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public URL getUrl() {
        return url;
    }
}
