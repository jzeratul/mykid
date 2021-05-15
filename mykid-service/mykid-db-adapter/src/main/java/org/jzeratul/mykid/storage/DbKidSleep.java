package org.jzeratul.mykid.storage;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sleep")
public class DbKidSleep {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column(name = "CREATED_AT")
	LocalDateTime createdAt;

  @Column(name = "USR_ID")
  private Long userid;
  
	@Column(name = "START_SLEEP")
	LocalDateTime startSleep;

	@Column(name = "END_SLEEP")
	LocalDateTime endSleep;

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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public DbKidSleep setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
		return this;
	}

	public LocalDateTime getStartSleep() {
		return startSleep;
	}

	public DbKidSleep setStartSleep(LocalDateTime startSleep) {
		this.startSleep = startSleep;
		return this;
	}

	public LocalDateTime getEndSleep() {
		return endSleep;
	}

	public DbKidSleep setEndSleep(LocalDateTime endSleep) {
		this.endSleep = endSleep;
		return this;
	}

}
