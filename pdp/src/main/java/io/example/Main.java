package io.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

import static io.example.csv.CSVParser.parallelCSVParser;
import static io.example.csv.CSVParser.simpleCSVParser;

public class Main {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
//
//        CompletableFuture<Collection<Employee>> csvParallel = CompletableFuture.supplyAsync(
//                () -> parallelCSVParser("customers-100000.csv", nameParser()).values());
//
//        CompletableFuture<Collection<Employee>> csvSimple = CompletableFuture.supplyAsync(
//                () -> simpleCSVParser("customers-100000.csv", nameParser()).values());
//
//        EmployeeRepository repository = new EmployeeRepository();
//
//        CompletableFuture.allOf(csvParallel, csvSimple)
//                .thenRun(() -> {
//                    try {
//                        var employeesParallel = csvParallel.get();
//
//                        Collection<Employee> batch = new LinkedList<>();
//                        for (Employee employee : employeesParallel) {
//                            if (batch.size() == 25) {
//                                repository.add(batch);
//                                batch.clear();
//                            }
//                            batch.add(employee);
//                        }
//                    } catch (InterruptedException | ExecutionException e) {
//                        throw new RuntimeException(e);
//                    }
//                })
//                .get();

        // Example usage:
        List<Integer> items = new ArrayList<>();
        for (int i = 1; i <= 2000; i++) {
            items.add(i);
        }

        ItemPartitioner<Integer> partitioner = new ItemPartitioner<>(items);
        List<List<Integer>> partitions = partitioner.partitions();

        for (List<Integer> partition : partitions) {
            System.out.println(partition);
        }
    }

    static Function<String[], Employee> nameParser() {
        return row -> new Employee(null, row[2], row[3]);
    }
}
