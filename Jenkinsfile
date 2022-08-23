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
}