package apm.muei.ficsociety.backend.domain.map;

import java.net.URL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Map {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(unique = true)
	private String name;
	private URL url;

	public Map() {
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
