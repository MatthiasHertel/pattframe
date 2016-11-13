package org.blueberry.kentuckyderby;

import javafx.application.Application;
import javafx.stage.Stage;

public class KentuckyDerby extends Application {

    private UserInterface userInterface = new UserInterface();

    @Override
    public void init() throws Exception {
        super.init();

        Configuration config = new Configuration();
        if (config.load()) {
            userInterface.construct(config);
        }
        else {
            config.printError(System.err);
            System.exit(1);
        }
    }

    @Override
    public void start(Stage primaryStage) {
        userInterface.assemble(primaryStage);
    }

    public static void main(String args[]) {
        KentuckyDerby.launch(args);
    }
}
