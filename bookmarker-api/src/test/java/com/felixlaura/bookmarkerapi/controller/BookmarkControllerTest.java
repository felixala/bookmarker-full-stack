package com.felixlaura.bookmarkerapi.controller;

import com.felixlaura.bookmarkerapi.domain.Bookmark;
import com.felixlaura.bookmarkerapi.repository.BookmarkRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:tc:postgresql:14-alpine:///demo"
})
public class BookmarkControllerTest {

    @Autowired
    BookmarkRepository repository;

    @Autowired
    private MockMvc mockMvc;

    private List<Bookmark> bookmarks;

    @BeforeEach
    void setUp(){
        repository.deleteAllInBatch();
        bookmarks = new ArrayList<>();

        bookmarks.add(new Bookmark(null, "SivaLabs", "https://sivalabs.in", Instant.now()));
        bookmarks.add(new Bookmark(null, "SpringBlog", "https://spring.io/blog", Instant.now()));
        bookmarks.add(new Bookmark(null, "Quarkus", "https://quarkus.io/", Instant.now()));
        bookmarks.add(new Bookmark(null, "Micronaut", "https://micronaut.io/", Instant.now()));
        bookmarks.add(new Bookmark(null, "JOOQ", "https://www.jooq.org/", Instant.now()));
        bookmarks.add(new Bookmark(null, "VladMihalcea", "https://vladmihalcea.com", Instant.now()));
        bookmarks.add(new Bookmark(null, "Thoughts On Java", "https://thorben-janssen.com/", Instant.now()));
        bookmarks.add(new Bookmark(null, "DZone", "https://dzone.com/", Instant.now()));
        bookmarks.add(new Bookmark(null, "DevOpsBookmarks", "http://www.devopsbookmarks.com/", Instant.now()));
        bookmarks.add(new Bookmark(null, "Kubernetes docs", "https://kubernetes.io/docs/home/", Instant.now()));
        bookmarks.add(new Bookmark(null, "Expressjs", "https://expressjs.com/", Instant.now()));
        bookmarks.add(new Bookmark(null, "Marcobehler", "https://www.marcobehler.com", Instant.now()));
        bookmarks.add(new Bookmark(null, "baeldung", "https://www.baeldung.com", Instant.now()));
        bookmarks.add(new Bookmark(null, "devglan", "https://www.devglan.com", Instant.now()));
        bookmarks.add(new Bookmark(null, "linuxize", "https://linuxize.com", Instant.now()));

        repository.saveAll(bookmarks);

    }

    @ParameterizedTest
    @CsvSource({
            "1,15,2,1,true,false,true,false",
            "2,15,2,2,false,true,false,true"
    })
    void shouldGetBookmarks(int pageNo, int totalElements, int totalPages, int currentPage,
                            boolean isFirst, boolean isLast,
                            boolean hasNext, boolean hasPrevious) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/bookmarks?page="+pageNo))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements", CoreMatchers.equalTo(totalElements)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages", CoreMatchers.equalTo(totalPages)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currentPage", CoreMatchers.equalTo(currentPage)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isFirst", CoreMatchers.equalTo(isFirst)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isLast", CoreMatchers.equalTo(isLast)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.hasNext", CoreMatchers.equalTo(hasNext)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.hasPrevious", CoreMatchers.equalTo(hasPrevious)))
        ;
    }
}