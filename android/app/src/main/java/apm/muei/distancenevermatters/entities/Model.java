package apm.muei.distancenevermatters.entities;

import java.net.URL;

public class Model {

    private long id;
    private String name;
    private URL url;
    private URL preview;

    public Model() {
    }

    public Model(String name, URL url, URL preview) {
        this.name = name;
        this.url = url;
        this.preview = preview;
    }

    public String getName() {
        return name;
    }

    public URL getUrl() {
        return url;
    }

    public URL getPreview() {
        return preview;
    }

}
