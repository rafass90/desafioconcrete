package com.concrete.desafio.vo;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table
public class User implements Serializable{
	private static final long serialVersionUID = 1L;

	public User() {
	}
	
	@Id
	@Column(columnDefinition = "BINARY(16)", unique = true)
	private UUID id;
	
	@Column(length = 40)
	@NotNull(message="Nome não informado")
	private String name;
	
	@Column(length = 40, unique = true)
	@NotNull(message="E-mail não informado")
	private String email;
	
	@Column(length = 32)
	@NotNull(message="Senha não informada")
	private String password;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=true)
	@JsonManagedReference
	private List<Phone> phones;
	
	@Column(nullable = false)
	private Instant created;
	
	@Column(nullable = true)
	private Instant modified;
	
	@Column(nullable = true)	
	private Instant lastLogin;
	
	@Lob
	@Column(length=512)
	private String token;
	
	
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Phone> getPhones() {
		return phones;
	}

	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Instant getCreated() {
		return created;
	}

	public void setCreated(Instant created) {
		this.created = created;
	}

	public Instant getModified() {
		return modified;
	}

	public void setModified(Instant modified) {
		this.modified = modified;
	}

	public Instant getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Instant lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}