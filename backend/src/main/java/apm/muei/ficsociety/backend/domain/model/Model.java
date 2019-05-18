package apm.muei.ficsociety.backend.domain.model;

import java.net.URL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Model {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(unique = true)
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

	public long getId() {
		return id;
	}

}
