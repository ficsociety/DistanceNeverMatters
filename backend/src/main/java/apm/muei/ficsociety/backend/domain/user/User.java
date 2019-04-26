package apm.muei.ficsociety.backend.domain.user;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {

	@Id
	private String uid;

	public User() {
	}

	public User(String uid) {
		this.uid = uid;
	}

	public String getUid() {
		return uid;
	}
}
