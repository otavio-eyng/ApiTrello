package com.example.tasks.Controller;

import com.example.tasks.Model.Board;
import com.example.tasks.Service.BoardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/boards")
public class BoardController {
    
    @Autowired
    private BoardService boardService;
    
    @GetMapping
    public List<Board> getAllBoards() {
        return boardService.getAllBoards();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Board> getBoardById(@PathVariable Long id) {
        Optional<Board> board = boardService.getBoardById(id);
        return board.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Board> createBoard(@Valid @RequestBody Board board) {
        try {
            Board savedBoard = boardService.saveBoard(board);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedBoard);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Board> updateBoard(@PathVariable Long id, @Valid @RequestBody Board boardDetails) {
        try {
            Board updatedBoard = boardService.updateBoard(id, boardDetails);
            return ResponseEntity.ok(updatedBoard);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
        try {
            boardService.deleteBoard(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}