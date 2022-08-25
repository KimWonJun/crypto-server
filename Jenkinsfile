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
					continueOnError:false, failOnError:true,
					publishers: [
						sshPublisherDesc(
							configName:'CICD_Jenkins',
							transfers : [
								sshTransfer(
									execCommand:'echo "Hello SSH" > helloworld.txt'
								)
							]
						)
					]
				)
            }
		}
    }
}