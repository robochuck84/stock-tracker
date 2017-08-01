package stock.dao;

import org.springframework.stereotype.Repository;
import stock.Stock;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class StockStore {

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
        StockEntity stockEntity = new StockEntity(stock.getName(), stock.getCurrentPrice());

        StockEntity previous = nameIndex.putIfAbsent(stock.getName(), stockEntity);
        if (previous != null) {
            throw new IllegalArgumentException("Name already exists");
        }

        Long id = counter.incrementAndGet();
        store.put(id, stockEntity);

        return id;
    }

    /**
     * Update an existing stock items price.
     *
     * @param id - id of the stock to update
     * @param price - price
     */
    public void update(Long id, Double price) {
        if (store.containsKey(id)) {
            StockEntity stockEntity = store.get(id);
            store.put(id, new StockEntity(stockEntity.getName(),
                                          price));
        }
    }

    /**
     * Retrieve the stock assigned to the ID, if no stock is mapped to the supplied key,
     * return an empty optional.
     *
     * @param id - id of the stock to retrieve
     * @return Stock if present
     */
    public Optional<Stock> retrieve(Long id) {
        Stock retVal = null;
        if (store.containsKey(id)) {
            StockEntity stockEntity = store.get(id);
            retVal = convert(id, stockEntity);
        }
        return Optional.ofNullable(retVal);
    }

    /**
     * Return all stocks
     *
     * @return all stocks
     */
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
