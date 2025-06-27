package com.example.tasks.Controller;

import com.example.tasks.Model.TaskGroup;
import com.example.tasks.Service.TaskGroupService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/task-groups")
public class TaskGroupController {
    
    @Autowired
    private TaskGroupService taskGroupService;
    
    @GetMapping
    public List<TaskGroup> getAllTaskGroups() {
        return taskGroupService.getAllTaskGroups();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TaskGroup> getTaskGroupById(@PathVariable Long id) {
        Optional<TaskGroup> taskGroup = taskGroupService.getTaskGroupById(id);
        return taskGroup.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/board/{boardId}")
    public List<TaskGroup> getTaskGroupsByBoardId(@PathVariable Long boardId) {
        return taskGroupService.getTaskGroupsByBoardId(boardId);
    }
    
    @PostMapping
    public ResponseEntity<TaskGroup> createTaskGroup(@Valid @RequestBody TaskGroup taskGroup) {
        try {
            TaskGroup savedTaskGroup = taskGroupService.saveTaskGroup(taskGroup);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTaskGroup);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<TaskGroup> updateTaskGroup(@PathVariable Long id, @Valid @RequestBody TaskGroup taskGroupDetails) {
        try {
            TaskGroup updatedTaskGroup = taskGroupService.updateTaskGroup(id, taskGroupDetails);
            return ResponseEntity.ok(updatedTaskGroup);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaskGroup(@PathVariable Long id) {
        try {
            taskGroupService.deleteTaskGroup(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}