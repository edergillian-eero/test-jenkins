def BRANCH_CHOICES = ['main', 'build', 'testing']
pipeline {
    agent any
    stages {
        stage('Hello') {
            steps {
                retry (2) {
                    httpRequest(
                        acceptType: 'APPLICATION_JSON',
                        authentication: 'eero-jenkins-pr-token',
                        validResponseCodes: '201,422',
                        contentType: 'APPLICATION_JSON',
                        httpMode: 'POST',
                        requestBody: payload,
                        url: "https://test.example.com"
                    )
                    echo "This line should NOT be executed"
                }
            }
        }
    }
}