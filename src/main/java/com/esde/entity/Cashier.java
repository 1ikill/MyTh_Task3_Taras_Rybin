package com.esde.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Cashier extends ReentrantLock{
    private static final Logger logger = LogManager.getLogger();

    private final int ID;

    public Cashier(int ID) {
        this.ID = ID;
    }

    public void serve(Customer customer) {
        try {
            logger.info("Cashier " + ID + " is serving customer " + customer.getId());
            TimeUnit.SECONDS.sleep(customer.getServiceTime());
            logger.info("Customer " + customer.getId() + " finished at cashier " + ID);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            unlock();
        }
    }
}
