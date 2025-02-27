package main;

import manager.TaskManager;
import tasks.*;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();


        Task task1 = new Task(manager.generateId(), "Задача 1", "Описание 1", Status.NEW, TaskType.TASK);
        Task task2 = new Task(manager.generateId(), "Задача 2", "Описание 2", Status.NEW, TaskType.TASK);


        Epic epic1 = new Epic(manager.generateId(), "Epic 1", "Описание Epic 1");
        Subtask subtask1 = new Subtask(manager.generateId(), "Подзадача 1", "Описание Подзадачи 1", Status.NEW, epic1.getId());
        Subtask subtask2 = new Subtask(manager.generateId(), "Подзадача  2", "Описание Подзадачи 2", Status.NEW, epic1.getId());


        Epic epic2 = new Epic(manager.generateId(), "Epic 2", "Описание Epic 2");
        Subtask subtask3 = new Subtask(manager.generateId(), "Подзадача 3", "Описание Подзадачи 3", Status.NEW, epic2.getId());


        manager.addTask(task1);
        manager.addTask(task2);
        manager.addTask(epic1);
        manager.addTask(epic2);
        manager.addTask(subtask1);
        manager.addTask(subtask2);
        manager.addTask(subtask3);


        System.out.println("Все задачи:");
        System.out.println(manager.getAllTasks());

        System.out.println("Все Epic:");
        System.out.println(manager.getAllEpics());

        System.out.println("Все подзадачи:");
        System.out.println(manager.getAllSubtasks());


        task1.setStatus(Status.IN_PROGRESS);
        subtask1.setStatus(Status.DONE);
        subtask2.setStatus(Status.IN_PROGRESS);
        subtask3.setStatus(Status.DONE);


        manager.updateTask(task1);
        manager.updateTask(subtask1);
        manager.updateTask(subtask2);
        manager.updateTask(subtask3);


        System.out.println("\nПосле обновления статусов:");
        System.out.println("Все задачи:");
        System.out.println(manager.getAllTasks());

        System.out.println("Все Epic:");
        System.out.println(manager.getAllEpics());

        System.out.println("Все подзадачи:");
        System.out.println(manager.getAllSubtasks());


        System.out.println("\nСтатус Epic 1: " + epic1.getStatus());
        System.out.println("Статус Epic 2: " + epic2.getStatus());


        manager.deleteTask(task1.getId());
        manager.deleteTask(epic1.getId());


        System.out.println("\nПосле удаления задачи и Epic:");
        System.out.println("Все задачи:");
        System.out.println(manager.getAllTasks());

        System.out.println("Все Epic:");
        System.out.println(manager.getAllEpics());

        System.out.println("Все подзадачи:");
        System.out.println(manager.getAllSubtasks());
    }
}