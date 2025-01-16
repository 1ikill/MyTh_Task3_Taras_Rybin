package com.esde.reader;

import com.esde.entity.Customer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class InputReader {
    private static final Logger logger = LogManager.getLogger();
    public static List<Customer> readCustomersFromFile(String fileName) throws IOException {
        List<Customer> customers = new ArrayList<>();

        try (Stream<String> lines = Files.lines(Paths.get(InputReader.class.getResource(fileName).toURI()))) {
            lines.forEach(line ->{
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0].trim());
                int serviceTime = Integer.parseInt(parts[1].trim());
                customers.add(new Customer(id, serviceTime));
            });
        } catch (IOException | URISyntaxException | NullPointerException e) {
            logger.error(e);
        }

        return customers;
    }
}
