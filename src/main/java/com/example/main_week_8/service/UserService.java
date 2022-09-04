package com.example.main_week_8.service;

import com.example.main_week_8.dto.TaskDTO;
import com.example.main_week_8.dto.UserDTO;
import com.example.main_week_8.model.Task;
import com.example.main_week_8.model.User;

import java.util.List;

public interface UserService {

    User registerUser(UserDTO userDTO);
    String loginUser(String email, String password);


    Task createTask(TaskDTO taskDTO);

    User getUserById(int id);

    Task updateTitleAndDescription(TaskDTO taskDTO , int id);

    Task getTaskById(int id);

    List<Task> viewAllTasks();

    boolean updateTaskStatus(String status, int id);

    List<Task> viewAllTaskByStatus(String status , int user_id);

    boolean deleteById(int id);
    User getUserByEmail(String email);

    String moveForward(int id);

    String moveBackward(int id);


    List<Task> showTaskByUser(int id);
}
