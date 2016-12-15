package org.blueberry.spaceinvaders.interfaces;

import org.blueberry.spaceinvaders.controller.ScreensController;

public interface ControlledScreen {

    //This method will allow the injection of the Parent ScreenPane
    public void setScreenParent(ScreensController screenPage);
}