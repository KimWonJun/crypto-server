pipeline {
    agent any

    stages {
        stage('Prepare') {
            agent any

            steps {
                checkout scm
            }

            post {

                success {
                    echo 'prepare success'
                }

                always {
                    echo 'done prepare'
                }

                cleanup {
                    echo 'after all other post conditions'
                }
            }
        }

        stage('build gradle') {
            steps {
				sh 'chmod +x ./gradlew'
                sh './gradlew clean build'

                sh 'ls -al ./build'
            }
            post {
                success {
                    echo 'gradle build success'
                }

                failure {
                    echo 'gradle build failed'
                }
            }
        }

        stage('dockerizing'){
            steps{
                sh 'docker . build -t kimwonjun/crypto-server-dev:$BUILD_NUMBER'
            }
        }

        stage('Push Image to Docker hub') {
            steps {
				withCredentials([usernamePassword(credentialsId: 'kimwonjun', passwordVariable: 'dockerHubPwd')]) {
                    sh "docker login -u kimwonjun -p ${dockerHubPwd}"
					sh 'docker push kimwonjun/crypto-server-dev:latest'
					sh 'docker push kimwonjun/crypto-server-dev:$BUILD_NUMBER'
                }
            }

            post {
                success {
                    echo 'success'
                }

                failure {
                    echo 'failed'
                }
            }
        }
		stage('Run Container on SSH Server'){
			steps{
                echo 'SSH'
                sshagent (credentials: ['CICD_Jenkins']) {
					withDockerRegistry([credentialsId:'kimwonjun', url:'https://registry.hub.docker.com']) {
						sh "ssh -o StrictHostKeyChecking=no ubuntu@43.200.219.169 'docker pull kimwonjun/crypto-server-dev'"
						sh "ssh -o StrictHostKeyChecking=no ubuntu@43.200.219.169 'docker ps -q --filter name=app-crypto-server-dev | grep -q . && docker rm -f \$(docker ps -aq --filter name=app-crypto-server-dev)'"
						sh "ssh -o StrictHostKeyChecking=no ubuntu@43.200.219.169 'docker run -d --name app-crypto-server-dev -p 8081:8080 kimwonjun/crypto-server-dev'"
					}
                }

            }
		}
    }
}