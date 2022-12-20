package com.example.demo.controller;

import com.example.demo.domain.Customer;
import com.example.demo.repo.CustomerRepo;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MainControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    @Autowired
    private CustomerRepo customerRepo;

    @Test
    public void getCostumerByIdWhichExists() throws Exception {
        Customer customer = new Customer(1L, "Tom", "Tom", "21.12.2003");
        customerRepo.save(customer);

        this.mockMvc.perform(get("/home/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", CoreMatchers.is("Tom")));
    }

    @Test
    public void getCostumerByIdWhichDoesntExists() throws Exception {
        this.mockMvc.perform(get("/home/2"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error", CoreMatchers.is("There isn`t such user")));
    }
}