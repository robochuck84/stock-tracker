package stock.controller;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import stock.Stock;
import stock.controller.StockController;
import stock.dao.StockStore;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StockControllerTest {

    @Test
    public void testGetStocks() {
        // given
        Stock test = new Stock(1l, "test", 10.0, "now");

        StockStore store = mock(StockStore.class);
        when(store.retrieveAll()).thenReturn(ImmutableList.of(test));

        StockController controller = new StockController(store);

        Stock expected = new Stock(1l, "test", 10.0, "now");

        // when
        List<Stock> testStocks = controller.getStocks();

        // then
        assertThat(testStocks, contains(expected));
    }

    @Test
    public void testGetStock() {
        // given
        Stock test = new Stock(1l, "test", 10.0, "now");

        StockStore store = mock(StockStore.class);
        when(store.retrieve(eq(1l))).thenReturn(Optional.of(test));

        StockController controller = new StockController(store);

        Stock expected = new Stock(1l, "test", 10.0, "now");

        // when
        Stock testStock = controller.getStock(1l);

        // then
        assertThat(testStock, equalTo(expected));
    }

    @Test
    public void testUpdateStock() {
        // given
        Stock test = new Stock(1l, "test", 10.0, "now");

        StockStore store = mock(StockStore.class);

        StockController controller = new StockController(store);

        // when
        controller.updateStock(1l, test);

        // then
        verify(store).update(eq(1l), eq(test.getCurrentPrice()));
    }

    @Test
    public void testCreateStock() {
        // given
        Stock test = new Stock(1l, "test", 10.0, "now");

        StockStore store = mock(StockStore.class);
        when(store.insert(eq(test))).thenReturn(1l);

        StockController controller = new StockController(store);

        // when
        Long testId = controller.createStock(test);

        // then
        assertThat(testId, equalTo(1l));
    }
}
