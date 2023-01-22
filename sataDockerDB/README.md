PARA CORRER EL BACKEND TAL CUAL ESTÁ ES NECESARIO TENER UNA BASE DE DATOS MYSQL CORRIENDO
PARA SOLUCIONAR LA FALTA DE BASE DE DATOS SEGUIR LOS SIGUIENTES PASOS:

1. Abrir una terminal que apunte a este directorio (/home/satita/Desktop/sataDockerDB)


2. 👉️Si es primera vez que se hace todo esto, ejecutar:
	
	sudo docker-compose up --build --detach
	
   👉️Si ya se ha ejecutado el comando anterior en previas ocasiones (por lo tanto, ya se ha 
   hecho el build de la imagen), el comando a ejecutar sería el siguiente:
   	
   	sudo docker-compose up --detach
   	
   (NOTAR QUE NO ES NECESARIO CONSTRUIR LA IMAGEN)
   (--detach es para dejar el proceso en background, así no termina si salimos de la consola)

3. Ejecutar esto para corroborar que todo salió bien y que tenemos el contenedor corriendo:
	
	sudo docker ps
	
	
NOTA: ES IMPERATIVO UTILIZAR LA keyword "sudo" PARA EJECUTAR COMANDOS DE DOCKER

💾️ Si se busca llenar la base de datos de forma previa, modificar el archivo:
/home/satita/Desktop/sataDockerDB/mysql_docker/seed/seed.sql

💾️ Si se busca modificar las tablas de la base de datos, modificar el archivo:
/home/satita/Desktop/sataDockerDB/mysql_docker/tables/db_tables.sql

EN AMBOS CASOS 💾️ SE DEBE HACER EL BUILD NUEVAMENTE: sudo docker-compose up --build --detach
