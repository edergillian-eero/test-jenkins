HARDWARES = ["test", "echo", "ball" ]
VALID_HARDWARES = "all" + HARDWARES


def tokenizeAndTrim(string, token) {
    def items = []
    for (item in string.tokenize(token)) {
        item = item.trim()
        if (items.contains(item)) {
            // skip duplicates
            continue
        } else {
            items.add(item)
        }
    }
    return items
}

def validateParam(param, valid) {
    items = tokenizeAndTrim(param, ',')
    for (p in items) {
       if (valid.contains(p)) {
            continue
        } else {
            throw new Exception("Invalid input: ${p}")
        }
    }
    return items
}

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
            sh "echo ${params.bootladers_hardware}"
            try {
              bl_hardwares = validateParam(params.bootloaders_hardware, VALID_HARDWARES)
              if (bl_hardwares.contains('all')) {
                  bl_hardwares = HARDWARES
              }
            } catch(err) {
              echo "Caught: ${err}"
            }
        }
    }
}
