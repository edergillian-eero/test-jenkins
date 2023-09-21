def BRANCH_CHOICES = ['main', 'build', 'testing']
pipeline {
    agent any
    stages {
        stage('Hello') {
            steps {
                int retries = 0
                retry (10) {
                    if (retries > 0) {
                        sleep(retries * 2)
                    }
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