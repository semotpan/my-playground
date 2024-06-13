package io.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

import static io.example.csv.CSVParser.parallelCSVParser;

public class Main {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
//
//        CompletableFuture<Collection<Employee>> csvParallel = CompletableFuture.supplyAsync(
//                () -> parallelCSVParser("customers-50000.csv", nameParser()).values());
//
//
        EmployeeRepository repository = new EmployeeRepository();
        try (ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())) {
            CompletableFuture.supplyAsync(
                            () -> parallelCSVParser("customers-50000.csv", nameParser()).values())
                    .thenAccept(employees -> {
                        ItemPartitioner<Employee> itemPartitioner = new ItemPartitioner<>(employees);
                        for (int i = 0; i < itemPartitioner.partitions().size(); i++) {
                            executorService.submit(new BatchTask<>(
                                    itemPartitioner.partition(i),
                                    i,
                                    repository::add,
                                    25
                            ));
                        }

                    })
                    .get();
        }
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

//        samplePartitioning();
    }

    private static void samplePartitioning() {
        // Example usage:
        List<Integer> items = new ArrayList<>();
        for (int i = 1; i <= 1954; i++) {
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
