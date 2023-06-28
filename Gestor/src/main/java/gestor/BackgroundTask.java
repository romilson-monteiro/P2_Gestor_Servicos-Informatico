/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestor;

/**
 *
 * @author vanildo9
 */
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BackgroundTask {
    private static final Executor executor = Executors.newFixedThreadPool(10);

    public static void runInBackground(Runnable task) {
        executor.execute(task);
    }
}

