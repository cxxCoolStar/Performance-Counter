package io.shulie.performancecounter.viewer;

import io.shulie.performancecounter.model.RequestStat;
import io.shulie.performancecounter.sender.EmailSender;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author chenxingxing
 * @date 2022/12/9 10:00 下午
 * 邮箱视图器
 */
@Component("emailViewer")
public class EmailViewer implements StatViewer {

    private EmailSender emailSender;
    private List toAddresses = new ArrayList<>();

    public EmailViewer() {
        this.emailSender = new EmailSender(/*省略参数*/);
    }

    public EmailViewer(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void addToAddress(String address) {
        toAddresses.add(address);
    }

    public void output(Map requestStats, long startTimeInMillis, long endTimeInMills) {
        // format the requestStats to HTML style.
        // send it to email toAddresses.
    }
}
