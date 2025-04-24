package org.fireballs.alfaballs.extern.controller;

import org.fireballs.alfaballs.extern.dto.group.PostPutGroup;
import org.fireballs.alfaballs.extern.dto.newdtos.IssueDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/issues")
public class IssueController {
    @PostMapping
    public ResponseEntity<IssueDto> createIssue(@Validated(PostPutGroup.class) @RequestBody IssueDto issueDto) {
    }

    @GetMapping("/{issueId}")
    public ResponseEntity<IssueDto> getIssue(@PathVariable long issueId) {
    }

    @PutMapping("/{issueId}")
    public ResponseEntity<IssueDto> updateIssue(@PathVariable long issueId, @RequestBody IssueDto issueDto) {
    }


}
