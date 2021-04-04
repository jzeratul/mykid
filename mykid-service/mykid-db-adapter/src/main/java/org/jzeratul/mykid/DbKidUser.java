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

  private String email;

  private String password;

  private OffsetDateTime createdAt;

  public Long id() {
    return id;
  }

  public DbKidUser id(Long id) {
    this.id = id;
    return this;
  }

  public String username() {
    return username;
  }

  public DbKidUser username(String username) {
    this.username = username;
    return this;
  }

  public String password() {
    return password;
  }

  public DbKidUser password(String password) {
    this.password = password;
    return this;
  }

  public OffsetDateTime createdAt() {
    return createdAt;
  }

  public DbKidUser createdAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  public String getEmail() {
    return email;
  }

  public DbKidUser setEmail(String email) {
    this.email = email;
    return this;
  }
}
