package stock.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import stock.Stock;
import stock.dao.StockStore;

import java.util.List;
import java.util.function.Supplier;

@RestController()
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:9000")
public class StockController {

    private Logger logger = LoggerFactory.getLogger(StockController.class);

    private StockStore store;

    @Autowired
    public StockController(StockStore store) {
        this.store = store;
    }

    @RequestMapping("/stocks")
    @ResponseBody
    public List<Stock> getStocks() {
        return store.retrieveAll();
    }

    @RequestMapping("/stocks/{id}")
    @ResponseBody
    public Stock getStock(@PathVariable("id") Long id) {
        return store.retrieve(id).orElseThrow(() -> new StockNotFoundException(id));
    }

    @RequestMapping(path="/stocks/{id}", method=RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void updateStock(@PathVariable("id") Long id, @RequestBody Stock stock) {
        Preconditions.checkNotNull(stock.getName(), badRequest("Name cannot be null"));
        Preconditions.checkNotNull(stock.getCurrentPrice(), badRequest("Price cannot be null"));
        store.update(id, stock);
    }

    @RequestMapping(path="/stocks", method=RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Long createStock(@RequestBody Stock stock) {
        Preconditions.checkNotNull(stock.getName(), badRequest("Name cannot be null"));
        Preconditions.checkNotNull(stock.getCurrentPrice(), badRequest("Price cannot be null"));
        return store.insert(stock);
    }

    private Supplier<BadRequestException> badRequest(String message) {
        return () -> new BadRequestException(message);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    class StockNotFoundException extends RuntimeException {
        public StockNotFoundException(Long id) {
            super("Cound not find stock with id " + id);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    class BadRequestException extends RuntimeException {
        public BadRequestException(String message) {
            super(message);
        }
    }
}
