#!/usr/bin/env groovy

node {
    stage('checkout') {
        git branch: 'main', url: 'https://github.com/seyeadamaUASZ/miniecom.git'
    }

    docker.image('jhipster/jhipster:v7.9.3').inside('-u jhipster -e MAVEN_OPTS="-Duser.home=./"') {
        stage('check java') {
            bat "java -version"
        }

        stage('clean') {
            bat "chmod +x mvnw"
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
                bat "mvn -ntp verify -P-webapp"
            } catch(err) {
                throw err
            } finally {
                junit '**/target/surefire-reports/TEST-*.xml,**/target/failsafe-reports/TEST-*.xml'
            }
        }

        stage('frontend tests') {
            try {
               bat "npm install"
               bat "npm test"
            } catch(err) {
                throw err
            } finally {
                junit '**/target/test-results/TESTS-results-jest.xml'
            }
        }

        stage('packaging') {
            bat "mvn -ntp verify -P-webapp -Pprod -DskipTests"
            archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
        }
        stage('quality analysis') {
            withSonarQubeEnv(installationName:'sq1'){
                bat 'mvn clean install -DskipTests org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.0.2155:sonar -Dsonar.java.binaries=target/classes'
            }
        }
    }
}
