package stock.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import stock.Stock;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class StockStore {

    private Logger logger = LoggerFactory.getLogger(StockStore.class);

    private final AtomicLong counter = new AtomicLong();

    private Map<Long, StockEntity> store;

    public StockStore() {
        this.store = new TreeMap<>();
    }

    public Long insert(Stock stock) {
        Long id = counter.incrementAndGet();
        store.put(id,
                  new StockEntity(stock.getName(),
                            stock.getCurrentPrice()));
        return id;
    }

    public void update(Long id, Stock stock) {
        if (store.containsKey(id)) {
            StockEntity stockEntity = store.get(id);
            store.put(id, new StockEntity(stockEntity.getName(),
                                    stock.getCurrentPrice()));
        }
    }

    public Optional<Stock> retrieve(Long id) {
        Stock retVal = null;
        if (store.containsKey(id)) {
            StockEntity stockEntity = store.get(id);
            retVal = new Stock(id,
                               stockEntity.getName(),
                               stockEntity.getCurrentPrice(),
                               stockEntity.getLastUpdated());
        }
        return Optional.ofNullable(retVal);
    }

    public List<Stock> retrieveAll() {
        return store.entrySet().stream()
                       .map(entry -> new Stock(entry.getKey(),
                                               entry.getValue().getName(),
                                               entry.getValue().getCurrentPrice(),
                                               entry.getValue().getLastUpdated()))
                .collect(Collectors.toList());
    }

}
