package stock;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.time.Instant;

/**
 * Represents a single stock to be tracked.
 */
public class Stock {

    private Long id;
    private String name;
    private Double currentPrice;
    private Instant lastUpdated;

    /**
     * Default constructor used in serialization.
     */
    public Stock() {

    }

    public Stock(String name, Double currentPrice) {
        this(null, name, currentPrice, Instant.now());
    }

    /**
     * Constructor.
     *
     * @param id - Identifier for the stock, managed internally
     * @param name - Human readable name of the stock
     * @param currentPrice - Current Price of the stock
     * @param lastUpdated - Last update timestamp of the stock
     */
    public Stock(Long id, String name, Double currentPrice, Instant lastUpdated) {
        this.id = id;
        this.name = name;
        this.currentPrice = currentPrice;
        this.lastUpdated = lastUpdated;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public Instant getLastUpdated() {
        return lastUpdated;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return Objects.equal(id, stock.id) &&
                       Objects.equal(name, stock.name) &&
                       Objects.equal(currentPrice, stock.currentPrice) &&
                       Objects.equal(lastUpdated, stock.lastUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, currentPrice, lastUpdated);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                       .add("id", id)
                       .add("name", name)
                       .add("currentPrice", currentPrice)
                       .add("lastUpdated", lastUpdated)
                       .toString();
    }
}
