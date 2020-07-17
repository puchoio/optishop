apiVersion: v1
kind: Service
metadata:
name: helloworld
labels:
  app: helloworld
spec:
  selector:
    app: helloworld
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: helloworld-v1
spec:
  replicas: 1
  template:
    metadata:
      labels:
        app: helloworld
        version: v1
    spec:
      containers:
      - image: helloworld-v1
        ...
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: helloworld-v2
spec:
  replicas: 1
  template:
    metadata:
      labels:
        app: helloworld
        version: v2
    spec:
      containers:
      - image: helloworld-v2
