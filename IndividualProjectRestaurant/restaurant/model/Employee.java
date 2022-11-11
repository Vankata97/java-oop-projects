package restaurant.model;

import java.math.BigDecimal;

public class Employee {

  private String name;
  private BigDecimal dailySalary;
  private JobTitle jobTitle;
  private BigDecimal tips;
  private BigDecimal weeklyIncome;

  public Employee(String name, BigDecimal salary, JobTitle jobTitle) {
    this.name = name;
    this.dailySalary = salary;
    this.jobTitle = jobTitle;
    this.tips = BigDecimal.ZERO;
    this.weeklyIncome = BigDecimal.ZERO;
  }

  public BigDecimal getWeeklyIncome() {
    return weeklyIncome;
  }

  public void setWeeklyIncome(BigDecimal weeklyIncome) {
    this.weeklyIncome = weeklyIncome;
  }

  public BigDecimal getTips() {
    return tips;
  }

  public void setTips(BigDecimal tips) {
    this.tips = tips;
  }

  public JobTitle getJobTitle() {
    return jobTitle;
  }

  public void setJobTitle(JobTitle jobTitle) {
    this.jobTitle = jobTitle;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigDecimal getDailySalary() {
    return dailySalary;
  }

  public void setDailySalary(BigDecimal dailySalary) {
    this.dailySalary = dailySalary;
  }

  public void giveTip(BigDecimal tip) {
    setTips(getTips().add(tip));
  }
}
