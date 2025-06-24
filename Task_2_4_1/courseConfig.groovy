import java.time.LocalDate

builder.course {
    tasks {
        task {
            id = 'Task_1_1_3'
            name = 'Хэш-таблица'
            maxPoints = 5
            softDeadline = LocalDate.parse('2025-12-31')
            hardDeadline = LocalDate.parse('2026-01-15')
        }
    }

    groups {
        group(name: 'JustMe') {
            student {
                github = 'SanTsigan'
                fullName = 'Цыганов Александр'
                repoUrl = 'https://github.com/SanTsigan/OOP'
            }
        }
    }

    checks {
        check {
            taskId = 'Task_1_1_3'
            github = 'SanTsigan'
        }
    }

    checkpoints {}

    settings {
        gradeConversion = [5:'5',3:'4',1:'3']
        bonusPoints = [:]
    }
}