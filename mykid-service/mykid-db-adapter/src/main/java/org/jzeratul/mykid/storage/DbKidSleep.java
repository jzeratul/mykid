package org.jzeratul.mykid.storage;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DbKidSleep {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column(name = "CREATED_AT")
	OffsetDateTime createdAt;

  @Column(name = "USR_ID")
  private Long userid;
  
	@Column(name = "START_SLEEP")
	OffsetDateTime startSleep;

	@Column(name = "END_SLEEP")
	OffsetDateTime endSleep;

	public Long getId() {
		return id;
	}

	public DbKidSleep setId(Long id) {
		this.id = id;
		return this;
	}
  
  public Long userid() {
  	return userid;
  }
  
  public DbKidSleep userid(Long userid) {
  	this.userid = userid;
  	return this;
  }

	public OffsetDateTime getCreatedAt() {
		return createdAt;
	}

	public DbKidSleep setCreatedAt(OffsetDateTime createdAt) {
		this.createdAt = createdAt;
		return this;
	}

	public OffsetDateTime getStartSleep() {
		return startSleep;
	}

	public DbKidSleep setStartSleep(OffsetDateTime startSleep) {
		this.startSleep = startSleep;
		return this;
	}

	public OffsetDateTime getEndSleep() {
		return endSleep;
	}

	public DbKidSleep setEndSleep(OffsetDateTime endSleep) {
		this.endSleep = endSleep;
		return this;
	}

}
