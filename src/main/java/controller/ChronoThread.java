package main.java.controller;
/**
 * Classe ChronoThread for timer
 *
 * @author : Gilles Andrieu [Atecna](http://www.atecna.fr/)
 * @version : 1.0
 */

import javafx.application.Platform;
import javafx.scene.control.Label;

public class ChronoThread implements Runnable {
    private Thread running;
    private Label chrono;
    private long endChrono, startTime, breakTime;
    private boolean continueThread, finishThread;


    /**
     * Constructor
     */
    public ChronoThread(){
        this.running = null;
        this.chrono = null;
        this.endChrono = 0;
        this.startTime = 0;
        this.breakTime = 0;
        this.continueThread = false;
        this.finishThread = false;
    }

    /**
     * Start timer
     * @param actionChrono
     * @throws InterruptedException
     */
    public void startThread(Label actionChrono) throws InterruptedException {
        // If thread is started, we stop it
        if (operating())
            stopThread();

        // Create thread and start it
        running = new Thread(this);
        running.start();
        finishThread = false;
        chrono = actionChrono;
    }

    /**
     * Break thread
     */
    public void breakThread() {
        if (operating()  && continueThread) {
            breakTime = System.currentTimeMillis();
            continueThread = false;
        }
    }

    /**
     * Restart thread
     */
    public synchronized  void resume() {
        if (operating() && !continueThread) {
            startTime +=  System.currentTimeMillis() - breakTime;
            continueThread = true;
            notifyAll();

        }
    }

    /**
     * Stop Thread
     */
    public synchronized void stopThread() {

        if (operating()) {
            finishThread = true;
            running.interrupt();
            notifyAll();
        }
    }

    /**
     * Override class run
     */
    @Override
    public void run() {
        running.setPriority(Thread.MIN_PRIORITY);
        continueThread = true;
        startTime = System.currentTimeMillis();

        // while thread is run
        while(!finishThread)
        {
            endChrono = System.currentTimeMillis() - startTime;

            try {
                running.sleep(1000);
                // update label timer
                Platform.runLater(() -> {
                    chrono.setText(show());
                });

                synchronized(this) {
                    while (!continueThread && !finishThread) wait();
                }
            }
            catch(InterruptedException e){
                // if an error, we stop thread
                running.interrupt();
                e.printStackTrace();
            }
        }
    }

    /**
     * Try if the thread is started
     * @return
     */
    public boolean operating() {

        return (running!=null) && (running.isAlive());
    }

    /**
     * Show timer
     * @return
     */
    public String show() {
        int duration = (int) endChrono/1000;
        int hour =  (duration / 3600);
        int minute =  ((duration % 3600) / 60);
        int second =  (duration % 60);

        String resultTime= hour + ":" + ((minute < 10)?"0"+minute:minute) + ":" + ((second < 10)?"0"+second:second);
        return resultTime;
    }

}
