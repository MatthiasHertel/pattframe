package org.blueberry.spaceinvaders1;

/**
 * Created by KK on 27.11.2016.
 */
public class Border {
    private static Border ourInstance = new Border();

    public static Border getInstance() {
        return ourInstance;
    }

    private Border() {
    }

    private int borderXstart;
    private int borderXend;
    private int borderYstart;
    private int borderYend;

    public void setBorder(int borderXstart, int borderYstart, int borderXend, int borderYend){
        this.borderXstart = borderXstart;
        this.borderXend = borderXend;
        this.borderYstart = borderYstart;
        this.borderYend = borderYend;
    }

    public int getXstart(){
        return borderXstart;
    }

    public int getYstart(){
        return borderYstart;
    }

    public int getXend(){
        return borderXend;
    }

    public int getYend(){
        return borderYend;
    }

}
