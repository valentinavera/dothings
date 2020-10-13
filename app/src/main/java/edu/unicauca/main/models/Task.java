package edu.unicauca.main.models;

public class Task {
    private String nameTask;

    public Task(){}
    public Task(String task) {
        this.nameTask = task;
            }

    public String getNameTask() {
        return nameTask;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }
}
