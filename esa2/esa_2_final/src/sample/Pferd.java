package sample;

public class Pferd {

  private Integer position = 0;

  
  public Pferd() {
  }

  public void setzeZug(int punktwert) {
      this.position += punktwert;
  }

    public int getPosition() {
        return this.position;
    }

}