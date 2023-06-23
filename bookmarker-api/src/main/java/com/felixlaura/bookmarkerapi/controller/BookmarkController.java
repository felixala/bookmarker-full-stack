package com.felixlaura.bookmarkerapi.controller;

import com.felixlaura.bookmarkerapi.domain.BookmarkDTO;
import com.felixlaura.bookmarkerapi.domain.BookmarksDTO;
import com.felixlaura.bookmarkerapi.domain.CreateBookmarkRequest;
import com.felixlaura.bookmarkerapi.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @GetMapping
    public BookmarksDTO getBookmarks(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                     @RequestParam(name = "query", defaultValue = "") String query){
        if(query == null || query.trim().length() == 0){
            return bookmarkService.getBookmarks(page);
        }
        return bookmarkService.searchBookmarks(query, page);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookmarkDTO createBookmark(@RequestBody @Valid CreateBookmarkRequest request){
        return bookmarkService.createBookmark(request);
    }

}
