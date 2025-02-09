package com.esde.reader;

import com.esde.entity.Customer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
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
        URL filePath = InputReader.class.getResource(fileName);
        if (filePath == null){
            logger.error("Invalid file path");
            throw new RuntimeException("Invalid file path");
        }

        try (Stream<String> lines = Files.lines(Paths.get(filePath.toURI()))) {
            lines.forEach(line ->{
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0].trim());
                int serviceTime = Integer.parseInt(parts[1].trim());
                customers.add(new Customer(id, serviceTime));
            });
        } catch (IOException | URISyntaxException e) {
            logger.error(e);
        }

        return customers;
    }
}
