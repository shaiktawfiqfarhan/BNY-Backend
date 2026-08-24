package com.Backend.controller;

import com.Backend.service.SharePointTestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class SharePointTestController {

    private final SharePointTestService sharePointTestService;

    public SharePointTestController(
            SharePointTestService sharePointTestService) {

        this.sharePointTestService =
                sharePointTestService;
    }

    @GetMapping("/sharepoint")
    public String testSharePoint()
            throws Exception {

        return sharePointTestService
                .readPage();
    }
}