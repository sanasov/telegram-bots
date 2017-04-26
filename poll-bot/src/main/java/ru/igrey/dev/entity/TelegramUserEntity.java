package ru.igrey.dev.entity;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created by sanasov on 26.04.2017.
 */
@EqualsAndHashCode
@ToString
public class TelegramUserEntity {
    private Long userId;
    private String firstName;
    private String lastName;
    private String userName;
    private Boolean isActive;
    private String status;
    private String myPolls;
    private String pollExchange;

    public TelegramUserEntity(Long userId, String firstName, String lastName, String userName, String status, String myPolls, String pollExchange, Boolean isActive) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.status = status;
        this.myPolls = myPolls;
        this.pollExchange = pollExchange;
        this.isActive = isActive;
    }

    public TelegramUserEntity() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMyPolls() {
        return myPolls;
    }

    public void setMyPolls(String myPolls) {
        this.myPolls = myPolls;
    }

    public String getPollExchange() {
        return pollExchange;
    }

    public void setPollExchange(String pollExchange) {
        this.pollExchange = pollExchange;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
