package com.example.tasks.Service;

import com.example.tasks.Model.Board;
import com.example.tasks.Repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    
    @Autowired
    private BoardRepository boardRepository;
    
    public Board saveBoard(Board board) {
        return boardRepository.save(board);
    }
    
    public Optional<Board> getBoardById(Long id) {
        return boardRepository.findById(id);
    }
    
    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }
    
    public Board updateBoard(Long id, Board boardDetails) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board não encontrado com id: " + id));
        
        board.setName(boardDetails.getName());
        board.setDescription(boardDetails.getDescription());
        
        return boardRepository.save(board);
    }
    
    public void deleteBoard(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board não encontrado com id: " + id));
        boardRepository.delete(board);
    }
    
    public boolean existsById(Long id) {
        return boardRepository.existsById(id);
    }
}