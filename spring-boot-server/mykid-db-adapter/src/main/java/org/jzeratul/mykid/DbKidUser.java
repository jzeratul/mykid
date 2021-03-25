package org.jzeratul.mykid;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.OffsetDateTime;

@Entity
@Table
public class DbKidUser {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;

  private String password;

  private OffsetDateTime createdAt;

  public Long getId() {
    return id;
  }

  public DbKidUser setId(Long id) {
    this.id = id;
    return this;
  }

  public String getUsername() {
    return username;
  }

  public DbKidUser setUsername(String username) {
    this.username = username;
    return this;
  }

  public String getPassword() {
    return password;
  }

  public DbKidUser setPassword(String password) {
    this.password = password;
    return this;
  }

  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public DbKidUser setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }
}
