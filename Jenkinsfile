def BRANCH_CHOICES = ['main', 'build', 'testing']
pipeline {
    agent any
    stages {
        stage('Hello') {
            steps {
                retry (10) {
                    sleep(3)
                    httpRequest(
                        validResponseCodes: '200',
                        httpMode: 'GET',
                        url: "http://test-nginx/"
                    )
                    echo "This line will be executed eventually"
                }
            }
        }
    }
}
