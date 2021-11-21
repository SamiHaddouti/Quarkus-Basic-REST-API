package org.exercise2;

/**
 * @Job object that contains information about job type, required experience level, provided salary
 * and a unique id
 */

public class Job {

  private Long id;
  private String type;
  private String experience;
  private int salary;

  // Setter and Getter for attributes
  public Long getId() {
    return id;
  }

  public String getType() {
    return type;
  }

  public void setType(final String type) {
    this.type = type;
  }

  public String getExperience() {
    return experience;
  }

  public void setExperience(final String experience) {
    this.experience = experience;
  }

  public void setSalary(final int salary) {
    this.salary = salary;
  }
}
