package org.fireballs.alfaballs.extern.controller;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.fireballs.alfaballs.app.service.BoardService;
import org.fireballs.alfaballs.domain.Board;
import org.fireballs.alfaballs.extern.assembler.BoardAssembler;
import org.fireballs.alfaballs.extern.dto.group.DetailsView;
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
    private final BoardAssembler boardAssembler;

    @JsonView(DetailsView.class)
    @PostMapping
    public ResponseEntity<BoardDto> createBoard(@PathVariable("projectId") Long projectId,
                                                @Validated(PostPutGroup.class) @RequestBody BoardDto boardDto) {

        Board savedBoard = boardService.saveNewBoard((projectId), boardDto.getBoardName());
        return new ResponseEntity<>(boardAssembler.toModel(savedBoard), HttpStatus.CREATED);
    }

    @JsonView(DetailsView.class)
    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDto> getBoard(@PathVariable("projectId") Long projectId,
                                             @PathVariable("boardId") Long boardId) {

        Board retrievedBoard = boardService.getBoardById((boardId));

        return new ResponseEntity<>(boardAssembler.toModel(retrievedBoard), HttpStatus.OK);
    }

    @JsonView(DetailsView.class)
    @PutMapping("/{boardId}")
    public ResponseEntity<BoardDto> updateBoard(@PathVariable("projectId") Long projectId,
                                                @PathVariable("boardId") Long boardId,
                                                @Validated(PostPutGroup.class) @RequestBody BoardDto boardDto) {
        Board existingBoard = boardService.getBoardById((boardId));
        existingBoard.setName(boardDto.getBoardName());

        Board updatedBoard = boardService.updateBoard(existingBoard);
        return new ResponseEntity<>(boardAssembler.toModel(updatedBoard), HttpStatus.OK);
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
