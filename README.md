https://quarkus.io/guides/getting-started#bootstrapping-the-project
	What will you need:
		[GraalVM](https://www.graalvm.org/downloads)
		Java 8+
		Maven 3.6.2+
	Generate project: mvn io.quarkus:quarkus-maven-plugin:1.4.2.Final:create -DprojectGroupId=com.jamon -DprojectArtifactId=scraper -DclassName="org.jamon.resource.started.GreetingResource" -Dpath="/hello
	Build 4 jvm: mvn clean install
	Build 4 ownNative: mvn package -Pnative
	Build 4 containerNative: mvnw package -Pnative -Dquarkus.native.container-build=true
	Build Image: docker build -f src/main/docker/Dockerfile.native -t pucho/scraper:0.0.1 .
	Run image: docker run -i --rm -p 8080:8080 pucho/scraper:0.0.1
