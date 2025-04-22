package org.fireballs.alfaballs.extern.controller;

import lombok.RequiredArgsConstructor;
import org.fireballs.alfaballs.app.service.BoardService;
import org.fireballs.alfaballs.domain.Board;
import org.fireballs.alfaballs.extern.assembler.BoardAssembler;
import org.fireballs.alfaballs.extern.dto.group.PostPutGroup;
import org.fireballs.alfaballs.extern.dto.newdtos.BoardDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/projects/{projectId}/kanban-board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final BoardAssembler boardAssembler;

    @PostMapping
    public ResponseEntity<BoardDto> createBoard(@PathVariable("projectId") String projectId,
                                                @Validated(PostPutGroup.class) @RequestBody BoardDto boardDto) {

        Board savedBoard = boardService.saveBoard(Long.parseLong(projectId), boardDto.getBoardName());
        return new ResponseEntity<>(boardAssembler.toModel(savedBoard), HttpStatus.CREATED);
    }

    //todo не работает, проблема со связями между сущностями ОРМ, надо разбираться
    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDto> getBoard(@PathVariable("projectId") String projectId,
                                             @PathVariable("boardId") String boardId) {

        Board retrievedBoard = boardService.getBoardById(Long.parseLong(boardId));

        return new ResponseEntity<>(boardAssembler.toModel(retrievedBoard), HttpStatus.OK);
    }
}
