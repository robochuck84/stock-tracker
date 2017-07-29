package stock.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import stock.Stock;
import stock.dao.StockStore;

import java.util.List;

@RestController()
@RequestMapping("/api")
public class StockController {

    private Logger logger = LoggerFactory.getLogger(StockController.class);

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
        return store.retrieve(id).orElseThrow(() -> new StockNotFoundException(id));
    }

    @RequestMapping(path="/stocks/{id}", method=RequestMethod.PUT)
    public void updateStock(@PathVariable("id") Long id, Stock stock) {
        store.update(id, stock);
    }

    @RequestMapping(path="/stocks", method=RequestMethod.POST)
    public Long createStock(Stock stock) {
        logger.info("Asked to create stock {}", stock);
        return store.insert(stock);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    class StockNotFoundException extends RuntimeException {
        public StockNotFoundException(Long id) {
            super("Cound not find stock with id " + id);
        }
    }
}
