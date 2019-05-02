package apm.muei.distancenevermatters.entities;

import java.net.URL;

public class Marker {

    private long id;
    private String name;
    private URL url;

    public Marker() {
    }

    public Marker(long id, String name, URL url) {
        this.id = id;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Marker)) return false;

        Marker marker = (Marker) o;

        if (id != marker.id) return false;
        if (name != null ? !name.equals(marker.name) : marker.name != null) return false;
        return url != null ? url.equals(marker.url) : marker.url == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }
}
