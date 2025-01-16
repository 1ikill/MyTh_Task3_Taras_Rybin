package com.esde.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cashier {
    private static final Logger logger = LogManager.getLogger();

    private final int id;
    private final Lock lock;

    public Cashier(int id) {
        this.id = id;
        this.lock = new ReentrantLock();
    }

    public boolean tryLock() {
        return lock.tryLock();
    }

    public void release() {
        lock.unlock();
    }

    public void serve(Customer customer) {
        try {
            logger.info("Cashier " + id + " is serving customer " + customer.getId());
            TimeUnit.SECONDS.sleep(customer.getServiceTime());
            logger.info("Customer " + customer.getId() + " finished at cashier " + id);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            release();
        }
    }
}
