HARDWARES = ["test", "echo", "ball" ]
VALID_HARDWARES = "all" + HARDWARES

def numChangedFiles = 0
def changeLogSets = currentBuild.changeSets
for (int i = 0; i < changeLogSets.size(); i++) {
  def entries = changeLogSets[i].items
  for (int j = 0; j < entries.length; j++) {
    def entry = entries[j]
    def files = new ArrayList(entry.affectedFiles)
    for (int k = 0; k < files.size(); k++) {
      def file = files[k]
      println file.path
      if (! file.path.startsWith("scripts/release/")) {
        numChangeFiles = numChangedFiles + 1
      }
    }
  }
}

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

if (numChangedFiles > 0) {
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
            try {
                echo "${params.bootloaders_hardware}"
                bl_hardwares = validateParam(params.bootloaders_hardware, VALID_HARDWARES)
                if (bl_hardwares.contains('all')) {
                    bl_hardwares = HARDWARES
                }
                echo "${bl_hardwares}"
            } catch(err) {
                echo "Caught: ${err}"
            }
        }
    }
}
