node {
  stage('========== Clone repository ==========') {
    checkout scm
  }
  stage('========== Build image ==========') {
    app = docker.build("kimwonjun/crypto-server-dev")
  }
  stage('========== Push image ==========') {
    docker.withRegistry('https://registry.hub.docker.com', 'kimwonjun') {
      app.push("${env.BUILD_NUMBER}")
      app.push("latest")
    }
  }
  stage('========== Run Container on SSH Server ==========') {
	sshagent (credentials: ['cicd_jenkins']) {
		sh "ssh -o StrictHostKeyChecking=no ubuntu@172.31.43.238 'docker pull kimwonjun/crypto-server-dev:latest'"
		sh "ssh -o StrictHostKeyChecking=no ubuntu@172.31.43.238 'docker rmi $(docker images --filter "dangling=true" -q --no-trunc)'"
		sh "ssh -o StrictHostKeyChecking=no ubuntu@172.31.43.238 'docker ps -q --filter name=app-crypto-server-dev | grep -q . && docker rm -f \$(docker ps -aq --filter name=app-crypto-server-dev)'"
		sh "ssh -o StrictHostKeyChecking=no ubuntu@172.31.43.238 'docker run -d --name app-crypto-server-dev -p 8081:8080 kimwonjun/crypto-server-dev:latest'"
	}
  }
}