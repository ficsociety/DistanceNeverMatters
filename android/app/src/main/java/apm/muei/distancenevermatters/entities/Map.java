package apm.muei.distancenevermatters.entities;

import java.net.URL;

public class Map {

    private long id;
    private String name;
    private URL url;

    public Map(){
    }

    public Map(String name, URL url) {
        this.name = name;
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public URL getUrl() {
        return url;
    }

}
