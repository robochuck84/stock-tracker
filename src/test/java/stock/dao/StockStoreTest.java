package stock.dao;

import org.junit.Test;
import stock.Stock;

import java.time.Instant;
import java.util.List;

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
        Stock test = store.retrieve(id);

        // then
        assertThat(test, equalTo(new Stock(1l, "test", 10.0, now)));
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

        Long id = 1l;
        Instant now = Instant.now();
        Stock stock = new Stock(id, "test", 10.0, now);

        // when
        Instant updated = Instant.now();
        store.update(id, new Stock(id, "test", 12.0, updated));
        Stock test = store.retrieve(id);

        // then
        assertThat(test, equalTo(new Stock(1l, "test", 12.0, updated)));
    }
}
