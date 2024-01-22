package model;

//this class only has 1 function and its job is to start multiple instances of the multithread function
//and then compares the outputs to eachother and keeps the one that matches minmax.
public class MultithreadingStarter {
    @SuppressWarnings("methodlength")
    //REQUIRES: whoseMove == 'W' || whoseMove == 'B'
    //EFFECTS: returns the gameState with the next good move for whoseMove
    public static GameState split(GameState given, char whoseMove) {

        GameState newState1 = GameState.coppyState(given);
        GameState newState2 = GameState.coppyState(given);
        GameState newState3 = GameState.coppyState(given);
        GameState newState4 = GameState.coppyState(given);

        MultithreadedFunction class1 = new MultithreadedFunction(newState1, whoseMove, 1);
        MultithreadedFunction class2 = new MultithreadedFunction(newState2, whoseMove, 2);
        MultithreadedFunction class3 = new MultithreadedFunction(newState3, whoseMove, 3);
        MultithreadedFunction class4 = new MultithreadedFunction(newState4, whoseMove, 4);

        Thread myThread1 = new Thread(class1);
        Thread myThread2 = new Thread(class2);
        Thread myThread3 = new Thread(class3);
        Thread myThread4 = new Thread(class4);
        if (given.board.length() == 128) {
            myThread1.start();
            myThread2.start();
            myThread3.start();
            myThread4.start();
        }


        try {
            if (given.board.length() != 128) {
                throw new Exception("Exception message");
            }
            myThread1.join();
            myThread2.join();
            myThread3.join();
            myThread4.join();
        } catch (Exception e) {
            System.out.println("error");
            return new GameState();
        }
        GameState returning = class1.getOutput();
        if ((class2.getOutput().returnValue > returning.returnValue && whoseMove == 'W')
                || (class2.getOutput().returnValue < returning.returnValue && whoseMove == 'B')) {
            returning = GameState.coppyState(class2.getOutput());
        }
        if ((class3.getOutput().returnValue > returning.returnValue && whoseMove == 'W')
                || (class3.getOutput().returnValue < returning.returnValue && whoseMove == 'B')) {
            returning = GameState.coppyState(class3.getOutput());
        }
        if ((class4.getOutput().returnValue > returning.returnValue && whoseMove == 'W')
                || (class4.getOutput().returnValue < returning.returnValue && whoseMove == 'B')) {
            returning = GameState.coppyState(class4.getOutput());
        }
        return returning;

    }
}
