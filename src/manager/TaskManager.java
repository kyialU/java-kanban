package manager;

import enums.TaskType;
import tasktypes.Epic;
import tasktypes.Subtask;
import tasktypes.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskManager {
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, Subtask> subtasks = new HashMap<>();
    private int nextId = 1;


    public int generateId() {
        return nextId++;
    }


    public boolean addTask(Task task) {
        if (task == null) {
            System.out.println("Ошибка: задача не может быть null.");
            return false;
        }

        switch (task.getType()) {
            case TaskType.TASK:
                tasks.put(task.getId(), task);
                return true;
            case TaskType.EPIC:
                epics.put(task.getId(), (Epic) task);
                return true;
            case TaskType.SUBTASK:
                Subtask subtask = (Subtask) task;
                if (!epics.containsKey(subtask.getEpicId())) {
                    System.out.println("Ошибка: Epic с id " + subtask.getEpicId() + " не существует.");
                    return false;
                }
                subtasks.put(subtask.getId(), subtask);
                epics.get(subtask.getEpicId()).addSubtask(subtask);
                return true;
            default:
                System.out.println("Ошибка: неизвестный тип задачи.");
                return false;
        }
    }


    public Task getTaskById(int id) {
        if (tasks.containsKey(id)) {
            return tasks.get(id);
        } else if (epics.containsKey(id)) {
            return epics.get(id);
        } else if (subtasks.containsKey(id)) {
            return subtasks.get(id);
        }
        System.out.println("Ошибка: задача с id " + id + " не найдена.");
        return null;
    }


    public boolean updateTask(Task task) {
        if (task == null) {
            System.out.println("Ошибка: задача не может быть null.");
            return false;
        }

        switch (task.getType()) {
            case TaskType.TASK:
                tasks.put(task.getId(), task);
                return true;
            case TaskType.EPIC:
                epics.put(task.getId(), (Epic) task);
                return true;
            case TaskType.SUBTASK:
                Subtask subtask = (Subtask) task;
                if (!epics.containsKey(subtask.getEpicId())) {
                    System.out.println("Ошибка: Epic с id " + subtask.getEpicId() + " не существует.");
                    return false;
                }
                subtasks.put(subtask.getId(), subtask);
                epics.get(subtask.getEpicId()).updateStatus();
                return true;
            default:
                System.out.println("Ошибка: неизвестный тип задачи.");
                return false;
        }
    }


    public boolean deleteTask(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
            return true;
        } else if (epics.containsKey(id)) {
            Epic epic = epics.remove(id);
            subtasks.values().removeIf(subtask -> subtask.getEpicId() == id);
            return true;
        } else if (subtasks.containsKey(id)) {
            Subtask subtask = subtasks.remove(id);
            Epic epic = epics.get(subtask.getEpicId());
            if (epic != null) {
                epic.removeSubtask(subtask);
                epic.updateStatus();
            }
            return true;
        }
        System.out.println("Ошибка: задача с id " + id + " не найдена.");
        return false;
    }


    public void deleteAllTasks() {
        tasks.clear();
        epics.clear();
        subtasks.clear();
    }


    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }


    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }


    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }


    public List<Subtask> getSubtasksByEpicId(int epicId) {
        List<Subtask> result = new ArrayList<>();
        for (Subtask subtask : subtasks.values()) {
            if (subtask.getEpicId() == epicId) {
                result.add(subtask);
            }
        }
        return result;
    }
}
