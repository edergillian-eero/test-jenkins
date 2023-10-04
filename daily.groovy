def BRANCH_CHOICES = ['main', 'build', 'testing']
node('master') {
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
    stage("Build") {
        subbuild = build(job: "downstream",
            parameters:
                string(name: 'bootloaders_hardware', value: params.bootloaders_hardware),
            wait: true,
            propagate: true)
    }
}
