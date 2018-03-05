/*
 * Copyright 2017 ObjectBox Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// https://jenkins.io/doc/book/pipeline/syntax/
def COLOR_MAP = ['SUCCESS': 'good', 'FAILURE': 'danger', 'UNSTABLE': 'danger', 'ABORTED': 'danger']
pipeline {
    agent any

    triggers {
        upstream(upstreamProjects: "objectbox-java/${env.BRANCH_NAME.replaceAll("/", "%2F")}",
                threshold: hudson.model.Result.FAILURE)
    }

    stages {
        stage('build-java') {
            steps {
                sh './gradlew --stacktrace clean build'
            }
        }

        stage('run-plain-java-example') {
            steps {
                dir('java-main/objectbox-notes-db') {
                    deleteDir()
                }
                sh './gradlew java-main:run'
                sh './gradlew java-main:run'
            }
        }
    }

    post {
        always {
            junit '**/build/test-results/**/TEST-*.xml'
            archive '**/build/reports/lint-results.html'
        }

        changed {
            slackSend color: COLOR_MAP[currentBuild.currentResult],
                    message: "Changed to ${currentBuild.currentResult}: ${currentBuild.fullDisplayName}\n${env.BUILD_URL}"
        }

        failure {
            slackSend color: "danger",
                    message: "Failed: ${currentBuild.fullDisplayName}\n${env.BUILD_URL}"
        }
    }
}
