package stock.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import stock.Stock;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class StockStore {

    private Logger logger = LoggerFactory.getLogger(StockStore.class);

    private final AtomicLong counter = new AtomicLong();

    private Map<Long, Stock> store;

    public StockStore() {
        this.store = new TreeMap<>();
    }

    public Long insert(Stock stock) {
        Long id = counter.incrementAndGet();
        store.put(id,
                  new Stock(stock.getName(),
                            stock.getCurrentPrice()));
        return id;
    }

    public void update(Long id, Stock stock) {
        if (store.containsKey(id)) {
            store.put(id, new Stock(stock.getName(),
                                    stock.getCurrentPrice()));
        }
    }

    public Stock retrieve(Long id) {
        return store.get(id);
    }

    public List<Stock> retrieveAll() {
        return store.values().stream().collect(Collectors.toList());
    }

}
