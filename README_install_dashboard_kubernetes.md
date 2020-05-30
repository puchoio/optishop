Install dashboard kubernetes

1. Apply dashboard yml:

```bash
kubectl apply -f https://raw.githubusercontent.com/kubernetes/dashboard/v2.0.1/aio/deploy/recommended.yaml
```

2. run proxy
```bash
kubectl proxy
``` 

3. create yml with ServiceAccount and ClusterRoleBinding
```bash
#ServiceAccount
apiVersion: v1
kind: ServiceAccount
metadata:
  name: admin-user
  namespace: kubernetes-dashboard
---
#ClusterRoleBinding
apiVersion: rbac.authorization.k8s.io/v1
kind: ClusterRoleBinding
metadata:
  name: admin-user
roleRef:
  apiGroup: rbac.authorization.k8s.io
  kind: ClusterRole
  name: cluster-admin
subjects:
- kind: ServiceAccount
  name: admin-user
  namespace: kubernetes-dashboard

```

4. apply config
```bash
 kubectl apply -f kubernetesDepoymentDashboard/ 	
```

5. get the token 
```bash 
kubectl -n kubernetes-dashboard describe secret $(kubectl -n kubernetes-dashboard get secret | grep admin-user | awk '{print $1}')   
```

6. now go to the browser using the url and introduce the token you get previously
```bash
http://localhost:8001/api/v1/namespaces/kubernetes-dashboard/services/https:kubernetes-dashboard:/proxy/
```

ref link:
[https://github.com/kubernetes/dashboard/blob/master/docs/user/access-control/creating-sample-user.md]
[https://github.com/kubernetes/dashboard]