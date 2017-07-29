package stock.dao;

import org.springframework.stereotype.Repository;
import stock.Stock;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class StockStore {

    private final AtomicLong counter = new AtomicLong();

    private Map<Long, Stock> store;

    public StockStore() {
        this.store = new TreeMap<>();
    }

    public Long insert(Stock stock) {
        Long id = counter.incrementAndGet();
        store.put(stock.getId(),
                new Stock(id,
                          stock.getName(),
                          stock.getCurrentPrice(),
                          stock.getLastUpdated()));
        return id;
    }

    public void update(Long id, Stock stock) {
        store.put(id, stock);
    }

    public Stock retrieve(Long id) {
        return store.get(id);
    }

    public List<Stock> retrieveAll() {
        return store.values().stream().collect(Collectors.toList());
    }

}
