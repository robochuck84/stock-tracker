package stock.controller;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import stock.Stock;
import stock.dao.StockStore;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockStore stockStore;

    @Test
    public void testGetStocks() throws Exception {
        // given
        when(stockStore.retrieveAll()).thenReturn(
                ImmutableList.of(
                        new Stock(1l, "test1", 10.0, "now"),
                        new Stock(2l, "test2", 12.0, "now")));

        // when
        mockMvc.perform(get("/api/stocks"))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("test1")))
                .andExpect(jsonPath("$[0].currentPrice", is(10.0)))
                .andExpect(jsonPath("$[0].lastUpdated", is("now")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("test2")))
                .andExpect(jsonPath("$[1].currentPrice", is(12.0)))
                .andExpect(jsonPath("$[1].lastUpdated", is("now")));
    }

    @Test
    public void testGetStock() throws Exception {
        // given
        when(stockStore.retrieve(eq(1l))).thenReturn(
                Optional.of(
                        new Stock(1l, "test", 12.0, "now")));

        // when
        mockMvc.perform(get("/api/stocks/1"))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("test")))
                .andExpect(jsonPath("$.currentPrice", is(12.0)))
                .andExpect(jsonPath("$.lastUpdated", is("now")));
    }

    @Test
    public void testGetStockWithFailure() throws Exception {
        // given
        when(stockStore.retrieve(eq(1l))).thenReturn(
                Optional.empty());

        // when
        mockMvc.perform(get("/api/stocks/1"))
                // then
                .andExpect(status().isNotFound());
    }


    @Test
    public void testUpdateStock() throws Exception {
        // given
        String body = "{\"currentPrice\":10.0}";

        // when
        mockMvc.perform(put("/api/stocks/1")
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON_UTF8))
                // then
                .andExpect(status().isOk());

        verify(stockStore).update(eq(1l), eq(10.0));
    }

    @Test
    public void testUpdateStockWithFailure() throws Exception {
        // given
        String body = "{}";

        // when
        mockMvc.perform(put("/api/stocks/1")
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON_UTF8))
                // then
                .andExpect(status().is4xxClientError());

        verifyNoMoreInteractions(stockStore);
    }

    @Test
    public void testCreateStock() throws Exception {
        // given
        String body = "{\"name\":\"test\",\"currentPrice\":10.0}";

        // when
        mockMvc.perform(post("/api/stocks")
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON_UTF8))
                // then
                .andExpect(status().isCreated());

        verify(stockStore).insert(isA(Stock.class));
    }

    @Test
    public void testCreateStockWithNoName() throws Exception {
        // given
        String body = "{\"currentPrice\":10.0}";

        // when
        mockMvc.perform(post("/api/stocks")
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON_UTF8))
                // then
                .andExpect(status().is4xxClientError());

        verifyNoMoreInteractions(stockStore);
    }

    @Test
    public void testCreateStockWithNoPrice() throws Exception {
        // given
        String body = "{\"name\":\"test\"}";

        // when
        mockMvc.perform(post("/api/stocks")
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON_UTF8))
                // then
                .andExpect(status().is4xxClientError());

        verifyNoMoreInteractions(stockStore);
    }
}
