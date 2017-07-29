package stock.dao;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.time.Instant;

public class StockEntity {

    private String name;
    private Double currentPrice;
    private Instant lastUpdated;

    public StockEntity(String name, Double currentPrice) {
        this(name, currentPrice, Instant.now());
    }

    /**
     * DAO level entity to hold stock information.
     *
     * @param name - Human readable name of stock
     * @param currentPrice - Current Price of the stock
     * @param lastUpdated - Last update timestamp of the stock
     */
    public StockEntity(String name, Double currentPrice, Instant lastUpdated) {
        this.name = name;
        this.currentPrice = currentPrice;
        this.lastUpdated = lastUpdated;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockEntity that = (StockEntity) o;
        return Objects.equal(name, that.name) &&
                       Objects.equal(currentPrice, that.currentPrice) &&
                       Objects.equal(lastUpdated, that.lastUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, currentPrice, lastUpdated);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                       .add("name", name)
                       .add("currentPrice", currentPrice)
                       .add("lastUpdated", lastUpdated)
                       .toString();
    }
}
