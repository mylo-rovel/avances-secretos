import time

class BoardArduinoCommunicator: 
	import serial

	def __init__(self, ARDUINO_PORT):
		self.arduino = self.serial.Serial(port=ARDUINO_PORT, baudrate=9600, timeout=2.5)
		print("ESPERANDO CONEXION CON PLACA ARDUINO...")
		# tiempo equivalente al delay que hay en el programa de arduino
		time.sleep(9) # corresponde al tiempo que la valvula requiere para cerrarse completamente
		# OJO. USAMOS ESTO PORQUE TENEMOS UNA SEGUNDA CAÑERIA SIEMPRE ABIERTA PARA NO ACUMULAR PRESION
		print("Placa ARDUINO alcanzada")


	def enviarDatosToArduino(self,simulacionJson, sataBoardClient):
		try:
			print("SIMULACION A ENVIAR:\n", simulacionJson)
			simulacionJsonBytes = simulacionJson.encode()
			self.arduino.write(simulacionJsonBytes)
			time.sleep(1.2)
			mensajeArduino = str(self.arduino.readlines()[0])
			print(f"Secuencias enviadas a ARDUINO")
			print(f"Mensaje de arduino: {mensajeArduino}")
			# raise ERROR si no es exitosa esta parte
			# ENVIAR MENSAJES PARA QUE ARDUINO NOS PUEDA RESPONDER Y ENVIAR DATOS
			while True:
				time.sleep(1)
				self.arduino.write("sendCaudalToSataBoard".encode())
				mensajeArduino = float(self.arduino.readlines()[0])
				print(f"Mensaje de arduino: {mensajeArduino}")
				if mensajeArduino < 0:
					print("VALOR NEGATIVO RECIBIDO. SIMULACION EJECUTADA EXITOSAMENTE")
					break
		except Exception as e:
			print("\nERROR EN EL PROCESO DE COMUNICACION CON ARDUINO")
			print(e)
		finally:
			sataBoardClient.sendAvisoTerminoToCentralCore(0)
	
	def recibirYReenviarDatos(self, sataBoardClient):
		try:
			while True:
				time.sleep(1)
				self.arduino.write("sendDatosToSataBoard".encode())
				mensajeArduino = float(self.arduino.readlines()[0])
				mensajeArduino = mensajeArduino.split("#")
				print(f"Mensaje de arduino: {mensajeArduino}")
				sataBoardClient.sendDatosToCentralCore(mensajeArduino[2])
		except Exception as e:
			print("\nERROR EN EL PROCESO DE COMUNICACION CON ARDUINO")
			print(e)