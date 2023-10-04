def BRANCH_CHOICES = ['main', 'build', 'testing']
pipeline {
    agent any
    properties([
        parameters([
            [$class                 : 'ValidatingStringParameterDefinition',
                name                   : 'bootloaders_hardware',
                defaultValue           : 'all',
                description            : 'Bundle bootloaders for this hardware only',
                failedValidationMessage: 'Please provide a valid hardware name',
                regex                  : '.*'],
        ]),
    ])
    stages {  
        stage("Build") {
            subbuild = build(job: "downstream",
                parameters:
                     [$class: 'ValidatingStringParameterValue', name: 'bootloaders_hardware', value: params.bootloaders_hardware],
                wait: true,
                propagate: true)
        }
    }
}
