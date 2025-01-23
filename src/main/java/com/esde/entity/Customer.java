package com.esde.entity;

import com.esde.manager.CashierManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class Customer implements Runnable {
    private static final Logger logger = LogManager.getLogger();

    private final int id;
    private final int serviceTime; // Time needed at the cashier

    public Customer(int id, int serviceTime) {
        this.id = id;
        this.serviceTime = serviceTime;
    }

    public int getId() {
        return id;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    @Override
    public void run() {
        while (true) {
            Cashier cashier = CashierManager.getInstance(0).getAvailableCashier();
            if (cashier != null) {
                cashier.serve(this);
                break;
            } else {
                logger.info("Customer " + id + " is waiting for an available cashier.");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    logger.error("Error while serving", e);
                }
            }
        }
    }
}
