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
        stage('quality analysis') {
            withSonarQubeEnv(installationName:'sq1'){
                bat 'mvn -Pprod clean verify sonar:sonar -Dsonar.host.url=http://localhost:9000'
            }
        }
    }

