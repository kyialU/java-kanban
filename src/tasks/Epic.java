package tasks;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private final List<Subtask> subtasks;
    private boolean isAutoUpdate = false; // Флаг для автоматического обновления

    public Epic(int id, String name, String description) {
        super(id, name, description, Status.NEW, TaskType.EPIC);
        this.subtasks = new ArrayList<>();
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    public boolean addSubtask(Subtask subtask) {
        if (subtask == null || subtask.getEpicId() != this.getId()) {
            System.out.println("Ошибка: подзадача не может быть null или не принадлежать этому Epic.");
            return false;
        }
        subtasks.add(subtask);
        updateStatus();
        return true;
    }

    public boolean removeSubtask(Subtask subtask) {
        if (subtask == null) {
            System.out.println("Ошибка: подзадача не может быть null.");
            return false;
        }
        subtasks.remove(subtask);
        updateStatus();
        return true;
    }

    public void updateStatus() {
        if (subtasks.isEmpty()) {
            isAutoUpdate = true; // Указываем, что обновление автоматическое
            setStatus(Status.NEW);
            isAutoUpdate = false; // Сбрасываем флаг
            return;
        }

        boolean allDone = true;
        boolean hasInProgress = false;

        for (Subtask subtask : subtasks) {
            if (subtask.getStatus() == Status.IN_PROGRESS) {
                hasInProgress = true;
            }
            if (subtask.getStatus() != Status.DONE) {
                allDone = false;
            }
        }

        isAutoUpdate = true; // Указываем, что обновление автоматическое
        if (hasInProgress) {
            setStatus(Status.IN_PROGRESS);
        } else if (allDone) {
            setStatus(Status.DONE);
        } else {
            setStatus(Status.NEW);
        }
        isAutoUpdate = false; // Сбрасываем флаг
    }

    @Override
    public void setStatus(Status status) {
        if (!isAutoUpdate) {
            System.out.println("Ошибка: статус Epic нельзя изменить вручную.");
        } else {
            super.setStatus(status); // Обновляем статус, если обновление автоматическое
        }
    }
}
