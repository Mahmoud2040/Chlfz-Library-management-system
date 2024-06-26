package com.maids.chelfz.config;

import com.maids.chelfz.entity.Book;
import com.maids.chelfz.entity.Borrow;
import com.maids.chelfz.entity.Patron;
import com.maids.chelfz.util.response.ApiResponseManager;
import com.maids.chelfz.util.response.impl.DefaultApiResponseManager;
import com.maids.chelfz.util.response.impl.ItemApiResponseManager;
import com.maids.chelfz.util.response.impl.ListApiResponseManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;



/**
 * Configuration class for setting up various ApiResponseManager beans.
 * This class defines beans for managing API responses for different entity types
 * such as Book, Patron, and Borrow. Each bean provides a specific response management
 * strategy to be used throughout the application.
 */
@Configuration
public class ApiResponseManagerConfig {

    @Bean
    public ApiResponseManager<Void> defaultApiResponseManager() {
        return new DefaultApiResponseManager();
    }
    @Bean
    @Primary
    public ApiResponseManager<List<Book>> BookListApiResponseManager() {
        return new ListApiResponseManager<>();
    }

    @Bean
    @Primary
    public ApiResponseManager<Book> bookApiResponseManager() {
        return new ItemApiResponseManager<>();
    }

    @Bean
    @Primary
    public ApiResponseManager<List<Patron>> patronListApiResponseManager() {
        return new ListApiResponseManager<>();
    }


    @Bean
    @Primary
    public ApiResponseManager<Patron> patronApiResponseManager() {
        return new ItemApiResponseManager<>();
    }


    @Bean
    @Primary
    public ApiResponseManager<Borrow> BorrowApiResponseManager() {
        return new ItemApiResponseManager<>();
    }
}
