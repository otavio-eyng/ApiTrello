package com.example.tasks.Service;

import com.example.tasks.Model.TaskGroup;
import com.example.tasks.Repository.TaskGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskGroupService {
    
    @Autowired
    private TaskGroupRepository taskGroupRepository;
    
    @Autowired
    private BoardService boardService;
    
    public TaskGroup saveTaskGroup(TaskGroup taskGroup) {
        // Validar se o board existe
        if (!boardService.existsById(taskGroup.getBoardId())) {
            throw new RuntimeException("Board não encontrado com id: " + taskGroup.getBoardId());
        }
        return taskGroupRepository.save(taskGroup);
    }
    
    public Optional<TaskGroup> getTaskGroupById(Long id) {
        return taskGroupRepository.findById(id);
    }
    
    public List<TaskGroup> getAllTaskGroups() {
        return taskGroupRepository.findAll();
    }
    
    public List<TaskGroup> getTaskGroupsByBoardId(Long boardId) {
        return taskGroupRepository.findByBoardId(boardId);
    }
    
    public TaskGroup updateTaskGroup(Long id, TaskGroup taskGroupDetails) {
        TaskGroup taskGroup = taskGroupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TaskGroup não encontrado com id: " + id));
        
        // Validar se o novo board existe (se foi alterado)
        if (!taskGroupDetails.getBoardId().equals(taskGroup.getBoardId())) {
            if (!boardService.existsById(taskGroupDetails.getBoardId())) {
                throw new RuntimeException("Board não encontrado com id: " + taskGroupDetails.getBoardId());
            }
        }
        
        taskGroup.setName(taskGroupDetails.getName());
        taskGroup.setBoardId(taskGroupDetails.getBoardId());
        
        return taskGroupRepository.save(taskGroup);
    }
    
    public void deleteTaskGroup(Long id) {
        TaskGroup taskGroup = taskGroupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TaskGroup não encontrado com id: " + id));
        taskGroupRepository.delete(taskGroup);
    }
    
    public boolean existsById(Long id) {
        return taskGroupRepository.existsById(id);
    }
}