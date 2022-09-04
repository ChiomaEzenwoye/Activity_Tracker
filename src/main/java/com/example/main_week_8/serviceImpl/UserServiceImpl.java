package com.example.main_week_8.serviceImpl;

import com.example.main_week_8.dto.TaskDTO;
import com.example.main_week_8.dto.UserDTO;
import com.example.main_week_8.exceptions.TaskNotFoundException;
import com.example.main_week_8.exceptions.UserNotFoundException;
import com.example.main_week_8.model.Status;
import com.example.main_week_8.model.Task;
import com.example.main_week_8.model.User;
import com.example.main_week_8.repository.TaskRepository;
import com.example.main_week_8.repository.UserRepository;
import com.example.main_week_8.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private  final TaskRepository taskRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public User registerUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        return  userRepository.save(user);
    }

    @Override
    public String loginUser(String email, String password) {
        String message = "";
        User user = getUserByEmail(email);
        if (user.getPassword().equals(password)){
            message = "Success";
        }else {
            message = "incorrect password";
        }
        return message;
    }


    @Override
    public Task createTask(TaskDTO taskDTO) {
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setStatus(Status.PENDING);
        task.setDescription(taskDTO.getDescription());
        User loginUser = getUserById(taskDTO.getUser_id());
        task.setUser(loginUser);
        return taskRepository.save(task);
    }

    @Override
    public User getUserById(int id){
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("user" + "not found in the database"));
    }



    @Override
    public Task updateTitleAndDescription(TaskDTO taskDTO , int id) {
        Task task = getTaskById(id);
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        return taskRepository.save(task);
    }

    @Override
    public List<Task> viewAllTasks() {
        return taskRepository.findAll();
    }



    @Override
    public List<Task> viewAllTaskByStatus(String status , int user_id) {
        return taskRepository.listOfTasksByStatus(status, user_id);
    }

    @Override
    public boolean deleteById(int id) {
        taskRepository.deleteById(id);
        return  true;
    }

    @Override
    public boolean updateTaskStatus(String status, int id){
        return taskRepository.updateTaskByIdAndStatus(status , id);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email + "not found in the database"));
    }

    @Override
    public Task getTaskById(int id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException( "Task not found in the database"));
    }


    @Override
    public String moveForward(int id){
        String message ="";
        Task task = taskRepository.findById(id).get();
        if(task.getStatus() == Status.PENDING){
            task.setStatus(Status.IN_PROGRESS);
            taskRepository.save(task);
            message="moved from pending to in-progress";
        }else if (task.getStatus() == Status.IN_PROGRESS){
            task.setStatus(Status.COMPLETED);
            task.setCompletedAt(LocalDateTime.now());
            taskRepository.save(task);
            message = "moved from in-progress to completed";
        }else {
            message ="unauthorized moved";
        }
        return message;

    }

    @Override
    public String moveBackward(int id){
        String message ="";
        Task task = taskRepository.findById(id).get();
        if(task.getStatus() == Status.IN_PROGRESS){
            task.setStatus(Status.PENDING);
            taskRepository.save(task);
            message ="moved back to pending";
        }else{
            message = " Unauthorized";
        }
        return message;
    }
    @Override
    public List<Task> showTaskByUser(int id){
        return taskRepository.listOfTasksByUserId(id);
    }


}
