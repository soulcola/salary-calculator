package com.petrtitov.web;

import com.petrtitov.config.ErrorInfo;
import com.petrtitov.config.ErrorType;
import com.petrtitov.model.Payment;
import com.petrtitov.util.JsonUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;

import static com.petrtitov.service.PaymentTestData.*;
import static com.petrtitov.web.SalaryCalculationController.REST_URL;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class SalaryCalculationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    protected ResultActions perform(MockHttpServletRequestBuilder builder) throws Exception {
        return mockMvc.perform(builder);
    }

    @Test
    void calculate() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("days", String.valueOf(testDuration1));
        params.add("salary", String.valueOf(testSalary));

        var result = perform(MockMvcRequestBuilders.get(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .params(params))
                .andExpect(status().isOk())
                .andReturn();

        var actual = JsonUtil.readValue(result.getResponse().getContentAsString(), Payment.class);
        Assertions.assertThat(actual).usingRecursiveComparison().isEqualTo(testPayment1);
    }

    @Test
    void calculateByDates() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("startDate", String.valueOf(startDate));
        params.add("endDate", String.valueOf(endDate));
        params.add("salary", String.valueOf(testSalary));

        var result = perform(MockMvcRequestBuilders.get(REST_URL + "/by-dates")
                .contentType(MediaType.APPLICATION_JSON)
                .params(params))
                .andExpect(status().isOk())
                .andReturn();

        var actual = JsonUtil.readValue(result.getResponse().getContentAsString(), Payment.class);
        Assertions.assertThat(actual).usingRecursiveComparison().isEqualTo(testPayment2);
    }

    @Test
    void wrongSalary() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("startDate", String.valueOf(startDate));
        params.add("endDate", String.valueOf(endDate));
        params.add("salary", String.valueOf(BigDecimal.valueOf(-100L)));

        var result = perform(MockMvcRequestBuilders.get(REST_URL + "/by-dates")
                .contentType(MediaType.APPLICATION_JSON)
                .params(params))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

        var actual = JsonUtil.readValue(result.getResponse().getContentAsString(), ErrorInfo.class);
        Assertions.assertThat(actual.getType()).isEqualTo(ErrorType.VALIDATION_ERROR);
    }

    @Test
    void wrongDates() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("startDate", String.valueOf(endDate));
        params.add("endDate", String.valueOf(startDate));
        params.add("salary", String.valueOf(testSalary));

        var result = perform(MockMvcRequestBuilders.get(REST_URL + "/by-dates")
                .contentType(MediaType.APPLICATION_JSON)
                .params(params))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

        var actual = JsonUtil.readValue(result.getResponse().getContentAsString(), ErrorInfo.class);
        Assertions.assertThat(actual.getType()).isEqualTo(ErrorType.INPUT_ERROR);
    }

    @Test
    void parseException() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("startDate", String.valueOf(endDate));
        params.add("endDate", "date");
        params.add("salary", String.valueOf(testSalary));

        var result = perform(MockMvcRequestBuilders.get(REST_URL + "/by-dates")
                .contentType(MediaType.APPLICATION_JSON)
                .params(params))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

        var actual = JsonUtil.readValue(result.getResponse().getContentAsString(), ErrorInfo.class);
        Assertions.assertThat(actual.getType()).isEqualTo(ErrorType.VALIDATION_ERROR);
    }
}
