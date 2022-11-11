package restaurant;
/**
 The restaurant now gives information for one month on a random basis.
 This s the reason why a new Enum - CalendarType is created, it supports
 random chosen month and year between 2022 and 2027 - done by Ana Popova
 */
public enum CalendarType
{
  JANUARY("Януари", 31, 2022),
  FEBRUARY("Февруари", 28, 2022),
  MARCH("Март", 31, 2022),
  APRIL("Април", 30, 2022),
  MAY("Май", 31, 2022),
  JUNE("Юни", 30, 2022),
  JULY("Юли", 31, 2022),
  AUGUST("Август", 31, 2022),
  SEPTEMBER("Септември", 30, 2022),
  OCTOBER("Октомври", 31, 2022),
  NOVEMBER("Ноември", 30, 2022),
  DECEMBER("Декември", 31, 2022);

  private int numberOfDays;
  private int year;

  private String name;

  CalendarType(String name, int numberOfDays, int year)
  {
    this.name = name;
    setYear(year);
    setNumberOfDays(numberOfDays);

  }

  public int getNumberOfDays()
  {
    return numberOfDays;
  }

  public void setNumberOfDays(int numberOfDays)
  {
    this.numberOfDays = numberOfDays;
    if (numberOfDays == 28) {
      if (year % 4 == 0) {
        this.numberOfDays = 29;
      }
    }
  }

  public int getYear()
  {
    return year;
  }

  public void setYear(int year)
  {
    this.year = year;
    setNumberOfDays(numberOfDays);
  }

  public String getName()
  {
    return name;
  }

  @Override
  public String toString()
  {
    return super.toString().toLowerCase();
  }

}

