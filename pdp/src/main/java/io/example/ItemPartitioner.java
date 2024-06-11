package io.example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ItemPartitioner<T> {

    private final List<List<T>> partitions;

    public ItemPartitioner(Collection<T> items) {
        if (items.size() < 1000) {
            this.partitions = new ArrayList<>(1);
            this.partitions.add(new ArrayList<>(items));
            return;
        }

        int availableProcessors = Runtime.getRuntime().availableProcessors();
        this.partitions = new ArrayList<>(availableProcessors);

        int partSize = (int) Math.ceil((double) items.size() / availableProcessors);

        List<T> itemList = new ArrayList<>(items);
        int start = 0, end = partSize;
        for (int part = 0; part < availableProcessors - 1; part++) {
            partitions.add(new ArrayList<>(itemList.subList(start, end)));
            start = end - 1;
            end += partSize;
        }

        if (start < items.size()) {
            partitions.add(new ArrayList<>(itemList.subList(start, items.size())));
        }
    }

    public List<List<T>> partitions() {
        return partitions;
    }
}
