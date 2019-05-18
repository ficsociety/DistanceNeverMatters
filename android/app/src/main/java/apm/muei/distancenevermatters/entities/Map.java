package apm.muei.distancenevermatters.entities;

import java.io.Serializable;
import java.net.URL;

public class Map implements Serializable {

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

    @Override
    public String toString() {
        return "Map{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url=" + url +
                '}';
    }
}
