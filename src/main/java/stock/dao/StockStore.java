package stock.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import stock.Stock;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class StockStore {

    private Logger logger = LoggerFactory.getLogger(StockStore.class);

    private final AtomicLong counter = new AtomicLong();

    private Map<Long, StockEntity> store;
    private ConcurrentMap<String, StockEntity> nameIndex;

    public StockStore() {
        this.store = new HashMap<>();
        this.nameIndex = new ConcurrentHashMap<>();
    }

    /**
     * Insert a new stock into the store, names are considered unique.
     *
     * @param stock - to be inserted
     * @return - new ID of the inserted stock
     * @throws IllegalArgumentException - if the name already appears in the store
     */
    public Long insert(Stock stock) throws IllegalArgumentException {
        if (nameIndex.get(stock.getName()) != null) {
            throw new IllegalArgumentException("Name already exists");
        }

        Long id = counter.incrementAndGet();

        // Concurrent Map can potentially call the mapping function multiple times,
        // its best to leave the ID generation outside, otherwise you may get duplicates
        // in the store.
        nameIndex.computeIfAbsent(stock.getName(), s -> {
            StockEntity stockEntity = new StockEntity(stock.getName(),
                                                      stock.getCurrentPrice());
            store.put(id, stockEntity);
            return stockEntity;
        });

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
            retVal = convert(id, stockEntity);
        }
        return Optional.ofNullable(retVal);
    }

    public List<Stock> retrieveAll() {
        return store.entrySet().stream()
                       .map(entry -> convert(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    private Stock convert(Long id, StockEntity stockEntity) {
        return new Stock(id,
                         stockEntity.getName(),
                         stockEntity.getCurrentPrice(),
                         stockEntity.getLastUpdated().toString());
    }

}
