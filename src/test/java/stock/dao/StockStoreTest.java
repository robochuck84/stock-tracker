package stock.dao;

import org.junit.Test;
import stock.Stock;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class StockStoreTest {

    @Test
    public void testInsertAndRetrieveStock() {
        // given
        StockStore store = new StockStore();

        Instant now = Instant.now();
        Stock stock = new Stock(1l, "test", 10.0, now);

        // when
        Long id = store.insert(stock);
        Optional<Stock> test = store.retrieve(id);

        // then
        assertThat(test.get().getName(), equalTo("test"));
        assertThat(test.get().getCurrentPrice(), equalTo(10.0));
    }

    @Test
    public void testRetrieveAll() {
        // given
        StockStore store = new StockStore();

        Stock stock1 = new Stock(1l, "test1", 1.0, Instant.now());
        Stock stock2 = new Stock(2l, "test2", 2.0, Instant.now());

        // when
        store.insert(stock1);
        store.insert(stock2);

        List<Stock> all = store.retrieveAll();

        // then
        assertThat(all, contains(stock1, stock2));
    }

    @Test
    public void testUpdate() {
        // given
        StockStore store = new StockStore();

        Stock stock = new Stock("test", 10.0);

        // when
        Long id = store.insert(stock);
        store.update(id, new Stock("test", 12.0));
        Optional<Stock> test = store.retrieve(id);

        // then
        assertThat(test.get().getName(), equalTo("test"));
        assertThat(test.get().getCurrentPrice(), equalTo(12.0));
    }
}
