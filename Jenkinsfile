def BRANCH_CHOICES = ['main', 'build', 'testing']
pipeline {
    agent any
    stages {
        stage('Hello') {
            steps {
                retry (10) {
                    sleep(3)
                    retries = retries + 1
                    httpRequest(
                        validResponseCodes: '200',
                        httpMode: 'GET',
                        url: "http://localhost:8090"
                    )
                    echo "This line will be executed eventually"
                }
            }
        }
    }
}