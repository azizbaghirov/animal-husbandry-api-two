@Library("test-library") _

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

fullpipeline(path:"aziz-test-two",artifact:"animal-husbandry-twoo.war",branchdev:"development")
//sendGoogleChatBuildReport(Version: env.VERSION, url="https://chat.googleapis.com/v1/spaces/AAAAdmKlymY/messages?key=AIzaSyDdI0hCZtE6vySjMm-WEfRq3CPzqKqqsHI&token=N2ciE_AgygArGpT28EEM5xWj6tnVASe91UHkVu1nKsU",
  //  message: "This is a <strike>simple</strike> <i>card<i> text message " +
    //             "with a <a href=\"https://github.com/mkutz/jenkins-google-chat-notification\">link</a>" +
      //           "<br>and a line break, " +
        //        "which does not support mention @all users in the Group.")
