import groovy.transform.Field

@Field // make variable global scope
Map FAILED_STAGES = [:]

def runTest(String stageName, Closure body) {
  catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
    try {
      body() // execute body i.e sh 'smth'
      FAILED_STAGES["${stageName}"] = "SUCCESS"
    } catch (e) {
      FAILED_STAGES["${stageName}"] = "FAILED"
      throw e // propagate error
    }

  }
}

@Library("test-library") _

fullpipeline(path:"aziz-test-two",artifact:"animal-husbandry-twoo.war",branchdev:"development")
