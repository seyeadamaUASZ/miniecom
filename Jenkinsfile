#!/usr/bin/env groovy

node("master") {
    stage('checkout') {
        git branch: 'main', url: 'https://github.com/seyeadamaUASZ/miniecom.git'
    }

        stage('check java') {
            bat "java -version"
        }

        stage('clean') {
            bat "mvn -ntp clean -P-webapp"
        }
        stage('nohttp') {
            bat "mvn -ntp checkstyle:check"
        }

        stage('install tools') {
            bat "mvn -ntp com.github.eirslett:frontend-maven-plugin:install-node-and-npm@install-node-and-npm"
        }

        stage('npm install') {
            bat "mvn -ntp com.github.eirslett:frontend-maven-plugin:npm"
        }
        stage('backend tests') {
            try {
                bat "mvn -ntp test -P-webapp"
            } catch(err) {
                throw err
            } finally {
                junit '**/target/surefire-reports/TEST-*.xml,**/target/failsafe-reports/TEST-*.xml'
            }
        }

        stage('packaging') {
            bat "mvn -ntp package -P-webapp -Pprod -DskipTests"
            archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
        }

        stage('Docker Build') {
            bat 'mvn verify -DskipTests jib:dockerBuild'
        }
         stage('Docker Push') {
             withCredentials([usernamePassword(credentialsId: '082bdb62-13e2-4e45-8435-3c987256309f', passwordVariable: 'Amadou031', usernameVariable: 'adama93')]) {
             bat "docker login -u adama93 -p Amadou031"
             bat 'docker push adama93/miniecom:latest'
          }
         }
        /*stage('quality analysis') {
            withSonarQubeEnv(installationName:'sq1'){
                bat 'mvn -Pprod clean verify sonar:sonar -Dsonar.host.url=http://localhost:9000'
            }
        }*/
    }

