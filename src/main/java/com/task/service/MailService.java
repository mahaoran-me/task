package com.task.service;

import com.task.entity.Task;
import com.task.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class MailService {

    private final LinkedBlockingQueue<Task> mailNotifyQueue;

    @Autowired
    public MailService(TaskService taskService, UserService userService,JavaMailSender javaMailSender) {
        mailNotifyQueue = new LinkedBlockingQueue<>();

        new Thread(() -> {
            while (true) {
                List<Task> tasks = taskService.willTimeout();
                tasks.forEach(mailNotifyQueue::offer);
                try {
                    Thread.sleep(1000 * 60 * 60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                Task task = null;
                try {
                    task = mailNotifyQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                User user = userService.findUserById(task.getUid());
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom("1829385036@qq.com");
                message.setTo(user.getEmail());
                message.setSubject("任务过期提醒");
                message.setText(user.getUsername() + "你好, 你的任务 " + task.getTitle() + " 即将过期，请及时查看");
                javaMailSender.send(message);
            }
        }).start();
    }
}