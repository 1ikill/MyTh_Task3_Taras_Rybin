package com.esde.manager;

import com.esde.entity.Cashier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CashierManager {
    private static final Lock lock = new ReentrantLock();
    private static CashierManager instance;

    private final List<Cashier> cashiers;

    private CashierManager(int numberOfCashiers) {
        cashiers = new ArrayList<>();
        for (int i = 0; i < numberOfCashiers; i++) {
            cashiers.add(new Cashier(i + 1));
        }
    }

    public static CashierManager getInstance(int numberOfCashiers) {
        if (instance == null) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new CashierManager(numberOfCashiers);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public Cashier getAvailableCashier() {
        for (Cashier cashier : cashiers) {
            if (cashier.tryLock()) {
                return cashier;
            }
        }
        return null;
    }
}
