package com.esde.main;

import com.esde.entity.Customer;
import com.esde.manager.CashierManager;
import com.esde.reader.InputReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        try {
            List<Customer> customers = InputReader.readCustomersFromFile("/customers.txt");
            CashierManager cashierManager = CashierManager.getInstance(3); // 3 кассы

            ExecutorService executorService = Executors.newFixedThreadPool(customers.size());
            for (Customer customer : customers) {
                executorService.submit(customer);
            }

            executorService.shutdown();
            executorService.awaitTermination(1, TimeUnit.MINUTES);

            logger.info("Simulation finished.");
        } catch (IOException | InterruptedException e) {
            logger.error("Error during simulation: ", e);
        }
    }
}