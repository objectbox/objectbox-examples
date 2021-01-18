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

// Turn off daemon to free memory faster after builds.
String gradleArgs = "-Dorg.gradle.daemon=false --stacktrace"

// https://jenkins.io/doc/book/pipeline/syntax/
pipeline {
    agent any

    options {
        gitLabConnection('objectbox-gitlab-connection')
    }

    triggers {
        upstream(upstreamProjects: "objectbox-java/${env.BRANCH_NAME.replaceAll("/", "%2F")}",
                threshold: hudson.model.Result.FAILURE)
    }

    stages {
        stage('init') {
            steps {
                sh 'chmod +x gradlew'
                sh './gradlew -version'
            }
        }

        stage('build-java') {
            steps {
                sh "./gradlew $gradleArgs clean build"
            }
        }

        stage('run-plain-java-example') {
            steps {
                dir('java-main/objectbox-notes-db') {
                    deleteDir()
                }
                sh "./gradlew $gradleArgs java-main:run"
                sh "./gradlew $gradleArgs java-main:run"
            }
        }
    }

    post {
        always {
            junit '**/build/test-results/**/TEST-*.xml'
            archiveArtifacts artifacts: '**/build/reports/lint-results.html'
        }

        failure {
            updateGitlabCommitStatus name: 'build', state: 'failed'
        }

        success {
            updateGitlabCommitStatus name: 'build', state: 'success'
        }
    }
}
