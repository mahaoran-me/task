package com.task.service;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MailThread implements Runnable {

    private final Thread thread;
    private final TaskService taskService;

    @Autowired
    public MailThread(TaskService taskService) {
        this.taskService = taskService;
        this.thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("开始发送");
            this.taskService.mailNotify();
            System.out.println("发送结束");
            try {
                Thread.sleep(1000 * 60 * 60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
