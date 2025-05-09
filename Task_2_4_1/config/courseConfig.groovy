course {
    tasks {
        task {
            id = '2.4.1'
            name = 'Инкапсуляция'
            maxPoints = 10
            softDeadline = LocalDate.parse('2025-05-10')
            hardDeadline = LocalDate.parse('2025-05-15')
        }
        task {
            id = '2.4.2'
            name = 'Наследование'
            maxPoints = 15
            softDeadline = LocalDate.parse('2025-05-20')
            hardDeadline = LocalDate.parse('2025-05-25')
        }
    }

    groups {
        group(name: 'A') {
            student {
                github = 'student123'
                fullName = 'Иванов Иван'
                repoUrl = 'https://github.com/student123/oop-course'
            }
            student {
                github = 'student456'
                fullName = 'Петров Петр'
                repoUrl = 'https://github.com/student456/oop-course'
            }
        }
    }

    checks {
        check {
            taskId = '2.4.1'
            github = 'student123'
        }
        check {
            taskId = '2.4.2'
            github = 'student456'
        }
    }

    checkpoints {
        checkpoint {
            name = '1-я контрольная'
            date = LocalDate.parse('2025-06-01')
        }
        checkpoint {
            name = '2-я контрольная'
            date = LocalDate.parse('2025-06-15')
        }
    }

    settings {
        gradeConversion = [90: '5', 75: '4', 60: '3']
        bonusPoints = ['student123': ['2.4.1': 2]]
    }
}