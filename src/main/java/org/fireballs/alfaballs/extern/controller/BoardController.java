package org.fireballs.alfaballs.extern.controller;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.fireballs.alfaballs.app.service.BoardService;
import org.fireballs.alfaballs.domain.Board;
import org.fireballs.alfaballs.extern.assembler.BoardDetailsAssembler;
import org.fireballs.alfaballs.extern.assembler.BoardShortcutAssembler;
import org.fireballs.alfaballs.extern.dto.group.PostPutGroup;
import org.fireballs.alfaballs.extern.dto.newdtos.BoardDto;
import org.fireballs.alfaballs.extern.dto.newdtos.MessageDto;
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

        Board savedBoard = boardService.saveNewBoard((projectId), boardDto.getBoardName());
        return new ResponseEntity<>(boardDetailsAssembler.toModel(savedBoard), HttpStatus.CREATED);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDto.Details> getBoard(@PathVariable("projectId") Long projectId,
                                             @PathVariable("boardId") Long boardId) {

        Board retrievedBoard = boardService.getBoardById((boardId));

        return new ResponseEntity<>(boardDetailsAssembler.toModel(retrievedBoard), HttpStatus.OK);
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<BoardDto.Details> updateBoard(@PathVariable("projectId") Long projectId,
                                                @PathVariable("boardId") Long boardId,
                                                @Validated(PostPutGroup.class) @RequestBody BoardDto.Shortcut boardDto) {
        Board existingBoard = boardService.getBoardById((boardId));
        existingBoard.setName(boardDto.getBoardName());

        Board updatedBoard = boardService.updateBoard(existingBoard);
        return new ResponseEntity<>(boardDetailsAssembler.toModel(updatedBoard), HttpStatus.OK);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<MessageDto> deleteBoard(@PathVariable("projectId") Long projectId,
                                            @PathVariable("boardId") Long boardId) {
        boardService.deleteBoard(boardId);

        MessageDto response = new MessageDto();
        response.setMessage("Board with ID " + boardId + " has been deleted");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
