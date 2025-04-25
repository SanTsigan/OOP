package ru.nsu.tsyganov.dsl.config;

public class Student {
    private String githubNick;  // Ник в GitHub ("ivanov")
    private String fullName;    // ФИО ("Иванов Иван")
    private String repoUrl;     // Ссылка на репозиторий

    public String getFullName() {
        return fullName;
    }

    public String getGithubNick() {
        return githubNick;
    }

    public String getRepoUrl() {
        return repoUrl;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setGithubNick(String githubNick) {
        this.githubNick = githubNick;
    }

    public void setRepoUrl(String repoUrl) {
        this.repoUrl = repoUrl;
    }
}


