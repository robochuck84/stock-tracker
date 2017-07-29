package stock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import stock.Stock;
import stock.dao.StockStore;

import java.util.List;

@RestController("/api")
public class StockController {

    private StockStore store;

    @Autowired
    public StockController(StockStore store) {
        this.store = store;
    }

    @RequestMapping("/stocks")
    public List<Stock> getStocks() {
        return store.retrieveAll();
    }

    @RequestMapping("/stocks/{id}")
    public Stock getStock(@PathVariable("id") Long id) {
        return store.retrieve(id);
    }

    @RequestMapping(path="/stocks/{id}", method=RequestMethod.PUT)
    public void updateStock(@PathVariable("id") Long id, Stock stock) {
        store.update(id, stock);
    }

    @RequestMapping(path="/stocks", method=RequestMethod.POST)
    public Long createStock(Stock stock) {
        return store.insert(stock);
    }
}
