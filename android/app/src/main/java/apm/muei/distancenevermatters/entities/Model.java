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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Model)) return false;

        Model model = (Model) o;

        if (id != model.id) return false;
        if (name != null ? !name.equals(model.name) : model.name != null) return false;
        if (url != null ? !url.equals(model.url) : model.url != null) return false;
        return preview != null ? preview.equals(model.preview) : model.preview == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (preview != null ? preview.hashCode() : 0);
        return result;
    }
}
