package stock.dao;

import org.junit.Test;
import stock.Stock;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class StockStoreTest {

    @Test
    public void testInsertAndRetrieveStock() {
        // given
        StockStore store = new StockStore();

        Stock stock = new Stock(1l, "test", 10.0, null);

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

        Stock stock1 = new Stock(1l, "test1", 1.0, null);
        Stock stock2 = new Stock(2l, "test2", 2.0, null);

        // when
        store.insert(stock1);
        store.insert(stock2);

        List<Stock> all = store.retrieveAll();

        // then
        assertThat(all, hasSize(2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDoubleInsertionOfTheSameName() {
        // given
        StockStore store = new StockStore();

        Stock stock1 = new Stock(1l, "test", 1.0, null);
        Stock stock2 = new Stock(2l, "test", 2.0, null);

        // when
        store.insert(stock1);
        store.insert(stock2);

        // then
        fail();
    }

    @Test
    public void testUpdate() {
        // given
        StockStore store = new StockStore();

        Stock stock = new Stock("test", 10.0);

        // when
        Long id = store.insert(stock);
        store.update(id, 12.0);
        Optional<Stock> test = store.retrieve(id);

        // then
        assertThat(test.get().getName(), equalTo("test"));
        assertThat(test.get().getCurrentPrice(), equalTo(12.0));
    }
}
