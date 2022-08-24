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
      docker.withServer ('ssh://ec2-43-200-219-169.ap-northeast-2.compute.amazonaws.com', 'cicd_jenkins') {
        docker.withRegistry('https://registry.hub.docker.com', 'kimwonjun') {
		  docker.pull('kimwonjun/crypto-server-dev:latest')
		}
        sh 'docker rmi $(docker images --filter "dangling=true" -q --no-trunc)'
        sh 'docker ps -q --filter name=app-crypto-server-dev | grep -q . && docker rm -f \$(docker ps -aq --filter name=app-crypto-server-dev)'
        sh 'docker run -d --name app-crypto-server-dev -p 8081:8080 kimwonjun/crypto-server-dev:latest'
        }
    }
}