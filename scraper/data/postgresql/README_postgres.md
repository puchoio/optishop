### creating postgres image with persistent volumen (https://severalnines.com/database-blog/using-kubernetes-deploy-postgresql)

1. Create configmap for the image: 

```bash
apiVersion: v1
kind: ConfigMap
metadata:
  name: postgres-config
  labels:
    app: postgres
data:
  POSTGRES_DB: scraperdb
  POSTGRES_USER: scraper
  POSTGRES_PASSWORD: password
```

2. Create postgres-storage for the image, being the persistentVolume the creation of the volume and the claim that does the binding: 

```bash
kind: PersistentVolume
apiVersion: v1
metadata:
  name: scraper-pv-volume
  labels:
    type: local
    app: postgres
spec:
  storageClassName: manual
  capacity:
    storage: 5Gi
  accessModes:
    - ReadWriteMany
  hostPath:
    path: "/mnt/data"
---
kind: PersistentVolumeClaim
apiVersion: v1
metadata:
  name: scraper-pv-claim
  labels:
    app: postgres
spec:
  storageClassName: manual
  accessModes:
    - ReadWriteMany
  resources:
    requests:
      storage: 5Gi
```

3. Create deployment for the image, being the deployment the creation of the deployment and the service that creates the service: 

```bash
apiVersion: extensions/v1beta1
kind: Deployment
metadata:
  name: postgres
spec:
  replicas: 1
  template:
    metadata:
      labels:
        app: postgres
    spec:
      containers:
        - name: postgres
          image: postgres:10.4
          imagePullPolicy: "Always"
          ports:
            - containerPort: 5432
          envFrom:
            - configMapRef:
                name: postgres-config
          volumeMounts:
            - mountPath: /var/lib/postgresql/data
              name: postgredb
      volumes:
        - name: scraperdb
          persistentVolumeClaim:
            claimName: scraper-pv-claim
---
apiVersion: v1
kind: Service
metadata:
  name: postgres
  labels:
    app: postgres
spec:
  type: NodePort
  selector:
    app: scraper
  ports:
   - port: 5432
     targetPort: 5432
     nodePort: 35432
  selector:
   app: postgres
```

4. Test the postgres started up&running 

```bash
kubectl get services

NAME              TYPE       CLUSTER-IP      EXTERNAL-IP   PORT(S)          AGE
postgres          NodePort   <postgres_ip>   <none>        <internal_postgres_port>:30070/TCP   15h

psql -h <postgres_ip> -U scraper --password -p <internal_postgres_port> scraperdb
```
