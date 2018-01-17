package com.zensar.uac.web.crawler.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by srikant.singh on 10/26/2016.
 */

/**
 * Created by Sagar Balai on 10/16/2016.
 * Purpose of the class: Use to create thread pool
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */

public final class ThreadPool {

    private static ExecutorService es = Executors.newFixedThreadPool(10);

    //Added a private constructor as this class contains only static methods so no need to instantiate this class.
    private ThreadPool() {
    }

    /**
     * Returns the thread pool executor with 10 threads.
     *
     * @return  the thread pool executor
     */
    public static ExecutorService getThreadPool() {
        return es;
    }
}
