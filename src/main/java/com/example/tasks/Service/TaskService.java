package com.example.tasks.Service;

import com.example.tasks.Model.Task;
import com.example.tasks.Repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    
    @Autowired
    private TaskRepository taskRepository;
    
    @Autowired
    private TaskGroupService taskGroupService;

    public Task saveTask(Task task) {
        // Validar se o taskGroup existe
        if (!taskGroupService.existsById(task.getTaskGroupId())) {
            throw new RuntimeException("TaskGroup não encontrado com id: " + task.getTaskGroupId());
        }
        return taskRepository.save(task);
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    
    public List<Task> getTasksByTaskGroupId(Long taskGroupId) {
        return taskRepository.findByTaskGroupId(taskGroupId);
    }
    
    public Task updateTask(Long id, Task taskDetails) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task não encontrada com id: " + id));
        
        // Validar se o novo taskGroup existe (se foi alterado)
        if (!taskDetails.getTaskGroupId().equals(task.getTaskGroupId())) {
            if (!taskGroupService.existsById(taskDetails.getTaskGroupId())) {
                throw new RuntimeException("TaskGroup não encontrado com id: " + taskDetails.getTaskGroupId());
            }
        }
        
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setStatus(taskDetails.getStatus());
        task.setTaskGroupId(taskDetails.getTaskGroupId());
        
        return taskRepository.save(task);
    }
    
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task não encontrada com id: " + id));
        taskRepository.delete(task);
    }
}