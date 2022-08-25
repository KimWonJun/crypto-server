pipeline {
    agent any
	environment {
		DOCKER_PULL_IMAGE = 'docker pull kimwonjun/crypto-server-dev:latest'
		DOCKER_REMOVE_CONTAINER = 'docker ps -q --filter name=app-crypto-server-dev | grep -q . && docker rm -f $(docker ps --filter name=app-crypto-server-dev)'
		DOCKER_REMOVE_IMAGE = 'docker rmi \"$\"(docker images --filter "dangling=true" -q --no-trunc)'
		DOCKER_RUN_CONTAINER = 'docker run -d --name app-crypto-server-dev -p 8081:8080 kimwonjun/crypto-server-dev:latest'
	}
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
                sh 'docker build -t kimwonjun/crypto-server-dev:$BUILD_NUMBER .'
            }
        }

        stage('Push Image to Docker hub') {
            steps {
				withDockerRegistry([credentialsId: 'kimwonjun', url:'']) {
					sh 'docker push kimwonjun/crypto-server-dev:latest'
					sh 'docker push kimwonjun/crypto-server-dev:$BUILD_NUMBER'
                }
            }
        }
		stage('Run Container on SSH Server'){
			steps{
				sshPublisher(
					publishers: [
						sshPublisherDesc(
							configName : 'Crypto_Dev_Server',
							transfers : [
								sshTransfer(
									execCommand:'${DOCKER_PULL_IMAGE}'
									execCommand:'${DOCKER_REMOVE_CONTAINER}'
									execCommand:'${DOCKER_REMOVE_IMAGE}'
									execCommand:'${DOCKER_RUN_CONTAINER}'
								)
							]
						)
					]
				)
			}
		}
    }
}