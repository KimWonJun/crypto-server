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

        stage('dockerizing'){
            steps{
                sh 'docker build -t kimwonjun/crypto-server-dev:$BUILD_NUMBER .'
				sh 'docker build -t kimwonjun/crypto-server-dev:latest .'
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
									execCommand:'docker pull kimwonjun/crypto-server-dev:latest'
								),
								sshTransfer(
									execCommand:'docker stop app-crypto-server-dev'
								),
								sshTransfer(
									execCommand:'docker rm app-crypto-server-dev'
								),
								sshTransfer(
									execCommand:'docker run -d --name app-crypto-server-dev -e "SPRING_PROFILES_ACTIVE=dev" -p 8081:8080 kimwonjun/crypto-server-dev:latest'
								)
							]
						)
					]
				)
			}
		}
		stage('Send Alarm to Slack Messenger') {
			steps {
				slackSend(channel:'#crypto프로젝트-알림-서비스', color:'#00FF00', message:'Deploy Finished', teamDomain:'crypto-Project'), tokenCredentialId:'slack-notifier';
			}
		}
    }
}