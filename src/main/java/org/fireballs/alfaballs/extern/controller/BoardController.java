package org.fireballs.alfaballs.extern.controller;

import lombok.RequiredArgsConstructor;
import org.fireballs.alfaballs.app.service.BoardService;
import org.fireballs.alfaballs.domain.Board;
import org.fireballs.alfaballs.extern.assembler.details.BoardDetailsAssembler;
import org.fireballs.alfaballs.extern.assembler.shortcut.BoardShortcutAssembler;
import org.fireballs.alfaballs.extern.dto.group.PostPutGroup;
import org.fireballs.alfaballs.extern.dto.BoardDto;
import org.fireballs.alfaballs.extern.dto.MessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/projects/{projectId}/boards")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final BoardDetailsAssembler boardDetailsAssembler;
    private final BoardShortcutAssembler boardShortcutAssembler;

    @PostMapping
    public ResponseEntity<BoardDto.Details> createBoard(@PathVariable("projectId") Long projectId,
                                                        @Validated(PostPutGroup.class) @RequestBody BoardDto.Shortcut boardDto) {

        Board savedBoard = boardService.saveBoard(projectId, boardShortcutAssembler.toEntity(boardDto));
        return new ResponseEntity<>(boardDetailsAssembler.toModel(savedBoard), HttpStatus.CREATED);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDto.Details> getBoard(@PathVariable("projectId") Long projectId,
                                                     @PathVariable("boardId") Long boardId) {

        Board retrievedBoard = boardService.getBoardById((boardId));
        return ResponseEntity.ok(boardDetailsAssembler.toModel(retrievedBoard));
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<BoardDto.Details> updateBoard(@PathVariable("projectId") Long projectId,
                                                        @PathVariable("boardId") Long boardId,
                                                        @Validated(PostPutGroup.class) @RequestBody BoardDto.Shortcut boardDto) {
        Board updatedBoard = boardService.updateBoard(boardId, boardShortcutAssembler.toEntity(boardDto));
        return ResponseEntity.ok(boardDetailsAssembler.toModel(updatedBoard));
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<MessageDto> deleteBoard(@PathVariable("projectId") Long projectId,
                                                  @PathVariable("boardId") Long boardId) {
        boardService.deleteBoard(boardId);
        return ResponseEntity.ok(new MessageDto("Board with ID " + boardId + " has been deleted"));
    }
}
