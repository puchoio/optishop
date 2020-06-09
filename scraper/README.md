# scraper project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```bash
./mvnw quarkus:dev
```

## Packaging and running the application

The application can be packaged using

```bash
./mvnw package
```

It produces the `scraper-1.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

The application is now runnable using

```bash
java -jar target/scraper-1.0-SNAPSHOT-runner.jar
```

## Creating a native executable

You can create a native executable using

```bash
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using

```bash
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with

```bash
./target/scraper-1.0-SNAPSHOT-runner
```

If you want to learn more about building native executables, please consult https://quarkus.io/guides/building-native-image.

## Configmap for the deployment in quarkus

```bash
apiVersion: v1
kind: ConfigMap
metadata:
  name: scraper-config
  namespace: optishop
data:
  application.properties:
    manolo=manoloPerez
```

## And add the volume to the deployement:

```bash
volumeMounts:
- name: scraper-volume
  mountPath: /work/config
```
