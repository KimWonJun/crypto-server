node {
  environment{
	registry = "kimwonjun/crypto-server-dev"
	registryCredential = 'kimwonjun'
  }
  stage('========== Clone repository ==========') {
    checkout scm
  }
  stage('========== Build image ==========') {
    app = docker.build("kimwonjun/crypto-server-dev")
  }
  stage('========== Push image ==========') {
    docker.withRegistry('', registryCredential) {
      app.push("${env.BUILD_NUMBER}")
      app.push("latest")
    }
  }
}